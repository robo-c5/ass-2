package behaviours;

import lejos.hardware.ev3.EV3;
import lejos.robotics.subsumption.Behavior;
import mapping.Bearing;
import mapping.Coordinate;
import mapping.Edge;
import mapping.Maze;
import mapping.MazeObject;
import mapping.Tile;
import setup.EV3Setup;
import setup.MazeSolvingRobot;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class CheckNeighbours implements Behavior {
	
	boolean supressed = false;

	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void suppress() {
		supressed = true;
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
		Tile currentTile = (Tile)maze.getMazeObject(currentPosition);
		for (MazeObject adjacent : currentTile.getNeighbours())
		{
			if (!adjacent.isVisited())
			{
				try {
					MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, adjacent));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}