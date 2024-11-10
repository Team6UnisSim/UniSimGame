package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the screen after the end of the game - displays stats and allows another to be started.
 */
public class EndScreen implements Screen {
    
    final UniSim game;
    private OrthographicCamera cam; // camera used to view the game.
    private int[] buildingCounts;
    private Sprite playAgainButton; // Sprite representing the texture and position of the button.

    public EndScreen(final UniSim game, int[] buildingCounts) { // passes in main application
        this.game = game;
        this.buildingCounts = buildingCounts;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 320); // sets the camera to 2d view with a given size

        playAgainButton = new Sprite(new Texture("Play_Again.png")); // creates a new Sprite using that texture
        playAgainButton.setPosition(cam.viewportWidth/2 - (playAgainButton.getWidth()/2), cam.viewportHeight/2 - 60);
        playAgainButton.setScale(2);

    }

    /**
     * Used to draw everything on this screen.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2667f, 0.6235f, 0.5922f, 1); // sets the background to the desired colour
        
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        
        playAgainButton.draw(game.batch);
        game.font.draw(game.batch, "You placed: ", cam.viewportWidth/2 - 40, cam.viewportHeight/2 + 50);
        game.font.draw(game.batch, Integer.toString(buildingCounts[0]) + " Accommodations", cam.viewportWidth/2 - 200, cam.viewportHeight/2 + 30);
        game.font.draw(game.batch, Integer.toString(buildingCounts[1]) + " Canteens", cam.viewportWidth/2 - 70, cam.viewportHeight/2 + 30);
        game.font.draw(game.batch, Integer.toString(buildingCounts[2]) + " Libraries", cam.viewportWidth/2 + 10, cam.viewportHeight/2 + 30);
        game.font.draw(game.batch, Integer.toString(buildingCounts[3]) + " Bars", cam.viewportWidth/2 + 85, cam.viewportHeight/2 + 30);
        
        handleInput();

        game.batch.end();
    }

    /**
     * Handles all inputs (in this case, hovering/clicking on the button).
     */
    private void handleInput() {
        Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); // Gets position of mouse in world co-ordinates
        if(playAgainButton.getBoundingRectangle().contains(pos.x, pos.y)) { // checks if mouse is hovering over button
            playAgainButton.setColor(1, 1, 1, 1); // recolours button if hovered.
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGame(game)); // starts new game if clicked.
                dispose();
            }
        }else {
            playAgainButton.setColor(0.6078f, 0.6078f, 0.6078f, 1); 
        }
    }

    // rest of the methods are not currently used but needed overridden

    @Override
    public void pause() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }
    
}