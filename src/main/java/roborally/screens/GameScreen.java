package roborally.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.Application;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.Phase;
import roborally.programcards.ProgramCard;

import java.util.ArrayList;

public class GameScreen implements Screen {
    // Originally from the Renderer-class:
    private TiledMap boardgfx;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private final ArrayList<TiledMapTileLayer.Cell> playerTiles = new ArrayList<>();

    private final Application app;
    private final Stage stage;
    private final GameLogic gameLogic;
    private ArrayList<Phase> phases;
    private DeckOfProgramCards deckOfProgramCards;
    private boolean phasesAreProgrammed;
    private int AVAILABLEPROGCARDS = 9;
    private int PHASES = 5;

    private Skin skin;
    private BitmapFont font = new BitmapFont();
    private Label.LabelStyle style = new Label.LabelStyle( font, Color.BLACK );

    private static final String FILE_PATH_0 = "src/main/assets/boards/board_template.tmx"; // empty board
    private static final String FILE_PATH_1 = "src/main/assets/boards/Risky_Exchange.tmx";
    private static final String FILE_PATH_2 = "src/main/assets/boards/Checkmate.tmx";
    //TODO Allow for player to choose between these from menu?

    public GameScreen(final Application app) {
        this.app = app;
        this.gameLogic = new GameLogic(this,1, FILE_PATH_1);
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
        camera.setToOrtho(false, gameLogic.boardWidth, gameLogic.boardHeight);
        camera.position.set(camera.viewportWidth / 4f, camera.viewportHeight / 4f, 0); // centering camera

        camera.update();
        renderer.setView(camera);
    }

    private void loadBoard() {
        // loading in the board from our tmx file, gets a given layer of that board with getLayers() use this for
        TmxMapLoader loader = new TmxMapLoader();
        boardgfx = loader.load(FILE_PATH_1);
    }

    public void placePlayers() {
        // getting texture for player piece
        playerTiles.add(new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png")))));

        // Will have multiple players, put these in an array?

        // creating a layer to hold the player, setting the player's position and
        playerLayer = new TiledMapTileLayer(gameLogic.boardWidth, gameLogic.boardHeight, 300, 300);
        // starting position for the player

        for (int i = 0; i < gameLogic.getPlayers().size(); i++) {
            playerLayer.setCell(
                    (int)gameLogic.getPlayers().get(i).getPosition().x,
                    (int)gameLogic.getPlayers().get(i).getPosition().y,
                    playerTiles.get(i));
        }
    }

    @Override
    public void show() {
        // show() gets called every time the screen-object is being called i.e. switching to this screen
        Gdx.input.setInputProcessor(stage); // keep track of how actors interact/influence/are being influenced on stage
        stage.clear(); // reload site

        loadBoard();
        createCam();
        placePlayers();

        // Additional UI on the stage: graphical representation of buttons
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initPlacementOfChosenProgramCards();
        initChooseProgCards();
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

        //TODO: better solution regarding showing an updated HUD?
        app.batch.begin();
        app.font.draw(app.batch, "Lives left: " + gameLogic.currentPlayer.getLives(),Application.WIDTH-450,Application.HEIGHT/40);
        app.font.draw(app.batch, "Health left: " + gameLogic.currentPlayer.getHealth(),Application.WIDTH-350,Application.HEIGHT/40);
        app.font.draw(app.batch, "Flags conquered: " + gameLogic.currentPlayer.numberOfFlags(),Application.WIDTH-200,Application.HEIGHT/40);
        app.batch.end();

        stage.draw();
        gameLogic.updateGameState();
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
        app.batch.dispose();
    }



    public void updatePlayerRotation(int playerIndex, Direction rotation) {
        //TODO can we iterate through enum as a list or something to get index of direction? Any other way to do this better
        switch(rotation) {
            case NORTH:
                playerTiles.get(playerIndex).setRotation(0);
                break;
            case EAST:
                playerTiles.get(playerIndex).setRotation(3);
                break;
            case SOUTH:
                playerTiles.get(playerIndex).setRotation(2);
                break;
            case WEST:
                playerTiles.get(playerIndex).setRotation(1);
                break;
        }
    }

    public void setPlayerPosition(Player player, Vector2 newPosition) {
        // Should only be called from GameLogic if the move is considered valid, updates rendering of player
        playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, null);
        playerLayer.setCell((int) newPosition.x, (int) newPosition.y, playerTiles.get(player.getPlayerNumber()-1));
    }

