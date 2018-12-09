package osbot_scripts.sections;

import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.framework.TabWid;
import osbot_scripts.framework.Tabs;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class MiningGuideSection extends TutorialSection {

	public MiningGuideSection() {
		super("Mining instructor");
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
		case 260:
			Position miningInstructorPosition = new Position(3080, 9506, 0);
			if (miningInstructorPosition != null) {
				if (getWalking().walk(miningInstructorPosition)) {
				}
			}
			if (myPlayer().getArea(5).contains(miningInstructorPosition)) {
				talkAndContinueWithInstructor();
			}
			break;

		case 270:
			clickObject(10080, "Prospect", new Position(3076, 9502, 0));
			Thread.sleep(2000);
			break;

		case 280:
			clickObject(10079, "Prospect", new Position(3084, 9502, 0));
			Thread.sleep(2000);
			break;

		case 290:
			// getTabs().open(Tab.INVENTORY);
			Tabs.openTab(this, TabWid.INVENTORY);
			break;

		case 300:
		case 311:
			mineTin();
			break;

		case 310:
			mineCopper();
			break;

		case 320:
			// Walking to its own positon to prevent it getting stuck
			getWalking().walk(myPlayer().getArea(2).getRandomPosition());

			// if (getInventory().isItemSelected()) {
			// getInventory().deselectItem();
			// }

			Item tinOre = getInventory().getItem(438);
			if (tinOre != null) {
				Item copperOre = getInventory().getItem(436);
				if (copperOre != null) {
					if (tinOre.interact()) {
						clickObject(10082, "Use", new Position(3079, 9498, 0));
					}
				} else {
					mineCopper();
				}
			} else {
				mineTin();
			}
			break;

		case 330:
			talkAndContinueWithInstructor();
			break;

		case 340:
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				Tabs.openTab(this, TabWid.INVENTORY);
			} else {
				smith();
			}
			break;

		case 350:
			Optional<RS2Widget> daggerWidgetOpt = getDaggerWidget();
			if (daggerWidgetOpt.isPresent()) {
				if (daggerWidgetOpt.get().interact()) {
					Sleep.sleepUntil(() -> getInventory().contains("Bronze dagger"), 6000, 600);
				}
			} else {
				smith();
			}
			break;

		case 360:
			clickObject(9718, "Open");
			break;

		case 370:
			TutorialScript.mainState = getNextMainState();
			break;
		}

		if (pendingContinue()) {
			selectContinue();
		}

	}

	private static final Area SMITH_AREA = new Area(3076, 9497, 3082, 9504);

	private void smith() {
		if (!SMITH_AREA.contains(myPosition())) {
			getWalking().walk(SMITH_AREA);
		} else if (!"Bronze bar".equals(getInventory().getSelectedItemName())) {
			getInventory().getItem("Bronze bar").interact("Use");
		} else if (getObjects().closest("Anvil").interact("Use")) {
			Sleep.sleepUntil(() -> getDaggerWidget().isPresent(), 5000, 600);
		}
	}

	private Optional<RS2Widget> getDaggerWidget() {
		RS2Widget daggerTextWidget = getWidgets().getWidgetContainingText(312, "Dagger");
		if (daggerTextWidget != null) {
			return Optional
					.ofNullable(getWidgets().get(daggerTextWidget.getRootId(), daggerTextWidget.getSecondLevelId()));
		}
		return Optional.empty();
	}

	/**
	 * 
	 */
	private void mineCopper() {
		clickObject(10079, "Mine", new Position(3084, 9502, 0));
		Sleep.sleepUntil(() -> myPlayer().getAnimation() == -1, 5000, 1000);
	}

	/**
	 * 
	 */
	private void mineTin() {
		clickObject(10080, "Mine", new Position(3076, 9502, 0));
		Sleep.sleepUntil(() -> myPlayer().getAnimation() == -1, 5000, 1000);
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.COMBAT_SECTION;
	}

}
