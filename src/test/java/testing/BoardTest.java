package testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import roborally.board.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    private static Board board;

    @BeforeAll
    public static void setUp(){
        board = new Board("boards/testBoard0.tmx");
    }
    @Test
    public void boardHeightTest(){
        assertEquals(5, board.getBoardHeight());
    }

    @Test
    public void boardWidthTest(){
        assertEquals(5, board.getBoardWidth());
    }

}
