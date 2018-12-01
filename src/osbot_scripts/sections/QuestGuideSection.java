package osbot_scripts.sections;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.sections.total.progress.MainState;

public class QuestGuideSection extends TutorialSection {

	public QuestGuideSection() {
		super("Quest Guide");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {

		log(getProgress());

		if (pendingContinue()) {
			selectContinue();
			return;
		}

		switch (getProgress()) {
		case 220:
			log("220 yay!");
			if (!new Area(new int[][] { { 3082, 3126 }, { 3090, 3126 }, { 3090, 3119 }, { 3080, 3119 }, { 3080, 3124 },
					{ 3082, 3126 } }).contains(myPlayer())) {
				RS2Object obj = getObjects().closest("Door");
				if (obj != null) {
					obj.interact("Open");
				}
				log("is not in correct area, clicking door!");
				// getDoorHandler().handleNextObstacle(new Position(3086, 3162, 0));
			} else {
				talkAndContinueWithInstructor();
			}
			break;

		case 230:
			getTabs().open(Tab.QUEST);
			break;

		case 240:
			talkAndContinueWithInstructor();
			break;

		case 250:
			clickObject(9726, "Climb-down");
			break;

		case 260:
			TutorialScript.mainState = getNextMainState();
			break;
		}

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.MINING_SECTION;
	}

}
