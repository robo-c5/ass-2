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
        DrawMaze.drawMaze(testMaze, gLCD);
        ev3brick.getKeys().waitForAnyPress();
    }
}
