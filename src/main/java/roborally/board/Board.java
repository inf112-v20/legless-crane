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
    private ArrayList<Tile> grid = new ArrayList<>();
    private ArrayList<int[]> boardWithLayers = new ArrayList<>();

    public Board(File file) throws ParserConfigurationException, IOException, SAXException {

        // There might not be a point in storing an "Open" tile if it has no logic attached to it.
        readBoardFromFile(file);

        for (int i = 0; i < boardSize; i++) {
            grid.add(Tile.OPEN);
        }

        // call each method to read in the elements stored in the different layers of the board.
        readHoles(boardWithLayers.get(1));
        readWrenches(boardWithLayers.get(2));
        readYellowBelts(boardWithLayers.get(3));
        readBlueBelts(boardWithLayers.get(4));
        readCogs(boardWithLayers.get(5));
        readBeams(boardWithLayers.get(6));
        readLasers(boardWithLayers.get(7));
        readSpawnpoints(boardWithLayers.get(8));
        readVictoryPoints(boardWithLayers.get(9));
        readWalls(boardWithLayers.get(10));


        for(int i = 0, j = grid.size() - 1; i < j; i++) {
            grid.add(i, grid.remove(j));
        } // reverse arraylist, This makes the map mirrored
        // columns are in the wrong order
        // rows in the right

        // reverses column order
        for (int i = 0; i < grid.size(); i += boardWidth) {
            int endOfBlock = Math.min(i + boardWidth, grid.size());
            for (int j = i, k = endOfBlock - 1; j < k; j++, k--) {
                Tile temp = grid.get(j);
                grid.set(j, grid.get(k));
                grid.set(k, temp);
            }
        }
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

    public Tile get(int x, int y) {
        return grid.get(x + y*boardWidth);
    }

    public void set(int x, int y, Tile tile) {
        grid.set(x + y*boardWidth, tile);
    }

    private void readBeams(int[] layer) {
        // might be entirely unnecessary, depending on how we implement beams and lasers.
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==39) grid.set(i, Tile.BEAM);
    }

    private void readLasers(int[] layer) {
        // might be entirely unnecessary, depending on how we implement beams and lasers.
        for (int i = 0; i<boardSize-1; i++)
            if (layer[i]==38) grid.set(i, Tile.LASER);
    }
    private void readHoles(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            if (layer[i] == 6) grid.set(i, Tile.HOLE);
        }
    }

    private void readWrenches(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 7: grid.set(i, Tile.WRENCH); // wrench and hammer
                case 15: grid.set(i, Tile.WRENCH); // wrench
            }
        }
    }

    private void readCogs(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 53: grid.set(i, Tile.COG); // red, counter-clockwise cogs
                case 54: grid.set(i, Tile.COG); // green, clockwise cogs
            }
        }
    }

    private void readVictoryPoints(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 55: grid.set(i, Tile.FLAG); // flag number 1
                case 63: grid.set(i, Tile.FLAG); // flag number 2
                case 71: grid.set(i, Tile.FLAG); // flag number 3
                case 79: grid.set(i, Tile.FLAG); // flag number 4
            }

        }
    }

    private void readSpawnpoints(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch(layer[i]) {
                case 121: grid.set(i, Tile.SPAWNPOINT); // spawn player 1
                case 122: grid.set(i, Tile.SPAWNPOINT); // spawn player 2
                case 123: grid.set(i, Tile.SPAWNPOINT); // spawn player 3
                case 124: grid.set(i, Tile.SPAWNPOINT); // spawn player 4
                case 129: grid.set(i, Tile.SPAWNPOINT); // spawn player 5
                case 130: grid.set(i, Tile.SPAWNPOINT); // spawn player 6
                case 131: grid.set(i, Tile.SPAWNPOINT); // spawn player 7
                case 132: grid.set(i, Tile.SPAWNPOINT); // spawn player 8
            }
        }
    }

    private void readWalls(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 8: grid.set(i, Tile.WALL); // southeast wall, corner
                case 16: grid.set(i, Tile.WALL); // northeast wall, corner
                case 23: grid.set(i, Tile.WALL); // eastern wall
                case 24: grid.set(i, Tile.WALL); // northwest wall, corner
                case 29: grid.set(i, Tile.WALL); // southern wall
                case 30: grid.set(i, Tile.WALL); // western wall
                case 31: grid.set(i, Tile.WALL); // northern wall
                case 32: grid.set(i, Tile.WALL); // southwest wall, corner
            }
        }
    }

    private void readYellowBelts(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 33: grid.set(i, Tile.BELT_YELLOW); // east to south, bend
                case 34: grid.set(i, Tile.BELT_YELLOW); // south to west, bend
                case 35: grid.set(i, Tile.BELT_YELLOW); // south to east, bend
                case 36: grid.set(i, Tile.BELT_YELLOW); // west to south, bend
                case 41: grid.set(i, Tile.BELT_YELLOW); // north to east, bend
                case 42: grid.set(i, Tile.BELT_YELLOW); // west to north, bend
                case 43: grid.set(i, Tile.BELT_YELLOW); // east to north, bend
                case 44: grid.set(i, Tile.BELT_YELLOW); // north to west, bend
                case 49: grid.set(i, Tile.BELT_YELLOW); // going north, straight
                case 50: grid.set(i, Tile.BELT_YELLOW); // going south, straight
                case 51: grid.set(i, Tile.BELT_YELLOW); // going west, straight
                case 52: grid.set(i, Tile.BELT_YELLOW); // going east, straight
                case 57: grid.set(i, Tile.BELT_YELLOW); // south & west to north, merge
                case 58: grid.set(i, Tile.BELT_YELLOW); // north & west to east, merge
                case 59: grid.set(i, Tile.BELT_YELLOW); // north & east to south, merge
                case 60: grid.set(i, Tile.BELT_YELLOW); // south & east to west, merge
                case 61: grid.set(i, Tile.BELT_YELLOW); // north & south to east, merge
                case 62: grid.set(i, Tile.BELT_YELLOW); // east & west to south, merge
                case 65: grid.set(i, Tile.BELT_YELLOW); // south & east to north, merge
                case 66: grid.set(i, Tile.BELT_YELLOW); // south & west to east, merge
                case 67: grid.set(i, Tile.BELT_YELLOW); // north & west to south, merge
                case 68: grid.set(i, Tile.BELT_YELLOW); // north & east to west, merge
                case 69: grid.set(i, Tile.BELT_YELLOW); // east & west to north, merge
                case 70: grid.set(i, Tile.BELT_YELLOW); // north & south to west, merge
            }
        }
    }

    private void readBlueBelts(int[] layer) {
        for (int i = 0; i<boardSize-1; i++) {
            switch (layer[i]) {
                case 17: grid.set(i, Tile.BELT_BLUE); // east to south, bend
                case 18: grid.set(i, Tile.BELT_BLUE); // south to west, bend
                case 19: grid.set(i, Tile.BELT_BLUE); // south to east, bend
                case 20: grid.set(i, Tile.BELT_BLUE); // west to south, bend
                case 25: grid.set(i, Tile.BELT_BLUE); // north to east, bend
                case 26: grid.set(i, Tile.BELT_BLUE); // west to north, bend
                case 27: grid.set(i, Tile.BELT_BLUE); // east to north, bend
                case 28: grid.set(i, Tile.BELT_BLUE); // north to west, bend
                case 13: grid.set(i, Tile.BELT_BLUE); // going north, straight
                case 21: grid.set(i, Tile.BELT_BLUE); // going south, straight
                case 22: grid.set(i, Tile.BELT_BLUE); // going west, straight
                case 14: grid.set(i, Tile.BELT_BLUE); // going east, straight
                case 73: grid.set(i, Tile.BELT_BLUE); // south & west to north, merge
                case 74: grid.set(i, Tile.BELT_BLUE); // north & west to east, merge
                case 75: grid.set(i, Tile.BELT_BLUE); // north & east to south, merge
                case 76: grid.set(i, Tile.BELT_BLUE); // south & east to west, merge
                case 81: grid.set(i, Tile.BELT_BLUE); // north & south to east, merge
                case 82: grid.set(i, Tile.BELT_BLUE); // east & west to south, merge
                case 77: grid.set(i, Tile.BELT_BLUE); // south & east to north, merge
                case 78: grid.set(i, Tile.BELT_BLUE); // south & west to east, merge
                case 86: grid.set(i, Tile.BELT_BLUE); // north & west to south, merge
                case 85: grid.set(i, Tile.BELT_BLUE); // north & east to west, merge
                case 84: grid.set(i, Tile.BELT_BLUE); // east & west to north, merge
                case 83: grid.set(i, Tile.BELT_BLUE); // north & south to west, merge
            }
        }
    }
}