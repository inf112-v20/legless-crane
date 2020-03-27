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
    private static final int MAX_HEALTH = 9;
    private final ArrayList<Integer> flag;
    private Vector2 position;
    private Direction rotation;
    private Vector2 backupPoint;
    private final int playerNumber;
    private final GameLogic gameLogic;
    private int nextFlag;

    public Player(int playerNumber, Vector2 spawnPoint, GameLogic gameLogic) {
        this.lives = 3;
        this.health = MAX_HEALTH;
        this.flag = new ArrayList<>();
        this.position = spawnPoint;
        this.backupPoint = spawnPoint;
        this.playerNumber = playerNumber;
        this.rotation = Direction.NORTH;
        this.gameLogic = gameLogic;
        this.nextFlag = 1;
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

        if (health < 0) {
            lives -= 1;
            health = MAX_HEALTH;
            gameLogic.respawnPlayer(this);
        }
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    /**
     * checks what the next flag the player should visit is.
     * If the flag number is too high or low, do nothing.
     * @param flagNum the number of the flag to register
     * @return whether or not this flag number is the next in order for the player.
     */
    public boolean registerFlag(int flagNum) {
        if (nextFlag==flagNum) {
            nextFlag++;
            return true;
        }
        return false;
    }

    public int getNextFlag() {
        return nextFlag;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getBackupPoint() {
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
}
