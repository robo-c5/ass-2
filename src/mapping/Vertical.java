package mapping;

import java.io.*;

public class Vertical extends Edge implements Serializable
{

	private static final long serialVersionUID = 2740778450110420051L;

	public Vertical(Coordinate topoPos)
	{
		super(topoPos);
		height = 30;
		width = 10;

		stringRep = "V";
	}
}
