package mapping;

public class Horizontal extends Edge{

    public Horizontal (Coordinate topoPos, Coordinate metricCentre) {
        super(topoPos, metricCentre);
        height = 10;
        width = 30;
        centre = metricCentre;

        stringRep = "H";
    }

}
