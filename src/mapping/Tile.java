package mapping;
public class Tile extends MazeObject {
	
	enum Colour { //eventually move this somewhere else?
		White, Green, Red, Unknown
    }

	Colour colour;
	
	public Tile(Coordinate pos) {
		super(pos);
		colour = Colour.Unknown;
		traversable = true;

		//test field
		stringRep = "T";
	}

	public void setColour(Colour newColour) {
	    colour = newColour;
    }
}