package behaviours;
import lejos.robotics.subsumption.Behavior;
import setup.MazeSolvingRobot;

public class NoGoTile implements Behavior {
	
	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return isGreen(MazeSolvingRobot.getColourSample());
	}
	
	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}
	

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	
	private boolean isGreen(float[] sample) {
		return true;
	}

}
