package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen implements Screen {
    final UniSim game;
    OrthographicCamera cam;
    Sprite startButton;
    Texture logo;

    public StartScreen(final UniSim game) {
        this.game = game;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 480, 320);
    
        startButton = new Sprite(new Texture("Start.png"));
        startButton.setPosition(cam.viewportWidth/2 - startButton.getWidth()/2, cam.viewportHeight/2 - 20);
        startButton.setScale(2);

        logo = new Texture("Logo.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2667f, 0.6235f, 0.5922f, 1);
        
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        
        game.batch.draw(logo, cam.viewportWidth/2 - logo.getWidth() - 25, (cam.viewportHeight/2) + 40, 200, 100);
        startButton.draw(game.batch);
        handleInput();

        game.batch.end();
    }

    private void handleInput() {
        Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if(startButton.getBoundingRectangle().contains(pos.x, pos.y)) {
            startButton.setColor(1, 1, 1, 1);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGame(game));
                dispose();
            }
        }else {
            startButton.setColor(0.6078f, 0.6078f, 0.6078f, 1);
        }
    }

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