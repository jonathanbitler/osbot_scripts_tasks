package osbot_scripts.taskhandling;

import java.util.HashMap;
import java.util.Map.Entry;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;

public class TaskHandler {

	public TaskHandler(MethodProvider provider, QuestStep quest, LoginEvent event, Script script) {
		this.provider = provider;
		this.quest = quest;
		this.event = event;
		this.script = script;
		this.setEvents(new MandatoryEventsExecution(provider));
	}

	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();

	private Task currentTask;

	private QuestStep quest;

	private MethodProvider provider;

	private LoginEvent event;
	
	private Script script;

	private MandatoryEventsExecution events;

	public void decideOnStartTask() {

		if (getCurrentTask() != null) {
			return;
		}
		// The task system
		boolean found = false;
		for (Entry<Integer, Task> entry : tasks.entrySet()) {
			int key = entry.getKey();
			Task task = entry.getValue();
			if (getCurrentTask() == null && quest.getQuestStageStep() >= 0 && key == quest.getQuestStageStep()) {
				setCurrentTask(task);
				getProvider().log("set task to: " + getCurrentTask() + " with key: " + key);
				found = true;
			}

		}
		if (!found) {
			setCurrentTask(tasks.get(0));
			getProvider().log("Couldn't find a corresponding task, setting task to 0 (begin of quest)");
		}
	}

	private long lastTask = System.currentTimeMillis();

	private static final Area TUT_ISLAND_AREA = new Area(
			new int[][] { { 3049, 3128 }, { 3032, 3090 }, { 3054, 3037 }, { 3146, 3048 }, { 3181, 3101 },
					{ 3163, 3129 }, { 3137, 3137 }, { 3131, 3145 }, { 3103, 3140 }, { 3087, 3156 }, { 3054, 3138 } });

	private static final Area TUT_ISLAND_AREA_CAVE = new Area(new int[][] { { 3067, 9521 }, { 3096, 9540 },
			{ 3128, 9540 }, { 3125, 9498 }, { 3085, 9482 }, { 3062, 9500 } });

	private static final Area LUMBRIDGE_AREA_START = new Area(
			new int[][] { { 3212, 3246 }, { 3273, 3246 }, { 3272, 3190 }, { 3212, 3190 } });

