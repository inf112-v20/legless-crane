package roborally.board;

public class TileObject {
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

    private TileObject(TileBuilder builder) {
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
    public static class TileBuilder{
        //required parameters

        //optional parameters
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
        public TileBuilder() {/*Currently have no required paramaters, so this is unneeded?*/}

        public TileBuilder setBlocker(Direction[] blockingDirections) {
            this.canBlockMovement = true;
            this.blockingDirections = blockingDirections;
            this.name = "blocker";
            //System.out.println("Blocker made");
            return this;
        }



        public TileBuilder setRotation(int rotation) {
            // +1, +2, +3 for clockwise rotation
            // -1, -2, -3 for counter-clockwise rotation
            this.canRotate = true;
            this.rotation = rotation;
            return this;
        }

        public TileBuilder setRepairAndBackup() {
            // might be smart to merge this and setSpawner() some how to generalize more
            this.canRepair = true;
            this.canBackup = true;
            return this;
        }
        
        public TileBuilder setFlag(int flagNum) {
            this.isFlag = true;
            this.flagNum = flagNum;
            return this;
        }

        public TileBuilder setSpawner() {
            // might be relevant to set which player can spawn here. Depends if we use this and backup
            this.isSpawner = true;
            return this;
        }

        public TileBuilder setMover(Direction movementDirection, int movementSpeed) {
            this.movementSpeed = movementSpeed;
            this.canMovePlayer = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public TileBuilder setMoveAndRotation(Direction movementDirection, int movementSpeed) {
            //TODO How to determine which direction to rotate?
            this.movementSpeed = movementSpeed;
            this.canMovePlayer = true;
            this.canRotate = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public TileBuilder setMergeAndMover(Direction movementDirection, int movementSpeed, Direction[] fromDirections) {
            this.canMergeLanes = true;
            this.canMovePlayer = true;
            this.movementSpeed = movementSpeed;
            this.fromDirections = fromDirections;
            this.movementDirection = movementDirection;
            return this;
        }

        public TileBuilder setKiller() {
            this.canKillPlayer = true;
            return this;
        }

        public TileObject build(){
            //System.out.println(this.canBlockMovement);
            return new TileObject(this);
        }
    }
}
