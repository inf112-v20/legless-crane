package roborally.programcards;

import roborally.ui.BaseActor;

public class ProgramCard extends BaseActor {
    private String movement;
    // TODO: add value? (make program cards have priority)

    public ProgramCard(String movement) {
        this.movement = movement;
    }

    public String getMovement() {
        return movement;
    }
}