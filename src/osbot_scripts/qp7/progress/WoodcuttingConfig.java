package osbot_scripts.qp7.progress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.Axe;
import osbot_scripts.framework.BankTask;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.ConcreteWalking;
import osbot_scripts.framework.GEPrice;
import osbot_scripts.framework.GenieLamp;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class WoodcuttingConfig extends QuestStep {

	public WoodcuttingConfig(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.WOODCUTTING_GOLD_FARM, event, script, false);
		// TODO Auto-generated constructor stub
	}

	static final Area MINING_ZONE = new Area(new int[][] { { 3132, 3534 }, { 3224, 3527 }, { 3274, 3456 },
			{ 3242, 3352 }, { 3151, 3352 }, { 3083, 3429 } });

	private static final ArrayList<Position> BANK_POSITION_VARROCK_EAST = new ArrayList<Position>(
			Arrays.asList(new Position(3184, 3436, 0)));

	private static final ArrayList<Position> BANK_PATH_TO_MINING_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3182, 3435, 0), new Position(3181, 3429, 0), new Position(3172, 3424, 0),
					new Position(3170, 3423, 0), new Position(3171, 3413, 0), new Position(3172, 3403, 0),
					new Position(3171, 3393, 0), new Position(3172, 3390, 0), new Position(3177, 3381, 0),
					new Position(3182, 3372, 0), new Position(3180, 3371, 0)));

	private static final ArrayList<Position> BANK_TO_WC_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3182, 3435, 0), new Position(3181, 3428, 0), new Position(3172, 3424, 0),
					new Position(3168, 3422, 0), new Position(3164, 3413, 0)));

	private static final ArrayList<Position> WC_AREA_TO_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(3165, 3380, 0), new Position(3164, 3390, 0), new Position(3164, 3390, 0),
					new Position(3164, 3392, 0), new Position(3164, 3402, 0), new Position(3164, 3405, 0),
					new Position(3167, 3415, 0), new Position(3167, 3415, 0), new Position(3168, 3420, 0),
					new Position(3176, 3426, 0), new Position(3181, 3430, 0), new Position(3183, 3434, 0),
					new Position(3183, 3437, 0), new Position(3182, 3437, 0)));

	private static final ArrayList<Position> MINING_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3180, 3370, 0)));

	private static final Area BANK_VARROCK_EAST_AREA = new Area(new int[][] { { 3180, 3447 }, { 3180, 3433 },
			{ 3186, 3433 }, { 3186, 3436 }, { 3189, 3436 }, { 3189, 3448 }, { 3180, 3448 } });

	private static final Area MINING_AREA = new Area(new int[][] { { 3179, 3379 }, { 3170, 3366 }, { 3175, 3363 },
			{ 3180, 3365 }, { 3184, 3373 }, { 3186, 3380 }, { 3182, 3381 } });

	private static final Area NOT_IN_CORRECT_ZONE = new Area(
			new int[][] { { 3187, 3449 }, { 3163, 3447 }, { 3145, 3415 }, { 3149, 3380 }, { 3173, 3344 },
					{ 3210, 3358 }, { 3232, 3387 }, { 3230, 3438 }, { 3227, 3456 } });

	public static final Area WHOLE_ACTION_AREA = new Area(
			new int[][] { { 2942, 3519 }, { 3332, 3519 }, { 3335, 3328 }, { 3250, 3175 }, { 2934, 3294 } });

	private String pickaxe;

	private GrandExchangeHandlerWoodcutting grandExchangeActions;

	private static final Area GRAND_EXCHANGE_AREA = new Area(
			new int[][] { { 3159, 3492 }, { 3159, 3484 }, { 3170, 3485 }, { 3170, 3495 }, { 3159, 3494 } });

	private static final ArrayList<Position> FROM_GE_TO_BANK_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3165, 3485, 0), new Position(3165, 3475, 0), new Position(3165, 3465, 0),
					new Position(3164, 3460, 0), new Position(3173, 3455, 0), new Position(3182, 3450, 0),
					new Position(3184, 3449, 0), new Position(3183, 3439, 0), new Position(3182, 3435, 0)));

	private static final ArrayList<Position> FROM_BANK_TO_WC_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3182, 3435, 0), new Position(3182, 3428, 0), new Position(3173, 3427, 0),
					new Position(3168, 3418, 0), new Position(3165, 3412, 0)));

	private static final ArrayList<Position> FROM_GE_TO_WC_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3164, 3484, 0), new Position(3165, 3474, 0), new Position(3166, 3464, 0),
					new Position(3167, 3454, 0), new Position(3168, 3452, 0), new Position(3172, 3443, 0),
					new Position(3176, 3436, 0), new Position(3172, 3427, 0), new Position(3168, 3418, 0),
					new Position(3164, 3412, 0)));

	private PlayersAround around = new PlayersAround(this);

	public static final Area WC_AREA = new Area(
			new int[][] { { 3154, 3419 }, { 3158, 3413 }, { 3158, 3408 }, { 3153, 3395 }, { 3156, 3374 },
					{ 3166, 3373 }, { 3175, 3378 }, { 3175, 3390 }, { 3173, 3401 }, { 3173, 3428 }, { 3156, 3424 } });

	@Override
	public void onStart() {
		boolean loggedIn = false;

		while (!loggedIn) {
			loggedIn = getClient().isLoggedIn();
			log("Waiting on logged in");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();

			getCamera().movePitch(RandomUtil.getRandomNumberInRange(50, 67));
			// around.exchangeContext(getBot());
			// new Thread(around).start();
		}

		boolean oak = getSkills().getStatic(Skill.WOODCUTTING) >= 15;

		if (getEvent() != null && getEvent().hasFinished() && GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from g.e.", -1, -1, getBot().getMethods(),
							FROM_GE_TO_BANK_PATH, GRAND_EXCHANGE_AREA,
							new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
							getScript(), getEvent(), false, true));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from mining", -1, -1, getBot().getMethods(),
							WC_AREA_TO_BANK, WC_AREA, BANK_VARROCK_EAST_AREA, getScript(), getEvent(), false, true));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new BankTask("withdraw axe", 0, getBot().getMethods(), true,
						new BankItem[] { new BankItem("axe", 1, false) }, BANK_VARROCK_EAST_AREA, this));

		if (getEvent() != null && getEvent().hasFinished() && GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to woodcutting area (normal)", -1, -1, getBot().getMethods(),
							FROM_GE_TO_WC_POSITION, GRAND_EXCHANGE_AREA, WC_AREA, getScript(), getEvent(), false,
							true));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to woodcutting area", -1, -1, getBot().getMethods(), FROM_BANK_TO_WC_POSITION,
							BANK_VARROCK_EAST_AREA, WC_AREA, getScript(), getEvent(), false, true));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click tree", -1, -1, getBot().getMethods(), WC_AREA, oak ? 1751 : 1276,
						oak ? new BankItem("Oak logs", 1, false) : new BankItem("Logs", 1, false), true, this, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank 2", -1, -1, getBot().getMethods(), WC_AREA_TO_BANK, WC_AREA,
						BANK_VARROCK_EAST_AREA, getScript(), getEvent(), false, true));

	}

	public void onPaint(Graphics2D g) {
		g.drawString("WOODCUTTING", 60, 50);
		g.drawString("Runtime " + (formatTime((System.currentTimeMillis() - beginTime))), 60, 75);
		// g.setColor(Color.WHITE);
		// int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
		// long profitPerHour = (long) (profit * (3600000.0 /
		// (System.currentTimeMillis() - beginTime)));
		// g.drawString("Sold ores " + soldAmount, 60, 50);
		// g.drawString("Begin ores " + beginAmount, 60, 65);
		// g.drawString("Current ores " + currentAmount, 60, 80);
		// g.drawString("Total mined ores " + ((currentAmount - beginAmount) +
		// soldAmount), 60, 95);
		// g.drawString("Money per hour " + (profitPerHour > 0 ? profitPerHour : "Need
		// more data"), 60, 110);
		// g.drawString("Time taken " + (formatTime((System.currentTimeMillis() -
		// beginTime))), 60, 125);
		//
		// g.drawString((around.aroundMine < 0 ? "Calculating.."
		// : around.aroundMine + " / " + around.getPlayersAroundExceptMe()).toString(),
		// 60, 150);
	}

	@Override
	public void onLoop() throws InterruptedException, IOException {

		boolean oak = getSkills().getStatic(Skill.WOODCUTTING) >= 15;

		log("Running the side loop..");

		GenieLamp.openGenieLamp(this);

		if (!Config.NO_LOGIN) {
			if (getEvent() != null && getEvent().hasFinished() && !isLoggedIn()) {
				log("Not logged in.. restarting");
				BotCommands.waitBeforeKill((MethodProvider) this, "BECAUSE OF NOT LOGGED IN ATM E02");
			}
		}

		// Supporting world hopping when world is too full
		int threshholdToHop = 10_000;
		int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
		long profitPerHour = (long) (profit * (3600000.0 / (System.currentTimeMillis() - beginTime)));
		if ((profitPerHour > 0) && (profitPerHour < threshholdToHop)
				&& ((System.currentTimeMillis() - beginTime) > 1_800_000)
				|| ((System.currentTimeMillis() - beginTime) > 3_600_000)) {
			if (WorldHop.hop(this)) {
				beginTime = System.currentTimeMillis();
				beginAmount = currentAmount;
				soldAmount = 0;
			}
		}

		// if (WC_AREA.contains(myPlayer()) && ((!getInventory().contains(1349) &&
		// !getInventory().contains(1351)
		// && !getInventory().contains(1353) && !getInventory().contains(1355) &&
		// !getInventory().contains(1357)
		// && !getInventory().contains(1359)))) {
		// log("Is at wc area wihout an axe, restarting tasks 2!");
		// resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
		// }

		if (WC_AREA.contains(myPlayer()) && !getInventory().contains("Bronze axe")
				&& !getInventory().contains("Iron axe") && !getInventory().contains("Steel axe")
				&& !getInventory().contains("Mithril axe") && !getInventory().contains("Adamant axe")
				&& !getInventory().contains("Rune axe")) {
			log("Is at mining area wihout an axe, restarting tasks!");
			resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
		}

		if (getBank().isOpen() && Config.doesntHaveAnyAxe(this)) {
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", getEvent().getUsername(), getEvent());
			BotCommands.waitBeforeKill(this, "BECAUSE DOESNT HAVE AXE IN BANK OR INVENTORY, SETTING ACCOUNT TO BANNED");
		}

		// if (Config.doesntHaveAnyAxe(this) && !getBank().isOpen() &&
		// WC_AREA.contains(myPlayer())) {
		// log("Player doesn't have any pickaxe, getting it from bank");
		// resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
		// }

		if (!WC_AREA.contains(myPlayer())) {
			log("not in a zone!");
			resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
		}

		int logsAmount = (int) (oak ? getBank().getAmount("Oak logs") : getBank().getAmount("Logs"));
		int itemId = (int) (oak ? 1521 : 1511);

		// The tasks of getting new pickaxes, looking for the skill and then the
		// pickaxe
		// corresponding with this skill level, when the player is in lumrbidge, then
		// also execute this task to get a new mining pickaxe
		// When having more than 200 clay, then go to the g.e. and sell it
		if (getBank().isOpen() && logsAmount > 200) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		}

		int clayAmount = -1;
		int totalAccountValue = -1;
		if (getBank().isOpen()) {
			clayAmount = (int) logsAmount;
			totalAccountValue += (int) getBank().getAmount(995);
			totalAccountValue += (clayAmount * new GEPrice().getBuyingPrice(itemId));

			int bankedAmount = (int) logsAmount;
			if (beginAmount == -1) {
				beginAmount = bankedAmount;
			}
			if (bankedAmount < currentAmount) {
				soldAmount += (currentAmount - bankedAmount);
			}
			currentAmount = bankedAmount;

			int coinsAmount = (int) getBank().getAmount(995);

			// If has more than 100k then start tradinig it over to the mule
			if (coinsAmount > 44_000) {

				// Setting the status of the account that it wants to mule to another account
				// in
				// the database
				if (getEvent() != null && getEvent().getUsername() != null
						&& DatabaseUtilities.getMuleTradingFreeAccounts(this, getEvent()) < 3) {

					ConcreteWalking.walkToGe(this);

					DatabaseUtilities.updateStageProgress(this, "MULE_TRADING", 0, getEvent().getUsername(),
							getEvent());
					BotCommands.killProcess(this, getScript(), "BECAUSE WANTING TO GO TO MULE TRADING", getEvent());
				}
			}

		}
		log("[ESTIMATED] account value is: " + totalAccountValue);
		if (getEvent() != null && getEvent().getUsername() != null && totalAccountValue > 0) {
			DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), totalAccountValue, getEvent());
		}

		// This is made so when a player reaches a level, or doesn't have a base
		// pickaxe, then it goes to the g.e. and buys one according to its mining level
		if (totalAccountValue > Axe.BRONZE.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) <= 3
				&& ((!getInventory().contains("Bronze axe") && !getBank().contains("Bronze axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		} else if (totalAccountValue > Axe.IRON.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) > 3 && getSkills().getStatic(Skill.WOODCUTTING) < 6
				&& ((!getInventory().contains("Iron axe") && !getBank().contains("Iron axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		} else if (totalAccountValue > Axe.STEEL.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) >= 6 && getSkills().getStatic(Skill.WOODCUTTING) < 21
				&& ((!getInventory().contains("Steel axe") && !getBank().contains("Steel axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		} else if (totalAccountValue > Axe.MITHRIL.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) >= 21
				&& ((!getInventory().contains("Mithril axe") && !getBank().contains("Mithril axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		} else if (totalAccountValue > Axe.ADAMANT.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) >= 31 && getSkills().getStatic(Skill.WOODCUTTING) < 41
				&& ((!getInventory().contains("Adamant axe") && !getBank().contains("Adamant axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		} else if (totalAccountValue > Axe.RUNE.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.WOODCUTTING) >= 41
				&& ((!getInventory().contains("Rune axe") && !getBank().contains("Rune axe")))) {
			setGrandExchangeActions(new GrandExchangeHandlerWoodcutting(getEvent(), this));
		}

		log("g.e. running " + getGrandExchangeTask() + " "
				+ (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
						? getGrandExchangeTask().getTask().finished()
						: "null"));

		// Setting a G.E. task
		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() == null) {
			getGrandExchangeTask().exchangeContext(getBot());
			getGrandExchangeTask().setTask();
		}

		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
				&& getGrandExchangeTask().getTask().finished()) {
			// the current grand exchange task to null
			setGrandExchangeActions(null);
			resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
			log("Finished G.E. task, walking back to varrock bank");
		}

		// Looping through the grand exchange task
		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
				&& !getGrandExchangeTask().getTask().finished()) {
			getGrandExchangeTask().getTask().loop();

			System.out.println("RESET 01");
			resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());

		}

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
	 * @return the pickaxe
	 */
	public String getPickaxe() {
		return pickaxe;
	}

	/**
	 * @param pickaxe
	 *            the pickaxe to set
	 */
	public void setPickaxe(String pickaxe) {
		this.pickaxe = pickaxe;
	}

	/**
	 * @return the grandExchangeActions
	 */
	public GrandExchangeHandlerWoodcutting getGrandExchangeTask() {
		return grandExchangeActions;
	}

	/**
	 * @param grandExchangeActions
	 *            the grandExchangeActions to set
	 */
	public void setGrandExchangeActions(GrandExchangeHandlerWoodcutting grandExchangeActions) {
		this.grandExchangeActions = grandExchangeActions;
	}

	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// if (tasks.getTaskAttempts() > 5000) {
		// getScript().stop(false);
		// }
	}

}
