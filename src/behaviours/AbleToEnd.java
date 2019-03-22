package behaviours;

import java.util.ArrayList;
import java.util.Collections;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import mapping.Coordinate;
import mapping.Tile;
import pathfinding.AStarSearch;
import setup.MazeSolvingRobot;

public class AbleToEnd implements Behavior {

	@Override
	public boolean takeControl() {
		return MazeSolvingRobot.getEnd();
	}

	@Override
	public void suppress() {
	}

	@Override
	public void action() {
		Tile startTile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition());

		Tile redTile = MazeSolvingRobot.getEndTile();

		ArrayList<Tile> pathToRed = AStarSearch.ShortestPath(startTile, redTile);
		
		Collections.reverse(pathToRed);
		
		LCD.clear();
		int i = 0;
		for (Tile nextMove : pathToRed) {
			LCD.drawString(nextMove.getTopologicalPosition().toString(), 3, 3 + i);
			i++;
		}
		
		// followPath(pathToRed);
		
		BrickFinder.getLocal().getKeys().waitForAnyPress();
		
		ArrayList<Tile> redToStart = pathToRed;
		Collections.reverse(redToStart);
		
		LCD.clear();		
		i = 0;
		for (Tile nextMove : redToStart) {
			LCD.drawString(nextMove.getTopologicalPosition().toString(), 3, 3 + i);
			i++;
		}
		
		// followPath(redToStart);		

		BrickFinder.getLocal().getKeys().waitForAnyPress();

		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		Sound.beep();
		Delay.msDelay(50);
		System.exit(0);
	}
}
