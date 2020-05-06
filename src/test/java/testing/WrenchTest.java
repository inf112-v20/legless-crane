package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import roborally.board.Board;

public class WrenchTest {
    private static Board board;

    @BeforeAll
    public static void setup() {
        board = new Board("boards/testBoard0.tmx");
    }

    @Test
    public void positionHasWrenchTest(){
        assertEquals(true, board.getTile(new Vector2(3,0)).isWrench());
    }
}
