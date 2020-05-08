package roborally.application;


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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;

import java.util.ArrayList;

// Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html

public class GameScreen implements Screen {
    private TiledMap boardgfx;
    public TiledMapTileLayer playerLayer;
    private OrthogonalTiledMapRenderer renderer;
    private final ArrayList<TiledMapTileLayer.Cell> playerTiles = new ArrayList<>();

    private final Application app;
    private final GameLogic gameLogic;
    private ArrayList<ProgramCard> placementOfPhases;
    private DeckOfProgramCards deckOfProgramCards;

    private final int numberOfPhases = 5;
    private int currentPlayer = 0;
    private float phaseX;
    private float phaseY;
    private int phaseNum = 0;
    private int index;

    private final Stage stage;
    private Skin skin;

    private static final String FILE_PATH_1 = "boards/Risky_Exchange.tmx";

    public GameScreen(final Application app) {
        this.app = app;
        this.gameLogic = new GameLogic(this,2, FILE_PATH_1);
        this.stage = new Stage(new FitViewport(Application.WIDTH, Application.HEIGHT, app.camera));
    }

    /**
     * Should return moves[phases][cards]
     * ( ( player1 card, player2 card, ... , ... , ...) ,
     * ( player1 card, player2 card, ... , ... , ...)
     * , ... , ... , ... )
     * moves chosen by all players, separated into phases
     */

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
        app.font.draw(app.batch, "Flags conquered: " + (gameLogic.currentPlayer.getNextFlag()-1),Application.WIDTH-200,Application.HEIGHT/40f);
        app.font.draw(app.batch, "GameState: " + gameLogic.getGameState(), Application.WIDTH-1100,Application.HEIGHT/40f);
        app.font.draw(app.batch, "Phase: " + (gameLogic.getPhase()+1), Application.WIDTH-1200,Application.HEIGHT/40f);
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
    }
    public void gameOver() {
        app.setScreen(app.loseScreen);
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
        playerTiles.add(new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Queen.png")))));
        playerTiles.add(new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/Pawn.png")))));

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
            default:
                System.out.println("Wrong case in updatePlayerRotation(" + playerIndex +"," + rotation.toString()+")");
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

    public void hidePlayer(Player player) {
        playerLayer.setCell((int) player.getPosition().x, (int) player.getPosition().y, null);
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
        for (int i = 0; i < numberOfPhases; i++) {
            Texture img = new Texture("cards/phase"+i+".png");
            ProgramCard phase = new ProgramCard();
            phase.setTexture(img);
            phase.setWidth(210);
            phase.setHeight(250);
            phase.setOriginCenter();
            phaseX = Application.WIDTH / 3.1f;
            phaseY = Application.HEIGHT / 10f;
            phase.setPosition(phaseX + 250 * i, phaseY);
            phase.setRectangleBoundary();
            placementOfPhases.add(phase);
            stage.addActor(phase);
        }
    }

    /**
     * "Deck of program cards" which deals 9 randomly chosen program cards.
     * Reads each of the 9 movements according to the program cards.
     * GameLogic deals with logic of adding/removing cards while programming the robot.
     */
    public void deckOfProgramCards() {
        deckOfProgramCards = new DeckOfProgramCards();
        for (int i = 0; i < gameLogic.getPlayers().get(currentPlayer).getHealth(); i++){
            index = gameLogic.getCardIndices().get((currentPlayer*9)+i);
            final ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));

            card.setDeckIndex(index);

            String fileName = "cards/" + deckOfProgramCards.getProgramCardMovement(index)+deckOfProgramCards.getProgramCardPriority(index) + ".png";
            card.setTexture(new Texture(fileName));
            card.setWidth(210);
            card.setHeight(250);
            card.setOriginCenter();
            card.setOrigin(Application.WIDTH / 15f + i%2*150, Application.HEIGHT / 20f + 120 * i);
            card.setPosition(card.getOriginX(), card.getOriginY());
            card.setRectangleBoundary();
            card.setName("tidy");

            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    // if the card should decide a phase
                    if (card.getY() == card.getOriginY()) {

                        phaseNum = gameLogic.fillPhase(currentPlayer, card.getDeckIndex());
                        if (phaseNum != -1) {
                            card.addAction(Actions.moveTo(phaseX + 250 * phaseNum, phaseY, 0.2f));
                            placementOfPhases.remove(phaseNum);
                            placementOfPhases.add(phaseNum, card);
                        }
                    }
                    // If the card is already in a phase
                    else {
                        card.addAction(Actions.moveTo(card.getOriginX(), card.getOriginY(), 0.2f));
                        phaseNum = placementOfPhases.indexOf(card);
                        placementOfPhases.remove(card);
                        placementOfPhases.add(phaseNum, new ProgramCard());
                        gameLogic.regretPhase(currentPlayer, phaseNum);
                    }
                }
            });
            stage.addActor(card);
            card.setZIndex(5);
        }
    }

    /**
     * Buttons as click listeners.
     * The button "Main menu" transfers from GameScreen to MenuScreen.
     * The button "Start" confirms the currentPlayer's moves
     */
    private void buttons() {
        TextButton menuButton = new TextButton("Main menu", skin, "default");
        menuButton.setPosition(Application.WIDTH / 12f, Application.HEIGHT - 150f);
        menuButton.setSize(250, 100);
        menuButton.getLabel().setFontScale(3.0f);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.menuScreen);
            }
        });
        TextButton goButton = new TextButton("Start", skin, "default");
        goButton.setPosition(Application.WIDTH / 3f + 450, Application.HEIGHT / 50f);
        goButton.setSize(250, 100);
        goButton.getLabel().setFontScale(3.0f);
        goButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (gameLogic.cardsAreChosen()) {
                    gameLogic.cardsChosen=true;
                    clearCards();
                }
            }
        });
        stage.addActor(menuButton);
        stage.addActor(goButton);
    }

    /**
     * Method to provide graphics regarding "fire lasers from players"
     * @param player The player getting shot by a laser
     */
    public void shootPlayer(Player player){
        Vector2 target = player.getPosition();
        playerLayer.setCell((int)target.x, (int)target.y, new Cell().setTile(new StaticTiledMapTile
                (new TextureRegion(new Texture("img/fire.png")))));
    }

    /**
     * When currentPlayer has chosen cards before a turn, cards get removed both logically and visually (and phaseNum reset)
     */
    public void clearCards(){
        // Logically tidying
        placementOfPhases.clear();
        for (int i = 0; i < 5; i++) {
            placementOfPhases.add(new ProgramCard());
        }

        for (Actor actor : stage.getActors()) {
            if (actor.getName()=="tidy") {
                actor.addAction(Actions.removeActor());
            }
        }
    }

    /**
     * Preparation in case the currentPlayer gets locked cards.
     * The cards are not clickable, just removable as clearCards() gets called.
     */
    public void prepareCards(){
        for (int i = 4; i >= gameLogic.getPlayers().get(currentPlayer).getHealth(); i--) {
            String movement = gameLogic.getChosenCards()[i][currentPlayer].getMovement();
            int priority = gameLogic.getChosenCards()[i][currentPlayer].getPriority();

            ProgramCard card = new ProgramCard(movement, priority);

            String fileName = "cards/" + movement + priority + ".png";
            card.setTexture(new Texture(fileName));
            card.setWidth(210);
            card.setHeight(250);
            card.setOriginCenter();
            card.setOrigin(Application.WIDTH / 15f, Application.HEIGHT / 20f + 120 * i);
            card.setPosition(card.getOriginX(), card.getOriginY());
            card.setRectangleBoundary();
            card.setName("tidy");
            card.addAction(Actions.moveTo(phaseX + 250 * i, phaseY, 0.2f));
            stage.addActor(card);
        }
    }
}

