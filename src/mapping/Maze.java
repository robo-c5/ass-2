package mapping;

import mapping.Edge.EdgeType;

public class Maze {

	final int width = 19;
	final int height = 13; 
	
	String stringRep = "";
	MazeObject[][] grid = new MazeObject[width][height];
	// 0, 0 is the South-West corner edge intersection
	
	public Maze() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (j% 2 == 0) {
					if (i % 2 == 0) {
						grid[i][j] = new Edge(i, j, EdgeType.Intersection);
					} else {
						grid[i][j] = new Edge(i, j, EdgeType.Horizontal);
					} 
				} else {
					if (i % 2 == 0) {
						grid[i][j] = new Edge(i, j, EdgeType.Vertical);
					} else {
						grid[i][j] = new Tile(i, j);
					}	
				}
				stringRep += grid[i][j].toString();
			}
			stringRep += "\n";
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				MazeObject[] adjacents = new MazeObject[4];
				if (j<height-1) {
					adjacents[MazeObject.NORTH] = grid[i][j+1];
				}
				if (j>0) {
					adjacents[MazeObject.SOUTH] = grid[i][j-1];
				}
				if (i<width-1) {
					adjacents[MazeObject.EAST] = grid[i+1][j];
				}					
				if (i>0) {
					adjacents[MazeObject.WEST] = grid[i-1][j];	
				}
				grid[i][j].setNeighbours(adjacents);
			}
		}
	}
	
	@Override
	public String toString() {
		return stringRep;
	}
	
	public MazeObject getMazeObject(int x, int y) {
		return grid[x][y];
	}
	
}