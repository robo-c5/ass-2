package behaviours;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import lejos.robotics.navigation.*;
import lejos.robotics.pathfinding.*;
import lejos.robotics.subsumption.Behavior;
import mapping.*;
import navigation.*;

import setup.*;

public class RedTile implements Behavior {

	@Override
	public boolean takeControl() {
		return isRed(MazeSolvingRobot.getColourSample());
	}

	@Override
	public void suppress() {
		//cannot be suppressed
	}
	
	//general procedure once detect Red: turn around (wide eyes), return to previous position. Then find shortest path back to start tile then take it.
	@Override
	public void action() {
		try {
			MazeSolvingRobot.rotateTo(MazeSolvingRobot.getOpposite(MazeSolvingRobot.getBearing()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
		MazeSolvingRobot.moveTo(MazeSolvingRobot.getTopoPosition());
		Coordinate currentMetricPos = MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition()).getCentre();
		float x = currentMetricPos.getX();
		float y = currentMetricPos.getY();
		Pose start = new Pose(x, y, MazeSolvingRobot.getBearing().getAngle());
		Waypoint goal = new Waypoint(0,0);		
		AstarSearchAlgorithm searchAlg = new AstarSearchAlgorithm();
		TileGrid gridMesh = convertMaze();
		NodePathFinder npf = new NodePathFinder (searchAlg, gridMesh);
		try {
			Path path = npf.findRoute(start, goal);
			Queue<Tile> route = parsePath(path);
		} catch (DestinationUnreachableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private boolean isRed(float[] sample) {
		if (0.212 < sample[0] && sample[0] < 0.253 ) {
			if (0.049 < sample[1] && sample[1] < 0.063) {
				if (0.047 < sample[2] && sample[2] < 0.056) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static TileGrid convertMaze() {
		Maze mazeMap = MazeSolvingRobot.getMaze();
		ArrayList<Tile> tempTileSet = new ArrayList<Tile>();
		for (int y = 1; y <= Maze.getHEIGHT()-1; y++) {
			for (int x = 1; x < Maze.getWIDTH()-1; x++) {
				Tile tile = (Tile) mazeMap.getMazeObject(mazeMap.getCoordinate(y, x));
				if (tile.isTraversable()) 
					tempTileSet.add(tile);	
			}
		}		
		return new TileGrid(tempTileSet);
	}
	
	private static Queue<Tile> parsePath(Path path) {
		Queue<Tile> route = new LinkedList<Tile>();
		for (Waypoint wp : path) {
			for (int y = 1; y < Maze.getHEIGHT()-1; y+=2) {
				for (int x = 1; x < Maze.getWIDTH()-1; y+=2) {
					Coordinate tilePos = MazeSolvingRobot.getMaze().getCoordinate(y, x);
					Tile tile = (Tile) MazeSolvingRobot.getMaze().getMazeObject(tilePos);
					if (tile.isWaypointWithin(wp)) {
						route.add(tile);						
					}
				}
			}
		}
		return route;
	}
	
	
}
