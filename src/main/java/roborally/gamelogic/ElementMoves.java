package roborally.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public enum ElementMoves {
    EXPRESS_BELTS, ALL_BELTS, PUSHERS, COGS;

    public ElementMoves advance() {
        ArrayList<ElementMoves> states = new ArrayList<>();
        Collections.addAll(states, ElementMoves.values());

        int index = states.indexOf(this);

        if (index < states.size()-1) {
            return states.get(index+1);
        } else {
            return states.get(0);
        }
    }
}
