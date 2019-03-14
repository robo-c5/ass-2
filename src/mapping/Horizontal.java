package mapping;

public class Horizontal extends Edge{
	
	private static final int HEIGHT = 10;
	private static final int WIDTH = 30;

    public Horizontal (Coordinate topoPos) {
        super(topoPos);
        height = HEIGHT;
        width = WIDTH;

        stringRep = "H";
    }
    
    public static int get() {
    	return HEIGHT;
    }

}
