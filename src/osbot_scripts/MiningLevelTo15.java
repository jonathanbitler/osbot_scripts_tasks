package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.MiningLevelTo15Configuration;

@ScriptManifest(author = "pim97", info = "MINING_LEVEL_TO_15", logo = "", name = "MINING_LEVEL_TO_15", version = 1.0)
public class MiningLevelTo15 extends Script {

	private MiningLevelTo15Configuration goldfarmMining;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		getGoldfarmMining().getTaskHandler().getEvents().fixedMode();
		getGoldfarmMining().getTaskHandler().getEvents().fixedMode2();
		getGoldfarmMining().getTaskHandler().getEvents().executeAllEvents();
		
		
		// Account must have atleast 7 quest points, otherwise set it back to quesiton
		if (getQuests().getQuestPoints() < 7) {
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
					login.getUsername());
			BotCommands.killProcess((Script) this);
		}
		
		// If mining is equals or bigger than 15, then it can proceed to mining iron
		if (getSkills().getStatic(Skill.MINING) >= 15) {
			Thread.sleep(5000);
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
					login.getUsername());
			BotCommands.killProcess((Script) this);
		}
		
		//Breaking for set amount of minutes because has done a few laps
		if (getGoldfarmMining().getDoneLaps() > 15) {
			log("Taking a break...");
			Thread.sleep(5000);
			DatabaseUtilities.updateAccountBreakTill(this, getGoldfarmMining().getEvent().getUsername(), 30);
			BotCommands.killProcess((Script) this);
		}

		// The loop for other stuff than tasks
		getGoldfarmMining().onLoop();

		// The loop for tasks, may only loop when a grand exchange task
		// is not active at the moment
		if (getGoldfarmMining().getGrandExchangeTask() == null) {
			getGoldfarmMining().getTaskHandler().taskLoop();
		}

		return random(0, 1);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		goldfarmMining = new MiningLevelTo15Configuration(login, (Script) this);

		if (login != null && login.getUsername() != null) {
			getGoldfarmMining()
					.setQuestStageStep(Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername())));
		}

		getGoldfarmMining().exchangeContext(getBot());
		getGoldfarmMining().onStart();
	}

	/**
	 * @return the goldfarmMining
	 */
	public MiningLevelTo15Configuration getGoldfarmMining() {
		return goldfarmMining;
	}

	/**
	 * @param goldfarmMining
	 *            the goldfarmMining to set
	 */
	public void setGoldfarmMining(MiningLevelTo15Configuration goldfarmMining) {
		this.goldfarmMining = goldfarmMining;
	}

}
