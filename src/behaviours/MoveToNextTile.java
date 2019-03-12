package behaviours;

import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.EV3Setup;
import setup.MazeSolvingRobot;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class MoveToNextTile implements Behavior {

	boolean supressed = false;
	// one Edge width + 2 half-Tile widths
	private final int travelUnit = 40;

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void suppress() {
		supressed = true;
	}

	/*
	 * Get the position of the robot. Get its Bearing Get the nearest Tile in the
	 * given direction. Move there if it is traversable
	 */

	@Override
	public void action() {
		//while (!supressed) { // if this is done is a loop, then it can be interrupted, which is good, but
								// this means we have to keep track of how far the robot has moved
			
			//Thread.yield();
		//}
		//something like?
		Coordinate currentPosition = MazeSolvingRobot.getPosition();
		Bearing currentDirection = MazeSolvingRobot.getBearing();
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(currentPosition);
		Tile destinationTile = maze.getNearestTile(currentTile, currentDirection);
		try
		{
			if (!maze.isPathBetweenBlocked(currentTile, destinationTile)) {
				MazeSolvingRobot.moveTo(destinationTile.getCentre());
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
	}
}