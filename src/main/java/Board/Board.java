package Board;
import java.util.ArrayList;

public class Board {
    private int boardSize;
    private ArrayList<Tile> grid = new ArrayList<Tile>();
    //private ArrayList<Cell> playerGrid = new ArrayList<Cell>();

    /**
     * the Board is created and filled with Cell.WATER, essentially
     * ensuring all tiles are present and "empty".
     *
     * @param	size	the size of the board being created.
     */
    public Board(int size) {
        /*
        unnecessary size restraint?
        if(size > 26 || size <= 0)
            throw new IllegalArgumentException();
         */

        this.boardSize = size;
        /*
        simple loop to fill an empty board, make a different method to read from .txt file or w/e
        could also just use set() method.
         */
        for (int i = 0; i < boardSize*boardSize; ++i)
            grid.add(Tile.OPEN);
    }


    public int size() {
        return boardSize;
    }

    public Tile get(Position pos) {
        return grid.get(pos.getX() + pos.getY()*boardSize);
    }

    public void set(Position pos, Tile state) {
        grid.set(pos.getX() + pos.getY()*boardSize, state);
    }
}
