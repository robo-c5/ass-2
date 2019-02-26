public class Edge extends MazeObject {
	
	enum EdgeType {
		Vertical, Horizontal, Intersection;
	}
	EdgeType type;
	
	public Edge(int x, int y) {
		super(x, y);
		traversible = false;
	}
	
}