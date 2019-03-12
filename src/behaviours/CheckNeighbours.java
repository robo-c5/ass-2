package behaviours;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
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
		return (MazeSolvingRobot.getIRSample() < DETECT_WALL_DISTANCE);
	}

	/*
	 * Get the position of the robot. Get its Bearing Wait e.g. 1 sec to make sure
	 * if wall their or not, then turn to check all non visited neighbours similarly
	 * going clockwise
	 */

	@Override
	public void action() {
		Coordinate currentPosition = MazeSolvingRobot.getPosition();
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(currentPosition);
		checkAdjacentEdges(currentTile);
		boolean shouldBackTrack = true;
		Tile targetMazeTile = new Tile(null, null); // the maze object the robot will move towards
		checkAdjacentTiles(getAdjacentTiles(currentTile, maze), shouldBackTrack, targetMazeTile);
		if (shouldBackTrack) // no unvisited tiles remain so backtrack
		{
			backTrack(targetMazeTile);
		}
		rotateTo(currentTile, targetMazeTile);
	}

	private void rotateTo(Tile currentTile, Tile targetMazeTile) {
		try {
			MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, targetMazeTile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkAdjacentEdges(Tile currentTile)
	{
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				try {
					MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, adjacent)); // rotate to face the edge
					Delay.msDelay(500);
					if (detectWall()) {
						adjacent.setNoGo();
					} else {
						adjacent.setVisited();
					}
					// may want to just do wall checking here instead of having another behaviour
					// for it
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void checkAdjacentTiles(Tile[] adjacentTiles, boolean shouldBackTrack, Tile targetMazeTile) {
		for (Tile adjacent : adjacentTiles) {
			if (!adjacent.isTraversable() && !adjacent.isVisited()) // if the neighbour is unvisited and not a wall
			{
				targetMazeTile = (Tile) adjacent;
				shouldBackTrack = false;
				return;
			}
		}
	}

	private void backTrack(Tile targetMazeTile) {
		MazeSolvingRobot.popFromNavPath(); // pop the top element from the navpath stack
		if (MazeSolvingRobot.getNavPath().size() > 0) {
			targetMazeTile = MazeSolvingRobot.pollNavPath(); // then set the new targetTile as the top element of
																// the navPath
		} else // if the navpath is empty at this point, this means you have got back to the
				// start of the maze without reaching the destination point
		{

		}
	}

	private Tile[] getAdjacentTiles(Tile currentTile, Maze maze) {
		try {
			return (maze.getAdjacentTiles(currentTile));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private void moveTo(Coordinate currentPosition, Bearing currentDirection, Tile currentTile, Maze maze) {
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