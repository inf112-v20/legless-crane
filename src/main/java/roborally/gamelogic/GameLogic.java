package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.application.GameScreen;

import java.util.ArrayList;


public class GameLogic {
    @SuppressWarnings("CanBeFinal")
    public Player currentPlayer;
    private final ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private final GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;
    private int count = 0;

    private GameState gameState;

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
    }

    private void performMove() {
        // perform the next move in the list of moves on the player in question.
        // a 3 move should be broken up into a 3 queued moves of 1 and should added to the list as such.

        // list of moves
        // list of players these moves belong to
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
    public void updateGameState() {
        // keep timer, but tune this so things don't happen too fast?
        // have a different timer for different things?
        // an extra delay with message to indicate a new phase has begun?
        if (count<10) {
            count++;
            return;
        }
        switch (gameState) {

            // Missing, announce intent to power down or continue running next turn, done in deal cards?

            case DEAL_CARDS:
                //TODO Deal cards to each player, for each damage token a robot has, deal one fewer Program card.
                // See "Locking registers" for anyone with 5 or more damage tokens
                // Check if all players have chosen the five cards they want to use, begin timer if one has yet to choose.
                // If only one player is programming registers on a given turn (because the other
                // robots are powered down or out of the game) begin timer right away.
                // Once all players have chosen their cards, insert them in the correct order in the cards/moveList
                // remember which cards corresponds to which Player.
                break;
            case MOVEPLAYER:
                //TODO move the player
                // Execute the moves using performMove()
                break;
            case MOVEBOARD:
                //TODO Order of board elements:
                // 1. Express conveyor belts move 1 space in the direction of the arrows.
                // 2. Express conveyor belts and normal conveyor belts move 1 space in the
                // direction of the arrows.
                // 3. Pushers push if active.
                // 4. Gears rotate 90° in the direction of the arrows.
                break;
            case FIRE_LASERS:
                //TODO Lasers fire
                // Lasers don't pass through robots or walls
                // robot lasers fire
                break;
            case RESOLVE_INTERACTIONS:
                //TODO Any robot that’s survived the mayhem to this point and is on a flag “touches” that flag. Starting
                // next turn, it can move on to the next flag, in order.
                // Any robot on a flag or repair site updates its archive location by putting its Archive marker
                // on that space. If the robot is destroyed before reaching another archive location, this is where it
                // will reenter the race.
                break;
            case CLEANUP:
                // Repairs & Upgrades
                //Robots on a single-wrench space discard 1 Damage token.
                //Robots on a crossed wrench/hammer space discard 1
                //Damage token AND draw one Option card. When you
                //draw an Option card, read it aloud to the other players
                //and put it in front of you, face up. (See “Using Options to
                //Prevent Damage” on p. 10 for more on Option cards.)

                //Discard all Program cards from registers that aren’t
                //locked.

                //Players whose robots were powered down
                //this turn announce whether their robots will
                //remain powered down on the next turn.
                //Each robot that was destroyed this turn
                //reenters play in the space containing its
                //Archive marker. The player chooses which
                //direction the robot faces.
                //Robots reentering the race receive 2
                //Damage tokens (plus any Damage tokens
                //taken while powered down). A player
                //may decide at this time to reenter the race
                //powered down for the next turn (to discard
                //the Damage tokens).
                //After you’re done with cleanup, begin the
                //next turn.

                //If two or more robots would reenter play
                //on the same space, they’re placed back on
                //the board in the order they were destroyed.
                //The first robot that was destroyed gets the archive
                //space, facing any direction that player chooses.
                //The player whose robot was destroyed next then
                //chooses an empty adjacent space (looking
                //orthogonally OR diagonally) and puts the robot on
                //that space. That robot can face any direction that
                //player chooses, except that there can’t be another
                //robot in its line of sight 3 spaces away or closer.
                //Ignore all board elements except for pits when
                //placing your robot in an adjacent space.
                //You can’t start a turn with your robot in a pit.
                //They suffer enough as it is.
                break;
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
                if (speed != 1) {
                    beltsMovePlayer(player);
                }
                beltsMovePlayer(player);
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
            //TODO When robots collide, one will push the other, check for other robots here?

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
