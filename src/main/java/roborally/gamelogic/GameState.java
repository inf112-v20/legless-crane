package roborally.gamelogic;

public enum GameState {
    GIVECARDS, MOVEPLAYER,MOVEBOARD, DAMAGE;


    public GameState next() {
        //check which gamestate this is, switches to the next one.
        // starts new round if relevant
        return MOVEBOARD;
    }
}
