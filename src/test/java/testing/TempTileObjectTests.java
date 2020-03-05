package testing;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TempTileObjectTests {

    private Board board;

    public TempTileObjectTests() throws IOException, SAXException, ParserConfigurationException {
        board = new Board(new File("src/test/testingResources/testBoard0.tmx"));
    }

    @Test
    public void boardCorrectlyReadsHoles() {
        assertEquals(true, board.get(new Vector2(1,3)).canKillPlayer());

    }

    @Test
    public void boardCorrectlyReadsWrenches() {
        assertEquals(true, board.get(new Vector2(3,0)).canRepair());
    }

    @Test
    public void boardCorrectlyReadsCogs() {
        assertEquals(true, board.get(new Vector2(2,4)).canRotate()); }

    @Test
    public void boardCorrectlyReadsYellowBelts() {
        assertEquals(true, board.get(new Vector2(0,2)).canMovePlayer());
        assertEquals(board.get(new Vector2(0,2)).getMovementSpeed(),1);
    }

    @Test
    public void boardCorrectlyReadsBlueBelts() {
        assertEquals(true, board.get(new Vector2(4,4)).canMovePlayer());
        assertEquals(board.get(new Vector2(4,4)).getMovementSpeed(),2);
    }

    @Test
    public void boardCorrectlyReadsSpawnPoints() {
        assertEquals(true, board.get(new Vector2(1,0)).isSpawner()); }

    @Test
    public void boardCorrectlyReadsFlags() {
        assertEquals(true, board.get(new Vector2(3,1)).isFlag());
    }

    @Test
    public void boardCorrectlyReadsWalls() {
        assertEquals(true, board.get(new Vector2(1,4)).canBlockMovement());
    }
}