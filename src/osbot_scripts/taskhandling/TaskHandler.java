package osbot_scripts.taskhandling;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.Coordinates;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;

public class TaskHandler {

	public TaskHandler(MethodProvider provider, QuestStep quest, LoginEvent event, Script script) {
		this.provider = provider;
		this.quest = quest;
		this.event = event;
		this.script = script;
		this.setEvents(new MandatoryEventsExecution(provider, event));
	}

	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();

	private Task currentTask;

	private QuestStep quest;

	private MethodProvider provider;

	private LoginEvent event;

	private Script script;

	private MandatoryEventsExecution events;

	private long lastTask = System.currentTimeMillis();

	private static final Area TUT_ISLAND_AREA = new Area(
			new int[][] { { 3049, 3128 }, { 3032, 3090 }, { 3054, 3037 }, { 3146, 3048 }, { 3181, 3101 },
					{ 3163, 3129 }, { 3137, 3137 }, { 3131, 3145 }, { 3103, 3140 }, { 3087, 3156 }, { 3054, 3138 } });

	private static final Area TUT_ISLAND_AREA_CAVE = new Area(new int[][] { { 3067, 9521 }, { 3096, 9540 },
			{ 3128, 9540 }, { 3125, 9498 }, { 3085, 9482 }, { 3062, 9500 } });

	private static final Area LUMBRIDGE_AREA_START = new Area(
			new int[][] { { 3212, 3246 }, { 3273, 3246 }, { 3272, 3190 }, { 3212, 3190 } });

