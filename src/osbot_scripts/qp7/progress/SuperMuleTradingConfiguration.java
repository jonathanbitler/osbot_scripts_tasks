package osbot_scripts.qp7.progress;

import java.util.HashMap;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.WidgetActionFilter;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

public class SuperMuleTradingConfiguration extends QuestStep {

	public SuperMuleTradingConfiguration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.UNKNOWN, event, script, false);
		// TODO Auto-generated constructor stub
		tBody = new TradingBody(this);
	}

	@Override
	public void onStart() {
		tBody.exchangeContext(getBot());
		tBody.timeout = System.currentTimeMillis();

		demo = new ThreadDemo();
		demo.exchangeContext(this.getBot());
		demo.setLoginEvent(getEvent());
		new Thread(demo).start();
	}

	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub

	}

	private String accountStatus;

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	private ThreadDemo demo;

	private TradingBody tBody;

	@Override
	public void onLoop() throws InterruptedException {
		if (getAccountStatus() == null) {
			setAccountStatus(DatabaseUtilities.getAccountStatus(this, getEvent().getUsername(), getEvent()));
			log("set acc status to: " + getAccountStatus());

			if (getAccountStatus() == null) {
				log("account status was null");
				return;
			}
		}

		if (getEvent().hasFinished() && !isLoggedIn()) {
			BotCommands.killProcess(this, getScript(), "BECAUSE NOT LOGGED IN 01 MULE TRADING", getEvent());
		}

		log("Running the side loop..");

		System.out.println("STATUS: " + getAccountStatus());
		if (tBody.tradingDone) {

			if (getAccountStatus().equalsIgnoreCase("MULE")) {
				// if (update) {
				DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), 0, getEvent());
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername(), getEvent());
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getEmailTradeWith(), getEvent());
				// }
				BotCommands.killProcess(this, getScript(), "BECAUSE OF DONE WITH SUPER MULE TRADING", getEvent());
				getScript().stop();
			} else if (getAccountStatus().equalsIgnoreCase("SUPER_MULE")) {
				// Successfull trading
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername(), getEvent());

				tBody.getAccountValueAndUpdateInDatabase("");
				tBody.handleRetrades(125);
			}
			return;

		}

		log(getEvent().getAccountStage());

		tBody.tries++;

		if (tBody.tries > (getAccountStatus().equalsIgnoreCase("MULE") ? 300 : 300)) {
			tBody.tradingDone = true;
			// update = true;
			log("Failed to trade it over");
		}

		if (getAccountStatus().equalsIgnoreCase("MULE")) {
			tBody.handleGeneralFirst("UNKNOWN", "");
		}

		// The mule itself
		else if (getAccountStatus().equalsIgnoreCase("SUPER_MULE")) {
			String toTradeWith = DatabaseUtilities.getAccountToTradeWith(this, getEvent().getUsername(), getEvent());

			if (toTradeWith != null) {
				String otherPlayersStatus = DatabaseUtilities.getAccountStatusByIngameName("server_muling", this,
						toTradeWith, getEvent());

				if (otherPlayersStatus != null && otherPlayersStatus.equalsIgnoreCase("SERVER_MULE")) {
					BotCommands.killProcess(this, getScript(), "BECAUSE ABOUT TO TRADE WITH SERVER MULE", getEvent());
					getScript().stop();
				}
			}

			tBody.handleGeneralSecond(true);
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

}
