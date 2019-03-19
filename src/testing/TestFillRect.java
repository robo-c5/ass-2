package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.GraphicsLCD;

//Testing how fillRect works by drawing a few filled rectangles. We found that rectangles are drawn from left-to-right, then top-to-bottom.

public class TestFillRect
{

	public static void main(String args[])
	{
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		GraphicsLCD gLCD = BrickFinder.getLocal().getGraphicsLCD();
		gLCD.clear();
		gLCD.setColor(GraphicsLCD.BLACK);
		gLCD.fillRect(10, 0, 5, 5);
		gLCD.fillRect(20, 20, 5, 5);
		gLCD.fillRect(40, 40, 5, 5);
		gLCD.fillRect(20, 60, 5, 5);
		ev3brick.getKeys().waitForAnyPress();
	}

}
