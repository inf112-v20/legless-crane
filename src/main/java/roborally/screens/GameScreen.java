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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;


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






    @Override
    public void show() {

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



    }

    @Override
    public void resize(int i, int i1) {

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
