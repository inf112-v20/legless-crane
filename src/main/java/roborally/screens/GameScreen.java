package roborally.screens;

/*
Originally from the Renderer-class
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.gui.Renderer;


public class GameScreen implements Screen {
    /* Originally from the Renderer-class
    private TiledMap board;
    private TiledMapTileLayer background;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell playerTile; // regular player texture.
    private Vector2 playerPosition;
    private int boardWidth;
    private int boardHeight;
    */

    private final Renderer app;
    private Stage stage;

    public GameScreen(final Renderer app) {        // Constructor here is equal to Create() in Renderer
        this.app = app;
        // Creating main stage
        this.stage = new Stage(new FitViewport(Renderer.WIDTH, Renderer.HEIGHT, app.camera));
    }

    public void update(float delta) {
    }







    @Override
    public void show() {
        // show() gets called every time the screen-object is being called -> Put the game logic here
        Gdx.input.setInputProcessor(stage);              // keep track of how actors interact/influence/being

        stage.clear();
    }

    @Override
    public void render(float v) {
        /*
        Orignially from the Renderer-class:
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerLayer = new TiledMapTileLayer(boardWidth, boardHeight, 300, 300);

        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y, playerTile);

        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
         */

        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);
        stage.draw();    // KOR SKAL DENNE?

        app.batch.begin();  // getBatch?
        app.font.draw(app.batch, "GameScreen", 120, 120);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, false); // check this one as getting more stages

        /*
        Originally from the Renderer-class:
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
         */

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        /*
        Originally from the Renderer-class:
        board.dispose();
        renderer.dispose();
         */
        stage.dispose();
    }

    /*
    Originally from the Renderer-class
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            // I think we're supposed to clear the player's previous position in addition to setting the new one here.
            // but it works so who knows.
            case Input.Keys.LEFT:
                playerPosition = new Vector2((Math.min(boardWidth - 1, Math.max(0, (int) playerPosition.x - 1))),
                        (Math.min(boardHeight, (Math.max(0, (int) playerPosition.y)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
                return true;

            case Input.Keys.RIGHT:
                playerPosition = new Vector2((Math.min(boardWidth - 1, (Math.max(0, (int) playerPosition.x + 1)))),
                        (Math.min(boardHeight, (Math.max(0, (int) playerPosition.y)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
                return true;
            case Input.Keys.DOWN:
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0, (int) playerPosition.x)))),
                        (Math.min(boardHeight - 1, (Math.max(0, (int) playerPosition.y - 1)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
                return true;
            case Input.Keys.UP:
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0, (int) playerPosition.x)))),
                        (Math.min(boardHeight - 1, (Math.max(0, (int) playerPosition.y + 1)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
                return true;
            default:
                return false;
        }
    } */
}
