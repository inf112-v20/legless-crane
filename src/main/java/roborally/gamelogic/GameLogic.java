package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.application.GameScreen;
import roborally.programcards.ProgramCard;

import java.util.ArrayList;
import java.util.Collections;


public class GameLogic {
    @SuppressWarnings("CanBeFinal")
    public Player currentPlayer;
    private final ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private final GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;
    private int count = 0;
    private int phase; // would it make more sense to start this at a different number and change the if statement?

    private GameState gameState;
    private ArrayList<Integer> queuedPlayers = new ArrayList<>();
    private ArrayList<Integer> cardIndices = new ArrayList<>();
    private ArrayList<Moves> queuedMoves = new ArrayList<>();
    private ArrayList<Integer> list;
    public boolean cardsChosen = false;

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Constructor which establishes a singleton connection between gamescreen and players, ensuring we only get one
     * instance of GameLogic and GameScreen.
     *
     * loads the board from file
     *
     * Adds players to the game and places them on their spawnpoint, also ensuring they only have one GameLogic to
     * think about.
     *
     * sets the first player as the currentPlayer (only relevant once multiple players are in and they take turns)
     *
     * @param gameScreen the screen which calls GameLogic, should be called using new GameLogic(this, numPlayers) so
     *                   we don't get more than one instance of GameLogic or GameScreen
     * @param numPlayers the number of players this game (how many Player objects to create in this constructor)
     */
    public GameLogic(GameScreen gameScreen, int numPlayers, String filePath){
        this.gameScreen = gameScreen;
        loadBoard(filePath);
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i),this));
        }

        currentPlayer = players.get(0); // so far only used by GameScreen
        gameState = GameState.DEAL_CARDS;

        setCardIndices();
    }

    /**
     * performs the move queued next with the player that should perform it. (Assuming the lists are in sync)
     */
    private void performMove() {
        switch (queuedMoves.remove(0)) {
            case FORWARD:
                forwardMovement(players.get(queuedPlayers.remove(0)));
                break;
            case BACK:
                backwardMovement(players.get(queuedPlayers.remove(0)));
                break;
            case LEFT:
                rotatePlayer(players.get(queuedPlayers.remove(0)), -1);
                break;
            case RIGHT:
                rotatePlayer(players.get(queuedPlayers.remove(0)), 1);
                break;

            default:
                throw new IllegalStateException("Unexpected value");
        }
    }

    /**
     * Gets the card chosen by all players for the given phase we're in.
     *
     * Converts the cards chosen into queued moves so they can be staggered, i.e. move 1 * 3 instead of move 3 tiles
     * instantly for a 3 movement card.
     *
     *TODO: Sort this list according to priority, make sure we sort both queuedPlayers and queuedMoves at the same time
     * in the same way.
     */
    private void queuePhase() {
        ProgramCard[] cardsThisPhase = gameScreen.getChosenCards()[phase];

        for (int i = 0; i <cardsThisPhase.length ;i++) {
            switch(cardsThisPhase[i].getMovement()) {
                case "move_1_":
                    queuedMoves.add(Moves.FORWARD);
                    queuedPlayers.add(i);
                    break;
                case "move_2_":
                    queuedMoves.add(Moves.FORWARD);
                    queuedMoves.add(Moves.FORWARD);
                    queuedPlayers.add(i);
                    queuedPlayers.add(i);
                    break;
                case "move_3_":
                    queuedMoves.add(Moves.FORWARD);
                    queuedMoves.add(Moves.FORWARD);
                    queuedMoves.add(Moves.FORWARD);
                    queuedPlayers.add(i);
                    queuedPlayers.add(i);
                    queuedPlayers.add(i);
                    break;
                case "u_turn_":
                    queuedMoves.add(Moves.RIGHT);
                    queuedMoves.add(Moves.RIGHT);
                    queuedPlayers.add(i);
                    queuedPlayers.add(i);
                    break;
                case "back_up_":
                    queuedMoves.add(Moves.BACK);
                    queuedPlayers.add(i);
                    break;
                case "rotate_left_":
                    queuedMoves.add(Moves.LEFT);
                    queuedPlayers.add(i);
                    break;
                case "rotate_right_":
                    queuedMoves.add(Moves.RIGHT);
                    queuedPlayers.add(i);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cardsThisPhase[i].getMovement());
            }
        }
    }

    /**
     * Gets called by GameScreen every frame, checks the state of the game and updates it accordingly
     *
     * the update of the gameState is staggered to only update every 10 frames (or every 10th call of updateGameState()
     * so the screen does not update instantly, making it difficult for the player to follow the progress of the game.
     *
     * updateGameState() loops through the enum GameState each phase performing every step in order.
     *
     * 1. DEAL_CARDS is visited once every round, we get the cards chosen by the player on GameScreen and advance the
     * GameState, if cards haven't been chosen yet, nothing happens. This is where the phases begin
     *
     * Every phase consists of these steps being done in order:
     * A. REVEAL_CARDS gets the movement for this phase (one card per player,
     * and queues the moves to be made using queuePhase([the_cards_this_phase])
     *
     * B. MOVE_PLAYER moves the players on the board, it goes through the sorted list of moves and players performing
     * them which was made with queuePhase earlier in REVEAL_CARDS. If the player moves off the board or pushes another
     * player off the board or into a pit, the player dies (checked with killIfOffBoard()). Each player gets to perform
     * it's move before the board starts to move or attack the player. we check if there are any more moves queued, if
     * not then we advance the gameState.
     *
     * C. MOVE_BOARD handles interactions between the player and elements on the board where the player is moved,
     * if on a belt the player is moved etc. Once all elements have been checked in order, the gameState advances.
     * TODO: Implement pushers (Not in MVP?)
     *
     * D. FIRE_LASERS updates the player's health if any are standing on a static laser.
     * TODO: expand this to include lasers fired by players
     * TODO: Add logic making sure the laser does not go through players or walls and damage things not in it's LoS
     *
     * E. RESOLVE_INTERACTIONS handles additional interactions between player and board such as registering
     * flags and updating backup/archive locations. In other words, flags and repair sites on the board.
     *
     * 2. CLEANUP happens after all 5 phases have been performed, any player on a wrench/repair tile will be repaired.
     * TODO: Expand this to give the player option cards if on a crossed wrench/hammer space. (Not in MVP?)
     * TODO: If any players have locked registers, those cards should not reenter the deck.
     *
     */
    public void updateGameState() {
        if (count<60) {
            count++;
            return;
        }
        count = 0;
        //TODO: Fine-tune this timer so the game flows well.

        switch (gameState) {
            case DEAL_CARDS:
                if(cardsChosen) gameState = gameState.advance();
                break;

            case REVEAL_CARDS:
                queuePhase();
                gameState = gameState.advance();
                break;

            case MOVE_PLAYER:
                performMove();
                killIfOffBoard();
                if (queuedMoves.size() == 0 || queuedPlayers.size() == 0) {
                    gameState = gameState.advance();
                    //System.out.println("all players have moved");
                }
                break;

            case MOVE_BOARD:
                for (ElementMoves moves : ElementMoves.values()) {
                    for(Player player : players) {
                        Vector2 playerPosition = player.getPosition();
                        Tile playerTile = board.getTile(playerPosition);
                        switch (moves) {
                            case EXPRESS_BELTS:
                                if (playerTile.isBelt() && playerTile.getMovementSpeed() == 2) beltsMovePlayer(player);
                                break;
                            case ALL_BELTS:
                                if (playerTile.isBelt()) beltsMovePlayer(player);
                                break;
                            case PUSHERS:
                                // pushers not yet implemented
                                break;
                            case COGS:
                                if (playerTile.isCog()) rotationCogs(player);
                                break;
                        }
                    }
                }
                killIfOffBoard();
                gameState = gameState.advance();

            case FIRE_LASERS:
                for (Player player : players) {
                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isLaser()) player.updateHealth(playerTile.getHealthChange());
                }

                gameState = gameState.advance();
                break;

            case RESOLVE_INTERACTIONS:
                for (Player player : players) {
                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isWrench()) player.setBackupPoint(playerPosition);

                    if (playerTile.isFlag()) {
                        registerFlag(currentPlayer);
                        player.setBackupPoint(playerPosition);
                    }
                }
                gameState = gameState.advance();
                break;

            case CLEANUP:
                if (phase < 4) {
                    gameState = gameState.advance().advance();
                    phase++;
                    break;
                }

                for (Player player : players) {
                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isWrench()) player.updateHealth(playerTile.getHealthChange());
                }

                for (Player player : players) {
                    if (player.getHealth() < 5) {
                        int lockedRegisters = player.getHealth();
                        for (int i = 0; i < lockedRegisters; i++) {
                            gameScreen.getChosenCards()[i][player.getPlayerNumber() - 1] = new ProgramCard();
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            gameScreen.getChosenCards()[i][player.getPlayerNumber() - 1] = new ProgramCard();
                        }
                    }
                }

                cardsChosen = false;
                cardIndices.clear();
                setCardIndices();

                gameScreen.prepareCards();
                gameScreen.deckOfProgramCards();

                gameState = gameState.advance();
                phase = 0;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + gameState);
        }
    }

    /**
     * During one turn; each player gets unique, random card-indices from the same "deck of cards"
     *
     * @return array of 84 numbers (shuffled)
     */
    public ArrayList<Integer> getCardIndices(){
        return cardIndices;
    }

    /**
     * Initialize array of 84 numbers. Shuffles it.
     */
    private void setCardIndices() {
        list = new ArrayList<>();
        for (int i = 0; i < 84; i++) {
            list.add(i);
            Collections.shuffle(list);
        }
        cardIndices.addAll(list);
    }

    /**
     * Method for moving a player in a specific direction
     *
     * Checks if this goes off the board or if it collides with a wall, these cases currently do not allow movement in
     * that direction.
     *
     * @param player the player who's position we should update
     * @param dir the direction which the player should move (used for checking against walls
     */
    private void movePlayerInDirection(Player player, Direction dir) {
        Vector2 nextPos = getDirectionalPosition(player.getPosition(), dir);
        if (validMove(player, dir)) {
            gameScreen.setPlayerPosition(player, nextPos);
            player.setPosition(nextPos);
        }
    }

    /**
     * Player uses this method when dying, moving the player back to their backup point on both gameScreen and in
     * gameLogic
     *
     * @param player the player which should respawn.
     */
    public void respawnPlayer(Player player) {
        if(player.getLives()<=0) {
            gameScreen.gameOver();
        } else {
            gameScreen.setPlayerPosition(player, player.getBackupPoint());
            player.setPosition(player.getBackupPoint());
        }
    }

    /**
     * Method which calculates the direction the player will be facing using the rotate method in the Direction enum
     *
     * Updates the player rotation in game logic and in the renderer.
     *
     * @param player the player which should be rotated.
     * @param direction an integer which either negative or positive indicates which direction the player should rotate
     *                  the value is how much it should be rotated.
     */
    public void rotatePlayer(Player player, int direction) {
        // direction can be +1 (clocwise) or -1 (counter clockwise) (or more) for rotation
        player.setRotation(player.getRotation().rotate(direction));
        gameScreen.updatePlayerRotation(player.getPlayerNumber()-1, player.getRotation());
    }
    /**
     * Method which moves the player according to the type of movement belt the player is currently positioned at.
     * Checks if current location is belt, then checks each direction and bend the player could move or rotate to, to get the correct movement.
     * Called  one once in updateGameState() for yellow belts, twice for blue.
     * @param player the player which should be moved and rotated.
     */
    private void beltsMovePlayer(Player player) {
        Tile currentTile = board.getTile(player.getPosition());
        int bend = currentTile.getBendDirection();
        Direction dir = currentTile.getMovementDirection();
        if (board.getTile(player.getPosition()).isBelt()) {
            if (dir == Direction.NORTH) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
            else if (dir == Direction.SOUTH) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
            else if (dir == Direction.WEST) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);

            }
            else if (dir == Direction.EAST) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
        }
    }

    /**
     * In this method we check if the player is currently on cog tile.
     * If true, the player will then start rotating according to the two types of rotation tiles.
     * @param player the player which should be rotated.
     */
    private void rotationCogs(Player player) {
        Tile currentTile = board.getTile(player.getPosition());
        if (board.getTile(player.getPosition()).isCog()) {
            int rotation = currentTile.getRotation();
            if (rotation == 1) {
                rotatePlayer(player, 1);
            } else if (rotation == -1) {
                rotatePlayer(player, -1);
            }
        }
    }

    /**
     * Checks what number is on the flag the player is standing on
     * checks if this corresponds to the next flag the player should visit
     * registers the flag in player.registerFlag() and checks if the player has all the flags
     *
     * if the player does, the win screen shows.
     *
     * @param player the player we're comparing values for.
     *
     */
    private void registerFlag(Player player) {
        if(player.registerFlag(board.getTile(player.getPosition()).getFlagNum())
                && player.getNextFlag()>board.getFlags())
            gameScreen.playerWins();
    }

    public void backwardMovement(Player player) {
        movePlayerInDirection(player, player.getRotation().opposite());
    }

    public void forwardMovement(Player player) {
        movePlayerInDirection(player, player.getRotation());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * loads the board and gets the dimensions of it for future use within GameLogic.
     *
     * Currently uses a hardcoded board, future implementations might accept a parameter which determines which of the
     * boards to load.
     */
    private void loadBoard(String filePath){
        board = new Board(filePath);
        boardWidth = board.getBoardWidth();
        boardHeight = board.getBoardHeight();
    }

    /**
     * Checks if any player has gone into a pit or off the board. Kills the player if that is the case.
     *
     * Should be called whenever players move or can be moved. so MOVEBOARD and MVOEPLAYER in updateGameState
     */
    private void killIfOffBoard() {
        for (Player player : players) {
            // Check if player is in hole or went of board as a result of moving or being pushed by players or board
            Vector2 position = player.getPosition();
            Tile playerTile = board.getTile(position);

            if (position.x >= boardWidth || position.y >= boardHeight || position.x <= 0 || position.y <= 0 || playerTile.isHole()) {
                System.out.println("player died");
                player.updateHealth(-10);
            }
        }
    }

    /**
     * Checks if the tile the player is currently on or the tile the player wishes to move in the direction of blocks
     * movement between the two.
     *
     * Checks each tile's blocking directions (if any), movement is blocked if one of these directions on the tile
     * the player moves from is the same as the direction the player wishes to move. Or if the tile the player wishes
     * to move to blocks movement from the direction the player is coming from.
     *
     * @param player the player which wishes to move
     * @param direction the direction the player wishes to move
     * @return whether or not the player can move in that direction without being blocked
     */
    private boolean validMove(Player player, Direction direction) {
        Vector2 nextPosition = getDirectionalPosition(player.getPosition(), direction);

        if (nextPosition.x > boardWidth || nextPosition.y > boardHeight || nextPosition.x < 0 || nextPosition.y < 0) {
            return false;
        }

        Tile currentTile = board.getTile(player.getPosition());
        Tile nextTile = board.getTile(nextPosition);

        if (currentTile.canBlockMovement()) {
            for (Direction dir : currentTile.getBlockingDirections())
                if (dir.equals(direction))
                    return false;
        } else if (nextTile.canBlockMovement()) {
            for (Direction dir : nextTile.getBlockingDirections())
                if (dir == direction.opposite())
                    return false;
        }
        return true;
    }

    /**
     * returns the Vector2 x,y coordinate of a position in the moveDir direction
     *
     * @param position the position we're starting from
     * @param moveDir the direction we're moving to
     * @return the position one step in that direction
     */
    private Vector2 getDirectionalPosition(Vector2 position, Direction moveDir) {
        switch(moveDir) {
            case NORTH:
                return new Vector2(position.x,position.y+1);
            case EAST:
                return new Vector2(position.x+1,position.y);
            case SOUTH:
                return new Vector2(position.x,position.y-1);
            case WEST:
                return new Vector2(position.x-1,position.y);
            default:
                System.out.println("Incorrect direction given in getDirectionalPosition(), returning currentPos");
                return position;
        }
    }
}
