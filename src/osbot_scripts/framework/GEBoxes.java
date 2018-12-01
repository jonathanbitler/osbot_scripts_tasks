package osbot_scripts.framework;

import org.osbot.rs07.api.GrandExchange;

public enum GEBoxes {
	
	BOX_1(GrandExchange.Box.BOX_1, "F2P", 7),
	BOX_2(GrandExchange.Box.BOX_2, "F2P", 8),
	BOX_3(GrandExchange.Box.BOX_3, "F2P", 9),
	BOX_4(GrandExchange.Box.BOX_4, "P2P", 10),
	BOX_5(GrandExchange.Box.BOX_5, "P2P", 11),
	BOX_6(GrandExchange.Box.BOX_6, "P2P", 12),
	BOX_7(GrandExchange.Box.BOX_7, "P2P", 13),
	BOX_8(GrandExchange.Box.BOX_8, "P2P", 14);
	
	GrandExchange.Box box;
	String type;
	int childWidgetId;
	
	private GEBoxes(GrandExchange.Box box, String type, int childWidgetId) {
		this.box = box;
		this.type = type;
		this.childWidgetId = childWidgetId;
	}
	
	public GrandExchange.Box getBox() {
		return this.box;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getChildWidgetId() {
		return this.childWidgetId;
	}

}
