package server;

import java.io.*;
import java.net.*;

import lejos.utility.Delay;
import setup.MazeSolvingRobot;
import mapping.*;

public class EV3Server
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
		int count = 0;
		final int MAX_LOG_COUNT = 10;
		final int MAX_RUN_COUNT = 100;
		while (count < MAX_RUN_COUNT)
		{
			pushStats(out);
			if (count < MAX_LOG_COUNT)
				System.out.println("Sent stats");
			count++;
			Delay.msDelay(1000);
		}
		server.close();
	}

	public static void pushStats(OutputStream out)
	{
		try
		{
			ObjectOutputStream objectOutput = new ObjectOutputStream(out);
			objectOutput.writeObject(grid);
			objectOutput.writeObject(position);
			objectOutput.writeObject(heading);
			//objectOutput.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
