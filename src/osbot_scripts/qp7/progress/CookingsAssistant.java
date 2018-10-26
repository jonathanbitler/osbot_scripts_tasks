package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.DialogueTask;
import osbot_scripts.framework.ItemOnObjectTask;
import osbot_scripts.framework.PickupItemTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.task.Task;
import osbot_scripts.taskhandling.TaskHandler;

public class CookingsAssistant extends QuestStep {

	private List<Task> cookingAssistantTask = new ArrayList<Task>();

	private Task currentTask;

	private static final List<Position> PATH_TO_COOK = new ArrayList<Position>(
			Arrays.asList(new Position(3235, 3218, 0), new Position(3227, 3218, 0), new Position(3217, 3218, 0),
					new Position(3214, 3218, 0), new Position(3214, 3210, 0), new Position(3208, 3210, 0),
					new Position(3210, 3213, 0)));

	private static final List<Position> PATH_TO_MILKING_COW = new ArrayList<Position>(
			Arrays.asList(new Position(3208, 3212, 0), new Position(3214, 3210, 0), new Position(3214, 3219, 0),
					new Position(3224, 3219, 0), new Position(3234, 3219, 0), new Position(3235, 3218, 0),
					new Position(3236, 3225, 0), new Position(3246, 3226, 0), new Position(3256, 3227, 0),
					new Position(3258, 3227, 0), new Position(3255, 3237, 0), new Position(3252, 3247, 0),
					new Position(3249, 3257, 0), new Position(3250, 3257, 0), new Position(3249, 3266, 0),
					new Position(3256, 3269, 0)));

	private static final Area MILKING_AREA = new Area(new int[][] { { 3253, 3255 }, { 3266, 3255 }, { 3266, 3297 },
			{ 3264, 3299 }, { 3262, 3299 }, { 3261, 3300 }, { 3257, 3300 }, { 3256, 3299 }, { 3241, 3299 },
			{ 3240, 3298 }, { 3240, 3296 }, { 3241, 3295 }, { 3241, 3294 }, { 3242, 3293 }, { 3242, 3290 },
			{ 3241, 3289 }, { 3241, 3288 }, { 3240, 3287 }, { 3240, 3285 }, { 3244, 3281 }, { 3244, 3280 },
			{ 3246, 3278 }, { 3249, 3278 }, { 3251, 3276 }, { 3251, 3274 }, { 3253, 3272 } });

	private static final List<Position> PATH_TO_CHICKENS = new ArrayList<Position>(
			Arrays.asList(new Position(3255, 3266, 0), new Position(3249, 3267, 0), new Position(3244, 3274, 0),
					new Position(3239, 3283, 0), new Position(3238, 3285, 0), new Position(3239, 3294, 0),
					new Position(3230, 3299, 0), new Position(3230, 3299, 0)));

	private static final List<Position> PATH_TO_WHEAT_FIELD = new ArrayList<Position>(
			Arrays.asList(new Position(3233, 3293, 0), new Position(3238, 3296, 0), new Position(3239, 3286, 0),
					new Position(3240, 3276, 0), new Position(3240, 3276, 0), new Position(3242, 3266, 0),
					new Position(3243, 3260, 0), new Position(3233, 3260, 0), new Position(3223, 3260, 0),
					new Position(3215, 3260, 0), new Position(3214, 3270, 0), new Position(3213, 3280, 0),
					new Position(3213, 3280, 0), new Position(3203, 3281, 0), new Position(3193, 3282, 0),
					new Position(3184, 3284, 0), new Position(3174, 3285, 0), new Position(3164, 3286, 0),
					new Position(3162, 3287, 0), new Position(3159, 3296, 0), new Position(3158, 3299, 0)));

	private static final List<Position> PATH_TO_WINDMILL = new ArrayList<Position>(
			Arrays.asList(new Position(3162, 3287, 0), new Position(3166, 3291, 0), new Position(3167, 3300, 0),
					new Position(3169, 3307, 0)));

	private static final Area CHICKENS_AREA = new Area(
			new int[][] { { 3226, 3302 }, { 3225, 3301 }, { 3225, 3295 }, { 3231, 3295 }, { 3231, 3287 },
					{ 3237, 3287 }, { 3237, 3290 }, { 3238, 3291 }, { 3238, 3293 }, { 3237, 3294 }, { 3237, 3297 },
					{ 3238, 3298 }, { 3238, 3299 }, { 3238, 3300 }, { 3235, 3302 }, { 3226, 3302 } });

