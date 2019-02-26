public abstract class MazeObject {

	final static int NORTH = 0;
	final static int EAST = 1;
	final static int SOUTH = 2;
	final static int WEST = 3;
	// might delete this later
	
	boolean traversible = true;
	boolean visited = false;
	int xPos;
	int yPos;
	
	public MazeObject(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
}
