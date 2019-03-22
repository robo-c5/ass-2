package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.GraphicsLCD;
import mapping.DrawStats;
import mapping.Maze;

//Test the conversion of the Maze data object into pixels on the EV3 LCD screen. This class takes an empty maze so should only draw the boundary walls.

public class TestDrawMaze
{

	public static void main(String args[])
	{
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		GraphicsLCD gLCD = BrickFinder.getLocal().getGraphicsLCD();
		Maze testMaze = new Maze();
		DrawStats.drawMaze(testMaze);
		ev3Brick.getKeys().waitForAnyPress();
	}
}
