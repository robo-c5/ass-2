package testing;

import java.util.Scanner;

import mapping.Maze;
import mapping.MazeObject;

public class TestMazeObjectEquals {

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
        Maze testMaze = new Maze();
        System.out.println(testMaze);
        System.out.println("All coordinates are in the form (y,x) since that's how 2d arrays do it");
        System.out.println("Enter y coordinate then enter then x coordinate of MazeObjects to compare");
        System.out.println("First MazeObject:");
        int originYPos = sc.nextInt();
        int originXPos = sc.nextInt();
        MazeObject origin = testMaze.getMazeObject(testMaze.getCoordinate(originYPos, originXPos));
        System.out.println("Second MazeObject:");
        int destYPos = sc.nextInt();
        int destXPos = sc.nextInt();
        MazeObject destination = testMaze.getMazeObject(testMaze.getCoordinate(destYPos, destXPos));        
        boolean areEqual1 = origin.equals(destination);
        boolean areEqual2 = destination.equals(origin);
        
        System.out.println("Is MazeObject " + origin.toString() + " at (topo) " + origin.getTopologicalPosition().toString() + " equal to " + destination.toString() 
        + " at (topo) " + destination.getTopologicalPosition().toString() + ": " + areEqual1);
        System.out.println("Is MazeObject " + destination.toString() + " at (topo) " + destination.getTopologicalPosition().toString() + " equal to " + origin.toString() 
        + " at (topo) " + origin.getTopologicalPosition().toString() + ": " + areEqual2);
        
	}
}
