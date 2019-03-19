package testing.server;

import java.io.*;
import java.net.*;

import mapping.*;

//receives initial Robot stats from server, prints to console, then exits
public class RobotStatsClient {
	static String ip = "localhost";
	static Socket sock;
	static Maze maze;
	static Coordinate position;
	static Bearing heading;

	public static void main(String[] args) throws IOException {
		sock = new Socket(ip, LocalHostChatServer.port);
		System.out.println("Connected");
		getRobotStats();
		sock.close();
	}

	private static void getRobotStats() {
		try {
			InputStream in = sock.getInputStream();
			ObjectInputStream oIn = new ObjectInputStream(in);
			Object[] stats = (Object[]) oIn.readObject();
			maze = (Maze) stats[0];
			position = (Coordinate) stats[1];
			heading = (Bearing) stats[2];

			System.out.println();
			System.out.println(maze.toString());
			System.out.println();
			System.out.println("Current position: " + position.toString());
			System.out.println();
			System.out.println("Current heading: " + heading.toString());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cfe) {
			cfe.printStackTrace();
		}
	}
}
