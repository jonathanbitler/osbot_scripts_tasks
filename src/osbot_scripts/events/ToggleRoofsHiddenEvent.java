package osbot_scripts.events;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;

public final class ToggleRoofsHiddenEvent extends Event {

	private final CachedWidget advancedOptionsWidget = new CachedWidget("Advanced options");
	private final CachedWidget displaySettingsWidget = new CachedWidget(new WidgetActionFilter("Display"));
	private final CachedWidget toggleRoofHiddenWidget = new CachedWidget(new WidgetActionFilter("Roof-removal"));

	public void checkIfHasDialogue() throws InterruptedException {
		boolean finish = false;

		while (!finish && getDialogues().isPendingContinuation()) {
			finish = !getDialogues().isPendingContinuation();

			getDialogues().clickContinue();

			Thread.sleep(1500);
			log("CLICKING ON CONTINUE FOR DIALOGUE");
		}
	}
	
	@Override
	public final int execute() throws InterruptedException {
		checkIfHasDialogue();
		if (Tab.SETTINGS.isDisabled(getBot())) {
			setFailed();
		} else if (getTabs().getOpen() != Tab.SETTINGS) {
			checkIfHasDialogue();
			getTabs().open(Tab.SETTINGS);
		} else if (!advancedOptionsWidget.get(getWidgets()).isPresent()) {
			checkIfHasDialogue();
			displaySettingsWidget.get(getWidgets()).ifPresent(widget -> widget.interact());
		} else if (!toggleRoofHiddenWidget.get(getWidgets()).isPresent()) {
			checkIfHasDialogue();
			advancedOptionsWidget.get(getWidgets()).get().interact();
		} else if (toggleRoofHiddenWidget.get(getWidgets()).get().interact()) {
			setFinished();
		}
		return 200;
	}
}