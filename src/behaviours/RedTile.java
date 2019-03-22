package behaviours;

import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.*;

public class RedTile implements Behavior
{

	//The tile at (1,1) is the starting tile which we navigate back to

	@Override
	public boolean takeControl()
	{
		return isRed(MazeSolvingRobot.getColourSample());
	}

	@Override
	public void suppress()
	{
		//cannot be suppressed
	}

	//general procedure once detect Red: turn around (wide eyes), return to previous position. Then find shortest path back to start tile then take it.
	@Override
	public void action()
	{
		//MazeSolvingRobot.moveTo(MazeSolvingRobot.getTopoDestination());
	//	MazeSolvingRobot.setEndTile((Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoDestination())); 
	}

	private boolean isRed(float[] sample)
	{
		if (0.212 < sample[0] && sample[0] < 0.253)
		{
			if (0.049 < sample[1] && sample[1] < 0.063)
			{
				if (0.047 < sample[2] && sample[2] < 0.056)
				{
					return true;
				}
			}
		}
		return false;
	}
}
