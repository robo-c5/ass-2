package mapping;
public class Edge extends MazeObject {
	
	enum EdgeType {
		Vertical, Horizontal, Intersection;
	}
	
	EdgeType type;
	
	public Edge(int x, int y, EdgeType type) {
		super(x, y);
		traversible = false;
		this.type = type;
		stringRep += type.toString().charAt(0);
	}
	
}