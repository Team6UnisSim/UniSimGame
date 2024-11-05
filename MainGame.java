package com.badlogic.unisim;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame implements ApplicationListener {
    
    OrthographicCamera cam;
    SpriteBatch batch;
    Map map;
    Texture example;
    Building building;
    Building building2;
    Stage stage;

    @Override
    public void create() {
        // Prepare your application here.
        batch = new SpriteBatch();
        map = new Map();
        cam = new OrthographicCamera(480, 320);
        example = new Texture("free.png");
        building = new Building(240, 160, "1");
        building2 = new Building(120, 40, "2");
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        myActor actor = new myActor();
        stage.addActor(actor);

        cam.position.set(240, 160, 0);
        cam.update();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        cam.viewportWidth = 480f;
		cam.viewportHeight = 480f * height/width;
		cam.update();

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        // Draw your application here.
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render(cam, batch, example);

        batch.begin();
        building.handleLogic();
        building.render(batch);
        if(building.checkPlaced()) {
            building2.handleLogic();
            building2.render(batch);
        }
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            cam.translate(cam.zoom*-Gdx.input.getDeltaX(), cam.zoom*Gdx.input.getDeltaY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            cam.zoom -= 0.02;
        }else if(Gdx.input.isKeyPressed(Input.Keys.O)) {
            cam.zoom += 0.02;
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 0.8f);

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 480 - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 320 - effectiveViewportHeight / 2f);
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
    public void dispose() {
        // Destroy application's resources here.
        batch.dispose();
        stage.dispose();
    }
}