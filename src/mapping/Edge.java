package mapping;

import java.io.*;

public abstract class Edge extends MazeObject implements Serializable
{

	private static final long	serialVersionUID	= -2150729995853066621L;

	//test field
	protected boolean			isBoundary;

	public Edge(Coordinate topoPos)
	{
		super(topoPos);
		traversable = true;

		//test field
		isBoundary = false;
	}

	//testing method
	public void setBoundary()
	{
		isBoundary = true;
		setNoGo();
	}

	//testing method
	public boolean isBoundaryWall()
	{
		return isBoundary;
	}
}