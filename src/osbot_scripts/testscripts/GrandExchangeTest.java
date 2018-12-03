package osbot_scripts.testscripts;

import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.LinkedList;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.qp7.progress.CookingsAssistant;

@ScriptManifest(author = "pim97", info = "GE_TEST", logo = "", name = "GE_TEST", version = 1.0)
public class GrandExchangeTest extends Script {

	private CookingsAssistant cooksAssistant;

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {

		// getCooksAssistant().getTaskHandler().taskLoop();

		return random(500, 600);
	}

	private LoginEvent loginEvent;

	private static final LinkedList<Position> TEST = new LinkedList<Position>(
			Arrays.asList(
					new Position(3185, 3370, 0),
					new Position(3182, 3363, 0),
					new Position(3175, 3367, 0)
//					new Position(3181, 3371, 0), new Position(3190, 3366, 0), new Position(3191, 3365, 0),
//					new Position(3190, 3363, 0), new Position(3186, 3363, 0), new Position(3186, 3361, 0)
					)
			);

	
	public boolean walkExact(Position position, boolean disableRun) {
        WalkingEvent event = new WalkingEvent(position);
		event.setEnergyThreshold(101);
        event.setMinDistanceThreshold(1);
        if (disableRun && getSettings().isRunning()) {
			getSettings().setRunning(false);
		} else if (!disableRun && !getSettings().isRunning()) {
			getSettings().setRunning(true);
		}
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return execute(event).hasFinished();
    }
	
	@Override
	public void onStart() throws InterruptedException {

		walkExact(TEST.get(0), true);
		walkExact(TEST.get(1), true);
		walkExact(TEST.get(2), false);

		// login = LoginHandler.login(this, getParameters());
		// cooksAssistant = new CookingsAssistant(4626, 29, login, (Script) this);
		//
		// if (login != null && login.getUsername() != null) {
		// getCooksAssistant()
		// .setQuestStageStep(0);
		// }
		// log("Quest progress: " + getCooksAssistant().getQuestStageStep());
		//
		// getCooksAssistant().exchangeContext(getBot());
		// getCooksAssistant().onStart();
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
