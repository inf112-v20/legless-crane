package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import roborally.board.Board;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

public class HoleTest {
    private static Board board;
    private static Player player;
    private static GameLogic gameLogic;


    @BeforeAll
    public static void setUp(){
        board = new Board("boards/testBoard0.tmx");
        gameLogic = mock(GameLogic.class);
        player = new Player(0, new Vector2(1,3), gameLogic);
    }

    @Test
    public void checkHoleInPositionTest(){
        assertEquals(true, board.getTile(new Vector2(1,3)).isHole());
    }

    @Test
    public void playerTakesDamageTenAtHoleTest(){
        player.setPosition(new Vector2(1,3));
        assertEquals(-10, board.getTile(new Vector2(1,3)).getHealthChange());
    }

    @Test
    public void playerLoseOneLifeAtHoleTest(){
        assertEquals(3, player.getLives());
        player.setPosition(new Vector2(1,3));
        player.updateHealth(board.getTile(player.getPosition()).getHealthChange());
        assertEquals(2, player.getLives());
    }
}
