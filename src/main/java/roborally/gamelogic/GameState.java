package roborally.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public enum GameState {
    DEAL_CARDS, REVEAL_CARDS, MOVE_PLAYER, MOVE_BOARD, FIRE_LASERS, FIRE_PLAYER_LASER, RESOLVE_INTERACTIONS, CLEANUP;

    public GameState advance() {
        ArrayList<GameState> states = new ArrayList<>();
        Collections.addAll(states, GameState.values());

        int index = states.indexOf(this);

        if (index < states.size()-1) {
            return states.get(index+1);
        } else {
            return states.get(0);
        }
    }
}
