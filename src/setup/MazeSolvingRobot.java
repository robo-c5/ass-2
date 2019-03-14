package setup;

import java.util.Arrays;
import java.util.Stack;
import mapping.*;

public class MazeSolvingRobot extends EV3Setup {

	// move cardinals + getter to somewhere a bit more global so can use in
	private static final Bearing EAST = new Bearing(0);
	private static final Bearing NORTH = new Bearing(1);
	private static final Bearing WEST = new Bearing(2);
	private static final Bearing SOUTH = new Bearing(3);	
	private static final Bearing[] CARDINALS = { EAST, NORTH, WEST, SOUTH };
	private static final Coordinate INITIAL_ORIGIN = getMaze().getCoordinate(1, 1);
	//private static final Rectangle BOUNDARIES = [-15, -15, 380, 380];

	private static Bearing bearing;

	private static Coordinate topoPosition;

	private static Maze maze;
	
	private static Coordinate topoDestination;
	
	private static Stack<Tile> navPath; // a list of the visited tiles, in order, get popped off during backtracking

	public MazeSolvingRobot() {
		setMaze();
		//starting pose should be in centre of startTile (topo (1,1)), pointing to the right (think when)
		setTopoPosition(getMaze().getCoordinate(INITIAL_ORIGIN.getY(), INITIAL_ORIGIN.getX()));
		setBearing(0);
		startArbitrator();
	}
	
	public static Stack<Tile> getNavPath()
	{
		if (navPath == null)
		{
			navPath = new Stack<Tile>();
		}
		return navPath;
	}
	
	public static void addToNavPath(Tile tile)
	{
		getNavPath().push(tile);
	}

	public static void popFromNavPath()
	{
		getNavPath().pop();
	}
	
	public static Tile pollNavPath()
	{
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

	private static void setBearing(int index) {
		if (index >= 0 && index < CARDINALS.length)
			bearing = CARDINALS[index];
	}
	
	private static void setBearing(Bearing direction) {
		if (Arrays.asList(CARDINALS).contains(direction)) {
			bearing = direction;
		}
	}

	public static Coordinate getTopoDestination()
	{
		if (topoDestination == null)
		{
			topoDestination = new Coordinate(0, 0); // just prevents a null destination being returned
		}
		return topoDestination;
	}
	
	private static void setTopoDestination(Coordinate givenDestination)
	{
		topoDestination = givenDestination;
	}

	public static Coordinate getTopoPosition() {
		if (topoPosition == null) {
			setTopoPosition(getMaze().getCoordinate(0, 0)); // probably should throw some kind of error here as position should not be null
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

	public static void rotateTo(Bearing target) {
		int angleDifference = Bearing.minimiseAngle(target.getAngle() - getBearing().getAngle());
		EV3Setup.getPilot().rotate(angleDifference, true);
		setBearing(target);
	}
	
	public static void moveTo(Coordinate topologicalDestination) {
		setTopoDestination(topologicalDestination);
		Coordinate metricDestination = getMaze().getMazeObject(topologicalDestination).getCentre();
		getNav().goTo(metricDestination.getX(), metricDestination.getY());
		setTopoPosition(topologicalDestination);
	}

}
