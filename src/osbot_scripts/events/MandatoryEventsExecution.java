package osbot_scripts.events;

import java.awt.event.KeyEvent;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import osbot_scripts.util.Sleep;

public class MandatoryEventsExecution {

	public MandatoryEventsExecution(MethodProvider provider) {
		this.setProvider(provider);
	}

	private MethodProvider provider;

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

	public void fixedMode() throws InterruptedException {
		RS2Widget isResizable = getProvider().getWidgets().get(164, 32);

		// Has the resizable screen active
		if (isResizable != null && isResizable.isVisible()) {
			getProvider().log("In resizable mode!");

			RS2Widget inSettingsTab = getProvider().getWidgets().get(261, 33);
			// In settings tab or not

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

					Thread.sleep(5000);
					getProvider().log("Restarting for the resizable to take effect");
					System.exit(1);
				}
			} else {
				RS2Widget settingsTab = getProvider().getWidgets().get(164, 38);
				settingsTab.interact("Options");
			}
		}
	}

	public void fixedMode2() throws InterruptedException {
		RS2Widget isResizable = getProvider().getWidgets().get(164, 34);

		// Has the resizable screen active
		if (isResizable != null && isResizable.isVisible()) {
			getProvider().log("In resizable mode!");

			RS2Widget inSettingsTab = getProvider().getWidgets().get(261, 33);
			// In settings tab or not

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

					Thread.sleep(5000);
					getProvider().log("Restarting for the resizable to take effect");
					System.exit(1);
				}
			} else {
				RS2Widget settingsTab = getProvider().getWidgets().get(164, 38);
				settingsTab.interact("Options");
			}
		}
	}

	public boolean executeAllEvents() {
		// if (!disabled) {
		// getProvider().log("Executing all events roofs/auto/fixed mode");
		// if (!isFixedMode) {
		// RS2Widget widget = getProvider().getWidgets().get(164, 38);
		// if (widget != null) {
		// widget.interact("Options");
		// RS2Widget fixedMode = getProvider().getWidgets().get(261, 33, 9);
		// if (fixedMode != null) {
		// fixedMode.interact("Fixed mode");
		// isFixedMode = true;
		// getProvider().log("Set fixed to: " + isFixedMode);
		// }
		// }
		// // Event fixedModeEvent = new FixedModeEvent();
		// // getProvider().execute(fixedModeEvent);
		// // isFixedMode = fixedModeEvent.hasFinished();
		// }
		// getProvider().log("roofs currently:
		// "+getProvider().getSettings().areRoofsEnabled());
		// if ((!getProvider().getSettings().areRoofsEnabled() || !isAudioDisabled
		// || !getProvider().getSettings().isShiftDropActive())) {

		// getProvider().log("ello1");
		// getProvider().log("continue widget: "+getContinueWidget());

		if (getProvider().getWidgets().getWidgetContainingText("try talking to the Lumbridge Guide") != null
				&& getProvider().getWidgets().getWidgetContainingText("try talking to the Lumbridge Guide")
						.isVisible()) {
			selectContinue();
		}

		if ((getContinueWidget() != null && !getContinueWidget().isVisible()) || (getContinueWidget() == null)) {

			// To disable the interfaces
			getProvider().getWalking().walk(getProvider().myPlayer());
			// getProvider().log("ello2");
			if (!isAudioDisabled) {
				isAudioDisabled = disableAudio();
				getProvider().getTabs().open(Tab.INVENTORY);
				if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
					getProvider().getTabs().open(Tab.INVENTORY);
				}
				// getProvider().log("ello4");
			}

			getProvider().log("ello5");
			if (!getProvider().getSettings().isShiftDropActive()) {
				toggleShiftDrop();
				// getProvider().log("ello6");
				if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
					getProvider().getTabs().open(Tab.INVENTORY);
				}
			}

			if (!getProvider().getSettings().areRoofsEnabled()) {
				Event toggleRoofsHiddenEvent = new ToggleRoofsHiddenEvent();
				getProvider().execute(toggleRoofsHiddenEvent);
				// getProvider().log("ello3");
				if (getProvider().getTabs().getOpen() != Tab.INVENTORY) {
					getProvider().getTabs().open(Tab.INVENTORY);
				}
			}
		}
		// }
		// else if ((!getProvider().getSettings().areRoofsEnabled() || !isAudioDisabled
		// || !getProvider().getSettings().isShiftDropActive())
		// && (getContinueWidget() != null && getContinueWidget().isVisible())) {
		// getProvider().getDialogues().clickContinue();
		// }
		// }
		return true;
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

}
