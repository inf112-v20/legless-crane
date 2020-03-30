package roborally.gamelogic;

public enum GameState {
    DEAL_CARDS, MOVEPLAYER,MOVEBOARD,FIRE_LASERS,RESOLVE_INTERACTIONS, CLEANUP;


    public GameState next() {
        //check which gamestate this is, switches to the next one.
        // starts new round if relevant
        return MOVEBOARD;
    }
}
