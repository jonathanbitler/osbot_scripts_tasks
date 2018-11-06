package osbot_scripts.framework;

import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.AreaInterface;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class DropItemTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private String[] itemName;

	private String interactOption;

	private boolean dropAllExcept;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public DropItemTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			String interactOption, String... itemName) {
		setScriptName(scriptName);
		setProv(prov);
		setItemName(itemName);
		setInteractOption(interactOption);
		setCurrentQuestProgress(questProgress);
	}

	public DropItemTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			String interactOption, boolean dropAllExcept, String... itemName) {
		setScriptName(scriptName);
		setProv(prov);
		setItemName(itemName);
		setInteractOption(interactOption);
		setDropAllExcept(dropAllExcept);
		setCurrentQuestProgress(questProgress);
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

	@Override
	public void loop() {
		if (!ranOnStart()) {
			onStart();
		}

		if (isDropAllExcept()) {
			getApi().getInventory().dropAllExcept(getItemName()[0]);
		} else {
			for (int i = 0; i < getItemName().length; i++) {
				String item = getItemName()[i];
				if (getApi().getInventory().contains(item)) {
					if (getApi().getInventory().drop(item)) {
						getApi().log("Dropped item");
					}
				}

			}
		}

	}

	/**
	 * Have all the items been dropped?
	 * 
	 * @return
	 */
	private boolean droppedAllItems() {
		boolean success = true;
		for (int i = 0; i < getItemName().length; i++) {
			String item = getItemName()[i];
			if (getApi().getInventory().contains(item)) {
				success = false;
			}
		}
		return success;
	}

	@Override
	public boolean finished() {
		if (isDropAllExcept()) {
			return !getApi().getInventory().contains(getItemName()[0]);
		}
		return droppedAllItems();
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

	public String[] getItemName() {
		return itemName;
	}

	public void setItemName(String[] itemName2) {
		this.itemName = itemName2;
	}

	/**
	 * @return the dropAllExcept
	 */
	public boolean isDropAllExcept() {
		return dropAllExcept;
	}

	/**
	 * @param dropAllExcept
	 *            the dropAllExcept to set
	 */
	public void setDropAllExcept(boolean dropAllExcept) {
		this.dropAllExcept = dropAllExcept;
	}

}
