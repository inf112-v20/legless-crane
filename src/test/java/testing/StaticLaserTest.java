package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import roborally.board.Board;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class StaticLaserTest {
        private static Board board;
        private static Player player;
        private static GameLogic gameLogic;

        @BeforeAll
        public static void setup() {
            board = new Board("boards/Risky_Exchange.tmx");
            gameLogic = mock(GameLogic.class);
            player = new Player(0, new Vector2(0, 0), gameLogic);
        }

        @Test
        public void boardCorrectlyReadsStaticLaser() {
        assertEquals(true, board.getTile(new Vector2(10, 14)).isLaser());
        }

        @Test
        public void playerShouldLoseOneHealthPoint() {
            player.setPosition(new Vector2(10, 14));
            player.updateHealth(board.getTile(player.getPosition()).getHealthChange());
            assertEquals(8, player.getHealth());
        }
}
