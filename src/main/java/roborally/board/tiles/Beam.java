package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Beam implements LaserTile {
    //TODO is it even necessary to have a beam object? currently it behaves exactly as the Laser object.
    private Direction[] directionOfFire;

    public Beam(Direction[] directionOfFire){
        this.directionOfFire = directionOfFire;
    }

    @Override
    public Direction[] fieldOfFire() {
        return directionOfFire;
    }

    @Override
    public Position getPos() {
        return null;
    }
}
