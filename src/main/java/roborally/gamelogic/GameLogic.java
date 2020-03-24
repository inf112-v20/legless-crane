package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.screens.GameScreen;

import java.util.ArrayList;


public class GameLogic {
    public Player currentPlayer;
    private final ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private final GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;
    private int count = 0;

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
    public GameLogic(GameScreen gameScreen, int numPlayers){
        this.gameScreen = gameScreen;
        loadBoard();
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i),this));
        }
        currentPlayer = players.get(0); // so far only used by GameScreen
    }

    /**
     * TODO might move away from having a parameter here if we calculate instead of assign the next player
     *
     * This method should calculate which player is next in line for executing moves, temp method for now remove if
     * we do not implement multiple players before end of sprint
     *
     * Should check which player has the highest priority card (or just who's next in line) and update the currentPlayer
     * variable to that player.
     *
     * @param player player which should become the current one
     */
    private void updateCurrentPlayer(Player player) {
        // the current player should be set as the next according to priority on cards and other gamerules
        // see event driven game-loop.
        currentPlayer = player;
    }

    /**
     * Gets called by GameScreen every frame, checks the state of the game and updates it accordingly
     *
     * This is where the majority of the game logic / game loop will take place, might rely on it's own methods for
     * logic blocks to maintain readability in the code.
     *
     * Currently only checks if the player goes outside the board or into a hole.
     *
     * For testing purposes we have added a "timer" so for instance a cog does not continuously rotate the player,
     * should only do so every 10 frames.
     */
    public void updateGameState(){
        if (count<10) {
            count++;
            return;
        }

        for (Player player : players) {
            // variables to improve readability
            Vector2 playerPosition = player.getPosition();
            Tile playerTile = board.getTile(playerPosition);

            // loop through players and check for interactions between that player and elements on the board.
            if (!withinBoard(playerPosition)) {
                player.updateHealth(-10); // negative int == taking damage
            }

            else if (playerTile.isHole()){
                player.updateHealth(-10);// negative int == taking damage
            }

            else if (playerTile.isWrench()) {
                player.updateHealth(1); // positive int == repairing
                player.setBackupPoint(playerPosition);
            }
            // if laser, do damage


        }
        count = 0; // reset timer if we have interacted with player
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

        if (withinBoard(nextPos) && validMove(player, dir)) {
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
        gameScreen.setPlayerPosition(player, player.getBackupPoint());
        player.setPosition(player.getBackupPoint());
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
    private void loadBoard(){
        board = new Board("src/main/assets/boards/Board1.tmx");
        boardWidth = board.getBoardWidth();
        boardHeight = board.getBoardHeight();
    }

    /**
     * Checks if the desired position is within the board
     * @param move the desired position
     * @return whether or not the desired position's x,y coordinates are within the board.
     */
    private boolean withinBoard(Vector2 move) {
        return ((move.x < boardWidth && move.x >= 0) && (move.y < boardHeight) && move.y >= 0);
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
        Tile currentTile = board.getTile(player.getPosition());
        Tile nextTile = board.getTile(getDirectionalPosition(player.getPosition(), direction));

        if (currentTile.canBlockMovement()) {
            for (Direction dir : currentTile.getBlockingDirections())
                if (dir == direction)
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
