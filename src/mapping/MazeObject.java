package mapping;
public abstract class MazeObject {
	
	protected MazeObject[] neighbours = new MazeObject[4];
	protected boolean visited;
	protected Coordinate position;
    protected boolean traversable;
    protected int width;
	protected int height;

    //test field
	protected String stringRep;
	
	public MazeObject(Coordinate position) {
        traversable = true;
	    visited = false;
		this.position = position;
		stringRep = "";
    }

	public MazeObject[] getNeighbours() {
		return this.neighbours;
	}

    public void setNeighbour(MazeObject neighbour, Bearing direction) {
        neighbours[direction.getIntRep()] = neighbour;
    }
	
	public Coordinate getCoordinates() {
		return position;
	}

	public MazeObject getAdjacent(Bearing direction) {
	    return neighbours[direction.getIntRep()];
    }

    public boolean isTraversable() {
		return traversable;
	}
    
    public void setNonTraversable() {
        traversable = false;
    }

	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited() {
		visited = true;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

    //testing method
    @Override
    public String toString() {
        return stringRep;
    }
}