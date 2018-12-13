package osbot_scripts.qp7.progress;

import java.awt.Graphics2D;
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
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.Pickaxe;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.util.Sleep;

@ScriptManifest(author = "pim97@github & dormic@osbot", info = "ge", logo = "", name = "GE_SELL_BUY_MINING", version = 0)
public class Ge2 extends Script {

	public Ge2(LoginEvent login) {
		this.login = login;
	}

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
			log("tried to set to tries: " + login + " " + tries);
			if (login != null) {
				DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
						login.getUsername(), login);
			}
			BotCommands.killProcess((MethodProvider) this, (Script) this, "BECAUSE OF FAILED GE2 TASK", login);
		}

		if (!getClient().isLoggedIn() && login.hasFinished()) {
			log("Isn't logged in!?");
			Thread.sleep(5000);
			BotCommands.waitBeforeKill((MethodProvider) this, "BECAUSE OF NOT LOGGED IN RIGHT NOW");
		}

		// Fixed mode
		MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);
		ev.fixedMode();
		ev.fixedMode2();

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

		} else if (getTask() != null && getTask().finished()) {
			// log("Task has finished!");
			// if (login != null) {
			// DatabaseUtilities.updateStageProgress(this,
			// RandomUtil.gextNextAccountStage(this).name(), 0,
			// login.getUsername());
			// }
			// BotCommands.killProcess((MethodProvider) this, (Script) this);
		}

		return random(800, 1600);
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

					int ironOreAmount = (int) (getBank().getAmount("Iron ore"));
					int clayAmount = (int) (getBank().getAmount("Clay"));
					setTask(new GrandExchangeTask(this, new BankItem[] {},
							new BankItem[] { new BankItem("Iron ore", 440, ironOreAmount, 1, true),
									new BankItem("Uncut diamond", 1617, 1000, 1, true),
									new BankItem("Uncut emerald", 1621, 1000, 1, true),
									new BankItem("Uncut ruby", 1619, 1000, 1, true),
									new BankItem("Uncut sapphire", 1623, 1000, 1, true),
									new BankItem("Clay", 434, clayAmount, 1, true) },
							login, (Script) this));
				}
			}

			if (!getBank().isOpen()) {
				getBank().open();
			}

			// When having more than 200 clay, then go to the g.e. and sell it
			if (getBank().isOpen() && (getBank().getAmount("Iron ore") > 200 || getBank().getAmount("Clay") > 200)) {
				int ironOreAmount = (int) (getBank().getAmount("Iron ore"));
				int clayAmount = (int) (getBank().getAmount("Clay"));
				setTask(new GrandExchangeTask(this, new BankItem[] {},
						new BankItem[] { new BankItem("Iron ore", 440, ironOreAmount, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true),
								new BankItem("Clay", 434, clayAmount, 1, true) },
						login, (Script) this));
			}

			int ironAmount = -1;
			int clayAmount = -1;
			int totalAccountValue = -1;
			int coinsAmount = -1, coinsInventoryAmount = -1, ironAmountInventory = -1, clayAmountInventory = -1;
			if (getBank().isOpen()) {
				ironAmount = (int) getBank().getAmount("Iron ore");
				ironAmountInventory = (int) getInventory().getAmount("Iron ore");
				clayAmountInventory = (int) getInventory().getAmount("Clay");
				clayAmount = (int) getBank().getAmount("Clay");
				coinsAmount = (int) getBank().getAmount(995);
				coinsInventoryAmount = (int) getInventory().getAmount(995);
				totalAccountValue += coinsAmount;
				totalAccountValue += coinsInventoryAmount;
				totalAccountValue += (ironAmount * 100);
				totalAccountValue += (ironAmountInventory * 100);
				totalAccountValue += (clayAmount * 90);
				totalAccountValue += (clayAmountInventory * 90);
			}

			log("[ESTIMATED] account value is: " + totalAccountValue);
			if (login != null && login.getUsername() != null && totalAccountValue > 0) {
				DatabaseUtilities.updateAccountValue(this, login.getUsername(), totalAccountValue, login);
			}

			if (!getBank().isOpen()) {
				getBank().open();

				if (Config.doesntHaveAnyPickaxe(this)) {

					if (totalAccountValue >= 0 && totalAccountValue < Pickaxe.BRONZE.getPrice()) {
						DatabaseUtilities.updateStageProgress(this, AccountStage.OUT_OF_MONEY.name(), 0,
								login.getUsername(), login);
						log("Not enough money.. closing for next stage");
						BotCommands.waitBeforeKill(this, "BECAUSE OF NOT HAVING ENOUGH MONEY E02");
					}

					if (totalAccountValue < Pickaxe.IRON.getPrice()) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Bronze pickaxe", 1265, 1, Pickaxe.BRONZE.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								login, (Script) this));
					}

					if (totalAccountValue > Pickaxe.BRONZE.getPrice() && getSkills().getStatic(Skill.MINING) <= 3) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Bronze pickaxe", 1265, 1, Pickaxe.BRONZE.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								login, (Script) this));

					} else if (totalAccountValue > Pickaxe.IRON.getPrice() && getBank().isOpen()
							&& getSkills().getStatic(Skill.MINING) > 3 && getSkills().getStatic(Skill.MINING) < 6) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Iron pickaxe", 1267, 1, Pickaxe.IRON.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
								login, (Script) this));

					} else if (totalAccountValue > Pickaxe.STEEL.getPrice() && getBank().isOpen()
							&& getSkills().getStatic(Skill.MINING) >= 6 && getSkills().getStatic(Skill.MINING) < 21) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Steel pickaxe", 1269, 1, Pickaxe.STEEL.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
								login, (Script) this));

					} else if (totalAccountValue > Pickaxe.MITHRIL.getPrice() && getBank().isOpen()
							&& getSkills().getStatic(Skill.MINING) >= 21 && getSkills().getStatic(Skill.MINING) < 31) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Mithril pickaxe", 1273, 1, Pickaxe.MITHRIL.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								login, (Script) this));

					} else if (totalAccountValue > Pickaxe.ADAMANT.getPrice() && getBank().isOpen()
							&& getSkills().getStatic(Skill.MINING) >= 31 && getSkills().getStatic(Skill.MINING) < 41) {
						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Adamant pickaxe", 1271, 1, Pickaxe.ADAMANT.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								login, (Script) this));

					} else if (totalAccountValue > Pickaxe.RUNE.getPrice() && getBank().isOpen()
							&& getSkills().getStatic(Skill.MINING) >= 41) {

						setTask(new GrandExchangeTask(this,
								new BankItem[] {
										new BankItem("Rune pickaxe", 1275, 1, Pickaxe.RUNE.getPrice(), false) },
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
								login, (Script) this));
					}

				}
			}

			// The tasks of getting new pickaxes, looking for the skill and then the pickaxe
			// corresponding with this skill level, when the player is in lumrbidge, then
			// also execute this task to get a new mining pickaxe
			// This is made so when a player reaches a level, or doesn't have a base
			// pickaxe, then it goes to the g.e. and buys one according to its mining level
			if (totalAccountValue > Pickaxe.BRONZE.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) <= 3
					&& ((!getInventory().contains("Bronze pickaxe") && !getBank().contains("Bronze pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Bronze pickaxe", 1265, 1, Pickaxe.BRONZE.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true) },
						login, (Script) this));
			} else if (totalAccountValue > Pickaxe.IRON.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) > 3 && getSkills().getStatic(Skill.MINING) < 6
					&& ((!getInventory().contains("Iron pickaxe") && !getBank().contains("Iron pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Iron pickaxe", 1267, 1, Pickaxe.IRON.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
						login, (Script) this));
			} else if (totalAccountValue > Pickaxe.STEEL.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) >= 6 && getSkills().getStatic(Skill.MINING) < 21
					&& ((!getInventory().contains("Steel pickaxe") && !getBank().contains("Steel pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Steel pickaxe", 1269, 1, Pickaxe.STEEL.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
						login, (Script) this));
			} else if (totalAccountValue > Pickaxe.MITHRIL.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) >= 21 && getSkills().getStatic(Skill.MINING) < 31
					&& ((!getInventory().contains("Mithril pickaxe") && !getBank().contains("Mithril pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Mithril pickaxe", 1273, 1, Pickaxe.MITHRIL.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true) },
						login, (Script) this));
			} else if (totalAccountValue > Pickaxe.ADAMANT.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) >= 31 && getSkills().getStatic(Skill.MINING) < 41
					&& ((!getInventory().contains("Adamant pickaxe") && !getBank().contains("Adamant pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Adamant pickaxe", 1271, 1, Pickaxe.ADAMANT.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true) },
						login, (Script) this));
			} else if (totalAccountValue > Pickaxe.RUNE.getPrice() && getBank().isOpen()
					&& getSkills().getStatic(Skill.MINING) >= 41
					&& ((!getInventory().contains("Rune pickaxe") && !getBank().contains("Rune pickaxe")))) {

				setTask(new GrandExchangeTask(this,
						new BankItem[] { new BankItem("Rune pickaxe", 1275, 1, Pickaxe.RUNE.getPrice(), false) },
						new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
								new BankItem("Clay", 434, 1000, 1, true),
								new BankItem("Uncut diamond", 1617, 1000, 1, true),
								new BankItem("Uncut emerald", 1621, 1000, 1, true),
								new BankItem("Uncut ruby", 1619, 1000, 1, true),
								new BankItem("Uncut sapphire", 1623, 1000, 1, true), },
						login, (Script) this));
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

}