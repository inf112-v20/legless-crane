package roborally.board.tiles;

import roborally.board.Direction;
import roborally.board.Position;

public class Laser implements LaserTile {
    private Direction[] directionOfFire;

    public Laser(Direction[] directionOfFire){
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
