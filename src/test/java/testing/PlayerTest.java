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
    public void initialHealth(){
        assertEquals(9, player.getHealth());
    }

    @Test
    public void initialLives(){
        assertEquals(3, player.getLives());
    }

    @Test
    public void initialPosition(){
        assertEquals(startingPosition, player.getPosition());
    }

    @Test
    public void initialDir(){
        assertEquals(Direction.NORTH, player.getRotation());
    }

    @Test
    public void changeOfPlayerPosition(){
        player.setPosition(new Vector2(1, 0));
        assertNotEquals(player.getBackupPoint(), player.getPosition());
        assertEquals(new Vector2(1, 0), player.getPosition());
    }

    @Test
    public void playerTakesDamageAndLosesOneHealth(){
        player.updateHealth(-1);
        assertEquals(8, player.getHealth());
    }

    @Test
    public void playerTakesDamageAndLosesNineHealth(){
        player.updateHealth(-9);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void playerTakesMaxDamageAndLosesALife(){
        player.updateHealth(-10);
        assertEquals(2, player.getLives());
    }


    @Test
    public void checkDamagePlayerTakesFromHoleIsTen(){
        int damage = board.getTile(new Vector2(1,3)).getHealthChange();
        assertEquals(-10, damage);
    }

    @Test
    public void checkForCorrectRespawnPosition(){
        int damage = board.getTile(new Vector2(1, 3)).getHealthChange();
        assertEquals(-10, damage);
        player.updateHealth(damage);
        assertEquals(startingPosition, player.getPosition());
    }
}