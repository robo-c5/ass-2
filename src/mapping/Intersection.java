package mapping;

public class Intersection extends Edge{
	
	public Intersection (Coordinate topoPos, Coordinate metricPos) {
		super(topoPos, metricPos);
		height = 10;
		width = height;
		
		stringRep = "I";
	}

}
