package osbot_scripts.events;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public final class WidgetActionFilter implements Filter<RS2Widget> {

	private final String[] actions;

	public WidgetActionFilter(final String... actions) {
		this.actions = actions;
	}

	@Override
	public final boolean match(final RS2Widget rs2Widget) {
		if (rs2Widget == null || !rs2Widget.isVisible() || rs2Widget.getInteractActions() == null) {
			return false;
		}
		for (final String widgetAction : rs2Widget.getInteractActions()) {
			for (final String matchAction : actions) {
				if (matchAction.equals(widgetAction)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsText(MethodProvider s,int parent, int child, String text) {
		RS2Widget widget = s.getWidgets().get(parent, child);
		if (exists(widget) && widget.isVisible()) {
			return widget.getMessage().contains(text);
		}
		return false;
	}

	public static boolean interactTil(MethodProvider s,String action, int i, int j, int k, ConditionalSleep con) {
		RS2Widget widget = s.getWidgets().get(i, j, k);
		if (exists(widget)) {
			widget.interact(action);
			con.sleep();
			return true;
		}
		return false;
	}

	public static String getText(MethodProvider s, int i, int j) {
		RS2Widget widget = s.getWidgets().get(i, j);
		if (exists(widget) && widget.isVisible()) {
			return widget.getMessage();
		}
		return "";
	}

	private static boolean exists(RS2Widget widget) {
		return widget != null;
	}

	public static boolean interactTil(MethodProvider s, String action, int parent, int child, ConditionalSleep con) {
		RS2Widget widget = s.getWidgets().get(parent, child);
		if (exists(widget)) {
			widget.interact(action);
			con.sleep();
			return true;
		}
		return false;
	}

	public static boolean interactTilNull(MethodProvider s, String action, int parent, int child) {
		RS2Widget widget = s.getWidgets().get(parent, child);
		if (exists(widget)) {
			widget.interact(action);
			new ConditionalSleep(2500, 3000) {
				@Override
				public boolean condition() {
					return !exists(widget);
				}
			}.sleep();
			return true;
		}
		return false;
	}

	public static boolean interactTilNull(MethodProvider s, String action, int parent, int child, int child2) {
		RS2Widget widget = s.getWidgets().get(parent, child, child2);
		if (exists(widget)) {
			widget.interact(action);
			new ConditionalSleep(2500, 3000) {
				@Override
				public boolean condition() {
					return !exists(widget);
				}
			}.sleep();
			return true;
		}
		return false;
	}

}