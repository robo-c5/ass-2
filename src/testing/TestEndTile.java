package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.*;
import lejos.hardware.lcd.LCD;
import setup.MazeSolvingRobot;

public class TestEndTile {

	public static void main (String[] args)
	{
		new MazeSolvingRobot();
		MazeSolvingRobot.setEndTile(MazeSolvingRobot.getMaze().getCoordinate(3, 3));
		LCD.drawString(MazeSolvingRobot.getEndTile().getTopologicalPosition().toString(), 0, 5);
		((EV3) BrickFinder.getLocal()).getKeys().waitForAnyPress();
	}
	
}
