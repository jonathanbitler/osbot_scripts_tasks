package osbot_scripts.qp7.progress;

import java.io.IOException;

import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.GEPrice;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class ServerMuleTradingConfiguration extends QuestStep {

	public ServerMuleTradingConfiguration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.UNKNOWN, event, script, false);
		// TODO Auto-generated constructor stub
		tBody = new TradingBody(this);
	}

	private TradingBody tBody;

	private String accountStatus;

	private ThreadDemo demo;

	private int coinsAmount = -1;

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

	@Override
	public void onLoop() throws InterruptedException {
		if (getAccountStatus() == null) {
			setAccountStatus(DatabaseUtilities.getAccountStatus(this, getEvent().getUsername(), getEvent()));

			if (getAccountStatus() == null) {
				setAccountStatus(DatabaseUtilities.getAccountStatus("server_muling", this, getEvent().getUsername(),
						getEvent()));

				if (getAccountStatus() == null) {
					return;
				}
			}
		}

		if (getEvent().hasFinished() && !isLoggedIn()) {
			BotCommands.killProcess(this, getScript(), "BECAUSE NOT LOGGED IN 01 MULE TRADING", getEvent());
		}

		log("Running the side loop..");

		System.out.println("STATUS: " + getAccountStatus());

		tBody.walkToGrandExchange();

		// Not the mule

		if (tBody.tradingDone) {

			if (getAccountStatus().equalsIgnoreCase("SUPER_MULE")) {
				DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), 0, getEvent());
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername(), getEvent());
				DatabaseUtilities.updateStageProgress("server_muling", this, "UNKNOWN", 0,
						getEvent().getEmailTradeWith(), getEvent());

				DatabaseUtilities.insertLoggingMessage(this, getEvent(), "GOLD_TRANSFER",
						Integer.toString(coinsAmount));

				BotCommands.killProcess(this, getScript(), "BECAUSE OF DONE WITH SUPER MULE TRADING", getEvent());
				getScript().stop();
			} else if (getAccountStatus().equalsIgnoreCase("SERVER_MULE")) {
				// Successfull trading
				DatabaseUtilities.updateStageProgress("server_muling", this, "UNKNOWN", 0, getEvent().getUsername(),
						getEvent());

				tBody.getAccountValueAndUpdateInDatabase("server_muling");
				tBody.handleRetrades(5);
			}
			return;
		}

		log(getEvent().getAccountStage());

		tBody.tries++;

		if (tBody.tries > (getAccountStatus().equalsIgnoreCase("SUPER_MULE") ? 300 : 300)) {
			tBody.tradingDone = true;
			// update = true;
			log("Failed to trade it over");
		}

		if (getAccountStatus().equalsIgnoreCase("SUPER_MULE")) {
			tBody.handleGeneralFirst("UNKNOWN", "");

			if (coinsAmount <= 0) {
				try {
					coinsAmount = (int) ((getInventory().getAmount("Coins"))
							+ (getInventory().getAmount("Clay") * new GEPrice().getBuyingPrice(434)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// The mule itself
		else if (getAccountStatus().equalsIgnoreCase("SERVER_MULE")) {
			tBody.handleGeneralSecond(false);
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
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}
