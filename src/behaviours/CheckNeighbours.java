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

	private boolean doNotInterrupt = false;

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

	@Override
	public void action() {
		if (!doNotInterrupt) {
			doNotInterrupt = true;
			Maze maze = MazeSolvingRobot.getMaze();
			Tile currentTile = (Tile) maze.getMazeObject(MazeSolvingRobot.getTopoPosition());
			currentTile.setVisited(); // set the current tile visited before you leave it, otherwise there are issues backtracking
			checkAdjacentEdges(currentTile);
			Tile targetMazeTile = findNextMove(getNearbyReachableTiles(currentTile, maze));
			rotateTo(currentTile, targetMazeTile);
			MazeSolvingRobot.moveByATile();
			MazeSolvingRobot.setTopoPosition(targetMazeTile.getTopologicalPosition());
			doNotInterrupt = false;
			LCD.clear();
		}
	}

	private void rotateTo(Tile currentTile, Tile targetMazeTile) {
		Bearing targetBearing = (MazeSolvingRobot.getMaze().getBearing(currentTile, targetMazeTile)); // offSetBearing
		MazeSolvingRobot.rotateRobotTo(targetBearing);
	}

	private void checkAdjacentEdges(Tile currentTile) {
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				Delay.msDelay(50);
				// LCD.clear();
				// LCD.drawString(adjacent.getTopologicalPosition().toString(), 0, 4);
				// LCD.drawString("" + MazeSolvingRobot.getMaze().getBearing(currentTile,
				// adjacent), 0, 3);
				MazeSolvingRobot.rotateAndScan(MazeSolvingRobot.getMaze().getBearing(currentTile, adjacent));
				if (detectWall()) {
					// Sound.setVolume(5);
					// Sound.beep();
					adjacent.setNoGo();
				} else {
					adjacent.setVisited();
				}
				// Delay.msDelay(3000);
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

		} else // if the navpath is empty at this point, this means you have got back to the
				// start of the maze without reaching the destination point, so not sure what
				// should happen here
		{
			return null;
		}
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