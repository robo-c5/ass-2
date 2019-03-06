package behaviours.skeleton;

import lejos.robotics.subsumption.Behavior;
import mapping.*;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class DetectWall implements Behavior {
    boolean supressed = false;

    @Override
    public boolean takeControl() {
        //if IR? sensor returns less than 20cm from something, we detected a wall
        return false;
    }

    @Override
    public void suppress() {
        supressed = true;
    }

    /*
    Upon detecting a wall, we should:
    Get the position of the robot.
    Get its Bearing
    Use these to find out the position of the detected Wall
    Set that Wall as non-traversible, then ensure it is also set as visited (probably done outside this class.
    We expect to go back to checking the neighbours at the current location
    */

    @Override
    public void action() {
        while (!supressed) {
            Coordinate currentPosition = chungus.getCoordinates();
            Bearing currentDirection = chungus.getBearing();
            MazeObject observedObject = grid.getMazeObject(Maze.travelByBearing(currentPosition, currentDirection));
            observedObject.setWall();
        }
    }
}