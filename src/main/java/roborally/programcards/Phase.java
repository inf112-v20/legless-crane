package roborally.programcards;

import roborally.ui.BaseActor;

import java.util.ArrayList;

public class Phase extends BaseActor {
    private ArrayList<ProgramCard> list;

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
