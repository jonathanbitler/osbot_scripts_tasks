package osbot_scripts.qp7.progress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
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
import osbot_scripts.framework.DepositBank;
import osbot_scripts.framework.GenieLamp;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.Pickaxe;
import osbot_scripts.framework.WalkTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.qp7.progress.entities.Rock;
import osbot_scripts.scripttypes.mining.clay.ClayMiningRimmington;
import osbot_scripts.scripttypes.mining.clay.IronMiningWestOfVarrock;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

public class MiningRimmingtonClay extends QuestStep {

	public MiningRimmingtonClay(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MINING_RIMMINGTON_CLAY, event, script, false);
		// TODO Auto-generated constructor stub
	}

	public static final ArrayList<Position> MINING_SPOT_TO_DEPOSIT_BOX = new ArrayList<Position>(
			Arrays.asList(new Position(2986, 3241, 0), new Position(2986, 3241, 0), new Position(2985, 3240, 0),
					new Position(2985, 3239, 0), new Position(2985, 3238, 0), new Position(2986, 3237, 0),
					new Position(2987, 3236, 0), new Position(2987, 3235, 0), new Position(2987, 3234, 0),
					new Position(2987, 3233, 0), new Position(2988, 3232, 0), new Position(2989, 3232, 0),
					new Position(2990, 3232, 0), new Position(2990, 3233, 0), new Position(2990, 3234, 0),
					new Position(2991, 3234, 0), new Position(2992, 3234, 0), new Position(2993, 3235, 0),
					new Position(2994, 3236, 0), new Position(2995, 3236, 0), new Position(2996, 3236, 0),
					new Position(2997, 3236, 0), new Position(2998, 3236, 0), new Position(2999, 3236, 0),
					new Position(3000, 3237, 0), new Position(3001, 3237, 0), new Position(3002, 3237, 0),
					new Position(3003, 3237, 0), new Position(3004, 3237, 0), new Position(3005, 3237, 0),
					new Position(3006, 3237, 0), new Position(3007, 3237, 0), new Position(3008, 3238, 0),
					new Position(3009, 3239, 0), new Position(3010, 3239, 0), new Position(3011, 3239, 0),
					new Position(3012, 3239, 0), new Position(3013, 3240, 0), new Position(3014, 3241, 0),
					new Position(3015, 3241, 0), new Position(3016, 3241, 0), new Position(3017, 3241, 0),
					new Position(3018, 3241, 0), new Position(3019, 3241, 0), new Position(3020, 3241, 0),
					new Position(3021, 3241, 0), new Position(3022, 3241, 0), new Position(3023, 3241, 0),
					new Position(3024, 3241, 0), new Position(3025, 3241, 0), new Position(3026, 3241, 0),
					new Position(3027, 3240, 0), new Position(3028, 3239, 0), new Position(3028, 3238, 0),
					new Position(3029, 3237, 0), new Position(3030, 3237, 0), new Position(3031, 3237, 0),
					new Position(3032, 3237, 0), new Position(3033, 3236, 0), new Position(3034, 3236, 0),
					new Position(3035, 3236, 0), new Position(3036, 3236, 0), new Position(3037, 3236, 0),
					new Position(3038, 3236, 0), new Position(3039, 3236, 0), new Position(3040, 3236, 0),
					new Position(3041, 3236, 0), new Position(3042, 3236, 0), new Position(3043, 3236, 0),
					new Position(3044, 3236, 0), new Position(3045, 3235, 0)));

	public static final ArrayList<Position> DEPOSIT_BOX_TO_MINING_AREA = new ArrayList<Position>(
			Arrays.asList(new Position(3044, 3235, 0), new Position(3044, 3235, 0), new Position(3043, 3235, 0),
					new Position(3042, 3236, 0), new Position(3041, 3236, 0), new Position(3040, 3236, 0),
					new Position(3039, 3236, 0), new Position(3038, 3236, 0), new Position(3037, 3236, 0),
					new Position(3036, 3237, 0), new Position(3035, 3237, 0), new Position(3034, 3237, 0),
					new Position(3033, 3237, 0), new Position(3032, 3237, 0), new Position(3031, 3237, 0),
					new Position(3030, 3237, 0), new Position(3029, 3237, 0), new Position(3028, 3238, 0),
					new Position(3028, 3239, 0), new Position(3027, 3240, 0), new Position(3026, 3241, 0),
					new Position(3025, 3241, 0), new Position(3024, 3241, 0), new Position(3023, 3241, 0),
					new Position(3022, 3241, 0), new Position(3021, 3241, 0), new Position(3020, 3241, 0),
					new Position(3019, 3241, 0), new Position(3018, 3241, 0), new Position(3017, 3241, 0),
					new Position(3016, 3241, 0), new Position(3015, 3241, 0), new Position(3014, 3241, 0),
					new Position(3013, 3241, 0), new Position(3012, 3241, 0), new Position(3011, 3241, 0),
					new Position(3010, 3240, 0), new Position(3009, 3240, 0), new Position(3008, 3240, 0),
					new Position(3007, 3240, 0), new Position(3006, 3240, 0), new Position(3005, 3240, 0),
					new Position(3004, 3240, 0), new Position(3003, 3240, 0), new Position(3002, 3240, 0),
					new Position(3001, 3240, 0), new Position(3000, 3240, 0), new Position(2999, 3240, 0),
					new Position(2998, 3240, 0), new Position(2997, 3240, 0), new Position(2996, 3240, 0),
					new Position(2995, 3239, 0), new Position(2994, 3238, 0), new Position(2993, 3237, 0),
					new Position(2992, 3236, 0), new Position(2991, 3235, 0), new Position(2990, 3234, 0),
					new Position(2990, 3233, 0), new Position(2990, 3232, 0), new Position(2989, 3232, 0),
					new Position(2988, 3232, 0), new Position(2988, 3233, 0), new Position(2988, 3234, 0),
					new Position(2988, 3235, 0), new Position(2987, 3236, 0), new Position(2986, 3237, 0),
					new Position(2985, 3238, 0), new Position(2985, 3239, 0), new Position(2985, 3240, 0)));

	private MiningLocationTemplate miningTemplate;

	private String pickaxe;

	private Ge2 grandExchangeActions;

	private PlayersAround around = new PlayersAround(this);

	public int runs = 0;

	@Override
	public void onStart() {
		waitOnLoggedIn();

		// boolean iron = getSkills().getStatic(Skill.MINING) >= 15 &&
		// getQuests().getQuestPoints() >= 7;
		boolean iron = false;

		setMiningTemplate(new ClayMiningRimmington());
		// if (iron) {
		// setMiningTemplate(new IronMiningWestOfVarrock());
		// } else {
		// setMiningTemplate(new ClayMiningRimmington());
		// }

		boolean isDoingIronMining = getMiningTemplate() instanceof IronMiningWestOfVarrock;

		log("Set mining template to: " + getMiningTemplate() + " iron: " + isDoingIronMining);

		if (Config.doesntHaveAnyPickaxe(this) || beginTime == -1
				|| ((runs > 0) && (runs % (getInventory().contains("Bronze pickaxe") ? 2 : 5) == 0))) {
			if (getEvent() != null && getEvent().hasFinished() && Locations.GRAND_EXCHANGE_AREA.contains(myPlayer())) {
				getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
						new WalkTask("walk to fally bank from g.e.", -1, -1, getBot().getMethods(),
								getMiningTemplate().getPositionsFromGeToBank(), Locations.GRAND_EXCHANGE_AREA,
								getMiningTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true));
			} else {
				getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
						new WalkTask("walk to fally bank", -1, -1, getBot().getMethods(),
								getMiningTemplate().getPositionsFromMiningSpotToBank(),
								getMiningTemplate().getAreaOfMiningLocation(),
								getMiningTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true));
			}
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new BankTask("withdraw pickaxe", -1, getBot().getMethods(), true,
							new BankItem[] { new BankItem("pickaxe", 1, false) },
							getMiningTemplate().getBankAreaLocation(), this));

			// nog met webwalking
			if (getEvent() != null && getEvent().hasFinished() && Locations.GRAND_EXCHANGE_AREA.contains(myPlayer())) {
				getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
						new WalkTask("walk to mining area (normal)", -1, -1, getBot().getMethods(),
								getMiningTemplate().getPositionsFromGeToMiningPosition(), Locations.GRAND_EXCHANGE_AREA,
								getMiningTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true));
			} else {
				getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
						new WalkTask("walk to mining area", -1, -1, getBot().getMethods(),
								getMiningTemplate().gePositionsFromBankToMiningSpot(),
								getMiningTemplate().getBankAreaLocation(),
								getMiningTemplate().getAreaOfMiningLocation(), getScript(), getEvent(), false, true));
			}

		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to deposit box", -1, -1, getBot().getMethods(), MINING_SPOT_TO_DEPOSIT_BOX,
							new Area(new int[][] { { 2983, 3243 }, { 2983, 3237 }, { 2990, 3237 }, { 2990, 3243 } }),
							new Area(new int[][] { { 3039, 3238 }, { 3039, 3231 }, { 3054, 3232 }, { 3054, 3239 },
									{ 3039, 3239 } }),
							getScript(), getEvent(), false, true));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new DepositBank("deposit clay ore", -1, getBot().getMethods(),
							new BankItem[] { new BankItem("Clay", 434, 27, 1, false),
									new BankItem("Uncut diamond", 1617, 1000, 1, true),
									new BankItem("Uncut emerald", 1621, 1000, 1, true),
									new BankItem("Uncut ruby", 1619, 1000, 1, true),
									new BankItem("Uncut sapphire", 1623, 1000, 1, true) },
							new Area(new int[][] { { 3039, 3238 }, { 3039, 3231 }, { 3054, 3232 }, { 3054, 3239 },
									{ 3039, 3239 } }),
							this));

			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to mining area (from deposit box)", -1, -1, getBot().getMethods(),
							DEPOSIT_BOX_TO_MINING_AREA,
							new Area(new int[][] { { 3041, 3238 }, { 3053, 3238 }, { 3053, 3232 }, { 3041, 3232 },
									{ 3041, 3238 } }),
							getMiningTemplate().getAreaOfMiningLocation(), getScript(), getEvent(), false, true));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to mining spot", -1, -1, getBot().getMethods(),
						getMiningTemplate().getExactMiningSpotPosition(), getMiningTemplate().getAreaOfMiningLocation(),
						getScript(), getEvent(), false, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click mining", -1, -1, getBot().getMethods(),
						getMiningTemplate().exactMiningPositionArea(), isDoingIronMining ? 7488 : 7454,
						isDoingIronMining ? new BankItem("Iron ore", 1, false) : new BankItem("Clay", 1, false), true,
						this, isDoingIronMining ? Rock.IRON : Rock.CLAY));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank 2", -1, -1, getBot().getMethods(),
						getMiningTemplate().getPositionsFromMiningSpotToBank(),
						getMiningTemplate().getAreaOfMiningLocation(), getMiningTemplate().getBankAreaLocation(),
						getScript(), getEvent(), false, true));

		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();

			around.exchangeContext(getBot());
			new Thread(around).start();

			// if (Config.NO_LOGIN) {
			// ThreadDemo demo = new ThreadDemo();
			// demo.exchangeContext(getBot());
			// demo.setLoginEvent(getEvent());
			// new Thread(demo).start();
			// }
		}

	}

	public void onPaint(Graphics2D g) {
		// g.drawString("MINING_CLAY", 60, 50);
		// g.drawString("Runtime " + (formatTime((System.currentTimeMillis() -
		// beginTime))), 60, 75);
		g.setColor(Color.WHITE);
		// int profit = ((currentAmount - beginAmount) + soldAmount) * 60;
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
		// g.drawString("Calculated wealth " + ((currentAmount * 60) + (runs * 27 *
		// 60)), 60, 140);
		g.drawString("Runs " + (runs), 60, 165);

		g.drawString((around.aroundMine < 0 ? "Calculating.."
				: around.aroundMine + " / " + around.getPlayersAroundExceptMe()).toString(), 60, 200);
	}

	private int hopAmount = 0;

	private long timeoutHop = -1;

	@Override
	public void onLoop() throws InterruptedException, IOException {
		log("Running the side loop..");

		if (getMiningTemplate().getAreaOfMiningLocation().contains(myPlayer())) {
			if (System.currentTimeMillis() - timeoutHop > 600_000 && !Config.NO_LOGIN) {
				int amount = 4;

				if (WorldHop.hop(this, amount, 4)) {
					hopAmount++;
					log("Current hop amount: " + hopAmount);

					if (hopAmount > 4) {
						log("Hop timeout initializted!");
						timeoutHop = System.currentTimeMillis();
						hopAmount = 0;
					}
					return;
				}
			}
		}

		// Closing the world hopper
		RS2Widget worldHopper = getWidgets().get(69, 3);
		if (worldHopper != null && worldHopper.isVisible()) {
			worldHopper.interact();
		}

		// When inventory not open, go to inventory
		if (!getTabs().isOpen(Tab.INVENTORY)) {
			getTabs().open(Tab.INVENTORY);
		}

		GenieLamp.openGenieLamp(this);

		if (!Config.NO_LOGIN) {
			if (getEvent() != null && getEvent().hasFinished() && !isLoggedIn()) {
				log("Not logged in.. restarting");
				BotCommands.waitBeforeKill((MethodProvider) this, "BECAUSE OF NOT LOGGED IN ATM E02");
			}
		}

		// Supporting world hopping when world is too full
		// int threshholdToHop = 10_000;
		// int profit = ((currentAmount - beginAmount) + soldAmount) * 140;
		// long profitPerHour = (long) (profit * (3600000.0 /
		// (System.currentTimeMillis() - beginTime)));
		// if ((profitPerHour > 0) && (profitPerHour < threshholdToHop)
		// && ((System.currentTimeMillis() - beginTime) > 1_800_000)
		// || ((System.currentTimeMillis() - beginTime) > 3_600_000)) {
		// if (WorldHop.hop(this)) {
		// beginTime = System.currentTimeMillis();
		// beginAmount = currentAmount;
		// soldAmount = 0;
		// }
		// }

		if (isLoggedIn()) {
			// If the player is fighting or under combat, then reset the stage to prevent
			// going dead

			if (getMiningTemplate().getAreaOfMiningLocation().contains(myPlayer()) && ((getInventory().contains(1266)
					|| getInventory().contains(1268) || getInventory().contains(1270) || getInventory().contains(1272)
					|| getInventory().contains(1274) || getInventory().contains(1276)))) {
				log("Is at mining area wihout a pickaxe, restarting tasks 2!");
				resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());
			}

			if (getMiningTemplate().getAreaOfMiningLocation().contains(myPlayer())
					&& !getInventory().contains("Bronze pickaxe") && !getInventory().contains("Iron pickaxe")
					&& !getInventory().contains("Steel pickaxe") && !getInventory().contains("Mithril pickaxe")
					&& !getInventory().contains("Adamant pickaxe") && !getInventory().contains("Rune pickaxe")) {
				log("Is at mining area wihout a pickaxe, restarting tasks!");
				resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());
			}

			if (Config.doesntHaveAnyPickaxe(this) && !getBank().isOpen()
					&& getMiningTemplate().getAreaOfMiningLocation().contains(myPlayer())) {
				log("Player doesn't have any pickaxe, getting it from bank");
				resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());
			}

			if (!getMiningTemplate().getAreaOfOperating().contains(myPlayer())) {
				log("not in mining zone!");
				resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());
			}
		}

		// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
		// corresponding with this skill level, when the player is in lumrbidge, then
		// also execute this task to get a new mining pickaxe
		// When having more than 200 clay, then go to the g.e. and sell it
		if (getBank().isOpen() && (getBank().getAmount("Clay") > 200
				|| (getBank().getAmount("Iron ore") > 200 && getQuests().getQuestPoints() >= 7))) {
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
					Thread.sleep(1500);

				}

				// In location of bronze pickaxea and got the pickaxe, walk back
				while (bronzePickaxeLocation.contains(myPlayer()) && (getInventory().contains("Bronze pickaxe"))) {
					getWalking().webWalk(getMiningTemplate().getAreaOfMiningLocation());
					getWalking().walkPath(getMiningTemplate().getExactMiningSpotPosition());
					Thread.sleep(1500);
				}

			} else {

				Ge2 ge = new Ge2(getEvent(), this);

				GrandExchangeTask task = new GrandExchangeTask(this, new BankItem[] {},
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
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
		int ironAmount = -1;
		int totalAccountValue = -1;
		if (getBank().isOpen()) {
			clayAmount = (int) getBank().getAmount("Clay");
			ironAmount = (int) getBank().getAmount("Iron ore");
			totalAccountValue += (int) getBank().getAmount(995);
			totalAccountValue += (clayAmount * 60);

			/**
			 * Coins calculation for runs that you've done.
			 */
			int coinsPirceForClay = getGrandexchangePriceForItem(434, true);
			int totalCoinsInRuns = (runs * (coinsPirceForClay * 27));
			totalAccountValue += totalCoinsInRuns;

			if (getQuests().getQuestPoints() >= 7) {
				totalAccountValue += (ironAmount * 90);
			}

			int bankedAmount = (int) getBank().getAmount("Clay");
			if (getQuests().getQuestPoints() >= 7) {
				bankedAmount += (int) getBank().getAmount("Iron ore");
			}
			if (beginAmount == -1) {
				beginAmount = bankedAmount;
			}
			if (bankedAmount < currentAmount) {
				soldAmount += (currentAmount - bankedAmount);
			}
			currentAmount = bankedAmount;

			// int coinsAmount = (int) getBank().getAmount(995);

			// If has more than 100k then start tradinig it over to the mule
			if (((runs > 0) && ((runs % 10) == 0)) || (getBank().isOpen() && getBank().getAmount("Coins") > 33_000)) {

				// Setting the status of the account that it wants to mule to another account in
				// the database
				if (getEvent() != null && getEvent().getUsername() != null
						&& DatabaseUtilities.getMuleTradingFreeAccounts(this, getEvent()) < 5) {

					ConcreteWalking.walkToGe(this);

					Ge2 ge = new Ge2(getEvent(), this);

					GrandExchangeTask task = new GrandExchangeTask(this, new BankItem[] {},
							new BankItem[] { new BankItem("Uncut diamond", 1617, 1000, 1, true),
									new BankItem("Uncut emerald", 1621, 1000, 1, true),
									new BankItem("Uncut ruby", 1619, 1000, 1, true),
									new BankItem("Uncut sapphire", 1623, 1000, 1, true),
									new BankItem("Clay", 434, 1000, 1, true) },
							getEvent(), getScript(), this);

					ge.setTask(task);
					setGrandExchangeActions(ge);

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
			// }
			// else if (totalAccountValue > Pickaxe.IRON.getPrice() && getBank().isOpen()
			// && getSkills().getStatic(Skill.MINING) > 3 &&
			// getSkills().getStatic(Skill.MINING) < 6
			// && ((!getInventory().contains("Iron pickaxe") && !getBank().contains("Iron
			// pickaxe")))) {
			//
			// setGrandExchangeActions(new Ge2(getEvent(), this));
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
			if (getBank().isOpen()) {
				getGrandExchangeTask().exchangeContext(getBot());
				getGrandExchangeTask().setTask();
			} else {
				setGrandExchangeActions(null);
			}
		}

		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
				&& getGrandExchangeTask().getTask().finished()) {
			// the current grand exchange task to null
			setGrandExchangeActions(null);
			resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());
			log("Finished G.E. task, walking back to varrock bank");
		}

		// Looping through the grand exchange task
		if (getGrandExchangeTask() != null && getGrandExchangeTask().getTask() != null
				&& !getGrandExchangeTask().getTask().finished()) {
			getGrandExchangeTask().getTask().loop();

			System.out.println("RESET 01");
			resetStage(AccountStage.MINING_RIMMINGTON_CLAY.name());

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

	/**
	 * @return the miningTemplate
	 */
	public MiningLocationTemplate getMiningTemplate() {
		return miningTemplate;
	}

	/**
	 * @param miningTemplate
	 *            the miningTemplate to set
	 */
	public void setMiningTemplate(MiningLocationTemplate miningTemplate) {
		this.miningTemplate = miningTemplate;
	}

}
