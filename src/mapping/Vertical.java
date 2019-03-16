package mapping;

import java.io.*;

public class Vertical extends Edge implements Serializable {
	
	private static final long serialVersionUID = 020102L;

    public Vertical (Coordinate topoPos) {
        super(topoPos);
        height = 30;
        width = 10;
        
        stringRep = "V";
    }
}
