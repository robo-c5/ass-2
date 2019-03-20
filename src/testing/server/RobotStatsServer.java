package testing.server;

import java.io.*;
import java.net.*;

import setup.MazeSolvingRobot;
import mapping.*;

public class RobotStatsServer
{

	public static final int		port	= 7657;

	private static Maze			grid;
	private static Coordinate	position;
	private static Bearing		heading;

	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");

		grid = MazeSolvingRobot.getMaze();
		position = MazeSolvingRobot.getTopoPosition();
		heading = MazeSolvingRobot.getBearing();

		OutputStream out = client.getOutputStream();
		pushStats(out);

		System.out.println("Sent stats");
		server.close();
		System.out.println("Closed socket");
	}

	public static void pushStats(OutputStream out)
	{
		try
		{
			ObjectOutputStream objectOutput = new ObjectOutputStream(out);
			objectOutput.writeObject(grid);
			objectOutput.writeObject(position);
			objectOutput.writeObject(heading);
			objectOutput.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
