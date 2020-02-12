package roborally.board.tiles;

import roborally.board.Position;

public class OpenTile implements ITile {

    private Position pos;

    OpenTile(Position pos) {
        this.pos = pos;
    }

    @Override
    public Position getPos() {
        return pos;
    }
}
