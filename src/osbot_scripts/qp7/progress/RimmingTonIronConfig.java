package osbot_scripts.qp7.progress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.BankTask;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.qp7.progress.entities.Rock;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class RimmingTonIronConfig extends QuestStep {

	public RimmingTonIronConfig(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.RIMMINGTON_IRON_ORE, event, script, false);
		// TODO Auto-generated constructor stub
	}

	private static final ArrayList<Position> BANK_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3013, 3356, 0)));

	private static final ArrayList<Position> BANK_PATH_TO_MINING_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3013, 3356, 0), new Position(3006, 3353, 0), new Position(3006, 3343, 0),
					new Position(3006, 3333, 0), new Position(3006, 3323, 0), new Position(3006, 3313, 0),
					new Position(3006, 3303, 0), new Position(3006, 3293, 0), new Position(3006, 3283, 0),
					new Position(3004, 3277, 0), new Position(2997, 3270, 0), new Position(2990, 3263, 0),
					new Position(2983, 3256, 0), new Position(2976, 3249, 0), new Position(2974, 3245, 0),
					new Position(2969, 3241, 0)));

	private static final ArrayList<Position> MINING_AREA_TO_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(2975, 3246, 0), new Position(2982, 3253, 0), new Position(2989, 3260, 0),
					new Position(2996, 3267, 0), new Position(3003, 3274, 0), new Position(3007, 3278, 0),
					new Position(3007, 3288, 0), new Position(3007, 3298, 0), new Position(3007, 3308, 0),
					new Position(3007, 3318, 0), new Position(3007, 3328, 0), new Position(3007, 3338, 0),
					new Position(3007, 3348, 0), new Position(3005, 3356, 0), new Position(3013, 3356, 0)));

	private static final ArrayList<Position> MINING_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(2969, 3241, 0)));

	private static final Area BANK_VARROCK_EAST_AREA = new Area(new int[][] { { 3180, 3447 }, { 3180, 3433 },
			{ 3186, 3433 }, { 3186, 3436 }, { 3189, 3436 }, { 3189, 3448 }, { 3180, 3448 } });

	private static final Area FALADOR_BANK_AREA = new Area(
			new int[][] { { 3007, 3360 }, { 3007, 3352 }, { 3024, 3352 }, { 3024, 3361 }, { 3007, 3361 } });

	private static final Area IN_CORRECT_ZONE = new Area(
			new int[][] { { 3007, 3366 }, { 2985, 3343 }, { 2989, 3282 }, { 2955, 3249 }, { 2971, 3215 },
					{ 2997, 3227 }, { 2999, 3260 }, { 3014, 3266 }, { 3023, 3304 }, { 3023, 3363 } });

	private static final Area RIMMINGTON_MINING_AREA = new Area(
			new int[][] { { 2966, 3240 }, { 2968, 3236 }, { 2973, 3240 }, { 2971, 3245 }, { 2967, 3243 } });

	private String pickaxe;

	@Override
	public void onStart() {
		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to falador bank", -1,
					-1, getBot().getMethods(), BANK_POSITION, FALADOR_BANK_AREA, getScript(), getEvent(), true, false));//
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new BankTask("withdraw pickaxe", 0, getBot().getMethods(), true,
						new BankItem[] { new BankItem("pickaxe", 1, false) }, FALADOR_BANK_AREA, this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to mining area", -1, -1, getBot().getMethods(), BANK_PATH_TO_MINING_AREA,
						FALADOR_BANK_AREA, RIMMINGTON_MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to mining spot", -1, -1,
				getBot().getMethods(), MINING_POSITION, RIMMINGTON_MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click mining", -1, -1, getBot().getMethods(), RIMMINGTON_MINING_AREA, 7488,
						new BankItem("Iron ore", 1, false), true, this, Rock.IRON));

		// String name, int itemId, int amount, int sellPrice, boolean withdrawNoted

		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// DropItemTask("drop all clay", -1, -1,
		// getBot().getMethods(), "Drop", true, new String[] { "Clay" }));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to falador bank 2", -1, -1, getBot().getMethods(), MINING_AREA_TO_BANK,
						FALADOR_BANK_AREA, getScript(), getEvent(), false, true));

	}

	private GrandExchangeTask grandExchangeTask = null;

	/**
	 * 
	 * @param g
	 */
	public void onPaint(Graphics2D g) {
		g.setColor(Color.WHITE);
		int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
		long profitPerHour = (long) (profit * (3600000.0 / (System.currentTimeMillis() - beginTime)));
		g.drawString("Sold ores " + soldAmount, 60, 50);
		g.drawString("Begin ores " + beginAmount, 60, 65);
		g.drawString("Current ores " + currentAmount, 60, 80);
		g.drawString("Total mined ores " + ((currentAmount - beginAmount) + soldAmount), 60, 95);
		g.drawString("Money per hour " + (profitPerHour > 0 ? profitPerHour : "Need more data"), 60, 110);
		g.drawString("Time taken " + (formatTime((System.currentTimeMillis() - beginTime))), 60, 125);
	}

	@Override
	public void onLoop() throws InterruptedException {
		log("Running the side loop..");

		// At the end of the loop, restarting the loop
		// if (BANK_VARROCK_EAST_AREA.contains(myPlayer())
		// && (getQuestStageStep() >= (getTaskHandler().getTasks().size() - 1))) {
		// log("Restarting tasks to 0 for another loop!");
		// resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		// }

		if (getEvent().hasFinished() && !isLoggedIn()) {
			log("Not logged in.. restarting");
			BotCommands.waitBeforeKill((MethodProvider) this, "BECAUSE OF NOT LOGGED IN E04");
		}

		// If the player is fighting or under combat, then reset the stage to prevent
		// going dead
		if (getCombat().isFighting() || myPlayer().isUnderAttack()) {
			log("Under attack! Resetting stage for now and going to bank");
			resetStage(AccountStage.RIMMINGTON_IRON_ORE.name());
		}

		// Supporting world hopping when world is too full
//		int threshholdToHop = 10_000;
//		int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
//		long profitPerHour = (long) (profit * (3600000.0 / (System.currentTimeMillis() - beginTime)));
//		if ((profitPerHour > 0) && (profitPerHour < threshholdToHop)
//				&& ((System.currentTimeMillis() - beginTime) > 1_800_000)
//				|| ((System.currentTimeMillis() - beginTime) > 3_600_000)) {
//			if (WorldHop.hop(this)) {
//				beginTime = System.currentTimeMillis();
//				beginAmount = currentAmount;
//				soldAmount = 0;
//			}
//		}

		if (RIMMINGTON_MINING_AREA.contains(myPlayer()) && !getInventory().contains("Bronze pickaxe")
				&& !getInventory().contains("Iron pickaxe") && !getInventory().contains("Steel pickaxe")
				&& !getInventory().contains("Mithril pickaxe") && !getInventory().contains("Adamant pickaxe")
				&& !getInventory().contains("Rune pickaxe")) {
			log("Is at mining area wihout a pickaxe, restarting tasks!");
			resetStage(AccountStage.RIMMINGTON_IRON_ORE.name());
		}

		if (!IN_CORRECT_ZONE.contains(myPlayer())) {
			resetStage(AccountStage.RIMMINGTON_IRON_ORE.name());
		}

		// When having more than 200 iron ore, then go to the g.e. and sell it
		if (getBank().isOpen() && getBank().getAmount("Iron ore") > 200) {
			DatabaseUtilities.updateStageProgress((MethodProvider) this, AccountStage.GE_SELL_BUY_MINING.name(), 0,
					getEvent().getUsername(), getEvent());
			BotCommands.killProcess((MethodProvider) this, getScript(), "BECAUSE OF SELLING RIGHT NOW TO GE",
					getEvent());
			return;
			// int amount = (int) (getBank().getAmount("Iron ore"));
			// int inventory = (int) (getInventory().getAmount("Iron ore"));
			// setGrandExchangeTask(new GrandExchangeTask(this, new BankItem[] {},
			// new BankItem[] { new BankItem("Iron ore", 440, amount + inventory, 1, true),
			// new BankItem("Uncut diamond", 1617, 1000, 1, true),
			// new BankItem("Uncut emerald", 1621, 1000, 1, true),
			// new BankItem("Uncut ruby", 1619, 1000, 1, true),
			// new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
			// null, getScript()));
		}

		int ironAmount = -1;
		int totalAccountValue = -1;
		if (getBank().isOpen()) {
			ironAmount = (int) getBank().getAmount("Iron ore");
			int coinsAmount = (int) getBank().getAmount(995);
			totalAccountValue += coinsAmount;

			// If has more than 100k then start tradinig it over to the mule
			if (coinsAmount > 50_000) {

				// Setting the status of the account that it wants to mule to another account in
				// the database
				if (getEvent() != null && getEvent().getUsername() != null) {
					DatabaseUtilities.updateStageProgress(this, "MULE_TRADING", 0, getEvent().getUsername(),
							getEvent());
					BotCommands.killProcess(this, getScript(), "MULE TRADING", getEvent());
				}
			}

			// Adds to the total value account
			totalAccountValue += (ironAmount * 120);

			int bankedAmount = (int) getBank().getAmount("Iron ore");
			if (beginAmount == -1) {
				beginAmount = bankedAmount - ironAmount;
			}
			if (bankedAmount < currentAmount) {
				soldAmount += (currentAmount - bankedAmount);
			}
			currentAmount = bankedAmount;
		}
		log("[ESTIMATED] account value is: " + totalAccountValue);
		if (getEvent() != null && getEvent().getUsername() != null && totalAccountValue > 0) {
			DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), totalAccountValue, getEvent());
		}

		// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
		// corresponding with this skill level, when the player is in lumrbidge, then
		// also execute this task to get a new mining pickaxe
		// This is made so when a player reaches a level, or doesn't have a base
		// pickaxe, then it goes to the g.e. and buys one according to its mining level
		if (totalAccountValue > 3000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) <= 3
				&& ((!getInventory().contains("Bronze pickaxe") && !getBank().contains("Bronze pickaxe")))) {

			DatabaseUtilities.updateStageProgress((MethodProvider) this, AccountStage.GE_SELL_BUY_MINING.name(), 0,
					getEvent().getUsername(), getEvent());
			BotCommands.killProcess((MethodProvider) this, getScript(), "BECAUSE OF GOING TO THE G.E.", getEvent());
			return;
			// setGrandExchangeTask(
			// new GrandExchangeTask(this, new BankItem[] { new BankItem("Bronze pickaxe",
			// 1265, 1, 1400, false) },
			// new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
			// new BankItem("Clay", 434, 1000, 1, true),
			// new BankItem("Uncut diamond", 1617, 1000, 1, true),
			// new BankItem("Uncut emerald", 1621, 1000, 1, true),
			// new BankItem("Uncut ruby", 1619, 1000, 1, true),
			// new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
			// null, getScript()));
		}
		// else if (totalAccountValue > 5000 && getBank().isOpen() &&
		// getSkills().getStatic(Skill.MINING) > 3
		// && getSkills().getStatic(Skill.MINING) < 6
		// && ((!getInventory().contains("Iron pickaxe") && !getBank().contains("Iron
		// pickaxe")))) {
		//
		// setGrandExchangeTask(
		// new GrandExchangeTask(this, new BankItem[] { new BankItem("Iron pickaxe",
		// 1267, 1, 1400, false) },
		// new BankItem[] { new BankItem("Iron ore", 440, 1000, 100, true),
		// new BankItem("Clay", 434, 1000, 100, true) },
		// null, getScript()));
		// }
		else if (totalAccountValue > 8000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 6
				&& getSkills().getStatic(Skill.MINING) < 21
				&& ((!getInventory().contains("Steel pickaxe") && !getBank().contains("Steel pickaxe")))) {

			DatabaseUtilities.updateStageProgress((MethodProvider) this, AccountStage.GE_SELL_BUY_MINING.name(), 0,
					getEvent().getUsername(), getEvent());
			BotCommands.killProcess((MethodProvider) this, getScript(), "BECAUSE OF GOING TO THE G.E. 2", getEvent());
			return;

			// setGrandExchangeTask(
			// new GrandExchangeTask(this, new BankItem[] { new BankItem("Steel pickaxe",
			// 1269, 1, 5000, false) },
			// new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
			// new BankItem("Clay", 434, 1000, 1, true),
			// new BankItem("Uncut diamond", 1617, 1000, 1, true),
			// new BankItem("Uncut emerald", 1621, 1000, 1, true),
			// new BankItem("Uncut ruby", 1619, 1000, 1, true),
			// new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
			// null, getScript()));
		}

		// else if (totalAccountValue > 15000 && getBank().isOpen() &&
		// getSkills().getStatic(Skill.MINING) >= 21
		// && getSkills().getStatic(Skill.MINING) < 31
		// && ((!getInventory().contains("Mithril pickaxe") &&
		// !getBank().contains("Mithril pickaxe")))) {
		//
		// setGrandExchangeTask(new GrandExchangeTask(this,
		// new BankItem[] { new BankItem("Mithril pickaxe", 1273, 1, 10000, false) },
		// new BankItem[] { new BankItem("Iron ore", 440, 1000, 100, true),
		// new BankItem("Clay", 434, 1000, 100, true) },
		// null, getScript()));
		// }

		else if (totalAccountValue > 30000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 31
				&& getSkills().getStatic(Skill.MINING) < 41
				&& ((!getInventory().contains("Adamant pickaxe") && !getBank().contains("Adamant pickaxe")))) {

			DatabaseUtilities.updateStageProgress((MethodProvider) this, AccountStage.GE_SELL_BUY_MINING.name(), 0,
					getEvent().getUsername(), getEvent());
			BotCommands.killProcess((MethodProvider) this, getScript(), "BECAUSE OF GOING TO THE G.E. 3", getEvent());
			return;

			// setGrandExchangeTask(new GrandExchangeTask(this,
			// new BankItem[] { new BankItem("Adamant pickaxe", 1271, 1, 20000, false) },
			// new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
			// new BankItem("Clay", 434, 1000, 1, true),
			// new BankItem("Uncut diamond", 1617, 1000, 1, true),
			// new BankItem("Uncut emerald", 1621, 1000, 1, true),
			// new BankItem("Uncut ruby", 1619, 1000, 1, true),
			// new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
			// null, getScript()));
		} else if (totalAccountValue > 45000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 41
				&& ((!getInventory().contains("Rune pickaxe") && !getBank().contains("Rune pickaxe")))) {

			DatabaseUtilities.updateStageProgress((MethodProvider) this, AccountStage.GE_SELL_BUY_MINING.name(), 0,
					getEvent().getUsername(), getEvent());
			BotCommands.killProcess((MethodProvider) this, getScript(), "BECAUSE OF GOING TO THE G.E. 4", getEvent());
			return;
			// setGrandExchangeTask(
			// new GrandExchangeTask(this, new BankItem[] { new BankItem("Rune pickaxe",
			// 1275, 1, 60000, false) },
			// new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
			// new BankItem("Clay", 434, 1000, 1, true),
			// new BankItem("Uncut diamond", 1617, 1000, 1, true),
			// new BankItem("Uncut emerald", 1621, 1000, 1, true),
			// new BankItem("Uncut ruby", 1619, 1000, 1, true),
			// new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
			// null, getScript()));
		}

		// log("g.e. running " + getGrandExchangeTask() + " "
		// + (getGrandExchangeTask() != null ? getGrandExchangeTask().finished() :
		// "null"));
		// if (getGrandExchangeTask() != null && getGrandExchangeTask().finished()) {
		//
		// // Resetting stage, walking back to the bank to deposit everything and
		// setting
		// // the current grand exchange task to null
		// resetStage(AccountStage.RIMMINGTON_IRON_ORE.name());
		// setGrandExchangeTask(null);
		// log("Finished G.E. task, walking back to varrock bank");
		// }
		// Looping through the grand exchange task
		// if (getGrandExchangeTask() != null && !getGrandExchangeTask().finished()) {
		// getGrandExchangeTask().loop();
		//
		// if (getQuestStageStep() != 0) {
		// resetStage(AccountStage.RIMMINGTON_IRON_ORE.name());
		// }
		//
		// }
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
	 * @return the grandExchangeTask
	 */
	// public GrandExchangeTask getGrandExchangeTask() {
	// return grandExchangeTask;
	// }
	//
	// /**
	// * @param grandExchangeTask
	// * the grandExchangeTask to set
	// */
	// public void setGrandExchangeTask(GrandExchangeTask grandExchangeTask) {
	// this.grandExchangeTask = grandExchangeTask;
	// }

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
	
	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub
		
	}

}
