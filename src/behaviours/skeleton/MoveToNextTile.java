package behaviours.skeleton;

import lejos.robotics.subsumption.Behavior;
//import lejos navigation stuff
import mapping.*;

//in terms of priority, MoveToNextTile > DetectWall > CheckNeighbours
public class MoveToNextTile implements Behavior {
    boolean supressed = false;
    //one Edge width + 2 half-Tile widths
    private final int travelUnit = 40;

    @Override
    public boolean takeControl() {
        //once CheckNeighbours has finished this can run
        return false;
    }

    @Override
    public void suppress() {
        supressed = true;
    }

    /*
    Get the position of the robot.
    Get its Bearing
    Get the nearest Tile in the given direction. Move there if it is traversable
    */

    @Override
    public void action() {
        while (!supressed) {
        }
    }
}