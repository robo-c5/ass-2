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
	private static ArrayList<Tile> frontier = new ArrayList<Tile>();
	private static ArrayList<Tile> visited = new ArrayList<Tile>();
	
	//prolly want a set of observed states, and one of about to be observed states
	//if about to be observed is empty we dun goofed
	public static ArrayList<Tile> ShortestPathToOrigin() {
		start = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition());
		goal = (Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getOrigin());
		currentState = start;
		ArrayList<Tile> shortestPath = new ArrayList<Tile>();
		addToFrontier(start);
		while(!isGoal(currentState)) {
			successors = getSuccessors(currentState);
			for (Tile state : successors) {
				addToFrontier(state);
			}
			currentState = getLowestCost(frontier);
		}
		return shortestPath;
	}
	
	//don't add to frontier if corresponding state is already in frontier, or if in list of already visited states, with a smaller or equal gCost
	private static void addToFrontier(Tile state) {
		if (!frontier.contains(state) ) {
			frontier.add(state);
		}
	}
	
	private static boolean isGoal(Tile state) {
		return state.equals(goal);
	}
	
	private static Tile[] getSuccessors(Tile state) {
		return MazeSolvingRobot.getMaze().getNearbyReachableTiles(state);
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
	
	//heuristic is euclidean distance from state Tile centre to goal Tile centre
	private static double hCost(Tile state) {
		int x1 = state.getCentre().getX();
		int y1 = state.getCentre().getX();
		int x2 = goal.getCentre().getX();
		int y2 = goal.getCentre().getX();
		
		return Math.sqrt((x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2));
	}
	
	//maybe do depth of tree for gCost
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
	
	private Tile getGoal() {
		return goal;
	}
	
	//may want to create state object
	private class State {
		Tile tile;
		double hCost;
		double gCost;
		Tile[] successors;
		
		private State(Tile tile) {
			
			this.tile = tile;
			hCost = calcHCost();
		}
		
		private double calcHCost() {
			int x1 = tile.getCentre().getX();
			int y1 = tile.getCentre().getX();
			int x2 = goal.getCentre().getX();
			int y2 = goal.getCentre().getX();
			
			return Math.sqrt((x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2));
		}
		
		private double getHCost() {
			return hCost;
		}
		
		private void setGCost(double cost) {
			gCost = cost;
		}
		
		private double getGCost() {
			return gCost;
		}
		
		private double getfCost() {
			return hCost + gCost;
		}		
		
		private boolean isGoal() {
			return tile.equals(goal);	
		}
		
		
		
	}
}
