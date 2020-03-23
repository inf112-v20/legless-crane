package roborally.board;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * We are aware that there is functionality in TiledMapLoader, and Tiled which allows us to do this a different way.
 *
 * While we'll likely switch to that way of reading in the game board, we are not prioritizing it currently as this
 * implementation works well for our purposes (if a bit bloated with switch-cases). We will likely focus more on
 * adding other criteria for the MVP than cleaning up this code for the foreseeable future
 *
 * In short, Board contains all tiles on the map, methods to read the map from a xml/tmx file, and other relevant
 * data like boardHeight, boardWidth, boardSize and a list of spawnpoints
 **/

public class Board {
    private int boardWidth;
    private int boardHeight;
    private int boardSize;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final Vector2[] spawnPoints = new Vector2[8];
    private Vector2[] flags = new Vector2[4];

    /**
     * TODO Check wording here
     * The constructor first reads the board from file using readBoard()
     *
     * following this it populates our ArrayList which will contain all tiles, with empty tiles.
     *
     * once tiles is filled, the elements which are not empty tiles are placed in their proper position in tiles.
     *
     * Finally, due to how we populate this list (0,0 is at the top left corner instead of bottom left), we move the
     * tiles in our List after reading in all tiles to their correct x,y position so rendered tiles match tiles used in
     * game logic.
     *
     * @param filePath the path to the file we are reading the board from.
     *
     */
    public Board(String filePath) {
        ArrayList<int[]> boardWithLayers = readBoard(filePath);

        for (int i = 0; i < boardSize; i++)
            tiles.add(new Tile.Builder().build());

        fillBoard(boardWithLayers);

        for(int i = 0, j = tiles.size() - 1; i < j; i++)
            tiles.add(i, tiles.remove(j));

        for (int i = 0; i < tiles.size(); i += boardWidth) {
            int endOfBlock = Math.min(i + boardWidth, tiles.size());
            for (int j = i, k = endOfBlock - 1; j < k; j++, k--) {
                Tile temp = tiles.get(j);
                tiles.set(j, tiles.get(k));
                tiles.set(k, temp);
            }
        }
    }

    public Tile getTile(Vector2 pos) {
        return tiles.get((int) (pos.x + pos.y*boardWidth));
    }
    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public Vector2 getSpawnPoints(int i) {
        return spawnPoints[i];
    }

