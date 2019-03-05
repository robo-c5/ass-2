package mapping;
import java.util.Scanner;

public class TestMaze {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        Maze testMaze = new Maze();
		System.out.println("All coordinates are in the form (y,x) since that's how 2d arrays do it");
		System.out.println("Enter y coord then enter then x coord");
	    int yPos = sc.nextInt();
	    int xPos = sc.nextInt();
	    sc.close();
		Coordinate testCoordinate = testMaze.getCoordinate(yPos, xPos);
		MazeObject testObject = testMaze.getMazeObject(testCoordinate);
		System.out.println(testMaze);
		if (testObject.isBoundaryWall()) {
		    System.out.println("The MazeObject " + testObject.toString() + " at " + testCoordinate.toString() + " is a Boundary Wall");
        }

		System.out.println("The neighbours of " + testObject.toString() + " at " + testCoordinate.toString() + " are: ");
		for (MazeObject neighbour : testObject.getNeighbours()) {
			if (neighbour != null)
			System.out.println(neighbour.toString() + " at position" + neighbour.getPosition().toString());
		}


	}
	
}