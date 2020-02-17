package testing;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import roborally.board.Board;
import roborally.board.Position;
import roborally.board.Tile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;


public class SimpleTileElementTests {

    private Board board;

    public SimpleTileElementTests() throws IOException, SAXException, ParserConfigurationException {
        board = new Board(5, 5, new File("src/test/testingResources/testBoard0.tmx"));
    }

    // Beams and lasers will not be tested in this simple test version

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
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 1 && y != 1)
                    assertNotSame(Tile.HOLE, board.get(new Position(x, y)));
    }

    @Test
    public void wrenchTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 3 && y != 4)
                    assertNotSame(Tile.WRENCH, board.get(new Position(x, y)));
    }

    @Test
    public void cogTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 2 && y != 0)
                    assertNotSame(Tile.COG, board.get(new Position(x, y)));
    }

    @Test
    public void yellowBeltTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 0 && y != 2)
                    assertNotSame(Tile.BELT_YELLOW, board.get(new Position(x, y)));
    }

    @Test
    public void blueBeltTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 4 && y != 0)
                    assertNotSame(Tile.BELT_BLUE, board.get(new Position(x, y)));
    }


    @Test
    public void spawnTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 1 && y != 4)
                    assertNotSame(Tile.SPAWNPOINT, board.get(new Position(x, y)));
    }

    @Test
    public void flagTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 3 && y != 3)
                    assertNotSame(Tile.FLAG, board.get(new Position(x, y)));
    }

    @Test
    public void wallTileNotInUnexpectedPosition() {
        for (int y = 0; y< board.getBoardHeight(); y++)
            for (int x = 0; x < board.getBoardWidth(); x++)
                if (x != 1 && y != 0)
                    assertNotSame(Tile.WALL, board.get(new Position(x, y)));
    }
}