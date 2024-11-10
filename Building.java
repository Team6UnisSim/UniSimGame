package com.badlogic.unisim;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Represents a building which can be placed on the map - takes the building type (Accommodation, Library, Canteen or Bar) as a parameter.
 */
public class Building {
    
    private Sprite sprite; // a Sprite object holds the texture and position of the building (and allows it to be recoloured).
    private String buildingType;
    private boolean isPlaced;

    public Building(String buildingType) {
        sprite = new Sprite(new Texture(buildingType + ".png")); // relies on properly named image files
        this.buildingType = buildingType;
        isPlaced = false;
    }

    /**
     * Draws this instance of {@code Building}.
     * @param batch the SpriteBatch used to render it.
     */
    public void render(SpriteBatch batch) {
        sprite.draw(batch); 
    }

    public String getType() {
        return buildingType;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean getIsPlaced() {
        return isPlaced;
    }

    /**
     * Currently only handles placing down buildings - when first created, will follow mouse and check for collisions - if detected, it will go
     * red (otherwise green) - if right clicked while green, it will be placed.
     * @param cam the game's camera (only used to get mouse co-ordinates)
     * @param map the game's map (instance of the {@code Map} class).
     * @param buildings the array of all other existing instances of the {@code Building} class.
     */

    public void handleLogic(OrthographicCamera cam, Map map, ArrayList<Building> buildings) {
        Rectangle buildingRectangle = sprite.getBoundingRectangle(); // gets the bounding rectangle of the building

        if(!isPlaced) {
            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); // gets the location of the mouse in world co-ordinates
            sprite.setPosition(pos.x - buildingRectangle.width/2, pos.y - buildingRectangle.height/2); // gets the sprite to follow mouse
            if(checkCollisions(map, buildings, buildingRectangle)) { // checks for any collisions
                sprite.setColor(0, 1, 0, 0.65f); // sets colour to green if none found
                if(Gdx.input.isButtonPressed(Buttons.RIGHT)) { // places building (resets colour and makes it unmovable)
                    isPlaced = true;
                    sprite.setColor(1, 1, 1, 1f);
                }
            }else {
                sprite.setColor(1, 0, 0, 0.65f); // sets colour to red if collisions found
            }
        }
    }

    /**
     * Checks if the building's sprite is overlapping with any map feature (trees, roads, water etc.) or any other instance of {@code Building} - 
     * all checked map features need to be in the same layer (in this case layer 2 - counted from 0 in Java).
     * @param map the game's map (instance of the {@code Map} class).
     * @param buildings the array of all other existing instances of the {@code Building} class.
     * @param buildingRectangle the bounding rectangle of this instance of {@code Building}'s sprite (used for positional info).
     * @return a boolean value that set to true if {@code buildingRectangle} isn't overlapping anything.
     */
    public boolean checkCollisions(Map map, ArrayList<Building> buildings, Rectangle buildingRectangle) {

        // checking collisions with map features (trees, roads, etc.)
        for(float x = buildingRectangle.x; x <= (buildingRectangle.x + buildingRectangle.width); x += map.TILE_SIZE) {
            for(float y = buildingRectangle.y; y <= (buildingRectangle.y + buildingRectangle.height); y += map.TILE_SIZE) { // checks every tile that overlaps the sprite
                if(x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight()) { // only checks tiles within the map
                    TiledMapTile tile = map.getTile(x, y); // gets the tile object from the map object
                    if(tile != null) { // if there is actually anything in the tile returns false
                        return false;
                    }
                }
            }
        }

        // checking collisions with other buildings that have been placed
        ArrayList<Building> otherBuildings = new ArrayList<Building>(buildings); // copies the array of buildings
        otherBuildings.remove(this); // removes self from the array
        for(Building i : otherBuildings) {
            if(buildingRectangle.overlaps(i.getSprite().getBoundingRectangle())) { // checks if self overlaps with other building sprites
                return false;
            }
        }
        return true;
    }
}
