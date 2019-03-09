package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.GraphicsLCD;
import static lejos.hardware.lcd.GraphicsLCD.*;

public class TestDrawMaze {

    public static void main(String args[]) {
        EV3 ev3brick = (EV3) BrickFinder.getLocal();
        GraphicsLCD gLCD = BrickFinder.getLocal().getGraphicsLCD();
        Maze testMaze = new Maze();
        final int gScreenHeight = gLCD.getHeight();
        final int gScreenWidth = gLCD.getWidth();
        gLCD.drawString("gLCD has height " + gScreenHeight + " and width " + gScreenWidth, gScreenWidth/2, gScreenHeight/2,
                GraphicsLCD.BASELINE|GraphicsLCD.HCENTER);
        ev3brick.getKeys().waitForAnyPress();
        DrawMaze.drawMaze(testMaze, gLCD);
        ev3brick.getKeys().waitForAnyPress();
    }
}
