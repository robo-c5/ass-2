package server;

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

import mapping.Bearing;
import mapping.Coordinate;
import mapping.Maze;
import mapping.MazeObject;

public class PCClientGUI extends Application
{
	private static String		ip				= "localhost";
	private static Socket		sock;

	private static Maze			maze;
	private static Coordinate	position;
	private static Bearing		direction;

	private static Text			heading			= new Text("No direction yet");
	private static Text			roboPos			= new Text("No position yet");
	private static Group		walls			= new Group();

	private static final int	WINDOW_HEIGHT	= 700;
	private static final int	WINDOW_WIDTH	= 760;
	private static final int	EDGE_OFFSET		= 10;
	private static final int	PIXEL_PER_CM	= 2;

	public static void main(String[] args) throws IOException
	{
		sock = new Socket(ip, EV3Server.port);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		Text debugMessage = new Text();
		debugMessage.setText("CONNECTED");
		debugMessage.setX(EDGE_OFFSET);
		debugMessage.setY(EDGE_OFFSET);

		roboPos.setX(100);
		roboPos.setY(50);
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));

		heading.setX(100);
		heading.setY(100);
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));

		Group messages = new Group();
		messages.getChildren().addAll(walls, roboPos, heading, debugMessage);

		Scene scene = new Scene(messages, WINDOW_WIDTH, WINDOW_HEIGHT);

		Thread thread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Runnable updater = new Runnable()
				{

					@Override
					public void run()
					{
						updateMessage();
					}
				};
				while (true)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException ex)
					{
					}
					getRobotStats();
					Platform.runLater(updater);
				}
			}

		});
		thread.setDaemon(true);
		thread.start();

		primaryStage.setTitle("Testing client-server communication displayed on a gui");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static void getRobotStats()
	{
		try
		{
			InputStream in = sock.getInputStream();
			ObjectInputStream objectInput = new ObjectInputStream(in);
			maze = (Maze) objectInput.readObject();
			position = (Coordinate) objectInput.readObject();
			direction = (Bearing) objectInput.readObject();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cfe)
		{
			cfe.printStackTrace();
		}
	}

	private static void updateMessage()
	{
		findWalls(maze);
		roboPos.setText("Robot position: " + position.toString());
		heading.setText("Robot bearing: " + direction.toString());
	}

	private static void findWalls(Maze testMaze)
	{
		int currentX;
		int currentY = WINDOW_HEIGHT - EDGE_OFFSET;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++)
		{
			currentX = EDGE_OFFSET;
			for (MazeObject testObject : testMaze.getRow(y))
			{
				pixelWidth = testObject.getWidth() * PIXEL_PER_CM;
				pixelHeight = testObject.getHeight() * PIXEL_PER_CM;
				startY = currentY - pixelHeight;
				Rectangle wall = new Rectangle(currentX, startY, pixelWidth, pixelHeight);
				if (!testObject.isTraversable())
					wall.setFill(javafx.scene.paint.Color.BLACK);
				else
					wall.setFill(javafx.scene.paint.Color.WHITE);
				walls.getChildren().add(wall);
				currentX += pixelWidth;
			}
			currentY -= pixelHeight;
		}
	}
}
