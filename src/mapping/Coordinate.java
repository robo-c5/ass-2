package mapping;

public class Coordinate {
    private int y;
    private int x;

    //test field
    private String stringRep;

    public Coordinate(int y, int x){
        this.y= y;
        this.x = x;

        stringRep = "(" + y + ", " + x + ")";
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    
    public boolean equals(Coordinate peer) {
    	if (y != peer.getY() || x != peer.getX())
    		return false;
    	return true;
    }

    //test method
    @Override
    public String toString() {
        return stringRep;
    }
}
