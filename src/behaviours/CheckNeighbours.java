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
	boolean doOnce = false;

	@Override
	public void action() {
		// if (doOnce)
		// return;
		// doOnce = true;
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(MazeSolvingRobot.getTopoPosition());

		//
		LCD.clear();
		Delay.msDelay(1000);
		int count = 0;
		for (Tile tile : getAdjacentTiles(currentTile, maze))
		{
			count++;
			LCD.drawString("Chose tile at (topo): " + tile.getTopologicalPosition().toString(), 0, 1+count);
		}
		
		if (true)
			return;
		//

		try {
			checkAdjacentEdges(currentTile);
		} catch (Exception e) {
		}

		boolean shouldBackTrack = true;
		Tile targetMazeTile = currentTile; // the maze object the robot will
		// move towards, by default assume
		// you can go forward
		checkAdjacentTiles(getAdjacentTiles(currentTile, maze), shouldBackTrack, targetMazeTile);

		if (shouldBackTrack) {// no unvisited tiles remain so backtrack
			backTrack(targetMazeTile);
		}
		LCD.drawString("Chose tile at (topo): " + targetMazeTile.getTopologicalPosition().toString(), 0, 4);
		rotateTo(currentTile, targetMazeTile);
	}

	private void rotateTo(Tile currentTile, Tile targetMazeTile) {
		try {
			MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, targetMazeTile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkAdjacentEdges(Tile currentTile) throws Exception {
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (!adjacent.isVisited()) {
				MazeSolvingRobot.rotateTo(Maze.getBearing(currentTile, adjacent)); // rotateto face the edge
				Delay.msDelay(5000); // stupid delay but otherwise rotation is interrupted so fine for now
				if (detectWall()) {
					adjacent.setNoGo();
				} else {
					adjacent.setVisited();
				}
			}
		}
	}

	private void checkAdjacentTiles(Tile[] adjacentTiles, boolean shouldBackTrack, Tile targetMazeTile) {
		for (Tile adjacent : adjacentTiles) {
			if (!adjacent.isTraversable() && !adjacent.isVisited()) {// if the neighbour is unvisited and not a
																		// wall/green tile
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
				// start of the maze without reaching the destination point, so not sure what
				// should happen here
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
				MazeSolvingRobot.moveTo(destinationTile.getTopologicalPosition());
				currentTile.setVisited();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}