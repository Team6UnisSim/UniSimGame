package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the inital menu class
 */
public class StartScreen implements Screen {
    
    final UniSim game;
    private OrthographicCamera cam; // camera used to view the game.
    private Sprite startButton; // a Sprite representing the location and texture of the start button.
    private Texture logo; // a Texture used to load in the logo image.

    public StartScreen(final UniSim game) { // passes in main application
        this.game = game;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 320); // sets the camera to 2d view with a given size
     
        startButton = new Sprite(new Texture("Start.png")); // creates a new Sprite using that texture
        startButton.setPosition(cam.viewportWidth/2 - startButton.getWidth()/2, cam.viewportHeight/2 - 20);
        startButton.setScale(2);

        logo = new Texture("Logo.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2667f, 0.6235f, 0.5922f, 1); // sets the background to the desired colour
        
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        
        // draw logo and button
        game.batch.draw(logo, cam.viewportWidth/2 - logo.getWidth() - 25, (cam.viewportHeight/2) + 40, 200, 100); 
        startButton.draw(game.batch);
        handleInput();

        game.batch.end();
    }

    /**
     * Handles all inputs (in this case, hovering/clicking on the button).
     */
    private void handleInput() {
        Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); // Gets position of mouse in world co-ordinates
        if(startButton.getBoundingRectangle().contains(pos.x, pos.y)) { // checks if mouse is hovering over button
            startButton.setColor(1, 1, 1, 1); // recolours button if hovered.
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGame(game)); // starts new game if clicked.
                dispose();
            }
        }else {
            startButton.setColor(0.6078f, 0.6078f, 0.6078f, 1);
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