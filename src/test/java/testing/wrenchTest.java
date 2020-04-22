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

public class wrenchTest {
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
    public void originalBackUpPointTest(){
        assertEquals(new Vector2(1,1), player.getBackupPoint());
    }

    @Test
    public void newBackUpPointAtWrenchTest(){
        player.setPosition(new Vector2(3,0));
        assertEquals(true, board.getTile(new Vector2(3,0)).isWrench());
        player.setPosition(new Vector2(3,1));
        gameLogic.updateGameState();
        assertEquals(new Vector2(3,0), player.getBackupPoint());
    }

}
