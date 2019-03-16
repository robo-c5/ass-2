package mapping;

import java.util.ArrayList;
import java.util.Collection;

import lejos.robotics.pathfinding.NavigationMesh;
import lejos.robotics.pathfinding.Node;
import setup.MazeSolvingRobot;

public class TileGrid implements NavigationMesh {
	private ArrayList<Node> nodeSet = new ArrayList<Node>();
	
	public TileGrid(ArrayList<Tile> tileSet) {
		for (Tile tile: tileSet) {
				addNode(tile.getNode(), 0);
		}		
		for (Tile tile: tileSet) {
			for (Tile neighbour : MazeSolvingRobot.getMaze().getAdjacentTiles(tile)) {
				connect(tile.getNode(), neighbour.getNode());
			}
		}			
	}
	
	@Override
	public int addNode(Node node, int neighbors)
	{
		nodeSet.add(node);
		return 0;
	}

	@Override
	public boolean removeNode(Node node)
	{
		Collection <Node> coll = node.getNeighbors();
		ArrayList <Node> arr = new ArrayList <Node> (coll);		
		for(int i = 0; i < arr.size(); i ++) {
			Node neighbor = arr.get(i);
			neighbor.removeNeighbor(node);
			node.removeNeighbor(neighbor);
		}
			
		return nodeSet.remove(node);

	}

	@Override
	public boolean connect(Node node1, Node node2)
	{
		node1.addNeighbor(node2);
		node2.addNeighbor(node1);
		return true;
	}

	@Override
	public boolean disconnect(Node node1, Node node2)
	{
		node1.removeNeighbor(node2);
		node2.removeNeighbor(node1);
		return true;

	}

	@Override
	public Collection <Node> getMesh() {
		return nodeSet;
	}

	@Override
	public void regenerate() {	
	}
}
