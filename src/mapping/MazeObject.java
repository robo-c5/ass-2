package mapping;
public abstract class MazeObject {
	
	protected MazeObject[] neighbours = new MazeObject[4];
	protected boolean isWall;
	protected boolean traversable;
	protected boolean visited;
	protected Coordinate position;
	protected String stringRep;

	//test field
	protected boolean isBoundary;
	
	public MazeObject(Coordinate position) {
        traversable = true;
	    visited = false;
	    isWall = false;
		this.position = position;
		stringRep = "";

		//test field
        isBoundary = true;
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

	public MazeObject getAdjacent(Bearing direction){
	    return neighbours[direction.getIntRep()];
    }

    public void setWall() {
		traversable = false;
		visited = true;
	    isWall = true;
    }

    //testing method
    public void setBoundary() {
	    isBoundary = true;
    }

    //testing method
    public boolean isBoundaryWall() {
	    return isBoundary;
    }

    //testing method
    @Override
    public String toString() {
        return stringRep;
    }
}