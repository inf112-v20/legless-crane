package roborally.programcards;

import roborally.ui.BaseActor;

public class ProgramCard extends BaseActor {
    private String movement;
    private int priority;

    /**
     * An object which contains the relevant data of a programcard, movement type and priority.
     */
    public ProgramCard(String movement, int priority) {
        this.movement = movement;
        this.priority = priority;
    }
    public String getMovement() {
        return movement;
    }

    public int getPriority() {return priority;}
}