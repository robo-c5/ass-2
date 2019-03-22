package mapping;

import lejos.robotics.navigation.Waypoint;
import setup.MazeSolvingRobot;

import java.io.*;

public class Tile extends MazeObject implements Serializable {

	private static final long serialVersionUID = 5205151117994797242L;

	public Tile(Coordinate topoPos) {
		super(topoPos);
		traversable = true;
		height = 30;
		width = height;

		// test field
		stringRep = "T";
	}

	@Override
	public void setCentre(int y, int x) {
		centre = new Coordinate(y, x);
	}

	public boolean isWaypointWithin(Waypoint wp) {
		int maxX = centre.getX() + width / 2;
		int minX = centre.getX() - width / 2;
		int maxY = centre.getY() + height / 2;
		int minY = centre.getY() - height / 2;
		if (minX > wp.getX() || wp.getX() > maxX)
			return false;
		if (minY > wp.getY() || wp.getY() > maxY)
			return false;
		return true;
	}

	@Override
	public void setVisited() {
		if (!visited) { // only add to navpath if this is the first time the tile has been seen
			MazeSolvingRobot.addToNavPath(this);
			MazeSolvingRobot.getMaze().tileVisited(this);; // also only bother removing from unvisited list if not already done so
		}
		MazeSolvingRobot.incrementVisitedTileCount();
		visited = true;
	}

}