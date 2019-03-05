package mapping;
public class Tile extends MazeObject {
	
	enum Colour { // move this somewhere else
		White, Green, Red, Unknown;
	}

	Colour colour;
	
	public Tile(Coordinate pos) {
		super(pos);
		colour = Colour.Unknown;
		stringRep = "T";
	}
	
}