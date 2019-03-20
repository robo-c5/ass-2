package testing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mapping.*;
import setup.MazeSolvingRobot;

public class JavaFXTesting extends Application
{
	static Maze			testMaze				= MazeSolvingRobot.getMaze();
	static Coordinate	testPos					= MazeSolvingRobot.getTopoPosition();
	static Bearing		testBearing				= MazeSolvingRobot.getBearing();

	static Group		walls					= new Group();
	static Text			roboPos					= new Text("No position found");
	static Text			heading					= new Text("No heading found");

	static final int	WINDOW_HEIGHT			= 700;
	static final int	WINDOW_WIDTH			= 760;
	static final int	WINDOW_BORDER_OFFSET	= 10;
	static final int	PIXEL_PER_CM			= 2;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		roboPos.setText("Robot position: " + testPos.toString());
		roboPos.setX(100);
		roboPos.setY(50);
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));

		heading.setText("Robot bearing: " + testBearing.toString());
		heading.setX(100);
		heading.setY(100);
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));

		setSomeWalls();

		getWalls();

		Group mazeRep = new Group();
		mazeRep.getChildren().addAll(walls, roboPos, heading);

		Scene scene = new Scene(mazeRep, WINDOW_WIDTH, WINDOW_HEIGHT);

		primaryStage.setTitle("Testing drawing of Maze + text above");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void setSomeWalls()
	{
		testMaze.getMazeObject(testMaze.getCoordinate(1, 2)).setNoGo();
		testMaze.getMazeObject(testMaze.getCoordinate(2, 2)).setNoGo();
		testMaze.getMazeObject(testMaze.getCoordinate(3, 2)).setNoGo();
		testMaze.getMazeObject(testMaze.getCoordinate(6, 1)).setNoGo();
		testMaze.getMazeObject(testMaze.getCoordinate(9, 9)).setNoGo();
	}

	public static void getWalls()
	{
		//reuses code from DrawMaze since similarly Rectangles start from their NW corner
		int currentX;
		int currentY = WINDOW_HEIGHT - WINDOW_BORDER_OFFSET;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++)
		{
			currentX = WINDOW_BORDER_OFFSET;
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