package roborally.programcards;

import java.util.ArrayList;

public class DeckOfProgramCards {
    private ArrayList<ProgramCard> deckOfProgramCards;

    public DeckOfProgramCards() {
        deckOfProgramCards = new ArrayList<ProgramCard>();
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("back"));             // Move backwards
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("u"));                // U-turn
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotateright"));      // Rotate right
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("rotateleft"));      // Rotate left
        }
        for (int i = 0; i < 18; i++) {
            deckOfProgramCards.add(new ProgramCard("1"));               // Move forward 1
        }
        for (int i = 0; i < 12; i++) {
            deckOfProgramCards.add(new ProgramCard("2"));               // Move forward 2
        }
        for (int i = 0; i < 6; i++) {
            deckOfProgramCards.add(new ProgramCard("3"));               // Move forward 3
        }
    }

    public String getProgramCardMovement(int index) {
        return deckOfProgramCards.get(index).getMovement();
    }

    public int getDeckSize() {
        return deckOfProgramCards.size();
    }
}