package osbot_scripts.hopping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bot.utils.RandomUtil;

public class WorldHop {

	/**
	 * Check if the current players in our area meets the limit
	 *
	 * @param area
	 *            the area that the players will be calculated
	 * @param playerLim
	 *            the amount of players needed to perform a hop
	 * @return true if the player count meets the limit
	 */
	public static boolean shouldWeHop(MethodProvider api, Area area, int playerLim) {
		int playersInArea = 0;
		List<Player> nearbyPlayers = api.players.getAll();

		// Remove our player from the list
		nearbyPlayers.remove(api.myPlayer());
		for (Player player : nearbyPlayers) {
			if (area.contains(player) && !player.getName().equalsIgnoreCase(api.myPlayer().getName())) {
				playersInArea++;
			}
		}
		return playersInArea >= playerLim;
	}

	public static boolean hopClick(MethodProvider api) {
		api.getWorlds().hopToF2PWorld();

		try {
			Thread.sleep(20_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		api.log("Sleeping 20 seconds to ensure everything initialized");
		return true;
	}

	public static boolean hop(MethodProvider api, int personAmount, int radius) {
		int playerDetectionRadius = 0; // A radius of 5 steps is 5 steps in every direction from our player
		//
		// List<Integer> worldsAvailable = new ArrayList<Integer>(
		// Arrays.asList(474, 477, 470, 479, 472, 476, 473, 478, 475, 471, 469, 394,
		// 453, 456, 452, 458, 460, 455,
		// 459, 451, 454, 457, 398, 397, 399, 383, 498, 497, 499, 504, 502, 503, 501,
		// 500, 505, 506));

		if (shouldWeHop(api, api.myPlayer().getArea(radius), personAmount)) {
			// int hopToWorld = worldsAvailable.get(RandomUtil.getRandomNumberInRange(0,
			// worldsAvailable.size() - 1));
			api.getWorlds().hopToF2PWorld();

			try {
				Thread.sleep(20_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			api.log("Sleeping 20 seconds to ensure everything initialized");
			return true;
		}
		return false;
	}

}
