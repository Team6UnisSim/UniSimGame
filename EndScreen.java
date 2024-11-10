package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen implements Screen {
    final UniSim game;
    OrthographicCamera cam;
    int[] buildingCounts;
    Sprite playAgainButton;

    public EndScreen(final UniSim game, int[] buildingCounts) {
        this.game = game;
        this.buildingCounts = buildingCounts;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 400);

        playAgainButton = new Sprite(new Texture("Play_Again.png"));
        playAgainButton.setPosition(cam.viewportWidth/2 - (playAgainButton.getWidth()/2), cam.viewportHeight/2 - 100);
        playAgainButton.setScale(2);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2667f, 0.6235f, 0.5922f, 1);
        
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

    private void handleInput() {
        Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if(playAgainButton.getBoundingRectangle().contains(pos.x, pos.y)) {
            playAgainButton.setColor(1, 1, 1, 1);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MainGame(game));
                dispose();
            }
        }else {
            playAgainButton.setColor(0.6078f, 0.6078f, 0.6078f, 1);
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