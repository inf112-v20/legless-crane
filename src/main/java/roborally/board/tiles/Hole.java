package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Hole implements StaticTile {
    private Position pos;

    public Hole(Position pos) {
        this.pos = pos;
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }

    @Override
    public boolean killsPlayer() {
        return true; //TODO Nice-To-Have: if statement here for cases where players can fly
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
        return null;
    }
}
