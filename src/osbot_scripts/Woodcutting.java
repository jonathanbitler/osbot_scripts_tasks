package osbot_scripts;

import java.awt.Graphics2D;
import java.io.IOException;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.Coordinates;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.WoodcuttingConfig;
import osbot_scripts.scripttypes.WoodcuttingType;

@ScriptManifest(author = "pim97", info = "WOODCUTTING_GOLD_FARM", logo = "", name = "WOODCUTTING_GOLD_FARM", version = 1.0)
public class Woodcutting extends Script {

	private WoodcuttingConfig goldfarmMining;

	private LoginEvent login;

	private MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		if (getGoldfarmMining().isLoggedIn()) {
			ev.fixedMode();
			ev.fixedMode2();
			ev.executeAllEvents();
		}

		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername(), login);
			BotCommands.killProcess((MethodProvider) this, (Script) this, "SHOULD BE ON TUT ISLAND MINING 15", login);
		}

		log("G.E. task: "
				+ (getGoldfarmMining().getGrandExchangeTask() != null ? getGoldfarmMining().getGrandExchangeTask()
						: "NULL"));

		// if (login.hasFinished()) {
		if (getGoldfarmMining().getGrandExchangeTask() == null) {
			try {
				getGoldfarmMining().getTaskHandler().taskLoop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				getGoldfarmMining().onLoop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// }
		return random(20, 80);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getGoldfarmMining().onPaint(g);
	}

	@Override
	public void onStart() throws InterruptedException {
		if (!Config.NO_LOGIN) {
			login = LoginHandler.login(this, getParameters());

			if (login != null) {
				login.setScript("WOODCUTTING_GOLD_FARM");
				DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN", login);
			}
		}
		goldfarmMining = new WoodcuttingConfig(login, (Script) this);
		goldfarmMining.setScriptAbstract(new WoodcuttingType());
		getGoldfarmMining().setQuest(false);

		if (!Config.NO_LOGIN) {
			if (login != null && login.getUsername() != null) {
				getGoldfarmMining().setQuestStageStep(0);
				// Integer.parseInt(DatabaseUtilities.getQuestProgress(this,
				// login.getUsername())));

				DatabaseUtilities.updateStageProgress(this, "WOODCUTTING_GOLD_FARM", 0,
						getGoldfarmMining().getEvent().getUsername(), login);
			}
		}

		getGoldfarmMining().exchangeContext(getBot());
		getGoldfarmMining().onStart();

	}

	/**
	 * @return the goldfarmMining
	 */
	public WoodcuttingConfig getGoldfarmMining() {
		return goldfarmMining;
	}

	/**
	 * @param goldfarmMining
	 *            the goldfarmMining to set
	 */
	public void setGoldfarmMining(WoodcuttingConfig goldfarmMining) {
		this.goldfarmMining = goldfarmMining;
	}

}
