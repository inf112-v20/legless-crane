package Testing;

import RoboRally.Board.Board;
import RoboRally.Board.BoardAndLayers;
import RoboRally.Board.Position;
import RoboRally.Board.Tile;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleBoardReadingTests {
    int[][] testBoard = new int[][]{{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,6,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,7,0
    },{
            0,0,54,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            49,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,21,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,129,0,0,0
    },{
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,71,0,
            0,0,0,0,0
    },{
            0,23,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    }};

    Board board = new Board(5, 25, new BoardAndLayers(testBoard));

    // Beams and lasers will be tested eventually

    @Test
    public void openExists() { assertEquals(Tile.OPEN, board.get(new Position(0,0))); }

    @Test
    public void holeDoesNotExist() { assertEquals(Tile.HOLE, board.get(new Position(1,1))); }

    @Test
    public void wrenchExists() { assertEquals(Tile.WRENCH, board.get(new Position(3,4))); }

    @Test
    public void cogExists() { assertEquals(Tile.COG, board.get(new Position(2,0))); }

    @Test
    public void beltYellowExists() { assertEquals(Tile.BELT_YELLOW, board.get(new Position(0,2))); }

    @Test
    public void beltBlueExists() { assertEquals(Tile.BELT_BLUE, board.get(new Position(4,0))); }

    @Test
    public void spawnpointExists() { assertEquals(Tile.SPAWNPOINT, board.get(new Position(1,4))); }

    @Test
    public void flagExists() { assertEquals(Tile.FLAG, board.get(new Position(3,3))); }

    @Test
    public void wallExists() { assertEquals(Tile.WALL, board.get(new Position(1,0))); }

}