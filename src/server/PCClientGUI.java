package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javafx.application.*;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import mapping.*;

public class PCClientGUI extends Application
{

	private static String				ip		= "localhost";
	private static Socket				sock;

	private static final int			height	= 700;
	private static final int			width	= 760;
	private static final int			offset	= 10;

	private static Maze					maze;
	private static Coordinate			topoPos;
	private static Bearing				direction;

	private static Text					roboPos	= new Text("Not initialised");
	private static Text					heading	= new Text("Not initialised");
	private static ArrayList<Rectangle>	walls	= new ArrayList<Rectangle>();
	private static boolean				end		= false;

	public static void main(String[] args) throws IOException
	{
		sock = new Socket(ip, TestServer.port);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{

		getRobotStats();

		Group mazeRep = new Group();

		roboPos.setX(100);
		roboPos.setY(50);
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));

		heading.setX(100);
		heading.setY(100);
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));

		drawWalls(maze);

		mazeRep.getChildren().addAll(walls);
		mazeRep.getChildren().addAll(roboPos, heading);

		Scene scene = new Scene(mazeRep, width, height);

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
						updateRobotStats();
					}
				};

				while (!end)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException ie)
					{
						ie.printStackTrace();
					}
					getRobotStats();

					Platform.runLater(updater);

				}
				try
				{
					sock.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.setDaemon(true);
		thread.start();

		primaryStage.setTitle("Testing drawing of Maze + text above");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static void getRobotStats()
	{
		try
		{
			InputStream in = sock.getInputStream();
			ObjectInputStream objectInput = new ObjectInputStream(in);

			for (int i = 0; i < 4; i++)
			{
				EV3StatsMessage msg = (EV3StatsMessage) objectInput.readObject();

				if (msg.getType().equals(EV3StatsMessage.MAZE))
					maze = (Maze) msg.getInfo();
				else if (msg.getType().equals(EV3StatsMessage.POSITION))
					topoPos = (Coordinate) msg.getInfo();
				else if (msg.getType().equals(EV3StatsMessage.HEADING))
					direction = (Bearing) msg.getInfo();
				else if (msg.getType().equals(EV3StatsMessage.END))
					end = (boolean) msg.getInfo();
			}
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private static void updateRobotStats()
	{
		drawWalls(maze);
		roboPos.setText("Robot Topological Postion: " + topoPos.toString());
		heading.setText("Robot heading: " + direction.toString());
	}

	private static void drawWalls(Maze testMaze)
	{
		final int PIXEL_PER_CM = 2;

		walls.clear();
		//reuses code from DrawMaze since similarly Rectangles start from their NW corner
		int currentX;
		int currentY = height - offset;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++)
		{
			currentX = offset;
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
				walls.add(wall);
				currentX += pixelWidth;
			}
			currentY -= pixelHeight;
		}
	}
}
