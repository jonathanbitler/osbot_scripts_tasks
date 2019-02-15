package osbot_scripts.qp7.progress;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class MuleTradingConfiguration extends QuestStep {

	public MuleTradingConfiguration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MULE_TRADING, event, script, false);
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

	private ThreadDemo demo;

	private TradingBody tBody;

	@Override
	public void onLoop() throws InterruptedException {

		if (getEvent().hasFinished() && !isLoggedIn()) {
			BotCommands.killProcess(this, getScript(), "BECAUSE NOT LOGGED IN 01 MULE TRADING", getEvent());
		}

		log("Running the side loop..");

		tBody.walkToGrandExchange();

		if (tBody.tradingDone) {
			if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {
				// if (update) {
				DatabaseUtilities.updateStageProgress(this,
						RandomUtil.gextNextAccountStage(this, getEvent()).name().toUpperCase(), 0,
						getEvent().getUsername(), getEvent());
				DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), 0, getEvent());
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getEmailTradeWith(), getEvent());
				// }
				BotCommands.killProcess(this, getScript(), "BECAUSE OF DONE WITH MULE TRADING", getEvent());
				getScript().stop();
			} else {
				// Successfull trading
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername(), getEvent());

				tBody.getAccountValueAndUpdateInDatabase("");
				tBody.handleRetrades(175);
			}
			return;
		}

		log(getEvent().getAccountStage());

		tBody.tries++;

		if (tBody.tries > (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING") ? 300 : 450)) {
			tBody.tradingDone = true;
			// update = true;
			log("Failed to trade it over");
		}

		if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {
			tBody.handleGeneralFirst("MULE-TRADING", "");
		}

		// The mule itself
		else

		{
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

	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub

	}

}
