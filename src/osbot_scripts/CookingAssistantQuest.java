package osbot_scripts;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.CookingsAssistant;
import osbot_scripts.task.Task;

@ScriptManifest(author = "pim97", info = "CookingAssistantQuest", logo = "", name = "QUEST_COOK_ASSISTANT", version = 1.0)
public class CookingAssistantQuest extends Script {

	private CookingsAssistant cooksAssistant = new CookingsAssistant(4626, 29);
	
	private LoginEvent login;
	

	@Override
	public int onLoop() throws InterruptedException {

		// TODO Auto-generated method stub
		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		if (getCooksAssistant().getQuestProgress() == 3 || closeQuestCompleted != null) {
			log("Successfully completed quest cooks assistant");
			closeQuestCompleted.interact();
			DatabaseUtilities.updateStageProgress(this, AccountStage.QUEST_ROMEO_AND_JULIET.name(), 0, login.getUsername());
			stop();
			return -1;
		}

		for (Task task : getCooksAssistant().getCookingAssistantTask()) {

			if (getCooksAssistant().getCurrentTask() == null) {
				log("System couldnt find a next action, logging out");
				break;
			}
			if (task.requiredConfigQuestStep() == getCooksAssistant().getQuestProgress()) {
				// Waiting for task to finish
				log("finish: " + getCooksAssistant().getCurrentTask().finished());
				while (!getCooksAssistant().getCurrentTask().finished()) {
					// if (!getCooksAssistant().isInQuestCutscene()) {
					getCooksAssistant().getCurrentTask().loop();
					// }
					log("performing task" + getCooksAssistant().getCurrentTask().getClass().getSimpleName());
					Thread.sleep(1000, 1500);
				}

				log("On next task: " + task.scriptName());
				getCooksAssistant().setCurrentTask(task);
			}
		}

		return random(500, 600);
	}

	private LoginEvent loginEvent;

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());

		getCooksAssistant().exchangeContext(getBot());
		getCooksAssistant().onStart();
		getCooksAssistant().decideOnStartTask();
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
