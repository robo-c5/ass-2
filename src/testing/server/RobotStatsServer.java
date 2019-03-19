package testing.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import setup.MazeSolvingRobot;
import mapping.*;

//pushes Robot initial stats to client server then exits
public class RobotStatsServer {
	public static final int port = 7657;
	private static Maze testMaze;
	private static Coordinate testPos;
	private static Bearing testDirection;

	public static void main(String[] args) throws IOException {
		testMaze = new Maze();
		testPos = MazeSolvingRobot.getOrigin();
		testDirection = MazeSolvingRobot.getCARDINALS()[0];

		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");

		OutputStream out = client.getOutputStream();
		pushStatus(out);
		server.close();
	}

	public static void pushStatus(OutputStream out) throws IOException {

		ObjectOutputStream objectOutput = new ObjectOutputStream(out);
		Object[] stats = { testMaze, testPos, testDirection };
		objectOutput.writeObject(stats);
	}
}
