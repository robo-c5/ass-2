package mapping;

public class Vertical extends Edge{
	
	public Vertical (Coordinate topoPos, Coordinate metricPos) {
		super(topoPos, metricPos);
		height = 30;
		width = 10;
		
		stringRep = "V";
	}
}