    /**
     * parses the .tmx file (xml) and gets relevant data about the board like boardHeight, boardWidth etc.
     *
     * We then get each layer of the board, which is essentially a String representing a list of numbers, by removing
     * spaces and utilizing .split() we can get a String array of each layer on the board
     *
     * We then turn this into an int array to make it easier to read from later. The ArrayList of these int arrays
     * which represent the board are then returned.
     *
     * @param filePath the path of the file we're reading the board from
     * @return the ArrayList representing the board and every layer on it.
     */
    private ArrayList<int[]> readBoard(String filePath) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(filePath));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("Issue reading in board, check the method readBoard() in Board.java");
        }

        NodeList nodeList = document.getElementsByTagName("layer");
        Element el = (Element)nodeList.item(0);
        boardWidth = Integer.parseInt(el.getAttribute("width"));
        boardHeight = Integer.parseInt(el.getAttribute("height"));
        boardSize = boardWidth* boardHeight;


        ArrayList<String[]> stringLayers = new ArrayList<>();

        //for every layer, split the layer into a list of strings without spaces (each element in list is a TileID)
        for (int i = 0; i<11;i++)
            stringLayers.add(document.getElementsByTagName("layer").item(i).getTextContent()
                    .replaceAll("\\s","").split(","));


        ArrayList<int[]> listOfLayers = new ArrayList<>();
        // turning layer into list of ints instead of list of strings, and putting each layer into ArrayList
        for (String[] layer : stringLayers) {
            int[] tempArray = new int[layer.length];
            for (int i = 0; i < layer.length; i++)
                tempArray[i] = Integer.parseInt(layer[i]);
            listOfLayers.add(tempArray);
        }
        // returning ArrayList of layers.
        return listOfLayers;
    }

    /**
     * method that loops through every tile on the board and checks if a property should be added to the tile in that
     * position on the board.
     *
     * Each TileID (number in listOfLayers.get(i)[j]) represents an element on the board, an appropriate attribute is
     * assigned the newTile which is being built by the Tile.Builder.
     *
     * If no relevant tileID is found, a basic "empty" Tile is made, if several are found, the Tile will contain all
     * these properties (intended) i.e. a Tile which has both a wall and a cog, will be able to both block movement and
     * rotate the player in it's designated directions.
     *
     * For easier readability the reading of each layer has been split into it's own method. This is a temporary measure
     * which might be replaced by enums or the replacement of Board in it's entirety.
     *
     * @param listOfLayers the ArrayList of integer arrays containing all tileIDs
     */
    private void fillBoard(ArrayList<int[]> listOfLayers){
        for (int i = 0; i<boardSize-1; i++) {
            // for every tile on board
            Tile.Builder newTile = new Tile.Builder();

            // check if there are any elements on this tile for each layer
            newTile = readHole(listOfLayers.get(1)[i], newTile);
            newTile = readWrenches(listOfLayers.get(2)[i], newTile);
            newTile = readYellowBelts(listOfLayers.get(3)[i], newTile);
            newTile = readBlueBelts(listOfLayers.get(4)[i], newTile);
            newTile = readCogs(listOfLayers.get(5)[i], newTile);
            //TODO Add lasers and beam for layer 6 & 7.
            newTile = readSpawns(listOfLayers.get(8)[i], newTile, i);
            newTile = readFlags(listOfLayers.get(9)[i], newTile, i);
            newTile = readWalls(listOfLayers.get(10)[i], newTile);

            // add the built Tile object to the board
            tiles.set(i, newTile.build());
        }
    }

    /**
     * The readXYZ() methods in this class follow the same structure:
     *
     * Check if the tileID is 0 ( no extra properties to be added from this layer ) if not, check which properties
     * to add if any
     *
     * Finally return the Tile with or without modifications, so it can be checked against future layers before being
     * built.
     *
     * @param tileID the ID each tile has in the .tmx file
     * @param newTile the Tile we are currently building
     * @return the tile we either have or have not added properties to.
     */
    private Tile.Builder readHole(int tileID, Tile.Builder newTile) {
        // if bigger holes will get implemented, this method will likely be expanded
        if (tileID == 0)
            return newTile;
        if (tileID == 6)
            newTile.setKiller();
        else
            System.out.println("Did not recognize TileID when checking for holes - ID: " + tileID);
        return newTile;
    }

    private Tile.Builder readWrenches(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            // pretty sure there is some distinction here between the two, which we have yet to implement.
            case 7: newTile.setRepairAndBackup(); // wrench and hammer
                break;
            case 15: newTile.setRepairAndBackup(); // wrench
                break;
            default:
                System.out.println("Did not recognize TileID when checking for wrenches - ID: " + tileID);
        }
        return newTile;
    }

    private Tile.Builder readCogs(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 53:
                newTile.setRotation(-1); // red, counter-clockwise cogs
                break;
            case 54:
                newTile.setRotation(1); // green, clockwise cogs
                break;
            default:
                System.out.println("Did not recognize TileID when checking for cogs - ID: " + tileID);
        }
        return newTile;
    }

    /**
     * readFlags and readSpawns deviate from the structure of other readXYZ methods
     * this is mostly due to having to register the position of each flag or spawn point.
     *
     * We might change how this is handled in the future
     *
     * @param tileID the ID each tile has in the .tmx file
     * @param newTile the Tile we are currently building
     * @param i the position in list of this tile
     * @return the tile we either have or have not added properties to.
     */
    private Tile.Builder readFlags(int tileID, Tile.Builder newTile, int i) {

        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 55: newTile.setFlag(1); // flag number 1
                flags[0] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 63: newTile.setFlag(2); // flag number 2
                flags[1] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 71: newTile.setFlag(3); // flag number 3
                flags[2] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 79: newTile.setFlag(4); // flag number 4
                flags[3] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            default:
                System.out.println("Did not recognize TileID when checking for flags - ID: " + tileID);
        }
        return newTile;
    }

    private Tile.Builder readSpawns(int tileID, Tile.Builder newTile, int i) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 121: newTile.setSpawner(); // spawn player 1
                spawnPoints[0] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 122: newTile.setSpawner(); // spawn player 2
                spawnPoints[1] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 123: newTile.setSpawner(); // spawn player 3
                spawnPoints[2] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 124: newTile.setSpawner(); // spawn player 4
                spawnPoints[3] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 129: newTile.setSpawner(); // spawn player 5
                spawnPoints[4] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 130: newTile.setSpawner(); // spawn player 6
                spawnPoints[5] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 131: newTile.setSpawner(); // spawn player 7
                spawnPoints[6] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            case 132: newTile.setSpawner(); // spawn player 8
                spawnPoints[7] = new Vector2(i%boardWidth, boardHeight -(i / boardWidth) -1);
                break;
            default:
                System.out.println("Did not recognize TileID when checking for spawnpoints - ID: " + tileID);
        }
        return newTile;
    }

    private Tile.Builder readWalls(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 8:
                newTile.setBlocker(new Direction[]{Direction.EAST, Direction.SOUTH});  // southeast wall, corner
                break;
            case 16:
                newTile.setBlocker(new Direction[]{Direction.NORTH, Direction.EAST}); // northeast wall, corner
                break;
            case 23:
                newTile.setBlocker(new Direction[]{Direction.EAST}); // eastern wall
                break;
            case 24:
                newTile.setBlocker(new Direction[]{Direction.NORTH, Direction.WEST}); // northwest wall, corner
                break;
            case 29:
                newTile.setBlocker(new Direction[]{Direction.SOUTH}); // southern wall
                break;
            case 30:
                newTile.setBlocker(new Direction[]{Direction.WEST}); // western wall
                break;
            case 31:
                newTile.setBlocker(new Direction[]{Direction.NORTH}); // northern wall
                break;
            case 32:
                newTile.setBlocker(new Direction[]{Direction.SOUTH, Direction.WEST}); // southwest wall, corner
                break;
            default:
                System.out.println("Did not recognize TileID when checking for walls - ID: " + tileID);
        }
        return newTile;
    }

    private Tile.Builder readYellowBelts(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 33:
                newTile.setCornerBelt(Direction.SOUTH,1); // east to south, bend
                break;
            case 34:
                newTile.setCornerBelt(Direction.WEST,1); // south to west, bend
                break;
            case 35:
                newTile.setCornerBelt(Direction.EAST,1); // south to east, bend
                break;
            case 36:
                newTile.setCornerBelt(Direction.SOUTH,1); // west to south, bend
                break;
            case 41:
                newTile.setCornerBelt(Direction.EAST,1); // north to east, bend
                break;
            case 42:
                newTile.setCornerBelt(Direction.NORTH,1); // west to north, bend
                break;
            case 43:
                newTile.setCornerBelt(Direction.NORTH,1); // east to north, bend
                break;
            case 44:
                newTile.setCornerBelt(Direction.WEST,1); // north to west, bend
                break;
            case 49:
                newTile.setStraightBelt(Direction.NORTH,1); // going north, straight
                break;
            case 50:
                newTile.setStraightBelt(Direction.SOUTH,1); // going south, straight
                break;
            case 51:
                newTile.setStraightBelt(Direction.WEST,1); // going west, straight
                break;
            case 52:
                newTile.setStraightBelt(Direction.EAST,1); // going east, straight
                break;
            case 57:
                newTile.setMergingLanes(Direction.NORTH, 1,
                        new Direction[]{Direction.SOUTH, Direction.WEST}); // south & west to north, merge
                break;
            case 58:
                newTile.setMergingLanes(Direction.EAST, 1,
                        new Direction[]{Direction.NORTH, Direction.WEST}); // north & west to east, merge
                break;
            case 59:
                newTile.setMergingLanes(Direction.SOUTH, 1,
                        new Direction[]{Direction.NORTH, Direction.EAST}); // north & east to south, merge
                break;
            case 60:
                newTile.setMergingLanes(Direction.WEST, 1,
                        new Direction[]{Direction.SOUTH, Direction.EAST}); // south & east to west, merge
                break;
            case 61:
                newTile.setMergingLanes(Direction.EAST, 1,
                        new Direction[]{Direction.NORTH, Direction.SOUTH}); // north & south to east, merge
                break;
            case 62:
                newTile.setMergingLanes(Direction.SOUTH, 1,
                        new Direction[]{Direction.EAST, Direction.WEST}); // east & west to south, merge
                break;
            case 65:
                newTile.setMergingLanes(Direction.NORTH, 1,
                        new Direction[]{Direction.SOUTH, Direction.EAST}); // south & east to north, merge
                break;
            case 66:
                newTile.setMergingLanes(Direction.EAST, 1,
                        new Direction[]{Direction.SOUTH, Direction.WEST}); // south & west to east, merge
                break;
            case 67:
                newTile.setMergingLanes(Direction.SOUTH, 1,
                        new Direction[]{Direction.NORTH, Direction.WEST}); // north & west to south, merge
                break;
            case 68:
                newTile.setMergingLanes(Direction.WEST, 1,
                        new Direction[]{Direction.NORTH, Direction.EAST}); // north & east to west, merge
                break;
            case 69:
                newTile.setMergingLanes(Direction.NORTH, 1,
                        new Direction[]{Direction.EAST, Direction.WEST}); // east & west to north, merge
                break;
            case 70:
                newTile.setMergingLanes(Direction.WEST, 1,
                        new Direction[]{Direction.NORTH, Direction.SOUTH}); // north & south to west, merge
                break;
            default:
                System.out.println("Did not recognize TileID when checking for yellow belts - ID: " + tileID);
        }
        return newTile;
    }

    private Tile.Builder readBlueBelts(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            // blue belts
            case 17:
                newTile.setCornerBelt(Direction.SOUTH,2); // east to south, bend
                break;
            case 18:
                newTile.setCornerBelt(Direction.WEST,2); // south to west, bend
                break;
            case 19:
                newTile.setCornerBelt(Direction.EAST,2); // south to east, bend
                break;
            case 20:
                newTile.setCornerBelt(Direction.SOUTH,2); // west to south, bend
                break;
            case 25:
                newTile.setCornerBelt(Direction.EAST,2); // north to east, bend
                break;
            case 26:
                newTile.setCornerBelt(Direction.NORTH,2); // west to north, bend
                break;
            case 27:
                newTile.setCornerBelt(Direction.NORTH,2); // east to north, bend
                break;
            case 28:
                newTile.setCornerBelt(Direction.WEST,2); // north to west, bend
                break;
            case 13:
                newTile.setStraightBelt(Direction.NORTH,2); // going north, straight
                break;
            case 21:
                newTile.setStraightBelt(Direction.SOUTH,2); // going south, straight
                break;
            case 22:
                newTile.setStraightBelt(Direction.WEST,2); // going west, straight
                break;
            case 14:
                newTile.setStraightBelt(Direction.EAST,2); // going east, straight
                break;
            case 73:
                newTile.setMergingLanes(Direction.NORTH, 2,
                        new Direction[]{Direction.SOUTH, Direction.WEST}); // south & west to north, merge
                break;
            case 74:
                newTile.setMergingLanes(Direction.EAST, 2,
                        new Direction[]{Direction.NORTH, Direction.WEST}); // north & west to east, merge
                break;
            case 75:
                newTile.setMergingLanes(Direction.SOUTH, 2,
                        new Direction[]{Direction.NORTH, Direction.EAST}); // north & east to south, merge
                break;
            case 76:
                newTile.setMergingLanes(Direction.WEST, 2,
                        new Direction[]{Direction.SOUTH, Direction.EAST}); // south & east to west, merge
                break;
            case 81:
                newTile.setMergingLanes(Direction.EAST, 2,
                        new Direction[]{Direction.NORTH, Direction.SOUTH}); // north & south to east, merge
                break;
            case 82:
                newTile.setMergingLanes(Direction.SOUTH, 2,
                        new Direction[]{Direction.EAST, Direction.WEST}); // east & west to south, merge
                break;
            case 77:
                newTile.setMergingLanes(Direction.NORTH, 2,
                        new Direction[]{Direction.SOUTH, Direction.EAST}); // south & east to north, merge
                break;
            case 78:
                newTile.setMergingLanes(Direction.EAST, 2,
                        new Direction[]{Direction.SOUTH, Direction.WEST}); // south & west to east, merge
                break;
            case 86:
                newTile.setMergingLanes(Direction.SOUTH, 2,
                        new Direction[]{Direction.NORTH, Direction.WEST}); // north & west to south, merge
                break;
            case 85:
                newTile.setMergingLanes(Direction.WEST, 2,
                        new Direction[]{Direction.NORTH, Direction.EAST}); // north & east to west, merge
                break;
            case 84:
                newTile.setMergingLanes(Direction.NORTH, 2,
                        new Direction[]{Direction.EAST, Direction.WEST}); // east & west to north, merge
                break;
            case 83:
                newTile.setMergingLanes(Direction.WEST, 2,
                        new Direction[]{Direction.NORTH, Direction.SOUTH}); // north & south to west, merge
                break;
            default:
                System.out.println("Did not recognize TileID when checking for blue belts - ID: " + tileID);
        }
        return newTile;
    }
}