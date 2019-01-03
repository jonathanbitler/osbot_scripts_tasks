package osbot_scripts.qp7.progress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.BankTask;
import osbot_scripts.framework.ClickObjectTask;
import osbot_scripts.framework.ConcreteWalking;
import osbot_scripts.framework.GenieLamp;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.Pickaxe;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.qp7.progress.entities.Rock;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

public class MiningLevelTo15Configuration extends QuestStep {

	public MiningLevelTo15Configuration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MINING_LEVEL_TO_15, event, script, false);
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

	private static final ArrayList<Position> MINING_AREA_TO_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3371, 0), new Position(3178, 3380, 0), new Position(3173, 3389, 0),
					new Position(3168, 3398, 0), new Position(3168, 3399, 0), new Position(3169, 3409, 0),
					new Position(3170, 3419, 0), new Position(3172, 3428, 0), new Position(3182, 3431, 0),
					new Position(3183, 3431, 0), new Position(3184, 3436, 0)));

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

	private Ge2 grandExchangeActions;

	private static final Area GRAND_EXCHANGE_AREA = new Area(
			new int[][] { { 3159, 3492 }, { 3159, 3484 }, { 3170, 3485 }, { 3170, 3495 }, { 3159, 3494 } });

	private static final ArrayList<Position> FROM_GE_TO_BANK_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3165, 3485, 0), new Position(3165, 3475, 0), new Position(3165, 3465, 0),
					new Position(3164, 3460, 0), new Position(3173, 3455, 0), new Position(3182, 3450, 0),
					new Position(3184, 3449, 0), new Position(3183, 3439, 0), new Position(3182, 3435, 0)));

	private static final ArrayList<Position> FROM_GE_TO_MINING_POSITION = new ArrayList<Position>(
			Arrays.asList(new Position(3164, 3485, 0), new Position(3164, 3475, 0), new Position(3164, 3465, 0),
					new Position(3164, 3455, 0), new Position(3165, 3451, 0), new Position(3174, 3447, 0),
					new Position(3175, 3446, 0), new Position(3173, 3436, 0), new Position(3171, 3426, 0),
					new Position(3169, 3416, 0), new Position(3167, 3413, 0), new Position(3169, 3403, 0),
					new Position(3171, 3393, 0), new Position(3173, 3383, 0), new Position(3173, 3383, 0),
					new Position(3182, 3378, 0), new Position(3187, 3375, 0), new Position(3179, 3369, 0),
					new Position(3177, 3367, 0)));

	private PlayersAround around = new PlayersAround(this);

	@Override
	public void onStart() {
		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();

			around.exchangeContext(getBot());
			new Thread(around).start();
		}

		if (getEvent() != null && getEvent().hasFinished() && GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from g.e.", -1, -1, getBot().getMethods(),
							FROM_GE_TO_BANK_PATH, GRAND_EXCHANGE_AREA,
							new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
							getScript(), getEvent(), false, true));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from mining", -1, -1, getBot().getMethods(),
							MINING_AREA_TO_BANK,
							new Area(new int[][] { { 3174, 3379 }, { 3186, 3379 }, { 3185, 3366 }, { 3176, 3362 },
									{ 3169, 3364 }, { 3171, 3372 } }),
							new Area(new int[][] { { 3180, 3441 }, { 3186, 3441 }, { 3186, 3433 }, { 3180, 3433 } }),
							getScript(), getEvent(), false, true));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new BankTask("withdraw pickaxe", 0,
				getBot().getMethods(), true, new BankItem[] { new BankItem("pickaxe", 1, false) },
				new Area(
						new int[][] { { 3180, 3439 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3440 }, { 3180, 3440 } }),
				this));

		if (getEvent().hasFinished() && GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to mining area (normal)", -1, -1, getBot().getMethods(),
							FROM_GE_TO_MINING_POSITION, GRAND_EXCHANGE_AREA, MINING_AREA, getScript(), getEvent(),
							false, true));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to mining area", -1, -1, getBot().getMethods(), BANK_PATH_TO_MINING_AREA,
							BANK_VARROCK_EAST_AREA, MINING_AREA, getScript(), getEvent(), false, true));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new WalkTask("walk to mining spot", -1, -1,
				getBot().getMethods(), MINING_POSITION, MINING_AREA, getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click mining", -1, -1, getBot().getMethods(),
						new Area(new int[][] { { 3184, 3374 }, { 3179, 3374 }, { 3179, 3369 }, { 3184, 3369 } }), 7454,
						new BankItem("Clay", 1, false), true, this, Rock.CLAY));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank 2", -1, -1, getBot().getMethods(), MINING_AREA_TO_BANK,
						MINING_AREA, BANK_VARROCK_EAST_AREA, getScript(), getEvent(), false, true));

	}

	public void onPaint(Graphics2D g) {
		g.drawString("MINING_CLAY", 60, 50);
		g.drawString("Runtime " + (formatTime((System.currentTimeMillis() - beginTime))), 60, 75);
//		g.setColor(Color.WHITE);
//		int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
//		long profitPerHour = (long) (profit * (3600000.0 / (System.currentTimeMillis() - beginTime)));
//		g.drawString("Sold ores " + soldAmount, 60, 50);
//		g.drawString("Begin ores " + beginAmount, 60, 65);
//		g.drawString("Current ores " + currentAmount, 60, 80);
//		g.drawString("Total mined ores " + ((currentAmount - beginAmount) + soldAmount), 60, 95);
//		g.drawString("Money per hour " + (profitPerHour > 0 ? profitPerHour : "Need more data"), 60, 110);
//		g.drawString("Time taken " + (formatTime((System.currentTimeMillis() - beginTime))), 60, 125);
//
//		g.drawString((around.aroundMine < 0 ? "Calculating.."
//				: around.aroundMine + " / " + around.getPlayersAroundExceptMe()).toString(), 60, 150);
	}

	@Override
	public void onLoop() throws InterruptedException, IOException {
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

		// If the player is fighting or under combat, then reset the stage to prevent
		// going dead
		if (getCombat().isFighting() || myPlayer().isUnderAttack()) {
			log("Under attack! Resetting stage for now! Going a round to stuck mugger");
			MuggerStuck.runCopperMine(getScript(), (MethodProvider) this);

			Sleep.sleepUntil(() -> !getCombat().isFighting() && !myPlayer().isUnderAttack(), 5000);
		}

		if (MINING_AREA.contains(myPlayer()) && ((getInventory().contains(1266) || getInventory().contains(1268)
				|| getInventory().contains(1270) || getInventory().contains(1272) || getInventory().contains(1274)
				|| getInventory().contains(1276)))) {
			log("Is at mining area wihout a pickaxe, restarting tasks 2!");
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		}

		if (MINING_AREA.contains(myPlayer()) && !getInventory().contains("Bronze pickaxe")
				&& !getInventory().contains("Iron pickaxe") && !getInventory().contains("Steel pickaxe")
				&& !getInventory().contains("Mithril pickaxe") && !getInventory().contains("Adamant pickaxe")
				&& !getInventory().contains("Rune pickaxe")) {
			log("Is at mining area wihout a pickaxe, restarting tasks!");
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		}

		if (Config.doesntHaveAnyPickaxe(this) && !getBank().isOpen() && MINING_AREA.contains(myPlayer())) {
			log("Player doesn't have any pickaxe, getting it from bank");
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		}

		if (!MINING_ZONE.contains(myPlayer())) {
			log("not in mining zone!");
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());
		}

		// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
		// corresponding with this skill level, when the player is in lumrbidge, then
		// also execute this task to get a new mining pickaxe
		// When having more than 200 clay, then go to the g.e. and sell it
		if (getBank().isOpen() && getBank().getAmount("Clay") > 200) {
			setGrandExchangeActions(new Ge2(getEvent(), this));
		}

		if (Config.doesntHaveAnyPickaxe(this) && getBank().isOpen()) {
			log("Player doesn't have any pickaxe, buying one right now");

			int costInBankAndInventory = (int) (getBank().getAmount(995) + getInventory().getAmount(995));
			int itemCost = getGrandexchangePriceForItem(1265, true);

			// *1.25 should prevent not insta buyable
			if ((getBank().isOpen()) && (costInBankAndInventory < (itemCost * 1.25))) {

				Area bronzePickaxeLocation = new Area(new int[][] { { 3228, 3226 }, { 3227, 3225 }, { 3227, 3220 },
						{ 3231, 3220 }, { 3231, 3225 }, { 3230, 3226 }, { 3228, 3226 } }).setPlane(2);

				// Not in location of bronze pickaxe
				while (!bronzePickaxeLocation.contains(myPlayer()) && (!getInventory().contains("Bronze pickaxe"))) {
					getWalking().webWalk(bronzePickaxeLocation);

					GroundItem item = getGroundItems().closest("Bronze pickaxe");
					if (item != null) {
						item.interact();
					}
					log("Going to pick up bronze pickaxe!");

					if (getInventory().contains("Bronze pickaxe")) {
						log("Got bronze pickaxe, walking to position!");
					}

				}

				// In location of bronze pickaxea and got the pickaxe, walk back
				while (bronzePickaxeLocation.contains(myPlayer()) && (getInventory().contains("Bronze pickaxe"))) {
					getWalking().webWalk(DoricsQuestConfig.MINING_AREA);
					getWalking().walkPath(DoricsQuestConfig.MINING_POSITION);
				}

			} else {

				Ge2 ge = new Ge2(getEvent(), this);

				GrandExchangeTask task = new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Bronze pickaxe", 1265, 1, Pickaxe.BRONZE.getPrice(), false) },
						new BankItem[] {
								// new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true) },
						getEvent(), getScript(), this);

				ge.setTask(task);
				setGrandExchangeActions(ge);
			}
		}

		int clayAmount = -1;
		int totalAccountValue = -1;
		if (getBank().isOpen()) {
			clayAmount = (int) getBank().getAmount("Clay");
			totalAccountValue += (int) getBank().getAmount(995);
			totalAccountValue += (clayAmount * 90);

			int bankedAmount = (int) getBank().getAmount("Clay");
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

				// Setting the status of the account that it wants to mule to another account in
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
		if (totalAccountValue > Pickaxe.BRONZE.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) <= 3
				&& ((!getInventory().contains("Bronze pickaxe") && !getBank().contains("Bronze pickaxe")))) {
			setGrandExchangeActions(new Ge2(getEvent(), this));
		} else if (totalAccountValue > Pickaxe.IRON.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) > 3 && getSkills().getStatic(Skill.MINING) < 6
				&& ((!getInventory().contains("Iron pickaxe") && !getBank().contains("Iron pickaxe")))) {

			setGrandExchangeActions(new Ge2(getEvent(), this));
		} else if (totalAccountValue > Pickaxe.STEEL.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) >= 6 && getSkills().getStatic(Skill.MINING) < 21
				&& ((!getInventory().contains("Steel pickaxe") && !getBank().contains("Steel pickaxe")))) {

			setGrandExchangeActions(new Ge2(getEvent(), this));
		} else if (totalAccountValue > Pickaxe.MITHRIL.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) >= 21
				&& ((!getInventory().contains("Mithril pickaxe") && !getBank().contains("Mithril pickaxe")))) {

			setGrandExchangeActions(new Ge2(getEvent(), this));
		} else if (totalAccountValue > Pickaxe.ADAMANT.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) >= 31 && getSkills().getStatic(Skill.MINING) < 41
				&& ((!getInventory().contains("Adamant pickaxe") && !getBank().contains("Adamant pickaxe")))) {

			setGrandExchangeActions(new Ge2(getEvent(), this));
		} else if (totalAccountValue > Pickaxe.RUNE.getPrice() && getBank().isOpen()
				&& getSkills().getStatic(Skill.MINING) >= 41
				&& ((!getInventory().contains("Rune pickaxe") && !getBank().contains("Rune pickaxe")))) {

			setGrandExchangeActions(new Ge2(getEvent(), this));
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
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());
			log("Finished G.E. task, walking back to varrock bank");
		}

		// Looping through the grand exchange task
		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
				&& !getGrandExchangeTask().getTask().finished()) {
			getGrandExchangeTask().getTask().loop();

			System.out.println("RESET 01");
			resetStage(AccountStage.MINING_LEVEL_TO_15.name());

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
