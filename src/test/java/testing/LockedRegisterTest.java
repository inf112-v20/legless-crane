package testing;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import roborally.application.GameScreen;
import roborally.gamelogic.GameLogic;
import roborally.gamelogic.Player;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class LockedRegisterTest {

    private GameScreen gameScreen;
    private final GameLogic gameLogic = new GameLogic(gameScreen, 3, FILE_PATH_1);
    private final DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
    private static final String FILE_PATH_1 = "boards/testBoard0.tmx";
    private final Vector2 startingPosition= new Vector2(1,1);
    private final Player player = new Player(0, startingPosition, gameLogic);
    final ProgramCard[] oneTurn = new ProgramCard[5];
    private int count = 0;
    private int health;
    private int lastIndex;

    /**
     * In general; this class tests the transition of cards between a new turn, during a turn (concerning damage)
     * and thereafter preparation in advance of the next turn.
     *
     * Checks code for both "currentPlayer" in GameLogic (5 first tests) and AIs in GameLogic (2 last tests).
     *
     * The test accordance1() checks that currentPlayer receives 5 default cards in advance of a new turn,
     * given that the 9 health points are unchanged.
     * Based on code in updateGameState() in gameLogic (cleanup).
     *
     */

    @Test
    public void accordance1() {
        health = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("default")) {
                count++;
            }
        }
        for (int i = 0; i < oneTurn.length; i++) {
            assertEquals(5, count);
        }
        count = 0;
    }

    /**
     * accordance2() checks if currentPlayer receives 4 new default cards in advance of a new turn,
     * in case it loses 5 health points during the previous turn.
     * Based on code in GameLogic: fillPhase() and the cleanup-state in updateGameState().
     */

    @Test
    public void accordance2() {
        player.updateHealth(-5);
        health = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("default")) {
                count++;
            }
        }
        for (int i = 0; i < oneTurn.length; i++) {
            assertEquals(4, count);
        }
        count = 0;
    }

    /**
     * accordance3() checks if currentPlayer receives 1 new default card in advance of a new turn,
     * in case it loses 8 health points during the previous turn.
     * Based on code in GameLogic: fillPhase() and the cleanup-state in updateGameState().
     */

    @Test
    public void accordance3() {
        player.updateHealth(-8);
        health = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("default")) {
                count++;
            }
        }
        for (int i = 0; i < oneTurn.length; i++) {
            assertEquals(1, count);
        }
        count = 0;
    }

    /**
     * accordance4() checks that default cards are added in ascending order,
     * in case some registers are locked from the previous turn.
     * Based on code in GameLogic: cleanup-state in updateGameState().
     */
    @Test
    public void accordance4() {
        player.updateHealth(-8);
        health = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("default")) {
                count = i;
            }
            assertEquals(0, count);
        }
        count = 0;
    }

    /**
     * In case of a new turn and health points lost during the previous turn (more than 4 points):
     * accordance5() checks that the phases which are not filled with new default cards return the orignial cards.
     * Based on code in GameLogic: cleanup-state in updateGameState().
     */
    @Test
    public void accordance5() {
        player.updateHealth(-8);
        health = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("back_up_")) {
                count ++;
            }
        }
        assertEquals(4, count);
        count = 0;
    }

    /**
     * addCardsAI() checks if the "AIs" in GameLogic get random cards dealt in advance of a turn.
     * Based on code from GameLogic (programAIs()-method).
     */

    @Test
    public void addCardsAI() {
        lastIndex = player.getHealth();

        if (lastIndex > 5){
            lastIndex = 5;
        }

        int startingPoint = 0;

        for (int phase = 0; phase < lastIndex; phase++) {
            int index = gameLogic.getCardIndices().get(startingPoint);
            ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                    deckOfProgramCards.getProgramCardPriority(index));
            oneTurn[phase] = card;
            startingPoint++;
            assertNotSame(oneTurn[phase].getMovement(),"default");
            }
    }

    /**
     * updatedCardsAI() checks if the AIs in GameLogic get new cards correctly adjusted after one turn.
     * 1 health point left results in only one default card for the next turn (which will be renewed).
     * Based on code in GameLogic:
     * - updateGameState()-method (cleanup-state after a turn)
     * - programAIs()-method (in advance of a new turn).
     */

    @Test
    public void updatedCardsAI() {
        player.updateHealth(-8);
        lastIndex = player.getHealth();

        for (int i = 0; i < oneTurn.length; i++) {
            oneTurn[i] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(0),deckOfProgramCards.getProgramCardPriority(0));
        }

        if (player.getHealth() < 5) {
            int lockedRegisters = player.getHealth();
            for (int i = 0; i < lockedRegisters; i++) {
                oneTurn[i] = new ProgramCard();
            }
        } else {
            for (int i = 0; i < 5; i++) {
                oneTurn[i] = new ProgramCard();
            }
        }

        if (lastIndex > 5){
            lastIndex = 5;
        }

        for (int phase = 0; phase < lastIndex; phase++) {
            ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(83),
                    deckOfProgramCards.getProgramCardPriority(83));
            oneTurn[phase] = card;
        }

        for (int i = 0; i < oneTurn.length; i++) {
            if (oneTurn[i].getMovement().equals("back_up_")) {
                count ++;
            }
        }
        assertEquals(4, count);
        count = 0;
    }
}
