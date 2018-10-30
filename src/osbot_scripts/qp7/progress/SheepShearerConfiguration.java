package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.ClickOnNpcTask;
import osbot_scripts.framework.ClickOnWidgetTask;
import osbot_scripts.framework.DialogueTask;
import osbot_scripts.framework.DropItemTask;
import osbot_scripts.framework.PickupItemTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.sections.total.progress.MainState;

public class SheepShearerConfiguration extends QuestStep {

	private static final int QUEST_CONFIG = 179;

	public SheepShearerConfiguration(LoginEvent event, Script script) {
		super(5037, QUEST_CONFIG, AccountStage.QUEST_SHEEP_SHEARER, event, script);
		// TODO Auto-generated constructor stub
	}

	private static final ArrayList<Position> PATH_TO_QUEST_START = new ArrayList<Position>(
			Arrays.asList(new Position(3236, 3224, 0), new Position(3230, 3232, 0), new Position(3224, 3240, 0),
					new Position(3218, 3248, 0), new Position(3217, 3248, 0), new Position(3216, 3258, 0),
					new Position(3216, 3267, 0), new Position(3214, 3277, 0), new Position(3213, 3279, 0),
					new Position(3203, 3280, 0), new Position(3193, 3281, 0), new Position(3188, 3282, 0),
					new Position(3189, 3272, 0), new Position(3189, 3272, 0)));

	private static final ArrayList<Position> PATH_TO_SHEEPS = new ArrayList<Position>(
			Arrays.asList(new Position(3189, 3273, 0), new Position(3188, 3278, 0), new Position(3189, 3282, 0),
					new Position(3198, 3279, 0), new Position(3201, 3278, 0), new Position(3211, 3277, 0),
					new Position(3214, 3277, 0), new Position(3215, 3267, 0), new Position(3216, 3261, 0),
					new Position(3209, 3263, 0)));

	private static final Area PATH_TO_SHEEPS_AREA = new Area(
			new int[][] { { 3193, 3277 }, { 3193, 3262 }, { 3192, 3261 }, { 3192, 3260 }, { 3193, 3259 },
					{ 3193, 3258 }, { 3194, 3257 }, { 3213, 3257 }, { 3213, 3269 }, { 3212, 3270 }, { 3212, 3274 },
					{ 3210, 3276 }, { 3206, 3276 }, { 3205, 3277 }, { 3193, 3277 } });

	private static final Area PATH_TO_QUEST_START_AREA = new Area(
			new int[][] { { 3188, 3275 }, { 3188, 3270 }, { 3193, 3270 }, { 3193, 3276 }, { 3188, 3276 } });

	private static final ArrayList<Position> PATH_TO_LUMBRIDGE_CASTLE = new ArrayList<Position>(
			Arrays.asList(new Position(3204, 3264, 0), new Position(3211, 3261, 0), new Position(3214, 3261, 0),
					new Position(3216, 3251, 0), new Position(3217, 3248, 0), new Position(3223, 3240, 0),
					new Position(3229, 3232, 0), new Position(3235, 3224, 0), new Position(3236, 3224, 0),
					new Position(3227, 3220, 0), new Position(3221, 3217, 0), new Position(3214, 3213, 0),
					new Position(3207, 3210, 0)));

	private static final Area PATH_TO_LUMBRIDGE_CASTLE_AREA = new Area(
			new int[][] { { 3204, 3208 }, { 3204, 3207 }, { 3206, 3207 }, { 3207, 3208 }, { 3207, 3209 },
					{ 3209, 3209 }, { 3209, 3212 }, { 3205, 3212 }, { 3205, 3210 }, { 3204, 3209 } });

	private static final Area PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE = new Area(
			new int[][] { { 3201, 3214 }, { 3201, 3204 }, { 3209, 3206 }, { 3208, 3208 }, { 3208, 3213 } }).setPlane(1);

	private static final ArrayList<Position> PATH_TO_LUMRBDIGE_CASTLE_FLOOR_ONE_SPINNING = new ArrayList<Position>(
			Arrays.asList(new Position(3205, 3208, 1), new Position(3206, 3210, 1), new Position(3206, 3213, 1),
					new Position(3206, 3214, 1), new Position(3209, 3214, 1), new Position(3210, 3214, 1),
					new Position(3210, 3215, 1)));

	private static final Area PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA = new Area(
			new int[][] { { 3208, 3217 }, { 3208, 3212 }, { 3214, 3212 }, { 3214, 3218 }, { 3208, 3218 } }).setPlane(1);

