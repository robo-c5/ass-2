package server;

import java.io.Serializable;

public class EV3StatsMessage implements Serializable
{
	private static final long	serialVersionUID	= -4379156030437823345L;

	public static final String	MAZE				= "Maze";
	public static final String	POSITION			= "Coordinate";
	public static final String	HEADING				= "Bearing";
	public static final String	END					= "End";

	private Object				info;
	private String				type;

	public EV3StatsMessage(Object info, String type)
	{
		this.info = info;
		this.type = type;
		;
	}

	public Object getInfo()
	{
		return info;
	}

	public String getType()
	{
		return type;
	}
}
