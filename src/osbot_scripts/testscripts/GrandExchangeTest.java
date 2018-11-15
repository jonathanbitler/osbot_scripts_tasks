package osbot_scripts.testscripts;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.util.Sleep;

@ScriptManifest(author = "pim97@github & dormic@osbot", info = "ge", logo = "", name = "GE_TEST", version = 0)
public class GrandExchangeTest extends Script {

	private GrandExchangeTask task;

	/**
	 * Loops
	 */
	@Override
	public int onLoop() throws InterruptedException {

		// MandatoryEventsExecution e = new MandatoryEventsExecution(this);
		// e.fixedMode();
		// e.fixedMode2();

		// if (!getTask().finished()) {
		// getTask().loop();
		// } else {
		// log("task was finished!");
		// }

		return random(800, 1600);
	}

	@Override
	public void onStart() throws InterruptedException {

		// log(getChatbox().contains(Chatbox.MessageType.GAME, "You operate the hopper.
		// The grain slides down the chute."));

		// e.executeAllEvents();

		// setTask(new GrandExchangeTask(this, new BankItem[] {
		//// new BankItem("Iron pickaxe", 1267, 1, 1000, false)
		// },
		// new BankItem[] { new BankItem("Clay", 434, 3, 2, true) }, null, (Script)
		// this));
	}

	@Override
	public void onExit() throws InterruptedException {

	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	/**
	 * @return the task
	 */
	public GrandExchangeTask getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(GrandExchangeTask task) {
		this.task = task;
	}

}
