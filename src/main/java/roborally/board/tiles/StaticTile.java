package roborally.board.tiles;

import roborally.board.Direction;

public interface StaticTile extends ITile {
    boolean blocksMovement();
    boolean killsPlayer();
    boolean canRepair();
    Direction[] directionsBlocked();
}
