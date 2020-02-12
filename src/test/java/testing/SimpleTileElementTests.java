package testing;

import roborally.board.Board;
import roborally.board.GameBoard;
import roborally.board.Position;
import roborally.board.Tile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


public class SimpleTileElementTests {
    private int[][] testBoard = new int[][]{{
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

    private Board board = new Board(5, 5, new GameBoard(testBoard));

    // Beams and lasers will be tested eventually

    @Test
    public void boardIsFilledWithOpenTiles() { assertEquals(Tile.OPEN, board.get(new Position(0,0))); }

    @Test
    public void boardCorrectlyReadsHoles() { assertEquals(Tile.HOLE, board.get(new Position(1,1))); }

    @Test
    public void boardCorrectlyReadsWrenches() { assertEquals(Tile.WRENCH, board.get(new Position(3,4))); }

    @Test
    public void boardCorrectlyReadsCogs() { assertEquals(Tile.COG, board.get(new Position(2,0))); }

    @Test
    public void boardCorrectlyReadsYellowBelts() { assertEquals(Tile.BELT_YELLOW, board.get(new Position(0,2))); }

    @Test
    public void boardCorrectlyReadsBlueBelts() { assertEquals(Tile.BELT_BLUE, board.get(new Position(4,0))); }

    @Test
    public void boardCorrectlyReadsSpawnPoints() { assertEquals(Tile.SPAWNPOINT, board.get(new Position(1,4))); }

    @Test
    public void boardCorrectlyReadsFlags() { assertEquals(Tile.FLAG, board.get(new Position(3,3))); }

    @Test
    public void boardCorrectlyReadsWalls() { assertEquals(Tile.WALL, board.get(new Position(1,0))); }

    @Test
    public void holeTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 1 && y == 1)
                    continue;
                else
                    assertNotSame(Tile.HOLE, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void wrenchTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 3 && y == 4)
                    continue;
                else
                    assertNotSame(Tile.WRENCH, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void cogTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 2 && y == 0)
                    continue;
                else
                    assertNotSame(Tile.COG, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void yellowBeltTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 0 && y == 2)
                    continue;
                else
                    assertNotSame(Tile.BELT_YELLOW, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void blueBeltTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 4 && y == 0)
                    continue;
                else
                    assertNotSame(Tile.BELT_BLUE, board.get(new Position(x, y)));
            }
        }
    }


    @Test
    public void spawnTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 1 && y == 4)
                    continue;
                else
                    assertNotSame(Tile.SPAWNPOINT, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void flagTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 3 && y == 3)
                    continue;
                else
                    assertNotSame(Tile.FLAG, board.get(new Position(x, y)));
            }
        }
    }

    @Test
    public void wallTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (x == 1 && y == 0)
                    continue;
                else
                    assertNotSame(Tile.WALL, board.get(new Position(x, y)));
            }
        }
    }

}