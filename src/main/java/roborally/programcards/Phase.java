package roborally.programcards;

import roborally.ui.BaseActor;

import java.util.ArrayList;

/*
 * Stores the program cards which will move the player during the phases of a current turn.
 */
public class Phase extends BaseActor {
    private final ArrayList<ProgramCard> list;

    public Phase() {
        list = new ArrayList<ProgramCard>();
    }

    public void addCard(ProgramCard c) {
        list.add(c);
    }

    public ProgramCard getTopCard() {
        if (list.isEmpty())
            return null;
        else
            return list.get(list.size() - 1);
    }
}
