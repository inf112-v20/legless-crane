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
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;

    public GameLogic(GameScreen gameScreen, int numPlayers){
        this.gameScreen = gameScreen;

        try {
            loadBoard();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ISSUE LOADING BOARD FROM FILE");
        }
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i), this));
        }
        currentPlayer = players.get(0);
    }

    private void loadBoard(){
        board = new Board(new File("src/main/assets/boards/Board1.tmx"));
        boardWidth = board.getBoardWidth();
        boardHeight = board.getBoardHeight();
    }



    private void updatePlayerPosition(Player player, Direction dir) {
        Vector2 nextPos = getDirectionalPosition(player, dir);

        if (validMove(nextPos) && willNotCollide(player, dir)) {
            // important that we do not update the player's position first here, as it's used in gameScreen to remove
            // the player gfx from the previous tile.
            gameScreen.setPlayerPosition(currentPlayer, nextPos);
            player.setPosition(nextPos);
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
        updatePlayerPosition(player, player.getRotation().opposite());
    }

    public void forwardMovement(Player player) {
        updatePlayerPosition(player, player.getRotation());
    }


    // can add other logic here to check if there are walls etc blocking movement
    public boolean validMove(Vector2 move) {
        return (move.x < board.getBoardWidth() && move.x >= 0) && (move.y < board.getBoardHeight() && move.y >= 0);
    }

    public boolean willNotCollide(Player player, Direction moveDir) {
        Tile currentTile = board.get(player.getPosition());
        Tile nextTile = board.get(getDirectionalPosition(player, moveDir));


        //TODO Might be better to use List or ArrayList for blocking directions?
        // easier to use .contains(dir) or something

        //TODO this might be where the bug is for wall movement currently, check this logic
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
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
