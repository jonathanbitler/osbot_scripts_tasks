package osbot_scripts.qp7.progress;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.objectHandling.LadderAndDoor;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class ObstacleTest3 extends QuestStep {

	public ObstacleTest3(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MINING_RIMMINGTON_CLAY, event, script, false);
		// TODO Auto-generated constructor stub
	}

	private Ge2 grandExchangeActions;

	private static final Area BEGIN_AREA_WALKING = new Area(
			new int[][] { { 3209, 3432 }, { 3216, 3432 }, { 3216, 3425 }, { 3209, 3425 }, { 3209, 3432 } });

	private static final Area END_AREA_WALKING = new Area(
			new int[][] { { 3159, 3440 }, { 3151, 3440 }, { 3151, 3431 }, { 3159, 3431 }, { 3159, 3440 } }).setPlane(1);

	private static final Area BEGIN_AREA_STAIRS = new Area(
			new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3161, 3432 }, { 3161, 3436 }, { 3156, 3436 } });

	private static final ArrayList<Position> PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3213, 3428, 0), new Position(3213, 3428, 0), new Position(3212, 3428, 0),
					new Position(3211, 3428, 0), new Position(3210, 3428, 0), new Position(3209, 3428, 0),
					new Position(3208, 3428, 0), new Position(3207, 3428, 0), new Position(3206, 3428, 0),
					new Position(3205, 3428, 0), new Position(3204, 3428, 0), new Position(3203, 3428, 0),
					new Position(3202, 3428, 0), new Position(3201, 3428, 0), new Position(3200, 3428, 0),
					new Position(3199, 3428, 0), new Position(3198, 3428, 0), new Position(3197, 3428, 0),
					new Position(3196, 3428, 0), new Position(3195, 3428, 0), new Position(3194, 3428, 0),
					new Position(3193, 3428, 0), new Position(3192, 3428, 0), new Position(3191, 3428, 0),
					new Position(3190, 3428, 0), new Position(3189, 3428, 0), new Position(3188, 3428, 0),
					new Position(3187, 3428, 0), new Position(3186, 3428, 0), new Position(3185, 3428, 0),
					new Position(3184, 3428, 0), new Position(3183, 3428, 0), new Position(3182, 3428, 0),
					new Position(3181, 3428, 0), new Position(3180, 3428, 0), new Position(3179, 3428, 0),
					new Position(3178, 3428, 0), new Position(3177, 3428, 0), new Position(3176, 3428, 0),
					new Position(3175, 3428, 0), new Position(3174, 3428, 0), new Position(3173, 3429, 0),
					new Position(3172, 3430, 0), new Position(3171, 3431, 0), new Position(3170, 3432, 0),
					new Position(3169, 3432, 0), new Position(3168, 3432, 0), new Position(3167, 3433, 0),
					new Position(3166, 3433, 0), new Position(3165, 3433, 0), new Position(3164, 3433, 0),
					new Position(3163, 3433, 0), new Position(3162, 3433, 0), new Position(3161, 3433, 0),
					new Position(3160, 3433, 0), new Position(3159, 3433, 0), new Position(3158, 3434, 0)));

	private static final ArrayList<Position> PATH_TO_JULIET = new ArrayList<Position>(
			Arrays.asList(new Position(3154, 3435, 1), new Position(3154, 3435, 1), new Position(3154, 3434, 1),
					new Position(3155, 3433, 1), new Position(3156, 3432, 1), new Position(3157, 3431, 1),
					new Position(3157, 3430, 1), new Position(3157, 3429, 1), new Position(3157, 3428, 1),
					new Position(3158, 3427, 1), new Position(3158, 3426, 1), new Position(3158, 3425, 1)));

	private static final ArrayList<Position> PATH_TO_STAIRCASE = new ArrayList<Position>(
			Arrays.asList(new Position(3158, 3425, 1), new Position(3158, 3425, 1), new Position(3158, 3426, 1),
					new Position(3158, 3427, 1), new Position(3158, 3428, 1), new Position(3158, 3429, 1),
					new Position(3157, 3430, 1), new Position(3157, 3431, 1), new Position(3156, 3431, 1),
					new Position(3155, 3432, 1), new Position(3154, 3432, 1), new Position(3153, 3433, 1),
					new Position(3152, 3434, 1)));

	private static final ArrayList<Position> PATH_TO_PLANE = new ArrayList<Position>(
			Arrays.asList(new Position(3159, 3434, 0), new Position(3159, 3434, 0), new Position(3160, 3434, 0),
					new Position(3161, 3434, 0), new Position(3162, 3434, 0), new Position(3163, 3433, 0),
					new Position(3164, 3433, 0), new Position(3165, 3433, 0), new Position(3166, 3433, 0),
					new Position(3167, 3433, 0), new Position(3168, 3433, 0), new Position(3169, 3433, 0),
					new Position(3170, 3433, 0), new Position(3171, 3433, 0), new Position(3172, 3433, 0),
					new Position(3173, 3433, 0), new Position(3174, 3433, 0), new Position(3175, 3433, 0),
					new Position(3176, 3433, 0), new Position(3177, 3433, 0), new Position(3178, 3433, 0),
					new Position(3179, 3432, 0), new Position(3180, 3432, 0), new Position(3181, 3432, 0),
					new Position(3182, 3432, 0), new Position(3183, 3432, 0), new Position(3184, 3432, 0),
					new Position(3185, 3432, 0), new Position(3186, 3432, 0), new Position(3187, 3432, 0),
					new Position(3188, 3432, 0), new Position(3189, 3432, 0), new Position(3190, 3432, 0),
					new Position(3191, 3432, 0), new Position(3192, 3432, 0), new Position(3193, 3432, 0),
					new Position(3194, 3432, 0), new Position(3195, 3432, 0), new Position(3196, 3432, 0),
					new Position(3197, 3432, 0), new Position(3198, 3432, 0), new Position(3199, 3431, 0),
					new Position(3200, 3430, 0), new Position(3201, 3430, 0), new Position(3202, 3430, 0),
					new Position(3203, 3430, 0), new Position(3204, 3430, 0), new Position(3205, 3430, 0),
					new Position(3206, 3430, 0), new Position(3207, 3430, 0), new Position(3208, 3430, 0),
					new Position(3209, 3429, 0), new Position(3210, 3429, 0), new Position(3211, 3429, 0),
					new Position(3212, 3429, 0), new Position(3213, 3428, 0)));

	private int laps = 0;

	@Override
	public void onStart() {
		waitOnLoggedIn();

		// if (!BEGIN_AREA_WALKING.contains(myPlayer())) {
		// getWalking().webWalk(BEGIN_AREA_WALKING);
		// return;
		// }

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk upstairs to juliet", -1,
				-1, getBot().getMethods(), PATH, BEGIN_AREA_WALKING, END_AREA_WALKING, getScript(), getEvent(), false,
				true, this,
				new ArrayList<LadderAndDoor>(Arrays.asList(new LadderAndDoor("Staircase", "Climb-up", false, new Area(
						new int[][] { { 3156, 3436 }, { 3166, 3436 }, { 3166, 3432 }, { 3156, 3432 }, { 3156, 3436 } }),
						END_AREA_WALKING)))));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to juliet", -1, -1,
				getBot().getMethods(), PATH_TO_JULIET, END_AREA_WALKING,
				new Area(new int[][] { { 3155, 3426 }, { 3155, 3425 }, { 3162, 3425 }, { 3162, 3426 }, { 3155, 3426 } })
						.setPlane(1),
				getScript(), getEvent(), false, true, this,
				new ArrayList<LadderAndDoor>(Arrays.asList(new LadderAndDoor("Door", "Open", false,
						new Area(new int[][] { { 3154, 3433 }, { 3158, 3433 }, { 3158, 3428 }, { 3160, 3428 },
								{ 3160, 3425 }, { 3156, 3425 }, { 3155, 3425 }, { 3155, 3429 }, { 3155, 3433 } })
										.setPlane(1),
						new Area(new int[][] { { 3156, 3426 }, { 3161, 3426 }, { 3161, 3425 }, { 3156, 3425 },
								{ 3156, 3426 } }).setPlane(1))))));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("downstairs", -1, -1, getBot().getMethods(), PATH_TO_STAIRCASE,
						new Area(new int[][] { { 3155, 3426 }, { 3161, 3426 }, { 3161, 3425 }, { 3155, 3425 },
								{ 3155, 3426 } }).setPlane(1),
						new Area(new int[][] { { 3156, 3437 }, { 3161, 3437 }, { 3161, 3436 }, { 3164, 3436 },
								{ 3164, 3432 }, { 3156, 3432 }, { 3156, 3437 } }),
						getScript(), getEvent(), false, true, this,
						new ArrayList<LadderAndDoor>(Arrays.asList(new LadderAndDoor("Staircase", "Climb-down", false,
								new Area(new int[][] { { 3152, 3438 }, { 3160, 3438 }, { 3160, 3431 }, { 3152, 3432 },
										{ 3152, 3438 } }).setPlane(1),
								new Area(new int[][] { { 3157, 3436 }, { 3164, 3436 }, { 3164, 3432 }, { 3157, 3432 },
										{ 3157, 3436 } }))))));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to plane", -1, -1,
				getBot().getMethods(), PATH_TO_PLANE, BEGIN_AREA_WALKING,
				new Area(
						new int[][] { { 3209, 3434 }, { 3216, 3434 }, { 3217, 3423 }, { 3209, 3423 }, { 3209, 3435 } }),
				getScript(), getEvent(), false, true, this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to plane", -1, -1,
				getBot().getMethods(), PATH_TO_PLANE, BEGIN_AREA_WALKING,
				new Area(
						new int[][] { { 3209, 3434 }, { 3216, 3434 }, { 3217, 3423 }, { 3209, 3423 }, { 3209, 3435 } }),
				getScript(), getEvent(), false, true, this));
		laps++;

		// getWorlds().hopToF2PWorld();
	}

	private void initializeBegin() {

	}

	public void onPaint(Graphics2D g) {
		g.drawString("Laps: " + laps, 60, 50);
	}

	@Override
	public void onLoop() throws InterruptedException, IOException {
		getTaskHandler().taskLoop();

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the grandExchangeActions
	 */
	public Ge2 getGrandExchangeTask() {
		return grandExchangeActions;
	}

	/**
	 * @param grandExchangeActions
	 *            the grandExchangeActions to set
	 */
	public void setGrandExchangeActions(Ge2 grandExchangeActions) {
		this.grandExchangeActions = grandExchangeActions;
	}

	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub

	}

}
