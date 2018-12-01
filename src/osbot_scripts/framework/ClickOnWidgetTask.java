package osbot_scripts.framework;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class ClickOnWidgetTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private String waitOnItem;

	private int waitOnItemAmount;

	private int[] widgetIds;

	private String interactOption;

	private RS2Widget widget;

	private int objectId;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public ClickOnWidgetTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			String interactOption, String waitOnItem, int waitOnItemAmount, int objectId, int... widgetIds) {
		setScriptName(scriptName);
		setProv(prov);
		setInteractOption(interactOption);
		setCurrentQuestProgress(questProgress);
		setWaitOnItem(waitOnItem);
		setWaitOnItemAmount(waitOnItemAmount);
		setWidgetIds(widgetIds);
		setObjectId(objectId);
	}

	public ClickOnWidgetTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			String interactOption, int objectId, int... widgetIds) {
		setScriptName(scriptName);
		setProv(prov);
		setWidgetIds(widgetIds);
		setInteractOption(interactOption);
		setCurrentQuestProgress(questProgress);
		setObjectId(objectId);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		if (getWidgetIds().length == 2) {// 2 and higher
			setWidget(getApi().getWidgets().get(getWidgetIds()[0], getWidgetIds()[1]));
		} else if (getWidgetIds().length == 3) {
			setWidget(getApi().getWidgets().get(getWidgetIds()[0], getWidgetIds()[1], getWidgetIds()[2]));
		}
		getApi().log("set widget to: " + getWidget());
		ranOnStart = true;
	}

	public RS2Widget getRS2WidgetInterface() {
		RS2Widget widget = null;
		if (getWidgetIds().length == 2) {// 2 and higher
			widget = getApi().getWidgets().get(getWidgetIds()[0], getWidgetIds()[1]);
		} else if (getWidgetIds().length == 3) {
			widget = getApi().getWidgets().get(getWidgetIds()[0], getWidgetIds()[1], getWidgetIds()[2]);
		}
		getApi().log("set widget to: " + widget);
		return widget;
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

	private long lastClick = 0;

	@Override
	public void loop() {
		if (!ranOnStart()) {
			// here something only run once
		}
		if (getRS2WidgetInterface() == null && getApi().myPlayer().getAnimation() == -1
				&& !getApi().myPlayer().isAnimating() && (System.currentTimeMillis() - lastClick > 40000)) {

			if (getObjectId() != -1) {
				RS2Object rs2Obj = getApi().getObjects().closest(obj -> obj.getId() == getObjectId());

				if (rs2Obj != null) {
					if (!getApi().getMap().canReach(getApi().myPlayer())) {
						getApi().getWalking().webWalk(rs2Obj.getPosition());
					} else if (getRS2WidgetInterface() == null) {

						if (rs2Obj.hasAction("Spin")) {
							rs2Obj.interact("Spin");
						} else {
							rs2Obj.interact();
						}
						lastClick = System.currentTimeMillis();
					}
				}
			}
		}
		if (getRS2WidgetInterface() != null) {
			getRS2WidgetInterface().interact(getInteractOption());
		}
		if (getWaitOnItem() != null) {
			Sleep.sleepUntil(() -> getWaitOnItemAmount() >= getApi().getInventory().getAmount(getWaitOnItem()), 10000);
		}

		Sleep.sleepUntil(() -> getRS2WidgetInterface() == null, 10000);
	}

	@Override
	public boolean finished() {
		getApi().log("widget: " + getRS2WidgetInterface());

		if (getWaitOnItem() != null && getWaitOnItem().length() > 0) {
			if (getApi().getInventory().getAmount(getWaitOnItem()) >= getWaitOnItemAmount()) {
				return true;
			}
			return getApi().getInventory().getAmount(getWaitOnItem()) >= getWaitOnItemAmount()
					&& getRS2WidgetInterface() == null;
		}
		return getRS2WidgetInterface() == null;
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

	public String getWaitOnItem() {
		return waitOnItem;
	}

	public int getWaitOnItemAmount() {
		return waitOnItemAmount;
	}

	public int[] getWidgetIds() {
		return widgetIds;
	}

	public void setWaitOnItem(String waitOnItem) {
		this.waitOnItem = waitOnItem;
	}

	public void setWaitOnItemAmount(int waitOnItemAmount) {
		this.waitOnItemAmount = waitOnItemAmount;
	}

	public void setWidgetIds(int[] widgetIds) {
		this.widgetIds = widgetIds;
	}

	/**
	 * @return the widget
	 */
	public RS2Widget getWidget() {
		return widget;
	}

	/**
	 * @param widget
	 *            the widget to set
	 */
	public void setWidget(RS2Widget widget) {
		this.widget = widget;
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

}
