package behaviours;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import setup.EV3Setup;
import setup.MazeSolvingRobot;

// hold escape key for a few seconds to quit the program
public class EndArbitrator implements Behavior {

	@Override
	public boolean takeControl() {
		return EV3Setup.escapePressed();
	}

	@Override
	public void suppress() {
		// should not be suppressed as this behaviour ends the program
	}

	@Override
	public void action() {
		System.exit(0);
		//MazeSolvingRobot.getColourSensor().close(); // all this closing should be done, but it causes errors
		//MazeSolvingRobot.getIRSensor().close();
		//MazeSolvingRobot.getNav().stop();
	}
	
}