package RoboRally.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PlayScreen implements Screen {
    private TiledMap board;
    private TiledMapTileLayer background, belts, holes, walls, spawn;


    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        board = loader.load("boards/board0.tmx"); // not mentioned in the libGDX / Tiled tutorial, unnecessary?
        background = (TiledMapTileLayer) board.getLayers().get("Background");
        belts = (TiledMapTileLayer) board.getLayers().get("Belts");
        holes = (TiledMapTileLayer) board.getLayers().get("Holes");
        walls = (TiledMapTileLayer) board.getLayers().get("Walls");
        spawn = (TiledMapTileLayer) board.getLayers().get("Spawn");
        renderer = new OrthogonalTiledMapRenderer(board);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 15,15);

        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        board.dispose();
        renderer.dispose();
    }
}
