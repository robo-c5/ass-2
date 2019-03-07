package mapping;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;

public class TestDrawMaze {

	public static void main(String args[]) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Maze testMaze = new Maze();
		DrawMaze.drawMaze(testMaze);
		ev3brick.getKeys().waitForAnyPress();
	}
}
