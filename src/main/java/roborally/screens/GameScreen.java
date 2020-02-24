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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.gui.Renderer;

// Class need to be "cleaned up"

public class GameScreen implements Screen {
    // Originally from the Renderer-class:
    private TiledMap board;
    private TiledMapTileLayer background;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell playerTile; // regular player texture.
    private Vector2 playerPosition;
    private int boardWidth;
    private int boardHeight;

    private final Renderer app;
    private Stage stage;
    private Skin skin;
    private TextButton buttonMenu;
    private TextButton moveUp;
    private TextButton moveDown;
    private TextButton moveLeft;
    private TextButton moveRight;

    public GameScreen(final Renderer app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Renderer.WIDTH, Renderer.HEIGHT, app.camera));
    }

    public void update(float f) { stage.act(f); }

    @Override
    public void show() {
        // show() gets called every time the screen-object is being called -> (put game logic here)
        Gdx.input.setInputProcessor(stage);         // keep track of how actors interact/influence/are being influenced on stage
        stage.clear(); // reload site

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

        // Additional UI on the stage: graphical representation of buttons
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();

        /*
        Originally from the Renderer-class:
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
        Gdx.input.setInputProcessor(this);  // Denne skal vekk, gjelder keyboad-input */
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

        playerLayer = new TiledMapTileLayer(boardWidth, boardHeight, 300, 300);
        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y, playerTile);

        renderer.setView(camera); //?????????
        renderer.render(); ///???

        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();

        stage.act(v);                      // ??
        stage.draw();

        app.batch.begin();
        app.font.draw(app.batch, "GameScreen", 1000, 1000);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, true); // check this one as getting more stages?
/*
        // Originally from the Renderer-class:
        camera.viewportWidth = width;
        camera.viewportHeight = height;*/
        //camera.update();

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
        board.dispose();
        renderer.dispose();
    }

    private void queueAssets(){
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }

    private void initMap(){

        TmxMapLoader loader = new TmxMapLoader();
        board = loader.load("boards/Board1.tmx");

        background = (TiledMapTileLayer) board.getLayers().get("background");

        boardWidth = background.getWidth();
        boardHeight = background.getHeight();

        renderer = new OrthogonalTiledMapRenderer(board, 1/300f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth,boardHeight);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);

        camera.update();
        renderer.setView(camera);

        playerTile = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));


        playerPosition = new Vector2(4,8);
    }
    private void initButtons() {

        buttonMenu = new TextButton("Main menu", skin, "default");
        buttonMenu.setPosition(1200, 100);
        buttonMenu.setSize(300, 100);
        buttonMenu.getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        moveUp = new TextButton("moveUp", skin, "default");
        moveUp.setPosition(400, 350);
        moveUp.setSize(300, 100);
        moveUp.getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        moveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0, (int) playerPosition.x)))),
                        (Math.min(boardHeight - 1, (Math.max(0, (int) playerPosition.y + 1)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
            }
        });
        moveDown = new TextButton("Move down", skin, "default");
        moveDown.setPosition(400, 50);
        moveDown.setSize(300, 100);
        moveDown.getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        moveDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerPosition = new Vector2((Math.min(boardWidth, (Math.max(0, (int) playerPosition.x)))),
                        (Math.min(boardHeight - 1, (Math.max(0, (int) playerPosition.y - 1)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
            }
        });

        moveLeft = new TextButton("Move left", skin, "default");
        moveLeft .setPosition(200, 200);
        moveLeft .setSize(300, 100);
        moveLeft .getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        moveLeft .addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerPosition = new Vector2((Math.min(boardWidth - 1, Math.max(0, (int) playerPosition.x - 1))),
                        (Math.min(boardHeight, (Math.max(0, (int) playerPosition.y)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
            }
        });
        moveRight = new TextButton("Move right", skin, "default");
        moveRight.setPosition(600, 200);
        moveRight.setSize(300, 100);
        moveRight.getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        moveRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerPosition = new Vector2((Math.min(boardWidth - 1, (Math.max(0, (int) playerPosition.x + 1)))),
                        (Math.min(boardHeight, (Math.max(0, (int) playerPosition.y)))));
                playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerTile);
            }
        });

        stage.addActor(buttonMenu);
        stage.addActor(moveUp);
        stage.addActor(moveDown);
        stage.addActor(moveLeft);
        stage.addActor(moveRight);
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
