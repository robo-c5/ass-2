package behaviours;

import lejos.robotics.subsumption.Behavior;
import setup.*;
import lejos.utility.Delay;
import mapping.*;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.geometry.*;

//in terms of priority, MoveToNextTile > CheckNeighbours
public class CheckNeighbours implements Behavior {

	private boolean suppressed = false;

	private final int DETECT_WALL_DISTANCE = 30;

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
	boolean doOnce = false;

	@Override
	public void action() {
		if (doOnce)
			return;
		doOnce = true;
		MazeSolvingRobot.gatherIRreadings();
		if (true)
			return;
		if (doOnce)
			return;
		doOnce = true;		
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(MazeSolvingRobot.getTopoPosition());

		try {
			checkAdjacentEdges(currentTile);
		} catch (Exception e) {
		}
		Tile targetMazeTile = findNextMove(getAdjacentTiles(currentTile, maze));
		rotateTo(currentTile, targetMazeTile);
		moveTo(targetMazeTile);
	}

	private void rotateTo(Tile currentTile, Tile targetMazeTile) {
		try {
			MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, targetMazeTile));
		} catch (Exception e) {
		}
	}

	private void checkAdjacentEdges(Tile currentTile) throws Exception {
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, adjacent)); // rotateto face the edge
				Delay.msDelay(50);
				if (detectWall()) {
					adjacent.setNoGo();
				} else {
					adjacent.setVisited();
				}
			}
		}
	}

	// look at all possible adjacent tiles, choose the first one that is available
	// to be moved to, if none, backtrack
	private Tile findNextMove(Tile[] adjacentTiles) {
		for (Tile adjacent : adjacentTiles) {
			// if the neighbour is unvisited + not a wall. green tile
			if (adjacent != null && adjacent.isTraversable() && !adjacent.isVisited()) {
				return adjacent;
			}
		}
		return backTrack();
	}

	private Tile backTrack() {
		MazeSolvingRobot.popFromNavPath(); // pop the top element from the navpath stack
		if (MazeSolvingRobot.getNavPath().size() > 0) {
			return MazeSolvingRobot.pollNavPath(); // then set the new targetTile as the top element of
													// the navPath
		} else // if the navpath is empty at this point, this means you have got back to the
				// start of the maze without reaching the destination point, so not sure what
				// should happen here
		{
			return null;
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

	private void moveTo(Tile targetTile) {
		try {
			MazeSolvingRobot.moveTo(targetTile.getTopologicalPosition());
			targetTile.setVisited();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}