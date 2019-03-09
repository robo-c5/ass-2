package mapping;
public abstract class Edge extends MazeObject {

	protected boolean wall;

    //test field
    protected boolean isBoundary;

	
	public Edge (Coordinate topoPos, Coordinate metricPos) {
        super(topoPos, metricPos);
		traversable = true;
		wall = false;

        //test field
        isBoundary = false;
	}

    public void setWall() {
        traversable = false;
        visited = true;
        wall = true;
    }
    
    public boolean isWall() {
    	return wall;
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