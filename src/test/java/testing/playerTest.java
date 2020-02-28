package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//venter med flere tester til vi har Player object
public class playerTest {
    private Vector2 playerPosition = new Vector2(0,0);

    @Test
    public void initialPlayerXPos(){
        assertEquals(0 , playerPosition.x);
    }
    @Test
    public void initialPlayerYPos(){
        assertEquals(0 , playerPosition.y);
    }
}