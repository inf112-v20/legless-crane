package roborally.board.tiles;


import roborally.board.Position;

/**
 * getPos returns the X,Y coordinates of the Tile object. Not currently utilized.
 * TODO Investigate if this interface is entirely unnecessary
 *TODO Investigate whether or not we should change these tile objects to save just one Tile object on each x,y
 * coordinate. current plan is to save the board as [[Wall],[Belt],[Wall, Belt]] in case there are multiple tiles on
 * each coordinate.
 * Could move everything into one single Tile object and then set all attributes like canMove(),canBlock() etc at baord
 * creation.
 */
public interface ITile {
    Position getPos();
}


/*
public Tile getTile(int id, int x_pos, int y_pos)
{
    switch (id)
    {
        case 0:  return new GroundTile(x_pos, y_pos); break;
        case 1:  return new SpringTile(x_pos, y_pos); break;
        ...
    }
    return Tile.getEmptyTile(x_pos, y_pos);
}
 */
