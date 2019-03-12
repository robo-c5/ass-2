package mapping;

import lejos.robotics.pathfinding.Node;
import setup.MazeSolvingRobot;

public abstract class MazeObject {

    protected MazeObject[] neighbours = new MazeObject[4];
    protected boolean visited;
    protected Coordinate topoPos;
    //South-Western corner
    protected Coordinate metricPos;
    protected Coordinate centre;
    protected boolean traversable;
    protected int width;
    protected int height;


    //test field
    protected String stringRep;

    public MazeObject(Coordinate topologicalPosition, Coordinate metricPos) {
        traversable = true;
        visited = false;
        this.topoPos = topologicalPosition;
        this.metricPos = metricPos;

        stringRep = "";
    }

    public MazeObject[] getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbour(MazeObject neighbour, Bearing direction) {
        neighbours[direction.getIntRep()] = neighbour;
    }

    public Coordinate getTopologicalPosition() {
        return topoPos;
    }

    public Coordinate getMetricPos() {
        return metricPos;
    }

    protected void initialiseCentre() {
        int centreY = metricPos.getY() + height/2;
        int centreX = metricPos.getX() + width/2;
        centre = new Coordinate(centreY, centreX);
    }

    public Coordinate getCentre() {
        return centre;
    }

    public MazeObject getAdjacent(Bearing direction) {
        return neighbours[direction.getIntRep()];
    }

    public boolean isTraversable() {
        return traversable;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public void setNoGo() {
    	traversable = false;
    	visited = true;
    }

    //testing method
    @Override
    public String toString() {
        return stringRep;
    }
}