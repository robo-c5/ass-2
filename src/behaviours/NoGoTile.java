package behaviours;
import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.MazeSolvingRobot;

public class NoGoTile implements Behavior {
	
	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return isGreen(MazeSolvingRobot.getColourSample());
	}
	
	@Override
	public void suppress() {
		suppressed = true;
	}
	

	@Override
	public void action() {
		Coordinate currentPosition = MazeSolvingRobot.getTopoPosition();
		Maze maze = MazeSolvingRobot.getMaze();
		Tile destinationTile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoDestination());
		destinationTile.setNoGo();
		MazeSolvingRobot.moveTo(currentPosition);
	}

	
	private boolean isGreen(float[] sample) {
		if (0.070 < sample[0] && sample[0] < 0.078 ) {
			if (0.153 < sample[1] && sample[1] < 0.170) {
				if (0.068 < sample[2] && sample[2] < 0.073) {
					return true;
				}
			}
		}
		return false;
	}

}
