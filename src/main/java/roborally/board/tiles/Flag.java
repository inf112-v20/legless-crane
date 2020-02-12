package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Flag implements StaticTile {
    private Position pos;

    public Flag(Position pos) {
        this.pos = pos;
    }

    @Override
    public boolean blocksMovement() {
        return false;
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
        return new Direction[0];
    }

    @Override
    public Position getPos() {
        return pos;
    }
}
