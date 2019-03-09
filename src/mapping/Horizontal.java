package mapping;

public class Horizontal extends Edge{

    public Horizontal (Coordinate topoPos, Coordinate metricPos) {
        super(topoPos, metricPos);
        height = 10;
        width = 30;
        initialiseCentre();

        stringRep = "H";
    }

}
