package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;

public class DrawStats
{
	private static final int CM_PER_PIXEL = 3;
	// might be hard to see a wall at the edge of lcd screen

	public static void drawMaze(Maze grid)
	{
		fillIntersections(grid);
		GraphicsLCD gScreen = BrickFinder.getLocal().getGraphicsLCD();
		gScreen.clear();
		int currentX;
		int currentY = gScreen.getHeight();
		int pixelHeight = 0;
		int pixelWidth;
		int startY;
		for (int y = 0; y < Maze.getHEIGHT(); y++)
		{
			currentX = 0;
			for (MazeObject testObject : grid.getRow(y))
			{
				pixelWidth = testObject.getWidth() / CM_PER_PIXEL;
				pixelHeight = testObject.getHeight() / CM_PER_PIXEL;
				startY = currentY - pixelHeight;
				if (testObject.isTraversable())
					gScreen.setColor(GraphicsLCD.WHITE);
				else
					gScreen.setColor(GraphicsLCD.BLACK);
				gScreen.fillRect(currentX, startY, pixelWidth, pixelHeight);
				currentX += pixelWidth + 0;
			}
			currentY -= (pixelHeight + 0);
		}
	}
	
	public static void drawStats(Coordinate position, Bearing heading) {
		LCD.drawString(position.toString(), 4, 0);
		LCD.drawString(heading.toString(), 4, 1);
	}

	public static void fillIntersections(Maze maze) {
		for (int y = 0; y < Maze.getHEIGHT(); y++) {
			for (MazeObject mO: maze.getRow(y)) {
				if (Maze.isIntersection(mO.getTopologicalPosition())) {
					for (MazeObject neighbour : mO.getNeighbours()) {
						if (neighbour != null) {
							if (!neighbour.isTraversable())
								mO.setNoGo();	
						}
					}
				}
			}
		}
	}
	
	public static int getCmPerPixel()
	{
		return CM_PER_PIXEL;
	}
	
	

}