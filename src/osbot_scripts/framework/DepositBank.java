package osbot_scripts.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.MiningRimmingtonClay;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class DepositBank extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private ArrayList<BankItem> itemsToDeposit = new ArrayList<BankItem>();

	private boolean depositAll;

	private Area area;

	private QuestStep step;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public DepositBank(String scriptName, int questProgress, MethodProvider prov, BankItem[] toDeposit, Area area,
			QuestStep step) {

		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setItemsToDeposit(new ArrayList<BankItem>(Arrays.asList(toDeposit)));
		setArea(area);
		setStep(step);

	}

	public DepositBank(String scriptName, int questProgress, MethodProvider prov, boolean depositAll, Area area,
			QuestStep step) {

		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setDepositAll(depositAll);
		setArea(area);
		setStep(step);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		ranOnStart = true;

		for (BankItem deposit : getItemsToDeposit()) {
			// Niet genoeg in inventory, zet dan correct
			if (deposit.getAmount() > getApi().getInventory().getAmount(deposit.getItemId())) {
				deposit.setAmount((int) getApi().getInventory().getAmount(deposit.getItemId()));
			}

			if (getApi().getInventory().getAmount(deposit.getItemId()) > 0) {
				deposit.setAmountBeforeAction(getApi().getInventory().getAmount(deposit.getItemId()));
			} else {
				deposit.setCompletedTask(true);
			}
		}
	}

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return super.getScriptName();
	}

	@Override
	public boolean startCondition() {
		return false;
	}

	public void logDeposit() {
		boolean log = (!Config.NO_LOGIN) && (getStep() != null && getStep().getTaskHandler() != null
				&& getStep().getTaskHandler().getCurrentTask() != null);

		if (log) {
			int ironOre = (int) getApi().getInventory().getAmount("Iron ore");
			int clayAmount = (int) getApi().getInventory().getAmount("Clay");

			if (ironOre == 27) {
				DatabaseUtilities.insertLoggingMessage(getApi(), getStep().getEvent(), "DEPOSIT_BANK_ITEM",
						Integer.toString(ironOre), "IRON_ORE");
			}
			if (clayAmount == 27) {
				DatabaseUtilities.insertLoggingMessage(getApi(), getStep().getEvent(), "DEPOSIT_BANK_ITEM",
						Integer.toString(clayAmount), "CLAY");
			}
		}
	}

	@Override
	public void loop() throws InterruptedException, IOException {
		// Onstart
		if (!ranOnStart()) {
			onStart();
		}

		// Not in gebied rond bank, loop er naar
		if (!getArea().contains(getApi().myPlayer())) {
			getApi().getWalking().webWalk(getArea());

			Sleep.sleepUntil(() -> getArea().contains(getApi().myPlayer()), 2500);
		} else {
			// Wanneer in het gebied, open de interface
			if (!getApi().getDepositBox().isOpen()) {
				getApi().getDepositBox().open();

				Sleep.sleepUntil(() -> getApi().getDepositBox().isOpen(), 2500);
			} else {
				// Wanneer interface open, klik op deposit clay -> Deposit-all
				for (BankItem deposit : getItemsToDeposit()) {
					if (deposit.isCompletedTask()) {
						continue;
					}

					// Log
					logDeposit();
					
					if (getApi().getDepositBox().deposit(deposit.getItemId(), deposit.getAmount())) {
						getApi().getDepositBox().close();
						if (getStep() instanceof MiningRimmingtonClay) {
							((MiningRimmingtonClay)getStep()).runs++;
						}
						deposit.setCompletedTask(true);
					}

					Sleep.sleepUntil(() -> depositingFinished(), 2500);
				}
			}
		}
		
		Thread.sleep(750);
	}

	private boolean allDeposited() {
		boolean done = true;
		for (BankItem sell : getItemsToDeposit()) {
			if (!sell.isCompletedTask()) {
				done = false;
			}
		}
		return done;
	}

	/**
	 * Deposited all?
	 * 
	 * @return
	 */
	private boolean depositingFinished() {
		boolean done = true;
		for (BankItem deposit : itemsToDeposit) {
			if (deposit.isInBankToBeSkipped()) {
				continue;
			}

			// deposit.setAmountBeforeAction(getApi().getInventory().getAmount(deposit.getName()));
			if ((deposit.getAmountBeforeAction() + deposit.getAmount()) != (getApi().getInventory()
					.getAmount(deposit.getName()))) {
				done = false;
			}
		}
		return done;
	}

	@Override
	public boolean finished() {
		getApi().log("log: " + depositingFinished() + " " + !getApi().getBank().isOpen());
		return allDeposited();
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
	 * @return the itemsToDeposit
	 */
	public ArrayList<BankItem> getItemsToDeposit() {
		return itemsToDeposit;
	}

	/**
	 * @param toDeposit
	 *            the itemsToDeposit to set
	 */
	public void setItemsToDeposit(ArrayList<BankItem> toDeposit) {
		this.itemsToDeposit = toDeposit;
	}

	/**
	 * @return the depositAll
	 */
	public boolean isDepositAll() {
		return depositAll;
	}

	/**
	 * @param depositAll
	 *            the depositAll to set
	 */
	public void setDepositAll(boolean depositAll) {
		this.depositAll = depositAll;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the step
	 */
	public QuestStep getStep() {
		return step;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(QuestStep step) {
		this.step = step;
	}

}
