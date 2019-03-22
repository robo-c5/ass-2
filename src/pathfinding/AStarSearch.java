package pathfinding;

import java.util.ArrayList;
import java.util.Stack;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import mapping.*;
import setup.MazeSolvingRobot;
import pathfinding.State;

// Currently doesn't check if dest is reachable, would require checking if the frontier is ever empty
public class AStarSearch {
	private static State origin;
	private static State currentState;
	private static ArrayList<State> frontier = new ArrayList<State>();
	private static ArrayList<State> visited = new ArrayList<State>();
	private static Tile startTile;
	private static Tile goalTile;

	public static ArrayList<Tile> ShortestPath(Tile start, Tile finish) {
		startTile = start;
		goalTile = finish;

		origin = new State(start, startTile, goalTile);
		origin.setGCost(0);
		
		currentState = origin;
		ArrayList<Tile> shortestPath = new ArrayList<Tile>();
		addToFrontier(origin);

		while (!currentState.isGoal()) {
			currentState = getLowestCost();
			visit(currentState);
		}
		
		while (currentState.getParent() != null) {
			shortestPath.add(currentState.getTile());
			currentState = currentState.getParent();
		}
		
		shortestPath.add(startTile);

		return shortestPath;
	}

	// since the maze mapping may not be complete when we hit the red tile, only
	// consider tiles we have already visited & are traversable (not green)
	// don't add to frontier if corresponding state is already in frontier or if in
	// visited with lower gCost
	// if find an equivilant state in visited set with higher gCost then proposed,
	// remove old from visited & add new to frontier
	private static void addToFrontier(State state) {
		if (state.getTile().isVisited() && state.getTile().isTraversable()) {
			if (frontier.contains(state)) {
				return;
			}
			State equivState = equivilantVisitedState(state);
			if (equivState != null) {
				if (equivState.getGCost() <= state.getGCost()) {
					return;
				}
				visited.remove(equivState);
			}
			frontier.add(state);
		}
	}

	private static State equivilantVisitedState(State state) {
		for (State visitedState : visited) {
			if (visitedState.getTile().equals(state.getTile())) {
				return visitedState;
			}
		}
		return null;
	}

	// decides which state to observe next
	private static State getLowestCost() {
		if(frontier.size() == 0) {
			Sound.beepSequence();
		}
		State currentBest = null;
		if (frontier.size() > 0)
			currentBest = frontier.get(0);
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
			if (tile != null) {
				Delay.msDelay(5000);
				if (state.getParent() == null) {
					State child = new State(tile, startTile, goalTile);
					child.setGCost(state.getGCost() + 1);
					child.setParent(state);
					children.add(child);
				} else if (!tile.equals(state.getParent().getTile())) {
					State child = new State(tile, startTile, goalTile);
					child.setGCost(state.getGCost() + 1);
					child.setParent(state);
					children.add(child);
				}
			}
		}
		state.setChildren(children);
	}

	public static Tile getStart() {
		return startTile;
	}

	public static Tile getGoal() {
		return goalTile;
	}

}
