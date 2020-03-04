package roborally.board;
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

/**
 * Currently this works by first initializing the board with open tiles, then reading in the different
 * elements to be placed on the board.
 *
 * Might keep this separate in the future for each type of element or group types of elements if it
 * looks like it would be easier.
 *
 * First iteration utilizes an Enum to keep track of the elements on the board-
 *
 * Known BUG: as lasers and beams can share a position with other objects, the last read element will be the
 * only element stored in our board. This should be addressed when we create Tile objects (see UML for planned
 * implementation)
 *
 * It's for that same reason that we have a lot of repeated code in the readXYZ() methods in this class,
 * to better be able to create different objects with different properties to be used for game-logic     *
 *
 * something like: new Belt("Yellow", Direction.NORTH) or: new Wall ([North, West]) or w/e
 *
 */

public class Board {
    private int boardWidth;
    private int boardSize;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final ArrayList<int[]> boardWithLayers = new ArrayList<>();

    public Board(File file) throws ParserConfigurationException, IOException, SAXException {
        readBoardFromFile(file);

        for (int i = 0; i < boardSize; i++) { tiles.add(new Tile.Builder().build()); } // standard empty tile

        readBoard(boardWithLayers);


        for(int i = 0, j = tiles.size() - 1; i < j; i++) {
            tiles.add(i, tiles.remove(j));
        } // reverse arraylist, This makes the map mirrored
        // columns are in the wrong order
        // rows in the right

        // reverses column order
        for (int i = 0; i < tiles.size(); i += boardWidth) {
            int endOfBlock = Math.min(i + boardWidth, tiles.size());
            for (int j = i, k = endOfBlock - 1; j < k; j++, k--) {
                Tile temp = tiles.get(j);
                tiles.set(j, tiles.get(k));
                tiles.set(k, temp);
            }
        }
        // now the map and logic here matches what is rendered
        //TODO this seems like a super dumb way of doing this, find something better
    }

    private void readBoardFromFile(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        // reading inn the board width and height and storing it
        NodeList nodeList = document.getElementsByTagName("layer");
        Element el = (Element)nodeList.item(0);
        boardWidth = Integer.parseInt(el.getAttribute("width"));
        int boardHeight = Integer.parseInt(el.getAttribute("height"));
        boardSize = boardWidth* boardHeight;

        /*
        Getting each layer from the .tmx (xml) file, .getTextContent() gives us a String with spaces before and after
        and separating each line for the y axis.

        Removing all spaces and splitting the string into an array by utilizing .split()
         */
        ArrayList<String[]> rawBoard = new ArrayList<>();

        for (int i = 0; i<11;i++)
            rawBoard.add(document.getElementsByTagName("layer").item(i).getTextContent()
                    .replaceAll("\\s","").split(","));

        // parsing for ints on that list, and adding to boardWithLayers which is an ArrayList of int arrays containing
        // the ID for each element on each layer.
        for (String[] layer : rawBoard) {
            int[] tempArray = new int[layer.length];
            for (int i = 0; i < layer.length; i++)
                tempArray[i] = Integer.parseInt(layer[i]);
            boardWithLayers.add(tempArray);
        }
    }


    public Tile get(int x, int y) { return tiles.get(x + y*boardWidth); } // no set method currently




    private void readBoard(ArrayList<int[]> input){
        for (int i = 0; i<boardSize-1; i++) {
            // for every tile on board
            Tile.Builder newTile = new Tile.Builder();
            // create a new tile
            //TODO this looks a bit dumb, is there a better way for us to handle this?
            newTile = readHole(input.get(1)[i], newTile);
            newTile = readWrenches(input.get(2)[i], newTile);
            newTile = readYellowBelts(input.get(3)[i], newTile);
            newTile = readBlueBelts(input.get(4)[i], newTile);
            newTile = readCogs(input.get(5)[i], newTile);
            newTile = readSpawns(input.get(8)[i], newTile);
            newTile = readFlags(input.get(9)[i], newTile);
            newTile = readWalls(input.get(10)[i], newTile);
            // Should check each layer and update newTile

            tiles.set(i, newTile.build());
        }
    }
    private Tile.Builder readHole(int tileID, Tile.Builder newTile) {
        if (tileID == 6)
            newTile.setKiller();
        else if (tileID != 0)
            System.out.println("Did not recognize TileID when checking for holes - ID: " + tileID);
        return newTile;
    }
    private Tile.Builder readWrenches(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
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
    private Tile.Builder readFlags(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 55: newTile.setFlag(1); // flag number 1
                break;
            case 63: newTile.setFlag(2); // flag number 2
                break;
            case 71: newTile.setFlag(3); // flag number 3
                break;
            case 79: newTile.setFlag(4); // flag number 4
                break;
            default:
                System.out.println("Did not recognize TileID when checking for flags - ID: " + tileID);
        }
        return newTile;
    }
    private Tile.Builder readSpawns(int tileID, Tile.Builder newTile) {
        if (tileID == 0) {
            return newTile;
        }
        switch (tileID) {
            case 121: newTile.setSpawner(); // spawn player 1
                break;
            case 122: newTile.setSpawner(); // spawn player 2
                break;
            case 123: newTile.setSpawner(); // spawn player 3
                break;
            case 124: newTile.setSpawner(); // spawn player 4
                break;
            case 129: newTile.setSpawner(); // spawn player 5
                break;
            case 130: newTile.setSpawner(); // spawn player 6
                break;
            case 131: newTile.setSpawner(); // spawn player 7
                break;
            case 132: newTile.setSpawner(); // spawn player 8
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