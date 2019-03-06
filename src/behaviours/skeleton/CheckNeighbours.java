package behaviours.skeleton;

import lejos.robotics.subsumption.Behavior;
import mapping.*;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class CheckNeighbours implements Behavior {
    boolean supressed = false;

    @Override
    public boolean takeControl() {
        //once MoveToNextTile has finished this can run
        return false;
    }

    @Override
    public void suppress() {
        supressed = true;
    }

    /*
    Get the position of the robot.
    Get its Bearing
    Wait e.g. 1 sec to make sure if wall their or not, then turn to check all non visited neighbours similarly going clockwise
    */

    @Override
    public void action() {
        while (!supressed) {
        }
    }
}