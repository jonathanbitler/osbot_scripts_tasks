package osbot_scripts;

import java.awt.Graphics2D;
import java.io.IOException;

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

		if (getRomeoAndJuliet().isLoggedIn()) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(this, login);
			ev.fixedMode();
			ev.fixedMode2();
			ev.executeAllEvents();
		}

		if (Coordinates.isOnTutorialIsland(this)) {
			DatabaseUtilities.updateStageProgress(this, "TUT_ISLAND", 0, login.getUsername(), login);
			BotCommands.killProcess((MethodProvider) this, (Script) this, "SHOULD BE ON TUTORIAL ISLAND ROMEO", login);
		}

		RS2Widget closeQuestCompleted = getWidgets().get(277, 15);
		if (getRomeoAndJuliet().getQuestProgress() == 100 || closeQuestCompleted != null) {
			log("Successfully completed quest romeo & juliet");
			if (closeQuestCompleted != null) {
				closeQuestCompleted.interact();
			}
			Thread.sleep(5000);
			DatabaseUtilities.updateStageProgress(this, RandomUtil.gextNextAccountStage(this, login).name(), 0,
					login.getUsername(), login);
			DatabaseUtilities.updateAccountBreakTill(this, getRomeoAndJuliet().getEvent().getUsername(), 60, login);

			BotCommands.killProcess((MethodProvider) this, (Script) this, "ALREADY COMPLETED QUEST ROMEO", login);
			return random(500, 600);
		}

		if (login.hasFinished()) {
			try {
				getRomeoAndJuliet().getTaskHandler().taskLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return random(500, 600);
	}

	@Override
	public void onPaint(Graphics2D g) {
		// g.drawString("Name Task:" +
		// getRomeoAndJuliet().getTaskHandler().getCurrentTask().getClass().getSimpleName()
		// + " " + "Quest Progress: " + getRomeoAndJuliet().getQuestProgress(), 58,
		// 400);
		// getRomeoAndJuliet().getTrailMouse().draw(g);
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		// TODO Auto-generated method stub
		login = LoginHandler.login(this, getParameters());
		login.setScript("QUEST_ROMEO_AND_JULIET");
		DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN", login);
		romeoAndJuliet = new RomeoAndJuliet(login, (Script) this);

		if (login != null && login.getUsername() != null) {
			getRomeoAndJuliet().setQuestStageStep(
					Integer.parseInt(DatabaseUtilities.getQuestProgress(this, login.getUsername(), login)));
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
