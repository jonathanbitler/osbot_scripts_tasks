package osbot_scripts.qp7.progress;

import java.awt.Color;

import org.osbot.rs07.api.Client.LoginState;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.util.Sleep;

public class ThreadDemo extends MethodProvider implements Runnable {

	private boolean run = true;

	public String paramaters;

	private LoginEvent loginEvent;

	public LoginEvent getLoginEvent() {
		return loginEvent;
	}

	public void setLoginEvent(LoginEvent loginEvent) {
		this.loginEvent = loginEvent;
	}

	private boolean isWrongEmail() {
		// return getColorPicker().isColorAt(422, 232, new Color(255, 255, 0));
		return getColorPicker().isColorAt(541, 216, new Color(255, 255, 0));
	}

	public boolean isLoggedIn() {
		return //
		isHopping() || //
				getClient().getLoginStateValue() == 30 || //
				getClient().isLoggedIn() || //
				isLoading();
	}

	public boolean isHopping() {
		return //
		getClient().getLoginStateValue() == 45 || //
				getClient().getLoginStateValue() == 25;//
	}

	public boolean isLoading() {
		return //
		getClient().getLoginState() == LoginState.LOADING || //
				getClient().getLoginState() == LoginState.LOADING_MAP;
	}

	// private final CachedWidget isDeadInterface = new CachedWidget("Never show me
	// this again");

	private boolean relog = false;

	private int[] location = new int[6];

	private boolean animated = false;

	private int ticker = 0;

	@Override
	public void run() {
		while (run) {
			try {

				if (getClient().isLoggedIn()) {
					if (myPlayer().isAnimating() || myPlayer().isMoving()) {
						animated = true;
						ticker = 0;
						location[0] = myPlayer().getX();
						location[1] = myPlayer().getY();
						location[2] = myPlayer().getZ();
					} else {
						animated = false;
					}

					if (location[0] == myPlayer().getX() && location[1] == myPlayer().getY()
							&& location[2] == myPlayer().getZ() && !animated) {

						if (ticker > 120) {
							log("Player standing still too long! Restarting right now!");
							Thread.sleep(20_000);
							System.exit(1);
						}

						ticker++;
						log("Current ticker is: " + ticker);
					}
				}

				// Account password is wrong when not logged in
				if (!isLoggedIn() && isWrongEmail()) {
					log("Account password is wrong, setting to invalid password");
					DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", getLoginEvent().getUsername(),
							getLoginEvent());
					BotCommands.waitBeforeKill(this, "BECAUSE OF INVALID PASSWORD");
				}

				// if (!isLoggedIn() && !relog) {
				// relog = true;
				//// if (paramaters != null) {
				//// loginEvent = LoginHandler.login(this, paramaters);
				//// }
				// getBot().addLoginListener(loginEvent);
				// execute(loginEvent);
				// }
				//
				// Is logged in and loginevent finished
				if (isLoggedIn() && loginEvent != null && loginEvent.getUsername() != null
						&& loginEvent.hasFinished()) {

					if (loginEvent.getScript() != null) {
						// log("Seperate thread is currently running.. " + loginEvent.getScript());

						// Resizable mode when logged in
						if (!loginEvent.getScript().equalsIgnoreCase("TUT_ISLAND")) {
							MandatoryEventsExecution ev = new MandatoryEventsExecution(this, getLoginEvent());
							ev.fixedMode();
							ev.fixedMode2();
						}

						// if
						// (loginEvent.getScript().equalsIgnoreCase(AccountStage.MINING_IRON_ORE.name())
						// ||
						// loginEvent.getScript().equalsIgnoreCase(AccountStage.MINING_LEVEL_TO_15.name()))
						// {
						// if (!MiningLevelTo15Configuration.MINING_ZONE.contains(myPlayer())
						// && !MiningLevelTo15Configuration.WHOLE_ACTION_AREA.contains(myPlayer())) {
						// log("Isn't at correct position!? Restarting..");
						// BotCommands.waitBeforeKill();
						// }
						// }
					}

				}

				// Prevent stuck on login
				if (loginEvent != null && loginEvent.hasFinished() && !isLoggedIn()) {
					// log("Isn't logged in!?");
					BotCommands.waitBeforeKill(this, "BECAUSE OF NOT LOGGED IN RIGHT NOW E01");
				}

				// Death interface click
				if (getWidgets() != null && getClient().isLoggedIn()) {
					RS2Widget close = getWidgets().get(153, 71);
					if (close != null && close.isVisible()) {
						close.interact();
					}

					RS2Widget close1 = getWidgets().get(153, 107);
					if (close1 != null && close1.isVisible()) {
						close1.interact();
					}

					RS2Widget close3 = getWidgets().get(153, 108);
					if (close3 != null && close3.isVisible()) {
						close3.interact();
					}
				}

				Thread.sleep(2_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		run = false;
	}

}