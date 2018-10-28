package osbot_scripts;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.CookingsAssistant;
import osbot_scripts.task.Task;

@ScriptManifest(author = "pim97", info = "CookingAssistantQuest", logo = "", name = "QUEST_COOK_ASSISTANT", version = 1.0)
public class CookingAssistantQuest extends Script {

	private CookingsAssistant cooksAssistant;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		// TODO Auto-generated method stub
		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		log(getCooksAssistant().getQuestProgress());
		if (getCooksAssistant().getQuestProgress() == 2 || closeQuestCompleted != null) {
			log("Successfully completed quest cooks assistant");
			if (closeQuestCompleted != null) {
				closeQuestCompleted.interact();
			}
			
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage().name(), 0,
					login.getUsername());
			BotCommands.killProcess((Script)this);
			return random(500, 600);
		}

		getCooksAssistant().getTaskHandler().taskLoop();

		return random(500, 600);
	}

	private LoginEvent loginEvent;

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		cooksAssistant = new CookingsAssistant(4626, 29, login, (Script)this);

		if (login != null && login.getUsername() != null) {
			getCooksAssistant()
					.setQuestStageStep(Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername())));
		}
		log("Quest progress: " + getCooksAssistant().getQuestStageStep());

		getCooksAssistant().exchangeContext(getBot());
		getCooksAssistant().onStart();
//		getCooksAssistant().getTaskHandler().decideOnStartTask();
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
