package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.SheepShearerConfiguration;

@ScriptManifest(author = "pim97", info = "QUEST_SHEEP_SHEARER", logo = "", name = "QUEST_SHEEP_SHEARER", version = 1.0)
public class SheepShearer extends Script {

	private SheepShearerConfiguration sheepShearer;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}
		

		getSheepShearer().getTaskHandler().getEvents().fixedMode();
		getSheepShearer().getTaskHandler().getEvents().fixedMode2();
		getSheepShearer().getTaskHandler().getEvents().executeAllEvents();
		
		// TODO Auto-generated method stub
		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		log(getSheepShearer().getQuestProgress());

		if (getSheepShearer().getQuestProgress() == 21 || closeQuestCompleted != null) {
			log("Successfully completed quest sheep shearer");
			if (closeQuestCompleted != null) {
				closeQuestCompleted.interact();
			}
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0,
					login.getUsername());
			DatabaseUtilities.updateAccountBreakTill(this, getSheepShearer().getEvent().getUsername(), 60);
			BotCommands.killProcess((Script) this);
			return random(500, 600);
		}

		getSheepShearer().getTaskHandler().taskLoop();

		return random(500, 600);
	}

	@Override
	public void onPaint(Graphics2D g) {
//		getSheepShearer().getTrailMouse().draw(g);
		getMouse().setDefaultPaintEnabled(true);
	}
	
	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		sheepShearer = new SheepShearerConfiguration(login, (Script) this);

		if (login != null && login.getUsername() != null) {
			getSheepShearer()
					.setQuestStageStep(Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername())));
		}
		log("Quest progress: " + getSheepShearer().getQuestStageStep());

		getSheepShearer().exchangeContext(getBot());
		getSheepShearer().onStart();
		// getSheepShearer().getTaskHandler().decideOnStartTask();
	}

	/**
	 * @return the sheepShearer
	 */
	public SheepShearerConfiguration getSheepShearer() {
		return sheepShearer;
	}

	/**
	 * @param sheepShearer
	 *            the sheepShearer to set
	 */
	public void setSheepShearer(SheepShearerConfiguration sheepShearer) {
		this.sheepShearer = sheepShearer;
	}

}
