package testing;


import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ProgramCardTest {
    private final DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
    private final ArrayList<ProgramCard> placementOfPhases = new ArrayList<>();
    private final ProgramCard phase = new ProgramCard();

    // Control deck size (in regards to deal correctly 9 random cards in GameScreen). 0-83: deckOfProgramCards-1.

    @Test
    public void checkDeckSize() {
        assertEquals(84, deckOfProgramCards.getDeckSize());
    }

    // Check if a card's movement is being consistent with priority (based on array index in DeckOfProgramCards).

    @Test
    public void checkPriorityAccordingToMovement() {
        assertEquals(240, deckOfProgramCards.getProgramCardPriority(20));
        assertEquals("rotateright", deckOfProgramCards.getProgramCardMovement(20));
    }

    // Check if a program card without parameters indicates default movement value. "Phase = program card without movement/priority"..

    @Test
    public void checkDefaultPhase() {
        assertEquals("default", phase.getMovement());
    }

    // Check if a phase correctly adds a program card dealt randomly from DeckOfProgramCards.

    @Test
    public void checkReadyPhase() {

        // Deal 5 random cards from DeckOfCards:

        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * deckOfProgramCards.getDeckSize() - 1);
            final ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));

            // 5 empty program cards as phases (ready to be "programmed")

            placementOfPhases.add(i, phase);

            // Control after addition:

            if (placementOfPhases.get(i).getMovement().equals("default")) {
                placementOfPhases.remove(i);
                placementOfPhases.add(i, card);
            }
            assertNotNull(placementOfPhases.get(i));
        }

        // Check if 5 random movements are being read after programming the robot (the 5 phases which are ready):

        for (int i = 0; i < 5; i++) {
            String movement = placementOfPhases.get(i).getMovement();
            assertEquals(true, "1".equals(movement)
                    || "2".equals(movement)
                    || "3".equals(movement)
                    || "u".equals(movement)
                    || "back".equals(movement)
                    || "rotateright".equals(movement)
                    || "rotateleft".equals(movement));
        }
    }
}