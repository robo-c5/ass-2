package mapping;

import lejos.hardware.lcd.*;

public class DrawMaze {
	private static final int CM_PER_PIXEL = 10;
	
	public static void drawMaze (Maze grid) {
		int currentPixelY = 178;
		int currentPixelX = 0;
		int pixelWidth;
		int pixelHeight = 0;
		int colour = 0;
		
		LCD.clear();		
		for (int y = 0; y < grid.getHEIGHT(); y++) {
			for (MazeObject testObject : grid.getRow(y)) {
				if (testObject.isTraversable()) {
					colour = 1;
				} else {
					colour = 0;
				}
				pixelHeight = testObject.getHeight() / CM_PER_PIXEL;
				pixelWidth = testObject.getWidth() / CM_PER_PIXEL;
				drawObject(currentPixelY, currentPixelX, pixelHeight, pixelWidth, colour);
				currentPixelX += pixelWidth;
			}
			currentPixelY -= pixelHeight;
		}
	}
		
	public static void drawObject(int startY, int startX, int height, int width, int colour) {
		for (int j = 0; j < height; j ++) {
			for (int i = 0; i < width ; i++) {
				LCD.setPixel(startX+i, startY-j, colour);
			}
		}
	}

}