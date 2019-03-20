package behaviours;

import lejos.robotics.subsumption.Behavior;
import setup.MazeSolvingRobot;

public class AbleToEnd implements Behavior
{
	private static final int TILE_TOTAL_NUM = 6*9;
	//currently doing just traverse whole maze
	private static final int TILE_VISITED_THRESHOLD = TILE_TOTAL_NUM * 1;

	@Override
	public boolean takeControl()
	{
		return (MazeSolvingRobot.getVisitedTileCount() >=  TILE_VISITED_THRESHOLD) && MazeSolvingRobot.isRedFound();
	}

	@Override
	public void action()
	{
		// first get shortest path from currentPos to endTile
		//next travel along it
		//now find shortest path from end tile to start tile
		//travel it
		
		//play some meme shit
		//send off Maze to server boi all quick
		//close things and then system.exit(0)
	}

	@Override
	public void suppress()
	{
		// TODO Auto-generated method stub

	}

}
