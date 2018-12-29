package osbot_scripts.sections;

import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

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
			prospect(Rock.TIN);
			break;
		case 280:
			prospect(Rock.COPPER);
			break;
		case 290:
			talkAndContinueWithInstructor();
			break;
		case 300:
		case 311:
			mine(Rock.TIN);
			break;
		case 310:
			mine(Rock.COPPER);
			break;

		case 320:
			if (getTabs().open(Tab.INVENTORY)) {
				smelt();
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

	private void prospect(Rock rock) {
		RS2Object closestRock = rock.getClosestWithOre(getBot().getMethods());
		if (closestRock != null && closestRock.interact("Prospect")) {
			Sleep.sleepUntil(this::pendingContinue, 6000, 600);
		}
	}

	private void mine(Rock rock) {
		RS2Object closestRock = rock.getClosestWithOre(getBot().getMethods());
		if (closestRock != null && closestRock.interact("Mine")) {
			Sleep.sleepUntil(this::pendingContinue, 6000, 600);
		}
	}

	private void smelt() {
		if (!"Tin ore".equals(getInventory().getSelectedItemName())) {
			getInventory().getItem("Tin ore").interact("Use");
		} else if (getObjects().closest("Furnace").interact("Use")) {
			Sleep.sleepUntil(() -> getInventory().contains("Bronze bar"), 5000, 600);
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

enum Rock {

	COPPER((short) 4645, (short) 4510), TIN((short) 53);

	private final short[] COLOURS;

	Rock(final short... COLOURS) {
		this.COLOURS = COLOURS;
	}

	public RS2Object getClosestWithOre(final MethodProvider S) {
		// noinspection unchecked
		return S.getObjects().closest(obj -> {
			short[] colours = obj.getDefinition().getModifiedModelColors();
			if (colours != null) {
				for (short c : colours) {
					for (short col : COLOURS) {
						if (c == col)
							return true;
					}
				}
			}
			return false;
		});
	}
}
