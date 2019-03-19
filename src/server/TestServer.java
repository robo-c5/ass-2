package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import lejos.utility.Delay;
import setup.MazeSolvingRobot;
import mapping.*;

public class TestServer
{
	public static final int		port		= 7657;
	private static Maze			testMaze;
	private static Tile			currentTile;
	private static Coordinate	testPos;
	private static Bearing		testDirection;
	private static Bearing[]	cardinals	= MazeSolvingRobot.getCARDINALS();
	private static int			movesLeft	= 10;
	private static boolean		deadEnd		= false;

	private static Random		rand		= new Random();
	private static final float	WALL_CHANCE	= 0.3f;

	public static void main(String[] args) throws IOException
	{
		testMaze = new Maze();
		testPos = MazeSolvingRobot.getOrigin();
		testDirection = MazeSolvingRobot.getCARDINALS()[0];
		currentTile = (Tile) testMaze.getMazeObject(testPos);

		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");

		OutputStream out = client.getOutputStream();

		while (movesLeft > 0)
		{
			System.out.println("Currently at " + testPos.toString());
			checkTile();
			setRandomWalls();
			if (deadEnd)
			{
				pushStatus(out);
				break;
			}
			chooseNext();
			forward();
			pushStatus(out);
			Delay.msDelay(1000);
			movesLeft--;
		}
		server.close();
	}

	public static void checkTile()
	{
		while (unvisitedNeighbour() != null)
		{
			rotate("L");
			currentTile.getAdjacent(testDirection).setVisited();
			currentTile.setVisited();
		}
	}

	public static void setRandomWalls()
	{
		int wallCount = 0;
		int maxWalls = 3;
		for (MazeObject neighbour : currentTile.getNeighbours())
		{
			if (!neighbour.isTraversable())
			{
				wallCount++;
			}
		}
		for (MazeObject neighbour : currentTile.getNeighbours())
		{
			if (wallCount < maxWalls && rand.nextFloat() < WALL_CHANCE)
			{
				neighbour.setNoGo();
				wallCount++;
			}
		}
		if (wallCount == maxWalls)
			deadEnd = true;
	}

	public static void chooseNext()
	{
		Tile nextTile = testMaze.getNearbyReachableTiles(currentTile)[0];
		testDirection = testMaze.getBearing(currentTile, nextTile);
	}

	public static void setWall(MazeObject wall)
	{
		wall.setNoGo();
	}

	public static void rotate(String direction)
	{
		int dirIndex = 0;
		for (int i = 0; i < cardinals.length; i++)
		{
			if (MazeSolvingRobot.getCARDINALS()[i].equals(testDirection))
			{
				dirIndex = i;
			}
		}
		if (direction.equals("L"))
		{
			if (dirIndex == 3)
				dirIndex = 0;
			else
				dirIndex++;
		}
		else
		{
			if (dirIndex == 0)
				dirIndex = 3;
			else
				dirIndex--;
		}
		testDirection = cardinals[dirIndex];
	}

	private static MazeObject unvisitedNeighbour()
	{
		for (MazeObject neighbour : currentTile.getNeighbours())
		{
			if (!neighbour.isVisited())
				return neighbour;
		}
		return null;
	}

	public static void forward()
	{
		testPos = testMaze.travelByBearing(testMaze.travelByBearing(currentTile.getTopologicalPosition(), testDirection), testDirection);
		currentTile = (Tile) testMaze.getMazeObject(testPos);
	}

	public static void pushStatus(OutputStream out) throws IOException
	{
		EV3StatsMessage maze = new EV3StatsMessage(testMaze, EV3StatsMessage.MAZE);
		EV3StatsMessage topoPos = new EV3StatsMessage(testPos, EV3StatsMessage.POSITION);
		EV3StatsMessage heading = new EV3StatsMessage(testDirection, EV3StatsMessage.HEADING);
		EV3StatsMessage end = new EV3StatsMessage(deadEnd, EV3StatsMessage.END);

		ObjectOutputStream objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(maze);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(topoPos);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(heading);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(end);

	}
}
