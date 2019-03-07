package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;

public class TestDrawObject {
	
	public static void main(String args[]) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		LCD.clear();
		DrawMaze.drawObject(20, 20, 10, 10, 1);
		ev3brick.getKeys().waitForAnyPress();	
	}

}
