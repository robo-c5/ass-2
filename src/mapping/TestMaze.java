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
		if (testObject instanceof Edge) {
            if (((Edge) testObject).isBoundaryWall()) {
                System.out.println("The MazeObject " + testObject.toString() + " at " + testCoordinate.toString() + " is a Boundary Wall");
                System.out.println();
            }
        }
		
		System.out.println(testObject.toString() + " at " + testCoordinate.toString() + " is " + testObject.getHeight() +  " cm or " + testObject.getHeight()/DrawMaze.getCmPerPixel() + " Pixels high and " + testObject.getWidth() + 
				" cm or " + testObject.getWidth()/DrawMaze.getCmPerPixel() + " Pixels Wide");
		
		System.out.println();
		System.out.println("The neighbours of " + testObject.toString() + " at " + testCoordinate.toString() + " are: ");
		for (MazeObject neighbour : testObject.getNeighbours()) {
			if (neighbour != null)
			System.out.println(neighbour.toString() + " at position" + neighbour.getTopologicalPosition().toString());
		}
		
		System.out.println();
		System.out.println("Here is the stringRep of that row");
		
		
		for (MazeObject mO : testMaze.getRow(yPos)) {
			System.out.print(mO.toString());
		}
		
		System.out.println();
		System.out.println("Here is the stringRep of that column");

		
		for (MazeObject mO : testMaze.getColumn(yPos)) {
			System.out.print(mO.toString());
		}

	}
	
}