	public void taskLoop() throws InterruptedException {
		if (getProvider().getClient().isLoggedIn()) {

			// Checking if acc is on tutorial island
			if (TUT_ISLAND_AREA.contains(getProvider().myPlayer())
					|| TUT_ISLAND_AREA_CAVE.contains(getProvider().myPlayer())) {
				if (getEvent() != null && getEvent().getUsername() != null) {
					DatabaseUtilities.updateStageProgress(getProvider(), AccountStage.TUT_ISLAND.name(), 0,
							getEvent().getUsername());
					BotCommands.killProcess(getScript());
					getProvider().log("Account didn't belong here, sending back to tutorial island");
				}
			}

			// Checking if he account is on fixed mode (client)
			getEvents().fixedMode();
		}

		// Checking is the account is not logged in
		if (!getProvider().getClient().isLoggedIn()) {
			getEvent().execute();
		}

		// Is the account too long logged in without doing anything? Set it to
		// stuck/timeout
		long currentTask = System.currentTimeMillis();

		getProvider().log("Task time: " + (currentTask - lastTask));
		if (lastTask != 0 && currentTask - lastTask > 60000) {
			getProvider().log("Took too much time, proably stuck!");
			if (getEvent() != null && getEvent().getUsername() != null) {
				DatabaseUtilities.updateAccountStatusInDatabase(getProvider(),
						getEvent().getAccountStage().equalsIgnoreCase("WALKING-STUCK") ? "WALKING_STUCK" : "TIMEOUT",
						getEvent().getUsername());
			}
			System.exit(1);
		}

		// Actual task looping
		boolean foundTask = false;
		// Tasks romeo & juilet
		for (Entry<Integer, Task> entry : getTasks().entrySet()) {

//			getProvider().log("Account stage: " + getEvent().getAccountStage());
//			if (getEvent().getAccountStage().equalsIgnoreCase("WALKING-STUCK")) {
//				Position pos = new Position(3235, 3225, 0);
//
//				 getProvider().getWalking().webWalk(pos);
////
////				getProvider().getDoorHandler().handleNextObstacle(pos);
////				getProvider().getWalking().walk(pos);
//
//				getProvider().log("Account stuck, trying to walk to lumbridge");
//				if (LUMBRIDGE_AREA_START.contains(getProvider().myPlayer())) {
//					DatabaseUtilities.updateAccountStatusInDatabase(getProvider(), "AVAILABLE",
//							getEvent().getUsername());
//					BotCommands.killProcess(getScript());
//				}
//				return;
//			}

			int questStepRequired = entry.getKey();
			Task task = entry.getValue();

			// Finding new task when starting
			if (getCurrentTask() == null && questStepRequired == getQuest().getQuestStageStep()
					&& task.requiredConfigQuestStep() == getQuest().getQuestProgress()) {
				setCurrentTask(task);
				getProvider().log("On next task new: " + task.scriptName() + " " + task.requiredConfigQuestStep() + " "
						+ getQuest().getQuestProgress() + " " + getQuest().getQuestStageStep() + " "
						+ questStepRequired);
				foundTask = true;
				// Set a task when null
			}

			// Has the script found a task already?
			if (!foundTask && getCurrentTask() == null) {
				getProvider().log("Not current task, finding next one! " + task.scriptName() + " "
						+ task.requiredConfigQuestStep() + " " + getQuest().getQuestProgress() + " "
						+ getQuest().getQuestStageStep() + " " + questStepRequired);
				// Not this task, next one
				continue;
			}
			// for (Task task : getRomeoAndJuliet().getRomeoAndJulietTasks()) {
			if (getCurrentTask() == null) {
				getProvider().log("System couldnt find a next action, logging out");
				break;
			}
			if (task.requiredConfigQuestStep() == getQuest().getQuestProgress()
					&& (questStepRequired == getQuest().getQuestStageStep()
							|| questStepRequired == getQuest().getQuestStageStep() - 1)) {

				// Waiting for task to finish
				getProvider().log("finish: " + getCurrentTask().finished());

				// Waiting on task to get finished
				while (!getCurrentTask().finished()) {
					// if (getDialogues().isPendingContinuation() &&
					// getRomeoAndJuliet().isInQuestCutscene()) {
					// getDialogues().clickContinue();
					// } else
					if (getProvider().getDialogues().isPendingContinuation()) {
						getProvider().getDialogues().clickContinue();
					} else {
						getCurrentTask().loop();
					}

					getProvider().log("performing task" + getCurrentTask().getClass().getSimpleName());
					Thread.sleep(1000, 1500);
				}

				// Task is finished
				getProvider().log("On next task: " + task.scriptName());
				setCurrentTask(task);

				// Fished the last task at...?
				lastTask = System.currentTimeMillis();

				// Updating stage in database
				if (getEvent() != null && getEvent().getUsername() != null) {
					DatabaseUtilities.updateStageProgress(getProvider(), getQuest().getStage().name(),
							// getQuest().getQuestStageStep() - 1, getEvent().getUsername());
							getQuest().getQuestStageStep(), getEvent().getUsername());
				}

				// Step increased with 1 in database
				getQuest().setQuestStageStep(questStepRequired + 1);
			}
		}
	}

	public HashMap<Integer, Task> getTasks() {
		return tasks;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setTasks(HashMap<Integer, Task> tasks) {
		this.tasks = tasks;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
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
	 * @return the provider
	 */
	public MethodProvider getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(MethodProvider provider) {
		this.provider = provider;
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
	 * @return the events
	 */
	public MandatoryEventsExecution getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(MandatoryEventsExecution events) {
		this.events = events;
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