	private static final Area COOKS_AREA = new Area(new int[][] { { 3205, 3217 }, { 3205, 3209 }, { 3215, 3209 },
			{ 3217, 3211 }, { 3217, 3212 }, { 3213, 3212 }, { 3213, 3218 }, { 3205, 3218 } });

	private static final Area WHEAT_AREA = new Area(
			new int[][] { { 3153, 3305 }, { 3153, 3297 }, { 3155, 3295 }, { 3157, 3295 }, { 3162, 3290 },
					{ 3164, 3290 }, { 3165, 3291 }, { 3165, 3292 }, { 3164, 3293 }, { 3164, 3296 }, { 3165, 3297 },
					{ 3165, 3299 }, { 3162, 3302 }, { 3162, 3303 }, { 3160, 3305 }, { 3160, 3306 }, { 3159, 3307 },
					{ 3158, 3307 }, { 3157, 3308 }, { 3155, 3308 }, { 3153, 3306 }, { 3153, 3305 } });

	private static final Position FLOWER_POSITION = new Position(3165, 3308, 2);

	private static final Area FLOWER_AREA = new Area(new int[][] { { 3164, 3308 }, { 3166, 3310 }, { 3168, 3310 },
			{ 3170, 3308 }, { 3170, 3306 }, { 3168, 3304 }, { 3166, 3304 }, { 3164, 3306 }, { 3164, 3308 } })
					.setPlane(2);

	private static final Area WHEAT_FLOOR_1 = new Area(new int[][] { { 3166, 3310 }, { 3168, 3310 }, { 3170, 3308 },
			{ 3170, 3306 }, { 3168, 3304 }, { 3165, 3304 }, { 3164, 3306 }, { 3164, 3308 } }).setPlane(1);

	private static final Area WHEAT_FLOOR_2 = new Area(new int[][] { { 3166, 3310 }, { 3168, 3310 }, { 3170, 3308 },
			{ 3170, 3306 }, { 3168, 3304 }, { 3165, 3304 }, { 3164, 3306 }, { 3164, 3308 } }).setPlane(2);

	private static final Area WHEAT_FLOOR_0 = new Area(new int[][] { { 3166, 3310 }, { 3168, 3310 }, { 3170, 3308 },
			{ 3170, 3306 }, { 3168, 3304 }, { 3165, 3304 }, { 3164, 3306 }, { 3164, 3308 } }).setPlane(0);

	private static final Area BASEMENT_AREA = new Area(
			new int[][] { { 3207, 9614 }, { 3207, 9627 }, { 3221, 9627 }, { 3221, 9614 } });

	private static final List<Position> PATH_TO_COOK_FROM_MILL = new ArrayList<Position>(
			Arrays.asList(new Position(3166, 3301, 0), new Position(3166, 3291, 0), new Position(3166, 3287, 0),
					new Position(3176, 3285, 0), new Position(3186, 3283, 0), new Position(3186, 3283, 0),
					new Position(3196, 3281, 0), new Position(3206, 3279, 0), new Position(3208, 3278, 0),
					new Position(3214, 3270, 0), new Position(3216, 3268, 0), new Position(3216, 3258, 0),
					new Position(3216, 3250, 0), new Position(3222, 3242, 0), new Position(3227, 3234, 0),
					new Position(3231, 3225, 0), new Position(3235, 3216, 0), new Position(3225, 3217, 0),
					new Position(3218, 3218, 0), new Position(3215, 3219, 0), new Position(3214, 3213, 0),
					new Position(3214, 3209, 0), new Position(3209, 3209, 0), new Position(3208, 3214, 0)));

	private static final Position POT_ON_MACHINE = new Position(3167, 3305, 0);

	private static final Position BUCKET_POSITION_IN_BASEMENT = new Position(3215, 9624, 0);

	private static final int QUEST_CONFIG = 29;

	public CookingsAssistant(int questStartNpc, int configQuestId) {
		super(questStartNpc, configQuestId, AccountStage.QUEST_COOK_ASSISTANT);
		// TODO Auto-generated constructor stub
	}

	private String scriptName = this.getClass().getSimpleName();

