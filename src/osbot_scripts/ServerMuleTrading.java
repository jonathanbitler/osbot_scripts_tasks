package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.Coordinates;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.MuleTradingConfiguration;
import osbot_scripts.qp7.progress.ServerMuleTradingConfiguration;
import osbot_scripts.qp7.progress.SuperMuleTradingConfiguration;

@ScriptManifest(author = "pim97", info = "SERVERMULE_TRADING", logo = "", name = "SERVERMULE_TRADING", version = 1.0)
public class ServerMuleTrading extends Script {

	private ServerMuleTradingConfiguration muleTrading;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername(), login);
			BotCommands.killProcess((MethodProvider) this, (Script) this, "SHOULD BE ON TUT ISLAND, MULE TRADING",
					login);
		}

		if (login.hasFinished() && !getClient().isLoggedIn()) {
			BotCommands.waitBeforeKill((MethodProvider) this, "NOT LOGGED IN, MULE TRADING");
		}

		if (getClient().isLoggedIn() && !getTrade().isCurrentlyTrading()) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);
			ev.fixedMode();
			ev.fixedMode2();
			if (login.getAccountStage().equalsIgnoreCase("MULE-TRADING")) {
				ev.executeAllEvents();
			}
		}

		if (!getClient().isLoggedIn()) {
			BotCommands.waitBeforeKill(this, "BECAUSE MULING AND ACCOUNT IS LOGGED OFF");
		}

		// Account must have atleast 7 quest points, otherwise set it back to quesiton

		// if (getQuests().getQuestPoints() < 7) {
		// DatabaseUtilities.updateStageProgress(this,
		// RandomUtil.gextNextAccountStage(this).name(), 0,
		// login.getUsername());
		// BotCommands.killProcess((Script) this);
		// }

		// Looping for tasks and normal loop
		getMuleTrading().onLoop();
		// getMuleTrading().getTaskHandler().taskLoop();

		return random(1000, 2000);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		login.setScript("SERVERMULE_TRADING");
		if (DatabaseUtilities.isServerMuleTradingAccount(this, login, login.getUsername())) {
			DatabaseUtilities.updateLoginStatus("server_muling", this, login.getUsername(), "LOGGED_IN", login);
		} else {
			DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN", login);
		}
		muleTrading = new ServerMuleTradingConfiguration(login, (Script) this);

		// if (login != null && login.getUsername() != null) {
		// getMuleTrading().setQuestStageStep(
		// Integer.parseInt(DatabaseUtilities.getQuestProgress(this,
		// login.getUsername(), login)));
		// }

		getMuleTrading().exchangeContext(getBot());
		getMuleTrading().onStart();
	}

	/**
	 * @return the muleTrading
	 */
	public ServerMuleTradingConfiguration getMuleTrading() {
		return muleTrading;
	}

	/**
	 * @param muleTrading
	 *            the muleTrading to set
	 */
	public void setMuleTrading(ServerMuleTradingConfiguration muleTrading) {
		this.muleTrading = muleTrading;
	}

}
