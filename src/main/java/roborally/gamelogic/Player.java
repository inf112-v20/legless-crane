package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Direction;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * An object which contains the relevant data of a Player, health, lives etc.
 */
public class Player {
    private int lives;
    private int health;
    private Vector2 position;
    private Direction rotation;
    private Vector2 backupPoint;
    private final int playerNumber;
    private GameLogic gameLogic;

    public Player(int playerNumber, Vector2 spawnPoint, GameLogic gameLogic) {
        this.lives = 3;
        this.health = 9;
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
        health = max(min(health+changeInHealth,9),0);
        if (health <= 0)
            respawn();
    }

    public int getHealth() {
        // not needed?
        return health;
    }

    public int getLives() {
        // not needed?
        return lives;
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
        if (lives > 1) {
            lives -= 1;
            health = 9;
            gameLogic.respawnPlayer(this);
        } else {
            //TODO: game over
        }
    }
}
