package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
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
import osbot_scripts.framework.AccountStage;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.RomeoAndJuliet;

@ScriptManifest(author = "pim97", info = "RomeoAndJulietQuest", logo = "", name = "QUEST_ROMEO_AND_JULIET", version = 1.0)
public class RomeoAndJulietQuest extends Script {

	private RomeoAndJuliet romeoAndJuliet;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		if (getDialogues().isPendingContinuation()) {
			getDialogues().clickContinue();
		}

		if (getClient().isLoggedIn()) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(this);
			ev.fixedMode();
			ev.fixedMode2();
			ev.executeAllEvents();
		}
		
		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername());
			BotCommands.killProcess((MethodProvider)this, (Script) this);
		}
		
		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		if (getRomeoAndJuliet().getQuestProgress() == 100 || closeQuestCompleted != null) {
			log("Successfully completed quest romeo & juliet");
			if (closeQuestCompleted != null) {
				closeQuestCompleted.interact();
			}
			Thread.sleep(5000);
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this).name(), 0, login.getUsername());
			DatabaseUtilities.updateAccountBreakTill(this, getRomeoAndJuliet().getEvent().getUsername(), 60);

			BotCommands.killProcess((MethodProvider)this, (Script) this);
			return random(500, 600);
		}

		getRomeoAndJuliet().getTaskHandler().taskLoop();

		return random(500, 600);
	}

	@Override
	public void onPaint(Graphics2D g) {
//		g.drawString("Name Task:" + getRomeoAndJuliet().getTaskHandler().getCurrentTask().getClass().getSimpleName()
//				+ " " + "Quest Progress: " + getRomeoAndJuliet().getQuestProgress(), 58, 400);
//		getRomeoAndJuliet().getTrailMouse().draw(g);
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		// TODO Auto-generated method stub
		login = LoginHandler.login(this, getParameters());
		login.setScript("QUEST_ROMEO_AND_JULIET");
		romeoAndJuliet = new RomeoAndJuliet(login, (Script)this);

		if (login != null && login.getUsername() != null) {
			getRomeoAndJuliet().setQuestStageStep(Integer.parseInt(
					DatabaseUtilities.getQuestProgress(this, login.getUsername())));
		}
		
		log("Quest progress: " + getRomeoAndJuliet().getQuestStageStep());
		
		getRomeoAndJuliet().exchangeContext(getBot());
		getRomeoAndJuliet().onStart();
		// getRomeoAndJuliet().getTaskHandler().decideOnStartTask();
		// getRomeoAndJuliet().decideOnStartTask();

	}

	/**
	 * @return the romeoAndJuliet
	 */
	public RomeoAndJuliet getRomeoAndJuliet() {
		return romeoAndJuliet;
	}

	/**
	 * @param romeoAndJuliet
	 *            the romeoAndJuliet to set
	 */
	public void setRomeoAndJuliet(RomeoAndJuliet romeoAndJuliet) {
		this.romeoAndJuliet = romeoAndJuliet;
	}

}
