package mapping;
import java.util.Scanner;

public class TestMaze {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter x then enter then y coords");
	    int xCoord = sc.nextInt();
	    int yCoord = sc.nextInt();		
		Maze maze = new Maze();
		System.out.println(maze); 	
		System.out.println("The neighbours of (" + xCoord + "," + yCoord + ") are: ");
		for (MazeObject neighbour : maze.getMazeObject(xCoord, yCoord).getNeighbours()) {
			if (neighbour != null)
			System.out.println(neighbour + " at position (" + neighbour.getPos()[0] + ", " + neighbour.getPos()[1] + ")");
		}		
	}
	
}