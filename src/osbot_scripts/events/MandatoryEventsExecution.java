package osbot_scripts.events;

import java.awt.event.KeyEvent;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.util.Sleep;

public class MandatoryEventsExecution {

	public MandatoryEventsExecution(MethodProvider provider, LoginEvent login) {
		this.setProvider(provider);
		this.setLogin(login);
	}

	private MethodProvider provider;

	private LoginEvent login;

	public boolean isLoggedIn() {
		return //
		provider.getClient().getLoginStateValue() == 30 || //
				provider.getClient().isLoggedIn();
	}

	private boolean isAudioDisabled = false, isFixedMode = false;

	private boolean disabled = false;

	private boolean disableAudio() {
		Event disableAudioEvent = new DisableAudioEvent();
		getProvider().execute(disableAudioEvent);
		return disableAudioEvent.hasFinished();
	}

	private boolean toggleShiftDrop() {
		Event toggleShiftDrop = new ToggleShiftDropEvent();
		getProvider().execute(toggleShiftDrop);
		return toggleShiftDrop.hasFinished();
	}

	private int failed = 0;

	public void fixedMode() throws InterruptedException {
		boolean loop = true;

		if (!isLoggedIn() || getProvider() == null || getProvider().getWidgets() == null || !getLogin().hasFinished()) {
			return;
		}

		RS2Widget isResizable = getProvider().getWidgets().get(164, 32);

		if ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)) {
			return;
		}

		// Has the resizable screen active
		while (loop) {
			if (isResizable != null && isResizable.isVisible()) {
				getProvider().log("In resizable mode!");

				RS2Widget inSettingsTab = getProvider().getWidgets().get(261, 33);
				// In settings tab or not

				RS2Widget settingsTab = getProvider().getWidgets().get(164, 38);
				if (settingsTab != null) {
					settingsTab.interact("Options");
				}

				if (failed > 5) {

					getProvider().log("Couldn't fix itself, restarting");
					Thread.sleep(5000);
					BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF FIXED MODE");
				}
				failed++;

				RS2Widget computerTan = getProvider().getWidgets().get(261, 1, 0);
				if (computerTan != null && computerTan.isVisible()) {
					computerTan.interact("Display");
				}

				if (inSettingsTab != null && inSettingsTab.isVisible()) {
					RS2Widget fixedMode = getProvider().getWidgets().get(261, 33);
					Sleep.sleepUntil(() -> fixedMode != null && fixedMode.isVisible(), 5000);
					// Click on fixed mode
					if (fixedMode != null && fixedMode.isVisible()) {
						fixedMode.interact("Fixed mode");

						getProvider().log("Restarting for the resizable to take effect");
						getProvider().log("test: "
								+ ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)));
						Sleep.sleepUntil(
								() -> (isResizable != null && !isResizable.isVisible()) || (isResizable == null), 5000);
						if ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)) {
							getProvider().log("Successfully resized");
							loop = false;
							Thread.sleep(5000);
							BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF FIXED MODE");
						}

					}
				}
			}
		}
	}

	public void fixedMode2() throws InterruptedException {

		if (!EnableFixedModeEvent.isFixedModeEnabled(getProvider())) {
			if (getProvider().execute(new EnableFixedModeEvent()).hasFinished()) {
				System.out.println("Set client to fixed mode, finished");
				BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF FIXED MODE");
			}
		}

		if (!isLoggedIn() || getProvider() == null || getProvider().getWidgets() == null || !getLogin().hasFinished()) {
			return;
		}

		boolean loop = true;
		RS2Widget isResizable = getProvider().getWidgets().get(164, 34);

		if ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)) {
			return;
		}

		// Has the resizable screen active
		while (loop) {
			// Has the resizable screen active
			while (isResizable != null && isResizable.isVisible()) {
				getProvider().log("In resizable mode!");

				RS2Widget inSettingsTab = getProvider().getWidgets().get(261, 33);
				// In settings tab or not

				RS2Widget settingsTab = getProvider().getWidgets().get(164, 38);
				if (settingsTab != null) {
					settingsTab.interact("Options");
				}

				if (failed > 5) {
					getProvider().log("Couldn't fix itself, restarting");
					Thread.sleep(5000);
					BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF FIXED MODE");
				}
				failed++;

				RS2Widget computerTan = getProvider().getWidgets().get(261, 1, 0);
				if (computerTan != null && computerTan.isVisible()) {
					computerTan.interact("Display");
				}

				if (inSettingsTab != null && inSettingsTab.isVisible()) {
					RS2Widget fixedMode = getProvider().getWidgets().get(261, 33);
					Sleep.sleepUntil(() -> fixedMode != null && fixedMode.isVisible(), 5000);

					// Click on fixed mode
					if (fixedMode != null && fixedMode.isVisible()) {
						fixedMode.interact("Fixed mode");

						getProvider().log("Restarting for the resizable to take effect");
						getProvider().log("test: "
								+ ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)));
						Sleep.sleepUntil(
								() -> (isResizable != null && !isResizable.isVisible()) || (isResizable == null), 5000);
						if ((isResizable != null && !isResizable.isVisible()) || (isResizable == null)) {
							getProvider().log("Successfully resized");
							loop = false;
							Thread.sleep(5000);
							BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF FIXED MODE");
						}

					}
				}
			}
		}
	}

	private static final Area LUMRBDIGE = new Area(
			new int[][] { { 3220, 3236 }, { 3247, 3236 }, { 3246, 3211 }, { 3203, 3204 }, { 3200, 3234 } });

	public void executeAllEvents() {

		// Continueing when has a dialogue to prevent getting stuck
		if (pendingContinue()) {
			selectContinue();
			getProvider().getWalking().walk(getProvider().myPlayer().getArea(2));
		}

		if (!isAudioDisabled) {
			isAudioDisabled = disableAudio();
			getProvider().getTabs().open(Tab.INVENTORY);
			if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
				getProvider().getTabs().open(Tab.INVENTORY);
			}
		}
		if (!getProvider().getSettings().isShiftDropActive()) {
			toggleShiftDrop();
			if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
				getProvider().getTabs().open(Tab.INVENTORY);
			}
		}

		if (!LUMRBDIGE.contains(getProvider().myPlayer())) {
			if (!getProvider().getSettings().areRoofsEnabled()) {
				if (pendingContinue()) {
					selectContinue();
				}

				getProvider().getWalking().walk(getProvider().myPlayer().getPosition().translate(1, 1));
				Event toggleRoofsHiddenEvent = new ToggleRoofsHiddenEvent();
				getProvider().execute(toggleRoofsHiddenEvent);
				if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
					getProvider().getTabs().open(Tab.INVENTORY);
				}
			}
		}

	}

	protected boolean pendingContinue() {
		RS2Widget continueWidget = getContinueWidget();
		return continueWidget != null && continueWidget.isVisible();
	}

	private RS2Widget getContinueWidget() {
		return getProvider().getWidgets().singleFilter(getProvider().getWidgets().getAll(),
				widget -> widget.isVisible() && (widget.getMessage().contains("Click here to continue")
						|| widget.getMessage().contains("Click to continue")));
	}

	protected boolean selectContinue() {
		RS2Widget continueWidget = getContinueWidget();
		if (continueWidget == null) {
			return false;
		}
		if (continueWidget.getMessage().contains("Click here to continue")) {
			getProvider().getKeyboard().pressKey(KeyEvent.VK_SPACE);
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		} else if (continueWidget.interact()) {
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		}
		return false;
	}

	/**
	 * @return the provider
	 */
	public MethodProvider getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(MethodProvider provider) {
		this.provider = provider;
	}

	/**
	 * @return the login
	 */
	public LoginEvent getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(LoginEvent login) {
		this.login = login;
	}

}
