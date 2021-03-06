package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Board;
import roborally.board.Direction;
import roborally.board.Tile;
import roborally.application.GameScreen;
import roborally.programcards.DeckOfProgramCards;
import roborally.programcards.ProgramCard;

import java.util.*;


public class GameLogic {
    @SuppressWarnings("CanBeFinal")
    public Player currentPlayer;
    private final ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private final GameScreen gameScreen;
    public int boardWidth;
    public int boardHeight;
    private int count = 0;
    private int phase;
    private GameState gameState;
    private ArrayList<PlayerMove> queue = new ArrayList<>();
    private ArrayList<Integer> cardIndices = new ArrayList<>();
    private ProgramCard[][] chosenCards;
    private ArrayList<Integer> list;
    public boolean cardsChosen = false;

    private Stack<Stack<Integer>> playersNextPhase = new Stack<>();
    private DeckOfProgramCards deckOfProgramCards;

    private final int numberOfPhases = 5;
    private int phaseNum = 0;
    private int index;

    private int lastIndex;
    private int startingPoint;


    public GameState getGameState() {
        return gameState;
    }

    public int getPhase() {
        return phase;
    }

    /**
     * Constructor which establishes a singleton connection between gamescreen and players, ensuring we only get one
     * instance of GameLogic and GameScreen.
     *
     * loads the board from file
     *
     * Adds players to the game and places them on their spawnpoint, also ensuring they only have one GameLogic to
     * think about.
     *
     * sets the first player as the currentPlayer (only relevant once multiple players are in and they take turns)
     *
     * @param gameScreen the screen which calls GameLogic, should be called using new GameLogic(this, numPlayers) so
     *                   we don't get more than one instance of GameLogic or GameScreen
     * @param numPlayers the number of players this game (how many Player objects to create in this constructor)
     */
    public GameLogic(GameScreen gameScreen, int numPlayers, String filePath){
        this.gameScreen = gameScreen;
        loadBoard(filePath);
        for(int i = 0; i <numPlayers; i++) {
            players.add(new Player(i+1, board.getSpawnPoints(i),this));
        }

        currentPlayer = players.get(0); // so far only used by GameScreen
        gameState = GameState.DEAL_CARDS;

        setCardIndices();
        chosenCards = new ProgramCard[5][players.size()];
        deckOfProgramCards = new DeckOfProgramCards();
        initializeLists();
    }

    public ProgramCard[][] getChosenCards() {
        return chosenCards;
    }

    /**
     * When a new game is getting started,
     * phases and stacks which keep track of "regretting" (see regretPhase()) are initialized.
     *
     * General principle: "default cards" (i.e. new ProgramCard()) without assigned values represent non-programmed phases.
     *
     */
    private void initializeLists(){
        for (int row = 0; row < getChosenCards().length; row++) {
            for (int col = 0; col < getChosenCards()[row].length; col++) {
                getChosenCards()[row][col]= new ProgramCard();
            }
        }
        for (int playerNum = 0; playerNum < players.size(); playerNum++) {
            playersNextPhase.add(new Stack<Integer>());
            }
    }

    /**
     * For currentPlayer in GameScreen:
     * Method which deals with logic concerning adding cards as phases (while programming the robot).
     */
    public int fillPhase(int playerNum, int index) {

        lastIndex = players.get(playerNum).getHealth()-1;
        if (lastIndex >= numberOfPhases) {
            lastIndex = 4;
        }

        // All phases are already filled
        if (!getChosenCards()[lastIndex][playerNum].getMovement().equals("default") && playersNextPhase.get(playerNum).isEmpty()) {
            return -1;
        }

        if (!playersNextPhase.get(playerNum).isEmpty()) {
            phaseNum = playersNextPhase.get(playerNum).pop();
        }

        while (!getChosenCards()[phaseNum][playerNum].getMovement().equals("default") && phaseNum < lastIndex) {
            phaseNum++;
        }

        getChosenCards()[phaseNum][playerNum] = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                deckOfProgramCards.getProgramCardPriority(index));

