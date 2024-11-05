package com.badlogic.unisim;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;

    public Map() {
        map = new TmxMapLoader().load("testMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(OrthographicCamera cam, SpriteBatch batch, Texture texture) {
        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    public void update (float delta) {

    }

    public void dispose() {
        map.dispose();
    }

    public int getWidth() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getWidth();
    }

    public int getHeight() {
        return ((TiledMapTileLayer) map.getLayers().get(0)).getHeight();
    }

    public int getLayer() {
        return map.getLayers().getCount();
    }
    
}
