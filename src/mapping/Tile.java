package mapping;

import lejos.robotics.pathfinding.Node;

public class Tile extends MazeObject {

    private Node node;

    public Tile (Coordinate topoPos) {
        super(topoPos);
        traversable = true;
        height = 30;
        width = height;

        //test field
        stringRep = "T";
    }
    
    public Node getNode() {
    	return node;
    }
    
    @Override
    public void setCentre(int y, int x) {
    	centre = new Coordinate(y, x);
    	setNode(x, y);
    }
    
    private void setNode(int x, int y) {
    	node = new Node(x, y);
    }
}