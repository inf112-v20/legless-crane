package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Wall implements StaticTile{
    private Direction[] sidesBlocked;
    private Position pos;

    public Wall(Direction[] sidesBlocked, Position pos) {
        this.pos = pos;
        this.sidesBlocked = sidesBlocked;
    }

    @Override
    public boolean blocksMovement() {
        //Todo doublecheck if there are any cases where a wall does not always block movement in at least one direction
        return true;
    }

    @Override
    public boolean killsPlayer() {
        return false;
    }

    @Override
    public boolean canRepair() {
        return false;
    }

    @Override
    public Direction[] directionsBlocked() {
        return sidesBlocked;
    }

    @Override
    public Position getPos() {
        return pos;
    }
}
