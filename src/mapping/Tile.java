package mapping;

import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Node;
import java.io.*;

public class Tile extends MazeObject implements Serializable {
	
	private static final long serialVersionUID = 0202L;

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
    
    public boolean isWaypointWithin(Waypoint wp) {
    	int maxX = centre.getX() + width/2;
    	int minX = centre.getX() - width/2;
    	int maxY = centre.getY() + height/2;
    	int minY = centre.getY() - height/2 ;
    	if (minX > wp.getX() || wp.getX() > maxX) 
    		return false;
    	if (minY > wp.getY() || wp.getY() > maxY)
    		return false;
    	return true;
    }
}