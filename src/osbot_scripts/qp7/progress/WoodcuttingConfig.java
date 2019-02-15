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
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;
import osbot_scripts.scripttypes.templates.WoodcuttingTemplateFactory;
import osbot_scripts.scripttypes.types.WoodcuttingType;
import osbot_scripts.scripttypes.woodcutting.logs.LogsCuttingWestOfVarrock;
import osbot_scripts.scripttypes.woodcutting.oak.OakCuttingWestOfVarrock;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class WoodcuttingConfig extends QuestStep {

	public WoodcuttingConfig(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.WOODCUTTING_GOLD_FARM, event, script, false);
		// TODO Auto-generated constructor stub
	}

	private String pickaxe;

	private GrandExchangeHandlerWoodcutting grandExchangeActions;

	private PlayersAround around = new PlayersAround(this);

	private WoodcuttingLocationTemplate woodcuttingTemplate;

	@Override
	public void onStart() {
		waitOnLoggedIn();

		if (beginTime == -1) {
			beginTime = System.currentTimeMillis();

			getCamera().movePitch(RandomUtil.getRandomNumberInRange(50, 67));
			// around.exchangeContext(getBot());
			// new Thread(around).start();
		}

		boolean oak = getSkills().getStatic(Skill.WOODCUTTING) >= 15;

		// Setting a woodcutting template
		if (getWoodcuttingTemplate() == null) {
			setWoodcuttingTemplate(
					oak ? WoodcuttingTemplateFactory.getWoodcuttingOakTemplate() : new LogsCuttingWestOfVarrock());
		} else if (oak && getWoodcuttingTemplate() != null) {
			int chanceToSwitchWoodcuttingLocation = RandomUtil.getRandomNumberInRange(0, 4);

			if (chanceToSwitchWoodcuttingLocation == 0) {
				setWoodcuttingTemplate(
						oak ? WoodcuttingTemplateFactory.getWoodcuttingOakTemplate() : new LogsCuttingWestOfVarrock());
			}
		}
		log("Got woodcutting template: " + getWoodcuttingTemplate().getClass().getSimpleName());

		if (getEvent() != null && getEvent().hasFinished() && Locations.GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from g.e.", -1, -1, getBot().getMethods(),
							getWoodcuttingTemplate().getPositionsFromGeToBank(), Locations.GRAND_EXCHANGE_AREA,
							getWoodcuttingTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true,
							this));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to varrock west bank from mining", -1, -1, getBot().getMethods(),
							getWoodcuttingTemplate().getPositionsFromWoodcuttingSpotToBank(),
							getWoodcuttingTemplate().getAreaOfWoodcuttingLocation(),
							getWoodcuttingTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true,
							this));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new BankTask("withdraw axe", 0, getBot().getMethods(), true,
						new BankItem[] { new BankItem("axe", 1, false) },
						getWoodcuttingTemplate().getBankAreaLocation(), this));

		if (getEvent() != null && getEvent().hasFinished() && Locations.GRAND_EXCHANGE_AREA.contains(myPlayer())) {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to woodcutting area (normal)", -1, -1, getBot().getMethods(),
							getWoodcuttingTemplate().getPositionsFromGeToWoodcuttingPosition(),
							Locations.GRAND_EXCHANGE_AREA, getWoodcuttingTemplate().getAreaOfWoodcuttingLocation(),
							getScript(), getEvent(), false, true, this));
		} else {
			getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
					new WalkTask("walk to woodcutting area", -1, -1, getBot().getMethods(),
							getWoodcuttingTemplate().getPositionsFromBankToWoodcuttingSpot(),
							getWoodcuttingTemplate().getBankAreaLocation(),
							getWoodcuttingTemplate().getAreaOfWoodcuttingLocation(), getScript(), getEvent(), false,
							true, this));
		}

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new ClickObjectTask("click tree", -1, -1, getBot().getMethods(),
						getWoodcuttingTemplate().getAreaOfWoodcuttingLocation(), oak ? 1751 : 1276,
						oak ? new BankItem("Oak logs", 1, false) : new BankItem("Logs", 1, false), true, this, true));

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(),
				new WalkTask("walk to varrock west bank 2", -1, -1, getBot().getMethods(),
						getWoodcuttingTemplate().getPositionsFromWoodcuttingSpotToBank(),
						getWoodcuttingTemplate().getAreaOfWoodcuttingLocation(),
						getWoodcuttingTemplate().getBankAreaLocation(), getScript(), getEvent(), false, true, this));

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

		if ((getWoodcuttingTemplate().getAreaOfWoodcuttingLocation().contains(myPlayer()))
				&& ((!getInventory().contains(1349) && !getInventory().contains(1351) && !getInventory().contains(1353)
						&& !getInventory().contains(1355) && !getInventory().contains(1357)
						&& !getInventory().contains(1359)))) {
			log("Is at wc area wihout an axe, restarting tasks 2!");
			resetStage(AccountStage.WOODCUTTING_GOLD_FARM.name());
		}

		if ((getWoodcuttingTemplate().getAreaOfWoodcuttingLocation().contains(myPlayer()))
				&& (!getInventory().contains("Bronze axe") && !getInventory().contains("Iron axe")
						&& !getInventory().contains("Steel axe") && !getInventory().contains("Mithril axe")
						&& !getInventory().contains("Adamant axe") && !getInventory().contains("Rune axe"))) {
			log("Is at woodcutting area wihout an axe, restarting tasks!");
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

		if (!getWoodcuttingTemplate().getAreaOfOperating().contains(myPlayer())) {
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
			if (coinsAmount > 15_000) {

				// Setting the status of the account that it wants to mule to another account
				// in
				// the database
				if (getEvent() != null && getEvent().getUsername() != null
						&& DatabaseUtilities.getMuleTradingFreeAccounts(this, getEvent()) < 3) {

					ConcreteWalking.walkToGe(this, getEvent());

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

	/**
	 * @return the woodcuttingTemplate
	 */
	public WoodcuttingLocationTemplate getWoodcuttingTemplate() {
		return woodcuttingTemplate;
	}

	/**
	 * @param woodcuttingTemplate
	 *            the woodcuttingTemplate to set
	 */
	public void setWoodcuttingTemplate(WoodcuttingLocationTemplate woodcuttingTemplate) {
		this.woodcuttingTemplate = woodcuttingTemplate;
	}

}
