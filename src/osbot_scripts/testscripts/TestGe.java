package osbot_scripts.testscripts;

import java.awt.Graphics2D;
import java.io.IOException;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.login.LoginHandler;

@ScriptManifest(author = "pim97", info = "TEST_GE", logo = "", name = "TEST_GE", version = 1.0)
public class TestGe extends Script {

	private LoginEvent login;

	private Test test;

	@Override
	public int onLoop() throws InterruptedException {
		try {
			test.getTaskHandler().taskLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stop(false);
		return random(1000, 2000);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		if (!getClient().isLoggedIn()) {
			login = LoginHandler.login(this, getParameters());
			login.setScript("TEST_GE");
		}
		test = new Test(0, 0, null, (Script) this);
		test.exchangeContext(getBot());
		test.onStart();
		// DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN",
		// login);
	}

}
