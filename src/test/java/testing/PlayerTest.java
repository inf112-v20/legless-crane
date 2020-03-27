package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

/**
 * Testing for correct initialisation of a player. This includes position, backup point for respawn, health, lives etc.
 */
public class PlayerTest {
    GameLogic gamelogic;
    private Player player = new Player(1, new Vector2(1,3), null);
    private Board board;

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
        Assertions.assertEquals(new Vector2(1, 3), player.getPosition());
    }

    @Test
    public void initialDir(){
        Assertions.assertEquals(Direction.NORTH, player.getRotation());
    }
/**
    @Test
    public void initialPlayerXPos(){
        assertEquals(0 , playerPosition.x);
    }
    @Test
    public void initialPlayerYPos(){
        assertEquals(0 , playerPosition.y);
    }

    public TempTileObjectTests() {
    board = new Board("boards/testBoard0.tmx");
    }

 @Test
 public void boardCorrectlyReadsHoles() {
 assertEquals(true, board.getTile(new Vector2(1,3)).doesDamage());
 }
    */


}