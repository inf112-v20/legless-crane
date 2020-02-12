package roborally.board.tiles;

import roborally.board.Direction;

public interface Mover extends ITile {
    boolean canMove();
    boolean canRotate();

    int moveSpeed();

    Direction moveDirection();

}
