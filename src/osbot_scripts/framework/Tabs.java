package osbot_scripts.framework;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

public class Tabs {

	public static boolean openTab(MethodProvider api, TabWid tab) {
		RS2Widget widget = null;
		if (tab.getMain() > 0 && tab.getSub() > 0) {
			widget = api.getWidgets().get(tab.getMain(), tab.getSub());
		} else if (tab.getMain() > 0 && tab.getSub() > 0 && tab.getSubsub() > 0) {
			widget = api.getWidgets().get(tab.getMain(), tab.getSub(), tab.getSubsub());
		}
		return widget != null && widget.interact() && widget.isVisible();
	}

}
