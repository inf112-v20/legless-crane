package testing;

import roborally.application.GameScreen;
import roborally.gamelogic.GameLogic;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProgramCardTest {
    private GameScreen gameScreen;
    private final GameLogic gameLogic = new GameLogic(gameScreen, 3, FILE_PATH_1);
    private final DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
    private static final String FILE_PATH_1 = "boards/testBoard0.tmx";
    private final ProgramCard phase = new ProgramCard();
    private int count = 0;

    /**
     * Checks that method returns correct size of array (representing indices in DeckOfProgramCards).
     */
    @Test
    public void checkCardIndices1() {
        assertEquals(84, gameLogic.getCardIndices().size());
    }

    /**
     * Checks that method returns unique numbers ranging from 0 to 83
     * Based on code in GameLogic: setCardIndices().
     */
    @Test
    public void checkCardIndices2() {
        for (int i = 0; i < gameLogic.getCardIndices().size(); i++) {
            for (int j = 0; j < 84; j++) {
                if (gameLogic.getCardIndices().get(i) == j)
                    count++;
            }
            assertEquals(1, count);
            count = 0;
        }
    }

    /**
     * Checks accordance between priority and movement for cards.
     * Based on code in DeckOfProgramCards.
     */
    @Test
    public void checkPriorityAccordingToMovement1() {
        assertEquals(240, deckOfProgramCards.getProgramCardPriority(20));
        assertEquals("rotate_right_", deckOfProgramCards.getProgramCardMovement(20));
    }

    /**
     * Checks accordance between priority and movement for cards.
     * Based on code in DeckOfProgramCards.
     */
    @Test
    public void checkPriorityAccordingToMovement2() {
        assertEquals(660, deckOfProgramCards.getProgramCardPriority(65));
        assertEquals("move_1_", deckOfProgramCards.getProgramCardMovement(65));
    }

    /**
     * Checks accordance between priority and movement for cards.
     * Based on code in DeckOfProgramCards.
     */
    @Test
    public void checkPriorityAccordingToMovement3() {
        assertEquals(810, deckOfProgramCards.getProgramCardPriority(80));
        assertEquals("move_3_", deckOfProgramCards.getProgramCardMovement(80));
    }

    /**
     * Checks that a card without assigned movement/priority returns expected value
     * Based on code in ProgramCard.
     */
    @Test
    public void checkDefaultCard() {
        assertEquals("default", phase.getMovement());
    }

    /**
     * Checks that a card with assigned movement/priority does not return default
     * Based on code in ProgramCard.
     */
    @Test
    public void checkNonDefaultCard() {
        ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0), deckOfProgramCards.getProgramCardPriority(0));
        assertNotSame("default", card.getMovement());
    }

    /**
     * Checks that a program card correctly stores the index value of "deckOfCards".
     * Used in GameScreen to transfer indices to GameLogic while programming phases.
     * Based on code in ProgramCard.
     */
    @Test
    public void checkAccordanceBetweenDeckIndexAndCardIndex() {
        ProgramCard card1 = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0), deckOfProgramCards.getProgramCardPriority(0));
        card1.setDeckIndex(0);

        assertEquals(0, card1.getDeckIndex());
    }

    /**
     * Control that "program cards given as phases" do not return invalid values, (having random cards dealt in advance).
     * Based on code in:
     * - GameLogic: setCardIndices(), getCardIndices()
     * - GameScreen: deckOfProgramCards()
     */
    @Test
    public void checkIfPhasesGetReadCorrectly1() {
        ProgramCard[] onePhase = new ProgramCard[5];

        for (int i = 0; i < onePhase.length; i++) {
            int index = gameLogic.getCardIndices().get(i);
            onePhase[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));

            onePhase[i].setDeckIndex(index);
        }
        for (int i = 0; i < onePhase.length; i++) {
            assertNotSame("default", onePhase[i].getMovement());
        }
    }
}