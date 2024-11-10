package com.badlogic.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents the main application, which can then move between different screens - in this case, {@code MainGame}, 
 * {@code StartScreen} and {@code EndScreen}
 */
public class UniSim extends Game {
    public SpriteBatch batch; // same SpriteBatch shared for rendering all assets
    public BitmapFont font; // used as the font for the whole application

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new StartScreen(this)); // this is the screen first displayed by the application
    }

    // not really used
    public void render() {
        super.render();
    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
