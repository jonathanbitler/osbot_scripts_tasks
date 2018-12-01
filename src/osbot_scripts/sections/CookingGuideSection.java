package osbot_scripts.sections;

import java.util.Random;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;

import osbot_scripts.TutorialScript;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class CookingGuideSection extends TutorialSection {

	public CookingGuideSection() {
		super("Master Chef");
		// TODO Auto-generated constructor stub
	}

	private void openDoor() {
		RS2Object doorObject = getObjects().closest(9709);

		if (doorObject != null) {
			if (doorObject.interact("Open")) {
				Sleep.sleepUntil(() -> new Area(
						new int[][] { { 3078, 3089 }, { 3075, 3089 }, { 3075, 3086 }, { 3079, 3086 }, { 3079, 3089 } })
								.contains(myPlayer().getPosition()),
						10000, 5000);
			} else {
				getWalking().walk(new Position(3079, 3084, 0));
			}
		}
	}

	private void makeDough() {
		Item flour = getInventory().getItem(2516);
		if (flour != null) {
			Item bucketOfWater = getInventory().getItem(1929);

			if (bucketOfWater != null) {
				bucketOfWater.interact("Use");
				flour.interact("Use");
				Sleep.sleepUntil(() -> getInventory().contains(2307), 2000, 1000);
			}
		}
	}

	private void doughOnFire() {
		RS2Object fireRange = getObjects().closest(9736);
		if (fireRange != null) {
			fireRange.interact("Cook");
			Sleep.sleepUntil(() -> getInventory().contains(2309), 4000, 1000);
		}
	}

	private void clickingEmotes() {
		if (getTabs().open(Tab.EMOTES)) {
			RS2Widget emoteWidget = getWidgets().get(216, 1, new Random().nextInt(20));
			if (emoteWidget != null) {
				emoteWidget.interact();
				Sleep.sleepUntil(() -> !myPlayer().isAnimating(), 3000, 1000);
				if (getTabs().open(Tab.SETTINGS)) {
					if (!getSettings().isRunning()) {
						getSettings().setRunning(true);
						Sleep.sleepUntil(() -> getSettings().isRunning(), 3000, 1000);
					}
				}
			}
		}
	}

	private void walkToDungeon() {
		getWalking().webWalk(new Area(new int[][] { { 3083, 3128 }, { 3087, 3128 }, { 3087, 3126 }, { 3083, 3126 } }));
		clickObject(9716, "Open", new Position(3085, 3127, 0));
		Sleep.sleepUntil(() -> myPlayer().getArea(5).contains(new Position(3085, 3127, 0)), 10000, 3000);

		// if (!myPlayer().getArea(5).contains(new Position(3085, 3127, 0))) {
		// }

		Sleep.sleepUntil(() -> new Area(new int[][] { { 3082, 3126 }, { 3090, 3126 }, { 3090, 3119 }, { 3080, 3119 },
				{ 3080, 3123 }, { 3080, 3124 } }).contains(myPlayer().getPosition()), 10000, 3000);
	}

	private static final Area INSIDE_COOKING = new Area(new int[][] { { 3073, 3086 }, { 3073, 3083 }, { 3079, 3083 },
			{ 3079, 3087 }, { 3076, 3087 }, { 3076, 3089 }, { 3078, 3089 }, { 3078, 3092 }, { 3073, 3092 },
			{ 3073, 3089 }, { 3074, 3089 }, { 3074, 3087 }, { 3073, 3087 }, { 3073, 3086 } });

	private void openNextDoor(int objId, int x, int y) {

		// getObjects().getAll().stream().forEach(obj -> {
		// if (obj.getId() == objId) {
		// log(x+" "+y+" "+obj.getX()+" "+obj.getY()+" "+objId+" "+obj.getId());
		// if (x == obj.getX() && y == obj.getY()) {
		// obj.interact();
		// log("clicking on obj: "+obj.getId()+" "+obj.getX()+" "+obj.getY());
		// }
		// }
		// });
		// getObjects().closest(obj -> obj.getX() == x && obj.getY() == y && obj.getId()
		// == objId)

		getDoorHandler().handleNextObstacle(new Position(x, y, 0));
		Sleep.sleepUntil(() -> myPlayer().getArea(1).contains(new Position(x, y, 0)), 10000);

		// RS2Object door = getObjects().closest(obj -> obj.getX() == x && obj.getY() ==
		// y && obj.getId() == objId);
		// if (door != null) {
		// door.interact();
		// }
		// Sleep.sleepUntil(() -> door.getArea(1).contains(myPlayer()), 10000);
	}

	@Override
	public void onLoop() throws InterruptedException {
		log(getProgress());

		log("inside cooking: " + INSIDE_COOKING.contains(myPlayer()));

		switch (getProgress()) {
		case 130:
			getWalking().walk(new Position(3080, 3084, 0));
			openNextDoor(9709, 3079, 3084);
			break;

		case 140:
			if (!INSIDE_COOKING.contains(myPlayer())) {
				openNextDoor(9709, 3079, 3084);
			} else {
				talkAndContinueWithInstructor();
			}
			break;

		case 150:
			if (!INSIDE_COOKING.contains(myPlayer())) {
				openNextDoor(9709, 3079, 3084);
			} else {
				makeDough();
			}
			break;

		case 160:
			if (!INSIDE_COOKING.contains(myPlayer())) {
				openNextDoor(9709, 3079, 3084);
			} else {
				doughOnFire();
			}
			break;

		case 170:
			if (getDoorHandler().handleNextObstacle(new Position(3072, 3090, 0))) {
				Sleep.sleepUntil(() -> getProgress() != 170, 5000, 600);
			}
			break;

		case 180:
			if (!INSIDE_COOKING.contains(myPlayer())) {
				openNextDoor(9709, 3079, 3084);
			} else {
				openNextDoor(9710, 3072, 3090);
			}

			break;

		case 183:
		case 187:
			clickingEmotes();
			break;

		case 200:
			// No progress
			if (!getSettings().isRunning()) {
				getSettings().setRunning(true);
			} else {
				getSettings().setRunning(false);
			}
			break;

		case 210:
			walkToDungeon();
			break;

		case 220:
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
		return MainState.QUEST_SECTION;
	}

}
