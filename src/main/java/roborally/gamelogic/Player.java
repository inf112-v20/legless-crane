package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Direction;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * An object which contains the relevant data of a Player, health, lives etc.
 */
public class Player {
    private int lives;
    private int health;
    private static final int MAX_HEALTH = 10;
    private final ArrayList<Integer> flag;
    private Vector2 position;
    private Direction rotation;
    private Vector2 backupPoint;
    private final int playerNumber;
    private final GameLogic gameLogic;

    public Player(int playerNumber, Vector2 spawnPoint, GameLogic gameLogic) {
        this.lives = 3;
        this.health = MAX_HEALTH;
        this.flag = new ArrayList<>();
        this.position = spawnPoint;
        this.backupPoint = spawnPoint;
        this.playerNumber = playerNumber;
        this.rotation = Direction.NORTH;
        this.gameLogic = gameLogic;
    }

    public void setBackupPoint(Vector2 backupPoint) {
        this.backupPoint = backupPoint;
    }

    /**
     * updates the player's health and checks if it reaches 0,
     * If this happens, the player should respawn or lose the game.
     *
     * @param changeInHealth the difference (negative for damage, positive for repairing)
     */
    public void updateHealth(int changeInHealth) {
        // health is updated to somewhere between 9-0 according to changeInHealth
        health = max( min( health + changeInHealth , MAX_HEALTH) , 0);

        System.out.println("Health has been updated, current health is: " + health);
        //TODO Show this information to the player on GameScreen and not in console
        if (health <= 0)
            respawn();
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Adds a particular flag number if correctly visited by the current player.
     *
     * @param flagNumber the flag number on the tile (w/flag) being visited.
     *
     */
    public void addFlag(int flagNumber){
        if (flag.size()==2) {
            // TODO: app.setScreen(WinScreen); in GameScreen?
        } flag.add(flagNumber);
    }

    /**
     * Keeps track of how many flags which the current player has visited.
     */
    public int numberOfFlags() {
        if (flag.isEmpty()){
            return 0;
        } return flag.size();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getBackupPoint() {
        // not needed?
        return backupPoint;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Direction getRotation() {
        return rotation;
    }

    public void setRotation(Direction rotation) {
        this.rotation = rotation;
    }

    /**
     * checks if the player has lives left, if it does, it respawns with full health at it's backupPoint
     *
     * if not, nothing currently happens to it. This will change once we implement a game over screen or something.
     */
    private void respawn() {
        if (lives > 0) {
            lives -= 1;
            health = MAX_HEALTH;
            gameLogic.respawnPlayer(this);
            System.out.println("Your robot died, current lives: " + lives + " current health: " + health);
            //TODO Show this information to the player on GameScreen and not in console
        } else {
            System.out.println("Game over");
            //TODO Show this information to the player on GameScreen and not in console (app.setScreen(LoseScreen))
        }
    }
}
