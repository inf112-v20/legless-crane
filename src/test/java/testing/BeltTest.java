package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class BeltTest {
    private static Board board;
    private static Player player;
    private static GameLogic gameLogic;

    @BeforeAll
    public static void setup() {
        board = new Board("boards/testBoard0.tmx");
        gameLogic = mock(GameLogic.class);
        player = new Player(0, new Vector2(1, 1), gameLogic);
    }

    /**
     * simple test to check that belt registers as the correct tile type
     */
    @Test
    public void checkBeltInPositionTest(){
        assertEquals(true, board.getTile(new Vector2(0,2)).isBelt());
    }

}
