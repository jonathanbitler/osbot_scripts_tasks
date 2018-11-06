package osbot_scripts.mouse;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.script.MethodProvider;

public class MouseTrailApi {

	private MethodProvider api;
	
	public MouseTrailApi(MethodProvider api) {
		this.api = api;
	}
	
	/**
	 * @return the trail
	 */
	public MouseTrail getTrail() {
		return trail;
	}
	/**
	 * @param trail the trail to set
	 */
	public void setTrail(MouseTrail trail) {
		this.trail = trail;
	}

	/**
	 * @return the cursor
	 */
	public MouseCursor getCursor() {
		return cursor;
	}

	/**
	 * @param cursor the cursor to set
	 */
	public void setCursor(MouseCursor cursor) {
		this.cursor = cursor;
	}

	private MouseTrail trail = new MouseTrail(0, 255, 255, 2000, api);
	private MouseCursor cursor = new MouseCursor(52, 4, Color.white, api);
	
	/**
	 * Paints the graphics
	 */
	public void draw(Graphics2D g) {
		trail.paint(g);
		cursor.paint(g);
	}
	
}
