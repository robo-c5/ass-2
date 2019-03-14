package mapping;

import lejos.robotics.pathfinding.Node;
import setup.MazeSolvingRobot;

public class Tile extends MazeObject {

    private Node node;

    public Tile (Coordinate topoPos, Coordinate metricCentre) {
        super(topoPos, metricCentre);
        traversable = true;
        height = 30;
        width = height;
        centre = metricCentre;
        node = new Node(metricCentre.getX(), metricCentre.getY());

        //test field
        stringRep = "T";
    }
    
    public Node getNode() {
    	return node;
    }
}