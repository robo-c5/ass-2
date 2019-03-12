package behaviours;
import lejos.robotics.pathfinding.*;
import lejos.robotics.mapping.*;
import lejos.robotics.subsumption.Behavior;
import mapping.*;
import setup.MazeSolvingRobot;

public class RedTile implements Behavior {
	
	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return isRed(MazeSolvingRobot.getColourSample());
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	@Override
	public void action() {
		//convert current Maze object to a NavMesh
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
	
	private static FourWayGridMesh convertMaze() {
		Maze mazeMap = MazeSolvingRobot.getMaze();
		LineMap lineMap = new LineMap();
		FourWayGridMesh fWGM = new FourWayGridMesh(lineMap, 0.0f, 0.0f);
		for (int y = 0; y < Maze.getHEIGHT(); y++) {
			for (MazeObject tile : mazeMap.getRow(y)) {
				if (tile instanceof Tile) {
					Node newNode = ((Tile) tile).getNode();
					fWGM.addNode(newNode, 0);
					for (MazeObject neighbour : mazeMap.getAdjacentTiles((Tile) tile)) {
						fWGM.connect(newNode, (neighbour.getNode());
					}		
				}
				
			}
		}
		return fWGM;
	}

}
