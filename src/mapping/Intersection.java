package mapping;

public class Intersection extends Edge{

    public Intersection (Coordinate topoPos) {
        super(topoPos);
        height = 10;
        width = height;
        
        stringRep = "I";
    }

}
