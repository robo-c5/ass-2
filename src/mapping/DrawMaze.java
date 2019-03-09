package mapping;

import lejos.hardware.lcd.GraphicsLCD;
import static lejos.hardware.lcd.GraphicsLCD.*;

public class DrawMaze {
    private static final int CM_PER_PIXEL = 5;
    //might be hard to see a wall at the edge of lcd screen
    private static final int X_OFFSET = 6;
    private static final int Y_OFFSET = 6;

    public static void drawMaze (Maze grid, GraphicsLCD gScreen) {
        gScreen.clear();
        for (int y = 0; y < grid.getHEIGHT(); y++) {
            for (MazeObject testObject : grid.getRow(y)) {
                if (testObject.isTraversable()) {
                    gScreen.setColor(gScreen.BLACK);
                } else {
                    gScreen.setColor(gScreen.WHITE);
                }
                drawObject(testObject, gScreen);
            }
        }
    }

    ///have to convert from metric coordinates to pixel coordinates
    //fillRect presumably draws left to right, top to bottom
    //want to draw Maze row 0 at bottom of screen
    //once finished drawing an object make sure start drawing from one pixel further along than end of previous object
    //first get south and west neighbours of the object
    //if e.g. boundary wall and is no neighbour in W direction just start drawing from X_OFFSET
    //else start drawing from pixel end of W neighbour + 1
    //if no neighbour in S direction start drawing from screenHeight - (Y_OFFSET + pixelHeight) (draws top-> bottom)
    //else start drawing from pixel end of S neighbour - 1
    public static void drawObject(MazeObject drawSubject, GraphicsLCD gScreen) {
        int pixelXStart;
        int pixelYStart;
        int pixelHeight = drawSubject.getHeight() / CM_PER_PIXEL;
        int pixelWidth = drawSubject.getWidth() / CM_PER_PIXEL;

        try {
            MazeObject westNeighbour = drawSubject.getAdjacent(Maze.getCARDINALS()[3]);
            int westNeighbourXPixelEnd = westNeighbour.getMetricPos().getX() / CM_PER_PIXEL;
            pixelXStart = westNeighbourXPixelEnd + 1;
        } catch (NullPointerException npe) {
            pixelXStart = X_OFFSET;
        }

        try {
            MazeObject southNeighbour = drawSubject.getAdjacent(Maze.getCARDINALS()[2]);
            int southNeighbourYPixelEnd = southNeighbour.getMetricPos().getY() / CM_PER_PIXEL;
            pixelYStart = southNeighbourYPixelEnd - 1;
        } catch (NullPointerException npe) {
            pixelYStart = gScreen.getHeight() - (Y_OFFSET + pixelHeight);
        }

        gScreen.fillRect(pixelXStart, pixelYStart, pixelWidth, pixelHeight);
    }

    public static int getCmPerPixel() {
        return CM_PER_PIXEL;
    }

}