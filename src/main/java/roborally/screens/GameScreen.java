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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.Application;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;

import java.util.ArrayList;

// Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html

public class GameScreen implements Screen {
    private TiledMap boardgfx;
    private TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private final ArrayList<TiledMapTileLayer.Cell> playerTiles = new ArrayList<>();

    private final Application app;
    private final GameLogic gameLogic;
    private ArrayList<ProgramCard> placementOfPhases;
    private boolean phasesAreProgrammed;
    private final int numberOfPhases = 5;

    private final Stage stage;
    private Skin skin;

    private static final String FILE_PATH_0 = "boards/board_template.tmx"; // empty board
    private static final String FILE_PATH_1 = "boards/Risky_Exchange.tmx";
    private static final String FILE_PATH_2 = "boards/Checkmate.tmx";
    //TODO Allow for player to choose between these from menu?

    public GameScreen(final Application app) {
        this.app = app;
        this.gameLogic = new GameLogic(this,1, FILE_PATH_1);
        this.stage = new Stage(new FitViewport(Application.WIDTH, Application.HEIGHT, app.camera));
    }

    private void update(float f){
        stage.act(f);
    }

    /**
     * The methods which are added separates out UI from show(). Graphics represented visually to the lucky one playing the game.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        loadBoard();
        createCam();
        placePlayers();

        queueAssets();
        phases();
        deckOfProgramCards();
        buttons();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        renderer.getBatch().begin();
        renderer.renderTileLayer(playerLayer);
        renderer.getBatch().end();
        update(v);

        app.batch.begin();
        app.font.draw(app.batch, "Lives left: " + gameLogic.currentPlayer.getLives(),Application.WIDTH-450,Application.HEIGHT/40f);
        app.font.draw(app.batch, "Health left: " + gameLogic.currentPlayer.getHealth(),Application.WIDTH-350,Application.HEIGHT/40f);
        app.font.draw(app.batch, "Flags conquered: " + gameLogic.currentPlayer.numberOfFlags(),Application.WIDTH-200,Application.HEIGHT/40f);
        app.batch.end();

        stage.draw();
        gameLogic.updateGameState();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, false);
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

    public void playerWins() {
        app.setScreen(app.winScreen);
        // Pass which player wins to customize message on screeN?
    }

    /**
     * Loads the board from a ".tmx-file".
     * Gets a given layer of the board through getLayers().
     */
    private void loadBoard() {
        TmxMapLoader loader = new TmxMapLoader();
        boardgfx = loader.load(FILE_PATH_1);
    }

    /**
     * Camera settings regarding the map.
     */
    private void createCam() {
        renderer = new OrthogonalTiledMapRenderer(boardgfx, 1 / 400f);
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, gameLogic.boardWidth, gameLogic.boardHeight);
        camera.position.set(camera.viewportWidth / 4f, camera.viewportHeight / 4f, 0); // centering camera

        camera.update();
        renderer.setView(camera);
    }

    /**
     * Visual representation of a player placed on the map.
     * Loads texture (.png-file), creates layer to hold the player and sets the position.
     * Will change as transition to multiplayer-mode happens.
     */
    public void placePlayers() {
        playerTiles.add(new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Tower.png")))));

        playerLayer = new TiledMapTileLayer(gameLogic.boardWidth, gameLogic.boardHeight, 300, 300);

        for (int i = 0; i < gameLogic.getPlayers().size(); i++) {
            playerLayer.setCell(
                    (int)gameLogic.getPlayers().get(i).getPosition().x,
                    (int)gameLogic.getPlayers().get(i).getPosition().y,
                    playerTiles.get(i));
        }
    }

    /**
     * Visual representation of player rotation, gets updated by renderer.
     *
     * @param playerIndex the current player "which rotates"
     * @param rotation the direction the player shall have
     */
    public void updatePlayerRotation(int playerIndex, Direction rotation) {
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

    /**
     * Places player on a new position on the map (visually).
     * Should only be called from GameLogic if the move is considered valid.
     * Updates rendering of the current player.
     *
     * @param player current player
     * @param newPosition new position
     */
    public void setPlayerPosition(Player player, Vector2 newPosition) {
        playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, null);
        playerLayer.setCell((int) newPosition.x, (int) newPosition.y, playerTiles.get(player.getPlayerNumber()-1));
    }

    /**
     * Assets (visual representation of the current buttons).
     */
    private void queueAssets(){
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));
    }

    /**
     * Phases. Program cards (chosen as phases) will be stored as the indices in the array "placementOfPhases".
     */
    public void phases() {
        placementOfPhases = new ArrayList<>();
        Texture img = new Texture("cards/background.jpg");
        for (int i = 0; i < numberOfPhases; i++) {
            ProgramCard phase = new ProgramCard();
            phase.setTexture(img);
            phase.setWidth(170);
            phase.setHeight(200);
            phase.setOriginCenter();
            phase.setPosition(Application.WIDTH / 3f + 250 * i, Application.HEIGHT / 10f);
            phase.setRectangleBoundary();
            placementOfPhases.add(phase);
            stage.addActor(phase);
        }
    }
    /**
     * "Deck of program cards" which deals 9 randomly chosen program cards.
     * Reads each of the 9 movements according to the program cards.
     * ClickListener (a program card being chosen) adds the chosen program card to a phase.
     */
    public void deckOfProgramCards() {
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        for (int i = 0; i < 9; i ++) {
            int index = (int) (Math.random() * deckOfProgramCards.getDeckSize());         // Choose randomly program cards
            final ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));
          
            String fileName = "cards/" + deckOfProgramCards.getProgramCardMovement(index) + ".jpg";
            card.setTexture(new Texture(fileName));
            card.setWidth(170);
            card.setHeight(200);
            card.setOriginCenter();
            card.setPosition(Application.WIDTH / 15f, Application.HEIGHT / 20f + 120 * i );
            card.setRectangleBoundary();

            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (int i = 0; i < numberOfPhases; i++) {
                        if (placementOfPhases.get(i).getMovement().equals("default")) {
                            card.addAction(Actions.moveTo(placementOfPhases.get(i).getX(), placementOfPhases.get(i).getY(), 0.2f));
                            placementOfPhases.remove(i);   // "default card"
                            placementOfPhases.add(i, card); // "program card"

                            if (!placementOfPhases.get(numberOfPhases-1).getMovement().equals("default")) {
                                phasesAreProgrammed = true;
                            } return;
                        }
                    }
                }
            });
            stage.addActor(card);
            card.setZIndex(5);             // Bottom of cards get hidden (beneficial while overlap)
        }
    }

    /**
     * Buttons as click listeners.
     * The button "Main menu" transfers from GameScreen to MenuScreen.
     * The button "Let's go!" reads the current player's phases "click by click".
     */
    private void buttons() {
        TextButton menuButton = new TextButton("Main menu", skin, "default");
        menuButton.setPosition(Application.WIDTH / 20f, Application.HEIGHT - 150f);
        menuButton.setSize(250, 100);
        menuButton.getLabel().setFontScale(3.0f);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        TextButton goButton = new TextButton("Let's go!", skin, "default");
        goButton.setPosition(Application.WIDTH / 3f + 450, Application.HEIGHT / 50f);
        goButton.setSize(250, 100);
        goButton.getLabel().setFontScale(3.0f);
        goButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (phasesAreProgrammed) {
                    for (ProgramCard phase : placementOfPhases) {
                        if (phase != null) {
                            String movement = phase.getMovement();
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
                            placementOfPhases.remove(phase);
                            if (placementOfPhases.isEmpty()){      // When all movements are done
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
}

