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
		
		followPath(pathToRed);
		
		ArrayList<Tile> redToStart = pathToRed;
		Collections.reverse(redToStart);
		
		followPath(redToStart);

		MazeSolvingRobot.drawStats();
		
		BrickFinder.getLocal().getKeys().waitForAnyPress();
		System.exit(0);
	}
	
	public static void followPath(ArrayList<Tile> path) {
		for (int i = 0; i < path.size()-1; i++) {
			Tile currentTile = path.get(i);
			Tile targetMazeTile = path.get(i+1);
			
			CheckNeighbours.rotateTo(currentTile, targetMazeTile);
			MazeSolvingRobot.moveByATile();
		}
	}
}
