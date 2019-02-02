package osbot_scripts.events;

import java.awt.Color;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.constants.ResponseCode;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.input.mouse.RectangleDestination;
import org.osbot.rs07.listener.LoginResponseCodeListener;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;

public final class LoginEvent extends Event implements LoginResponseCodeListener {

	private final String username, password;

	private String accountStage, tradeWith, emailTradeWith, actualUsername;

	private long startTime = -1;

	private int pid;

	private String script;

	private MethodProvider api;

	private String dbUsername = null, dbName = null, dbPassword = null;

	public String getDbUsername() {
		return dbUsername;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public LoginEvent(final String username, final String password, int pid, String accountStage, MethodProvider api) {
		this.username = username;
		this.password = password;
		this.pid = pid;
		this.accountStage = accountStage;
		this.api = api;
		this.setAsync();
	}

	@Override
	public final int execute() throws InterruptedException {
		// Set current time
		if (getStartTime() == -1) {
			setStartTime(System.currentTimeMillis());
		}

		if (!getBot().isLoaded()) {
			return 1000;
		} else if ((getClient().isLoggedIn() && getLobbyButton() == null)) {
			getBot().getScriptExecutor().resume();
			setFinished();

			getApi().log("SUCCESSFULLY LOGGED IN! SET FINISHED TO: " + hasFinished());

//			if (DatabaseUtilities.isServerMuleTradingAccount(getApi(), this, getUsername())) {
//				new Thread(() -> DatabaseUtilities.updateLoginStatus("server_muling", api, username, "LOGGED_IN", this))
//						.start();
//			} else {
				new Thread(() -> DatabaseUtilities.updateLoginStatus(api, username, "LOGGED_IN", this)).start();
			//			}

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
			DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", this.username, this);
			BotCommands.waitBeforeKill(api, "BECAUSE OF ACCOUNT IS LOCKED");
		} else if (isDisabledMessageVisible()) {
			log("Account is banned, setting to locked");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", this.username, this);
			BotCommands.waitBeforeKill(api, "BECAUSE OF ACCOUNT IS BANNED");
		} else if (isWrongEmail()) {
			log("Account password is wrong, setting to invalid password");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", this.username, this);
			BotCommands.waitBeforeKill(api, "BECAUSE OF PASSWORD IS WRONG");
		}

		if (isAlreadyLoggedInLocked()) {
			log("Account is already logged in.. waiting 30 seconds to restart");
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BotCommands.waitBeforeKill(api, "BECAUSE OF ALREADY LOGGED IN");
		}

		if (!getClient().isLoggedIn()) {
			setFailed();
		} else {
			if (!getAccountStage().equalsIgnoreCase("UNKNOWN")) {
				DatabaseUtilities.updateAccountStatusInDatabase(this, "AVAILABLE", this.username, this);
			}

		}

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
		// return getColorPicker().isColorAt(422, 232, new Color(255, 255, 0));
		return getColorPicker().isColorAt(541, 216, new Color(255, 255, 0));
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
		log("RESPONSE CODE IS: " + responseCode);

		if (ResponseCode.isDisabledError(responseCode)) {
			log("Login failed, account is disabled");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", this.username, this);
			setFailed();
			BotCommands.waitBeforeKill(api, "BECAUSE OF ACCOUNT IS DISABELD");
			return;
		}

		if (ResponseCode.isConnectionError(responseCode)) {
			log("Connection error, attempts exceeded");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "AVAILABLE", this.username, this);
			setFailed();
			BotCommands.waitBeforeKill(api, "BECAUSE OF CONNECTION ERROR");
			return;
		}

		switch (responseCode) {
		case 5:
		case 1:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 13:
		case 14:
		case 16:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
			setFailed();
			BotCommands.waitBeforeKill(api, "NULL");
			break;

		case 11:
		case 18:
			log("Account is locked, setting to locked");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", this.username, this);
			BotCommands.waitBeforeKill(api, "NULL");
			break;

		case 3:
			log("Account password is wrong, setting to invalid password");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "LOCKED", this.username, this);
			BotCommands.waitBeforeKill(api, "NULL");
			break;

		case 4:
			log("Account is banned, setting to locked");
			DatabaseUtilities.updateAccountStatusInDatabase(this, "BANNED", this.username, this);
			BotCommands.waitBeforeKill(api, "NULL");
			break;
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

	/**
	 * @return the emailTradeWith
	 */
	public String getEmailTradeWith() {
		return emailTradeWith;
	}

	/**
	 * @param emailTradeWith
	 *            the emailTradeWith to set
	 */
	public void setEmailTradeWith(String emailTradeWith) {
		this.emailTradeWith = emailTradeWith;
	}

	/**
	 * @return the actualUsername
	 */
	public String getActualUsername() {
		return actualUsername;
	}

	/**
	 * @param actualUsername
	 *            the actualUsername to set
	 */
	public void setActualUsername(String actualUsername) {
		this.actualUsername = actualUsername;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * @return the api
	 */
	public MethodProvider getApi() {
		return api;
	}

	/**
	 * @param api
	 *            the api to set
	 */
	public void setApi(MethodProvider api) {
		this.api = api;
	}

}