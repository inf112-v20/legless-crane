package RoboRally.Board;

public class BoardCollection {
    // Could store boards in a list of lists sort of like this:
    int[][] boards= new int[][]{{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    }, {
            0, 1, 0, 0, 2, 2, 2, 1, 2, 0, 0,
            0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0,
            1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0,
            0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }
    };
    /**
     * There could be some long-term issues with how we save our maps, the first iterations should be no issue.
     * First issue that comes to mind is storing lasers and placing them properly on the board.
     * There can be several beans on the same tile, they can intersect with other elements on the board.
     *  See "Chop Shop Challenge" map in the rulebook.
     *
     * first implementation may not need to bother with facing of elements such as walls just yet.
     *
     * 0 = open
     * 1 = hole
     *
     * 11, 12, 13, 14 = wall facing N,E,S,W
     * 21, 22, 23, 24 = yellow belt facing N,E,S,W
     * 31, 32, 33, 34 = blue belt facing N,E,S,W
     * 41, 42 = cog facing clockwise and counter-clockwise
     */

    public int[] getBoard(int num) {
        if (num > boards.length-1 || num < 0) {
            System.out.println("Invalid board-selection");
            return null;
        } else
            return boards[num];
    }
}