    public void initPlacementOfChosenProgramCards() {
        phases = new ArrayList<Phase>();
        Texture img = new Texture("cards/background.jpg");
        for (int i = 0; i < PHASES; i++) {
            Phase placement = new Phase();
            placement.setTexture(img);
            placement.setWidth(170);
            placement.setHeight(200);
            placement.setOriginCenter();
            placement.setPosition(Application.WIDTH / 3 + 250 * i, Application.HEIGHT / 10);
            placement.setRectangleBoundary();
            phases.add(placement);
            stage.addActor(placement);
        }
    }

    public void initChooseProgCards() {
        deckOfProgramCards = new DeckOfProgramCards();
        for (int i = 0; i < AVAILABLEPROGCARDS; i ++) {
            int index = (int) (Math.random() * deckOfProgramCards.getDeckSize());         // Choose randomly program cards
            final ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));
            String fileName = "cards/" + deckOfProgramCards.getProgramCardMovement(index) + ".jpg";
            card.setTexture(new Texture(fileName));
            card.setWidth(170);
            card.setHeight(200);
            card.setOriginCenter();
            card.setPosition(Application.WIDTH / 15, Application.HEIGHT / 20 + 120*i );
            card.setRectangleBoundary();

            card.addListener(new ClickListener() {    // "Program robot"
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (int i = 0; i < PHASES; i++) {
                        if (phases.get(i).getTopCard() == null) { // If program card is chosen, move to available phase
                            card.addAction(Actions.moveTo(phases.get(i).getX(), phases.get(i).getY(), 0.2f));
                            phases.get(i).addCard(card);
                            if (i==PHASES-1) {
                                phasesAreProgrammed = true;
                            } return;
                        }
                    }
                }
            });
            stage.addActor(card);
            card.setZIndex(5);                 // cards created later should render earlier (on bottom)
        }
    }

    private void initButtons() {
        TextButton menuButton = new TextButton("Main menu", skin, "default");
        menuButton.setPosition(Application.WIDTH / 20, Application.HEIGHT - 150);
        menuButton.setSize(250, 100);
        menuButton.getLabel().setFontScale(3.0f);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        TextButton goButton = new TextButton("Let's go!", skin, "default");
        goButton.setPosition(Application.WIDTH / 3 + 450, Application.HEIGHT / 50);
        goButton.setSize(250, 100);
        goButton.getLabel().setFontScale(3.0f);
        goButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {  // gå gjennom phase for phase, remove card
                if (phasesAreProgrammed) {
                    for (Phase phas : phases) {
                        if (!phas.getTopCard().equals(null)) {
                            String movement = phas.getTopCard().getMovement();
                            switch (movement) {
                                case "1":
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    break;
                                case "2":
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    break;
                                case "3":
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    gameLogic.forwardMovement(gameLogic.currentPlayer);
                                    break;
                                case "u":
                                    gameLogic.rotatePlayer(gameLogic.currentPlayer, 2);
                                    break;
                                case "back":
                                    gameLogic.backwardMovement(gameLogic.currentPlayer);
                                    break;
                                case "rotateleft":
                                    gameLogic.rotatePlayer(gameLogic.currentPlayer, -1);
                                    break;
                                case "rotateright":
                                    gameLogic.rotatePlayer(gameLogic.currentPlayer, +1);
                                    break;
                            }
                            phases.remove(phas);
                            if (phases.isEmpty()){                // When all movements are done
                                phasesAreProgrammed = false;
                            }
                            return;
                        }
                    }
                }
            }
        });
        stage.addActor(menuButton);
        stage.addActor(goButton);
    }



/*
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
                gameLogic.backwardMovement(gameLogic.currentPlayer);

            }
        });
        TextButton moveDown = new TextButton("Move Ahead", skin, "default");
        moveDown.setPosition(600, 50);
        moveDown.setSize(300, 100);
        moveDown.getLabel().setFontScale(3.0f);
        moveDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameLogic.forwardMovement(gameLogic.currentPlayer);
            }
        });

        TextButton rotateLeft = new TextButton("Rotate left", skin, "default");
        rotateLeft.setPosition(600, 200);
        rotateLeft.setSize(300, 100);
        rotateLeft.getLabel().setFontScale(3.0f);
        rotateLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	gameLogic.rotatePlayer(gameLogic.currentPlayer,-1);
            }
        });
        TextButton rotateRight = new TextButton("Rotate right", skin, "default");
        rotateRight.setPosition(1000, 200);
        rotateRight.setSize(300, 100);
        rotateRight.getLabel().setFontScale(3.0f);
        rotateRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameLogic.rotatePlayer(gameLogic.currentPlayer,+1);
            }
        });
        stage.addActor(buttonMenu);
        stage.addActor(moveUp);
        stage.addActor(moveDown);
        stage.addActor(rotateLeft);
        stage.addActor(rotateRight);
    }*/

}

