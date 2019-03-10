package setup;

import mapping.*;

public class MazeSolvingRobot {

	private static Bearing bearing;
	
	private static Coordinate position;

	private static Maze maze;
	
	public MazeSolvingRobot(int y, int x, int bearingIndex) {
		setPosition(y, x);
		setBearing(bearingIndex);
		createEV3();
	}

	public static void main(String[] args) {
	}
	
	public static Maze getMaze()
	{
		if (bearing == null)
		{
			setMaze();
		}
		return maze;
	}
	
	public static void setMaze()
	{
		maze = new Maze();
	}
	
	public static Bearing getBearing()
	{
		if (bearing == null)
		{
			setBearing(0);
		}
		return bearing;
	}
	
	public static void setBearing(int index)
	{
		bearing = new Bearing(index);
	}
	
	private static void createEV3()
	{
		new EV3Setup();
	}
	
	// returns an array of 2 ints, y-pos then x-pos
	public static Coordinate getPosition()
	{
		if (position == null)
		{
			setPosition(0, 0); // probably should throw some kind of error here as position should not be null
		}
		return position;
	}
	
	public static void setPosition(int y, int x)
	{
		position = new Coordinate(y, x);
	}

}
