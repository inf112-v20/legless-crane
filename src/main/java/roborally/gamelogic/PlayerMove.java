package roborally.gamelogic;

public class PlayerMove implements Comparable {
    private Moves move;
    private int playerNumber;
    private int movePriority;

    /**
     * A small custom-class to make sorting and execution of queued moves easier.
     *
     * implements Comparable to allow easy comparison of move priority
     *
     * @param m the move to be performed on a player
     * @param num the number of the player being moved
     * @param priority the priority of the movement.
     */
    public PlayerMove(Moves m, int num, int priority) {
        this.move=m;
        this.playerNumber=num;
        this.movePriority=priority;
    }

    public Moves getMove() {
        return move;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getMovePriority() {
        return movePriority;
    }

    @Override
    public int compareTo(Object o) {
        return (((PlayerMove) o).getMovePriority() - this.movePriority);
    }
}
