package pathfinding;

import java.util.ArrayList;

import mapping.*;
import setup.MazeSolvingRobot;

public class AStarSearch
{
	private static Tile start;
	private static Tile goal;
	private static Tile currentState;
	private static Tile[] successors;
	
	//prolly want a set of observed states, and one of about to be observed states
	//if about to be observed is empty we dun goofed
	public static ArrayList<Tile> ShortestPathToOrigin() {
		start = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition());
		goal = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getOrigin());
		currentState = start;
		ArrayList<Tile> shortestPath = new ArrayList<Tile>();
		
		while(!isGoal(currentState)) {
			successors = getSuccessors(currentState);

		}
		return shortestPath;
	}
	
	//decides which state to observe next
	private static Tile getLowestCost(ArrayList<Tile> set) {
		Tile currentBest = set.get(0);
		if (set.size() > 1) {
			for (int i = 1; i < set.size(); i++) {
				Tile state = set.get(i);
				if (fCost(state) < fCost(currentBest)) {
					currentBest = state;
				}
			}
		}
		return currentBest;		
	}
	
	private static boolean isGoal(Tile state) {
		return state.equals(goal);
	}
	
	private static Tile[] getSuccessors(Tile state) {
		return MazeSolvingRobot.getMaze().getNearbyReachableTiles(state);
	}
	
	private static double hCost(Tile state) {
		int x1 = state.getCentre().getX();
		int y1 = state.getCentre().getX();
		int x2 = goal.getCentre().getX();
		int y2 = goal.getCentre().getX();
		
		return Math.sqrt((x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2));
	}
	
	private static double gCost(Tile state) {
		int x1 = state.getCentre().getX();
		int y1 = state.getCentre().getX();
		int x2 = start.getCentre().getX();
		int y2 = start.getCentre().getX();
		
		return Math.sqrt((x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2));
	}
	
	private static double fCost(Tile state) {
		return hCost(state) + gCost(state);
	}
}
