package osbot_scripts.framework;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

public class ConcreteWalking {

	private static final ArrayList<Position> GE_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3446, 0), new Position(3183, 3451, 0),
					new Position(3174, 3456, 0), new Position(3165, 3461, 0), new Position(3165, 3461, 0),
					new Position(3165, 3471, 0), new Position(3165, 3481, 0), new Position(3164, 3486, 0)));

	public static void walkToGe(MethodProvider api) {
		// If the player is not in the grand exchange area, then walk to it
		if (api.myPlayer() != null
				&& !new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
						.contains(api.myPlayer())) {

			api.log("Preparing to walk to the grand exchange!");

			// Contains in banking area varrock
			if (new Area(new int[][] { { 3180, 3438 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3438 } })
					.contains(api.myPlayer())) {
				api.getWalking().walkPath(GE_PATH);
			} else {
				api.getWalking().webWalk(
						new Area(new int[][] { { 3160, 3494 }, { 3168, 3494 }, { 3168, 3485 }, { 3160, 3485 } }));
				api.log("The player has a grand exchange task but isn't there, walking to there");
			}
		}
	}

}
