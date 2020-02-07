package RoboRally.gui;


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


public class Renderer extends InputAdapter implements ApplicationListener  {
    private TiledMap board;
    private TiledMapTileLayer background, playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Cell playerTile; // regular player texture.
    private Vector2 playerPosition;
    private int boardWidth;
    private int boardHeight;

    @Override
    public void create() {
        // Loading in board from tmx file
        TmxMapLoader loader = new TmxMapLoader();
        board = loader.load("boards/Board1.tmx");

        background = (TiledMapTileLayer) board.getLayers().get("background");

        boardWidth = background.getWidth();
        boardHeight = background.getHeight();

        // creating a new camera and 2D/Orthogonal renderer
        renderer = new OrthogonalTiledMapRenderer(board, 1/300f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth,boardHeight);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);

        camera.update();
        renderer.setView(camera);

        // getting texture for player piece
        playerTile = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));


        playerPosition = new Vector2(6,2);

        // making this renderer the input processor.
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            // I think we're supposed to clear the player's previous position in addition to setting the new one here.
            // but it works so who knows.
            case Input.Keys.LEFT:
                playerPosition = new Vector2((Math.min(boardWidth-1, Math.max(0,(int)playerPosition.x-1))),
                        (Math.min(boardHeight, (Math.max(0,(int)playerPosition.y)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;

            case Input.Keys.RIGHT:
                playerPosition = new Vector2((Math.min(boardWidth-1, (Math.max(0,(int)playerPosition.x+1)))),
                        (Math.min(boardHeight, (Math.max(0,(int)playerPosition.y)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
            case Input.Keys.DOWN:
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0,(int)playerPosition.x)))),
                        (Math.min(boardHeight-1, (Math.max(0,(int)playerPosition.y-1)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
            case Input.Keys.UP:
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0,(int)playerPosition.x)))),
                        (Math.min(boardHeight-1, (Math.max(0,(int)playerPosition.y+1)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerLayer = new TiledMapTileLayer(boardWidth, boardHeight, 300, 300);

        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y, playerTile);

        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose(){
        board.dispose();
        renderer.dispose();
    }
}