	public void taskLoop() throws InterruptedException {

		if (!getQuest().isLoggedIn() && getQuest().getEvent() != null && getQuest() != null
				&& getQuest().getEvent() != null && getQuest().getEvent().hasFinished()) {
			BotCommands.killProcess(getProvider(), getScript(), "BECAUSE OF NOT BEING LOGGED IN ANYMORE E02",
					getEvent());
		}

		// Checking if acc is on tutorial island
		if (Coordinates.isOnTutorialIsland(getProvider())) {
			DatabaseUtilities.updateStageProgress(getProvider(), "TUT_ISLAND", 0, getEvent().getUsername(), getEvent());
			BotCommands.killProcess(getProvider(), getScript(), "BECAUSE SHOULD BE ON TUTORIAL ISLAND E01", getEvent());
		}

		// Checking if at task is resizable or no
		getEvents().fixedMode();
		getEvents().fixedMode2();

		// Is the account too long logged in without doing anything? Set it to
		// stuck/timeout, it will run the client again with WebWalking enabled to
		// un-stuck the person
		long currentTask = System.currentTimeMillis();

		getProvider().log("Task time: " + (currentTask - lastTask));

		// So doesn't timeout when at grand exchange buying stuff
		if (getEvent() != null && getEvent().hasFinished() && !getQuest().isQuest()
				&& new Area(new int[][] { { 3153, 3505 }, { 3153, 3478 }, { 3178, 3478 }, { 3178, 3505 } })
						.contains(getProvider().myPlayer())) {
			lastTask = System.currentTimeMillis();
		}

		// When taking too long to do a task, then set the timeout status
		if ((getQuest().isQuest() && lastTask != 0 && currentTask - lastTask > 600_000)) {
			getProvider().log("Took too much time, proably stuck!");
			if (getEvent() != null && getEvent().getUsername() != null) {
				DatabaseUtilities.updateAccountStatusInDatabase(getProvider(),
						getEvent().getAccountStage().equalsIgnoreCase("WALKING-STUCK") ? "WALKING_STUCK" : "TIMEOUT",
						getEvent().getUsername(), getEvent());
			}
			BotCommands.waitBeforeKill(getProvider(), "BECAUSE IS WALKING STUCK");
		} else if (!getQuest().isQuest() && lastTask != 0 && currentTask - lastTask > 800_000) {
			getProvider().log("Took too much time, proably stuck!");
			BotCommands.waitBeforeKill(getProvider(), "BECAUSE TASKS TOOK TOO MUCH TIME BETWEEN");
		}

		// Quest loop
		// getQuest().onLoop();

		// Has the task corresponding with the character been found or not?
		boolean foundTask = false;

		HashMap<Integer, Task> copyTasks = new HashMap<Integer, Task>(getTasks());

		// Looping over all the tasks
		for (Entry<Integer, Task> entry : copyTasks.entrySet()) {

			int taskAttempts = 0;
			int questStepRequired = entry.getKey();
			Task task = entry.getValue();

			// Finding new task out of the database (the number) when starting
			if (getCurrentTask() == null
					&& (questStepRequired == getQuest().getQuestStageStep() || (currentTask - lastTask) > 30_000)
					&& (task.requiredConfigQuestStep() == getQuest().getQuestProgress() || !getQuest().isQuest())) {
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

			// Sometimes a task can't be found and will try to correct itself afterwards,
			// but this means that he task could still be null, this way it won't break
			if (getCurrentTask() == null) {
				getProvider().log("System couldnt find a next action, breaking out");
				break;
			}

			if ((task.requiredConfigQuestStep() == getQuest().getQuestProgress() || !getQuest().isQuest())
					&& (questStepRequired == getQuest().getQuestStageStep()
							|| questStepRequired == getQuest().getQuestStageStep() - 1
							|| (currentTask - lastTask) > 30_000)) {

				// Waiting for task to finish

				// If null current task, then continue
				if (getCurrentTask() == null) {
					getProvider().log("System couldnt find a next action, breaking out");
					break;
				}
				getProvider().log("finish: " + getCurrentTask().finished());

				// Waiting on task to get finished
				while (!getCurrentTask().finished()) {
					// If null current task, then continue
					if (getCurrentTask() == null) {
						getProvider().log("System couldnt find a next action, breaking out");
						break;
					}

					// If the person is not logged in anymore, but the client is still open, then
					// exit the client
					if ((!getProvider().getClient().isLoggedIn())
							&& (System.currentTimeMillis() - getEvent().getStartTime() > 200_000)) {
						getProvider().log("Person wasn't logged in anymore, logging out!");
						Thread.sleep(5000);
						BotCommands.waitBeforeKill(getProvider(), "BECAUSE OF NOT LOGGED IN ANYMORE E01");
					}

					// Sometimes dialogue pops up without a dialoguetask and could get stuck because
					// of this
					if (getProvider().getDialogues().isPendingContinuation()) {
						getProvider().getDialogues().clickContinue();
					} else {
						// Task loop
						getCurrentTask().loop();
					}

					// Sometimes the script can't perform the task correctly and will get stuck
					// performing the task over and over again without completing it
					taskAttempts++;

					// When is about to task out, try to move the camera every 10th appempt to try
					// to fix itself
					if (taskAttempts >= 50 && taskAttempts % 10 == 0 && getQuest().isQuest()) {
						getProvider().getCamera().movePitch(RandomUtil.getRandomNumberInRange(0, 60));
						getProvider().getCamera().moveYaw(RandomUtil.getRandomNumberInRange(0, 360));
						getProvider().log("Moving camera due to tasking out");
					}

					if (taskAttempts > 75 && getQuest().isQuest()
							&& !getCurrentTask().getClass().getSimpleName().equalsIgnoreCase("ClickObjectTask")) {
						DatabaseUtilities.updateAccountStatusInDatabase(getProvider(), "TASK_TIMEOUT",
								getEvent().getUsername(), getEvent());

						BotCommands.waitBeforeKill(getProvider(), "BECAUSE TASK TIMEOUT ON ATTEMPTS E01");
					}

					// If null current task, then continue
					if (getCurrentTask() == null) {
						getProvider().log("System couldnt find a next action, breaking out");
						break;
					}
					getProvider().log("performing task" + getCurrentTask().getClass().getSimpleName() + " attempt: "
							+ taskAttempts);

					// Checking if at task is resizable or no
					getEvents().fixedMode();
					getEvents().fixedMode2();
					getEvents().executeAllEvents();

					// getProvider().log("14");

					if (getQuest().isQuest()) {
						Thread.sleep(1000, 1500);
					} else {
						Thread.sleep(40, 80);
					}
				}

				// Task is finished, go to the next one
				getProvider().log("On next task: " + task.scriptName());
				setCurrentTask(task);

				// Fished the last task at...? If the task doesnt want to complete, it will be
				// set 'TIMEOUT' in the database, and you will have to manually fix this. The
				// script will try to fix itself on the next run with the quest progress, but
				// can't always do this
				lastTask = System.currentTimeMillis();

				// Updating stage in database
				if (getEvent() != null && getEvent().getUsername() != null) {
					DatabaseUtilities.updateStageProgress(getProvider(), getQuest().getStage().name(),
							getQuest().getQuestStageStep(), getEvent().getUsername(), getEvent());
				}

				// Step increased with 1 in database
				getQuest().setQuestStageStep(questStepRequired + 1);
			}
		}

		// When all the tasks are complete, start with a new one with fresh variables
		if (!getQuest().isQuest()
				&& getQuest().getQuestStageStep() >= (getQuest().getTaskHandler().getTasks().size() - 1)) {
			// getQuest().resetStage(null);
			getQuest().resetStage(getEvent().getScript());
			getProvider().log("[TASKHANDLER] Clearing & restarting all tasks 3");
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
	 * @param script
	 *            the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

}
