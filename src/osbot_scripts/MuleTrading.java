package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.MuleTradingConfiguration;

@ScriptManifest(author = "pim97", info = "MULE_TRADING", logo = "", name = "MULE_TRADING", version = 1.0)
public class MuleTrading extends Script {

	private MuleTradingConfiguration muleTrading;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}
		
		if (login.hasFinished() && !getClient().isLoggedIn()) {
			System.exit(1);
		}

		getMuleTrading().getTaskHandler().getEvents().fixedMode();
		getMuleTrading().getTaskHandler().getEvents().fixedMode2();
		getMuleTrading().getTaskHandler().getEvents().executeAllEvents();
		
		// Account must have atleast 7 quest points, otherwise set it back to quesiton
		
//		if (getQuests().getQuestPoints() < 7) {
//			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
//					login.getUsername());
//			BotCommands.killProcess((Script) this);
//		}

		//Looping for tasks and normal loop
		getMuleTrading().onLoop();
//		getMuleTrading().getTaskHandler().taskLoop();

		return random(300, 600);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		muleTrading = new MuleTradingConfiguration(login, (Script) this);

		if (login != null && login.getUsername() != null) {
			getMuleTrading()
					.setQuestStageStep(Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername())));
		}

		getMuleTrading().exchangeContext(getBot());
		getMuleTrading().onStart();
	}

	/**
	 * @return the muleTrading
	 */
	public MuleTradingConfiguration getMuleTrading() {
		return muleTrading;
	}

	/**
	 * @param muleTrading
	 *            the muleTrading to set
	 */
	public void setMuleTrading(MuleTradingConfiguration muleTrading) {
		this.muleTrading = muleTrading;
	}

}
