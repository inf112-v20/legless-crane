package roborally.screens;


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
import org.xml.sax.SAXException;
import roborally.Application;
import roborally.board.Board;
import roborally.board.Tile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class GameScreen implements Screen {
    // Originally from the Renderer-class:
    private TiledMap boardgfx;
    private Board board;
    private TiledMapTileLayer background;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell playerTile; // regular player texture.
    public Vector2 playerPosition;
    private int boardWidth;
    private int boardHeight;

    private final Application app;
    private Stage stage;
    private Skin skin;
    private TextButton buttonMenu;
    private TextButton moveUp;
    private TextButton moveDown;
    private TextButton moveLeft;
    private TextButton moveRight;

    public GameScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.WIDTH, Application.HEIGHT, app.camera));
    }

    // Calls the Actor.act(float) method on each actor in the stage.
    // Updates the actor based on time. Typically this is called each frame by Stage.act(float)
    private void update(float f){
        stage.act(f);
    }

    private void createCam() {
        // creating a new camera and 2D/Orthogonal renderer
        renderer = new OrthogonalTiledMapRenderer(boardgfx, 1 / 400f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);
        camera.position.set(camera.viewportWidth / 4f, camera.viewportHeight / 4f, 0); // centering camera

        camera.update();
        renderer.setView(camera);
    }



    private void loadBoard() throws IOException, SAXException, ParserConfigurationException {
        //TODO load in a Board here too for use in logic?
        board = new Board(new File("src/main/assets/boards/Board1.tmx"));

        // loading in the board from our tmx file, gets a given layer of that board with getLayers() use this for
        TmxMapLoader loader = new TmxMapLoader();
        boardgfx = loader.load("src/main/assets/boards/Board1.tmx");
        background = (TiledMapTileLayer) boardgfx.getLayers().get("background");

        boardWidth = background.getWidth();
        boardHeight = background.getHeight();
    }

    private void placePlayer() {
        // getting texture for player piece
        playerTile = new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png"))));

        // creating a layer to hold the player, setting the player's position and
        playerLayer = new TiledMapTileLayer(boardWidth, boardHeight, 300, 300);
        // starting position for the player
        playerPosition = new Vector2(6, 2);
        playerLayer.setCell((int)playerPosition.x,(int)playerPosition.y,playerTile);

    }

    @Override
    public void show() {
        // show() gets called every time the screen-object is being called i.e. switching to this screen
        Gdx.input.setInputProcessor(stage); // keep track of how actors interact/influence/are being influenced on stage
        stage.clear(); // reload site

        try {
            loadBoard();
        } catch (Exception e) {
            e.printStackTrace();
        } //TODO find a better way to handle this?

        createCam();
        placePlayer();

        // Additional UI on the stage: graphical representation of buttons
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
    }

    @Override
    public void render(float v) {
        // render is called when the screen should render itself, which happens all the time
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // render the game map
        renderer.render();
        // render the player
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
        // render buttons:
        update(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, false); // check this one as getting more stages?
    }

    @Override
    public void pause() {/*intentionally empty method*/}

    @Override
    public void resume() {/*intentionally empty method*/}

    @Override
    public void hide() {/*intentionally empty method*/}

    @Override
    public void dispose() {
        stage.dispose();
        boardgfx.dispose();
        renderer.dispose();
    }

    private void queueAssets(){ app.assets.load("ui/uiskin.atlas", TextureAtlas.class); }

    /*
    Can add current pos here when we get directional walls in,
    check if a wall in currentPos or nextPos blocks movement between the two tiles.
    */
    private boolean willCollide(int x, int y) { return board.get(x, y) == Tile.WALL; }

    private void updatePlayerPosition(Vector2 currentPos, float newX, float newY) {
        if (validMove(newX,newY) && !willCollide((int) newX, (int) newY)) {
            playerPosition = new Vector2(newX, newY);
            playerLayer.setCell((int) newX, (int) newY, playerTile);
            playerLayer.setCell((int)currentPos.x, (int)currentPos.y, null);
        }
    }

    // can add other logic here to check if there are walls etc blocking movement
    private boolean validMove(float x, float y) {
        return (x < boardWidth && x >= 0) && (y < boardHeight && y >= 0);
    }

    private void initButtons() {
        buttonMenu = new TextButton("Main menu", skin, "default");
        buttonMenu.setPosition(1400, 125);
        buttonMenu.setSize(300, 100);
        buttonMenu.getLabel().setFontScale(3.0f);
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        moveUp = new TextButton("moveUp", skin, "default");
        moveUp.setPosition(1000, 50);
        moveUp.setSize(300, 100);
        moveUp.getLabel().setFontScale(3.0f);
        moveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updatePlayerPosition(playerPosition,playerPosition.x, playerPosition.y+1);

            }
        });
        moveDown = new TextButton("Move down", skin, "default");
        moveDown.setPosition(600, 50);
        moveDown.setSize(300, 100);
        moveDown.getLabel().setFontScale(3.0f);
        moveDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updatePlayerPosition(playerPosition,playerPosition.x, playerPosition.y-1);
            }
        });

        moveLeft = new TextButton("Move left", skin, "default");
        moveLeft.setPosition(600, 200);
        moveLeft.setSize(300, 100);
        moveLeft.getLabel().setFontScale(3.0f);
        moveLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updatePlayerPosition(playerPosition, playerPosition.x-1, playerPosition.y);
            }
        });
        moveRight = new TextButton("Move right", skin, "default");
        moveRight.setPosition(1000, 200);
        moveRight.setSize(300, 100);
        moveRight.getLabel().setFontScale(3.0f);
        moveRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updatePlayerPosition(playerPosition,playerPosition.x+1, playerPosition.y);
            }
        });
        stage.addActor(buttonMenu);
        stage.addActor(moveUp);
        stage.addActor(moveDown);
        stage.addActor(moveLeft);
        stage.addActor(moveRight);
    }
}
