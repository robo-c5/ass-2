package pathfinding;

import java.util.ArrayList;
import java.util.Stack;

import mapping.*;
import setup.MazeSolvingRobot;

public class AStarSearch
{
	private static State start;
	private static State goal;
	private static State currentState;
	private static ArrayList<State> frontier = new ArrayList<State>();
	private static ArrayList<State> visited = new ArrayList<State>();
	
	public static Stack<Tile> ShortestPathToOrigin() {
		start = new State((Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getTopoPosition()));
		goal = new State((Tile) MazeSolvingRobot.getMaze().getMazeObject(MazeSolvingRobot.getOrigin()));
		
		currentState = start;		
		Stack<Tile> shortestPath = new Stack<Tile>();
		addToFrontier(start);
		
		while(!currentState.isGoal()) {
			currentState = getLowestCost();
			visit(currentState);
		}
		
		while(!currentState.isStart()) {
			shortestPath.add(currentState.getTile());
			currentState = currentState.getParent();
		}
		
		return shortestPath;
	}
	
	//don't add to frontier if corresponding state is already in frontier, or if in list of already visited states, with a smaller or equal gCost
	private static void addToFrontier(State state) {
		if (frontier.contains(state) ) {
			return;
		}
		State equivState = equivilantVisitedState(state);
		if (equivState != null) {
			if (equivState.getGCost() <= state.getGCost()) {
				return;
			}
			frontier.remove(equivState);
		}
		frontier.add(state);
	}
	
	private static State equivilantVisitedState(State state) {
		for (State visitedState : visited) {
			if (visitedState.getTile().equals(state.getTile())) {
				return visitedState;
			}
		}
		return null;
	}
	
	//decides which state to observe next
	private static State getLowestCost() {
		State currentBest = frontier.get(0);
		if (frontier.size() > 1) {
			for (int i = 1; i < frontier.size(); i++) {
				State tempState = frontier.get(i);
				if (tempState.getFCost() < currentBest.getFCost()) {
					currentBest = tempState;
				}
			}
		}
		return currentBest;		
	}
	
	private static void visit(State state) {
		findChildren(state);
		for (State child : state.getChildren()) {			
			addToFrontier(child);
		}
		visited.add(state);
		frontier.remove(state);
	}

	private static void findChildren(State state) {
		Tile[] nearbyReachableTiles = MazeSolvingRobot.getMaze().getNearbyReachableTiles(state.getTile());
		ArrayList<State> children = new ArrayList<State>();
		for (Tile tile : nearbyReachableTiles) {
			State child = new State(tile);
			child.setGCost(state.getGCost() + 1);
			child.setParent(state);
			children.add(child);
		}
		state.setChildren(children);
	}
	
	//may want to create state object
	static class State {
		Tile tile;
		double hCost;
		double gCost;
		ArrayList<State> children;
		State parent;
		
		private State(Tile tile) {
			this.tile = tile;
			hCost = calcHCost();
		}
		
		//heuristic is euclidean distance from state Tile centre to goal Tile centre
		private double calcHCost() {
			int x1 = tile.getCentre().getX();
			int y1 = tile.getCentre().getY();
			int x2 = goal.getTile().getCentre().getX();
			int y2 = goal.getTile().getCentre().getY();
			
			return Math.sqrt((x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2));
		}
		
		private double getGCost() {
			return gCost;
		}
		
		private void setGCost(double cost) {
			gCost = cost;
		}
		
		private double getFCost() {
			return hCost + gCost;
		}		
		
		private boolean isGoal() {
			return tile.equals(goal.getTile());	
		}
		
		private boolean isStart() {
			return tile.equals(start.getTile());
		}
		
		private State getParent() {
			return parent;
		}
		
		private void setParent(State parent) {
			this.parent = parent;
		}
		
		private ArrayList<State> getChildren() {
			return children;
		}
		
		private void setChildren(ArrayList<State> successors) {
			children = successors;
		}
		
		private Tile getTile() {
			return tile;
		}	
	}
}
