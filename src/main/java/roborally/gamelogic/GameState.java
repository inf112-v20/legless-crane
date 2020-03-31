package roborally.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public enum GameState {
    DEAL_CARDS, REVEAL_CARDS, MOVEPLAYER, MOVEBOARD, FIRE_LASERS, RESOLVE_INTERACTIONS, CLEANUP;

    public GameState next() {
        ArrayList<GameState> states = new ArrayList<>();
        Collections.addAll(states, GameState.values());

        int index = states.indexOf(this);

        if (index == states.size()) {
            return states.get(0);
        } else {
            return states.get(index+1);
        }
    }
}
