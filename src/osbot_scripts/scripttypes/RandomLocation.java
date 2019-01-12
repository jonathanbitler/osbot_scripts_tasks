package osbot_scripts.scripttypes;

import java.util.ArrayList;
import java.util.Random;

import org.osbot.rs07.api.map.Position;

import osbot_scripts.bot.utils.RandomUtil;

public class RandomLocation {

	public RandomLocation(ArrayList<Position>[] locations) {
		this.locations = locations;
	}

	public ArrayList<Position>[] locations;

	public int length() {
		int length = 0;
		for (ArrayList<Position> list : locations) {
			if (list != null) {
				length++;
			}
		}
		return length;
	}

	public ArrayList<Position> getRandomPosition() {
		int randomIndex = RandomUtil.getRandomNumberInRange(0, (length() - 1));
		return locations[randomIndex];
	}
}