        return phaseNum;
    }

    /**
     * For currentPlayer in GameScreen:
     * Method which deals with logic concerning regretting cards which have been chosen as phases.
     * Fills a stack (particular stack assigned to the currentPlayer) with "next phase number" to get filled.
     *
     * @param playerNum the player regretting a card
     * @param phaseNum the phase which is getting changed (e.g. first phase = 0).
     */
    public void regretPhase(int playerNum, int phaseNum){
        getChosenCards()[phaseNum][playerNum] = new ProgramCard();
        playersNextPhase.get(playerNum).push(phaseNum);
    }

    /**
     * Method which checks whether or not all phases for all players are ready.
     *
     * @return status regarding fulfillment of the players' phases
     * When true, currentPlayer can start the turn in GameScreen.
     */
    public boolean cardsAreChosen() {
        programAIs();

        for (int row = 0; row < getChosenCards().length; row++) {
            for (int col = 0; col < getChosenCards()[row].length; col++) {
                if (getChosenCards()[row][0].getMovement().equals("default")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *  Prepare "dummy"-AIs for a turn. Random moves are given.
     *  Reads "card indices" from gameLogic.getCardIndices() from individual starting points.
     */
    private void programAIs(){
        for (int playerNum = 1; playerNum < players.size(); playerNum++) {

            lastIndex = players.get(playerNum).getHealth();

            if (lastIndex > numberOfPhases){
                lastIndex = 5;
            }

            startingPoint = playerNum * players.get(playerNum - 1).getHealth();

            for (int phase = 0; phase < lastIndex; phase++) {
                index = getCardIndices().get(startingPoint);
                ProgramCard card = new ProgramCard(deckOfProgramCards.getProgramCardMovement(index),
                        deckOfProgramCards.getProgramCardPriority(index));
                chosenCards[phase][playerNum] = card;
                startingPoint++;
            }
        }
    }

    /**
     * performs the move queued next with the player that should perform it. (Assuming the lists are in sync)
     */
    private void performMove() {
        if (queue.size()==0) {
            return; // if no moves are queued, do nothing.
        }
        PlayerMove nextMove = queue.remove(0);
        if (nextMove.getType() == Moves.FORWARD) {
            forwardMovement(players.get(nextMove.getPlayerNumber()-1));
        } else if (nextMove.getType() == Moves.BACK) {
            backwardMovement(players.get(nextMove.getPlayerNumber()-1));
        } else if (nextMove.getType() == Moves.LEFT) {
            rotatePlayer(players.get(nextMove.getPlayerNumber()-1), -1);
        } else if (nextMove.getType() == Moves.RIGHT) {
            rotatePlayer(players.get(nextMove.getPlayerNumber()-1), 1);
        } else {
            throw new IllegalStateException("Unexpected value");
        }
        killIfOffBoard();
    }

    /**
     * Gets the card chosen by all players for the given phase we're in.
     *
     * Converts the cards chosen into queued moves so they can be staggered, i.e. move 1 * 3 instead of move 3 tiles
     * instantly for a 3 movement card.
     *
     * Once all the moves for the players have been queued up this phase, we sort it using quicksort.
     *
     */
    private void queuePhase() {
        ProgramCard[] cardsThisPhase = getChosenCards()[phase];

        for (int i = 0; i <cardsThisPhase.length ;i++) {
            if(players.get(i).isDead()) continue;
            switch(cardsThisPhase[i].getMovement()) {
                case "move_1_":
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "move_2_":
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "move_3_":
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    queue.add(new PlayerMove(Moves.FORWARD, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "u_turn_":
                    queue.add(new PlayerMove(Moves.RIGHT, i+1, cardsThisPhase[i].getPriority()));
                    queue.add(new PlayerMove(Moves.RIGHT, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "back_up_":
                    queue.add(new PlayerMove(Moves.BACK, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "rotate_left_":
                    queue.add(new PlayerMove(Moves.LEFT, i+1, cardsThisPhase[i].getPriority()));
                    break;
                case "rotate_right_":
                    queue.add(new PlayerMove(Moves.RIGHT, i+1, cardsThisPhase[i].getPriority()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cardsThisPhase[i].getMovement());
            }
        }
        Collections.sort(queue);
    }


    /**
     * Gets called by GameScreen every frame, checks the state of the game and updates it accordingly
     *
     * the update of the gameState is staggered to only update every 10 frames (or every 10th call of updateGameState()
     * so the screen does not update instantly, making it difficult for the player to follow the progress of the game.
     *
     * updateGameState() loops through the enum GameState each phase performing every step in order.
     *
     * 1. DEAL_CARDS is visited once every round, we get the cards chosen by the player on GameScreen and advance the
     * GameState, if cards haven't been chosen yet, nothing happens. This is where the phases begin
     *
     * Every phase consists of these steps being done in order:
     * A. REVEAL_CARDS gets the movement for this phase (one card per player,
     * and queues the moves to be made using queuePhase([the_cards_this_phase])
     *
     * B. MOVE_PLAYER moves the players on the board, it goes through the sorted list of moves and players performing
     * them which was made with queuePhase earlier in REVEAL_CARDS. If the player moves off the board or pushes another
     * player off the board or into a pit, the player dies (checked with killIfOffBoard()). Each player gets to perform
     * it's move before the board starts to move or attack the player. we check if there are any more moves queued, if
     * not then we advance the gameState.
     *
     * C. MOVE_BOARD handles interactions between the player and elements on the board where the player is moved,
     * if on a belt the player is moved etc. Once all elements have been checked in order, the gameState advances.
     *
     * D. FIRE_LASERS updates the player's health if any are standing on a static laser.
     *
     * E. RESOLVE_INTERACTIONS handles additional interactions between player and board such as registering
     * flags and updating backup/archive locations. In other words, flags and repair sites on the board.
     *
     * 2. CLEANUP happens after all 5 phases have been performed, any player on a wrench/repair tile will be repaired.
     * TODO: Expand this to give the player option cards if on a crossed wrench/hammer space. (Not in MVP?)
     *
     */
    public void updateGameState() {
        if (count<20) {
            count++;
            return;
        }
        count = 0;

        switch (gameState) {
            case DEAL_CARDS:
                if (cardsChosen) {
                    gameState = gameState.advance(); }
                break;

            case REVEAL_CARDS:
                queuePhase();
                gameState = gameState.advance();
                break;

            case MOVE_PLAYER:
                performMove();
                if (queue.size() == 0) {
                    gameState = gameState.advance();
                }
                break;

            case MOVE_BOARD:
                killIfOffBoard();
                for (ElementMoves moves : ElementMoves.values()) {
                    for(Player player : players) {
                        if (player.isDead()) continue;

                        Vector2 playerPosition = player.getPosition();
                        Tile playerTile = board.getTile(playerPosition);
                        switch (moves) {
                            case EXPRESS_BELTS:
                                if (playerTile.isBelt() && playerTile.getMovementSpeed() == 2) beltsMovePlayer(player);
                                break;
                            case ALL_BELTS:
                                if (playerTile.isBelt()) beltsMovePlayer(player);
                                break;
                            case PUSHERS:
                                // pushers not yet implemented
                                break;
                            case COGS:
                                if (playerTile.isCog()) rotationCogs(player);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + moves);
                        }
                    }
                }

                gameState = gameState.advance();

            case FIRE_LASERS:
                killIfOffBoard();
                for (Player player : players) {
                    if (player.isDead()) continue;

                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isLaser()) player.updateHealth(playerTile.getHealthChange());

                }
                gameState = gameState.advance();
                break;

            case FIRE_PLAYER_LASER:
                killIfOffBoard();
                for (Player player : players){
                    if (player.isDead()) continue;

                    Vector2 playerPosition = player.getPosition();
                    Direction dir = player.getRotation();

                    laserPathCheck(player, dir, playerPosition);
                }
                gameState = gameState.advance();
                break;

            case RESOLVE_INTERACTIONS:
                killIfOffBoard();
                for (Player player : players) {
                    if (player.isDead()) continue;

                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isWrench()) player.setBackupPoint(playerPosition);

                    if (playerTile.isFlag()) {
                        registerFlag(currentPlayer);
                        player.setBackupPoint(playerPosition);
                    }
                }
                gameState = gameState.advance();
                break;

            case CLEANUP:
                if (phase < 4) {
                    gameState = gameState.advance().advance();
                    phase++;
                    break;
                }

                for (Player player : players) {
                    if (player.isDead()) continue;

                    Vector2 playerPosition = player.getPosition();
                    Tile playerTile = board.getTile(playerPosition);

                    if (playerTile.isWrench() || playerTile.isFlag()) player.updateHealth(playerTile.getHealthChange());
                }

                // Depending on health: fill phases with default cards in preparation for the next turn
                for (Player player : players) {

                    if (player.getHealth() < 5) {
                        int lockedRegisters = player.getHealth();
                        for (int i = 0; i < lockedRegisters; i++) {
                            getChosenCards()[i][player.getPlayerNumber() - 1] = new ProgramCard();
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            getChosenCards()[i][player.getPlayerNumber() - 1] = new ProgramCard();
                        }
                    }
                }

                if(noOtherPlayersLeft())
                    gameScreen.playerWins();

                for (Player player : players) {
                    if (player.isDead() && !player.isPermaDead()) {
                        respawnPlayer(player);
                    }
                }


                cardsChosen = false;
                cardIndices.clear();
                setCardIndices();

                gameScreen.prepareCards();
                gameScreen.deckOfProgramCards();
                phaseNum = 0;

                gameState = gameState.advance();
                phase = 0;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + gameState);
        }
    }

    /**
     * Method which checks if the current player is heading towards another player.
     * If another player: cause damage.
     *
     * @param player the player that is looking
     * @param pos the position it was standing on
     * @param direction the direction the current player is heading
     * @return if the player can't look any further
     */

    private void laserPathCheck(Player player, Direction direction, Vector2 pos) {
        Vector2 nextPosition = getDirectionalPosition(pos, direction);

        if (nextPosition.x >= boardWidth || nextPosition.y >= boardHeight || nextPosition.x <= 0 || nextPosition.y <= 0) {
            return;
        }

        Tile currentTile = board.getTile(player.getPosition());
        Tile nextTile = board.getTile(nextPosition);

        if (currentTile.canBlockMovement()) {
            for (Direction dir : currentTile.getBlockingDirections())
                if (dir.equals(direction))
                    return;
        } else if (nextTile.canBlockMovement()) {
            for (Direction dir : nextTile.getBlockingDirections())
                if (dir == direction.opposite())
                    return;
        }
        for (Player otherPlayer : players) {
            if (otherPlayer != player) {
                if (otherPlayer.getPosition().equals(nextPosition)) {
                    otherPlayer.updateHealth(-1);
                    gameScreen.shootPlayer(otherPlayer);
                    return;
                }
            }
        }
        laserPathCheck(player, direction, nextPosition);
    }

    /**
     * Checks if the tile the player is currently on or the tile the player wishes to move in the direction of, blocks
     * movement between the two.
     *
     * Checks each tile's blocking directions (if any), movement is blocked if one of these directions on the tile
     * the player moves from is the same as the direction the player wishes to move. Or if the tile the player wishes
     * to move to blocks movement from the direction the player is coming from.
     *
     * @param player the player which wishes to move
     * @param direction the direction the player wishes to move
     * @return whether or not the player can move in that direction without being blocked
     */
    public boolean notBlockedByWall(Player player, Direction direction) {
        Vector2 nextPosition = getDirectionalPosition(player.getPosition(), direction);

        if (nextPosition.x > boardWidth || nextPosition.y > boardHeight || nextPosition.x < 0 || nextPosition.y < 0) {
            return false;
        }

        Tile currentTile = board.getTile(player.getPosition());
        Tile nextTile = board.getTile(nextPosition);

        if (currentTile.canBlockMovement()) {
            for (Direction dir : currentTile.getBlockingDirections())
                if (dir.equals(direction))
                    return false;
        } else if (nextTile.canBlockMovement()) {
            for (Direction dir : nextTile.getBlockingDirections())
                if (dir == direction.opposite())
                    return false;
        }
        return true;
    }

    /**
     * Checks if there is a player in the direction given from the given player's position.
     * Pushes the player in the given direction if there is one there.
     *
     * @param player our starting out point
     * @param direction the direction we're checking
     * @return if the tile is free or the player in it can be pushed.
     */
    public boolean notBlockedByPlayer(Player player, Direction direction) {
        Vector2 nextPosition = getDirectionalPosition(player.getPosition(), direction);
        for (Player otherPlayer : players) {
            if (otherPlayer != player) {
                if (otherPlayer.getPosition().equals(nextPosition)) {
                    return movePlayerInDirection(otherPlayer, direction);
                }
            }
        }
        return true;
    }

    /**
     * During one turn; each player gets unique, random card-indices from the same "deck of cards"
     *
     * @return array of 84 numbers (shuffled)
     */
    public ArrayList<Integer> getCardIndices(){
        return cardIndices;
    }

    /**
     * Initialize array of 84 numbers. Shuffles it.
     */
    private void setCardIndices() {
        list = new ArrayList<>();
        for (int i = 0; i < 84; i++) {
            list.add(i);
            Collections.shuffle(list);
        }
        cardIndices.addAll(list);
    }

    /**
     * Method for moving a player in a specific direction
     *
     * Checks if this goes off the board or if it collides with a wall, these cases currently do not allow movement in
     * that direction.
     *
     * @param player the player who's position we should update
     * @param dir the direction which the player should move (used for checking against walls
     */
    private boolean movePlayerInDirection(Player player, Direction dir) {
        Vector2 nextPos = getDirectionalPosition(player.getPosition(), dir);
        if (notBlockedByWall(player, dir) && notBlockedByPlayer(player,dir)) {
            gameScreen.setPlayerPosition(player, nextPos);
            player.setPosition(nextPos);
            return true;
        }
        return false;
    }

    /**
     * helper method
     * @return if all players except the main player are dead or not
     */
    public boolean noOtherPlayersLeft() {
        for (Player p : players) {
            if (p != players.get(0)) {
                if (!p.isPermaDead()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void gameOver() {
        gameScreen.gameOver();
    }


    /**
     * Player uses this method when dying, moving the player back to their backup point on both gameScreen and in
     * gameLogic
     *
     * @param player the player which should respawn.
     */
    public void respawnPlayer(Player player) {
        gameScreen.setPlayerPosition(player, player.getBackupPoint());
        player.setPosition(player.getBackupPoint());
        player.respawn();
    }

    /**
     * Method which calculates the direction the player will be facing using the rotate method in the Direction enum
     *
     * Updates the player rotation in game logic and in the renderer.
     *
     * @param player the player which should be rotated.
     * @param direction an integer which either negative or positive indicates which direction the player should rotate
     *                  the value is how much it should be rotated.
     */
    public void rotatePlayer(Player player, int direction) {
        // direction can be +1 (clocwise) or -1 (counter clockwise) (or more) for rotation
        player.setRotation(player.getRotation().rotate(direction));
        gameScreen.updatePlayerRotation(player.getPlayerNumber()-1, player.getRotation());
    }
    /**
     * Method which moves the player according to the type of movement belt the player is currently positioned at.
     * Checks if current location is belt, then checks each direction and bend the player could move or rotate to, to get the correct movement.
     * Called  one once in updateGameState() for yellow belts, twice for blue.
     * @param player the player which should be moved and rotated.
     */
    private void beltsMovePlayer(Player player) {
        Tile currentTile = board.getTile(player.getPosition());
        int bend = currentTile.getBendDirection();
        Direction dir = currentTile.getMovementDirection();
        if (board.getTile(player.getPosition()).isBelt()) {
            if (dir == Direction.NORTH) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
            else if (dir == Direction.SOUTH) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
            else if (dir == Direction.WEST) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);

            }
            else if (dir == Direction.EAST) {
                if (bend == 1) {
                    rotatePlayer(player, -1);
                    movePlayerInDirection(player, dir);
                }
                else if (bend == 2) {
                    rotatePlayer(player, 1);
                    movePlayerInDirection(player, dir);
                }
                else movePlayerInDirection(player, dir);
            }
        }
    }

    /**
     * In this method we check if the player is currently on cog tile.
     * If true, the player will then start rotating according to the two types of rotation tiles.
     * @param player the player which should be rotated.
     */
    private void rotationCogs(Player player) {
        Tile currentTile = board.getTile(player.getPosition());
        if (board.getTile(player.getPosition()).isCog()) {
            int rotation = currentTile.getRotation();
            if (rotation == 1) {
                rotatePlayer(player, 1);
            } else if (rotation == -1) {
                rotatePlayer(player, -1);
            }
        }
    }

    /**
     * Checks what number is on the flag the player is standing on
     * checks if this corresponds to the next flag the player should visit
     * registers the flag in player.registerFlag() and checks if the player has all the flags
     *
     * if the player does, the win screen shows.
     *
     * @param player the player we're comparing values for.
     *
     */
    private void registerFlag(Player player) {
        if(player.registerFlag(board.getTile(player.getPosition()).getFlagNum())
                && player.getNextFlag()>board.getFlags())
            gameScreen.playerWins();
    }

    public void backwardMovement(Player player) {
        movePlayerInDirection(player, player.getRotation().opposite());
    }

    public void forwardMovement(Player player) {
        movePlayerInDirection(player, player.getRotation());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * loads the board and gets the dimensions of it for future use within GameLogic.
     *
     * Currently uses a hardcoded board, future implementations might accept a parameter which determines which of the
     * boards to load.
     */
    private void loadBoard(String filePath){
        board = new Board(filePath);
        boardWidth = board.getBoardWidth();
        boardHeight = board.getBoardHeight();
    }

    /**
     * Checks if any player has gone into a pit or off the board. Kills the player if that is the case.
     *
     * Should be called whenever players move or can be moved. so MOVEBOARD and MVOEPLAYER in updateGameState
     */
    private void killIfOffBoard() {
        for (Player player : players) {
            if (player.isDead()) continue;

            // Check if player is in hole or went of board as a result of moving or being pushed by players or board
            Vector2 position = player.getPosition();
            Tile playerTile = board.getTile(position);

            if (position.x >= boardWidth-1 || position.y >= boardHeight-1 || position.x <= 0 || position.y <= 0 || playerTile.isHole()) {
                player.updateHealth(-20);
                if (player.getPlayerNumber() == 1) {
                    System.out.println("You died :(");
                } else {
                    System.out.println("Player " + player.getPlayerNumber() + " died");
                }
            }
        }
    }

    /**
     * returns the Vector2 x,y coordinate of a position in the moveDir direction
     *
     * @param position the position we're starting from
     * @param moveDir the direction we're moving to
     * @return the position one step in that direction
     */
    private Vector2 getDirectionalPosition(Vector2 position, Direction moveDir) {
        switch(moveDir) {
            case NORTH:
                return new Vector2(position.x,position.y+1);
            case EAST:
                return new Vector2(position.x+1,position.y);
            case SOUTH:
                return new Vector2(position.x,position.y-1);
            case WEST:
                return new Vector2(position.x-1,position.y);
            default:
                System.out.println("Incorrect direction given in getDirectionalPosition(), returning currentPos");
                return position;
        }
    }

    public void killPlayer(Player player) {
        queue.removeIf(move -> move.getPlayerNumber()==player.getPlayerNumber()); // remove moves from this phase
        gameScreen.hidePlayer(player); // hide the player
    }
}
