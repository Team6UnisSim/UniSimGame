package com.badlogic.unisim;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Represents the game's map, which was created in a map editor (I used Tiled) and loaded into the game via a
 * {@code TmxMapLoader} as an instance of {@code TiledMap} (built-in libGDX type).
 */
public class Map {
    
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    final int TILE_SIZE = 16;

    public Map() {
        map = new TmxMapLoader().load("UniSimMap.tmx"); // loads in the map object from file
        mapRenderer = new OrthogonalTiledMapRenderer(map); // initialise renderer (lets you render the map)
    }

    public void render(OrthographicCamera cam, SpriteBatch batch) {
        mapRenderer.setView(cam);
        mapRenderer.render(); // draw map
    }

    public void dispose() {
        map.dispose();
    }

    /**
     * Gets the tile at the specified location in layer 2 of the map (indexed from 0 in Java)
     * @param x
     * @param y
     * @return
     */
    public TiledMapTile getTile(float x, float y) {
        Cell mapCell = ((TiledMapTileLayer) map.getLayers().get(1)).getCell((int) x / TILE_SIZE, (int) y / TILE_SIZE);

        if (mapCell != null) {
            TiledMapTile tile = mapCell.getTile();
            if (tile != null) {
                return tile;
            }
        }
        return null;
    }

    public int getWidth() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getWidth()*TILE_SIZE;
    }

    public int getHeight() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getHeight()*TILE_SIZE;
    }   
}
