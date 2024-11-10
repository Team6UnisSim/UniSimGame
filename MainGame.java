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

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame implements Screen {
    final UniSim game;
    
    OrthographicCamera cam;
    Map map;
    ArrayList<Building> buildings;
    int mapWidth, mapHeight;
    LocalTime startTime;

    private Stage stage;

    public static boolean placingBuilding = false;

    public MainGame(final UniSim game) {
        this.game = game;

        // Prepare your application here.
        map = new Map();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        
        cam = new OrthographicCamera(mapWidth, mapHeight);
        cam.position.set(mapWidth/2, mapHeight/2, 0);
        cam.update();

        buildings = new ArrayList<>();

        stage = new Stage();

        startTime = LocalTime.now();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        // Draw your application here.
        game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        map.render(cam, game.batch);

        boolean placingBuilding = false;
        for(Building i : buildings) {
            i.handleLogic(cam, map, buildings);
            i.render(game.batch);
            if(!i.getIsPlaced()) {
                placingBuilding = true;
            }
        }

        handleInput(placingBuilding);
        cam.update();

        int[] counts = countBuildings();
        game.font.draw(game.batch, "Accommodations: " + Integer.toString(counts[0]), 70, 465);
        game.font.draw(game.batch, "Canteens: " + Integer.toString(counts[1]), 210, 465);
        game.font.draw(game.batch, "Libraries: " + Integer.toString(counts[2]), 300, 465);
        game.font.draw(game.batch, "Bars: " + Integer.toString(counts[3]), 380, 465);
        
        checkTimer();
        game.font.draw(game.batch, "Hold H for help!", 450, 465);
        getHelp();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        game.batch.end();
    }

    private void handleInput(boolean placingBuilding) {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            cam.translate(cam.zoom*-Gdx.input.getDeltaX(), cam.zoom*Gdx.input.getDeltaY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            cam.zoom -= 0.02;
        }else if(Gdx.input.isKeyPressed(Input.Keys.O)) {
            cam.zoom += 0.02;
        }

        // prevents inverse zoom
        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 0.45f*(1/cam.viewportWidth*cam.viewportHeight)); // (cam.viewportWidth*cam.viewportHeight/500000)

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        // stops the camera from moving outside the bounds of the map
		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, mapWidth - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, mapHeight - effectiveViewportHeight / 2f);
    
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

    public Building createBuilding(String buildingType) {
        Building building = new Building(buildingType);
        buildings.add(building);
        return building;
    }

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

    public int[] countBuildings() {
        int[] buildingCounts = {0, 0, 0, 0};
        for(Building i : buildings) {
            if(i.getType() == "Accommodation") {
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

    public void checkTimer() {
        long elapsedTime = Duration.between(startTime, LocalTime.now()).getSeconds(); 

        if(elapsedTime%60 < 10) {      
            game.font.draw(game.batch, "0" + elapsedTime/60 + ":0" + elapsedTime%60, 20, 465);
        }else {
            game.font.draw(game.batch, "0" + elapsedTime/60 + ":" + elapsedTime%60, 20, 465);
        }
        if(elapsedTime > 30) { // change back to 300
            game.setScreen(new EndScreen(game, countBuildings()));
            dispose();
        }
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
    }
}