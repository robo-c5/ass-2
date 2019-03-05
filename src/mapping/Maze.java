package mapping;

import mapping.Edge.EdgeType;
import java.lang.Math;

public class Maze {

    static final Bearing NORTH = new Bearing(0);
    static final Bearing EAST = new Bearing(1);
    static final Bearing SOUTH = new Bearing(2);
    static final Bearing WEST = new Bearing(3);
    static final Bearing[] CARDINALS = {NORTH, EAST, SOUTH, WEST};
	static final int WIDTH = 19;
	static final int HEIGHT = 13;
	
	String stringRep = "";
	Coordinate[][] coordinateGrid = new Coordinate[HEIGHT][WIDTH];
	MazeObject[][] objectGrid = new MazeObject[HEIGHT][WIDTH];
	// 0, 0 is the South-West corner edge intersection
	
	public Maze() {
        initialiseCoordinateGrid();
        initialiseMazeObjectGrid();
        declareBoundaryWalls();
		associateNeighbours();
	}

	private void initialiseCoordinateGrid() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                coordinateGrid[y][x] = new Coordinate(y,x);
            }
        }
    }

    private void initialiseMazeObjectGrid() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (isIntersection(coordinateGrid[y][x]))
                    objectGrid[y][x] = new Edge(coordinateGrid[y][x], EdgeType.Intersection);
                else if (isHorizontalEdge(coordinateGrid[y][x]))
                    objectGrid[y][x] = new Edge(coordinateGrid[y][x], EdgeType.Horizontal);
                else if (isVerticalEdge(coordinateGrid[y][x]))
                    objectGrid[y][x] = new Edge(coordinateGrid[y][x], EdgeType.Vertical);
                else if (isTile(coordinateGrid[y][x]))
                    objectGrid[y][x] = new Tile(coordinateGrid[y][x]);
                stringRep += (objectGrid[y][x].toString());
            }
            stringRep += ("\n");
        }
    }

	private void associateNeighbours() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                for (Bearing direction : CARDINALS) {
                    try {
                        MazeObject newNeighbour = getMazeObject(travelByBearing(getCoordinate(y,x), direction));
                        objectGrid[y][x].setNeighbour(newNeighbour, direction);
                    } catch (Exception e) {
                        break;
                    }
                }

            }
        }
    }

    private void declareBoundaryWalls() {
	    for (MazeObject outer : getRow(0)) {
	        outer.setBoundaryWall();
        }
        for (MazeObject outer : getRow(HEIGHT-1)) {
            outer.setBoundaryWall();
        }
        for (MazeObject outer : getColumn(0)) {
            outer.setBoundaryWall();
        }
        for (MazeObject outer : getColumn(WIDTH-1)) {
            outer.setBoundaryWall();
        }
    }

    public Coordinate travelByBearing(Coordinate origin, Bearing direction) {
        int newY = (int) (origin.getY() + Math.cos(Math.toRadians(direction.getAngle())));
        //for angle = 90 this line should increment x by 1. But it don't.
        int newX = (int) (origin.getX() + Math.sin(Math.toRadians(direction.getAngle())));
        return getCoordinate(newY, newX);
    }
	
	public MazeObject getMazeObject(Coordinate pos) {
		return objectGrid[pos.getY()][pos.getX()];
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

    public Coordinate getCoordinate(int y, int x) {
        return coordinateGrid[y][x];
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

    @Override
    public String toString() {
        return stringRep;
    }
}
