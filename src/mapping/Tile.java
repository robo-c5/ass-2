package mapping;
public class Tile extends MazeObject {
	
	enum Colour { //eventually move this somewhere else?
		White, Green, Red, Unknown
    }

	Colour colour;
	
	public Tile (Coordinate topoPos, Coordinate metricPos) {
		super(topoPos, metricPos);
		colour = Colour.Unknown;
		traversable = true;
		height = 30;
		width = height;
		
		//test field
		stringRep = "T";
	}

	public void setColour(Colour newColour) {
	    colour = newColour;
    }
}