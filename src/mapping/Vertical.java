package mapping;

public class Vertical extends Edge{

    public Vertical (Coordinate topoPos, Coordinate metricCentre) {
        super(topoPos, metricCentre);
        height = 30;
        width = 10;
        centre = metricCentre;
        
        stringRep = "V";
    }
}
