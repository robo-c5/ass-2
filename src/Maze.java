public class Maze {

	final int width = 19;
	final int height = 13; 
	
	String stringRep = "";
	MazeObject[][] grid = new MazeObject[width][height];
	
	public Maze() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i % 2 == 1 && j % 2 == 1) {
					grid[i][j] = new Tile(i, j);
					stringRep += " ";
				} else {
					grid[i][j] = new Edge(i, j);
					stringRep += "#";
				}
			}
			stringRep += "\n";
		}
	}
	
	@Override
	public String toString() {
		return stringRep;
	}
	
}