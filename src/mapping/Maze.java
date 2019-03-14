package mapping;

import java.lang.Math;
import java.util.Stack;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import setup.MazeSolvingRobot;

public class Maze {

	private static final int WIDTH = 19;
	private static final int HEIGHT = 13;

	// (0,0) is the South-Western corner of the grid, and (18,12) its North-Eastern
	private Coordinate[][] topoCoordGrid = new Coordinate[HEIGHT][WIDTH];
	private MazeObject[][] objectGrid = new MazeObject[HEIGHT][WIDTH];

	// test field
	private String stringRep = "";

	public Maze() {
		initialiseCoordinateGrid();
		initialiseMazeObjectGrid();
		initialiseObjectCentres();
		declareBoundaryWalls();
		associateNeighbours();
	}

	private void initialiseCoordinateGrid() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				topoCoordGrid[y][x] = new Coordinate(y, x);
			}
		}
	}

	private void initialiseMazeObjectGrid() {
		Stack<StringBuilder> mazeRows = new Stack<StringBuilder>();
		StringBuilder rowRep = new StringBuilder();

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {				
				if (isIntersection(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Intersection(topoCoordGrid[y][x]);
				else if (isHorizontalEdge(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Horizontal(topoCoordGrid[y][x]);
				else if (isVerticalEdge(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Vertical(topoCoordGrid[y][x]);
				else if (isTile(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Tile(topoCoordGrid[y][x]);

				rowRep.append(objectGrid[y][x].toString());

			}
			
			mazeRows.push(rowRep);
			rowRep = new StringBuilder();

		}

		StringBuilder reversedLineOrder = new StringBuilder();
		while (!mazeRows.isEmpty()) {
			reversedLineOrder.append(mazeRows.pop());
			reversedLineOrder.append("\n");
		}
		stringRep = reversedLineOrder.toString();

	}

	public boolean isIntersection(Coordinate pos) {
		return (pos.getY() % 2 == 0 && pos.getX() % 2 == 0);
	}

	public boolean isHorizontalEdge(Coordinate pos) {
		return (pos.getY() % 2 == 0 && pos.getX() % 2 == 1);
	}

	public boolean isVerticalEdge(Coordinate pos) {
		return (pos.getY() % 2 == 1 && pos.getX() % 2 == 0);
	}

	public boolean isTile(Coordinate pos) {
		return (pos.getY() % 2 == 1 && pos.getX() % 2 == 1);
	}
	
	private void initialiseObjectCentres() {
		int currentX = -25;
		int currentY = -25;
		for (int y = 0; y < HEIGHT; y ++) {
			for (int x = 0; x < WIDTH; x++) {
				MazeObject mO = objectGrid[y][x];
				int centreX = currentX + mO.getWidth()/2;
				int centreY = currentY + mO.getHeight()/2;
				mO.setCentre(centreY, centreX);
				currentX += mO.getWidth();
			}
			currentY += getRow(y)[0].getHeight();
			currentX = -25;
		}
	}

	private void declareBoundaryWalls() {
		for (MazeObject outer : getRow(0)) {
			Edge edge = (Edge) outer;
			// edge.setNoGo();

			// test method
			edge.setBoundary();
		}
		for (MazeObject outer : getRow(HEIGHT - 1)) {
			Edge edge = (Edge) outer;
			// edge.setNoGo();

			// test method
			edge.setBoundary();
		}
		for (MazeObject outer : getColumn(0)) {
			Edge edge = (Edge) outer;
			// edge.setNoGo();

			// test method
			edge.setBoundary();
		}
		for (MazeObject outer : getColumn(WIDTH - 1)) {
			Edge edge = (Edge) outer;
			// edge.setNoGo();

			// test method
			edge.setBoundary();
		}
	}

	public MazeObject[] getRow(int y) {
		return objectGrid[y];
	}

	public MazeObject[] getColumn(int x) {
		MazeObject[] column = new MazeObject[HEIGHT];
		for (int y = 0; y < HEIGHT; y++) {
			column[y] = objectGrid[y][x];
		}
		return column;
	}

	private void associateNeighbours() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				for (Bearing direction : MazeSolvingRobot.getCARDINALS()) {
					try {
						MazeObject newNeighbour = getMazeObject(travelByBearing(getCoordinate(y, x), direction));
						objectGrid[y][x].setNeighbour(newNeighbour, direction);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public MazeObject getMazeObject(Coordinate pos) {
		return objectGrid[pos.getY()][pos.getX()];
	}

	public Tile[] getAdjacentTiles(Tile currentTile) {
		Tile[] adjacentTiles = new Tile[4];
		for (Bearing dir : MazeSolvingRobot.getCARDINALS()) {
			Tile nearestTile = getNearestTile(currentTile, dir);
			try {
					if (!isPathBetweenBlocked(currentTile, nearestTile) && nearestTile.isTraversable()) {
						adjacentTiles[dir.getIntRep()] = nearestTile;
					}
				} catch (Exception e) {
			}
		}
		return adjacentTiles;
	}

	public Coordinate travelByBearing(Coordinate origin, Bearing direction) {
		double angleInRadians = Math.toRadians(direction.getAngle());
		int newY = (int) (origin.getY() + Math.sin(angleInRadians));
		int newX = (int) (origin.getX() + Math.cos(angleInRadians));
		return getCoordinate(newY, newX);
	}

	public Coordinate getCoordinate(int y, int x) {
		return topoCoordGrid[y][x];
	}

	public Tile getNearestTile(Tile origin, Bearing direction) {
		try {
			return (Tile) (origin.getAdjacent(direction).getAdjacent(direction));
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public boolean isPathBetweenBlocked(Tile origin, Tile destination) throws Exception {
		Bearing direction = getBearing(origin, destination);
		Edge sharedEdge1 = (Edge) origin.getAdjacent(direction);

		Edge sharedEdge2 = (Edge) destination.getAdjacent(MazeSolvingRobot.getOpposite(direction));
	
		if (sharedEdge1 != sharedEdge2) {

			throw new Exception("Origin and Destination Tiles do not share a neighbouring Edge");
		}
		return sharedEdge1.isTraversable();
	}

	public Coordinate[][] getCoordinateGrid() {
		return topoCoordGrid;
	}

	public MazeObject[][] getObjectGrid() {
		return objectGrid;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static Bearing getBearing(MazeObject origin, MazeObject destination) throws Exception {
	
		LCD.drawString("Current: " + Integer.toString(origin.getCentre().getY()) + ", " + Integer.toString(origin.getCentre().getX()), 0, 6);
		LCD.drawString("dest: " + Integer.toString(destination.getCentre().getY()) + ", " + Integer.toString(destination.getCentre().getX()), 0, 7);
		Delay.msDelay(10000);
		LCD.clear();
		
		for (Bearing direction : MazeSolvingRobot.getCARDINALS()) {
			if (origin.getAdjacent(direction).equals(destination))
				return direction;
		}
		throw new Exception("Angle between objects is not a Cardinal Direction");
	}

	// test method
	@Override
	public String toString() {
		return stringRep;
	}
}
