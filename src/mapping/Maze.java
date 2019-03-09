package mapping;

import java.lang.Math;
import java.util.Stack;

public class Maze {
    //move cardinals + getter to somewhere a bit more global so can use in
    private static final Bearing NORTH = new Bearing(0);
    private static final Bearing EAST = new Bearing(1);
    private static final Bearing SOUTH = new Bearing(2);
    private static final Bearing WEST = new Bearing(3);
    private static final Bearing[] CARDINALS = {NORTH, EAST, SOUTH, WEST};
	private static final int WIDTH = 19;
	private static final int HEIGHT = 13;

	//(0,0) is the South-Western corner of the grid, and (18,12) its North-Eastern
    private Coordinate[][] topoCoordGrid = new Coordinate[HEIGHT][WIDTH];
    private MazeObject[][] objectGrid = new MazeObject[HEIGHT][WIDTH];

    //test field
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
                topoCoordGrid[y][x] = new Coordinate(y,x);
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
            reversedLineOrder .append(mazeRows.pop());
            reversedLineOrder .append("\n");
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
            //edge.setWall();

            //test method
            edge.setBoundary();
        }
        for (MazeObject outer : getRow(HEIGHT-1)) {
            Edge edge = (Edge) outer;
            //edge.setWall();

            //test method
            edge.setBoundary();
        }
        for (MazeObject outer : getColumn(0)) {
            Edge edge = (Edge) outer;
            //edge.setWall();

            //test method
            edge.setBoundary();
        }
        for (MazeObject outer : getColumn(WIDTH-1)) {
            Edge edge = (Edge) outer;
            //edge.setWall();

            //test method
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
                for (Bearing direction : CARDINALS) {
                    try {
                        MazeObject newNeighbour = getMazeObject(travelByBearing(getCoordinate(y,x), direction));
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

    public boolean isPathBetweenBlocked (Tile origin, Tile destination, Bearing direction) throws Exception {
	    Edge sharedEdge1 = (Edge) origin.getAdjacent(direction);
	    Edge sharedEdge2 = (Edge) destination.getAdjacent(getOpposite(direction));
	    if (sharedEdge1 != sharedEdge2) {
	        throw new Exception("Origin and Destination Tiles do not share a neighbouring Edge");
        }
	    return sharedEdge1.isWall();
    }

    public static Bearing[] getCARDINALS() {
        return CARDINALS;
    }

    public Bearing getOpposite(Bearing direction) throws Exception{
	    for (int i = 0; i < CARDINALS.length; i++) {
	        if (direction == CARDINALS[i]) {
                return CARDINALS[(i + CARDINALS.length / 2) % 4];
            }
        }
        throw new Exception("Bearing not found in list of Cardinal directions");

    }

    public Coordinate[][] getCoordinateGrid() {
        return topoCoordGrid;
    }

    public MazeObject[][] getObjectGrid() {
        return objectGrid;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    //test method
    @Override
    public String toString() {
        return stringRep;
    }
}