	/**
	 */
	public void decideOnStartTask() {
		if (getCurrentTask() != null) {
			return;
		}
		// The task system
		for (Task task : getCookingAssistantTask()) {
			if (getCurrentTask() == null && getQuestProgress() == task.requiredConfigQuestStep()) {
				setCurrentTask(task);
				log("set task to: " + getCurrentTask());
			} else {
				log("is not in quest step for: " + task.getClass().getSimpleName() + " step: "
						+ task.requiredConfigQuestStep() + " curr: " + getQuestProgress());
			}
		}
	}

	@Override
	public void onStart() {

		getCookingAssistantTask()
				.add(new WalkTask("walk to cook", 0, QUEST_CONFIG, getBot().getMethods(), PATH_TO_COOK, COOKS_AREA));

		getCookingAssistantTask().add(new DialogueTask("talk with cook", 0, QUEST_CONFIG, getBot().getMethods(),
				COOKS_AREA, 4626, new String[] { "What's wrong?", "I'm always happy to help a cook in distress",
						"I'll get right on it", "Actually, I know where to find this stuff." }));

		if (!getInventory().contains("Bucket of milk")) {
			getCookingAssistantTask().add(new WalkTask("walk cow milking", 1, QUEST_CONFIG, getBot().getMethods(),
					PATH_TO_MILKING_COW, MILKING_AREA));

			getCookingAssistantTask().add(new ClickObjectTask("fill bucket", 1, QUEST_CONFIG, getBot().getMethods(),
					MILKING_AREA, 8689, "Milk", "Bucket of milk"));
		}

		if (!getInventory().contains("Egg")) {
			getCookingAssistantTask().add(new WalkTask("walk to chickens", 1, QUEST_CONFIG, getBot().getMethods(),
					PATH_TO_CHICKENS, CHICKENS_AREA));

			getCookingAssistantTask().add(
					new PickupItemTask("pickup egg", 1, QUEST_CONFIG, getBot().getMethods(), CHICKENS_AREA, "Egg"));
		}

		if (!getInventory().contains("Pot of flour")) {

			getCookingAssistantTask().add(new WalkTask("path to wheat field", 1, QUEST_CONFIG, getBot().getMethods(),
					PATH_TO_WHEAT_FIELD, WHEAT_AREA));

			if (!getInventory().contains("Grain")) {

				getCookingAssistantTask().add(new ClickObjectTask("take wheat", 1, QUEST_CONFIG, getBot().getMethods(),
						WHEAT_AREA, 15506, "Pick", "Grain"));
			}
			getCookingAssistantTask().add(new WalkTask("path to wind mill", 1, QUEST_CONFIG, getBot().getMethods(),
					PATH_TO_WINDMILL, WHEAT_FLOOR_0));

			// go up 2 ladders
			getCookingAssistantTask().add(new ClickObjectTask("climb up 1", 1, QUEST_CONFIG, getBot().getMethods(),
					WHEAT_FLOOR_0, 12964, "Climb-up", WHEAT_FLOOR_1));

			getCookingAssistantTask().add(new ClickObjectTask("climb up 2", 1, QUEST_CONFIG, getBot().getMethods(),
					WHEAT_FLOOR_1, 12965, "Climb-up", WHEAT_FLOOR_2));

			getCookingAssistantTask().add(new ItemOnObjectTask("grain on machine", 1, QUEST_CONFIG,
					getBot().getMethods(), FLOWER_AREA, 24961, "Grain"));

			getCookingAssistantTask().add(new ClickObjectTask("operate", 1, QUEST_CONFIG, getBot().getMethods(),
					WHEAT_FLOOR_2, 24964, 24967));

			// go down 2 ladders
			getCookingAssistantTask().add(new ClickObjectTask("climb down 1", 1, QUEST_CONFIG, getBot().getMethods(),
					WHEAT_FLOOR_2, 12966, "Climb-down", WHEAT_FLOOR_1));

			getCookingAssistantTask().add(new ClickObjectTask("climb down 0", 1, QUEST_CONFIG, getBot().getMethods(),
					WHEAT_FLOOR_1, 12965, "Climb-down", WHEAT_FLOOR_0));

			getCookingAssistantTask().add(new ClickObjectTask("get flour to pot", 1, QUEST_CONFIG,
					getBot().getMethods(), WHEAT_FLOOR_0, 1781, "Pot of flour"));

		}

		getCookingAssistantTask().add(new WalkTask("path to cook from mill", 1, QUEST_CONFIG, getBot().getMethods(),
				PATH_TO_COOK_FROM_MILL, COOKS_AREA));

		getCookingAssistantTask().add(new DialogueTask("talk with cook final", 1, QUEST_CONFIG, getBot().getMethods(),
				COOKS_AREA, 4626, new String[] { "" }));

	}

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		// log(getQuestProgress());
		// log(progress);
		//
		// switch (getQuestProgress()) {
		// case 0:
		// // Starting quest
		//
		// if (walkToPosition(PATH_TO_COOK, COOKS_AREA) && talkWithNpc()) {
		// log("talked with npc");
		// }
		//
		// break;
		//
		// case 1:
		// // Progress on starting up
		// if (progress == CooksAssistantSteps.TALK_WITH_COOK) {
		// progress = CooksAssistantSteps.GOING_TO_BASEMENT_FOR_BUCKET;
		// }
		//
		// // Started quest
		// if (progress == CooksAssistantSteps.GOING_TO_BASEMENT_FOR_BUCKET) {
		// if (pickupBucket()) {
		// progress = CooksAssistantSteps.GETTING_BUCKET_OF_MILK;
		// log("picked up bucket");
		// if (walkUpToCook()) {
		// log("walked back to cook");
		// }
		// }
		// } else if (progress == CooksAssistantSteps.GETTING_BUCKET_OF_MILK) {
		// if (getBucketOfMilk()) {
		// log("got bucket of milk");
		// }
		// }
		// break;
		//
		// case 2:
		// // Finished quest
		// break;
		//
		// default:
		// break;
		// }

	}

	/**
	 * 
	 * @return
	 */
	private boolean walkUpToCook() {
		if (BASEMENT_AREA.contains(myPlayer())) {
			if (clickObjectToArea("Ladder", COOKS_AREA)) {
				return clickObjectToArea("Ladder", COOKS_AREA);
			}
		} else {
			return walkToPosition(PATH_TO_COOK, COOKS_AREA);
		}
		return false;
	}

	/**
	 * Retreives a bucket of milk
	 * 
	 * @return
	 */
	private boolean getBucketOfMilk() {
		if (getInventory().contains("Bucket of milk")) {
			return true;
		}

		if (walkToPosition(PATH_TO_MILKING_COW, MILKING_AREA)) {
			return clickObject(8689, "Bucket of milk", "Milk");
		}
		return false;
	}

	/**
	 * Picks up the bucket in the basement
	 * 
	 * @return
	 */
	// private boolean pickupBucket() {
	// // je hebt al een bucket
	// if (getInventory().contains("Bucket")) {
	// return true;
	// }
	// progress = CooksAssistantSteps.GOING_TO_BASEMENT_FOR_BUCKET;
	//
	// // wanneer niet in de basement ga er naartoe
	// if (!getInventory().contains("Bucket")) {
	// if (!BASEMENT_AREA.contains(myPlayer())) {
	// if (walkToPosition(PATH_TO_COOK, COOKS_AREA) && clickObjectToArea(14880,
	// BASEMENT_AREA)) {
	// log("going down to basement");
	// }
	// }
	//
	// // wanneer wel in de basement ga het oppakken
	// else if (BASEMENT_AREA.contains(myPlayer())) {
	// if (getWalking().walk(BUCKET_POSITION_IN_BASEMENT)) {
	// if (pickupItem(1925)) {
	// log("trying to pickup");
	// Sleep.sleepUntil(() -> getInventory().contains("Bucket"), 10000);
	// return true;
	// } else {
	// // wanneer er geen bucket is, wachten op de bucket
	// log("waiting for bucket spawn...");
	// Sleep.sleepUntil(() -> getGroundItems().closest("Bucket") != null, 60000,
	// 2000);
	// return false;
	// }
	// }
	// }
	// }
	// return false;
	// }

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
	 * @return the cookingAssistantTask
	 */
	public List<Task> getCookingAssistantTask() {
		return cookingAssistantTask;
	}

	/**
	 * @param cookingAssistantTask
	 *            the cookingAssistantTask to set
	 */
	public void setCookingAssistantTask(List<Task> cookingAssistantTask) {
		this.cookingAssistantTask = cookingAssistantTask;
	}

	/**
	 * @return the currentTask
	 */
	public Task getCurrentTask() {
		return currentTask;
	}

	/**
	 * @param currentTask
	 *            the currentTask to set
	 */
	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

}
