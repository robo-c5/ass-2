package behaviours;

import lejos.robotics.subsumption.Behavior;
import setup.*;
import lejos.utility.Delay;
import mapping.*;
import pathfinding.AStarSearch;

import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.geometry.*;

//in terms of priority, MoveToNextTile > CheckNeighbours
public class CheckNeighbours implements Behavior {

	private boolean doNotInterrupt = false;

	private final int DETECT_WALL_DISTANCE = 30;

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void suppress() {
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
		if (!doNotInterrupt) {
			doNotInterrupt = true;
			Maze maze = MazeSolvingRobot.getMaze();
			Tile currentTile = (Tile) maze.getMazeObject(MazeSolvingRobot.getTopoPosition());
			currentTile.setVisited(); // set the current tile visited before you leave it, otherwise there are issues
										// backtracking
			checkAdjacentEdges(currentTile);
			Tile targetMazeTile = findNextMove(getNearbyReachableTiles(currentTile, maze));
			if (targetMazeTile == null) // if no next tile can be found, this means we have backtracked back to the start, so can now end
			{
				setEnd();
				return;
			}
			// if the current tile is red and all tiles are visited, end
			if (isRed(MazeSolvingRobot.getColourSample(), currentTile) && MazeSolvingRobot.getMaze().getUnvisitedTiles().size() == 0)
			{
				setEnd();
				return;
			}
			rotateTo(currentTile, targetMazeTile);
			MazeSolvingRobot.moveByATile();
			MazeSolvingRobot.setTopoPosition(targetMazeTile.getTopologicalPosition());
			doNotInterrupt = false;
		}
	}

	private void setEnd() {

	}

	private void rotateTo(Tile currentTile, Tile targetMazeTile) {
		Bearing targetBearing = (MazeSolvingRobot.getMaze().getBearing(currentTile, targetMazeTile)); // offSetBearing
		MazeSolvingRobot.rotateRobotTo(targetBearing);
	}

	private void checkAdjacentEdges(Tile currentTile) {
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				Delay.msDelay(50);
				MazeSolvingRobot.rotateAndScan(MazeSolvingRobot.getMaze().getBearing(currentTile, adjacent));
				if (detectWall()) {
					adjacent.setNoGo();
				} else {
					adjacent.setVisited();
				}
			}
		}
		MazeSolvingRobot.resetMedMotor();
	}

	// look at all possible adjacent tiles, choose the first one that is available
	// to be moved to, if none, backtrack
	private Tile findNextMove(Tile[] adjacentTiles) {
		if (!isGreen(MazeSolvingRobot.getColourSample())) {
			for (Tile adjacent : adjacentTiles) {
				if (adjacent != null && adjacent.isTraversable() && !adjacent.isVisited()) {
					return adjacent;
				}
			}
		}
		return backTrack();
	}

	private boolean isRed(float[] sample, Tile currentTile) {
		////TESTING REMOVE THIS
		//if (currentTile.getTopologicalPosition().equals(new Coordinate(1, 3)))
		//{
		//	MazeSolvingRobot.setEndTile(currentTile.getTopologicalPosition());
		//	return true;
		//}
		////
		
		if (0.05 < sample[0] && sample[0] < 0.35) {
			if (0.015 < sample[1] && sample[1] < 0.07) {
				if (0.015 < sample[2] && sample[2] < 0.06) {
					MazeSolvingRobot.setEndTile(currentTile.getTopologicalPosition());
					return true;
				}
			}
		}
		return false;
	}

	private boolean isGreen(float[] sample) {
		Delay.msDelay(50);
		if (0.01 < sample[0] && sample[0] < 0.03) {
			if (0.045 < sample[1] && sample[1] < 0.07) {
				if (0.022 < sample[2] && sample[2] < 0.05) {
					return true;
				}
			}
		}
		return false;
	}

	private Tile backTrack() {
		MazeSolvingRobot.popFromNavPath(); // pop the top element from the navpath stack
		if (MazeSolvingRobot.getNavPath().size() > 0) {
			return MazeSolvingRobot.pollNavPath(); // then set the new targetTile as the top element of
													// the navPath

		} else if (MazeSolvingRobot.isRedFound())

		{
			MazeSolvingRobot.end();
		}
		return null;
	}

	private Tile[] getNearbyReachableTiles(Tile currentTile, Maze maze) {
		try {
			return (maze.getNearbyReachableTiles(currentTile));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

}