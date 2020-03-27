package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;
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
        Assertions.assertEquals(10, player.getHealth());
    }

    @Test
    public void initialLives(){
        Assertions.assertEquals(3, player.getLives());
    }

    @Test
    public void initialPosition(){
        Assertions.assertEquals(startingPosition, player.getPosition());
    }

    @Test
    public void initialDir(){
        Assertions.assertEquals(Direction.NORTH, player.getRotation());
    }

    @Test
    public void changeOfPlayerPosition(){
        player.setPosition(new Vector2(1, 0));
        Assertions.assertNotEquals(player.getBackupPoint(), player.getPosition());
        Assertions.assertEquals(new Vector2(1, 0), player.getPosition());
    }

    @Test
    public void playerTakesDamageAndLosesOneHealth(){
        player.updateHealth(-1);
        Assertions.assertEquals(9, player.getHealth());
    }

    @Test
    public void playerTakesDamageAndLosesNineHealth(){
        player.updateHealth(-9);
        Assertions.assertEquals(1, player.getHealth());
    }

    @Test
    public void playerTakesDamageAndLosesALife(){
        player.updateHealth(-10);
        Assertions.assertEquals(2, player.getLives());
    }

    @Test
    public void checkDamagePlayerTakesFromHoleIsTen(){
        int damage = board.getTile(new Vector2(1,3)).getHealthChange();
        Assertions.assertEquals(-10, damage);
    }

    @Test
    public void checkForCorrectRespawnPosition(){
        int damage = board.getTile(new Vector2(1, 3)).getHealthChange();
        Assertions.assertEquals(-10, damage);
        player.updateHealth(damage);
        Assertions.assertEquals(startingPosition, player.getPosition());
    }
}