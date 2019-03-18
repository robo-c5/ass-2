package server;

import mapping.*;

public class EV3StatsMessage {
	
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
