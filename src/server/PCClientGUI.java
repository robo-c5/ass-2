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

public class PCClientGUI extends Application {
	
	private static String ip = "localhost";
	private static Socket sock; 
	
	private static final int height = 700;
	private static final int width = 760;
	private static final int offset = 10;
	
	private static Maze maze;
	private static Coordinate topoPos;
	private static Bearing direction;
	private static Text roboPos = new Text();	
	private static Text heading = new Text();
	private static ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	

	public static void main(String[] args) throws IOException {		
		sock = new Socket(ip, TestServer.port);
		launch(args);		
	}
	
	@Override
    public void start(Stage primaryStage) throws Exception {
    	
    	getRobotStats();
    	
    	Group mazeRep = new Group();
    	mazeRep.getChildren().addAll(walls);
    	mazeRep.getChildren().addAll(roboPos, heading);
    	
    	Scene scene = new Scene(mazeRep, width, height);
    	
    	primaryStage.setTitle("Testing drawing of Maze + text above");
    	
    	Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                    	try {
	                        getRobotStats();
	                        drawInfo();
                    	} catch (IOException ioe) {
                    		
                    	}
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	private static void getRobotStats() throws IOException{
		try {
			InputStream in = sock.getInputStream();
			ObjectInputStream objectInput = new ObjectInputStream(in);
			EV3StatsMessage msg = (EV3StatsMessage) objectInput.readObject();
			
			if (msg.getType().equals(EV3StatsMessage.MAZE))
				maze = (Maze) msg.getInfo();
			else if (msg.getType().equals(EV3StatsMessage.POSITION))
				topoPos = (Coordinate) msg.getInfo();
			else if (msg.getType().equals(EV3StatsMessage.HEADING))
				direction = (Bearing) msg.getInfo();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		
		
    }
	
	private static void drawInfo() {
		final int PIXEL_PER_CM = 2;
    	Text roboPos = new Text();		
    	roboPos.setText("Robot Topological Postion: " + topoPos.toString());
    	roboPos.setX(100);
		roboPos.setY(50);		
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));
		
		
		heading.setText("Robot heading: " + direction.toString());
		heading.setX(100);
		heading.setY(100);	
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));
    	
    	//reuses code from DrawMaze since similarly Rectangles start from their NW corner
    	int currentX;
		int currentY = height - offset;
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++) {
			currentX = offset;
			for (MazeObject testObject : maze.getRow(y)) {
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
