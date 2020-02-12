package roborally.board.tiles;


import roborally.board.Direction;

public interface LaserTile extends ITile {
    Direction[] fieldOfFire();
}
