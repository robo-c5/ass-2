package mapping;

import java.io.*;

public class Bearing implements Serializable
{

	private static final long	serialVersionUID	= -8992211162525649376L;

	private static final int	ANGULAR_OFFSET		= 90;
	private static final int	MAX_DEGREES			= 360;
	private int					intRep;
	//with respect to the x-axis
	private int					angle;

	public Bearing(int index)
	{
		intRep = index;
		// e.g. north is a 90 degree turn (CCW) from x-axis
		angle = minimiseAngle(ANGULAR_OFFSET * (index));
	}

	//no point turning 270 degrees where we could turn -90. Probably not necessary thanks to navigation but may be handy
	public static int minimiseAngle(int angle)
	{
		if (angle > MAX_DEGREES / 2)
			angle -= MAX_DEGREES;
		return angle;
	}

	public int getIntRep()
	{
		return intRep;
	}

	public int getAngle()
	{
		return angle;
	}

	public boolean equals(Bearing peer)
	{
		if (intRep != peer.getIntRep())
			return false;
		if (angle != peer.getAngle())
			return false;
		return true;
	}
}
