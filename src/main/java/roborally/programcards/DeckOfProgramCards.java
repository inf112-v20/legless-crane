package roborally.programcards;

import java.util.ArrayList;

public class DeckOfProgramCards {
    private ArrayList<ProgramCard> deckOfProgramCards;

    /**
     * Constructing the deck of program cards that dictate player movement
     * Each card has unique priority and a movement type
     */
    public DeckOfProgramCards() {
        deckOfProgramCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("back", 430 + (i * 10)));    // Move backwards
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("u", 10 + (i * 10)));        // U-turn
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotateright", 80 + (i * 20)));   // Rotate right
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotateleft", 70 + (i * 20)));   // Rotate left
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("1", 490 + (i * 10)));       // Move forward 1
        }
        for (int i = 0; i < 12; i++) {
            deckOfProgramCards.add(new ProgramCard("2", 670 + (i * 10)));       // Move forward 2
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("3", 790 + (i * 10)));       // Move forward 3
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