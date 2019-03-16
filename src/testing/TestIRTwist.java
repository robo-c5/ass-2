package testing;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import mapping.Bearing;
import mapping.Coordinate;
import mapping.Maze;
import mapping.MazeObject;
import mapping.Tile;
import setup.MazeSolvingRobot;

public class TestIRTwist {
	
	public static void main(String args[]) {
		//EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		//EV3MediumRegulatedMotor motorC = new EV3MediumRegulatedMotor(MotorPort.C);
		//motorC.setSpeed(360);
		//motorC.setAcceleration(360);
		
		
		new MazeSolvingRobot();
		
		Maze maze = MazeSolvingRobot.getMaze();
		Tile currentTile = (Tile) maze.getMazeObject(MazeSolvingRobot.getTopoPosition());
		
		//checkAdjacentEdges(currentTile);
		LCD.drawString(currentTile.getTopologicalPosition().toString(), 0, 4);
		LCD.drawString(maze.getBearing(currentTile, maze.getMazeObject(maze.getCoordinate(1, 0))).toString(), 0, 5);// MazeSolvingRobot.getBearing().toString(), 0, 5);
		Delay.msDelay(10000);
	}

	private void checkAdjacentEdges(Tile currentTile) {
		LCD.clear();
		for (MazeObject adjacent : currentTile.getNeighbours()) {
			if (adjacent.isVisited()) {
				MazeSolvingRobot.rotateAndScan(MazeSolvingRobot.getMaze().getBearing(currentTile, adjacent));
			}
		}
		MazeSolvingRobot.resetMedMotor();
	}
	
}
