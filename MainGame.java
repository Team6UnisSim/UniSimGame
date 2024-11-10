package com.badlogic.unisim;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Represens the screen used for the game itself.
 */
public class MainGame implements Screen {
    
    final UniSim game;
    private OrthographicCamera cam; // camera used to view the game.
    private Map map; // Represents the game's map.
    private ArrayList<Building> buildings; // list holding all buildings that have been created.
    private int mapWidth, mapHeight;
    private LocalTime startTime; 
    private Stage stage; // used to order components - in this case, handles the timer, help and counters

    public MainGame(final UniSim game) { // passes in main application
        this.game = game;

        map = new Map();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        
        cam = new OrthographicCamera(mapWidth, mapHeight);
        cam.position.set(mapWidth/2, mapHeight/2, 0); // centers the camera
        cam.update();

        buildings = new ArrayList<>();

        stage = new Stage(); // will separate certain components from camera movement

        startTime = LocalTime.now(); // sets the start time to current time - used with counter
    }

    /**
     * Handles the resizing of the window, as camera view will need updated.
     * @param width the width of the new window.
     * @param height the height of the new window.
     */
    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();

        stage.getViewport().update(width, height, true);
    }

    /**
     * Draws everything on the screen (constant loop while game running) and delegates inputs.
     */
    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen every frame

        game.batch.begin();

        map.render(cam, game.batch); // draw map

        boolean placingBuilding = false;
        for(Building i : buildings) { // deal with logic and draw all buildings
            i.handleLogic(cam, map, buildings);
            i.render(game.batch);
            if(!i.getIsPlaced()) { // prevents multiple buildings being placed simultaneously
                placingBuilding = true;
            }
        }

        handleInput(placingBuilding); // call input handling and update camera once processed
        cam.update();

        // draw counters
        int[] counts = countBuildings();
        game.font.draw(game.batch, "Accommodations: " + Integer.toString(counts[0]), 70, 465);
        game.font.draw(game.batch, "Canteens: " + Integer.toString(counts[1]), 210, 465);
        game.font.draw(game.batch, "Libraries: " + Integer.toString(counts[2]), 300, 465);
        game.font.draw(game.batch, "Bars: " + Integer.toString(counts[3]), 380, 465);
        
        checkTimer(); // delegate timer handling

        // deals with help menus
        game.font.draw(game.batch, "Hold H for help!", 450, 465);
        getHelp();

        // processes stage actions and draws stage components
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        game.batch.end();
    }

    /**
     * Deals with (most) of the possible inputs during the game.
     * @param placingBuilding boolean value that indicates if a building is currently being placed
     */
    private void handleInput(boolean placingBuilding) {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) { // register left-click
            cam.translate(cam.zoom*-Gdx.input.getDeltaX(), cam.zoom*Gdx.input.getDeltaY()); // moves camera according to mouse movement - speed according to zoom
        }

        // allow camera to zoom in/out with I/O pressed
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            cam.zoom -= 0.02;
        }else if(Gdx.input.isKeyPressed(Input.Keys.O)) {
            cam.zoom += 0.02;
        }

        // prevents inverse zoom and adjusts zoom cap based on window size (allows resizing to actually work)
        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 0.45f*(1/cam.viewportWidth*cam.viewportHeight)); 

		float effectiveWidth = cam.viewportWidth * cam.zoom;
		float effectiveHeight = cam.viewportHeight * cam.zoom;

        // stops the camera from moving outside the bounds of the map
		cam.position.x = MathUtils.clamp(cam.position.x, effectiveWidth / 2, mapWidth - effectiveWidth / 2);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveHeight / 2, mapHeight - effectiveHeight / 2);
    
        // allows a new building to be placed down - keys 1, 2, 3 and 4 for different types
        if(!placingBuilding) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { 
                createBuilding("Accommodation");
            }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                createBuilding("Canteen");
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                createBuilding("Library");
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                createBuilding("Bar");
            }
        }
    }

    /**
     * Creates a new instance of {@code Building} and adds it to the list of buildings.
     * @param buildingType the type of the building to be created (e.g Library) - currently only 4.
     * @return the new instance of {@code Building} - needed to stop other buildings being able to be placed.
     */
    public Building createBuilding(String buildingType) {
        Building building = new Building(buildingType);
        buildings.add(building);
        return building;
    }

    /**
     * Displays the controls when H key pressed (needed to be in a separate method to other input handling).
     */
    public void getHelp() {
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.font.draw(game.batch, "Left click to move the map", 20, 220);
            game.font.draw(game.batch, "Right click to confirm placement", 20, 200);
            game.font.draw(game.batch, "Hold I/O to zoom in/out", 20, 180);
            game.font.draw(game.batch, "Press 1 to place down an Accommodation", 20, 160);
            game.font.draw(game.batch, "Press 2 to place down a Canteen", 20, 140);
            game.font.draw(game.batch, "Press 3 to place down a Library", 20, 120);
            game.font.draw(game.batch, "Press 4 to place down a Bar", 20, 100);
        }
    }

    /**
     * Iterates through the ArrayList {@code buildings} and adds up the totals of different building types.
     * @return a list of 4 ints with type numbers - order is Accommodation, Canteen, Library and Bar.
     */
    public int[] countBuildings() {
        int[] buildingCounts = {0, 0, 0, 0};
        for(Building i : buildings) { 
            if(i.getType() == "Accommodation") { // gets the type of all {@code Building} instances in the list
                buildingCounts[0] ++;
            }else if (i.getType() == "Canteen") {
                buildingCounts[1] ++;
            }else if (i.getType() == "Library") {
                buildingCounts[2] ++;
            }else if (i.getType() == "Bar") {
                buildingCounts[3] ++;
            }
        }
        return buildingCounts;
    }

    /**
     * Increments and displays the timer, then checks if the game is over - direct to {@code EndScreen} if so.
     */
    public void checkTimer() {
        long elapsedTime = Duration.between(startTime, LocalTime.now()).getSeconds(); // gets how much time has passed since game start in seconds

        if(elapsedTime%60 < 10) {      
            game.font.draw(game.batch, "0" + elapsedTime/60 + ":0" + elapsedTime%60, 20, 465); // displays minutes then seconds (adds extra 0 if below 10)
        }else {
            game.font.draw(game.batch, "0" + elapsedTime/60 + ":" + elapsedTime%60, 20, 465);
        }
        if(elapsedTime > 300) { // checks if 5 minutes reached
            game.setScreen(new EndScreen(game, countBuildings()));
            dispose();
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
    }

    // rest of the methods are not currently used but needed overridden

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}