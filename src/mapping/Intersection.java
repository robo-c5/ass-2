package mapping;

public class Intersection extends Edge{

    public Intersection (Coordinate topoPos, Coordinate metricCentre) {
        super(topoPos, metricCentre);
        height = 10;
        width = height;
        centre = metricCentre;
        
        stringRep = "I";
    }

}
