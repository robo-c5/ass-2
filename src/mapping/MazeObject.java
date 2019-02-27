package mapping;
public abstract class MazeObject {
	
	private MazeObject[] neighbours = new MazeObject[4];
	final static int NORTH = 0;
	final static int EAST = 1;
	final static int SOUTH = 2;
	final static int WEST = 3;
	// might delete this later
	
	boolean traversible = true;
	boolean visited = false;
	int xPos;
	int yPos;
	String stringRep = "";
	
	public MazeObject(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public void setNeighbours(MazeObject[] neighbours) {
		this.neighbours = neighbours;
	}
	
	public MazeObject[] getNeighbours() {
		return this.neighbours;
	}
	
	@Override 
	public String toString() {
		return stringRep;
	}
	
	public int[] getPos() {
		int[] coords = {xPos, yPos};
		return coords;
	}
}