package osbot_scripts.framework;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class ClickOnNpcTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private int[] npcId;

	private String interactOption;

	private String waitForItem;

	private int requiredAmountTask;
	
	private Area area;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public ClickOnNpcTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			String interactOption, int[] npcIds, String waitForItem, int amount, Area withinArea) {
		setScriptName(scriptName);
		setProv(prov);
		setInteractOption(interactOption);
		setCurrentQuestProgress(questProgress);
		setNpcId(npcIds);
		setWaitForItem(waitForItem);
		setRequiredAmountTask(amount);
		setArea(withinArea);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		ranOnStart = true;
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return super.getScriptName();
	}

	@Override
	public boolean startCondition() {
		// Optional<GroundItem> object =
		// getProv().getGroundItems().getAll().stream().filter(Objects::nonNull)
		// .filter(obj ->
		// obj.getName().equalsIgnoreCase(getWaitForItemString())).findFirst();
		// if (object.isPresent()) {
		// return false;
		// }
		return false;
	}

	NPC npc;

	@Override
	public void loop() {
		if (!ranOnStart()) {
			onStart();
		}

		NPC npc = getProv().getNpcs().closest(getNpcId());

		// Optional<NPC> npcOptional =
		// getProv().getNpcs().getAll().stream().filter(Objects::nonNull)
		// .filter(npc -> npc.getId() == getNpcId() &&
		// npc.getArea(20).contains(getProv().myPlayer())).findFirst();

		// if (npcOptional.isPresent()) {
		// NPC npc = npcOptional.get();

		if (npc != null && getArea().contains(npc)) {
			npc.interact(getInteractOption());
			this.npc = npc;

			if (getWaitForItem() != null && getWaitForItem().length() > 0) {
				Sleep.sleepUntil(() -> getProv().getInventory().contains(getWaitForItem()), 10000, 2000);
			} else {
				Sleep.sleepUntil(() -> npc.getArea(2).contains(getProv().myPlayer()), 10000, 2000);
			}
		}

	}

	@Override
	public boolean finished() {
		if (npc != null) {
			if (getWaitForItem() != null && getWaitForItem().length() > 0) {
				return getProv().getInventory().contains(getWaitForItem())
						&& getProv().getInventory().getAmount(getWaitForItem()) >= getRequiredAmountTask();
			}
			return npc.getArea(2).contains(getProv().myPlayer());
		}
		return false;
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

	/**
	 * @return the waitForItem
	 */
	public String getWaitForItem() {
		return waitForItem;
	}

	/**
	 * @param waitForItem
	 *            the waitForItem to set
	 */
	public void setWaitForItem(String waitForItem) {
		this.waitForItem = waitForItem;
	}

	public int getRequiredAmountTask() {
		return requiredAmountTask;
	}

	public void setRequiredAmountTask(int requiredAmountTask) {
		this.requiredAmountTask = requiredAmountTask;
	}

	/**
	 * @return the npcId
	 */
	public int[] getNpcId() {
		return npcId;
	}

	/**
	 * @param npcIds
	 *            the npcId to set
	 */
	public void setNpcId(int[] npcIds) {
		this.npcId = npcIds;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

}
