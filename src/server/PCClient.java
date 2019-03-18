package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import mapping.*;
import setup.MazeSolvingRobot;
import testing.Group;
import testing.Rectangle;
import testing.Scene;
import testing.Stage;
import testing.Text;

public class PCClient extends Application {
	
	private static boolean run = true;
	private static Maze maze;
	private static Coordinate topoPos;
	private static Bearing heading;
	private static String ip = "10.0.1.1"; // BT
	private static Socket sock = new Socket(ip, EV3Server.port);

	public static void main(String[] args) throws IOException {		
		launch(args);		
	}
	
	@Override
    public void start(Stage primaryStage) throws Exception {
    	final int height = 700;
    	final int width = 760;
    	final int offset = 10;
    	
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
                        getRobotStats();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
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
			
			
			if (msg.getType().equals(EV3Server.MAZE))
				maze = (Maze) msg.getInfo();
			else if (msg.getType().equals(EV3Server.POSITION))
				topoPos = (Coordinate) msg.getInfo();
			else if (msg.getType().equals(EV3Server.HEADING))
				heading = (Bearing) msg.getInfo();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				run = false;
			}
		
		
    }
	
	private static void drawInfo() throws Exception {
		final int PIXEL_PER_CM = 2;
    	Text roboPos = new Text();		
    	roboPos.setText("Robot Topological Postion: " + topoPos.toString());
    	roboPos.setX(100);
		roboPos.setY(50);		
		roboPos.setFont(Font.font("null", FontWeight.BOLD, 36));
		
		Text heading = new Text();
		
		heading.setText("Robot heading: " + heading.toString());
		heading.setX(100);
		heading.setY(100);	
		heading.setFont(Font.font("null", FontWeight.BOLD, 36));
		  	
    	ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
    	
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
