package testing;

import com.badlogic.gdx.math.Vector2;

import roborally.board.Board;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

/**
 * Testing for correct initialisation of a player. This includes position, health etc.
 */
public class PlayerTest {
    private GameLogic gameLogic = mock(GameLogic.class);
    private Vector2 startingPosition= new Vector2(1,1);
    private Board board = new Board("boards/testBoard0.tmx");
    private Player player = new Player(0, startingPosition, gameLogic);

    @Test
    public void initialHealthTest(){
        assertEquals(9, player.getHealth());
    }

    @Test
    public void initialLivesTest(){
        assertEquals(3, player.getLives());
    }

    @Test
    public void initialPositionTest(){
        assertEquals(startingPosition, player.getPosition());
    }

    @Test
    public void initialDirTest(){
        assertEquals(Direction.NORTH, player.getRotation());
    }

    @Test
    public void changeOfPlayerPositionTest(){
        player.setPosition(new Vector2(1, 0));
        assertNotEquals(player.getBackupPoint(), player.getPosition());
        assertEquals(new Vector2(1, 0), player.getPosition());
    }

    @Test
    public void playerTakesDamageAndLosesOneHealthTest(){
        player.updateHealth(-1);
        assertEquals(8, player.getHealth());
    }

    @Test
    public void playerTakesDamageAndLosesNineHealthTest(){
        player.updateHealth(-9);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void playerTakesMaxDamageAndLosesALifeTest(){
        player.updateHealth(-10);
        assertEquals(2, player.getLives());
    }

    @Test
    public void checkDamagePlayerTakesFromHoleIsTenTest(){
        int damage = board.getTile(new Vector2(1,3)).getHealthChange();
        assertEquals(-10, damage);
    }

    @Test
    public void checkForCorrectRespawnPositionTest(){
        int damage = board.getTile(new Vector2(1, 3)).getHealthChange();
        assertEquals(-10, damage);
        player.updateHealth(damage);
        assertEquals(startingPosition, player.getPosition());
    }
}