package roborally.gamelogic;

public enum ElementMoves {
    EXPRESS_BELTS, ALL_BELTS,PUSHERS,COGS,DONE;

    public ElementMoves next() {
        return this;
    }
}
