package testing.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import setup.MazeSolvingRobot;
import mapping.*;
import server.EV3StatsMessage;

public class RobotStatsServer
{
	public static final int		port	= 7657;
	private static Maze			testMaze;
	private static Coordinate	testPos;
	private static Bearing		testDirection;
	private static boolean		end		= false;

	public static void main(String[] args) throws IOException
	{
		testMaze = new Maze();
		testPos = MazeSolvingRobot.getOrigin();
		testDirection = MazeSolvingRobot.getCARDINALS()[0];

		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");
		Scanner in = new Scanner(System.in);

		while (true)
		{
			OutputStream out = client.getOutputStream();
			pushStatus(out);
		}
	}

	public static void pushStatus(OutputStream out) throws IOException
	{
		EV3StatsMessage maze = new EV3StatsMessage(testMaze, EV3StatsMessage.MAZE);
		EV3StatsMessage topoPos = new EV3StatsMessage(testPos, EV3StatsMessage.POSITION);
		EV3StatsMessage heading = new EV3StatsMessage(testDirection, EV3StatsMessage.HEADING);
		EV3StatsMessage endServer = new EV3StatsMessage(end, EV3StatsMessage.END);

		ObjectOutputStream objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(maze);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(topoPos);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(heading);

		objectOutput = new ObjectOutputStream(out);
		objectOutput.writeObject(endServer);

	}
}
