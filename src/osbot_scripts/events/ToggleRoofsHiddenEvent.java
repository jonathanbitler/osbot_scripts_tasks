package osbot_scripts.events;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;

public final class ToggleRoofsHiddenEvent extends Event {

	private final CachedWidget advancedOptionsWidget = new CachedWidget("Advanced options");
	private final CachedWidget displaySettingsWidget = new CachedWidget(new WidgetActionFilter("Display"));
	private final CachedWidget toggleRoofHiddenWidget = new CachedWidget(new WidgetActionFilter("Roof-removal"));

	private static final Area LUMBRIDGE = new Area(
			new int[][] { { 3053, 3138 }, { 3036, 3102 }, { 3038, 3054 }, { 3059, 3041 }, { 3096, 3037 },
					{ 3141, 3048 }, { 3158, 3067 }, { 3169, 3078 }, { 3171, 3118 }, { 3160, 3133 }, { 3136, 3138 },
					{ 3129, 3145 }, { 3097, 3143 }, { 3090, 3154 }, { 3066, 3147 }, { 3053, 3138 } });

	private static final Area LUMBRIDGE_UPPER_ONE = new Area(
			new int[][] { { 3053, 3138 }, { 3036, 3102 }, { 3038, 3054 }, { 3059, 3041 }, { 3096, 3037 },
					{ 3141, 3048 }, { 3158, 3067 }, { 3169, 3078 }, { 3171, 3118 }, { 3160, 3133 }, { 3136, 3138 },
					{ 3129, 3145 }, { 3097, 3143 }, { 3090, 3154 }, { 3066, 3147 }, { 3053, 3138 } }).setPlane(1);

	private static final Area LUMBRIDGE_UPPER_TWO = new Area(
			new int[][] { { 3053, 3138 }, { 3036, 3102 }, { 3038, 3054 }, { 3059, 3041 }, { 3096, 3037 },
					{ 3141, 3048 }, { 3158, 3067 }, { 3169, 3078 }, { 3171, 3118 }, { 3160, 3133 }, { 3136, 3138 },
					{ 3129, 3145 }, { 3097, 3143 }, { 3090, 3154 }, { 3066, 3147 }, { 3053, 3138 } }).setPlane(2);

	public void checkIfHasDialogue() throws InterruptedException {
		if (LUMBRIDGE.contains(myPlayer()) || LUMBRIDGE_UPPER_ONE.contains(myPlayer())
				|| LUMBRIDGE_UPPER_TWO.contains(myPlayer())) {
			return;
		}
		boolean finish = false;
		while (!finish && (getDialogues().isPendingContinuation() || getDialogues().isPendingOption())) {
			finish = !getDialogues().isPendingContinuation() && !getDialogues().isPendingOption();

			getDialogues().clickContinue();
			getDialogues().selectOption(0);

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