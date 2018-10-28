package osbot_scripts.events;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;

public class FixedModeEvent extends Event {

	private final CachedWidget toggleShiftClickDrop = new CachedWidget(
			new WidgetActionFilter("Fixed mode"));

	@Override
	public final int execute() throws InterruptedException {
		if (Tab.SETTINGS.isDisabled(getBot())) {
			setFailed();
		} else if (getTabs().getOpen() != Tab.SETTINGS) {
			getTabs().open(Tab.SETTINGS);
		} else if (!toggleShiftClickDrop.get(getWidgets()).isPresent()) {
			//OK!
		} else if (toggleShiftClickDrop.get(getWidgets()).get().interact()) {
			setFinished();
		}
		return 200;
	}
}
