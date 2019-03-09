package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.GraphicsLCD;
import static lejos.hardware.lcd.GraphicsLCD.*;

public class TestDrawObject {

    public static void main(String args[]) {
        EV3 ev3brick = (EV3) BrickFinder.getLocal();
        GraphicsLCD gLCD = BrickFinder.getLocal().getGraphicsLCD();
        gLCD.clear();
        gLCD.setColor(gLCD.BLACK);
        gLCD.fillRect(20, 20, 5, 5);
        gLCD.fillRect(40, 40, 5, 5);
        gLCD.fillRect(20, 60, 5, 5);
        ev3brick.getKeys().waitForAnyPress();
    }

}
