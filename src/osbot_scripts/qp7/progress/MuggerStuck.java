package osbot_scripts.qp7.progress;

import java.util.Arrays;
import java.util.LinkedList;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class MuggerStuck {

	private static final LinkedList<Position> TEST_CLAY_MINE = new LinkedList<Position>(
			Arrays.asList(
					new Position(3185, 3370, 0),
					new Position(3182, 3363, 0),
					new Position(3180, 3367, 0),
					new Position(3180, 3371, 0)
//					new Position(3181, 3371, 0), new Position(3190, 3366, 0), new Position(3191, 3365, 0),
//					new Position(3190, 3363, 0), new Position(3186, 3363, 0), new Position(3186, 3361, 0)
					)
			);
	
	private static final LinkedList<Position> TEST_IRON_MINE = new LinkedList<Position>(
			Arrays.asList(
					new Position(3185, 3370, 0),
					new Position(3182, 3363, 0),
					new Position(3175, 3367, 0)
//					new Position(3181, 3371, 0), new Position(3190, 3366, 0), new Position(3191, 3365, 0),
//					new Position(3190, 3363, 0), new Position(3186, 3363, 0), new Position(3186, 3361, 0)
					)
			);

	private static boolean walkExact(Script script, MethodProvider api, Position position, boolean disableRun) {
		WalkingEvent event = new WalkingEvent(position);
		event.setEnergyThreshold(101);
		event.setMinDistanceThreshold(2);
		if (disableRun && api.getSettings().isRunning()) {
			api.getSettings().setRunning(false);
		} else if (!disableRun && !api.getSettings().isRunning()) {
			api.getSettings().setRunning(true);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return script.execute(event).hasFinished();
	}

	public static void runCopperMine(Script script, MethodProvider api) {
		walkExact(script, api,TEST_CLAY_MINE.get(0), true);
		walkExact(script, api,TEST_CLAY_MINE.get(1), true);
		walkExact(script, api,TEST_CLAY_MINE.get(2), false);
		walkExact(script, api,TEST_CLAY_MINE.get(3), false);
	}
	

	public static void runIronMine(Script script, MethodProvider api) {
		walkExact(script, api,TEST_IRON_MINE.get(0), true);
		walkExact(script, api,TEST_IRON_MINE.get(1), true);
		walkExact(script, api,TEST_IRON_MINE.get(2), false);
	}

}
