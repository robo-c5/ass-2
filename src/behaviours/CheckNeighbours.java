package behaviours;

import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.MazeSolvingRobot;

//in terms of priority, MoveToNextTile > CheckNeighbours
public class CheckNeighbours implements Behavior {

	private boolean suppressed = false;
	
	private final int DETECT_WALL_DISTANCE = 20;

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	private boolean detectWall() {
		return (MazeSolvingRobot.getIRSample()[0] < DETECT_WALL_DISTANCE);
	}
	
	/*
	 * Get the position of the robot. Get its Bearing Wait e.g. 1 sec to make sure
	 * if wall their or not, then turn to check all non visited neighbours similarly
	 * going clockwise
	 */

	@Override
	public void action() {
		Coordinate currentPosition = MazeSolvingRobot.getPosition();
		Bearing currentDirection = MazeSolvingRobot.getBearing();
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(currentPosition);
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				try {
					MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, adjacent));
					if (!detectWall()) { // if no wall is detected
						while (!suppressed)
							moveTo(currentPosition, currentDirection, currentTile, maze);
					}
					// may want to just do wall checking here instead of having another behaviour
					// for it
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void moveTo (Coordinate currentPosition, Bearing currentDirection, Tile currentTile, Maze maze)
	{
		Tile destinationTile = maze.getNearestTile(currentTile, currentDirection);
		try {
			if (!maze.isPathBetweenBlocked(currentTile, destinationTile)) {
				MazeSolvingRobot.moveTo(destinationTile.getCentre());
				currentTile.setVisited();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}