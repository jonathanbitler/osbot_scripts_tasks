package osbot_scripts.mouse;

import java.awt.Point;

public class MousePathPoint extends Point{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720638945661894188L;
	private long finishTime;

	public MousePathPoint(int x, int y, int lastingTime){
		super(x,y);
		finishTime= System.currentTimeMillis() + lastingTime;
	}
	
	public boolean isUp(){
		return System.currentTimeMillis() > finishTime;
	}
}