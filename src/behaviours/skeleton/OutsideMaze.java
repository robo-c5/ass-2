package behaviours.skeleton;

import lejos.robotics.subsumption.Behavior;
//import lejos Sound;
//import lejos LCD;
//import lejos keys
import mapping.*;

//Highest priority besides one that finds finish?
public class OutsideMaze implements Behavior {
    boolean supressed = false;

    @Override
    public boolean takeControl() {
        return isOutsideMaze(chungus.getCoordinates());
    }

    //non-suppressable
    @Override
    public void suppress() {
    }

    /*
    sound.buzz();
    lcd.clear();
    lcd.drawString("Robot outside Maze");
    key.waitForAnyPress();
    stop arbitrator
    System.exit(0) or equivalent
    */
    @Override
    public void action() {
    }

    private boolean isOutsideMaze(Coordinate currentPosition) {
        return (currentPosition.getX() < 0 || currentPosition.getX() >= grid.getWIDTH()
                || currentPosition.getY() < 0 || currentPosition .getY() >= grid.getHEIGHT());
    }
}