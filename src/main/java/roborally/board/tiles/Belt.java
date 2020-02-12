package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

/**
 *TODO There's an exception not currently handled from the rulebook for rotating belts:
 * This rotation happens only if the robot is moved onto
 * the rotating space by another conveyor belt, not when
 * the robot moves onto the rotating space on its own or
 * as a result of being pushed. In those cases, the belt
 * doesn’t rotate the robot; it simply moves the robot
 * normally when the board elements move.
 */
public class Belt implements Mover{
    private int speed;
    private boolean canRotate;
    private Direction dir;
    private Position pos;


    public Belt (int speed, Direction dir, boolean canRotate, Position pos) {
        this.speed = speed;
        this.canRotate = canRotate; // if this belt is a rotating belt or not, should rotate in direction dir.
        this.dir = dir;
        this.pos = pos;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean canRotate() {
        return canRotate;
    }

    @Override
    public int moveSpeed() {
        return speed;
    }

    @Override
    public Direction moveDirection() {
        return dir;
    }

    @Override
    public Position getPos() {
        return pos;
    }
}
