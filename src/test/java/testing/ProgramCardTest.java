package testing;

import org.lwjgl.Sys;
import roborally.application.GameScreen;
import roborally.gamelogic.GameLogic;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class ProgramCardTest {
    private GameScreen gameScreen;
    private GameLogic gameLogic = new GameLogic(gameScreen, 3, FILE_PATH_1);
    private final DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
    private static final String FILE_PATH_1 = "boards/Risky_Exchange.tmx";
    private final ProgramCard phase = new ProgramCard();
    private int count = 0;

    /**
     * Control if method returns correct size of array ("deckOfCards-indices")
     */
    @Test
    public void checkCardIndices1() {
        assertEquals(84, gameLogic.getCardIndices().size());
    }

    /**
     * Control that method returns unique numbers ranging from 0 to 83
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
     * Control accordance between priority and movement
     */
    @Test
    public void checkPriorityAccordingToMovement1() {
        assertEquals(240, deckOfProgramCards.getProgramCardPriority(20));
        assertEquals("rotate_right_", deckOfProgramCards.getProgramCardMovement(20));
    }

    @Test
    public void checkPriorityAccordingToMovement2() {
        assertEquals(660, deckOfProgramCards.getProgramCardPriority(65));
        assertEquals("move_1_", deckOfProgramCards.getProgramCardMovement(65));
    }

    @Test
    public void checkPriorityAccordingToMovement3() {
        assertEquals(810, deckOfProgramCards.getProgramCardPriority(80));
        assertEquals("move_3_", deckOfProgramCards.getProgramCardMovement(80));
    }

    /**
     * Control that a card without assigned movement/priority returns expected value
     */
    @Test
    public void checkDefaultPhase() {
        assertEquals("default", phase.getMovement());
    }
}