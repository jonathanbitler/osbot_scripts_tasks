package osbot_scripts.qp7.progress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Skill;
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
import osbot_scripts.qp7.progress.entities.Rock;
import osbot_scripts.sections.total.progress.MainState;

public class IronMinerConfiguration extends QuestStep {

	public IronMinerConfiguration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MINING_IRON_ORE, event, script, false);
		// TODO Auto-generated constructor stub
	}

	private static final ArrayList<Position> BANK_POSITION_VARROCK_EAST = new ArrayList<Position>(
			Arrays.asList(new Position(3184, 3436, 0)));

	private static final ArrayList<Position> BANK_PATH_TO_MINING_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3182, 3435, 0), new Position(3181, 3429, 0), new Position(3172, 3424, 0),
					new Position(3170, 3423, 0), new Position(3171, 3413, 0), new Position(3172, 3403, 0),
					new Position(3171, 3393, 0), new Position(3172, 3390, 0), new Position(3177, 3381, 0),
					new Position(3182, 3372, 0), new Position(3180, 3371, 0), new Position(3175, 3367, 0)));

	private static final ArrayList<Position> MINING_AREA_TO_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3371, 0), new Position(3178, 3380, 0), new Position(3173, 3389, 0),
					new Position(3168, 3398, 0), new Position(3168, 3399, 0), new Position(3169, 3409, 0),
					new Position(3170, 3419, 0), new Position(3172, 3428, 0), new Position(3182, 3431, 0),
					new Position(3183, 3431, 0), new Position(3184, 3436, 0)));

	private static final ArrayList<Position> MINING_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3175, 3367, 0)));

	private static final Area BANK_VARROCK_EAST_AREA = new Area(new int[][] { { 3180, 3447 }, { 3180, 3433 },
			{ 3186, 3433 }, { 3186, 3436 }, { 3189, 3436 }, { 3189, 3448 }, { 3180, 3448 } });

	private static final Area MINING_AREA = new Area(new int[][] { { 3179, 3379 }, { 3170, 3366 }, { 3175, 3363 },
			{ 3180, 3365 }, { 3184, 3373 }, { 3186, 3380 }, { 3182, 3381 } });

	private static final Area NOT_IN_CORRECT_ZONE = new Area(
			new int[][] { { 3187, 3449 }, { 3163, 3447 }, { 3145, 3415 }, { 3149, 3380 }, { 3173, 3344 },
					{ 3210, 3358 }, { 3232, 3387 }, { 3230, 3438 }, { 3227, 3456 } });

	private String pickaxe;



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
		g.drawString("Money per hour " + (profitPerHour > 0 ? profitPerHour : "Need more data"), 60,
				110);
		g.drawString("Time taken " + (formatTime((System.currentTimeMillis() - beginTime))), 60, 125);
	}
	@Override
	public void onStart() {
		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();
		}
		
		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank", -1, -1, getBot().getMethods(), BANK_POSITION_VARROCK_EAST,
						new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
						getScript(), getEvent(), true, false));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new BankTask("withdraw pickaxe", 0,
				getBot().getMethods(), true, new BankItem[] { new BankItem("pickaxe", 1, false) },
				new Area(
						new int[][] { { 3180, 3439 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3440 }, { 3180, 3440 } }),
				this));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to mining area", -1, -1, getBot().getMethods(), BANK_PATH_TO_MINING_AREA,
						BANK_VARROCK_EAST_AREA, MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to mining spot", -1, -1,
				getBot().getMethods(), MINING_POSITION, MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click mining", -1, -1, getBot().getMethods(),
						new Area(new int[][] { { 3174, 3370 }, { 3180, 3370 }, { 3180, 3365 }, { 3174, 3365 } }), 7488,
						new BankItem("Iron ore", 1, false), true, this, Rock.IRON));

		// String name, int itemId, int amount, int sellPrice, boolean withdrawNoted

		// getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new
		// DropItemTask("drop all clay", -1, -1,
		// getBot().getMethods(), "Drop", true, new String[] { "Clay" }));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank 2", -1, -1, getBot().getMethods(), MINING_AREA_TO_BANK,
						BANK_VARROCK_EAST_AREA, getScript(), getEvent(), false, true));

	}

	private GrandExchangeTask grandExchangeTask = null;

	@Override
	public void onLoop() throws InterruptedException {
		log("Running the side loop..");

		// At the end of the loop, restarting the loop
		// if (BANK_VARROCK_EAST_AREA.contains(myPlayer())
		// && (getQuestStageStep() >= (getTaskHandler().getTasks().size() - 1))) {
		// log("Restarting tasks to 0 for another loop!");
		// resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		// }

		if (getEvent().hasFinished() && !getClient().isLoggedIn()) {
			System.exit(1);
		}

		// If the player is fighting or under combat, then reset the stage to prevent
		// going dead
		if (getCombat().isFighting() || myPlayer().isUnderAttack()) {
			log("Under attack! Resetting stage for now and going to bank");
			getWalking().walkPath(
					new ArrayList<Position>(Arrays.asList(new Position(3182, 3371, 0), new Position(3181, 3365, 0),
							new Position(3173, 3361, 0), new Position(3169, 3368, 0), new Position(3173, 3377, 0),
							new Position(3183, 3379, 0), new Position(3185, 3379, 0), new Position(3181, 3371, 0))));
			resetStage(AccountStage.MINING_IRON_ORE.name());
		}

		if (MINING_AREA.contains(myPlayer()) && !getInventory().contains("Bronze pickaxe")
				&& !getInventory().contains("Iron pickaxe") && !getInventory().contains("Steel pickaxe")
				&& !getInventory().contains("Mithril pickaxe") && !getInventory().contains("Adamant pickaxe")
				&& !getInventory().contains("Rune pickaxe")) {
			log("Is at mining area wihout a pickaxe, restarting tasks!");
			resetStage(AccountStage.MINING_IRON_ORE.name());
		}

		if (!MiningLevelTo15Configuration.MINING_ZONE.contains(myPlayer())) {
			resetStage(AccountStage.MINING_IRON_ORE.name());
		}

		// When having more than 200 clay, then go to the g.e. and sell it
		if (getBank().isOpen() && getBank().getAmount("Iron ore") > 200) {
			int amount = (int) (getBank().getAmount("Iron ore"));
			setGrandExchangeTask(new GrandExchangeTask(this, new BankItem[] {},
					new BankItem[] { new BankItem("Iron ore", 440, amount, 1, true) }, null, getScript()));
		}

		int ironAmount = -1;
		int totalAccountValue = -1;
		if (getBank().isOpen()) {
			ironAmount = (int) getBank().getAmount("Iron ore");
			int coinsAmount = (int) getBank().getAmount(995);
			totalAccountValue += coinsAmount;

			// If has more than 100k then start tradinig it over to the mule
			if (coinsAmount > 100_000) {

				// Setting the status of the account that it wants to mule to another account in
				// the database
				if (getEvent() != null && getEvent().getUsername() != null) {
					DatabaseUtilities.updateStageProgress(this, "MULE_TRADING", 0, getEvent().getUsername());
					BotCommands.killProcess(this, getScript());
				}
			}

			totalAccountValue += (ironAmount * 120);
			
			int bankedAmount = (int) getBank().getAmount("Iron ore");
			if (beginAmount == -1) {
				beginAmount = bankedAmount;
			}
			if (bankedAmount < currentAmount) {
				soldAmount += (currentAmount - bankedAmount);
			}
			currentAmount = bankedAmount;
			// beginAmount,soldAmount, currentAmount;
		}
		log("[ESTIMATED] account value is: " + totalAccountValue);
		if (getEvent() != null && getEvent().getUsername() != null && totalAccountValue > 0) {
			DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), totalAccountValue);
		}

		// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
		// corresponding with this skill level, when the player is in lumrbidge, then
		// also execute this task to get a new mining pickaxe
		// This is made so when a player reaches a level, or doesn't have a base
		// pickaxe, then it goes to the g.e. and buys one according to its mining level
		if (totalAccountValue > 3000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) <= 3
				&& ((!getInventory().contains("Bronze pickaxe") && !getBank().contains("Bronze pickaxe")))) {

			setGrandExchangeTask(
					new GrandExchangeTask(this, new BankItem[] { new BankItem("Bronze pickaxe", 1265, 1, 1400, false) },
							new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
									new BankItem("Clay", 434, 1000, 1, true) },
							null, getScript()));
		} else if (totalAccountValue > 5000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) > 3
				&& getSkills().getStatic(Skill.MINING) < 6
				&& ((!getInventory().contains("Iron pickaxe") && !getBank().contains("Iron pickaxe")))) {

			setGrandExchangeTask(
					new GrandExchangeTask(this, new BankItem[] { new BankItem("Iron pickaxe", 1267, 1, 1400, false) },
							new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
									new BankItem("Clay", 434, 1000, 1, true) },
							null, getScript()));
		} else if (totalAccountValue > 8000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 6
				&& getSkills().getStatic(Skill.MINING) < 21
				&& ((!getInventory().contains("Steel pickaxe") && !getBank().contains("Steel pickaxe")))) {

			setGrandExchangeTask(
					new GrandExchangeTask(this, new BankItem[] { new BankItem("Steel pickaxe", 1269, 1, 5000, false) },
							new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
									new BankItem("Clay", 434, 1000, 1, true) },
							null, getScript()));
		} else if (totalAccountValue > 15000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 21
				&& getSkills().getStatic(Skill.MINING) < 31
				&& ((!getInventory().contains("Mithril pickaxe") && !getBank().contains("Mithril pickaxe")))) {

			setGrandExchangeTask(new GrandExchangeTask(this,
					new BankItem[] { new BankItem("Mithril pickaxe", 1273, 1, 10000, false) },
					new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
							new BankItem("Clay", 434, 1000, 1, true) },
					null, getScript()));
		} else if (totalAccountValue > 30000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 31
				&& getSkills().getStatic(Skill.MINING) < 41
				&& ((!getInventory().contains("Adamant pickaxe") && !getBank().contains("Adamant pickaxe")))) {

			setGrandExchangeTask(new GrandExchangeTask(this,
					new BankItem[] { new BankItem("Adamant pickaxe", 1271, 1, 20000, false) },
					new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
							new BankItem("Clay", 434, 1000, 1, true) },
					null, getScript()));
		} else if (totalAccountValue > 100000 && getBank().isOpen() && getSkills().getStatic(Skill.MINING) >= 41
				&& ((!getInventory().contains("Rune pickaxe") && !getBank().contains("Rune pickaxe")))) {

			setGrandExchangeTask(
					new GrandExchangeTask(this, new BankItem[] { new BankItem("Rune pickaxe", 1275, 1, 60000, false) },
							new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
									new BankItem("Clay", 434, 1000, 1, true) },
							null, getScript()));
		}

		log("g.e. running " + getGrandExchangeTask() + " "
				+ (getGrandExchangeTask() != null ? getGrandExchangeTask().finished() : "null"));
		if (getGrandExchangeTask() != null && getGrandExchangeTask().finished()) {

			// Resetting stage, walking back to the bank to deposit everything and setting
			// the current grand exchange task to null
			resetStage(AccountStage.MINING_IRON_ORE.name());
			setGrandExchangeTask(null);
			log("Finished G.E. task, walking back to varrock bank");
		}
		// Looping through the grand exchange task
		if (getGrandExchangeTask() != null && !getGrandExchangeTask().finished()) {
			getGrandExchangeTask().loop();

			if (getQuestStageStep() != 0) {
				resetStage(AccountStage.MINING_IRON_ORE.name());
			}

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
	 * @return the grandExchangeTask
	 */
	public GrandExchangeTask getGrandExchangeTask() {
		return grandExchangeTask;
	}

	/**
	 * @param grandExchangeTask
	 *            the grandExchangeTask to set
	 */
	public void setGrandExchangeTask(GrandExchangeTask grandExchangeTask) {
		this.grandExchangeTask = grandExchangeTask;
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

}
