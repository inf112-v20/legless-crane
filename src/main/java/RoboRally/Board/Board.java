package RoboRally.Board;
import java.util.ArrayList;

public class Board {
    private int boardSize;
    private ArrayList<Tile> grid = new ArrayList<>();
    // stores all elements on the board
    private ArrayList<Boolean> players = new ArrayList<>();
    // stores all locations of the players. Might be better with a different type than Boolean.
    private BoardCollection boards = new BoardCollection();

    public Board() {
        // TODO: Ask for input, or just automatically pick a board?
        // TODO: need to set the boardSize so we can use get(pos) and set(pos, state) and update the board after creation
        readBoard(boards.getBoard(0));



    }

    public void readBoard(int[] board) {
        // readBoard should fill the grid array with enums for each element that can go in the board
        // TODO: Must be updated and ideally refactored entirely to allow facing of elements i.e. North vs south wall
        for (int tile : board) {
            switch (tile) {
                case 0:
                    grid.add(Tile.OPEN);
                case 1:
                    grid.add(Tile.HOLE);
                case 2:
                    grid.add(Tile.WALL);
                case 3:
                    grid.add(Tile.RIGHTCOG);
                case 4:
                    grid.add(Tile.LEFTCOG);
            }
        }
    }

    public Tile get(Position pos) {
        return grid.get(pos.getX() + pos.getY()*boardSize);
    }

    public void set(Position pos, Tile state) {
        grid.set(pos.getX() + pos.getY()*boardSize, state);
    }
}
