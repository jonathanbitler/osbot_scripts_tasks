package osbot_scripts.task;

import org.osbot.rs07.script.MethodProvider;

public class TaskSkeleton {

	private int currentQuestProgress;

	private MethodProvider prov;

	private String scriptName;

	protected int configQuestId;

	public int getConfigQuestId() {
		return configQuestId;
	}

	public void setConfigQuestId(int configQuestId) {
		this.configQuestId = configQuestId;
	}

	/**
	 * 
	 * @return
	 */
	public boolean correctStepInQuest() {
		return getCurrentQuestProgress() == getQuestProgress();
	}

	/**
	 * Gets current progress
	 * 
	 * @return
	 */
	public final int getQuestProgress() {
		return getProv().getConfigs().get(this.configQuestId);
	}

	/**
	 * @return the scriptName
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * @param scriptName
	 *            the scriptName to set
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * @return the prov
	 */
	public MethodProvider getProv() {
		return prov;
	}

	/**
	 * @param prov
	 *            the prov to set
	 */
	public void setProv(MethodProvider prov) {
		this.prov = prov;
	}

	/**
	 * @return the currentQuestProgress
	 */
	public int getCurrentQuestProgress() {
		return currentQuestProgress;
	}

	/**
	 * @param currentQuestProgress the currentQuestProgress to set
	 */
	public void setCurrentQuestProgress(int currentQuestProgress) {
		this.currentQuestProgress = currentQuestProgress;
	}
}
