package mapping;

public class Coordinate {
    private int[] coords = new int[2];
    private String stringRep;

    public Coordinate(int y, int x){
        coords[0] = y;
        coords[1] = x;
        stringRep = "(" + y + ", " + x + ")";
    }

    public int getY() {
        return coords[0];
    }

    public int getX() {
        return coords[1];
    }

    @Override
    public String toString() {
        return stringRep;
    }
}