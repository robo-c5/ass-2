package behaviours;

import java.util.Stack;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import mapping.Coordinate;
import mapping.Tile;
import pathfinding.AStarSearch;
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
	public void suppress() {}
	
	@Override
	public void action()
	{
		Tile currentTile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition());
		Tile startTile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(new Coordinate(1,1));
		Stack<Tile> path = AStarSearch.ShortestPath(currentTile, MazeSolvingRobot.getEndTile()); // first navigate back to the red tile
		followPath(path);
		path = AStarSearch.ShortestPath(currentTile, startTile); // then go back to the start
		followPath(path);
		Sound.beep();
		Delay.msDelay(10);
		Sound.beep();
		Delay.msDelay(10);
		Sound.beep();
		//play some meme shit
		//send off Maze to server boi all quick
		//close things and then system.exit(0)
	}

	private void followPath(Stack<Tile> path)
	{
		while (!path.isEmpty())
		{
			MazeSolvingRobot.moveTo(path.pop().getTopologicalPosition());
		}
		Sound.beep();
		Delay.msDelay(10);
		Sound.beep();
		Delay.msDelay(1000);
	}

}
