package roborally.board;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Enum containing possible directions a player or other elements could be facing or operate in.
 * Since diagonal movement so far is not a factor, this should work very well for the time being.
 */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * When used, this method returns the opposite direction of the one it's used on.
     *
     * Direction.EAST.opposite() would give Direction.WEST etc.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        switch (this) {
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
            case NORTH: return SOUTH;
            default: return null;
        }
    }

    /**
     * rotate() allows us to get the direction after rotating clockwise or counter-clockwise the direction and distance
     * determined by the parameter.
     *
     * @param rotation a positive or negative integer which determines which direction and how much to rotate
     *                 negative value -> counter-clockwise, positive value -> clockwise
     * @return the direction post-rotation
     */
    public Direction rotate(int rotation) {
        // allows us to accept a negative or positive int for how much and how little it should rotate
        // negative value is counter-clockwise, positive is clockwise
        ArrayList<Direction> directions = new ArrayList<>();
        Collections.addAll(directions, Direction.values());
        int index = directions.indexOf(this);

        if ((rotation + index) % directions.size() < 0)
            return directions.get(((rotation + index) % directions.size()) + directions.size());
        else
            return directions.get((rotation + index) % directions.size());
    }
}
