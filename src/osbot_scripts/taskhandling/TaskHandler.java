package osbot_scripts.taskhandling;

import java.util.HashMap;
import java.util.Map.Entry;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;

public class TaskHandler {
	
	public TaskHandler(MethodProvider provider, QuestStep quest) {
		this.provider = provider;
		this.quest = quest;
	}

	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();

	private Task currentTask;

	private QuestStep quest;

	private MethodProvider provider;

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

}
