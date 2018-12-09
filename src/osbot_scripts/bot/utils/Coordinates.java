package osbot_scripts.bot.utils;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

public class Coordinates {

	private static final Area TUT_ISLAND_AREA = new Area(
			new int[][] { { 3049, 3128 }, { 3032, 3090 }, { 3054, 3037 }, { 3146, 3048 }, { 3181, 3101 },
					{ 3163, 3129 }, { 3137, 3137 }, { 3131, 3145 }, { 3103, 3140 }, { 3087, 3156 }, { 3054, 3138 } });

	private static final Area TUT_ISLAND_AREA_CAVE = new Area(new int[][] { { 3067, 9521 }, { 3096, 9540 },
			{ 3128, 9540 }, { 3125, 9498 }, { 3085, 9482 }, { 3062, 9500 } });

	private static final Area TUT_ISLAND_UPPER = new Area(
			new int[][] { { 3047, 3140 }, { 3045, 3052 }, { 3179, 3070 }, { 3170, 3133 } });

	private static final Area TUT_ISLAND_UPPER_2 = new Area(
			new int[][] { { 3062, 3135 }, { 3064, 3073 }, { 3151, 3073 }, { 3143, 3138 } }).setPlane(1);

	public static boolean isOnTutorialIsland(MethodProvider api) {
		if (!api.getClient().isLoggedIn()) {
			return false;
		}
		if (TUT_ISLAND_AREA.contains(api.myPlayer()) || TUT_ISLAND_AREA_CAVE.contains(api.myPlayer())
				|| TUT_ISLAND_UPPER.contains(api.myPlayer()) || TUT_ISLAND_UPPER_2.contains(api.myPlayer())) {
			return true;
		}
		return false;
	}
}
