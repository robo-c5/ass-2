package testing.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import mapping.Bearing;
import mapping.Coordinate;
import mapping.Maze;
import mapping.MazeObject;
import setup.MazeSolvingRobot;
import testing.Rectangle;

public class RobotStatsClientGUI extends Application {
	static String ip = "localhost";
	static Socket sock;
	static Maze maze;
	static Coordinate position;
	static Bearing direction;
	
	static Text heading = new Text("No direction yet");
	static Text roboPos = new Text("No position yet");
	static ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	static Object[] stats;
	
	static final int WINDOW_HEIGHT = 700;
	static final int WINDOW_WIDTH = 760;
	static final int EDGE_OFFSET = 10;

	static final int PIXEL_PER_CM = 2;

	public static void main(String[] args) throws IOException {
		sock = new Socket(ip, LocalHostChatServer.port);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Text debugMessage = new Text();
		debugMessage.setText("CONNECTED");
		debugMessage.setX(50);
		debugMessage.setY(50);
		
		roboPos.setX(100);
		roboPos.setY(50);
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));

		heading.setX(100);
		heading.setY(100);
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));

		Group messages = new Group();
		mazeRep.getChildren().addAll(walls);
		mazeRep.getChildren().addAll(roboPos, heading, debugMessage);

		Scene scene = new Scene(messages, WINDOW_WIDTH, WINDOW_HEIGHT);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						updateMessage();
					}
				};

				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}
					getRobotStats();
					Platform.runLater(updater);
				}
				try {
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}

		});
		thread.setDaemon(true);
		thread.start();

		primaryStage.setTitle("Testing client-server communication displayed on a gui");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static void getRobotStats() {
		try {
			InputStream in = sock.getInputStream();
			ObjectInputStream oIn = new ObjectInputStream(in);
			stats = (Object[]) oIn.readObject();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cfe) {
			cfe.printStackTrace();
		}
	}

	private static void updateMessage() {
		walls = findWalls(maze);
		roboPos.setText("Robot Topological Postion: " + ((Coordinate) stats[1]).toString());
		heading.setText("Robot heading: " + ((Bearing) stats[2]).toString());
		
	}
	
	private static ArrayList<Rectangle> findWalls(Maze testMaze) {
		int currentX;
		int currentY = WINDOW_HEIGHT - EDGE_OFFSET;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		ArrayList<Rectangle> obstacles = new ArrayList<Rectangle>();
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
				obstacles.add(wall);
				currentX += pixelWidth;
			}
			currentY -= pixelHeight;
		}
		return obstacles;
	}
}
