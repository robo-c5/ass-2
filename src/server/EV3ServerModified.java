package server;

import java.io.*;
import java.net.*;

import setup.MazeSolvingRobot;
import server.*;

public class EV3ServerModified {

	public static final int port = 81221;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Awaiting client..");
		Socket client = server.accept();
		System.out.println("CONNECTED");
		
		OutputStream out = client.getOutputStream();
		
		ObjectOutputStream objectOutput = new ObjectOutputStream(out);	
		
		EV3StatsMessage maze = new EV3StatsMessage(MazeSolvingRobot.getMaze(), EV3StatsMessage.MAZE);
		EV3StatsMessage topoPos = new EV3StatsMessage(MazeSolvingRobot.getTopoPosition(), EV3StatsMessage.POSITION);
		EV3StatsMessage heading = new EV3StatsMessage(MazeSolvingRobot.getBearing(), EV3StatsMessage.HEADING);
		
		objectOutput.writeObject(maze);
		
		objectOutput = new ObjectOutputStream(out);		
		objectOutput.writeObject(topoPos);
		
		objectOutput = new ObjectOutputStream(out);		
		objectOutput.writeObject(heading);
		
		server.close();
	}
}
