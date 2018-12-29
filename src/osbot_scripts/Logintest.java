package osbot_scripts;

import java.awt.Graphics2D;

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
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.MuleTradingConfiguration;

@ScriptManifest(author = "pim97", info = "LOGIN_TEST", logo = "", name = "LOGIN_TEST", version = 1.0)
public class Logintest extends Script {

	private LoginEvent login;

	@Override
	public int onLoop() throws InterruptedException {
		return random(1000, 2000);
	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	@Override
	public void onStart() throws InterruptedException {
		login = LoginHandler.login(this, getParameters());
		login.setScript("LOGIN_TEST");
		DatabaseUtilities.updateLoginStatus(this, login.getUsername(), "LOGGED_IN", login);
	}

}
