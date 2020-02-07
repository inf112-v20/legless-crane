package RoboRally.board;

public enum Tile {
    /* TODO replace Enum with Tile objects

     Currently we do not have a distinction between walls facing N, E, S or W. Could be implemented but would make
     the enum pretty long. Might switch to creating Tile objects instead before then
     */
    OPEN,
    HOLE,
    WRENCH,
    BELT_YELLOW,
    BELT_BLUE,
    COG,
    BEAM,
    LASER,
    SPAWNPOINT,
    FLAG,
    WALL
}
