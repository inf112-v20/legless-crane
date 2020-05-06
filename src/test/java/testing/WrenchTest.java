package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import roborally.board.Board;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

public class WrenchTest {
    private static Board board;
    private static Player player;
    private static GameLogic gameLogic;


    @BeforeAll
    static void setup() {
        board = new Board("boards/testBoard0.tmx");
        gameLogic = mock(GameLogic.class);
        player = new Player(0, new Vector2(1, 3), gameLogic);
    }

    @Test
    public void positionHasWrenchTest(){
        assertEquals(true, board.getTile(new Vector2(3,0)).isWrench());
    }

    @Test
    public void givesOneHealthIfNotMaxHealthTest(){
        player.updateHealth(-1);
        player.setPosition(new Vector2(3, 0));
        player.updateHealth(board.getTile(player.getPosition()).getHealthChange());
        assertEquals(9, player.getHealth());
    }

    @Test
    public void playerGetsNewBackUpPointAtWrenchTest(){
        player.setPosition(new Vector2(3, 0));
        player.setBackupPoint(player.getPosition());
        assertEquals(new Vector2(3,0), player.getBackupPoint());
    }
}
