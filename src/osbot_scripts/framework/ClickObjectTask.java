package osbot_scripts.framework;

import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.AreaInterface;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class ClickObjectTask extends TaskSkeleton implements Task, AreaInterface {

	private boolean ranOnStart = false;

	private Area area;

	private Area finalDestinationArea;

	private int objectId;

	private String waitForItemString;

	private String interactOption;

	private boolean clickedObject;

	private int waitOnObjectIdToChange;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
	}

	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, int waitOnObjectIdToChange) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setWaitOnObjectIdToChange(waitOnObjectIdToChange);
	}

	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, String interactionOption, Area finalDestinationArea) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setFinalDestinationArea(finalDestinationArea);
		setInteractOption(interactionOption);
	}

	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, String waitForItemString) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
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
	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, String interactOption, String waitForItemString) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setInteractOption(interactOption);
		setWaitForItemString(waitForItemString);
	}

	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, String interactOption, String waitForItemString, Area finalDestinationArea) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setInteractOption(interactOption);
		setWaitForItemString(waitForItemString);
		setFinalDestinationArea(finalDestinationArea);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		// If player is not in the selected field, then walk to it
		if (getArea() != null && !getProv().myPlayer().getArea(10).contains(getArea().getRandomPosition())) {
			getProv().getWalking().walk(getArea());
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
		Optional<RS2Object> object = getProv().getObjects().getAll().stream().filter(Objects::nonNull)
				.filter(obj -> obj.getId() == getObjectId()).findFirst();

		if (!object.isPresent()) {
			return false;
		}
		if (getProv().myPlayer().getArea(20).contains(object.get()) && correctStepInQuest()) {
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
			Sleep.sleepUntil(() -> getArea().contains(getProv().myPlayer()), 10000);
		}
		RS2Object object = getProv().getObjects().closest(getObjectId());
		// Optional<RS2Object> object =
		// getProv().getObjects().getAll().stream().filter(Objects::nonNull)
		// .filter(obj -> obj.getId() == getObjectId()).findFirst();

		if (object != null) {
			Area area = object.getArea(3);
			getProv().getWalking().walk(area);

			// When cannot reach object, then turn to webwalking to the position
			if (!getProv().getMap().isWithinRange(object.getPosition(), getProv().myPlayer(), 2)) {
				if (!getProv().getWalking().webWalk(area)) {
					if (getProv().getWalking().walk(area)) {
						getProv().getDoorHandler().handleNextObstacle(area);
					}
				}
			}
			if (getInteractOption() != null && getInteractOption().length() > 0) {
				object.interact(getInteractOption());
				setClickedObject(true);
			} else {
				object.interact();
				setClickedObject(true);
			}
			if (getWaitOnObjectIdToChange() > 0) {
				Sleep.sleepUntil(() -> object.getArea(2).contains(getProv().myPlayer()), 20000, 5000);
			}
		}
		if (getFinalDestinationArea() != null) {
			// Waiting before player is in an area
			Sleep.sleepUntil(() -> getFinalDestinationArea().contains(getProv().myPlayer()), 10000);
		}

		if (getWaitForItemString() != null && getWaitForItemString().length() > 0) {
			Sleep.sleepUntil(() -> getProv().getInventory().contains(getWaitForItemString()), 10000);
		}

	}

	@Override
	public boolean finished() {
		getProv().log("CONF: " + isClickedObject() + " "
				+ (getWaitForItemString() != null && getWaitForItemString().length() > 0) + " "
				+ getFinalDestinationArea() != null);

		if (getWaitForItemString() != null && getWaitForItemString().length() > 0) {
			return isClickedObject() && getProv().getInventory().contains(getWaitForItemString());
		}
		if (getFinalDestinationArea() != null) {
			return getFinalDestinationArea().contains(getProv().myPlayer()) && isClickedObject();
		}
		return isClickedObject();
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
	 * @return the objectId
	 */
	public int getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
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
	 * @return the clickedObject
	 */
	public boolean isClickedObject() {
		return clickedObject;
	}

	/**
	 * @param clickedObject
	 *            the clickedObject to set
	 */
	public void setClickedObject(boolean clickedObject) {
		this.clickedObject = clickedObject;
	}

	/**
	 * @return the finalDestinationArea
	 */
	public Area getFinalDestinationArea() {
		return finalDestinationArea;
	}

	/**
	 * @param finalDestinationArea
	 *            the finalDestinationArea to set
	 */
	public void setFinalDestinationArea(Area finalDestinationArea) {
		this.finalDestinationArea = finalDestinationArea;
	}

	/**
	 * @return the waitOnObjectIdToChange
	 */
	public int getWaitOnObjectIdToChange() {
		return waitOnObjectIdToChange;
	}

	/**
	 * @param waitOnObjectIdToChange
	 *            the waitOnObjectIdToChange to set
	 */
	public void setWaitOnObjectIdToChange(int waitOnObjectIdToChange) {
		this.waitOnObjectIdToChange = waitOnObjectIdToChange;
	}

}
