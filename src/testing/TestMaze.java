package testing;
import java.util.Scanner;

import mapping.Bearing;
import mapping.Coordinate;
import mapping.DrawMaze;
import mapping.Edge;
import mapping.Maze;
import mapping.MazeObject;
import mapping.Tile;
import setup.MazeSolvingRobot;

//This class uses the String Representation of a Maze and its components to draw out the maze on the command line, and provide information which helps ensure the 
//maze is intialised correctly;
public class TestMaze {
   private static final int CM_PER_PIXEL = DrawMaze.getCmPerPixel();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Maze testMaze = new Maze();
        System.out.println(testMaze);
        System.out.println("All coordinates are in the form (y,x) since that's how 2d arrays do it");
        System.out.println("Enter y coordinate then enter then x coordinate of MazeObject to test");
        int yPos = sc.nextInt();
        int xPos = sc.nextInt();
        sc.close();
        Coordinate testCoordinate = testMaze.getCoordinate(yPos, xPos);
        MazeObject testObject = testMaze.getMazeObject(testCoordinate);
        if (testObject instanceof Edge) {
            if (((Edge) testObject).isBoundaryWall()) {
                System.out.println("The MazeObject " + testObject.toString() + " at (topo) " + testCoordinate.toString() + " is a Boundary Wall");
                System.out.println();
            }
        }
        
        System.out.println();
        System.out.println(testObject.toString() + " at (topo) " + testCoordinate.toString() + " is " + testObject.getHeight() +  " cm or "
                + testObject.getHeight()/CM_PER_PIXEL + " Pixels high and " + testObject.getWidth() +
                " cm or " + testObject.getWidth()/CM_PER_PIXEL + " Pixels Wide");
        System.out.println();
        
        System.out.println("Traversable: " + testObject.isTraversable());
        System.out.println("Visited: " + testObject.isVisited());
        System.out.println();
        
        System.out.println(testObject.toString() + " at (topo) " + testCoordinate.toString() + " has centre at (metric) " + 
        testObject.getCentre().toString() );
        System.out.println();
        
        System.out.println("Its four corners are: ");
        System.out.println("NE at " + testObject.getCorners()[0].toString());
        System.out.println("NW at " + testObject.getCorners()[1].toString());
        System.out.println("SW at " + testObject.getCorners()[2].toString());
        System.out.println("SE at " + testObject.getCorners()[3].toString());
      
        System.out.println();
        System.out.println("The neighbours of " + testObject.toString() + " at (topo) " + testCoordinate.toString() + " are: ");
        for (MazeObject neighbour : testObject.getNeighbours()) {
            if (neighbour != null)
                System.out.println(neighbour.toString() + " at position (topo) " + neighbour.getTopologicalPosition().toString() + " traversable: " + neighbour.isTraversable());
        }
        
        if (testObject instanceof Tile) {
        	System.out.println();
        	System.out.println("The nearest Tiles to " + testObject.toString() + " at (topo) " + testCoordinate.toString() + " are: ");
        	Tile neighbouringTile;
		    for (Bearing direction : MazeSolvingRobot.getCARDINALS()) {
		    	neighbouringTile =  testMaze.getNearestTile((Tile) testObject, direction);
		    	if (neighbouringTile != null) {
		    		System.out.println(neighbouringTile.toString() + " at position (topo) " + neighbouringTile.getTopologicalPosition().toString() + ":");
		    		Edge sharedEdge = testMaze.sharedNeighbour((Tile) testObject, neighbouringTile);
					System.out.println("Shared Edge " + sharedEdge.toString() + " is at " + sharedEdge.getTopologicalPosition().toString());
					System.out.println("traversable: " + testMaze.sharedNeighbour((Tile) testObject, neighbouringTile).isTraversable());
					System.out.println("Reachable : " + !testMaze.isPathBetweenBlocked((Tile) testObject, neighbouringTile));
					System.out.println();
		    	}		
		    }
        }

        System.out.println("Here is the stringRep row " + testObject.getTopologicalPosition().getY() + ":");
        for (MazeObject mO : testMaze.getRow(yPos)) {
            System.out.print(mO.toString());
        }
        System.out.println();
        
        System.out.println("Here is the stringRep column " + testObject.getTopologicalPosition().getY() + ":");
        for (MazeObject mO : testMaze.getColumn(xPos)) {
            System.out.print(mO.toString());
        }
    }
}