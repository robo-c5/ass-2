package testing;

import java.util.ArrayList;

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
 
public class JavaFXTesting extends Application {
    public static void main(String[] args) { 
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	final int height = 700;
    	final int width = 760;
    	final int offset = 10;
    	
    	final int PIXEL_PER_CM = 2;
    	Text roboPos = new Text();		
    	roboPos.setText("Robot Topological Postion: " + MazeSolvingRobot.getTopoPosition().toString());
    	roboPos.setX(100);
		roboPos.setY(50);		
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));
		
		Text heading = new Text();
		
		heading.setText("Robot heading: " + MazeSolvingRobot.getBearing().toString());
		heading.setX(100);
		heading.setY(100);	
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));
		
    	Maze testMaze = MazeSolvingRobot.getMaze();    	
    	ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
    	
    	//reuses code from DrawMaze since similarly Rectangles start from their NW corner
    	int currentX;
		int currentY = height - offset;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++) {
			currentX = offset;
			for (MazeObject testObject : testMaze.getRow(y)) {
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
		
		Group mazeRep = new Group();
    	mazeRep.getChildren().addAll(walls);
    	mazeRep.getChildren().addAll(roboPos, heading);
    	
    	Scene scene = new Scene(mazeRep, width, height);
    	
    	primaryStage.setTitle("Testing drawing of Maze + text above");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}