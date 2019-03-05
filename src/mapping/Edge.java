package mapping;
public class Edge extends MazeObject {
	
	enum EdgeType {
		Vertical, Horizontal, Intersection;
	}
	
	EdgeType type;
	
	public Edge(Coordinate pos, EdgeType type) {
		super(pos);
		traversible = false;
		this.type = type;
		stringRep = Character.toString(type.toString().charAt(0));
	}
	
}