package com.badlogic.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UniSim extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new StartScreen(this));
    }

    public void render() {
        super.render();
    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
