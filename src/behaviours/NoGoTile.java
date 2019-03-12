package behaviours;
import lejos.robotics.subsumption.Behavior;
import mapping.Coordinate;
import mapping.Maze;
import mapping.Tile;
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
		Coordinate currentPosition = MazeSolvingRobot.getPosition();
		Maze maze = MazeSolvingRobot.getMaze();
		//get destination tile from MazeSolving Robot destination field
		//set that tile's colour to green
		//set that tile as visited and non traversable
		//go back to the previous tile (should be tile at currentposition field as hopefully not updated until move would complete)
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
