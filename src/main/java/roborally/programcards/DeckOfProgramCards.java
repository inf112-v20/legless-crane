package roborally.programcards;

import java.util.ArrayList;

/**
 * Deck of program cards, collection of the particular distribution of program cards (according to movements).
 */
public class DeckOfProgramCards {
    private final ArrayList<ProgramCard> deckOfProgramCards;

    /**
     * Constructing the deck of program cards that dictate player movement
     * Each card has unique priority and a movement type
     */
    public DeckOfProgramCards() {
        deckOfProgramCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("back_up_", 430 + (i * 10)));    // Move backwards
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("u_turn_", 10 + (i * 10)));        // U-turn
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotate_right_", 80 + (i * 20)));   // Rotate right
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotate_left_", 70 + (i * 20)));   // Rotate left
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("move_1_", 490 + (i * 10)));       // Move forward 1
        }
        for (int i = 0; i < 12; i++) {
            deckOfProgramCards.add(new ProgramCard("move_2_", 670 + (i * 10)));       // Move forward 2
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("move_3_", 790 + (i * 10)));       // Move forward 3
        }
    }

    public String getProgramCardMovement(int index) {
        return deckOfProgramCards.get(index).getMovement();
    }

    public int getProgramCardPriority(int index) {
        return deckOfProgramCards.get(index).getPriority();
    }

    public int getDeckSize() {
        return deckOfProgramCards.size();
    }
}