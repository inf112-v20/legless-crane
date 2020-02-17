package roborally.board;
import org.w3c.dom.Document;
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
 *TODO As Tiled designates an ID to every given tile in a tilemap, investigate if this changes per map or
 * if it stays the same regardless of which map is made with it.
 * Also check if we can set this ID ourselves for IDs that make more sense for our use.
 *
 *TODO would be nice to add some long-term solutions for getting width of the board we're loading in here
 * if we ever get around to having different boards.
 *
 * Methods are currently public for testing purposes
 */

public class Board {
    private int boardWidth;
    private int boardHeight;
    private int boardSize;
    private ArrayList<Tile> grid = new ArrayList<>();

    public Board(int boardWidth, int boardHeight, File file) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        // Getting each layer from the .tmx file
        ArrayList<String> rawBoard = new ArrayList<>();
        for (int i = 0; i<11;i++)
            rawBoard.add(document.getElementsByTagName("layer").item(i).getTextContent().replaceAll("\\s",""));


        ArrayList<String[]> tempStringList = new ArrayList<>();
        ArrayList<int[]> boardWithLayers = new ArrayList<>();
        // splitting the String into a list
        for (String layer : rawBoard) { tempStringList.add(layer.split(",")); }

        // parsing for ints on that list, and adding to boardWithLayers which is an arraylist of int arrays containting
        // the ID for each element on each layer.
        // TODO there's probably a better way to store this data.
        for (String[] layer : tempStringList) {
            int[] tempArray = new int[layer.length];
            for (int i = 0; i < layer.length; i++)
                tempArray[i] = Integer.parseInt(layer[i]);
            boardWithLayers.add(tempArray);
        }

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        boardSize = boardWidth*boardHeight;

        for (int i = 0; i < boardSize; i++) {
            grid.add(Tile.OPEN);
        }
        // call each method to read in the elements stored in the different layers of the board.
        readHoles(boardWithLayers.get(1));
        readWrenches(boardWithLayers.get(2));
        readCogs(boardWithLayers.get(3));
        readYellowBelts(boardWithLayers.get(4));
        readBlueBelts(boardWithLayers.get(5));
        readBeams(boardWithLayers.get(6));
        readLasers(boardWithLayers.get(7));
        readSpawnpoints(boardWithLayers.get(8));
        readVictoryPoints(boardWithLayers.get(9));
        readWalls(boardWithLayers.get(10));

        //TODO best solution I've found to save the map and it's tiles is to do so in different layers, separated by
        // their logic, due to the overlapping nature of some maps.
        // movers in one, blockers in one, laser in one?
    }

    // TODO There is a lot of repetition in the readXYZ() methods, this should be addressed after Tile objects are in.
    private void readHoles(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i] == 6)
                grid.set(i, Tile.HOLE);
    }

    private void readWrenches(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==7)
                grid.set(i, Tile.WRENCH);
            else if (layer[i] == 15)
                grid.set(i, Tile.WRENCH);
    }

    private void readCogs(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==53)
                grid.set(i, Tile.COG);
            else if (layer[i] == 54)
                grid.set(i, Tile.COG);
    }

    private void readYellowBelts(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i] == 33)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 36)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 41)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 44)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 49)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 50)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 51)
                grid.set(i, Tile.BELT_YELLOW);
            else if (layer[i] == 52)
                grid.set(i, Tile.BELT_YELLOW);
    }

    private void readBlueBelts(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==14)
                grid.set(i, Tile.BELT_BLUE);
            else if (layer[i] == 21)
                grid.set(i, Tile.BELT_BLUE);
    }

    private void readBeams(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==39)
                grid.set(i, Tile.BEAM);
    }

    private void readLasers(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==38)
                grid.set(i, Tile.LASER);
    }

    private void readSpawnpoints(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==121)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 122)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 123)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 124)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 129)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 130)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 131)
                grid.set(i, Tile.SPAWNPOINT);
            else if (layer[i] == 132)
                grid.set(i, Tile.SPAWNPOINT);
    }


    private void readVictoryPoints(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==55)
                grid.set(i, Tile.FLAG);
            else if (layer[i] == 63)
                grid.set(i, Tile.FLAG);
            else if (layer[i] == 71)
                grid.set(i, Tile.FLAG);
    }

    private void readWalls(int[] layer) {
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i] == 8)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 16)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 23)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 24)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 29)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 30)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 31)
                grid.set(i, Tile.WALL);
            else if (layer[i] == 32)
                grid.set(i, Tile.WALL);
    }
    

    public Tile get(Position pos) {
        return grid.get(pos.getX() + pos.getY()*boardWidth);
    }

    public void set(Position pos, Tile state) {
        grid.set(pos.getX() + pos.getY()*boardWidth, state);
    }

    public int getBoardSize() {return boardSize;}

    public int getBoardWidth() {return boardWidth;}

    public int getBoardHeight() {return boardHeight;}
}