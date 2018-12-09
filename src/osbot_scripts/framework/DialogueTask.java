package osbot_scripts.framework;

import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Optional;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.qp7.progress.QuestStep;
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

	private QuestStep quest;

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setConfigQuestId(questConfig);
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
		setConfigQuestId(questConfig);
	}

	public DialogueTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Area area,
			int npcId, int questPointsFinished, QuestStep quest, String... selections) {
		setScriptName(scriptName);
		setProv(prov);
		setArea(area);
		setNpcId(npcId);
		setDialogueSelection(selections);
		setCurrentQuestProgress(questProgress);
		setQuestPointsFinished(questPointsFinished);
		setConfigQuestId(questConfig);
		setQuest(quest);
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
		setConfigQuestId(questConfig);
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
		setConfigQuestId(questConfig);
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
		Optional<NPC> npc = getApi().getNpcs().getAll().stream().filter(Objects::nonNull)
				.filter(findNpc -> findNpc.getId() == this.getNPCId()).findFirst();
		if (!npc.isPresent()) {
			return false;
		}
		// Player must be in reach 20 tiles from npc
		if (getApi().myPlayer().getArea(20).contains(npc.get()) && correctStepInQuest()) {
			// Area must contain player
			if (getArea() != null && !getArea().contains(getApi().myPlayer())) {
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

		getApi().log("quest finished: " + (getQuestProgress() >= getQuestPointsFinished()) + " " + !pendingContinue()
				+ " " + " " + spokenTo + " " + !isInQuestCutscene() + " " + getQuestPointsFinished() + " "
				+ getQuestProgress() + " " + "in cutscene: " + getApi().getMap().isMinimapLocked() + " "
				+ getApi().getWidgets().get(548, 51) == null + " " + getApi().getWidgets().get(231, 1) + " "
						+ (getApi().getCamera().getPitchAngle() == 22 && getApi().getCamera().getYawAngle() == 191));

		// testing TODO: find out if cares

		// if (getArea() != null && !pendingContinue() &&
		// !getProv().getDialogues().isPendingOption()) {
		// // Waiting before player is in an area
		// Sleep.sleepUntil(() -> getArea().contains(getProv().myPlayer()), 10000);
		// }
		if (pendingContinue()) {
			selectContinue();
		} else if (getApi().getDialogues().isPendingOption()) {
			getApi().getDialogues().selectOption(getDialogueSelections());
		}

		if (getQuest() != null) {
			// Also looping with quest
			try {
				getQuest().onLoop();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		attempts++;

		if (attempts > 50 && getArea() != null) {
			getApi().getWalking().webWalk(getArea());
		}

		if (!isInQuestCutscene()) {
			NPC npc = getApi().getNpcs().closest(this.getNPCId());

			if (npc != null && !pendingContinue() && !getApi().getDialogues().isPendingOption()) {
				Sleep.sleepUntil(() -> !pendingContinue(), 5000);
				npc.interact("Talk-to");
				Sleep.sleepUntil(() -> pendingContinue(), 5000);
				spokenTo = true;
			}

			// else if (pendingContinue()) {
			// selectContinue();
			// } else if (getApi().getDialogues().isPendingOption()) {
			// getApi().getDialogues().selectOption(getDialogueSelections());
			// }

			// Wait till has a dialogue to prevent instant finishing

			if (npc != null && !isInQuestCutscene() && !pendingContinue() && !getApi().getDialogues().isPendingOption()
					&& (!getApi().getMap().canReach(npc) || !npc.getArea(3).contains(getApi().myPlayer()))) {
				// If can't reach, then webwalk to it (is costly but has to, otherwise bot gets
				// stuck)
				if (!getApi().getWalking().webWalk(npc.getPosition())) {
					if (getApi().getWalking().walk(npc.getPosition())) {
						getApi().getDoorHandler().handleNextObstacle(npc.getPosition());
					}
				}
			}
		}

	}

	private int attempts = 0;

	@Override
	public boolean finished() {
		// TODO Auto-generated method stu
		if (getWaitForItem() != null && getWaitForItem().length() > 0) {
			return !pendingContinue() && !getApi().getDialogues().isPendingOption() && spokenTo && !isInQuestCutscene()
					&& getApi().getInventory().contains(getWaitForItem())
					&& getApi().getInventory().getAmount(getWaitForItem()) > 0;
		}
		if (getQuestPointsFinished() > 0 && getConfigQuestId() > 0) {
			return getQuestProgress() >= getQuestPointsFinished() && !pendingContinue()
			// && !getApi().getDialogues().isPendingOption()
					&& spokenTo && !isInQuestCutscene();
		}
		return !pendingContinue()
				// && !getApi().getDialogues().isPendingOption()
				&& spokenTo && !isInQuestCutscene();
	}

	/**
	 * Is in the qurdt cutscene?
	 * 
	 * @return
	 */
	public boolean isInQuestCutscene() {

		return
		// getProv().getConfigs().get(1021) == 192 &&
		getApi().getMap().isMinimapLocked() ||
		// getApi().getWidgets().get(548, 51) == null
		// || getApi().getWidgets().get(231, 1) != null
		// ||
				(getApi().getCamera().getPitchAngle() == 22 && getApi().getCamera().getYawAngle() == 191);
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
			this.getApi().getKeyboard().pressKey(KeyEvent.VK_SPACE);
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
		return this.getApi().getWidgets().singleFilter(this.getApi().getWidgets().getAll(),
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

}
