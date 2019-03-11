package behaviours;

import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.EV3Setup;
import setup.MazeSolvingRobot;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class DetectWall implements Behavior {

	private final int DETECT_WALL_DISTANCE = 20;

	@Override
	public boolean takeControl() {
		return (EV3Setup.getIRSample()[0] < DETECT_WALL_DISTANCE);
	}

	@Override
	public void suppress() {
	}

	/*
	 * Upon detecting a wall, we should: Get the position of the robot. Get its
	 * Bearing Use these to find out the position of the detected Wall Set that Wall
	 * as non-traversible, then ensure it is also set as visited (probably done
	 * outside this class. We expect to go back to checking the neighbours at the
	 * current location
	 */

	@Override
	public void action() {
		Coordinate currentPosition = MazeSolvingRobot.getPosition();
		Bearing currentDirection = MazeSolvingRobot.getBearing();
		Maze maze = MazeSolvingRobot.getMaze();
		// better if travelByBearing could be static, then wouldn't need another ref to maze
		Edge observedObject = (Edge) maze.getMazeObject(maze.travelByBearing(currentPosition, currentDirection));
		observedObject.setWall();
	}

}