package RoboRally.Renderer;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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


public class Renderer implements ApplicationListener {
    private TiledMap board;
    private TiledMapTileLayer background, belts, holes, walls, spawn, flag, playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Cell playerTile; // regular player texture.
    private Cell playerDeath; // player texture when dead
    private Cell playerVictory; // player texture with victory
    private Vector2 playerPosition;



    @Override
    public void create() {
        TmxMapLoader loader = new TmxMapLoader();
        board = loader.load("boards/board0.tmx");

        background = (TiledMapTileLayer) board.getLayers().get("Background");
        belts = (TiledMapTileLayer) board.getLayers().get("Belts");
        holes = (TiledMapTileLayer) board.getLayers().get("Holes");
        walls = (TiledMapTileLayer) board.getLayers().get("Walls");
        spawn = (TiledMapTileLayer) board.getLayers().get("Spawn");
        flag = (TiledMapTileLayer) board.getLayers().get("Flag");

        renderer = new OrthogonalTiledMapRenderer(board, 1/64f);
        // TODO her må det finnes en bedre måte å besteme unitScale på, foreløpig er float tallet 1/pixler.

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 15,15);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        renderer.setView(camera);

        StaticTiledMapTile tile = new StaticTiledMapTile(
                new TextureRegion(new Texture("img/Piece64x64.png")));
        // TODO Dette må kunne gjøres bedre vel.

        playerTile = new Cell().setTile(tile);
        playerPosition = new Vector2(1,1);




        //playerTile.setTile(board.getTileSets("img/Piece64x64.png"));
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



        playerLayer = new TiledMapTileLayer(background.getWidth(), background.getHeight(), 64, 64);

        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y, playerTile);


        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
        // TODO Få orden på forskjellige layers og hvordan rendere de.
        // TODO få bevegelse av brikke på plass
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
