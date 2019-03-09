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
    public static void drawObject(MazeObject drawSubject, GraphicsLCD gScreen) {
        int pixelHeight = drawSubject.getHeight() / CM_PER_PIXEL;
        int pixelWidth = drawSubject.getWidth() / CM_PER_PIXEL;
        int startX = X_OFFSET + drawSubject.getMetricPos().getX() / CM_PER_PIXEL;
        int startY = gScreen.getHeight() - (Y_OFFSET + (drawSubject.getMetricPos().getY() / CM_PER_PIXEL) + pixelHeight);
        gScreen.fillRect(startX, startY, pixelWidth, pixelHeight);
    }

    public static int getCmPerPixel() {
        return CM_PER_PIXEL;
    }

}