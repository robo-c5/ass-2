package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.*;
import lejos.hardware.lcd.*;

public class DrawMaze {
	private static final int RESOLUTION = 10;
	private static GraphicsLCD screen;
	
	
	public DrawMaze() {
		screen = BrickFinder.getDefault().getGraphicsLCD();		
	}
	
	public static void drawMaze (Maze grid) {
		int currentPixelY = screen.getHeight();
		int currentPixelX = 0;
		int adjustY = 0;
		for (int y = 0; y < grid.getHEIGHT(); y++) {
			for (MazeObject testObject : grid.getRow(y)) {
				if (testObject.isTraversable()) {
					screen.setColor(0,0,0);
				} else {
					screen.setColor(255,255,255);
				}
				drawObject(currentPixelY, currentPixelX, testObject);
				currentPixelX += testObject.getWidth();
				adjustY = testObject.getWidth() / RESOLUTION;
			}
			currentPixelY -= adjustY;
		}
	}
		
	public static void drawObject(int y, int x,  MazeObject wall) {
		screen.fillRect(x, y, wall.getWidth(), wall.getHeight());
	}
	
	public GraphicsLCD getScreen() {
		return screen;
	}

}