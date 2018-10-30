package osbot_scripts.framework;

import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.task.AreaInterface;
import osbot_scripts.task.DialogueInterface;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class DialogueTask extends TaskSkeleton implements Task, AreaInterface, DialogueInterface {

	private boolean ranOnStart = false;

	private Area area;

	private int npcId;

	private String[] dialogueSelection;

	private boolean spokenTo = false;

	private String waitForItem;

	private int questPointsFinished;

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setDialogueSelection(selections);
		setCurrentQuestProgress(questProgress);
	}

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, int questPointsFinished, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setDialogueSelection(selections);
		setCurrentQuestProgress(questProgress);
		setQuestPointsFinished(questPointsFinished);
	}

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, String waitForItem, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setDialogueSelection(selections);
		setCurrentQuestProgress(questProgress);
		setWaitForItem(waitForItem);
	}

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, String waitForItem, int questPointsFinished, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setDialogueSelection(selections);
		setCurrentQuestProgress(questProgress);
		setWaitForItem(waitForItem);
		setQuestPointsFinished(questPointsFinished);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		// If player is not in the selected field, then walk to it
		// if (getArea() != null && !getArea().contains(getProv().myPlayer())) {
		// getProv().getWalking().walk(getArea());
		// }
		ranOnStart = true;
	}

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return this.area;
	}

	@Override
	public String[] getDialogueSelections() {
		// TODO Auto-generated method stub
		return this.dialogueSelection;
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return super.getScriptName();
	}

	@Override
	public int getNPCId() {
		// TODO Auto-generated method stubs
		return this.npcId;
	}

	@Override
	public boolean startCondition() {
		Optional<NPC> npc = getProv().getNpcs().getAll().stream().filter(Objects::nonNull)
				.filter(findNpc -> findNpc.getId() == this.getNPCId()).findFirst();
		if (!npc.isPresent()) {
			return false;
		}
		// Player must be in reach 20 tiles from npc
		if (getProv().myPlayer().getArea(20).contains(npc.get()) && correctStepInQuest()) {
			// Area must contain player
			if (getArea() != null && !getArea().contains(getProv().myPlayer())) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void loop() {
		if (!ranOnStart()) {
			onStart();
		}

		// testing TODO: find out if cares

		// if (getArea() != null && !pendingContinue() &&
		// !getProv().getDialogues().isPendingOption()) {
		// // Waiting before player is in an area
		// Sleep.sleepUntil(() -> getArea().contains(getProv().myPlayer()), 10000);
		// }

		if (!isInQuestCutscene()) {
			NPC npc = getProv().getNpcs().closest(this.getNPCId());

			if (npc != null && !pendingContinue() && !getProv().getDialogues().isPendingOption()) {
				npc.interact("Talk-to");
				Sleep.sleepUntil(() -> pendingContinue(), 5000);
				spokenTo = true;
			} else if (pendingContinue()) {
				selectContinue();
			} else if (getProv().getDialogues().isPendingOption()) {
				getProv().getDialogues().selectOption(getDialogueSelections());
			}
			// Wait till has a dialogue to prevent instant finishing

			if (npc != null && !isInQuestCutscene()
					&& (!getProv().getMap().canReach(npc) || !npc.getArea(3).contains(getProv().myPlayer()))) {
				// If can't reach, then webwalk to it (is costly but has to, otherwise bot gets
				// stuck)
				if (!getProv().getWalking().webWalk(npc.getPosition())) {
					if (getProv().getWalking().walk(npc.getPosition())) {
						getProv().getDoorHandler().handleNextObstacle(npc.getPosition());
					}
				}
			}
		}

	}

	@Override
	public boolean finished() {
		// TODO Auto-generated method stub
		if (getQuestPointsFinished() > 0 && getConfigQuestId() > 0) {
			int step = getProv().getConfigs().get(getConfigQuestId());
			return getQuestPointsFinished() == step && !pendingContinue() && !getProv().getDialogues().isPendingOption()
					&& spokenTo && !isInQuestCutscene();
		}
		if (getWaitForItem() != null && getWaitForItem().length() > 0) {
			return !pendingContinue() && !getProv().getDialogues().isPendingOption() && spokenTo && !isInQuestCutscene()
					&& getProv().getInventory().contains(getWaitForItem());
		}
		return !pendingContinue() && !getProv().getDialogues().isPendingOption() && spokenTo && !isInQuestCutscene();
	}

	/**
	 * Is in the qurdt cutscene?
	 * 
	 * @return
	 */
	public boolean isInQuestCutscene() {
		return
		// getProv().getConfigs().get(1021) == 192 &&
		getProv().getMap().isMinimapLocked() || getProv().getWidgets().get(548, 51) == null;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @param npcId
	 *            the npcId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}

	/**
	 * @return the dialogueSelection
	 */
	public String[] getDialogueSelection() {
		return dialogueSelection;
	}

	/**
	 * @param dialogueSelection
	 *            the dialogueSelection to set
	 */
	public void setDialogueSelection(String[] dialogueSelection) {
		this.dialogueSelection = dialogueSelection;
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
			this.getProv().getKeyboard().pressKey(KeyEvent.VK_SPACE);
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		} else if (continueWidget.interact()) {
			Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1000, 500);
			return true;
		}
		return false;
	}

	/**
	 * Returns the widget for and checks if contains click here to continue
	 * 
	 * @return
	 */
	private RS2Widget getContinueWidget() {
		return this.getProv().getWidgets().singleFilter(this.getProv().getWidgets().getAll(),
				widget -> widget.isVisible() && (widget.getMessage().contains("Click here to continue")
						|| widget.getMessage().contains("Click to continue")));
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

	/**
	 * @return the questPointsFinished
	 */
	public int getQuestPointsFinished() {
		return questPointsFinished;
	}

	/**
	 * @param questPointsFinished
	 *            the questPointsFinished to set
	 */
	public void setQuestPointsFinished(int questPointsFinished) {
		this.questPointsFinished = questPointsFinished;
	}

}
