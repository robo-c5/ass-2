package mapping;
import java.util.Scanner;

public class TestMaze {
    private final static int CM_PER_PIXEL = 5;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Maze testMaze = new Maze();
        System.out.println(testMaze);
        System.out.println("All coordinates are in the form (y,x) since that's how 2d arrays do it");
        System.out.println("Enter y coordinate then enter then x coordinate");
        int yPos = sc.nextInt();
        int xPos = sc.nextInt();
        sc.close();
        Coordinate testCoordinate = testMaze.getCoordinate(yPos, xPos);
        MazeObject testObject = testMaze.getMazeObject(testCoordinate);
        if (testObject instanceof Edge) {
            if (((Edge) testObject).isBoundaryWall()) {
                System.out.println("The MazeObject " + testObject.toString() + " at " + testCoordinate.toString() + " is a Boundary Wall");
                System.out.println();
            }
        }

        System.out.println(testObject.toString() + " at " + testCoordinate.toString() + " is " + testObject.getHeight() +  " cm or "
                + testObject.getHeight()/CM_PER_PIXEL + " Pixels high and " + testObject.getWidth() +
                " cm or " + testObject.getWidth()/CM_PER_PIXEL + " Pixels Wide");

        Coordinate objectSW = testObject.getMetricPos();
        int maxY = objectSW.getY() + testObject.getHeight();
        int maxX = objectSW.getX() + testObject.getWidth();
        Coordinate objectNE = new Coordinate(maxY, maxX);
        System.out.println(testObject.toString() + " at " + testCoordinate.toString() + " has a South-Western corner at "
                + objectSW.toString() + " and a North-Eastern Corner at " + objectNE.toString() + " and its centre is at "
                + testObject.getCentre().toString());

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