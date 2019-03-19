package behaviours;

import lejos.robotics.subsumption.Behavior;
//import lejos Sound;
//import lejos LCD;
//import lejos keys
import mapping.*;
import setup.MazeSolvingRobot;

//Highest priority besides one that finds finish?
public class OutsideMaze implements Behavior
{
	boolean supressed = false;

	@Override
	public boolean takeControl()
	{
		return isOutsideMaze(MazeSolvingRobot.getTopoPosition());
	}

	//non-suppressable
	@Override
	public void suppress()
	{
	}

	/*
	sound.buzz();
	lcd.clear();
	lcd.drawString("Robot outside Maze");
	key.waitForAnyPress();
	stop arbitrator
	System.exit(0) or equivalent
	*/
	@Override
	public void action()
	{
	}

	public static boolean isOutsideMaze(Coordinate currentPosition)
	{
		return (currentPosition.getX() < 0 || currentPosition.getX() >= Maze.getWIDTH() || currentPosition.getY() < 0
				|| currentPosition.getY() >= Maze.getHEIGHT());
	}
}