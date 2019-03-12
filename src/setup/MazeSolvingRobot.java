package setup;

import java.util.Arrays;

import mapping.*;

public class MazeSolvingRobot extends EV3Setup {

	// move cardinals + getter to somewhere a bit more global so can use in
	private static final Bearing NORTH = new Bearing(0);
	private static final Bearing EAST = new Bearing(1);
	private static final Bearing SOUTH = new Bearing(2);
	private static final Bearing WEST = new Bearing(3);
	private static final Bearing[] CARDINALS = { NORTH, EAST, SOUTH, WEST };
	private static final Coordinate initialOrigin = getMaze().getMazeObject(getMaze().getCoordinate(1, 1)).getMetricPos();

	private static Bearing bearing;

	private static Coordinate position;

	private static Maze maze;

	public MazeSolvingRobot(int startY, int startX, int bearingIndex) {
		createEV3();
		setMaze();
		setPosition(getMaze().getCoordinate(startY, startX));
		setBearing(bearingIndex);
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

	private static void setBearing(int index) {
		if (index >= 0 && index < CARDINALS.length)
			bearing = CARDINALS[index];
	}
	
	private static void setBearing(Bearing direction) {
		if (Arrays.asList(CARDINALS).contains(direction)) {
			bearing = direction;
		}
	}

	private static void createEV3() {
		new EV3Setup();
	}

	// returns an array of 2 ints, y-pos then x-pos
	public static Coordinate getPosition() {
		if (position == null) {
			setPosition(getMaze().getCoordinate(0, 0)); // probably should throw some kind of error here as position should not be null
		}
		return position;
	}

	public static void setPosition(Coordinate location) {
		position = getMaze().getCoordinate(location.getY(), location.getX());
	}

	public static void rotateTo(Bearing target) {
		int angleDifference = Bearing.minimiseAngle(target.getAngle() - getBearing().getAngle());
		EV3Setup.getPilot().rotate(angleDifference);
		setBearing(target);
	}
	
	public static void moveTo(Coordinate destination) {
		int adjustedX = destination.getX() - initialOrigin.getX();
		int adjustedY = destination.getY() - initialOrigin.getY();
		EV3Setup.getNav().goTo(adjustedX, adjustedY);
		setPosition(destination);
	}
}
