package mapping;

import lejos.robotics.pathfinding.Node;
import setup.MazeSolvingRobot;

public class Tile extends MazeObject {

    enum Colour { //eventually move this somewhere else?
        White, Green, Red, Unknown
    }

    Colour colour;

    public Tile (Coordinate topoPos, Coordinate metricPos) {
        super(topoPos, metricPos);
        colour = Colour.Unknown;
        traversable = true;
        height = 30;
        width = height;
        initialiseCentre();

        //test field
        stringRep = "T";
    }

    public void setColour(Colour newColour) {
        colour = newColour;
    }
    
    public Node getNode() {
    	float adjustedX = metricPos.getX() - MazeSolvingRobot.getOrigin().getX();
		float adjustedY = metricPos.getY() - MazeSolvingRobot.getOrigin().getY();
		return new Node(adjustedX, adjustedY);
    }
}