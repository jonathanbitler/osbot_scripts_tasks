package osbot_scripts.framework;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.util.Sleep;

public class GenieLamp {

	public static void openGenieLamp(MethodProvider api) {
		if (api.getInventory().contains(2528)) {
			Item item = api.getInventory().getItem(2528);

			Sleep.sleepUntil(() -> item != null, 5000);
			if (item != null) {
				item.interact();

				RS2Widget exp = api.getWidgets().get(134, 15);

				Sleep.sleepUntil(() -> exp != null && exp.isVisible(), 5000);
				if (exp != null && exp.isVisible()) {
					exp.interact();

					RS2Widget confirm = api.getWidgets().get(134, 26);

					Sleep.sleepUntil(() -> confirm != null && confirm.isVisible(), 5000);
					if (confirm != null && confirm.isVisible()) {
						confirm.interact();
					}
				}
			}
		}
	}
}
