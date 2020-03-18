package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Direction;

public class Player {
    private final GameLogic gameLogic;
    private int health;
    private int lives;
    private Vector2 position;
    private Vector2 backupPoint;
    private int playerNumber;
    private Direction rotation;

    public Player(int playerNumber, Vector2 spawnPoint, GameLogic gameLogic) {
        this.playerNumber = playerNumber;
        this.gameLogic = gameLogic;
        this.position = spawnPoint;
        this.health = 9;
        this.lives = 3;
        this.backupPoint = spawnPoint;
        this.rotation = Direction.NORTH;

        //gameLogic.updateRenderingPlayer(position,Direction.NORTH);
    }

    public int handleDamage(int damage) {
        return 0;
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
