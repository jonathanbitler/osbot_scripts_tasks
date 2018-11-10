package osbot_scripts.events;

import java.awt.Color;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.constants.ResponseCode;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.input.mouse.RectangleDestination;
import org.osbot.rs07.listener.LoginResponseCodeListener;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.database.DatabaseUtilities;

public final class LoginEvent extends Event implements LoginResponseCodeListener {

	private final String username, password;

	private String accountStage, tradeWith;

	private int pid;

	public LoginEvent(final String username, final String password, int pid, String accountStage) {
		this.username = username;
		this.password = password;
		this.pid = pid;
		this.accountStage = accountStage;
		setAsync();
	}

	@Override
	public final int execute() throws InterruptedException {
		if (!getBot().isLoaded()) {
			return 1000;
		} else if ((getClient().isLoggedIn() && getLobbyButton() == null)) {
			getBot().getScriptExecutor().resume();
			setFinished();
		} else if (getClient().isLoggedIn() && getLobbyButton() != null) {
			clickHereToPlayButton();
		} else if (!getBot().getScriptExecutor().isPaused()) {
			getBot().getScriptExecutor().pause();
		} else if (getLobbyButton() != null) {
			clickLobbyButton();
		} else if (isOnWorldSelectorScreen()) {
			cancelWorldSelection();
		} else if (!isPasswordEmpty()) {
			clickCancelLoginButton();
		} else {
			login();
		}
		return random(100, 150);
	}

	private boolean isOnWorldSelectorScreen() {
		return getColorPicker().isColorAt(50, 50, Color.BLACK);
	}

	private void cancelWorldSelection() {
		if (getMouse().click(new RectangleDestination(getBot(), 712, 8, 42, 8))) {
			new ConditionalSleep(3000) {
				@Override
				public boolean condition() throws InterruptedException {
					return !isOnWorldSelectorScreen();
				}
			}.sleep();
		}
	}

	private boolean isPasswordEmpty() {
		return !getColorPicker().isColorAt(350, 260, Color.WHITE);
	}

	private boolean clickCancelLoginButton() {
		return getMouse().click(new RectangleDestination(getBot(), 398, 308, 126, 27));
	}

	private void login() {
		switch (getClient().getLoginUIState()) {
		case 0:
			clickExistingUsersButton();
			break;
		case 1:
			clickLoginButton();
			break;
		case 2:
			enterUserDetails();
			break;
		case 3:
			clickTryAgainButton();
			break;
		}
	}

	private void clickExistingUsersButton() {
		getMouse().click(new RectangleDestination(getBot(), 400, 280, 120, 20));
	}

	private void clickLoginButton() {
		getMouse().click(new RectangleDestination(getBot(), 240, 310, 120, 20));
	}

	private void enterUserDetails() {
		if (!getKeyboard().typeString(username)) {
			setFailed();
			return;
		}

		if (!getKeyboard().typeString(password)) {
			setFailed();
			return;
		}

		new ConditionalSleep(30_000) {
			@Override
			public boolean condition() throws InterruptedException {
				return getLobbyButton() != null || getClient().getLoginUIState() == 3 || isDisabledMessageVisible()
						|| isLocked() || isAlreadyLoggedInLocked() || isWrongEmail();
			}
		}.sleep();

		if (isLocked()) {
			log("Account is locked, setting to locked");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", this.username);
			System.exit(1);
		} else if (isDisabledMessageVisible()) {
			log("Account is banned, setting to locked");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", this.username);
			System.exit(1);
		} else if (isWrongEmail()) {
			log("Account password is wrong, setting to invalid password");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "INVALID_PASSWORD", this.username);
			System.exit(1);
		}

		if (isAlreadyLoggedInLocked()) {
			log("Account is already logged in.. waiting 30 seconds to restart");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(1);
		}

		if (!getClient().isLoggedIn()) {
			setFailed();
		} else {
			if (!getAccountStage().equalsIgnoreCase("UNKNOWN")) {
				DatabaseUtilities.updateAccountStatusInDatabase(this, "AVAILABLE", this.username);
			}

		}

//		beginTime = System.currentTimeMillis();
//		Thread th1 = new Thread(() -> {
//			boolean alive = true;
//			while (alive) {
//				if (System.currentTimeMillis() - beginTime > 300_000) {
//					try {
//						log("Couldn't be logged in.. restarting");
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.exit(1);
//				}
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (getClient().isLoggedIn()) {
//				alive = false;
//				log("You've successfully logged in, it will no longer exit after 5 minutes");
//			}
//		});
//		th1.start();
	}

	private long beginTime;

	private boolean clickHereToPlayButton() {
		RS2Widget clickToPlay = getWidgets().getWidgetContainingText("CLICK HERE TO PLAY");
		if (clickToPlay != null) {
			clickToPlay.interact("Play");
			return true;
		}
		return false;
	}

	private boolean isLocked() {
		return getColorPicker().isColorAt(222, 196, Color.YELLOW);
	}
	
	private boolean isWrongEmail() {
		return getColorPicker().isColorAt(438, 327, new Color(255, 255, 255));
	}

	private boolean isAlreadyLoggedInLocked() {
		return getColorPicker().isColorAt(272, 227, new Color(255, 255, 0));
	}

	private boolean clickTryAgainButton() {
		return getMouse().click(new RectangleDestination(getBot(), 318, 262, 130, 26));
	}

	private boolean isDisabledMessageVisible() {
		return getColorPicker().isColorAt(483, 191, new Color(255, 255, 0));
	}

	private void clickLobbyButton() {
		if (getLobbyButton().interact()) {
			new ConditionalSleep(10_000) {
				@Override
				public boolean condition() throws InterruptedException {
					return getLobbyButton() == null;
				}
			}.sleep();
		}
	}

	private RS2Widget getLobbyButton() {
		try {
			return getWidgets().get(378, 83);// (gangsthurh) changed code here
			// return getWidgets().getWidgetContainingText("CLICK HERE TO PLAY");
		} catch (NullPointerException n) {
			return null;
		}

	}

	@Override
	public final void onResponseCode(final int responseCode) throws InterruptedException {
		if (ResponseCode.isDisabledError(responseCode)) {
			log("Login failed, account is disabled");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", this.username);
			setFailed();
			System.exit(1);
			return;
		}

		if (ResponseCode.isConnectionError(responseCode)) {
			log("Connection error, attempts exceeded");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "AVAILABLE", this.username);
			setFailed();
			System.exit(1);
			return;
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/**
	 * @return the accountStage
	 */
	public String getAccountStage() {
		return accountStage;
	}

	/**
	 * Sets an account stage
	 * 
	 * @param accountStage
	 */
	public void setAccountStage(String accountStage) {
		this.accountStage = accountStage;
	}

	/**
	 * @return the tradeWith
	 */
	public String getTradeWith() {
		return tradeWith;
	}

	public void setTradeWith(String tradeWith) {
		this.tradeWith = tradeWith;
	}

}