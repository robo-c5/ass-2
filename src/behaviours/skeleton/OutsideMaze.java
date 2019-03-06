package behaviours.skeleton;

import lejos.robotics.subsumption.Behavior;
//import lejos Sound;
//import lejos LCD;
//import lejos keys
import mapping.*;

public class OutsideMaze implements Behavior {
    boolean supressed = false;

    @Override
    public boolean takeControl() {
        return isOutsideMaze(chungus.getCoordinates());
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
            //stop arbitrator
            //sound.buzz();
            //lcd.clear();
            //lcd.drawString("Robot outside Maze");
            //key.waitForAnyPress();
            //System.exit(0) or equivalent
        }
    }

    private boolean isOutsideMaze(Coordinate currentPosition) {
        return (currentPosition.getX() < 0 || currentPosition.getX() >= grid.getWIDTH()
                || currentPosition.getY() < 0 || currentPosition .getY() >= grid.getHEIGHT());
    }
}