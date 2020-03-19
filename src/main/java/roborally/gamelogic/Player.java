package roborally.gamelogic;

import com.badlogic.gdx.math.Vector2;
import roborally.board.Direction;
import roborally.gamelogic.GameLogic;


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

    public void setBackupPoint(Vector2 newBackupPoint) {
        backupPoint = newBackupPoint;
    }



    public void handleDamage(int damage) {
        // this method should be called by GameLogic every "tick" to check if any players are on lasers or holes.
        // if they're on such a tile, call this method for that player.

    health = health-damage;
       if (health < 0){
           doRespawn();
       }
        // if health below 0, lose life
        // when player dies, respawn at backup
    }

    private boolean lifeStatusIsOk() {
        if (lives < 1){
            // game over
            return false;
        } return true;
    }

    private void doRespawn() {
        if (lifeStatusIsOk()) {
            lives -= 1;
            gameLogic.updatePlayerPosition(this, null, backupPoint);
        } else {
            //TODO: game over
        }
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
