package testing;

import java.util.Scanner;

import mapping.*;

//This class tests whether the getBearing method works as intended. In the end the root cause of issues was missing a few NOT (or !) statements.

public class TestGetBearing
{

	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		Maze testMaze = new Maze();
		System.out.println(testMaze);
		System.out.println("All coordinates are in the form (y,x) since that's how 2d arrays do it");
		System.out.println("Enter y coordinate then enter then x coordinate of MazeObjects to test");
		System.out.println("First the origin MazeObject:");
		int originYPos = sc.nextInt();
		int originXPos = sc.nextInt();
		MazeObject origin = testMaze.getMazeObject(testMaze.getCoordinate(originYPos, originXPos));
		System.out.println("Then the destination MazeObject:");
		int destYPos = sc.nextInt();
		int destXPos = sc.nextInt();
		MazeObject destination = testMaze.getMazeObject(testMaze.getCoordinate(destYPos, destXPos));
		Bearing direction = testMaze.getBearing(origin, destination);
		System.out.println("To get to " + destination.toString() + " at (topo) " + destination.getTopologicalPosition().toString()
				+ " from " + origin.toString() + " at (topo) " + origin.getTopologicalPosition().toString());
		System.out.println("You must travel in the cardinal direction " + direction.toString());

	}
}
