package testing;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TempTileObjectTests {

    private final Board board;

    public TempTileObjectTests() {
        board = new Board("boards/testBoard0.tmx");
    }

    @Test
    public void boardCorrectlyReadsHoles() {
        assertEquals(true, board.getTile(new Vector2(1, 3)).isHole());
    }

    @Test
    public void boardCorrectlyReadsWrenches() {
        assertEquals(true, board.getTile(new Vector2(3, 0)).isWrench());
    }

    @Test
    public void boardCorrectlyReadsCogs() {
        assertEquals(true, board.getTile(new Vector2(2, 4)).isCog()); }

    @Test
    public void boardCorrectlyReadsYellowBelts() {
        assertEquals(true, board.getTile(new Vector2(0, 2)).isBelt());
        assertEquals(board.getTile(new Vector2(0,2)).getMovementSpeed(),1);
    }

    @Test
    public void boardCorrectlyReadsBlueBelts() {
        assertEquals(true, board.getTile(new Vector2(4, 4)).isBelt());
        assertEquals(board.getTile(new Vector2(4,4)).getMovementSpeed(),2);
    }

    @Test
    public void boardCorrectlyReadsSpawnPoints() {
        assertEquals(true, board.getTile(new Vector2(1, 0)).isSpawner()); }

    @Test
    public void boardCorrectlyReadsFlags() {
        assertEquals(true, board.getTile(new Vector2(3, 1)).isFlag());
    }

    @Test
    public void boardCorrectlyReadsWalls() {
        assertEquals(true, board.getTile(new Vector2(1, 4)).canBlockMovement());
    }
}