package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Building {
    
    Sprite image;
    String buildingType;
    private boolean isPlaced;

    public Building(int x, int y, String buildingType) {
        image = new Sprite(new TextureRegion(new Texture("free.png"), x, y), 20, 10, 50, 50);
        this.buildingType = buildingType;
        isPlaced = false;
    }

    public void render(SpriteBatch batch) {
        image.draw(batch);
    }

    public String getType() {
        return buildingType;
    }

    public Sprite getSprite() {
        return image;
    }

    public void handleLogic(OrthographicCamera cam, Map map, Array<Building> buildings) {
        Rectangle buildingRectangle = image.getBoundingRectangle();
        
        if(!isPlaced) {
            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            image.setPosition(pos.x - buildingRectangle.width/2, pos.y - buildingRectangle.height/2);
            if(checkCollisions(map, buildings, buildingRectangle)) {
                image.setColor(0, 1, 0, 0.65f);
                if(Gdx.input.isTouched()) {
                    isPlaced = true;
                    image.setColor(1, 1, 1, 1f);
                }
            }else {
                image.setColor(1, 0, 0, 0.65f);
            }
        }

        if(Gdx.input.justTouched()) {
            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(buildingRectangle.contains(pos.x, pos.y)) {
                System.out.println(buildingType);
            }
        }
    }

    public boolean checkCollisions(Map map, Array<Building> buildings, Rectangle buildingRectangle) {
        for(float x = buildingRectangle.x; x < (buildingRectangle.x + buildingRectangle.width); x += 16) {
            for(float y = buildingRectangle.y; y < (buildingRectangle.y + buildingRectangle.height); y += 16) {
                if(x >= 0 && y >= 0 && x < 480 && y < 320) {
                    TiledMapTile tile = map.getTileAtLocation(1, x, y);
                    if(tile.getId() != 19) {
                        return false;
                    }
                }
            }
        }
        Array<Building> otherBuildings = new Array<Building>(buildings);
        otherBuildings.removeIndex(otherBuildings.indexOf(this, true));
        for(Building i : otherBuildings) {
            if(buildingRectangle.overlaps(i.getSprite().getBoundingRectangle())) {
                return false;
            }
        }
        return true;
    }
}
