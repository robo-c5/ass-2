package mapping;

import java.io.*;

public class Intersection extends Edge implements Serializable {
	
	private static final long serialVersionUID = 020103L;

    public Intersection (Coordinate topoPos) {
        super(topoPos);
        height = 10;
        width = height;
        
        stringRep = "I";
    }

}
