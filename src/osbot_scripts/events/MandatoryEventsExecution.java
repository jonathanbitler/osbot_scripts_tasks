package osbot_scripts.events;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;

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

	public void fixedMode() {
		RS2Widget isResizable = getProvider().getWidgets().get(164, 32);
		if (isResizable != null) {
			RS2Widget widget = getProvider().getWidgets().get(164, 38);
			if (widget != null) {
				widget.interact("Options");
				RS2Widget fixedMode = getProvider().getWidgets().get(261, 33, 9);
				if (fixedMode != null) {
					fixedMode.interact("Fixed mode");
					isFixedMode = true;
					getProvider().log("Set fixed to: " + isFixedMode);
					getProvider().log("Restarting for resizable to take effect");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(1);
				}
			}
		}
	}

	public boolean executeAllEvents() {
		if (!disabled) {
			getProvider().log("Executing all events roofs/auto/fixed mode");
			if (!isFixedMode) {
				RS2Widget widget = getProvider().getWidgets().get(164, 38);
				if (widget != null) {
					widget.interact("Options");
					RS2Widget fixedMode = getProvider().getWidgets().get(261, 33, 9);
					if (fixedMode != null) {
						fixedMode.interact("Fixed mode");
						isFixedMode = true;
						getProvider().log("Set fixed to: " + isFixedMode);
					}
				}
				// Event fixedModeEvent = new FixedModeEvent();
				// getProvider().execute(fixedModeEvent);
				// isFixedMode = fixedModeEvent.hasFinished();
			}
			if (!getProvider().getSettings().areRoofsEnabled()) {
				Event toggleRoofsHiddenEvent = new ToggleRoofsHiddenEvent();
				getProvider().execute(toggleRoofsHiddenEvent);
			}
			if (!isAudioDisabled) {
				isAudioDisabled = disableAudio();
			} else if (!getProvider().getSettings().isShiftDropActive()) {
				toggleShiftDrop();
			}
			getProvider().getTabs().open(Tab.INVENTORY);
			disabled = true;
		}
		return true;
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
