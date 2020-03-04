package roborally.board;

public class Tile {
    // No mandatory parameters for constructor yet.

    //optional parameters for constructor
    private final boolean canBlockMovement;
    private final boolean canKillPlayer;
    private final boolean canMergeLanes;
    private final boolean canMovePlayer;
    private final boolean canRotate;
    private final boolean canBackup;
    private final boolean canRepair;
    private final boolean isSpawner;
    private final boolean isFlag;
    private final int rotation;
    private final int flagNum;
    private final int movementSpeed;
    private final Direction[] blockingDirections;
    private final Direction[] fromDirections;
    private final Direction movementDirection;
    private final String name;

    private Tile(Builder builder) {
        //booleans
        this.canBlockMovement=builder.canBlockMovement;
        this.canMergeLanes=builder.canMergeLanes;
        this.canKillPlayer=builder.canKillPlayer;
        this.canMovePlayer=builder.canMovePlayer;
        this.canRotate=builder.canRotate;
        this.canBackup=builder.canBackup;
        this.canRepair=builder.canRepair;
        this.isSpawner=builder.isSpawner;
        this.isFlag=builder.isFlag;

        //ints
        this.rotation=builder.rotation;
        this.flagNum=builder.flagNum;
        this.movementSpeed=builder.movementSpeed;

        //directions
        this.blockingDirections=builder.blockingDirections;
        this.movementDirection=builder.movementDirection;
        this.fromDirections=builder.fromDirections;
        this.name=builder.name;
    }
    // getters
    public boolean canBlockMovement() { return canBlockMovement; }

    public String toString() {
        return name;
    }

    public boolean canKillPlayer() { return canKillPlayer; }

    public boolean canMergeLanes() { return canMergeLanes; }

    public boolean canMovePlayer() { return canMovePlayer; }

    public boolean canRotate() { return canRotate; }

    public boolean canBackup() { return canBackup; }

    public boolean canRepair() { return canRepair; }

    public boolean isSpawner() { return isSpawner; }

    public boolean isFlag() { return isFlag; }

    public int getMovementSpeed() { return movementSpeed; }

    public int getRotation() { return rotation; }

    public int getFlagNum() { return flagNum; }

    public Direction[] getBlockingDirections() { return blockingDirections; }

    public Direction[] getFromDirections() { return fromDirections; }

    public Direction getMovementDirection() { return movementDirection; }

    //Builder Class
    public static class Builder {
        // no required parameters

        // optional parameters
        private boolean canBlockMovement;
        private boolean canKillPlayer;
        private Direction[] blockingDirections; // directions blocked by this tile
        private Direction movementDirection;
        private boolean canMergeLanes;
        private Direction[] fromDirections;
        private boolean canMovePlayer;
        private int rotation;
        private boolean canRotate;
        private boolean canBackup;
        private boolean canRepair;
        private boolean isFlag;
        private int flagNum;
        private boolean isSpawner;
        private int movementSpeed;
        private String name;
        public Builder() {/*Currently have no required paramaters, so this is unneeded?*/}

        public Builder setBlocker(Direction[] blockingDirections) {
            this.canBlockMovement = true;
            this.blockingDirections = blockingDirections;
            this.name = "blocker";
            //System.out.println("Blocker made");
            return this;
        }

        public Builder setRotation(int rotation) {
            // +1, +2, +3 for clockwise rotation
            // -1, -2, -3 for counter-clockwise rotation
            this.canRotate = true;
            this.rotation = rotation;
            return this;
        }

        public Builder setRepairAndBackup() {
            // might be smart to merge this and setSpawner() some how to generalize more
            this.canRepair = true;
            this.canBackup = true;
            return this;
        }
        
        public Builder setFlag(int flagNum) {
            this.isFlag = true;
            this.flagNum = flagNum;
            return this;
        }

        public Builder setSpawner() {
            // might be relevant to set which player can spawn here. Depends if we use this and backup
            this.isSpawner = true;
            return this;
        }

        public Builder setStraightBelt(Direction movementDirection, int movementSpeed) {
            this.movementSpeed = movementSpeed;
            this.canMovePlayer = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder setCornerBelt(Direction movementDirection, int movementSpeed) {
            //TODO How to determine which direction to rotate?
            this.movementSpeed = movementSpeed;
            this.canMovePlayer = true;
            this.canRotate = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder setMergingLanes(Direction movementDirection, int movementSpeed, Direction[] fromDirections) {
            this.canMergeLanes = true;
            this.canMovePlayer = true;
            this.movementSpeed = movementSpeed;
            this.fromDirections = fromDirections;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder setKiller() {
            this.canKillPlayer = true;
            return this;
        }

        public Tile build(){
            //System.out.println(this.canBlockMovement);
            return new Tile(this);
        }
    }
}
