package RoboRally.Renderer;


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
    private TiledMapTileLayer background, belts, holes, walls, wrenches, spawn, flag, playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Cell playerTile; // regular player texture.
    private Cell playerDeath; // player texture when dead
    private Cell playerVictory; // player texture with victory
    private Vector2 playerPosition;



    @Override
    public void create() {
        /*
        Board and camera:
         */
        TmxMapLoader loader = new TmxMapLoader();
        board = loader.load("boards/Checkmate.tmx");

        background = (TiledMapTileLayer) board.getLayers().get("Background");
        belts = (TiledMapTileLayer) board.getLayers().get("Belts");
        holes = (TiledMapTileLayer) board.getLayers().get("Holes");
        walls = (TiledMapTileLayer) board.getLayers().get("Walls");
        wrenches = (TiledMapTileLayer) board.getLayers().get("Wrenches");
        spawn = (TiledMapTileLayer) board.getLayers().get("Spawnpoints");
        flag = (TiledMapTileLayer) board.getLayers().get("Victorypoints");

        renderer = new OrthogonalTiledMapRenderer(board, 1/300f);
        // TODO her må det finnes en bedre måte å besteme unitScale på, foreløpig er float tallet 1/pixler.

        camera = new OrthographicCamera();
        camera.setToOrtho(false, background.getWidth(),background.getHeight());
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        renderer.setView(camera);

        //different images for the player tile,
        playerTile = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));
        playerDeath = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));
        playerVictory = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));

        playerPosition = new Vector2(6,2);
        //input
        Gdx.input.setInputProcessor(this);
    }


    private boolean checkDeath(Vector2 position) {
        return holes.getCell((int) position.x, (int) position.y) != null;
    }

    private boolean checkVictory(Vector2 position) {
        return flag.getCell((int) position.x, (int) position.y) != null;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            // TODO Don't know why the map clears the player position, it shouldn't?
            /*
                setCell is limited by the board dimensions x(0-15) y(0-17), using max(0, x) and min(14,x) to ensure the position
                of the player does not move off the board.
             */
            case Input.Keys.LEFT:
                playerPosition = new Vector2((Math.min(13, Math.max(0,(int)playerPosition.x-1))),
                        (Math.min(17, (Math.max(0,(int)playerPosition.y)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;

            case Input.Keys.RIGHT:
                playerPosition = new Vector2((Math.min(13, (Math.max(0,(int)playerPosition.x+1)))),
                        (Math.min(17, (Math.max(0,(int)playerPosition.y)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
            case Input.Keys.DOWN:
                playerPosition = new Vector2((Math.min(13, (Math.max(0,(int)playerPosition.x)))),
                        (Math.min(17, (Math.max(0,(int)playerPosition.y-1)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
            case Input.Keys.UP:
                playerPosition = new Vector2((Math.min(13, (Math.max(0,(int)playerPosition.x)))),
                        (Math.min(17, (Math.max(0,(int)playerPosition.y+1)))));
                playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerTile);
                return true;
        }
        return false;
        /*
        if (checkVictory(playerPosition)) {
            playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerVictory);
        } else if (checkDeath(playerPosition)) {
            playerLayer.setCell((int)playerPosition.x, (int)playerPosition.y, playerDeath);
        } else {

        }
         */
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

        playerLayer = new TiledMapTileLayer(background.getWidth(), background.getHeight(), 300, 300);

        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y, playerTile);

        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
        // TODO Få orden på forskjellige layers og hvordan rendere de.

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose(){
        board.dispose();
        renderer.dispose();
    }
}
