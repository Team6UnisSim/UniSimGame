package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Building {
    
    Sprite image;
    String buildingType;
    int width, height;
    Texture texture;
    private boolean isPlaced;

    public Building(int x, int y, String name) {
        texture = new Texture("free.png");
        image = new Sprite(texture, x, y);
        width = texture.getWidth();
        height = texture.getHeight();
        isPlaced = false;
    }

    public void render(SpriteBatch batch) {
        image.draw(batch);
    }

    public String getType() {
        return buildingType;
    }

    public boolean checkPlaced() {
        return isPlaced;
    }

    public void handleLogic() {
        if(!isPlaced) {
            image.setAlpha(0.65f);
            image.setPosition(Gdx.input.getX() - width/2, 320 - (Gdx.input.getY() - height/2));
            if(Gdx.input.isTouched()) {
                isPlaced = true;
                image.setAlpha(1f);
            }
        }

        if(Gdx.input.justTouched()) {
            Rectangle buildingRectangle = image.getBoundingRectangle();
            if(buildingRectangle.contains(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.print("House");
            }
        }
    }
}
