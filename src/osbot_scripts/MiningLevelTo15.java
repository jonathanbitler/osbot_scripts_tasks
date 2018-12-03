package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.Coordinates;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.MiningLevelTo15Configuration;

@ScriptManifest(author = "pim97", info = "MINING_LEVEL_TO_15", logo = "", name = "MINING_LEVEL_TO_15", version = 1.0)
public class MiningLevelTo15 extends Script {

	private MiningLevelTo15Configuration goldfarmMining;

	private LoginEvent login;

	private MandatoryEventsExecution ev = new MandatoryEventsExecution(this);

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		if (getClient().isLoggedIn()) {
			ev.fixedMode();
			ev.fixedMode2();
			ev.executeAllEvents();
		}

		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername());
			BotCommands.killProcess((MethodProvider) this, (Script) this);
		}

		// Account must have atleast 7 quest points, otherwise set it back to quesiton
		if (getQuests().getQuestPoints() < 7) {
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
					login.getUsername());
			BotCommands.killProcess((MethodProvider) this, (Script) this);
		}

		// If mining is equals or bigger than 15, then it can proceed to mining iron
		if (getSkills().getStatic(Skill.MINING) >= 15) {
			Thread.sleep(5000);
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
					login.getUsername());
			BotCommands.killProcess((MethodProvider) this, (Script) this);
		}

		// Breaking for set amount of minutes because has done a few laps
		// if (getGoldfarmMining().getDoneLaps() > 15) {
		// log("Taking a break...");
		// Thread.sleep(5000);
		// DatabaseUtilities.updateAccountBreakTill(this,
		// getGoldfarmMining().getEvent().getUsername(), 30);
		// BotCommands.killProcess((MethodProvider)this, (Script) this);
		// }

		// The loop for other stuff than tasks
		// getGoldfarmMining().onLoop();

		// The loop for tasks, may only loop when a grand exchange task
		// is not active at the moment
		// if (getGoldfarmMining().getGrandExchangeTask() == null) {
		getGoldfarmMining().getTaskHandler().taskLoop();
		// }

		return random(20, 80);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getGoldfarmMining().onPaint(g);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		login.setScript("MINING_LEVEL_TO_15");
		goldfarmMining = new MiningLevelTo15Configuration(login, (Script) this);
		getGoldfarmMining().setQuest(false);

		if (login != null && login.getUsername() != null) {
			getGoldfarmMining().setQuestStageStep(0);
			// Integer.parseInt(DatabaseUtilities.getQuestProgress(this,
			// login.getUsername())));
		}

		getGoldfarmMining().exchangeContext(getBot());
		getGoldfarmMining().onStart();
		DatabaseUtilities.updateStageProgress(this, "MINING_LEVEL_TO_15", 0,
				getGoldfarmMining().getEvent().getUsername());
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
