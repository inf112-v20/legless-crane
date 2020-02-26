package roborally.board;

import java.util.ArrayList;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;


    public Direction opposite(Direction dir) {
        switch (dir) {
            case EAST: return WEST;
            case SOUTH: return NORTH;
            case WEST: return EAST;
            case NORTH: return SOUTH;
            default: return null;
        }
    }

    public Direction rotate(int rotation, Direction currentDirection) {
        // allows us to accept a negative or positive int for how much and how little it should rotate
        // negative value is counter-clockwise, positive is clockwise
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);
        int index = directions.indexOf(currentDirection);
        return directions.get((rotation + index) % directions.size());

    }
}
