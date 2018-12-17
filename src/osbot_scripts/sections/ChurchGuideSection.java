package osbot_scripts.sections;

import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.framework.TabWid;
import osbot_scripts.framework.Tabs;
import osbot_scripts.sections.total.progress.MainState;

public class ChurchGuideSection extends TutorialSection {

	public ChurchGuideSection() {
		super("Brother Brace");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		log(getProgress());

		if (pendingContinue()) {
			selectContinue();
			return;
		}

		switch (getProgress()) {
		case 550:
			if (new Area(new int[][] { { 3120, 3110 }, { 3120, 3103 }, { 3129, 3103 }, { 3129, 3111 }, { 3120, 3111 } })
					.contains(myPlayer().getPosition())) {
				talkAndContinueWithInstructor();
			} else {
				getWalking().webWalk(new Area(new int[][] { { 3120, 3110 }, { 3120, 3103 }, { 3129, 3103 },
						{ 3129, 3111 }, { 3120, 3111 } }));
				log("webwalking to the position of church");
			}
			break;

		case 560:
//			getTabs().open(Tab.PRAYER);
			Tabs.openTab(this, TabWid.PRAYER);
			break;

		case 570:
			talkAndContinueWithInstructor();
			break;

		case 580:
//			getTabs().open(Tab.FRIENDS);
			Tabs.openTab(this, TabWid.FRIENDS);
			break;

		case 590:
//			getTabs().open(Tab.ACCOUNT_MANAGEMENT);
			Tabs.openTab(this, TabWid.ACCOUNT_MANAGER);
			break;

		case 600:
			talkAndContinueWithInstructor();
			break;

		case 610:
			clickObject(9723, "Open", new Position(3122, 3103, 0));
			break;

		case 620:
			clickObject(9723, "Open", new Position(3122, 3103, 0));
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
		return MainState.WIZARD_GUIDE_SECTION;
	}

}
