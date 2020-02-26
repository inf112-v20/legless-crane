package testing;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import roborally.board.Board;
import roborally.board.Tile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class tempTileObjectTests {

    private Board board;

    public tempTileObjectTests() throws IOException, SAXException, ParserConfigurationException {
        board = new Board(new File("src/test/testingResources/testBoard0.tmx"));
    }

    @Test
    public void boardCorrectlyReadsHoles() {
        assertTrue(board.get(1,3).canKillPlayer());
    }

    @Test
    public void boardCorrectlyReadsWrenches() {
        assertTrue(board.get(3,0).isCanRepair());
    }

    @Test
    public void boardCorrectlyReadsCogs() { assertTrue(board.get(2,4).isCanRotate()); }

    @Test
    public void boardCorrectlyReadsYellowBelts() {
        assertTrue(board.get(0,2).isCanMovePlayer());
        assertEquals(board.get(0,2).getMovementSpeed(),1);
    }

    @Test
    public void boardCorrectlyReadsBlueBelts() {
        assertTrue(board.get(4,4).isCanMovePlayer());
        assertEquals(board.get(4,4).getMovementSpeed(),2);
    }

    @Test
    public void boardCorrectlyReadsSpawnPoints() { assertTrue(board.get(1,0).isSpawner()); }

    @Test
    public void boardCorrectlyReadsFlags() {
        assertTrue(board.get(3,1).isFlag());
    }

    @Test
    public void boardCorrectlyReadsWalls() {
        assertTrue(board.get(1,4).canBlockMovement());
    }
}