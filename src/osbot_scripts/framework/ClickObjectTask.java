package osbot_scripts.framework;

import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.qp7.progress.entities.Rock;
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

	private BankItem waitOnItems;

	// The total amount of items you want to get
	private boolean tillInventoryFull;

	private QuestStep quest;

	private Rock rock;

	private String chatboxContainingText;

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
			int objectId, BankItem waitOnItems, boolean tillInventoryFull, QuestStep quest) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setWaitOnItems(waitOnItems);
		setTillInventoryFull(tillInventoryFull);
		setQuest(quest);
	}

	public ClickObjectTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int objectId, BankItem waitOnItems, boolean tillInventoryFull, QuestStep quest, Rock rock) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setWaitOnItems(waitOnItems);
		setTillInventoryFull(tillInventoryFull);
		setQuest(quest);
		setRock(rock);
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
			int objectId, int waitOnObjectIdToChange, String chatboxContainingConfirmation) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
		setWaitOnObjectIdToChange(waitOnObjectIdToChange);
		setChatboxContainingText(chatboxContainingConfirmation);
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
		// if (getArea() != null &&
		// !getApi().myPlayer().getArea(10).contains(getArea().getRandomPosition())) {
		// getApi().getWalking().walk(getArea());
		// }
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
		Optional<RS2Object> object = getApi().getObjects().getAll().stream().filter(Objects::nonNull)
				.filter(obj -> obj.getId() == getObjectId()).findFirst();

		if (!object.isPresent()) {
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

		RS2Object object = getApi().getObjects()
				.closest(obj -> (getArea().contains(obj) && obj.getId() == getObjectId() || (getRock() != null
						&& getRock().hasOre(obj, getApi()) && getApi().myPlayer().getArea(2).contains(obj))));

		if (object != null) {
			if (getQuest() != null) {
				// Also looping with quest
				try {
					getQuest().onLoop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (waitOnItems != null) {
				waitOnItems.setAmountBeforeAction(getApi().getInventory().getAmount(waitOnItems.getName()));
			}
			if (getInteractOption() != null && getInteractOption().length() > 0) {
				object.interact(getInteractOption());
				setClickedObject(true);
			} else {
				object.interact();
				setClickedObject(true);
			}

			// For mining
			if (waitOnItems != null && getRock().hasOre(object, getApi()) && getRock() != null) {
				getApi().log("has ore: " + getRock().hasOre(object, getApi()));

				Sleep.sleepUntil(
						() -> (getApi().getInventory()
								.getAmount(waitOnItems.getName()) == (waitOnItems.getAmountBeforeAction()
										+ waitOnItems.getAmount()))
								|| (!getRock().hasOre(
										getApi().getObjects().closest(o -> o.getX() == object.getX()
												&& o.getY() == object.getY() && o.getId() == object.getId()),
										getApi())),
						20000);
			}

			if (getWaitOnObjectIdToChange() > 0 && waitOnItems == null) {
				Sleep.sleepUntil(() -> object.getArea(2).contains(getApi().myPlayer()), 20000, 5000);
			}
		}
		if (getFinalDestinationArea() != null && waitOnItems == null) {
			// Waiting before player is in an area
			Sleep.sleepUntil(() -> getFinalDestinationArea().contains(getApi().myPlayer()), 10000);
		}

		if (getWaitForItemString() != null && getWaitForItemString().length() > 0 && waitOnItems == null) {
			getApi().getTabs().open(Tab.INVENTORY);
			Sleep.sleepUntil(() -> getApi().getInventory().contains(getWaitForItemString()), 10000);
		}

		// if (object != null && !getProv().getMap().canReach(object.getPosition())) {
		// //Can't reach? Open door & walk to it
		// getProv().getDoorHandler().handleNextObstacle(object.getPosition());
		// getProv().getWalking().walk(object.getPosition());
		// }
		if (object != null && getFinalDestinationArea() != null
				&& !getFinalDestinationArea().contains(getApi().myPlayer())) {

			// Not in area? walking to the area of the object, extra backup
			Sleep.sleepUntil(() -> getFinalDestinationArea().contains(getApi().myPlayer()), 3000);
			getApi().getWalking().webWalk(object.getArea(2));
		}
		// if (object != null && getArea() != null
		// && !getArea().contains(getProv().myPlayer())) {
		//
		// // Not in area? walking to the area of the object, extra backup
		// Sleep.sleepUntil(() -> getArea().contains(getProv().myPlayer()), 3000);
		// getProv().getWalking().webWalk(object.getArea(2));
		// }

	}

	@Override
	public boolean finished() {
		getApi().log("CONF: " + isClickedObject() + " "
				+ (getWaitForItemString() != null && getWaitForItemString().length() > 0) + " "
				+ getFinalDestinationArea() != null);

		//The must be a certain text in the chatbox for it to proceed
		if (getChatboxContainingText() != null && getChatboxContainingText().length() > 0) {
			return getApi().getChatbox().contains(Chatbox.MessageType.GAME,
					getChatboxContainingText()) && isClickedObject();
		}
		if (waitOnItems != null && isTillInventoryFull()) {
			long afterAmount = getApi().getInventory().getAmount(waitOnItems.getName());
			return (((afterAmount == (waitOnItems.getAmountBeforeAction() + waitOnItems.getAmount())
					&& isClickedObject() && getApi().getInventory().contains(getWaitForItemString())))
					|| (isTillInventoryFull() && getApi().getInventory().isFull())
					|| (getApi().myPlayer().isUnderAttack() || getApi().getCombat().isFighting()));
		}
		if (getWaitForItemString() != null && getWaitForItemString().length() > 0) {
			return isClickedObject() && getApi().getInventory().contains(getWaitForItemString());
		}
		if (getFinalDestinationArea() != null) {
			return getFinalDestinationArea().contains(getApi().myPlayer()) && isClickedObject();
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

	/**
	 * @return the waitOnItems
	 */
	public BankItem getWaitOnItems() {
		return waitOnItems;
	}

	/**
	 * @param waitOnItems
	 *            the waitOnItems to set
	 */
	public void setWaitOnItems(BankItem waitOnItems) {
		this.waitOnItems = waitOnItems;
	}

	/**
	 * @return the tillInventoryFull
	 */
	public boolean isTillInventoryFull() {
		return tillInventoryFull;
	}

	/**
	 * @param tillInventoryFull
	 *            the tillInventoryFull to set
	 */
	public void setTillInventoryFull(boolean tillInventoryFull) {
		this.tillInventoryFull = tillInventoryFull;
	}

	/**
	 * @return the quest
	 */
	public QuestStep getQuest() {
		return quest;
	}

	/**
	 * @param quest
	 *            the quest to set
	 */
	public void setQuest(QuestStep quest) {
		this.quest = quest;
	}

	/**
	 * @return the rock
	 */
	public Rock getRock() {
		return rock;
	}

	/**
	 * @param rock
	 *            the rock to set
	 */
	public void setRock(Rock rock) {
		this.rock = rock;
	}

	/**
	 * @return the chatboxContainingText
	 */
	public String getChatboxContainingText() {
		return chatboxContainingText;
	}

	/**
	 * @param chatboxContainingText
	 *            the chatboxContainingText to set
	 */
	public void setChatboxContainingText(String chatboxContainingText) {
		this.chatboxContainingText = chatboxContainingText;
	}

}
