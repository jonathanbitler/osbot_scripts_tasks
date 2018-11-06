package osbot_scripts.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class GuilinorGuideSection extends TutorialSection {

	public GuilinorGuideSection() {
		super("Gielinor Guide");
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(getProgress());

		if (pendingContinue()) {
			selectContinue();
		}

		switch (getProgress()) {

		case 1:
			TutorialScript.mainState = MainState.CREATE_CHARACTER_DESIGN;
			break;
		case 0:
		case 2:
			if (getWidgets().getWidgetContainingText("What's your experience with Old School Runescape?") != null) {
				getDialogues().selectOption(random(1, 3));
			} else {
				talkAndContinueWithInstructor();
			}
			break;

		case 3:
			new MandatoryEventsExecution(this).fixedMode();
			new MandatoryEventsExecution(this).fixedMode2();
			getTabs().open(Tab.SETTINGS);
			break;

		case 7:
			talkAndContinueWithInstructor();
			break;

		case 10:
			clickObject(9398, "Open", new Position(3097, 3107, 0));
			Sleep.sleepUntil(() -> !myPlayer().isMoving(), 3000, 1000);
			break;

		case 20:
			TutorialScript.mainState = getNextMainState();
			break;
		}

	}

	@Override
	public boolean isCompleted() {
		return getProgress() > 0 ? true : false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.SURVIVAL_EXPERT;
	}

}
