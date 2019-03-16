package mapping;

import java.io.*;

public class Horizontal extends Edge implements Serializable {
	
	private static final long serialVersionUID = 020101L;
	
	private static final int HEIGHT = 10;
	private static final int WIDTH = 30;

    public Horizontal (Coordinate topoPos) {
        super(topoPos);
        height = HEIGHT;
        width = WIDTH;

        stringRep = "H";
    }
    
    public static int get() {
    	return HEIGHT;
    }

}
