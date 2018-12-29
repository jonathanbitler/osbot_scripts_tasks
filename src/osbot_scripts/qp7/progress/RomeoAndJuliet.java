package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.DialogueTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class RomeoAndJuliet extends QuestStep {

	private static final int QUEST_CONFIG = 144;

	private static final List<Position> PATH_TO_VARROCK_SQUARE_ONE = new ArrayList<Position>(
			Arrays.asList(new Position(3234, 3217, 0), new Position(3235, 3225, 0), new Position(3245, 3225, 0),
					new Position(3255, 3225, 0), new Position(3259, 3225, 0), new Position(3256, 3234, 0),
					new Position(3253, 3243, 0), new Position(3250, 3252, 0), new Position(3247, 3261, 0),
					new Position(3244, 3270, 0), new Position(3241, 3279, 0), new Position(3238, 3284, 0),
					new Position(3238, 3294, 0), new Position(3238, 3304, 0), new Position(3239, 3306, 0),
					new Position(3246, 3313, 0), new Position(3253, 3320, 0), new Position(3258, 3323, 0),
					new Position(3267, 3326, 0), new Position(3269, 3327, 0), new Position(3270, 3332, 0),
					new Position(3280, 3333, 0), new Position(3290, 3334, 0), new Position(3300, 3333, 0),
					new Position(3304, 3334, 0), new Position(3304, 3334, 0), new Position(3300, 3343, 0),
					new Position(3298, 3347, 0), new Position(3296, 3357, 0), new Position(3294, 3367, 0),
					new Position(3293, 3369, 0), new Position(3292, 3379, 0), new Position(3291, 3389, 0),
					new Position(3290, 3399, 0), new Position(3289, 3409, 0), new Position(3289, 3413, 0),
					new Position(3285, 3422, 0), new Position(3283, 3429, 0), new Position(3273, 3429, 0),
					new Position(3263, 3429, 0), new Position(3253, 3429, 0), new Position(3243, 3429, 0),
					new Position(3233, 3429, 0), new Position(3223, 3429, 0), new Position(3216, 3428, 0)));

	private static final List<Position> PATH_TO_JULIET = new ArrayList<Position>(
			Arrays.asList(new Position(3210, 3428, 0), new Position(3200, 3429, 0), new Position(3190, 3430, 0),
					new Position(3180, 3431, 0), new Position(3170, 3432, 0), new Position(3161, 3434, 0),
					new Position(3158, 3434, 0)));

	private static final List<Position> WALK_TO_ROMEO_FROM_JULIET = new ArrayList<Position>(
			Arrays.asList(new Position(3160, 3435, 0), new Position(3170, 3432, 0), new Position(3180, 3429, 0),
					new Position(3181, 3429, 0), new Position(3191, 3428, 0), new Position(3201, 3427, 0),
					new Position(3211, 3426, 0), new Position(3212, 3427, 0)));

	private static final Area VARROCK_SQUARE_AREA = new Area(new int[][] { { 3208, 3433 }, { 3210, 3422 },
			{ 3215, 3417 }, { 3220, 3420 }, { 3218, 3431 }, { 3215, 3433 } });

	private static final Area JULIA_FLOOR_0 = new Area(new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3164, 3432 },
			{ 3165, 3432 }, { 3165, 3439 }, { 3164, 3440 }, { 3161, 3440 }, { 3161, 3437 }, { 3156, 3437 } });

	private static final Area JULIA_FLOOR_1 = new Area(
			new int[][] { { 3159, 3437 }, { 3165, 3437 }, { 3165, 3424 }, { 3153, 3424 }, { 3153, 3437 } }).setPlane(1);

	private static final List<Position> PATH_TO_JULIET_FLOOR_1 = new ArrayList<Position>(
			Arrays.asList(new Position(3154, 3435, 1), new Position(3157, 3428, 1), new Position(3160, 3425, 1)));

	private static final Area AT_JULIET = new Area(
			new int[][] { { 3152, 3430 }, { 3152, 3424 }, { 3164, 3424 }, { 3164, 3430 } }).setPlane(1);

	private static final List<Position> PATH_TO_FATHER_LAWRENCE = new ArrayList<Position>(
			Arrays.asList(new Position(3215, 3428, 0), new Position(3221, 3436, 0), new Position(3223, 3439, 0),
					new Position(3230, 3446, 0), new Position(3233, 3449, 0), new Position(3235, 3454, 0),
					new Position(3236, 3460, 0), new Position(3240, 3464, 0), new Position(3245, 3467, 0),
					new Position(3246, 3476, 0), new Position(3250, 3480, 0), new Position(3255, 3480, 0)));

	private static final Area FATHER_LAWRENCE_AREA = new Area(
			new int[][] { { 3249, 3483 }, { 3249, 3476 }, { 3252, 3476 }, { 3252, 3471 }, { 3260, 3471 },
					{ 3260, 3489 }, { 3252, 3489 }, { 3252, 3484 }, { 3249, 3484 } });

	private static final List<Position> PATH_TO_BERRIES = new ArrayList<Position>(
			Arrays.asList(new Position(3256, 3479, 0), new Position(3255, 3469, 0), new Position(3255, 3469, 0),
					new Position(3255, 3468, 0), new Position(3245, 3467, 0), new Position(3245, 3467, 0),
					new Position(3245, 3467, 0), new Position(3245, 3457, 0), new Position(3245, 3451, 0),
					new Position(3245, 3441, 0), new Position(3245, 3434, 0), new Position(3248, 3427, 0),
					new Position(3258, 3427, 0), new Position(3268, 3427, 0), new Position(3269, 3428, 0),
					new Position(3279, 3426, 0), new Position(3279, 3426, 0), new Position(3282, 3425, 0),
					new Position(3286, 3416, 0), new Position(3289, 3407, 0), new Position(3290, 3397, 0),
					new Position(3290, 3397, 0), new Position(3291, 3391, 0), new Position(3292, 3381, 0),
					new Position(3292, 3381, 0), new Position(3292, 3375, 0), new Position(3287, 3374, 0),
					new Position(3280, 3373, 0), new Position(3276, 3372, 0)));

	private static final Area BERRIES_AREA = new Area(
			new int[][] { { 3260, 3373 }, { 3259, 3362 }, { 3278, 3366 }, { 3280, 3375 }, { 3268, 3376 } });

	private static final List<Position> PATH_TO_APOTHECARY = new ArrayList<Position>(
			Arrays.asList(new Position(3273, 3371, 0), new Position(3280, 3373, 0), new Position(3290, 3374, 0),
					new Position(3292, 3374, 0), new Position(3292, 3384, 0), new Position(3292, 3394, 0),
					new Position(3292, 3397, 0), new Position(3289, 3407, 0), new Position(3286, 3417, 0),
					new Position(3283, 3427, 0), new Position(3283, 3427, 0), new Position(3273, 3427, 0),
					new Position(3263, 3427, 0), new Position(3253, 3427, 0), new Position(3243, 3427, 0),
					new Position(3233, 3427, 0), new Position(3223, 3427, 0), new Position(3213, 3427, 0),
					new Position(3203, 3427, 0), new Position(3197, 3428, 0), new Position(3199, 3418, 0),
					new Position(3199, 3415, 0), new Position(3199, 3407, 0), new Position(3190, 3407, 0),
					new Position(3190, 3403, 0), new Position(3195, 3403, 0)));

	private static final Area APOTHECARY_AREA = new Area(
			new int[][] { { 3192, 3406 }, { 3192, 3402 }, { 3199, 3402 }, { 3199, 3407 }, { 3192, 3407 } });

	public RomeoAndJuliet(LoginEvent event, Script script) {
		super(5037, QUEST_CONFIG, AccountStage.QUEST_ROMEO_AND_JULIET, event, script, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {
		log(getQuestProgress());

		// if (getQuestProgress() == 0) {
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock square", 0, QUEST_CONFIG, getBot().getMethods(),
						PATH_TO_VARROCK_SQUARE_ONE, VARROCK_SQUARE_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with romeo", 0, QUEST_CONFIG, getBot().getMethods(), VARROCK_SQUARE_AREA, 5037,
						10, new String[] { "Perhaps I could help to find her for you?", "Yes, ok, I'll let her know.",
								"Ok, thanks." }));

		// }

		// if (getQuestProgress() <= 10) {
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to juliet", 10, QUEST_CONFIG, getBot().getMethods(), PATH_TO_JULIET_FLOOR_1,
						AT_JULIET, getScript(), getEvent(), true, false));

		// new WalkTask("walk to juliet", 10, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET,
		// new Area(new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3164, 3432 }, {
		// 3165, 3432 },
		// { 3165, 3439 }, { 3164, 3440 }, { 3161, 3440 }, { 3161, 3437 }, { 3156, 3437
		// } }),
		// getScript(), getEvent(), false, false));

		// getTaskHandler().getTasks().put(3, new ClickObjectTask("climb up 2", 10,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_0, 11797, "Climb-up", JULIA_FLOOR_1));
		//
		// getTaskHandler().getTasks().put(4, new WalkTask("walk to juliet", 10,
		// QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET_FLOOR_1, AT_JULIET, getScript(), getEvent(), false, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with juliet", 10, QUEST_CONFIG, getBot().getMethods(), AT_JULIET, 6268, 20,
						new String[] { "I guess I could look for him for you.", }));
		// }

		// if (getQuestProgress() <= 20) {
		// getTaskHandler().getTasks().put(6, new WalkTask("walk to climb down", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3160, 3425, 1), new
		// Position(3157, 3428, 1),
		// new Position(3154, 3432, 1), new Position(3154, 3435, 1))),
		// new Area(new int[][] { { 3152, 3437 }, { 3152, 3434 }, { 3157, 3434 }, {
		// 3157, 3438 },
		// { 3152, 3438 } }).setPlane(1),
		// getScript(), getEvent(), false, false));
		//
		// getTaskHandler().getTasks().put(7, new ClickObjectTask("climb down 2", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_1, 11799, "Climb-down", JULIA_FLOOR_0));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock square", 20, QUEST_CONFIG, getBot().getMethods(),
						WALK_TO_ROMEO_FROM_JULIET, VARROCK_SQUARE_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new DialogueTask("talk with romeo", 20,
				QUEST_CONFIG, getBot().getMethods(), VARROCK_SQUARE_AREA, 5037, 30, new String[] { "Ok, thanks." }));
		// }

		// if (getQuestProgress() <= 30) {
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to father lawrence", 30, QUEST_CONFIG, getBot().getMethods(),
						PATH_TO_FATHER_LAWRENCE, FATHER_LAWRENCE_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with father lawrence", 30, QUEST_CONFIG, getBot().getMethods(),
						FATHER_LAWRENCE_AREA, 5038, 40, new String[] { "Ok, thanks." }));
		// }

		// if (getQuestProgress() <= 40) {
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to berries", 40, QUEST_CONFIG, getBot().getMethods(), PATH_TO_BERRIES, BERRIES_AREA,
						getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new ClickObjectTask("take cadava", 40,
				QUEST_CONFIG, getBot().getMethods(), BERRIES_AREA, 23625, "Pick-from", "Cadava berries"));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to apothecary", 40, QUEST_CONFIG, getBot().getMethods(), PATH_TO_APOTHECARY,
						APOTHECARY_AREA, getScript(), getEvent(), false, true));

		// public DialogueTask(String scriptName, int questProgress, int questConfig,
		// MethodProvider prov, Area area,
		// int npcId, String waitForItem, int questPointsFinished, String... selections)
		// {
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with father lawrence", 40, QUEST_CONFIG, getBot().getMethods(), APOTHECARY_AREA,
						5036, "Cadava potion", 50,
						new String[] { "Talk about something else.", "Talk about Romeo & Juliet." }));
		// }

		// if (getQuestProgress() <= 50) {

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to juliet", 50, QUEST_CONFIG, getBot().getMethods(), PATH_TO_JULIET_FLOOR_1,
						AT_JULIET, getScript(), getEvent(), true, false));

		// getTaskHandler().getTasks().put(16,
		// new WalkTask("walk to juliet", 50, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET,
		// new Area(new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3164, 3432 }, {
		// 3165, 3432 },
		// { 3165, 3439 }, { 3164, 3440 }, { 3161, 3440 }, { 3161, 3437 }, { 3156, 3437
		// } }),
		// getScript(), getEvent(), false, false));
		//
		// getTaskHandler().getTasks().put(17, new ClickObjectTask("climb up 2", 50,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_0, 11797, "Climb-up", JULIA_FLOOR_1));
		//
		// getTaskHandler().getTasks().put(18, new WalkTask("walk to juliet", 50,
		// QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET_FLOOR_1, AT_JULIET, getScript(), getEvent(), false, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with juliet", 50, QUEST_CONFIG, getBot().getMethods(), AT_JULIET, 6268, 60,
						new String[] { "I guess I could look for him for you.", }));
		// }

		// if (getQuestProgress() <= 60) {
		// getTaskHandler().getTasks().put(20, new WalkTask("walk to climb down", 60,
		// QUEST_CONFIG,
		// getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3160, 3425, 1), new
		// Position(3157, 3428, 1),
		// new Position(3154, 3432, 1), new Position(3154, 3435, 1))),
		// new Area(new int[][] { { 3152, 3437 }, { 3152, 3434 }, { 3157, 3434 }, {
		// 3157, 3438 },
		// { 3152, 3438 } }).setPlane(1),
		// getScript(), getEvent(), false, false));
		//
		// getTaskHandler().getTasks().put(21, new ClickObjectTask("climb down 2", 60,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_1, 11799, "Climb-down", JULIA_FLOOR_0));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock square", 60, QUEST_CONFIG, getBot().getMethods(),
						WALK_TO_ROMEO_FROM_JULIET, VARROCK_SQUARE_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new DialogueTask("talk with romeo", 60,
				QUEST_CONFIG, getBot().getMethods(), VARROCK_SQUARE_AREA, 5037, 100, new String[] { "Ok, thanks." }));
		// }
	}

	@Override
	public void onLoop() throws InterruptedException {

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
	
	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub
		
	}

}
