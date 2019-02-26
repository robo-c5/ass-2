
public class Tile {

	final static int NORTH = 0;
	final static int EAST = 1;
	final static int SOUTH = 2;
	final static int WEST = 3;
	Edge[] edges = new Edge[4];
	
	int xPos;
	int yPos;
	boolean visited = false;
	static final int sideLength = 30;
	Colour colour;
	
	boolean traversible;

	public Tile(int x, int y) {
		
		xPos = x;
		yPos = y;
		
		if (xPos == 0) {
			Edge[WEST].setTraversible(false);
		} else if (xPos == 8) {
			Edge[EAST].setTraversible(false);
		}
		if (yPos == 0) {
			Edge[SOUTH].setTraversible(false);
		} else if (yPos == 5) {
			Edge[NORTH].setTraversible(false);
		}
		// add the outer walls to generated map
			
	}
	
}
