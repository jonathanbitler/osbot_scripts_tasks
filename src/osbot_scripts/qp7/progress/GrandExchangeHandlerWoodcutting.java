package osbot_scripts.qp7.progress;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.Axe;
import osbot_scripts.framework.GEPrice;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.util.Sleep;

@ScriptManifest(author = "pim97@github & dormic@osbot", info = "ge", logo = "", name = "GE_SELL_BUY_MINING_WC", version = 0)
public class GrandExchangeHandlerWoodcutting extends Script {

	public GrandExchangeHandlerWoodcutting(LoginEvent login, QuestStep quest) {
		this.login = login;
		this.quest = quest;
	}

	private QuestStep quest;

	private GrandExchangeTask task;

	private LoginEvent login;

	private int tries = 0;

	private static final ArrayList<Position> GE_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3446, 0), new Position(3183, 3451, 0),
					new Position(3174, 3456, 0), new Position(3165, 3461, 0), new Position(3165, 3461, 0),
					new Position(3165, 3471, 0), new Position(3165, 3481, 0), new Position(3164, 3486, 0)));

	/**
	 * Loops
	 */
	@Override
	public int onLoop() throws InterruptedException {

		// if (demo == null) {
		// log("Started new thread demo!");
		// demo = new ThreadDemo();
		// demo.exchangeContext(this.getBot());
		// demo.setLoginEvent(login);
		// new Thread(demo).start();
		// }

		if (getTask() == null) {
			sideLoop();
		}

		tries++;

		log("current tries: " + tries);
		if (tries > 50) {
			if (login != null) {
				log("tried to set to tries: " + login + " " + tries);
				DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this, login).name(), 0,
						login.getUsername(), login);
				BotCommands.killProcess((MethodProvider) this, (Script) this, "BECAUSE OF FAILED GE2 TASK", login);
			}
		}

		if (!getClient().isLoggedIn() && login != null && login.hasFinished()) {
			log("Isn't logged in!?");
			Thread.sleep(5000);
			BotCommands.waitBeforeKill((MethodProvider) this, "BECAUSE OF NOT LOGGED IN RIGHT NOW");
		}

		// Fixed mode
		if (login != null) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);
			ev.fixedMode();
			ev.fixedMode2();
		}

		// If the player is not in the grand exchange area, then walk to it
		if (myPlayer() != null
				&& !new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
						.contains(myPlayer())) {

			// Contains in banking area varrock
			if (new Area(new int[][] { { 3180, 3438 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3438 } })
					.contains(myPlayer())) {
				getWalking().walkPath(GE_PATH);
			} else {
				getWalking().webWalk(
						new Area(new int[][] { { 3160, 3494 }, { 3168, 3494 }, { 3168, 3485 }, { 3160, 3485 } }));
				log("The player has a grand exchange task but isn't there, walking to there");
			}
		}

		log("task: " + getTask());
		if (getTask() != null && !getTask().finished()) {
			getTask().loop();

		}

		// else if (getTask() != null && getTask().finished()) {
		// log("Task has finished!");
		// if (login != null) {
		// DatabaseUtilities.updateStageProgress(this,
		// RandomUtil.gextNextAccountStage(this).name(), 0,
		// login.getUsername());
		// }
		// BotCommands.killProcess((MethodProvider) this, (Script) this);
		// }

		return random(800, 1600);
	}

	private int getAmountOfItemInBankAndInventory(String name) {
		return (int) (getBank().getAmount(name) + (int) (getInventory().getAmount(name)));
	}

	public void sideLoop() throws InterruptedException {
		log("Running the side loop..");

		if (getClient().isLoggedIn() && myPlayer() != null && myPlayer().isVisible()) {

			if (tries > 15 && getTask() == null) {
				if (!getBank().isOpen()) {
					getBank().open();
				}

				Sleep.sleepUntil(() -> getBank().isOpen(), 10000);

				// Finding the deposit button
				RS2Widget depositAll = getWidgets().get(12, 42);
				Sleep.sleepUntil(() -> depositAll != null, 10000);

				// Wait until it clicked on the button and inventory is empty
				if (depositAll != null) {
					depositAll.interact("Deposit inventory");
				}
				Sleep.sleepUntil(() -> getInventory().isEmpty(), 10000);
				log("[GRAND EXCHANGE] inventory deposited");

				// When having more than 200 clay, then go to the g.e. and sell it
				if (getBank().isOpen()) {

					int logsAmount = (int) (getAmountOfItemInBankAndInventory("Logs"));
					int oakAmount = (int) (getAmountOfItemInBankAndInventory("Oak logs"));
					setTask(new GrandExchangeTask(this, new BankItem[] {},
							new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
									new BankItem("Logs", 1511, logsAmount, 1, true) },
							login, (Script) this, getQuest()));
				}
			}

			if (!getBank().isOpen()) {
				getBank().open();
			}

			// When having more than 200 clay, then go to the g.e. and sell it
			if (getBank().isOpen() && (getAmountOfItemInBankAndInventory("Logs") > 200
					|| getAmountOfItemInBankAndInventory("Oak logs") > 200)) {
				int logsAmount = (int) (getAmountOfItemInBankAndInventory("Logs"));
				int oakAmount = (int) (getAmountOfItemInBankAndInventory("Oak logs"));
				setTask(new GrandExchangeTask(this, new BankItem[] {},
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));
			}

			GEPrice price = new GEPrice();

			int oakPrice = 0;
			try {
				oakPrice = price.getBuyingPrice(1521);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int logsPrice = 0;
			try {
				logsPrice = price.getBuyingPrice(1511);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int oakLogs = -1;
			int normalLogs = -1;
			int totalAccountValue = -1;
			int coinsAmount = -1, coinsInventoryAmount = -1, oakAmountInventory = -1, normalAmountInventory = -1;
			if (getBank().isOpen()) {
				oakLogs = (int) getBank().getAmount("Oak logs");
				oakAmountInventory = (int) getInventory().getAmount("Oak logs");
				normalAmountInventory = (int) getInventory().getAmount("Logs");
				normalLogs = (int) getBank().getAmount("Logs");
				coinsAmount = (int) getBank().getAmount(995);
				coinsInventoryAmount = (int) getInventory().getAmount(995);
				totalAccountValue += coinsAmount;
				totalAccountValue += coinsInventoryAmount;
				totalAccountValue += (oakLogs * oakPrice);
				totalAccountValue += (oakAmountInventory * oakPrice);
				totalAccountValue += (normalLogs * logsPrice);
				totalAccountValue += (normalAmountInventory * logsPrice);
			}

			log("[ESTIMATED] account value is: " + totalAccountValue);
			if (login != null && login.getUsername() != null && totalAccountValue > 0) {
				DatabaseUtilities.updateAccountValue(this, login.getUsername(), totalAccountValue, login);
			}

			int logsAmount = (int) (getAmountOfItemInBankAndInventory("Logs"));
			int oakAmount = (int) (getAmountOfItemInBankAndInventory("Oak logs"));

			// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
			// corresponding with this skill level, when the player is in lumrbidge, then
			// also execute this task to get a new mining pickaxe
			// This is made so when a player reaches a level, or doesn't have a base
			// pickaxe, then it goes to the g.e. and buys one according to its mining level
			if (totalAccountValue > Axe.BRONZE.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) <= 3
					&& ((!getInventory().contains("Bronze axe") && !getBank().contains("Bronze axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Bronze axe", 1351, 1, Axe.BRONZE.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));

			} else if (totalAccountValue > Axe.IRON.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) > 3 && getSkills().getStatic(Skill.WOODCUTTING) < 6
					&& ((!getInventory().contains("Iron axe") && !getBank().contains("Iron axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Iron axe", 1349, 1, Axe.IRON.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));

			} else if (totalAccountValue > Axe.STEEL.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) >= 6 && getSkills().getStatic(Skill.WOODCUTTING) < 21
					&& ((!getInventory().contains("Steel axe") && !getBank().contains("Steel axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Steel axe", 1353, 1, Axe.STEEL.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));
				
			} else if (totalAccountValue > Axe.MITHRIL.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) >= 21 && getSkills().getStatic(Skill.WOODCUTTING) < 31
					&& ((!getInventory().contains("Mithril axe") && !getBank().contains("Mithril axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Mithril axe", 1355, 1, Axe.MITHRIL.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));
				
			} else if (totalAccountValue > Axe.ADAMANT.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) >= 31 && getSkills().getStatic(Skill.WOODCUTTING) < 41
					&& ((!getInventory().contains("Adamant axe") && !getBank().contains("Adamant axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Adamant axe", 1357, 1, Axe.ADAMANT.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));
				
			} else if (totalAccountValue > Axe.RUNE.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.WOODCUTTING) >= 41
					&& ((!getInventory().contains("Rune axe") && !getBank().contains("axe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Rune axe", 1359, 1, Axe.RUNE.getPrice(), false) },
						new BankItem[] { new BankItem("Oak logs", 1521, oakAmount, 1, true),
								new BankItem("Logs", 1511, logsAmount, 1, true) },
						login, (Script) this, getQuest()));
				
			}
		}
	}

	public void setTask() throws InterruptedException {
		log("Trying to set a task!");
		onLoop();
	}

	@Override
	public void onStart() throws InterruptedException {
		// while (!getBank().isOpen()) {
		// getBank().open();
		//
		// log("Waiting for bank to open..");
		// Thread.sleep(1000);
		// }
		if (getTask() == null) {
			sideLoop();
		}
		// login = LoginHandler.login(this, getParameters());
		// DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN");
	}

	// private ThreadDemo demo;

	@Override
	public void onExit() throws InterruptedException {

	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	/**
	 * @return the task
	 */
	public GrandExchangeTask getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(GrandExchangeTask task) {
		this.task = task;
	}

	/**
	 * @return the quest
	 */
	public QuestStep getQuest() {
		return quest;
	}

	/**
	 * @param quest
	 *            the quest to set
	 */
	public void setQuest(QuestStep quest) {
		this.quest = quest;
	}

}