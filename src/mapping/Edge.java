package mapping;
public abstract class Edge extends MazeObject {

	protected boolean isWall;

    //test field
    protected boolean isBoundary;

	
	public Edge (Coordinate topoPos, Coordinate metricPos) {
        super(topoPos, metricPos);
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