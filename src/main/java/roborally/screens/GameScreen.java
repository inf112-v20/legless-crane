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
import roborally.board.Direction;
import roborally.board.Tile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class GameScreen implements Screen {
    // Originally from the Renderer-class:
    private TiledMap boardgfx;
    private Board board;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer.Cell playerTile; // regular player texture.
    public Vector2 playerPosition;
    private int boardWidth;
    private int boardHeight;
    private int rotation = 0;

    private final Application app;
    private final Stage stage;
    private Skin skin;

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
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);
        camera.position.set(camera.viewportWidth / 4f, camera.viewportHeight / 4f, 0); // centering camera

        camera.update();
        renderer.setView(camera);
    }



    private void loadBoard() throws IOException, SAXException, ParserConfigurationException {
        board = new Board(new File("src/main/assets/boards/Board1.tmx"));

        // loading in the board from our tmx file, gets a given layer of that board with getLayers() use this for
        TmxMapLoader loader = new TmxMapLoader();
        boardgfx = loader.load("src/main/assets/boards/Board1.tmx");
        TiledMapTileLayer background = (TiledMapTileLayer) boardgfx.getLayers().get("background");

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

    private Vector2 getDirectionalPosition(Vector2 currentPos, Direction moveDir) {
        switch(moveDir) {
            case NORTH:
                return new Vector2(currentPos.x,currentPos.y+1);
            case EAST:
                return new Vector2(currentPos.x+1,currentPos.y);
            case SOUTH:
                return new Vector2(currentPos.x,currentPos.y-1);
            case WEST:
                return new Vector2(currentPos.x-1,currentPos.y);
            default:
                System.out.println("Incorrect direction given in getDirectionalPosition(), returning currentPos");
                return currentPos;
        }
    }

    private boolean willNotCollide(Vector2 currentPos, Direction moveDir) {
        Tile currentTile = board.get(currentPos);
        Tile nextTile = board.get(getDirectionalPosition(currentPos,moveDir));


        //TODO Might be better to use List or ArrayList for blocking directions?
        // easier to use .contains(dir) or something
        if (currentTile.canBlockMovement()) {
            for (Direction dir : currentTile.getBlockingDirections())
                if (dir == moveDir)
                    return false;
        } else if (nextTile.canBlockMovement()) {
            for (Direction dir : nextTile.getBlockingDirections())
                if (dir == moveDir.opposite())
                    return false;
        }
        return true;
    }

    private void updatePlayerPosition(Vector2 currentPos, Direction dir) {
        Vector2 nextPos = getDirectionalPosition(currentPos, dir);

        if (validMove(nextPos.x, nextPos.y) && willNotCollide(currentPos, dir)) {
            playerPosition = nextPos;
            playerLayer.setCell((int) nextPos.x, (int) nextPos.y, playerTile);
            playerLayer.setCell((int)currentPos.x, (int)currentPos.y, null);
        }
    }

    // can add other logic here to check if there are walls etc blocking movement
    private boolean validMove(float x, float y) {
        return (x < boardWidth && x >= 0) && (y < boardHeight && y >= 0);
    }

    private void initButtons() {
        TextButton buttonMenu = new TextButton("Main menu", skin, "default");
        buttonMenu.setPosition(1400, 125);
        buttonMenu.setSize(300, 100);
        buttonMenu.getLabel().setFontScale(3.0f);
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        TextButton moveUp = new TextButton("Move Back", skin, "default");
        moveUp.setPosition(1000, 50);
        moveUp.setSize(300, 100);
        moveUp.getLabel().setFontScale(3.0f);
        moveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	backwardMovement();

            }
        });
        TextButton moveDown = new TextButton("Move Ahead", skin, "default");
        moveDown.setPosition(600, 50);
        moveDown.setSize(300, 100);
        moveDown.getLabel().setFontScale(3.0f);
        moveDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	forwardMovement();
            }
        });

        TextButton rotateLeft = new TextButton("Rotate left", skin, "default");
        rotateLeft.setPosition(600, 200);
        rotateLeft.setSize(300, 100);
        rotateLeft.getLabel().setFontScale(3.0f);
        rotateLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	rotatePlayerLeft();
            }
        });
        TextButton rotateRight = new TextButton("Rotate right", skin, "default");
        rotateRight.setPosition(1000, 200);
        rotateRight.setSize(300, 100);
        rotateRight.getLabel().setFontScale(3.0f);
        rotateRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	rotatePlayerRight();
            }
        });
        stage.addActor(buttonMenu);
        stage.addActor(moveUp);
        stage.addActor(moveDown);
        stage.addActor(rotateLeft);
        stage.addActor(rotateRight);
    }
    private void rotatePlayerLeft() {
    	if (rotation == 4) {
			rotation = 0;
		}	
    	playerTile.setRotation(rotation+= 1);
    	}
    	
    private void rotatePlayerRight() {
    	if (rotation == 0) {
			rotation = 4;
		}	
    	playerTile.setRotation(rotation-= 1);
    }
    private void backwardMovement() {
    	if (rotation == 0) {
    		updatePlayerPosition(playerPosition, Direction.SOUTH);
    	}
    	else if (rotation == 1) {
    		updatePlayerPosition(playerPosition,Direction.EAST);
    	}
    	else if (rotation == 2) {
    		updatePlayerPosition(playerPosition,Direction.NORTH);
    	}
    	else if (rotation == 3) {
    		updatePlayerPosition(playerPosition,Direction.WEST);
    	}
    }
    
    private void forwardMovement() {
    	if (rotation == 0) {
    		updatePlayerPosition(playerPosition,Direction.NORTH);
    	}
    	else if (rotation == 1) {
    		updatePlayerPosition(playerPosition,Direction.WEST);
    	}
    	else if (rotation == 2) {
    		updatePlayerPosition(playerPosition, Direction.SOUTH);
    	}
    	else if (rotation == 3) {
    		updatePlayerPosition(playerPosition,Direction.EAST);
    	}
    }
}

