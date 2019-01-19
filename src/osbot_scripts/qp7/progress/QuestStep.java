package osbot_scripts.qp7.progress;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.osbot.rs07.api.Client.LoginState;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.anti_ban.MovementManager;
import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.GEPrice;
import osbot_scripts.mouse.MouseTrailApi;
import osbot_scripts.scripttypes.ScriptAbstract;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

public abstract class QuestStep extends MethodProvider {

	/**
	 * Handler for random events (anti-ban)
	 */
	// private final MovementManager movementManager = new MovementManager();

	/**
	 * Handling tasks per quest
	 * 
	 * @param tasks
	 */
	public abstract void timeOutHandling(TaskHandler tasks);

	public void waitOnLoggedIn() {
		boolean loggedIn = false;

		while (!loggedIn) {
			loggedIn = getClient().isLoggedIn();
			log("Waiting on logged in");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private ScriptAbstract scriptAbstract;

	/**
	 * The mouse's trial
	 */
	private MouseTrailApi mouse;

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
	 * Is the script a quest or not?
	 */
	private boolean isQuest;

	/**
	 * 
	 */
	private int doneLaps;

	protected int beginAmount = -1, soldAmount = -1, currentAmount = -1;

	protected long beginTime = -1;

	public boolean isLoggedIn() {
		return //
		isHopping() || //
				getClient().getLoginStateValue() == 30 || //
				getClient().isLoggedIn() || //
				isLoading();
	}

	public boolean isHopping() {
		return //
		getClient().getLoginStateValue() == 45 || //
				getClient().getLoginStateValue() == 25;//
	}

	public boolean isLoading() {
		return //
		getClient().getLoginState() == LoginState.LOADING || //
				getClient().getLoginState() == LoginState.LOADING_MAP;
	}

	/**
	 * Class that contains information to gather data about the g.e.
	 */
	private GEPrice gePrices = new GEPrice();

	/**
	 * Returns the g.e. cost for an item
	 * 
	 * @param itemId
	 * @param buy
	 * @return
	 */
	public int getGrandexchangePriceForItem(int itemId, boolean buy) {
		int cost = 0;
		try {
			cost = buy ? gePrices.getBuyingPrice(itemId) : gePrices.getSellingPrice(itemId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cost;
	}

	/**
	 * Formatting time to be HOUR:MINUTE:SECONDS
	 * 
	 * @param ms
	 * @return
	 */
	protected final String formatTime(final long ms) {
		long s = ms / 1000, m = s / 60, h = m / 60;
		s %= 60;
		m %= 60;
		h %= 24;
		return String.format("%02d:%02d:%02d", h, m, s);
	}

	/**
	 * 
	 * @param instructorName
	 */
	public QuestStep(int questStartNpc, int configQuestId, AccountStage stage, LoginEvent event, Script script,
			boolean isQuest) {
		this.questStartNpc = questStartNpc;
		this.configQuestId = configQuestId;
		this.stage = stage;
		this.questStageStep = 0;
		this.setEvent(event);
		this.script = script;
		this.taskHandler = new TaskHandler((MethodProvider) this, (QuestStep) this, event, script);
		this.setMouse(new MouseTrailApi((MethodProvider) this));
		this.setQuest(isQuest);
	}

	public void initializeAbstract() {
		this.scriptAbstract.setEvent(getEvent());
		this.scriptAbstract.setEvents(new MandatoryEventsExecution(this, getEvent()));
		this.scriptAbstract.setProvider(this);
		this.scriptAbstract.setQuest(this);
		this.scriptAbstract.setScript(getScript());
	}

	public boolean killTask = false;

	public boolean isKillTask() {
		return killTask;
	}

	public void setKillTask(boolean killTask) {
		this.killTask = killTask;
	}
	//
	// public void initializeMovementManager() {
	// int frequency = 100000, deviation = 80000;
	// movementManager.register(new RandomCameraEvent(frequency, deviation));
	// movementManager.register(new RandomMouseEvent(frequency, deviation));
	// // log("Registered two gaussian-random input movement events.");
	// // log("Movement event frequency: " + frequency + "ms, deviation: " +
	// deviation
	// // + "ms");
	// }

	/**
	 * Resetting the stagesbotha
	 */
	public void resetStage(String taskName) {
		if (getEvent() != null && getEvent().getUsername() != null && taskName != null) {
			DatabaseUtilities.updateStageProgress(this, taskName, 0, getEvent().getUsername(), getEvent());
		}
		setDoneLaps(getDoneLaps() + 1);
		setQuestStageStep(0);
		getTaskHandler().setCurrentTask(null);
		getTaskHandler().getTasks().clear();
		onStart();
		if (getTaskHandler().getTasks().size() > 0) {
			getTaskHandler().setCurrentTask(getTaskHandler().getTasks().get(0));
		}
		setKillTask(true);
		log("[TASKHANDLER] Clearing & restarting all tasks 2");
	}

	/**
	 * Gets current progress
	 * 
	 * @return
	 */
	public final int getQuestProgress() {
		if (this.configQuestId > 0) {
			return getConfigs().get(this.configQuestId);
		}
		return -1;
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
	 * @throws IOException
	 */
	public abstract void onLoop() throws InterruptedException, IOException;

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
						|| widget.getMessage().contains("Click to continue")
						|| widget.getMessage().contains("Please wait...")));
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
		try {
			this.questStageStep = questStageStep;
		} catch (Exception e) {
			BotCommands.killProcess(this, getScript(), "BECAUSE COULDN'T FETCH STAGE STEP PROGRESS E01", getEvent());
		}
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
	 * @param event
	 *            the event to set
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
	 * @param script
	 *            the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

	/**
	 * @param mouse
	 *            the mouse to set
	 */
	public void setMouse(MouseTrailApi mouse) {
		this.mouse = mouse;
	}

	/**
	 * 
	 * @return
	 */
	public MouseTrailApi getTrailMouse() {
		return this.mouse;
	}

	/**
	 * @return the isQuest
	 */
	public boolean isQuest() {
		return isQuest;
	}

	/**
	 * @param isQuest
	 *            the isQuest to set
	 */
	public void setQuest(boolean isQuest) {
		this.isQuest = isQuest;
	}

	/**
	 * @return the doneLaps
	 */
	public int getDoneLaps() {
		return doneLaps;
	}

	/**
	 * @param doneLaps
	 *            the doneLaps to set
	 */
	public void setDoneLaps(int doneLaps) {
		this.doneLaps = doneLaps;
	}

	/**
	 * @return the scriptAbstract
	 */
	public ScriptAbstract getScriptAbstract() {
		return scriptAbstract;
	}

	/**
	 * @param scriptAbstract
	 *            the scriptAbstract to set
	 */
	public void setScriptAbstract(ScriptAbstract scriptAbstract) {
		this.scriptAbstract = scriptAbstract;
		initializeAbstract();
		// initializeMovementManager();
	}

	/**
	 * @return the movementManager
	 */
	// public MovementManager getMovementManager() {
	// return movementManager;
	// }

}
