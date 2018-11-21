package osbot_scripts.framework;

import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.AreaInterface;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class PickupItemTask extends TaskSkeleton implements Task, AreaInterface {

	private boolean ranOnStart = false;

	private Area area;

	private String waitForItemString;

	private String interactOption;

	private boolean pickedUp;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public PickupItemTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			String waitForItemString) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setWaitForItemString(waitForItemString);
	}

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public PickupItemTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			String interactOption, String waitForItemString) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setInteractOption(interactOption);
		setWaitForItemString(waitForItemString);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		// If player is not in the selected field, then walk to it
		if (getArea() != null && !getApi().myPlayer().getArea(10).contains(getArea().getRandomPosition())) {
			getApi().getWalking().walk(getArea());
		}
		ranOnStart = true;
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return this.area;
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return super.getScriptName();
	}

	@Override
	public boolean startCondition() {
		Optional<GroundItem> object = getApi().getGroundItems().getAll().stream().filter(Objects::nonNull)
				.filter(obj -> obj.getName().equalsIgnoreCase(getWaitForItemString())).findFirst();
		if (object.isPresent()) {
			return false;
		}
		if (getApi().myPlayer().getArea(20).contains(object.get()) && correctStepInQuest()) {
			return true;
		}
		return false;
	}

	@Override
	public void loop() {
		if (!ranOnStart()) {
			onStart();
		}
		if (getArea() != null) {
			// Waiting before player is in an area
			Sleep.sleepUntil(() -> getArea().contains(getApi().myPlayer()), 10000);
		}

		GroundItem object = getApi().getGroundItems()
				.closest(obj -> obj.getName().equalsIgnoreCase(getWaitForItemString()));
		// Optional<GroundItem> object =
		// getApi().getGroundItems().getAll().stream().filter(Objects::nonNull)
		// .filter(obj ->
		// obj.getName().equalsIgnoreCase(getWaitForItemString())).findFirst();
		if (object != null) {
			if (getArea() != null && !getArea().contains(getApi().myPlayer())) {
				getApi().getWalking().webWalk(getArea());
			}

			if (getInteractOption() != null && getInteractOption().length() > 0) {
				object.interact(getInteractOption());
				setPickedUp(true);
			} else {
				object.interact();
				setPickedUp(true);
			}
		}

		// if (getWaitForItemString() != null && getWaitForItemString().length() > 0) {
		// Sleep.sleepUntil(() ->
		// getApi().getInventory().contains(getWaitForItemString()), 10000);
		//
		// if (getWaitForItemString() != null && getWaitForItemString().length() > 0
		// && !getApi().getInventory().contains(getWaitForItemString())) {
		// setPickedUp(false);
		// }
		// }

	}

	@Override
	public boolean finished() {
		if (getWaitForItemString() != null && getWaitForItemString().length() > 0) {
			return isPickedUp() && getApi().getInventory().contains(getWaitForItemString());
		}
		return isPickedUp();
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public int requiredConfigQuestStep() {
		// TODO Auto-generated method stub
		return getCurrentQuestProgress();
	}

	@Override
	public boolean ranOnStart() {
		// TODO Auto-generated method stub
		return ranOnStart;
	}

	/**
	 * @param ranOnStart
	 *            the ranOnStart to set
	 */
	public void setRanOnStart(boolean ranOnStart) {
		this.ranOnStart = ranOnStart;
	}

	/**
	 * @return the interactOption
	 */
	public String getInteractOption() {
		return interactOption;
	}

	/**
	 * @param interactOption
	 *            the interactOption to set
	 */
	public void setInteractOption(String interactOption) {
		this.interactOption = interactOption;
	}

	public String getWaitForItemString() {
		return waitForItemString;
	}

	public void setWaitForItemString(String waitForItemString) {
		this.waitForItemString = waitForItemString;
	}

	/**
	 * @return the pickedUp
	 */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/**
	 * @param pickedUp
	 *            the pickedUp to set
	 */
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}
