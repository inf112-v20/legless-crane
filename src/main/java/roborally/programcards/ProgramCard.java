package roborally.programcards;

import roborally.ui.BaseActor;

public class ProgramCard extends BaseActor {
    private String movement;
    private int priority;
    // TODO: add value? (make program cards have priority)

    public ProgramCard(String movement, int priority) {
        this.movement = movement;
        this.priority = priority;
    }
    public String getMovement() {
        return movement;
    }

    public int getPriority() {return priority;}
}