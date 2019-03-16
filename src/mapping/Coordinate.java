package mapping;

import java.io.*;

import lejos.robotics.navigation.Waypoint;

public class Coordinate implements Serializable {
	
	private static final long serialVersionUID = 04L; 
	
    private int y;
    private int x;

    //test field
    private String stringRep;

    public Coordinate(int y, int x){
        this.y= y;
        this.x = x;

        stringRep = "(" + y + ", " + x + ")";
    }
    
    public Coordinate(Waypoint wp) {
    	this.y = (int) wp.y;
    	this.x = (int) wp.x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    
    public boolean equals(Coordinate peer) {
    	if (y != peer.getY())
    		return false;
    	if (x != peer.getX())
    		return false;
    	return true;
    }

    //test method
    @Override
    public String toString() {
        return stringRep;
    }
}
