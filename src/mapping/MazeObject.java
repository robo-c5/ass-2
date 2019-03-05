package mapping;
public abstract class MazeObject {
	
	private MazeObject[] neighbours = new MazeObject[4];
	private boolean isWall;
	protected boolean traversible;
	protected boolean visited;
	protected Coordinate position;
	protected String stringRep;
	
	public MazeObject(Coordinate position) {
        traversible = true;
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
	
	public Coordinate getPosition() {
		return position;
	}

	public MazeObject getAdjacent(Bearing direction){
	    return neighbours[direction.getIntRep()];
    }

    public void setBoundaryWall() {
	    isWall = true;
    }

    public boolean isBoundaryWall() {
	    traversible = false;
	    visited = true;
	    return isWall;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}