package mapping;

public class Vertical extends Edge{

    public Vertical (Coordinate topoPos) {
        super(topoPos);
        height = 30;
        width = 10;
        
        stringRep = "V";
    }
}
