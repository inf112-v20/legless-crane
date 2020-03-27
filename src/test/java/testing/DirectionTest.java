package testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import roborally.board.Direction;

/**
 * Simple tests of the enum Directions
 * making sure rotation gives correct new direction
 */
public class DirectionTest {

    @Test
    public void turnClockwiseFromNorth(){
        Direction north = Direction.NORTH;
        Direction east = north.rotate(1);

        assertEquals(Direction.EAST, east);
    }

    @Test
    public void turnCounterClockwiseFromNorth(){
        Direction north = Direction.NORTH;
        Direction west = north.rotate(-1);

        assertEquals(Direction.WEST, west);
    }

    @Test
    public void uTurnClockwiseFromNorth(){
        Direction north = Direction.NORTH;
        Direction south = north.rotate(2);

        assertEquals(Direction.SOUTH, south);
    }

    @Test
    public void uTurnClockwiseFromWest(){
        Direction west = Direction.WEST;
        Direction east = west.rotate(2);

        assertEquals(Direction.EAST, east);
    }

    @Test
    public void uTurnClockwiseFromEast(){
        Direction east = Direction.EAST;
        Direction west = east.rotate(2);

        assertEquals(Direction.WEST, west);
    }

    @Test
    public void uTurnClockwiseFromSouth(){
        Direction south = Direction.SOUTH;
        Direction north = south.rotate(2);

        assertEquals(Direction.NORTH, north);
    }
}

