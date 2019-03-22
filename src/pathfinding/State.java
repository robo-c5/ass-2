package pathfinding;

import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import mapping.Tile;
import pathfinding.AStarSearch;

// may want to create state object
public class State {
	Tile tile;
	double hCost;
	double gCost;
	ArrayList<State> children;
	State parent;
	static Tile goalTile;
	static Tile startTile;

	private State() {
	}

	public State(Tile tile, Tile start, Tile goal) {
		this.tile = tile;
		if (startTile  == null || goalTile == null) {
			startTile = start;
			goalTile = goal;
		}
		hCost = calcHCost();
	}

	// heuristic is euclidean distance from state Tile centre to goal Tile centre
	private double calcHCost() {
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;

		x1 = tile.getCentre().getX();
		y1 = tile.getCentre().getY();
		x2 = goalTile.getCentre().getX();
		y2 = goalTile.getCentre().getY();

		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public double getGCost() {
		return gCost;
	}

	public void setGCost(double cost) {
		gCost = cost;
	}

	public double getFCost() {
		return hCost + gCost;
	}

	public boolean isGoal() {
		return tile.equals(goalTile);
	}

	public boolean isStart() {
		return tile.equals(startTile);
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public ArrayList<State> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<State> successors) {
		children = successors;
	}

	public Tile getTile() {
		return tile;
	}
}
