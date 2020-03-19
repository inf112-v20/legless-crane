package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.screens.GameScreen;

import java.io.File;
import java.util.ArrayList;

public class GameLogic {
    public Player currentPlayer;
    // Part of event-driven gameloop, will allow us to update which player's movement we should execute.
    // might not be strictly necessary before programmingcards are implemented
    private final ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private final GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;
    private int count = 0;

    private void updateCurrentPlayer(Player player) {
        // the current player should be set as the next according to priority on cards and other gamerules
        // see event driven game-loop.
        currentPlayer = player;
    }

    public void updateGameState(){ // gets called in the renderer in GameScreen
        if (count<10) {
            count++;
            return;
        }
        for (Player player : players){  // register interaction on playboard
            if (board.get(player.getPosition()).canKillPlayer()){
                player.handleDamage(10);
            } if (player.getPosition().x<0 || player.getPosition().y<0 || player.getPosition().x >= boardWidth || player.getPosition().y >= boardHeight ){
                player.handleDamage(10);
            }
        }
        count = 0;
    }

    public GameLogic(GameScreen gameScreen, int numPlayers){
        this.gameScreen = gameScreen;

        try {
            loadBoard();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ISSUE LOADING BOARD FROM FILE");
        }
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i),this));
        }
        currentPlayer = players.get(0); // so far only used by GameScreen
    }

    private void loadBoard(){
        board = new Board(new File("src/main/assets/boards/Board1.tmx"));
        boardWidth = board.getBoardWidth();
        boardHeight = board.getBoardHeight();
    }

    public void updatePlayerPosition(Player player, Direction dir, Vector2 pos) {
        //Vector2 nextPos = getDirectionalPosition(player, dir);
        if (dir == null){
            gameScreen.setPlayerPosition(player, pos);
            player.setPosition(pos);
            // Skal helst kalles kun når spiller dør
        }

        if (validMove(pos) && willNotCollide(player, pos, dir)) {
            // important that we do not update the player's position first here, as it's used in gameScreen to remove
            // the player gfx from the previous tile.
            gameScreen.setPlayerPosition(player, pos);
            player.setPosition(pos);

        }
    }

    public Vector2 getDirectionalPosition(Player player, Direction moveDir) {
        switch(moveDir) {
            case NORTH:
                return new Vector2(player.getPosition().x,player.getPosition().y+1);
            case EAST:
                return new Vector2(player.getPosition().x+1,player.getPosition().y);
            case SOUTH:
                return new Vector2(player.getPosition().x,player.getPosition().y-1);
            case WEST:
                return new Vector2(player.getPosition().x-1,player.getPosition().y);
            default:
                System.out.println("Incorrect direction given in getDirectionalPosition(), returning currentPos");
                return player.getPosition();
        }
    }

    public void rotatePlayer(Player player, int direction) {
        // direction can be +1 (clocwise) or -1 (counter clockwise) (or more) for rotation
        player.setRotation(player.getRotation().rotate(direction,player.getRotation()));
        gameScreen.updatePlayerRotation(player.getPlayerNumber()-1, player.getRotation());
    }

    public void backwardMovement(Player player) {
        updatePlayerPosition(player, player.getRotation().opposite(), getDirectionalPosition(player, player.getRotation().opposite()));
    }

    public void forwardMovement(Player player) {
        updatePlayerPosition(player, player.getRotation(), getDirectionalPosition(player, player.getRotation()));
    }
    // Player player, Direction dir, Vector2 pos)
    // updatePlayerPosition(player, getDirectionalPosition(player, player.getRotation()), player.getrotation())

    // can add other logic here to check if there are walls etc blocking movement
    public boolean validMove(Vector2 move) {
       // return ((move.x < boardWidth && move.x >= 0) && (move.y < boardHeight) && move.y >= 0);
        return true;
    }

    public boolean willNotCollide(Player player, Vector2 pos, Direction direction) {
        Tile currentTile = board.get(player.getPosition());
        Tile nextTile = board.get(pos);


        //TODO Might be better to use List or ArrayList for blocking directions?
        // easier to use .contains(dir) or something

        //TODO this might be where the bug is for wall movement currently, check this logic
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
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
