package osbot_scripts.qp7.progress;

import java.awt.event.KeyEvent;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

public abstract class QuestStep extends MethodProvider {

	/**
	 * The script
	 */
	private Script script;
	
	/**
	 * The taskHandler
	 */
	private TaskHandler taskHandler;
	
	/**
	 * The name of the instructor of the current stage
	 */
	private final int questStartNpc;

	/**
	 * Config ID for quest
	 */
	private final int configQuestId;

	/**
	 * 
	 */
	private LoginEvent event;
	/**
	 * 
	 */
	private int questStageStep;

	/**
	 * 
	 */
	private AccountStage stage;

	/**
	 * 
	 * @param instructorName
	 */
	public QuestStep(int questStartNpc, int configQuestId, AccountStage stage, LoginEvent event, Script script) {
		this.questStartNpc = questStartNpc;
		this.configQuestId = configQuestId;
		this.stage = stage;
		this.questStageStep = 0;
		this.setEvent(event);
		this.script = script;
		this.taskHandler = new TaskHandler((MethodProvider) this, (QuestStep) this, event, script);
	}

	/**
	 * Gets current progress
	 * 
	 * @return
	 */
	public final int getQuestProgress() {
		return getConfigs().get(this.configQuestId);
	}

	/**
	 * Is in the qurdt cutscene?
	 * 
	 * @return
	 */
	public boolean isInQuestCutscene() {
		return
		// getProv().getConfigs().get(1021) == 192 &&
		getMap().isMinimapLocked() || getWidgets().get(548, 51) == null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean pickupItem(Object object) {
		GroundItem item = null;
		if (object instanceof Integer) {
			item = getGroundItems().closest((Integer) object);
		} else if (object instanceof String) {
			item = getGroundItems().closest((String) object);
		}
		if (item != null) {
			item.interact();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public boolean clickObject(Object object, String retrieveItemName, String interactionObject) {
		RS2Object rs2Object = null;
		if (object instanceof Integer) {
			rs2Object = getObjects().closest((Integer) object);
		} else if (object instanceof String) {
			rs2Object = getObjects().closest((String) object);
		}
		if (rs2Object != null) {
			rs2Object.interact(interactionObject);
			return Sleep.sleepUntil(() -> getInventory().getItem(retrieveItemName) != null, 60000);
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean walkToPosition(List<Position> walkPath, Area finalDestinationArea) {
		if (getSettings().getRunEnergy() > 50 && !getSettings().isRunning()) {
			getSettings().setRunning(true);
		}
		if (getWalking().walkPath(walkPath)) {
			getDoorHandler().handleNextObstacle(finalDestinationArea);
			return true;
		}
		return false;
	}

	/**
	 * Clicking an object
	 * 
	 * @param id
	 * @return
	 */
	public boolean clickObjectToArea(Object object, Area area) {
		RS2Object rs2Object = null;
		if (object instanceof Integer) {
			rs2Object = getObjects().closest((Integer) object);
		} else if (object instanceof String) {
			rs2Object = getObjects().closest((String) object);
		}
		if (rs2Object != null) {
			rs2Object.interact();
			return Sleep.sleepUntil(() -> area.contains(myPlayer()), 60000);
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	// public boolean talkWithNpc() {
	// if (!getDialogues().inDialogue()) {
	// talkToQuestNpc();
	// return true;
	// } else if (getDialogues().inDialogue()) {
	// if (!getDialogues().selectOption(getDialogueClickOptions())) {
	// selectContinue();
	// return true;
	// }
	// return false;
	// }
	// return false;
	// }

	/**
	 * Loops through the section
	 * 
	 * @throws InterruptedException
	 */
	public abstract void onLoop() throws InterruptedException;

	/**
	 * On start
	 */
	public abstract void onStart();

	/**
	 * The section has been completed
	 * 
	 * @return
	 */
	public abstract boolean isCompleted();

	/**
	 * Returns the next main state
	 * 
	 * @return
	 */
	public abstract MainState getNextMainState();

	/**
	 * Returns the constructor of the area
	 * 
	 * @return
	 */
	protected NPC getMainQuestNpc() {
		return getNpcs().closest(this.questStartNpc);
	}

	/**
	 * Returns the widget for and checks if contains click here to continue
	 * 
	 * @return
	 */
	private RS2Widget getContinueWidget() {
		return getWidgets().singleFilter(getWidgets().getAll(),
				widget -> widget.isVisible() && (widget.getMessage().contains("Click here to continue")
						|| widget.getMessage().contains("Click to continue")));
	}

	/**
	 * Selects to continue the talk
	 * 
	 * @return
	 */
	protected boolean selectContinue() {
		RS2Widget continueWidget = getContinueWidget();
		if (continueWidget == null) {
			return false;
		}
		if (continueWidget.getMessage().contains("Click here to continue")) {
			getKeyboard().pressKey(KeyEvent.VK_SPACE);
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		} else if (continueWidget.interact()) {
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		}
		return false;
	}

	/**
	 * Talking with the construcot
	 */
	protected void talkToQuestNpc() {
		NPC instructor = getNpcs().closest(this.questStartNpc);
		if (instructor != null) {
			instructor.interact("Talk-to");
			Sleep.sleepUntil(() -> pendingContinue(), 5000, 3000);
		}
	}

	/**
	 * 
	 */
	protected void enableRunning() {
		if (getSettings().getRunEnergy() > 50 && !getSettings().isRunning()) {
			getSettings().setRunning(true);
		}
	}

	/**
	 * Waits till the continue widget is visible
	 * 
	 * @return
	 */
	protected boolean pendingContinue() {
		RS2Widget continueWidget = getContinueWidget();
		return continueWidget != null && continueWidget.isVisible();
	}

	/**
	 * @return the questStartNpc
	 */
	public int getQuestStartNpc() {
		return questStartNpc;
	}

	/**
	 * @return the configQuestId
	 */
	public int getConfigQuestId() {
		return configQuestId;
	}

	/**
	 * @return the questStageStep
	 */
	public int getQuestStageStep() {
		return questStageStep;
	}

	/**
	 * @param questStageStep
	 *            the questStageStep to set
	 */
	public void setQuestStageStep(int questStageStep) {
		this.questStageStep = questStageStep;
	}

	/**
	 * @return the stage
	 */
	public AccountStage getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(AccountStage stage) {
		this.stage = stage;
	}

	/**
	 * @return the taskHandler
	 */
	public TaskHandler getTaskHandler() {
		return taskHandler;
	}

	/**
	 * @param taskHandler
	 *            the taskHandler to set
	 */
	public void setTaskHandler(TaskHandler taskHandler) {
		this.taskHandler = taskHandler;
	}

	/**
	 * @return the event
	 */
	public LoginEvent getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(LoginEvent event) {
		this.event = event;
	}

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

}
