package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.BankTask;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.DialogueTask;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.ItemOnObjectTask;
import osbot_scripts.framework.PickupItemTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.entities.Rock;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class DoricsQuestConfig extends QuestStep {

	private static final List<Position> PATH_TO_CLAY_ORE = new ArrayList<Position>(
			Arrays.asList(new Position(3180, 3370, 0)));

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

	private static final Area CLAY_ORE_AREA = new Area(
			new int[][] { { 3178, 3370 }, { 3179, 3367 }, { 3183, 3371 }, { 3180, 3373 } });

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

	private static final int QUEST_CONFIG = 31;

	private static final ArrayList<Position> BANK_POSITION_VARROCK_EAST = new ArrayList<Position>(
			Arrays.asList(new Position(3184, 3436, 0)));

	private static final ArrayList<Position> DORIC_QUEST_START = new ArrayList<Position>(
			Arrays.asList(new Position(2952, 3452, 0)));

	private static final Area DORIC_QUEST_START_AREA = new Area(new int[][] { { 2950, 3454 }, { 2950, 3449 },
			{ 2954, 3449 }, { 2954, 3455 }, { 2950, 3455 }, { 2950, 3454 } });

	private static final ArrayList<Position> BANK_PATH_TO_MINING_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3182, 3435, 0), new Position(3181, 3429, 0), new Position(3172, 3424, 0),
					new Position(3170, 3423, 0), new Position(3171, 3413, 0), new Position(3172, 3403, 0),
					new Position(3171, 3393, 0), new Position(3172, 3390, 0), new Position(3177, 3381, 0),
					new Position(3182, 3372, 0), new Position(3180, 3371, 0)));

	private static final ArrayList<Position> MINING_AREA_TO_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3371, 0), new Position(3178, 3380, 0), new Position(3173, 3389, 0),
					new Position(3168, 3398, 0), new Position(3168, 3399, 0), new Position(3169, 3409, 0),
					new Position(3170, 3419, 0), new Position(3172, 3428, 0), new Position(3182, 3431, 0),
					new Position(3183, 3431, 0), new Position(3184, 3436, 0)));

	public static final ArrayList<Position> MINING_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3180, 3370, 0)));

	private static final Area BANK_VARROCK_EAST_AREA = new Area(new int[][] { { 3180, 3447 }, { 3180, 3433 },
			{ 3186, 3433 }, { 3186, 3436 }, { 3189, 3436 }, { 3189, 3448 }, { 3180, 3448 } });

	public static final Area MINING_AREA = new Area(new int[][] { { 3179, 3379 }, { 3170, 3366 }, { 3175, 3363 },
			{ 3180, 3365 }, { 3184, 3373 }, { 3186, 3380 }, { 3182, 3381 } });

	private static final Area NOT_IN_CORRECT_ZONE = new Area(
			new int[][] { { 3187, 3449 }, { 3163, 3447 }, { 3145, 3415 }, { 3149, 3380 }, { 3173, 3344 },
					{ 3210, 3358 }, { 3232, 3387 }, { 3230, 3438 }, { 3227, 3456 } });

	public DoricsQuestConfig(int questStartNpc, int configQuestId, LoginEvent event, Script script) {
		super(questStartNpc, configQuestId, AccountStage.QUEST_DORICS_QUEST, event, script, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {

		/**
		 * correct
		 */
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank", 0, QUEST_CONFIG, getBot().getMethods(),
						BANK_POSITION_VARROCK_EAST,
						new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
						getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new BankTask("withdraw pickaxe", 0,
				getBot().getMethods(), true, new BankItem[] { new BankItem("pickaxe", 1, false) },
				new Area(
						new int[][] { { 3180, 3439 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3440 }, { 3180, 3440 } }),
				this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to mining area", 0, QUEST_CONFIG, getBot().getMethods(), BANK_PATH_TO_MINING_AREA,
						BANK_VARROCK_EAST_AREA, MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to mining spot", 0, QUEST_CONFIG, getBot().getMethods(), MINING_POSITION,
						MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click mining", 0, QUEST_CONFIG, getBot().getMethods(),
						new Area(new int[][] { { 3184, 3374 }, { 3179, 3374 }, { 3179, 3369 }, { 3184, 3369 } }), 7454,
						new BankItem("Clay", 1, false), true, this, Rock.CLAY));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank", 0, QUEST_CONFIG, getBot().getMethods(),
						BANK_POSITION_VARROCK_EAST,
						new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
						getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new BankTask("withdraw pickaxe", 0,
				getBot().getMethods(), true, new BankItem[] { new BankItem("pickaxe", 1, false) },
				new Area(
						new int[][] { { 3180, 3439 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3440 }, { 3180, 3440 } }),
				this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Copper ore", 436, 4, 150, true),
								new BankItem("Iron ore", 440, 2, 250, true), new BankItem("Clay", 434, 6, 160, true), },
						new BankItem[] { new BankItem("Clay", 434, 80, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
						getEvent(), getScript(), this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new BankTask("withdraw items for quest", 0, getBot().getMethods(), true,
						new BankItem[] { new BankItem("Iron ore", 2, false), new BankItem("Copper ore", 4, false),
								new BankItem("Clay", 6, false) },
						new Area(new int[][] { { 3146, 3506 }, { 3148, 3470 }, { 3182, 3473 }, { 3182, 3508 } }),
						this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to dorics quest with items", 0, QUEST_CONFIG, getBot().getMethods(),
						DORIC_QUEST_START, DORIC_QUEST_START_AREA, getScript(), getEvent(), true, false));

		// TODO wrong npc id and dialogue & dialogue step after talk
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new DialogueTask("talk with doric", getQuestProgress() == 10 ? 10 : 0, QUEST_CONFIG,
						getBot().getMethods(), DORIC_QUEST_START_AREA, 3893, 1, this,
						new String[] { "I wanted to use your anvils.", "Yes, I will get you the materials.",
								"Certainly, I'll be right back!" }));

		/**
		 * incorrect
		 */

		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new DialogueTask("talk with cook", 0, QUEST_CONFIG, getBot().getMethods(),
		// CLAY_ORE_AREA, 4626, 1,
		// new String[] { "What's wrong?", "I'm always happy to help a cook in
		// distress",
		// "I'll get right on it", "Actually, I know where to find this stuff." }));
		//
		// // if (!getInventory().contains("Bucket of milk")) {
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("walk cow milking", 1, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_MILKING_COW,
		// MILKING_AREA, getScript(), getEvent(), true, false));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// ClickObjectTask("fill bucket", 1,
		// QUEST_CONFIG, getBot().getMethods(), MILKING_AREA, 8689, "Milk", "Bucket of
		// milk"));
		// // }
		//
		// // if (!getInventory().contains("Egg")) {
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("walk to chickens", 1, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_CHICKENS,
		// CHICKENS_AREA, getScript(), getEvent(), true, false));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new PickupItemTask("pickup egg", 1, QUEST_CONFIG, getBot().getMethods(),
		// CHICKENS_AREA, "Take", "Egg"));
		// // }
		//
		// // if (!getInventory().contains("Pot of flour")) {
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("path to wheat field", 1, QUEST_CONFIG, getBot().getMethods(),
		// PATH_TO_WHEAT_FIELD,
		// WHEAT_AREA, getScript(), getEvent(), true, false));
		//
		// // if (!getInventory().contains("Grain")) {
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// ClickObjectTask("take wheat", 1,
		// QUEST_CONFIG, getBot().getMethods(), WHEAT_AREA, 15506, "Pick", "Grain"));
		// // }
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("path to wind mill", 1, QUEST_CONFIG, getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3165, 3308, 2))),
		// WHEAT_FLOOR_2, getScript(),
		// getEvent(), true, false));
		//
		// // go up 2 ladders
		// // getTaskHandler().getTasks().put(9, new ClickObjectTask("climb up 1", 1,
		// // QUEST_CONFIG, getBot().getMethods(),
		// // WHEAT_FLOOR_0, 12964, "Climb-up", WHEAT_FLOOR_1));
		// //
		// // getTaskHandler().getTasks().put(10, new ClickObjectTask("climb up 2", 1,
		// // QUEST_CONFIG,
		// // getBot().getMethods(), WHEAT_FLOOR_1, 12965, "Climb-up", WHEAT_FLOOR_2));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// ItemOnObjectTask("grain on machine", 1,
		// QUEST_CONFIG, getBot().getMethods(), FLOWER_AREA, 24961, "Grain"));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new ClickObjectTask("operate", 1, QUEST_CONFIG, getBot().getMethods(),
		// WHEAT_FLOOR_2, 24964, 24967,
		// new String[] { "You operate the hopper. The grain slides down the chute.",
		// "You operate the empty hopper. Nothing interesting happens." }));
		//
		// // go down 2 ladders
		// // getTaskHandler().getTasks().put(13, new ClickObjectTask("climb down 1", 1,
		// // QUEST_CONFIG,
		// // getBot().getMethods(), WHEAT_FLOOR_2, 12966, "Climb-down",
		// WHEAT_FLOOR_1));
		// //
		// // getTaskHandler().getTasks().put(14, new ClickObjectTask("climb down 0", 1,
		// // QUEST_CONFIG,
		// // getBot().getMethods(), WHEAT_FLOOR_1, 12965, "Climb-down",
		// WHEAT_FLOOR_0));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("path down", 1, QUEST_CONFIG, getBot().getMethods(),
		// new ArrayList<Position>(Arrays.asList(new Position(3167, 3305, 0))),
		// WHEAT_FLOOR_0, getScript(),
		// getEvent(), true, false));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// ClickObjectTask("get flour to pot", 1,
		// QUEST_CONFIG, getBot().getMethods(), WHEAT_FLOOR_0, 1781, "Pot of flour"));
		//
		// // }
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
		// new WalkTask("path to cook from mill", 1, QUEST_CONFIG,
		// getBot().getMethods(), PATH_TO_COOK_FROM_MILL,
		// CLAY_ORE_AREA, getScript(), getEvent(), true, false));
		//
		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// DialogueTask("talk with cook final", 1,
		// QUEST_CONFIG, getBot().getMethods(), CLAY_ORE_AREA, 4626, 2, new String[] {
		// "" }));

	}

	private void resetQuestScript() {
		log("Restarting because of restarting script");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void onLoop() throws InterruptedException {
		if (getCombat().isFighting() || myPlayer().isUnderAttack()) {
			log("Under attack! Resetting stage for now! Going a round to stuck mugger");
			MuggerStuck.runCopperMine(getScript(), (MethodProvider) this);
		}

		if (getInventory().getEmptySlotCount() <= 14 && getQuestStageStep() == 4 && !getInventory().contains("Clay")) {
			resetStage(AccountStage.QUEST_DORICS_QUEST.name());
		}

		if (new Area(new int[][] { { 2950, 3454 }, { 2950, 3449 }, { 2954, 3449 }, { 2954, 3455 }, { 2950, 3455 },
				{ 2950, 3454 } }).contains(myPlayer()) && !inventoryContainsAllItems()) {
			resetStage(AccountStage.QUEST_DORICS_QUEST.name());
		}

		if (MINING_AREA.contains(myPlayer())
				&& (!getInventory().contains("Bronze pickaxe") && !getInventory().contains("Iron pickaxe")
						&& !getInventory().contains("Steel pickaxe") && !getInventory().contains("Mithril pickaxe"))
				|| (getInventory().isFull() && getInventory().getAmount("Clay") < 20)) {
			log("Is at mining area wihout a pickaxe, restarting tasks!");
			resetStage(AccountStage.QUEST_DORICS_QUEST.name());
		}
	}

	private boolean inventoryContainsAllItems() {
		return (getInventory().getAmount("Iron ore") >= 2 && getInventory().getAmount("Clay") >= 6
				&& getInventory().getAmount("Copper ore") >= 4 && getInventory().getAmount(437) <= 0)
				&& (getInventory().getAmount(441) <= 0) && (getInventory().getAmount(435) <= 0);
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

		/**
		 * Restarting when bank attempts > 10 to mining to 15
		 */
		if (tasks.isBankTask() && tasks.getTaskAttempts() > 10) {
			DatabaseUtilities.updateStageProgress(tasks.getProvider(), AccountStage.MINING_LEVEL_TO_15.name(), 0,
					getEvent().getUsername(), getEvent());

			BotCommands.waitBeforeKill(tasks.getProvider(),
					"BECAUSE TASK TIMEOUT ON ATTEMPTS E02 RESTARTING TO MINING");
		}

	}

}
