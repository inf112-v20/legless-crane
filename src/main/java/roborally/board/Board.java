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
    private int boardHeight;
    private int boardSize;
    private ArrayList<TileObject> tiles = new ArrayList<>();
    private ArrayList<int[]> boardWithLayers = new ArrayList<>();

    public Board(File file) throws ParserConfigurationException, IOException, SAXException {
        readBoardFromFile(file);

        for (int i = 0; i < boardSize; i++) { tiles.add(new TileObject.TileBuilder().build()); } // standard empty tile

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
                TileObject temp = tiles.get(j);
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
        boardHeight = Integer.parseInt(el.getAttribute("height"));
        boardSize = boardWidth*boardHeight;

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

    public int getBoardWidth() {return boardWidth;}

    public int getBoardHeight() {return boardHeight;}

    public TileObject get(int x, int y) { return tiles.get(x + y*boardWidth); } // no set method currently

    private void readBoard(ArrayList<int[]> input){
        for (int i = 0; i<boardSize-1; i++) {
            // for every tile on board
            for (int j = 1; j < input.size(); j++) {
                // read in from every layer.
                //TODO, check if there is already a tile at the i position? can we add to that tile's functionality?
                // might be better to have a bunch of if statements since switch-case is
                // either or.
                if (input.get(j)[i] != 0) {
                    // if not "empty", check if the ID matches:
                    switch (input.get(j)[i]) {
                        case 6:
                            tiles.set(i, new TileObject.TileBuilder().setKiller().build());
                            break;
                        case 7:
                            tiles.set(i, new TileObject.TileBuilder().setRepairAndBackup().build()); // wrench and hammer
                            break;
                        case 15:
                            tiles.set(i, new TileObject.TileBuilder().setRepairAndBackup().build()); // wrench
                            break;
                        case 53:
                            tiles.set(i, new TileObject.TileBuilder().setRotation(-1).build()); // red, counter-clockwise cogs
                            break;
                        case 54:
                            tiles.set(i, new TileObject.TileBuilder().setRotation(1).build()); // green, clockwise cogs
                            break;

                        case 55:
                            tiles.set(i, new TileObject.TileBuilder().setFlag(1).build()); // flag number 1
                            break;
                        case 63:
                            tiles.set(i, new TileObject.TileBuilder().setFlag(2).build()); // flag number 2
                            break;
                        case 71:
                            tiles.set(i, new TileObject.TileBuilder().setFlag(3).build()); // flag number 3
                            break;
                        case 79:
                            tiles.set(i, new TileObject.TileBuilder().setFlag(4).build()); // flag number 4
                            break;

                        case 121:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 1
                            break;
                        case 122:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 2
                            break;
                        case 123:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 3
                            break;
                        case 124:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 4
                            break;
                        case 129:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 5
                            break;
                        case 130:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 6
                            break;
                        case 131:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 7
                            break;
                        case 132:
                            tiles.set(i, new TileObject.TileBuilder().setSpawner().build()); // spawn player 8
                            break;

                        case 8:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.EAST, Direction.SOUTH}).build());  // southeast wall, corner
                            break;
                        case 16:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.NORTH, Direction.EAST}).build()); // northeast wall, corner
                            break;
                        case 23:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.EAST}).build()); // eastern wall
                            break;
                        case 24:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.NORTH, Direction.WEST}).build()); // northwest wall, corner
                            break;
                        case 29:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.SOUTH}).build()); // southern wall
                            break;
                        case 30:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.WEST}).build()); // western wall
                            break;
                        case 31:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.NORTH}).build()); // northern wall
                            break;
                        case 32:
                            tiles.set(i, new TileObject.TileBuilder().setBlocker(
                                    new Direction[]{Direction.SOUTH, Direction.WEST}).build()); // southwest wall, corner
                            break;

                        // yellow belts
                        case 33:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    1).build()); // east to south, bend
                            break;
                        case 34:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    1).build()); // south to west, bend
                            break;
                        case 35:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    1).build()); // south to east, bend
                            break;
                        case 36:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    1).build()); // west to south, bend
                            break;
                        case 41:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    1).build()); // north to east, bend
                            break;
                        case 42:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    1).build()); // west to north, bend
                            break;
                        case 43:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    1).build()); // east to north, bend
                            break;
                        case 44:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    1).build()); // north to west, bend
                            break;
                        case 49:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    1).build()); // going north, straight
                            break;
                        case 50:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    1).build()); // going south, straight
                            break;
                        case 51:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    1).build()); // going west, straight
                            break;
                        case 52:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    1).build()); // going east, straight
                            break;
                        case 57:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 1,
                                    new Direction[]{Direction.SOUTH, Direction.WEST}).build()); // south & west to north, merge
                            break;
                        case 58:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 1,
                                    new Direction[]{Direction.NORTH, Direction.WEST}).build()); // north & west to east, merge
                            break;
                        case 59:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 1,
                                    new Direction[]{Direction.NORTH, Direction.EAST}).build()); // north & east to south, merge
                            break;
                        case 60:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 1,
                                    new Direction[]{Direction.SOUTH, Direction.EAST}).build()); // south & east to west, merge
                            break;
                        case 61:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 1,
                                    new Direction[]{Direction.NORTH, Direction.SOUTH}).build()); // north & south to east, merge
                            break;
                        case 62:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 1,
                                    new Direction[]{Direction.EAST, Direction.WEST}).build()); // east & west to south, merge
                            break;
                        case 65:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 1,
                                    new Direction[]{Direction.SOUTH, Direction.EAST}).build()); // south & east to north, merge
                            break;
                        case 66:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 1,
                                    new Direction[]{Direction.SOUTH, Direction.WEST}).build()); // south & west to east, merge
                            break;
                        case 67:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 1,
                                    new Direction[]{Direction.NORTH, Direction.WEST}).build()); // north & west to south, merge
                            break;
                        case 68:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 1,
                                    new Direction[]{Direction.NORTH, Direction.EAST}).build()); // north & east to west, merge
                            break;
                        case 69:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 1,
                                    new Direction[]{Direction.EAST, Direction.WEST}).build()); // east & west to north, merge
                            break;
                        case 70:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 1,
                                    new Direction[]{Direction.NORTH, Direction.SOUTH}).build()); // north & south to west, merge

                            // blue belts

                            break;
                        case 17:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    2).build()); // east to south, bend
                            break;
                        case 18:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    2).build()); // south to west, bend
                            break;
                        case 19:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    2).build()); // south to east, bend
                            break;
                        case 20:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    2).build()); // west to south, bend
                            break;
                        case 25:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    2).build()); // north to east, bend
                            break;
                        case 26:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    2).build()); // west to north, bend
                            break;
                        case 27:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    2).build()); // east to north, bend
                            break;
                        case 28:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    2).build()); // north to west, bend
                            break;
                        case 13:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.NORTH,
                                    2).build()); // going north, straight
                            break;
                        case 21:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.SOUTH,
                                    2).build()); // going south, straight
                            break;
                        case 22:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.WEST,
                                    2).build()); // going west, straight
                            break;
                        case 14:
                            tiles.set(i, new TileObject.TileBuilder().setMover(Direction.EAST,
                                    2).build()); // going east, straight
                            break;
                        case 73:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 2,
                                    new Direction[]{Direction.SOUTH, Direction.WEST}).build()); // south & west to north, merge
                            break;
                        case 74:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 2,
                                    new Direction[]{Direction.NORTH, Direction.WEST}).build()); // north & west to east, merge
                            break;
                        case 75:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 2,
                                    new Direction[]{Direction.NORTH, Direction.EAST}).build()); // north & east to south, merge
                            break;
                        case 76:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 2,
                                    new Direction[]{Direction.SOUTH, Direction.EAST}).build()); // south & east to west, merge
                            break;
                        case 81:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 2,
                                    new Direction[]{Direction.NORTH, Direction.SOUTH}).build()); // north & south to east, merge
                            break;
                        case 82:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 2,
                                    new Direction[]{Direction.EAST, Direction.WEST}).build()); // east & west to south, merge
                            break;
                        case 77:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 2,
                                    new Direction[]{Direction.SOUTH, Direction.EAST}).build()); // south & east to north, merge
                            break;
                        case 78:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.EAST, 2,
                                    new Direction[]{Direction.SOUTH, Direction.WEST}).build()); // south & west to east, merge
                            break;
                        case 86:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.SOUTH, 2,
                                    new Direction[]{Direction.NORTH, Direction.WEST}).build()); // north & west to south, merge
                            break;
                        case 85:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 2,
                                    new Direction[]{Direction.NORTH, Direction.EAST}).build()); // north & east to west, merge
                            break;
                        case 84:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.NORTH, 2,
                                    new Direction[]{Direction.EAST, Direction.WEST}).build()); // east & west to north, merge
                            break;
                        case 83:
                            tiles.set(i, new TileObject.TileBuilder().setMergeAndMover(Direction.WEST, 2,
                                    new Direction[]{Direction.NORTH, Direction.SOUTH}).build()); // north & south to west, merge
                            break;
                        default:
                            System.out.println("TileID not found " + input.get(j)[i]);
                            // 38, 39 for laser and beam are currently not read.
                            break;
                    }
                }
            }
        }
    }
}