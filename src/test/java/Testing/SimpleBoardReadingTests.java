package Testing;

import RoboRally.Board.Board;
import RoboRally.Board.BoardAndLayers;
import RoboRally.Board.Position;
import RoboRally.Board.Tile;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleBoardReadingTests {
    int[][] testBoard = new int[][]{{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,6,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    },{
            0,0,0,
            0,0,0,
            0,0,0
    }};

    Board board = new Board(3, 9, new BoardAndLayers(testBoard));

    //TODO add more tests,
    // clean up this test class a bit more too.


    @Test
    public void holesReadSuccessfully() { assertEquals(Tile.HOLE, board.get(new Position(1,1))); }

}