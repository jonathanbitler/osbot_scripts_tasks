package osbot_scripts;

import java.awt.Graphics2D;
import java.io.IOException;

import org.osbot.rs07.api.ui.RS2Widget;
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
import osbot_scripts.qp7.progress.DoricsQuestConfig;
import osbot_scripts.scripttypes.ScriptType;
import osbot_scripts.scripttypes.types.MiningType;

@ScriptManifest(author = "pim97", info = "QUEST_DORICS_QUEST", logo = "", name = "QUEST_DORICS_QUEST", version = 1.0)
public class DoricsQuest extends Script {

	private DoricsQuestConfig goblinsDiplomacy;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		if (getGoblinsDiplomacy().isLoggedIn()) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);
			ev.fixedMode();
			ev.fixedMode2();
			ev.executeAllEvents();
		}

		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername(), login);
			BotCommands.killProcess((MethodProvider) this, (Script) this, "SHOULD BE ON TUT ISLAND DORICS", login);
		}

		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		log(getGoblinsDiplomacy().getQuestProgress());

		if (getGoblinsDiplomacy().getQuestProgress() == 100 || closeQuestCompleted != null) {
			log("Successfully completed goblins diplomacy");
			if (closeQuestCompleted != null) {
				closeQuestCompleted.interact();
			}

			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this, login).name(), 0,
					login.getUsername(), login);
			DatabaseUtilities.updateAccountBreakTill(this, getGoblinsDiplomacy().getEvent().getUsername(), 60, login);
			BotCommands.killProcess((MethodProvider) this, (Script) this, "ALREADY COMPLETED THE QUEST DORICS", login);
			return random(500, 600);
		}

		if (login.hasFinished()) {
			getGoblinsDiplomacy().onLoop();
			try {
				getGoblinsDiplomacy().getTaskHandler().taskLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return random(500, 600);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		if (login != null) {
			login.setScript("QUEST_DORICS_QUEST");
//			DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN", login);
		}
		goblinsDiplomacy = new DoricsQuestConfig(3893, 31, login, (Script) this);
		goblinsDiplomacy.setScriptAbstract(new MiningType());

		if (login != null && login.getUsername() != null) {
			// if (!Config.TEST) {
			getGoblinsDiplomacy().setQuestStageStep(
					Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername(), login)));
			// }
		}

		log("Quest progress: " + getGoblinsDiplomacy().getQuestStageStep());

		getGoblinsDiplomacy().exchangeContext(getBot());
		getGoblinsDiplomacy().onStart();
		// getCooksAssistant().getTaskHandler().decideOnStartTask();
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void onPaint(Graphics2D g) {
		// getCooksAssistant().getTrailMouse().draw(g);
		getMouse().setDefaultPaintEnabled(true);
	}

	/**
	 * @return the cooksAssistant
	 */
	public DoricsQuestConfig getGoblinsDiplomacy() {
		return goblinsDiplomacy;
	}

	/**
	 * @param cooksAssistant
	 *            the cooksAssistant to set
	 */
	public void setCooksAssistant(DoricsQuestConfig cooksAssistant) {
		this.goblinsDiplomacy = cooksAssistant;
	}

}
