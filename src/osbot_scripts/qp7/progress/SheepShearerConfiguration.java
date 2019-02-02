package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
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
import osbot_scripts.taskhandling.TaskHandler;

public class SheepShearerConfiguration extends QuestStep {

	private static final int QUEST_CONFIG = 179;

	public SheepShearerConfiguration(LoginEvent event, Script script) {
		super(5037, QUEST_CONFIG, AccountStage.QUEST_SHEEP_SHEARER, event, script, true);
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
			new int[][] { { 3188, 3276 }, { 3188, 3270 }, { 3192, 3270 }, { 3192, 3276 } });

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
					new Position(3210, 3215, 1), new Position(3212, 3216, 1)));

	private static final Area PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA = new Area(
			new int[][] { { 3208, 3217 }, { 3208, 3212 }, { 3214, 3212 }, { 3214, 3218 }, { 3208, 3218 } }).setPlane(1);

	@Override
	public void onStart() {
		log(getQuestProgress());

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to quest guide", 0, QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START,
						PATH_TO_QUEST_START_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with fred", 0, QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START_AREA,
						732, 1, new String[] { "I'm looking for a quest", "Yes okay. I can do that", "Of course!",
								"I'm something of an expert actually!" }));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new PickupItemTask("pickup shears", 1,
				QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START_AREA, "Take", "Shears"));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to sheeps", 1, QUEST_CONFIG, getBot().getMethods(), PATH_TO_SHEEPS,
						PATH_TO_SHEEPS_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DropItemTask("drop items", 1, QUEST_CONFIG, getBot().getMethods(), "Drop",
						new String[] { "Shrimps", "Water rune", "Mind rune", "Air rune", "Body rune", "Earth rune",
								"Bread", "Wooden shield", "Shortbow", "Bronze arrow", "Bronze dagger", "Grain",
								"Cadava berries" }));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickOnNpcTask("getting whool", 1, QUEST_CONFIG, getBot().getMethods(), "Shear",
						new int[] { 2801, 2795, 2800, 2794, 2796, 2802 }, "Wool", 20, PATH_TO_SHEEPS_AREA));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to spinning wheel", 1, QUEST_CONFIG, getBot().getMethods(),
						PATH_TO_LUMRBDIGE_CASTLE_FLOOR_ONE_SPINNING,
						PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new ClickObjectTask("click spinning wheel",
				1, QUEST_CONFIG, getBot().getMethods(), PATH_TO_LUMBRIDGE_CASTLE_AREA_FLOOR_ONE_SPINNING_AREA, 14889));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickOnWidgetTask("click on interface", 1, QUEST_CONFIG, getBot().getMethods(), "Spin",
						"Ball of Wool", 20, 14889, this, new int[] { 270, 14, 38 }));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to quest giver", 1, QUEST_CONFIG, getBot().getMethods(),
						new ArrayList<Position>(Arrays.asList(new Position(3208, 3210, 0), new Position(3214, 3210, 0),
								new Position(3214, 3218, 0), new Position(3224, 3218, 0), new Position(3234, 3218, 0),
								new Position(3235, 3219, 0), new Position(3230, 3228, 0), new Position(3225, 3237, 0),
								new Position(3220, 3246, 0), new Position(3217, 3253, 0), new Position(3215, 3263, 0),
								new Position(3213, 3273, 0), new Position(3213, 3279, 0), new Position(3203, 3280, 0),
								new Position(3193, 3281, 0), new Position(3188, 3282, 0), new Position(3189, 3272, 0),
								new Position(3189, 3272, 0))),
						PATH_TO_QUEST_START_AREA, getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new DialogueTask("talk with fred quest end",
				1, QUEST_CONFIG, getBot().getMethods(), PATH_TO_QUEST_START_AREA, 732, 21, new String[] { "" }));

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
		boolean clickNpc = tasks.getCurrentTask().getClass().getSimpleName().equalsIgnoreCase("ClickOnNpcTask");
		boolean clickOnWidgetTask = tasks.getCurrentTask().getClass().getSimpleName()
				.equalsIgnoreCase("ClickOnWidgetTask");

		if (clickOnWidgetTask && tasks.getTaskAttempts() > 350) {
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", getEvent().getUsername(), getEvent());
			BotCommands.waitBeforeKill(this, "BECAUSE DEAD AND TIMEOUT ON OBJECT");
		}

		// Clicking npcs when also having ball of wools in inventory & in that area
		if (clickNpc && tasks.getTaskAttempts() > 50 && getInventory().contains("Ball of wool")
				&& new Area(new int[][] { { 3193, 3277 }, { 3205, 3277 }, { 3206, 3276 }, { 3210, 3276 },
						{ 3212, 3274 }, { 3212, 3270 }, { 3213, 3269 }, { 3213, 3257 }, { 3194, 3257 }, { 3193, 3258 },
						{ 3193, 3259 }, { 3192, 3260 }, { 3192, 3261 }, { 3193, 3262 }, { 3193, 3277 } })
								.contains(myPlayer())) {
			getInventory().dropAll("Ball of wool");
			// BotCommands.waitBeforeKill(this, "BECAUSE TOO MANY TIMES CLICKED ON SHEEP!");
		}
	}

}
