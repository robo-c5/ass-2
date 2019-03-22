package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import mapping.*;
import setup.MazeSolvingRobot;

//Test the conversion of the Maze data object into pixels on the EV3 LCD screen. This class takes an empty maze so should only draw the boundary walls.

public class TestDrawStats
{

	public static void main(String args[])
	{
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Maze testMaze = new Maze();
		Coordinate position = MazeSolvingRobot.getOrigin();
		Bearing heading = MazeSolvingRobot.getCARDINALS()[0];
		DrawStats.drawMaze(testMaze);
		DrawStats.drawStats(position, heading);
		ev3Brick.getKeys().waitForAnyPress();
	}
}
