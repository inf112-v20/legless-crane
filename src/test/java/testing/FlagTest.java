package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FlagTest {
    private static Board board = new Board("boards/Risky_Exchange.tmx");
    private static GameLogic gameLogic= mock(GameLogic.class);
    private static Player player;


    @BeforeEach
    public void setup() {
        player = new Player(0, new Vector2(10, 9), gameLogic);
    }

    @Test
    public void flagShouldBecomeBackup(){
        player.setBackupPoint(player.getPosition());
        assertEquals(new Vector2(10,9), player.getBackupPoint());
    }

    @Test
    public void flagShouldUpdateHealth(){
        player.updateHealth(-1);
        player.updateHealth(board.getTile(player.getPosition()).getHealthChange());
        assertEquals(9, player.getHealth());
    }

    @Test
    public void flagShouldNotGetRegistered(){
        player.registerFlag(board.getTile(player.getPosition()).getFlagNum());
        assertEquals(1, player.getNextFlag());
    }

    @Test
    public void flagShouldGetRegistered(){
        player.setPosition(new Vector2(8, 15));
        player.registerFlag(board.getTile(player.getPosition()).getFlagNum());
        assertEquals(2, player.getNextFlag());
    }
}