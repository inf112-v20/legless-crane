package roborally.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public enum ElementMoves {
    EXPRESS_BELTS, ALL_BELTS, PUSHERS, COGS, DONE;

    public ElementMoves next() {
        ArrayList<ElementMoves> states = new ArrayList<>();
        Collections.addAll(states, ElementMoves.values());

        int index = states.indexOf(this);

        if (index == states.size()) {
            return states.get(0);
        } else {
            return states.get(index+1);
        }
    }
}
