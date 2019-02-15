package osbot_scripts.objectHandling;

import org.osbot.rs07.api.map.Area;

public class LadderAndDoor {

	public LadderAndDoor(String name, String interaction, boolean reverse, Area beginArea, Area finalArea) {
		this.reverse = reverse;
		this.beginArea = beginArea;
		this.finalArea = finalArea;
		this.name = name;
		this.interaction = interaction;
	}

	private String name, interaction;

	private boolean reverse;

	private Area beginArea;

	private Area finalArea;

	public String getName() {
		return name;
	}

	public String getInteraction() {
		return interaction;
	}

	public boolean isReverse() {
		return reverse;
	}

	public Area getBeginArea() {
		return beginArea;
	}

	public Area getFinalArea() {
		return finalArea;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public void setBeginArea(Area beginArea) {
		this.beginArea = beginArea;
	}

	public void setFinalArea(Area finalArea) {
		this.finalArea = finalArea;
	}

}
