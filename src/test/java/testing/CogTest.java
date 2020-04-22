package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import roborally.board.Board;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

/**
 * Testing correct movement for cogs
 */

public class CogTest {
    private static Board board;
    private static Player player;
    private static GameLogic gameLogic;

    @BeforeAll
    static void setup() {
        board = new Board("boards/testBoard0.tmx");
        gameLogic = mock(GameLogic.class);
        player = new Player(0, new Vector2(1, 1), gameLogic);
    }

    @Test
    public void checkCogInPositionTest(){
        assertEquals(true, board.getTile(new Vector2(2,4)).isCog());
    }

    @Test
    public void cog54RotateClockwiseTest(){
        assertEquals(Direction.NORTH, player.getRotation());
        player.setPosition(new Vector2(2,4));
        player.setRotation(player.getRotation().rotate(board.getTile(new Vector2(2,4)).getRotation()));
        assertEquals(Direction.EAST, player.getRotation());
    }

    @Test
    public void cog53RotateCounterClockwiseTest(){
        assertEquals(Direction.EAST, player.getRotation());
        player.setPosition(new Vector2(4,4));
        player.setRotation(player.getRotation().rotate(board.getTile(new Vector2(4,4)).getRotation()));
        assertEquals(Direction.NORTH, player.getRotation());
    }
}