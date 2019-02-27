package mapping;
public class Tile extends MazeObject {
	
	enum Colour { // move this somewhere else
		White, Green, Red, Unknown;
	}
	Colour colour = Colour.Unknown;
	
	public Tile(int x, int y) {
		super(x, y);
		stringRep = "T";
	}
	
}