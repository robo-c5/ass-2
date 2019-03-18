package server;

import java.io.*;
import java.net.*;
import lejos.hardware.Battery;
import setup.MazeSolvingRobot;

/**
 * Maximum LEGO EV3: Building Robots with Java Brains
 * ISBN-13: 9780986832291
 * Variant Press (C) 2014
 * Chapter 14 - Client-Server Robotics
 * Robot: EV3 Brick
 * Platform: LEGO EV3
 * @author Brian Bagnall
 * @version July 20, 2014
 */
public class EV3Server {

	public static final int port = 81221;
	
	public static final String MAZE = "Maze";
	public static final String POSITION = "Coordinate";
	public static final String HEADING = "Bearing";
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");
		
		OutputStream out = client.getOutputStream();
		
		ObjectOutputStream objectOutput = new ObjectOutputStream(out);	
		
		EV3StatsMessage maze = new EV3StatsMessage(MazeSolvingRobot.getMaze(), MAZE);
		EV3StatsMessage topoPos = new EV3StatsMessage(MazeSolvingRobot.getTopoPosition(), POSITION);
		EV3StatsMessage heading = new EV3StatsMessage(MazeSolvingRobot.getBearing(), HEADING);
		
		objectOutput.writeObject(maze);
		
		objectOutput = new ObjectOutputStream(out);		
		objectOutput.writeObject(topoPos);
		
		objectOutput = new ObjectOutputStream(out);		
		objectOutput.writeObject(heading);
		
		server.close();
	}
}
