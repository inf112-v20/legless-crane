package roborally.programcards;

import roborally.ui.BaseActor;

/**
 * Program card, stores movement which will represent the player's different phases during the game.
 * TODO: add values? (make program cards have priority)
 */
public class ProgramCard extends BaseActor {
    private String movement;

    public ProgramCard(String movement) {
        this.movement = movement;
    }

    public String getMovement() {
        return movement;
    }
}