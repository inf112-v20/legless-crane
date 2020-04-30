package roborally.programcards;

import roborally.ui.BaseActor;

/**
 * Program card, stores movement which will represent the player's different phases during the game.
 */
public class ProgramCard extends BaseActor {
    private final String movement;
    private int priority;
    private int deckIndex;

    /**
     * An object which contains the relevant data of a programcard, movement type and priority.
     */
    public ProgramCard(String movement, int priority) {
        this.movement = movement;
        this.priority = priority;
        this.deckIndex = deckIndex;
    }

    /**
     * Default program card (represents a phase ready to be "programmed").
     */
    public ProgramCard(){
        this.movement = "default";
    }

    public String getMovement() {
        return movement;
    }

    public int getPriority() {return priority;}

    public void setDeckIndex(int deckIndex){ this.deckIndex = deckIndex; }

    public int getDeckIndex(){ return this.deckIndex; }
}