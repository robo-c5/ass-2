package mapping;
public abstract class Edge extends MazeObject {

	protected boolean isWall;

    //test field
    protected boolean isBoundary;

	
	public Edge(Coordinate pos) {
		super(pos);
		traversable = true;
		isWall = false;

        //test field
        isBoundary = false;
	}

    public void setWall() {
        traversable = false;
        visited = true;
        isWall = true;
    }
    
    public boolean isWall() {
    	return isWall;
    }

    //testing method
    public void setBoundary() {
        isBoundary = true;
        setWall();
    }

    //testing method
    public boolean isBoundaryWall() {
        return isBoundary;
    }
}