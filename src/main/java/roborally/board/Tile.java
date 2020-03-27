package roborally.board;

public class Tile {
    private final boolean canBlockMovement;
    private final boolean doesDamage;
    private final boolean isBelt;
    private final boolean isCog;
    private final boolean isWrench;
    private final boolean isSpawner;
    private final boolean isFlag;
    private final int bendDirection;
    private final int rotation;
    private final int flagNum;
    private final int movementSpeed;
    private final Direction[] blockingDirections;
    private final Direction movementDirection;
    private final String name;
    private final int healthChange;

    /**
     * Private constructor which allows us to utilize a builder design pattern to add customized functionality to each
     * Tile object which represents every tile on the game board.
     *
     * Utilize any of the canXYZ methods to check the properties of this Tile Object
     *
     * @param builder The Builder object being built in this constructor
     */
    private Tile(Builder builder) {
        //booleans
        this.canBlockMovement=builder.canBlockMovement;
        this.doesDamage = builder.doesDamage;
        this.healthChange = builder.healthChange;
        this.isBelt = builder.isBelt;
        this.isCog =builder.canRotate;
        this.isWrench = builder.isWrench;
        this.isSpawner=builder.isSpawner;
        this.isFlag=builder.isFlag;

        //ints
        this.bendDirection=builder.bendDirection;
        this.rotation=builder.rotation;
        this.flagNum=builder.flagNum;
        this.movementSpeed=builder.movementSpeed;

        //directions
        this.blockingDirections=builder.blockingDirections;
        this.movementDirection=builder.movementDirection;
        this.name=builder.name;
    }
    // getters
    public boolean canBlockMovement() { return canBlockMovement; }

    public String toString() {
        return name;
    }

    public boolean doesDamage() { return doesDamage; }

    public boolean isBelt() { return isBelt; }

    public boolean isCog() { return isCog; }

    public boolean isWrench() { return isWrench; }

    public boolean isSpawner() { return isSpawner; }

    public boolean isFlag() { return isFlag; }

    public int getMovementSpeed() { return movementSpeed; }

    public int getBendDirection() { return bendDirection; }

    public int getRotation() { return rotation; } //TODO Remove if not used

    public int getFlagNum() { return flagNum; } //TODO Remove if not used

    public Direction[] getBlockingDirections() { return blockingDirections; }

    public Direction getMovementDirection() { return movementDirection; } //TODO Remove if not used

    public int getHealthChange() { return healthChange; }

    /**
     * By calling this class using Tile.Builder() we can add functionality to it using the different set methods
     * this way we allow for tiles with different properties where elements on the board overlap. Meaning we do not
     * need a hybrid WallAndBelt Tile Object for each case where they would overlap (this would not be feasible)
     *
     * To create a Tile Object, call "new Tile.Builder()" to get the builder class
     * add to it using any of the set methods like .setBlocker() .setRotation() and so on
     * finally call the .build() method to call the private constructor of the Tile Class.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {
        private boolean canBlockMovement;
        private boolean doesDamage;
        private Direction[] blockingDirections; // directions blocked by this tile
        private Direction movementDirection;
        private boolean isBelt;
        private int rotation;
        private boolean canRotate;
        private boolean isWrench;
        private boolean isFlag;
        private int flagNum;
        private boolean isSpawner;
        private int bendDirection;
        private int movementSpeed;
        private String name;
        private int healthChange;

        public Builder setBlocker(Direction[] blockingDirections) {
            this.canBlockMovement = true;
            this.blockingDirections = blockingDirections;
            this.name = "blocker";
            return this;
        }

        public Builder setCog(int rotation) {
            // +1, +2, +3 for clockwise rotation
            // -1, -2, -3 for counter-clockwise rotation
            this.canRotate = true;
            this.rotation = rotation;
            return this;
        }

        public Builder setWrench() {
            // might be smart to merge this and setSpawner() ? are these always the same?
            this.isWrench = true;
            this.healthChange = 1;
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
            this.isBelt = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder setCornerBelt(Direction movementDirection, int movementSpeed, int bendDirection) {
            //TODO How to determine which direction to rotate?
            this.movementSpeed = movementSpeed;
            this.bendDirection = bendDirection;
            this.isBelt = true;
            this.canRotate = true;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder setMergingBelt(Direction movementDirection, int movementSpeed, Direction[] fromDirections) {
            this.isBelt = true;
            this.movementSpeed = movementSpeed;
            this.movementDirection = movementDirection;
            return this;
        }

        public Builder damagePlayer(int healthChange) {
            this.doesDamage = true;
            this.healthChange = healthChange;
            return this;
        }

        public Tile build(){
            return new Tile(this);
        }


    }
}
