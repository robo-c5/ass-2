package mapping;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Stack;

import lejos.hardware.Sound;
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
		int currentX = 0;
		int currentY = 0;

		Stack<StringBuilder> mazeRows = new Stack<StringBuilder>();
		StringBuilder rowRep = new StringBuilder();

		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Coordinate metricPos = new Coordinate(currentY, currentX);
				if (isIntersection(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Intersection(topoCoordGrid[y][x], metricPos);
				else if (isHorizontalEdge(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Horizontal(topoCoordGrid[y][x], metricPos);
				else if (isVerticalEdge(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Vertical(topoCoordGrid[y][x], metricPos);
				else if (isTile(topoCoordGrid[y][x]))
					objectGrid[y][x] = new Tile(topoCoordGrid[y][x], metricPos);
				currentX += objectGrid[y][x].getWidth();

				rowRep.append(objectGrid[y][x].toString());

			}
			currentY += objectGrid[y][0].getHeight();
			currentX = 0;

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
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		for (Bearing dir : MazeSolvingRobot.getCARDINALS()) { // loop through north east south west
			Tile nearestTile = getNearestTile(currentTile, dir); // get the closest tile to the current tile, in that
																	// direction
			if (nearestTile != null) { // if next to a boundary, at least 1 tile will be null, possibly two
				try {
					if (!isPathBetweenBlocked(currentTile, nearestTile)) { // if there is nothing blocking the two
																			// tiles, add it to the array
						adjacentTiles.add(nearestTile);
					}
				} catch (Exception e) { 
				}
			}

		}
		return new Tile[0];
		// return (Tile[]) adjacentTiles.toArray();
	}

	public Coordinate travelByBearing(Coordinate origin, Bearing direction) {
		double angleInRadians = Math.toRadians(direction.getAngle());
		int newY = (int) (origin.getY() + Math.cos(angleInRadians));
		int newX = (int) (origin.getX() + Math.sin(angleInRadians));
		return getCoordinate(newY, newX);
	}

	public Coordinate getCoordinate(int y, int x) {
		return topoCoordGrid[y][x];
	}

	public Tile getNearestTile(Tile origin, Bearing direction) {
		return (Tile) (origin.getAdjacent(direction).getAdjacent(direction));
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
