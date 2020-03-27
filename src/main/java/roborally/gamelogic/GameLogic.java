package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.application.GameScreen;

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
    public GameLogic(GameScreen gameScreen, int numPlayers, String filePath){
        this.gameScreen = gameScreen;
        loadBoard(filePath);
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i),this));
        }
        currentPlayer = players.get(0); // so far only used by GameScreen
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
            Vector2 playerPosition = player.getPosition();
            Tile playerTile = board.getTile(playerPosition);
            int speed = playerTile.getMovementSpeed();

            if (!onBoard(playerPosition))
                player.updateHealth(-10);

            else if (playerTile.doesDamage())
                player.updateHealth(playerTile.getHealthChange());

            else if (playerTile.isBelt()) {
                if (speed == 1) {
                    beltsMovePlayer(player);
                } else {
                    beltsMovePlayer(player);
                    beltsMovePlayer(player);
                }
            }
            else if (playerTile.isCog())
                rotationCogs(player);

            else if (playerTile.isWrench()) {
                player.updateHealth(playerTile.getHealthChange());
                player.setBackupPoint(playerPosition);

            } else if (playerTile.isFlag()) {
                registerFlag(currentPlayer);
            }
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
     * Checks if the desired position is within the board
     * @param move the desired position
     * @return whether or not the desired position's x,y coordinates are within the board.
     */
    private boolean onBoard(Vector2 move) {
        return (move.x < boardWidth-1 && move.x >= 1) && (move.y < boardHeight-1 && move.y >= 1);
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