	@Override
	public void onStart() {

		log(getQuestProgress());

		// String scriptName, int questProgress, int questConfig, MethodProvider prov,
		// Area area,
		// String interactOption, String... itemName

		if (getQuestProgress() <= 0) {

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to quest guide", 0,
					QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START, PATH_TO_QUEST_START_AREA, getScript(), getEvent(), true));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new DialogueTask("talk with fred", 0, QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START_AREA,
							732, 1, new String[] { "I'm looking for a quest", "Yes okay. I can do that", "Of course!",
									"I'm something of an expert actually!" }));
		}

		if (getQuestProgress() <= 1) {
			if (!getInventory().contains("Shears")) {
				getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new PickupItemTask("pickup shears",
						1, QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START_AREA, "Take", "Shears"));
			}

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to sheeps", 1,
					QUEST_CONFIG, getBot().getMethods(), PATH_TO_SHEEPS, PATH_TO_SHEEPS_AREA, getScript(), getEvent(), false));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new DropItemTask("walk to quest guide", 1, QUEST_CONFIG, getBot().getMethods(), "Drop",
							new String[] { "Shrimps", "Water rune", "Mind rune", "Air rune", "Body rune", "Earth rune",
									"Bread", "Wooden shield", "Shortbow", "Bronze arrow", "Bronze dagger", "Grain", "Cadava berries" }));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new ClickOnNpcTask("getting whool", 1, QUEST_CONFIG, getBot().getMethods(), "Shear",
							new int[] { 2801, 2795, 2800, 2794, 2796, 2802 }, "Wool", 20, PATH_TO_SHEEPS_AREA));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to lumbridge castle", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_LUMBRIDGE_CASTLE, PATH_TO_LUMBRIDGE_CASTLE_AREA, getScript(), getEvent(), false));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new ClickObjectTask("climb up castle", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_LUMBRIDGE_CASTLE_AREA, 16671, "Climb-up", PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to spinning wheel", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_LUMRBDIGE_CASTLE_FLOOR_ONE_SPINNING,
							PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA, getScript(), getEvent(), false));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new ClickObjectTask("click spinning wheel", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA, 14889));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new ClickOnWidgetTask("click on interface", 1, QUEST_CONFIG, getBot().getMethods(), "Spin",
							"Ball of Wool", 20, 14889, new int[] { 270, 14, 38 }));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to stairs", 1,
					QUEST_CONFIG, getBot().getMethods(),
					new ArrayList<Position>(Arrays.asList(new Position(3210, 3215, 1), new Position(3209, 3215, 1),
							new Position(3206, 3211, 1), new Position(3206, 3209, 1))),
					PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE, getScript(), getEvent(), false));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new ClickObjectTask("climb down castle", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE, 16672, "Climb-down",
							PATH_TO_LUMBRIDGE_CASTLE_AREA));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to quest giver", 1,
					QUEST_CONFIG, getBot().getMethods(),
					new ArrayList<Position>(Arrays.asList(new Position(3208, 3210, 0), new Position(3214, 3210, 0),
							new Position(3214, 3218, 0), new Position(3224, 3218, 0), new Position(3234, 3218, 0),
							new Position(3235, 3219, 0), new Position(3230, 3228, 0), new Position(3225, 3237, 0),
							new Position(3220, 3246, 0), new Position(3217, 3253, 0), new Position(3215, 3263, 0),
							new Position(3213, 3273, 0), new Position(3213, 3279, 0), new Position(3203, 3280, 0),
							new Position(3193, 3281, 0), new Position(3188, 3282, 0), new Position(3189, 3272, 0),
							new Position(3189, 3272, 0))),
					PATH_TO_QUEST_START_AREA, getScript(), getEvent(), false));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new DialogueTask("talk with fred quest end", 1, QUEST_CONFIG, getBot().getMethods(),
							PATH_TO_QUEST_START_AREA, 732, 21, new String[] { "" }));
			// }
		}
		// public ClickOnWidgetTask(String scriptName, int questProgress, int
		// questConfig, MethodProvider prov,
		// String interactOption, String waitOnItem, int waitOnItemAmount, int...
		// widgetIds) {

		// public ClickOnNpcTask(String scriptName, int questProgress, int questConfig,
		// MethodProvider prov,
		// String interactOption, String npcName, String waitForItem, int amount) {

		// getTaskHandler().getTasks().put(1,
		// new DialogueTask("talk with romeo", 0, QUEST_CONFIG, getBot().getMethods(),
		// VARROCK_SQUARE_AREA,
		// 5037, new String[] { "Perhaps I could help to find her for you?",
		// "Yes, ok, I'll let her know.", "Ok, thanks." }));
		//
		// }
		//
		// if (getQuestProgress() <= 10) {
		// getTaskHandler().getTasks().put(2,
		// new WalkTask("walk to juliet", 10, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET,
		// new Area(new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3164, 3432 }, {
		// 3165, 3432 },
		// { 3165, 3439 }, { 3164, 3440 }, { 3161, 3440 }, { 3161, 3437 }, { 3156, 3437
		// } })));
		//
		// getTaskHandler().getTasks().put(3, new ClickObjectTask("climb up 2", 10,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_0, 11797, "Climb-up", JULIA_FLOOR_1));
		//
		// getTaskHandler().getTasks().put(4, new WalkTask("walk to juliet", 10,
		// QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET_FLOOR_1, AT_JULIET));
		//
		// getTaskHandler().getTasks().put(5, new DialogueTask("talk with juliet", 10,
		// QUEST_CONFIG,
		// getBot().getMethods(), AT_JULIET, 6268, new String[] { "I guess I could look
		// for him for you.", }));
		// }
		//
		// if (getQuestProgress() <= 20) {
		// getTaskHandler().getTasks().put(6, new WalkTask("walk to climb down", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3160, 3425, 1), new
		// Position(3157, 3428, 1),
		// new Position(3154, 3432, 1), new Position(3154, 3435, 1))),
		// new Area(new int[][] { { 3152, 3437 }, { 3152, 3434 }, { 3157, 3434 }, {
		// 3157, 3438 },
		// { 3152, 3438 } }).setPlane(1)));
		//
		// getTaskHandler().getTasks().put(7, new ClickObjectTask("climb down 2", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_1, 11799, "Climb-down", JULIA_FLOOR_0));
		//
		// getTaskHandler().getTasks().put(8, new WalkTask("walk to varrock square", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(), WALK_TO_ROMEO_FROM_JULIET, VARROCK_SQUARE_AREA));
		//
		// getTaskHandler().getTasks().put(9, new DialogueTask("talk with romeo", 20,
		// QUEST_CONFIG,
		// getBot().getMethods(), VARROCK_SQUARE_AREA, 5037, new String[] { "Ok,
		// thanks." }));
		// }
		//
		// if (getQuestProgress() <= 30) {
		// getTaskHandler().getTasks().put(10, new WalkTask("walk to father lawrence",
		// 30, QUEST_CONFIG,
		// getBot().getMethods(), PATH_TO_FATHER_LAWRENCE, FATHER_LAWRENCE_AREA));
		//
		// getTaskHandler().getTasks().put(11, new DialogueTask("talk with father
		// lawrence", 30, QUEST_CONFIG,
		// getBot().getMethods(), FATHER_LAWRENCE_AREA, 5038, new String[] { "Ok,
		// thanks." }));
		// }
		//
		// if (getQuestProgress() <= 40) {
		// getTaskHandler().getTasks().put(12, new WalkTask("walk to berries", 40,
		// QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_BERRIES, BERRIES_AREA));
		//
		// getTaskHandler().getTasks().put(13, new ClickObjectTask("take cadava", 40,
		// QUEST_CONFIG,
		// getBot().getMethods(), BERRIES_AREA, 23625, "Pick-from", "Cadava berries"));
		//
		// getTaskHandler().getTasks().put(14, new WalkTask("walk to apothecary", 40,
		// QUEST_CONFIG,
		// getBot().getMethods(), PATH_TO_APOTHECARY, APOTHECARY_AREA));
		//
		// getTaskHandler().getTasks().put(15,
		// new DialogueTask("talk with father lawrence", 40, QUEST_CONFIG,
		// getBot().getMethods(),
		// APOTHECARY_AREA, 5036, "Cadava potion",
		// new String[] { "Talk about something else.", "Talk about Romeo & Juliet."
		// }));
		// }
		//
		// if (getQuestProgress() <= 50) {
		// getTaskHandler().getTasks().put(16,
		// new WalkTask("walk to juliet", 50, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET,
		// new Area(new int[][] { { 3156, 3436 }, { 3156, 3432 }, { 3164, 3432 }, {
		// 3165, 3432 },
		// { 3165, 3439 }, { 3164, 3440 }, { 3161, 3440 }, { 3161, 3437 }, { 3156, 3437
		// } })));
		//
		// getTaskHandler().getTasks().put(17, new ClickObjectTask("climb up 2", 50,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_0, 11797, "Climb-up", JULIA_FLOOR_1));
		//
		// getTaskHandler().getTasks().put(18, new WalkTask("walk to juliet", 50,
		// QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_JULIET_FLOOR_1, AT_JULIET));
		//
		// getTaskHandler().getTasks().put(19, new DialogueTask("talk with juliet", 50,
		// QUEST_CONFIG,
		// getBot().getMethods(), AT_JULIET, 6268, new String[] { "I guess I could look
		// for him for you.", }));
		// }
		//
		// if (getQuestProgress() <= 60) {
		// getTaskHandler().getTasks().put(20, new WalkTask("walk to climb down", 60,
		// QUEST_CONFIG,
		// getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3160, 3425, 1), new
		// Position(3157, 3428, 1),
		// new Position(3154, 3432, 1), new Position(3154, 3435, 1))),
		// new Area(new int[][] { { 3152, 3437 }, { 3152, 3434 }, { 3157, 3434 }, {
		// 3157, 3438 },
		// { 3152, 3438 } }).setPlane(1)));
		//
		// getTaskHandler().getTasks().put(21, new ClickObjectTask("climb down 2", 60,
		// QUEST_CONFIG,
		// getBot().getMethods(), JULIA_FLOOR_1, 11799, "Climb-down", JULIA_FLOOR_0));
		//
		// getTaskHandler().getTasks().put(22, new WalkTask("walk to varrock square",
		// 60, QUEST_CONFIG,
		// getBot().getMethods(), WALK_TO_ROMEO_FROM_JULIET, VARROCK_SQUARE_AREA));
		//
		// getTaskHandler().getTasks().put(23, new DialogueTask("talk with romeo", 60,
		// QUEST_CONFIG,
		// getBot().getMethods(), VARROCK_SQUARE_AREA, 5037, new String[] { "Ok,
		// thanks." }));
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

}
