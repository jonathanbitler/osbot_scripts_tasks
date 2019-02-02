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
import osbot_scripts.qp7.progress.CookingsAssistant;

@ScriptManifest(author = "pim97", info = "CookingAssistantQuest", logo = "", name = "QUEST_COOK_ASSISTANT", version = 1.0)
public class CookingAssistantQuest extends Script {

	private CookingsAssistant cooksAssistant;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {
		try {
			if (getDialogues().isPendingContinuation()) {
				getDialogues().clickContinue();
			}

			if (getCooksAssistant().isLoggedIn()) {
				MandatoryEventsExecution ev = new MandatoryEventsExecution(getCooksAssistant(), login);
				ev.fixedMode();
				ev.fixedMode2();
				ev.executeAllEvents();
			}

			if (Coordinates.isOnTutorialIsland(this)) {
				DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername(), login);
				BotCommands.killProcess((MethodProvider) this, (Script) this, "SHOULD BE ON TUT ISLAND COOKS", login);
			}

			// TODO Auto-generated method stub
			RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
			log(getCooksAssistant().getQuestProgress());
			if (getCooksAssistant().getQuestProgress() == 2 || closeQuestCompleted != null) {
				log("Successfully completed quest cooks assistant");
				if (closeQuestCompleted != null) {
					closeQuestCompleted.interact();
				}

				DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this, login).name(), 0,
						login.getUsername(), login);
				DatabaseUtilities.updateAccountBreakTill(this, getCooksAssistant().getEvent().getUsername(), 60, login);
				BotCommands.killProcess((MethodProvider) this, (Script) this, "ALREADY COMPLETED THE QUEST COOKS",
						login);
				return random(500, 600);
			}

			if (login.hasFinished()) {
				try {
					getCooksAssistant().getTaskHandler().taskLoop();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log(DatabaseUtilities.exceptionToString(e, this, login));
		}
		return random(500, 600);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		login.setScript("QUEST_COOK_ASSISTANT");
		cooksAssistant = new CookingsAssistant(4626, 29, login, (Script) this);
		// DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN",
		// login);

		if (login != null && login.getUsername() != null) {
			getCooksAssistant().setQuestStageStep(
					Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername(), login)));
		}

		log("Quest progress: " + getCooksAssistant().getQuestStageStep());

		getCooksAssistant().exchangeContext(getBot());
		getCooksAssistant().onStart();
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
	public CookingsAssistant getCooksAssistant() {
		return cooksAssistant;
	}

	/**
	 * @param cooksAssistant
	 *            the cooksAssistant to set
	 */
	public void setCooksAssistant(CookingsAssistant cooksAssistant) {
		this.cooksAssistant = cooksAssistant;
	}

}
