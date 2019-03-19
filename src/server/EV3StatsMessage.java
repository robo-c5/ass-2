package server;

import mapping.*;

public class EV3StatsMessage {
	
	public static final String MAZE = "Maze";
	public static final String POSITION = "Coordinate";
	public static final String HEADING = "Bearing";
	
	private Object info;
	private String type;
	
	
	public EV3StatsMessage(Object info, String type) {
		this.info = info;
		this.type = type;;		
	}
	
	public Object getInfo() {
		return info;
	}
	
	public String getType() {
		return type;
	}
}
