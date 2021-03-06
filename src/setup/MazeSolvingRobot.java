package setup;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Stack;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import mapping.*;
import server.EV3Server;

public class MazeSolvingRobot extends EV3Setup {

	// move cardinals + getter to somewhere a bit more global so can use in
	private static final Bearing EAST = new Bearing(0) {
		private static final long serialVersionUID = 2805808181918454266L;

		@Override
		public String toString() {
			return "East";
		}
	};
	private static final Bearing NORTH = new Bearing(1) {
		private static final long serialVersionUID = -5102790217240141415L;

		@Override
		public String toString() {
			return "North";
		}
	};
	private static final Bearing WEST = new Bearing(2) {
		private static final long serialVersionUID = 1846638573309292989L;

		@Override
		public String toString() {
			return "West";
		}
	};
	private static final Bearing SOUTH = new Bearing(3) {
		private static final long serialVersionUID = -8171737250803624310L;

		@Override
		public String toString() {
			return "South";
		}
	};
	private static final Bearing[] CARDINALS = { EAST, NORTH, WEST, SOUTH };
	private static final Coordinate INITIAL_ORIGIN = getMaze().getCoordinate(1, 1);
	// private static final Rectangle BOUNDARIES = [-15, -15, 380, 380];

	private static Bearing bearing;

	private static Coordinate topoPosition;

	private static Maze maze;

	private static Coordinate topoDestination;

	private static Stack<Tile> navPath; // a list of the visited tiles, in order, get popped off during backtracking

	private static boolean redFound = false;

	private static int visitedTileCount = 0;

	private static boolean end;

	private static Tile endTile;

	private static final int TILE_SIZE = 40;
	
	public static Coordinate END_TILE;

	public MazeSolvingRobot() {
		setMaze();
		// starting pose should be in centre of startTile (topo (1,1)), pointing to the
		// right (think when)
		setTopoPosition(getMaze().getCoordinate(INITIAL_ORIGIN.getY(), INITIAL_ORIGIN.getX()));
		setBearing(0);
		startArbitrator();
	}

	public static Stack<Tile> getNavPath() {
		if (navPath == null) {
			navPath = new Stack<Tile>();
		}
		return navPath;
	}

	public static void addToNavPath(Tile tile) {
		getNavPath().push(tile);
	}

	public static void popFromNavPath() {
		getNavPath().pop();
	}

	public static Tile pollNavPath() {
		return getNavPath().peek();
	}

	public static Maze getMaze() {
		if (maze == null) {
			setMaze();
		}
		return maze;
	}

	public static void setMaze() {
		maze = new Maze();
	}

	public static Bearing[] getCARDINALS() {
		return CARDINALS;
	}

	public static Bearing getBearing() {
		if (bearing == null) {
			setBearing(0);
		}
		return bearing;
	}

	public static void setBearing(int index) {
		if (index >= 0 && index < CARDINALS.length)
			bearing = CARDINALS[index];
	}

	private static void setBearing(Bearing direction) {
		if (Arrays.asList(CARDINALS).contains(direction)) {
			bearing = direction;
		}
	}

	public static Coordinate getTopoDestination() {
		if (topoDestination == null) {
			topoDestination = new Coordinate(0, 0); // just prevents a null destination being returned
		}
		return topoDestination;
	}

	public static Coordinate getTopoPosition() {
		if (topoPosition == null) {
			setTopoPosition(getMaze().getCoordinate(0, 0)); // probably should throw some kind of error here as position
															// should not be null
		}
		return topoPosition;
	}

	public static Coordinate getOrigin() {
		return INITIAL_ORIGIN;
	}

	public static void setTopoPosition(Coordinate topoLocation) {
		topoPosition = getMaze().getCoordinate(topoLocation.getY(), topoLocation.getX());
	}

	public static Bearing getOpposite(Bearing direction) throws Exception {
		for (int i = 0; i < MazeSolvingRobot.getCARDINALS().length; i++) {
			if (direction == MazeSolvingRobot.getCARDINALS()[i]) {
				return MazeSolvingRobot.getCARDINALS()[(i + MazeSolvingRobot.getCARDINALS().length / 2) % 4];
			}
		}
		throw new Exception("Bearing not found in list of Cardinal directions");
	}

	public static void rotateRobotTo(Bearing target) {
		int angleDifference = Bearing.minimiseAngle(target.getAngle() - getBearing().getAngle());
		getPilot().rotate(angleDifference);
		setBearing(target);
		drawStats();
	}

	public static float rotateAndScan(Bearing givenBearing) {
		int angleDifference = Bearing.minimiseAngle(givenBearing.getAngle() - getBearing().getAngle());
		getirMotor().rotateTo(-angleDifference);
		return getIRSample();
	}

	public static void resetMedMotor() {
		getirMotor().rotateTo(0);
	}

	public static boolean getEnd() {
		return end;
	}

	public static void end() {
		end = true;
	}

	public static void moveByATile() {
		getPilot().travel(TILE_SIZE);
		drawStats();
	}

	public static boolean isRedFound() {
		return redFound;
	}

	public static int getVisitedTileCount() {
		return visitedTileCount;
	}

	public static void incrementVisitedTileCount() {
		visitedTileCount++;
	}

	public static Tile getEndTile() {
		return endTile;
	}

	public static void setEndTile(Coordinate end) {
		endTile = (Tile) getMaze().getMazeObject(end);
		redFound = true;
	}
	
	public static void drawStats() {
		DrawStats.drawMaze(getMaze());
		DrawStats.drawStats(topoPosition, bearing);
	}

}