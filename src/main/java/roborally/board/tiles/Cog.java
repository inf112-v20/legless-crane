package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Cog implements Mover {
    private Position pos;
    private Direction dir;

    public Cog(Position pos, Direction dir) {
        this.pos = pos;
        this.dir = dir;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean canRotate() {
        return true;
    }

    @Override
    public int moveSpeed() {
        return 0; //TODO use this and moveDir in case there are cogs that move 180 degrees instead of 90. or remove?
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
