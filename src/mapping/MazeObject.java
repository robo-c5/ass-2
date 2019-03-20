package mapping;

import java.io.*;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public abstract class MazeObject implements Serializable
{

	private static final long	serialVersionUID	= -2036641777773777264L;

	protected MazeObject[]		neighbours			= new MazeObject[4];
	protected boolean			visited;
	protected Coordinate		topoPos;
	protected Coordinate		centre;
	protected boolean			traversable;
	protected int				width;
	protected int				height;

	//test field
	protected String			stringRep;

	public MazeObject(Coordinate topologicalPosition)
	{
		traversable = true;
		visited = false;
		this.topoPos = topologicalPosition;

		stringRep = "";
	}

	public MazeObject[] getNeighbours()
	{
		return this.neighbours;
	}

	public void setNeighbour(MazeObject neighbour, Bearing direction)
	{
		neighbours[direction.getIntRep()] = neighbour;
	}

	public Coordinate getTopologicalPosition()
	{
		return topoPos;
	}

	public Coordinate getCentre()
	{
		return centre;
	}

	public void setCentre(int y, int x)
	{
		centre = new Coordinate(y, x);
	}

	//ordered with respect to initial bearing (x-axis +ve direction) and direction of positive turn
	public Coordinate[] getCorners()
	{
		int startX = centre.getX() - width / 2;
		int startY = centre.getY() - height / 2;
		int endX = centre.getX() + height / 2;
		int endY = centre.getX() + height / 2;
		Coordinate NECorner = new Coordinate(endY, endX);
		Coordinate NWCorner = new Coordinate(endY, startX);
		Coordinate SWCorner = new Coordinate(startY, startX);
		Coordinate SECorner = new Coordinate(startY, endX);
		Coordinate[] corners = { NECorner, NWCorner, SWCorner, SECorner };
		return corners;
	}

	public MazeObject getAdjacent(Bearing direction)
	{
		return neighbours[direction.getIntRep()];
	}

	public boolean isTraversable()
	{
		return traversable;
	}

	public boolean isVisited()
	{
		return visited;
	}

	public void setVisited()
	{
		visited = true;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setNoGo()
	{
		traversable = false;
		visited = true;
	}

	public boolean equals(MazeObject peer)
	{
		if (height != peer.getHeight())
			return false;
		if (width != peer.getWidth())
			return false;
		if (visited != peer.isVisited())
			return false;
		if (traversable != peer.isTraversable())
			return false;
		if (!topoPos.equals(peer.getTopologicalPosition()))
			return false;
		if (!centre.equals(peer.getCentre()))
			return false;
		if (!neighbours.equals(peer.getNeighbours()))
			return false;
		return true;
	}

	//testing method
	@Override
	public String toString()
	{
		return stringRep;
	}
}