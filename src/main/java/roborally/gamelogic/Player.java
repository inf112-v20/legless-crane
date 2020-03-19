package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Direction;

public class Player {
    private int lives;
    private int health;
    private Vector2 position;
    private Direction rotation;
    private Vector2 backupPoint;
    private final int playerNumber;

    public Player(int playerNumber, Vector2 spawnPoint) {
        this.lives = 3;
        this.health = 9;
        this.position = spawnPoint;
        this.backupPoint = spawnPoint;
        this.playerNumber = playerNumber;
        this.rotation = Direction.NORTH;
    }

    public void setBackupPoint(Vector2 newBackupPoint) {
        backupPoint = newBackupPoint;
    }


    public void handleDamage(int damage) {
        // this method should be called by GameLogic every "tick" to check if any players are on lasers or holes.
        // if they're on such a tile, call this method for that player.
        lives -= 1;

        health = health-damage;
        // if health below 0, lose life
        // when player dies, respawn at backup
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
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
