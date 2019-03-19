package testing;

import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import mapping.Bearing;
import mapping.Coordinate;
import mapping.Tile;
import setup.MazeSolvingRobot;

public class RobotMovementTest {

	public static Coordinate coord;
	public static Bearing bearing;

	public static void main(String[] args) {
		// new MazeSolvingRobot();

		// MazeSolvingRobot.setTopoPosition(MazeSolvingRobot.getOrigin());
		// MazeSolvingRobot.setBearing(0);
		coord = new Coordinate(1, 1);
		bearing = new Bearing(0);

		Bearing[] list = new Bearing[] { new Bearing(1) , new Bearing(1), new Bearing(1), new Bearing(1) };
		
		for (int i = 0; i < list.length; i++) {
			Tile tile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(coord);

			Tile target = null;// = (Tile) MazeSolvingRobot.getMaze()
			//		.getMazeObject(MazeSolvingRobot.getMaze().travelByBearing(coord, list[i]));

			for (Tile adj : MazeSolvingRobot.getMaze().getNearbyReachableTiles(tile))
			{
				if (adj != null && MazeSolvingRobot.getMaze().getBearing(tile, adj).getAngle() == list[i].getAngle())
				{
					target = adj;
					break;
				}
			}

			System.out.println("Current coord: " + coord.toString());
			System.out.println("Target coord: " + target.getTopologicalPosition().toString());

			System.out.println("Current bearing: " + bearing.getAngle());

			System.out.println("Bearing from current to target: "
					+ MazeSolvingRobot.getMaze().getBearing(tile, target).toString());

			Delay.msDelay(5000);
			System.out.println("--------");
			System.out.println("");
			
			//rotate
			bearing = list[i];
			//move
			coord = target.getTopologicalPosition();
		}

	}

}
