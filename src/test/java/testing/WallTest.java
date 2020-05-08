package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * testing that player cannot enter a tile through wall
 * and that player cannot exit tile through wall
 */

public class WallTest {
    private static Board board;
    private static Player player;
    private static GameLogic gameLogic;

    @BeforeAll
    public static void setUp() {
        board = new Board("boards/testBoard0.tmx"); //Has EastWall (TileID = 23 in posY = 1 posY = 0
        gameLogic = mock(GameLogic.class);
        player = new Player(0, new Vector2(2, 0), gameLogic);
    }

    @Test
    public void blockMovementFromEnteringThroughWallTest(){
        assertEquals(false, gameLogic.notBlockedByWall(player, Direction.WEST));
    }

    @Test
    public void blockMovementFromExitingThroughWallTest(){
        player.setPosition(new Vector2(1, 0));
        assertEquals(false, gameLogic.notBlockedByWall(player, Direction.EAST));
    }
}
