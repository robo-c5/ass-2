package mapping;
public class Edge extends MazeObject {
	
	enum EdgeType {
		Vertical, Horizontal, Intersection
    }

	private boolean isWall;
	private EdgeType type;

    //test field
    protected boolean isBoundary;

	
	public Edge(Coordinate pos, EdgeType type) {
		super(pos);
		traversable = false;
		isWall = false;
		this.type = type;

        //test field
		stringRep = Character.toString(type.toString().charAt(0));
        isBoundary = true;
	}

    public void setWall() {
        traversable = false;
        visited = true;
        isWall = true;
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