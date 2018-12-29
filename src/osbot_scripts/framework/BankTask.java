package osbot_scripts.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultDesktopManager;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class BankTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private ArrayList<BankItem> itemsToWithdraw = new ArrayList<BankItem>();

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
	public BankTask(String scriptName, int questProgress, MethodProvider prov, BankItem[] toWithdraw,
			BankItem[] toDeposit, Area area, QuestStep step) {

		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setItemsToDeposit(new ArrayList<BankItem>(Arrays.asList(toDeposit)));
		setItemsToWithdraw(new ArrayList<BankItem>(Arrays.asList(toWithdraw)));
		setArea(area);
		setStep(step);

	}

	public BankTask(String scriptName, int questProgress, MethodProvider prov, boolean depositAll,
			BankItem[] toWithdraw, Area area, QuestStep step) {

		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setDepositAll(depositAll);
		setItemsToWithdraw(new ArrayList<BankItem>(Arrays.asList(toWithdraw)));
		setArea(area);
		setStep(step);
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
		return false;
	}

	@Override
	public void loop() throws InterruptedException, IOException {
		if (!ranOnStart()) {
			onStart();
		}

		if (!getArea().contains(getApi().myPlayer())) {
			getApi().getWalking().webWalk(getArea());
		}

		if (new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
				.contains(getApi().myPlayer())) {
			NPC npc = getApi().getNpcs().closest("Banker");
			if (npc != null) {
				npc.interact("Collect");

				Sleep.sleepUntil(() -> getApi().getWidgets().get(402, 3) != null
						&& getApi().getWidgets().get(402, 3).isVisible(), 5000);
				RS2Widget widgetCollect = getApi().getWidgets().get(402, 3);

				if (widgetCollect != null && widgetCollect.isVisible()) {
					widgetCollect.interact();
				}
				Thread.sleep(5000);
			}
		}

		if (!getApi().getBank().isOpen()) {
			getApi().getBank().open();
			Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);
		}

		if (getApi().getBank().isOpen()) {
			if (getStep() != null) {
				getStep().onLoop();
			}

			// Depositing all items to the bank, can't deposit other items, because already
			// doing all
			if (isDepositAll()) {
				boolean isEmpty = false;

				while (!isEmpty) {
					isEmpty = getApi().getInventory().isEmpty();

					if (!isEmpty) {
						if (!getApi().getBank().isOpen()) {
							getApi().getBank().open();
							Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);
						}
						getApi().getBank().depositAll();
						Sleep.sleepUntil(() -> getApi().getInventory().isEmpty(), 10000);
						getApi().log("Depositing!");
					}
				}
			}

			// Withdrawing items
			for (BankItem withdraw : itemsToWithdraw) {

				// Withdrawing for pickaxes always picking the latets one
				if (withdraw.getName().equalsIgnoreCase("pickaxe")) {
					if (getApi().getSkills().getStatic(Skill.MINING) <= 3
							&& getApi().getBank().contains("Bronze pickaxe")) {
						withdraw.setName("Bronze pickaxe");
					} else if (getApi().getSkills().getStatic(Skill.MINING) > 3
							&& getApi().getSkills().getStatic(Skill.MINING) < 6
							&& getApi().getBank().contains("Iron pickaxe")) {
						withdraw.setName("Iron pickaxe");
					} else if (getApi().getSkills().getStatic(Skill.MINING) >= 6
							&& getApi().getSkills().getStatic(Skill.MINING) < 21
							&& getApi().getBank().contains("Steel pickaxe")) {
						withdraw.setName("Steel pickaxe");
					} else if (getApi().getSkills().getStatic(Skill.MINING) >= 21
							&& getApi().getSkills().getStatic(Skill.MINING) < 31
							&& getApi().getBank().contains("Mithril pickaxe")) {
						withdraw.setName("Mithril pickaxe");
					} else if (getApi().getSkills().getStatic(Skill.MINING) >= 31
							&& getApi().getSkills().getStatic(Skill.MINING) < 41
							&& getApi().getBank().contains("Adamant pickaxe")) {
						withdraw.setName("Adamant pickaxe");
					} else if (getApi().getSkills().getStatic(Skill.MINING) >= 41
							&& getApi().getBank().contains("Rune pickaxe")) {
						withdraw.setName("Rune pickaxe");
					}

					// If couldn't find it, then take the best pickaxe it has
					if (withdraw.getName().equalsIgnoreCase("pickaxe")) {
						if (getApi().getBank().contains("Bronze pickaxe")) {
							withdraw.setName("Bronze pickaxe");
						}
						if (getApi().getBank().contains("Iron pickaxe")) {
							withdraw.setName("Iron pickaxe");
						}
						if (getApi().getBank().contains("Steel pickaxe")) {
							withdraw.setName("Steel pickaxe");
						}
						if (getApi().getBank().contains("Mithril pickaxe")) {
							withdraw.setName("Mithril pickaxe");
						}
						if (getApi().getBank().contains("Adamant pickaxe")) {
							withdraw.setName("Adamant pickaxe");
						}
						if (getApi().getBank().contains("Rune pickaxe")) {
							withdraw.setName("Rune pickaxe");
						}

					}
					getApi().log("Changed item to: " + withdraw.getName());
				}

				// Withdrawing for pickaxes always picking the latets one
				if (withdraw.getName().equalsIgnoreCase("axe")) {
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 1
							&& getApi().getBank().contains("Bronze axe")) {
						withdraw.setName("Bronze axe");
					}
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 3
							&& getApi().getBank().contains("Iron axe")) {
						withdraw.setName("Iron axe");
					}
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 6
							&& getApi().getBank().contains("Steel axe")) {
						withdraw.setName("Steel axe");
					}
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 21
							&& getApi().getBank().contains("Mithril axe")) {
						withdraw.setName("Mithril axe");
					}
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 31
							&& getApi().getBank().contains("Adamant axe")) {
						withdraw.setName("Adamant axe");
					}
					if (getApi().getSkills().getStatic(Skill.WOODCUTTING) >= 41
							&& getApi().getBank().contains("Rune axe")) {
						withdraw.setName("Rune axe");
					}

					getApi().log("Changed item to: " + withdraw.getName());
				}

				if (!getApi().getBank().isOpen()) {
					getApi().getBank().open();
					Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);
				}
				// If can't find the item, then skip to the next one
				if (!getApi().getBank().contains(withdraw.getName())) {
					withdraw.setInBankToBeSkipped(true);
					withdraw.setCompletedTask(true);
					getApi().log("couldn't find the requested item id");
					continue;
				}

				// Sets the amount before it withdraws the item, this is to check the amount of
				// items it expected afterwards
				withdraw.setAmountBeforeAction(getApi().getInventory().getAmount(withdraw.getName()));

				// If the item is supposed to be withdrawn noted, then click noted, otherwise
				// click back to not noted

				boolean finish = false;

				while (!finish) {

					if (withdraw.isWithdrawNoted()) {
						getApi().getBank().enableMode(BankMode.WITHDRAW_NOTE);
						Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE),
								10000);
						finish = getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE);
					} else if (!withdraw.isWithdrawNoted()
							&& getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE)) {
						getApi().getBank().enableMode(BankMode.WITHDRAW_ITEM);
						Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_ITEM),
								10000);
						finish = getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_ITEM);
					} else {
						finish = true;
					}
					getApi().log("Waiting for correct withdraw method in bank!");
					Thread.sleep(2500);
				}

				// When not having enough, set the value to the current value in the bank
				if (getApi().getBank().getAmount(withdraw.getName()) < withdraw.getAmount()) {
					int price = (int) getApi().getBank().getAmount(withdraw.getName());
					int inv = (int) getApi().getInventory().getAmount(withdraw.getName());
					// if (withdraw.getPrice() > 0) {
					// withdraw.setPrice(price + inv);
					// getApi().log("[BANKTASK] set price to: "+price + inv);
					// }
					withdraw.setAmount(price);
				}

				boolean withdrawn = false;

				while (!withdrawn) {
					getApi().getBank().withdraw(withdraw.getName(), withdraw.getAmount());

					if (getApi().getInventory().getAmount(withdraw.getName()) >= withdraw.getAmount()) {
						withdrawn = true;
						withdraw.setCompletedTask(true);
						getApi().log("Successfully withdraw");
					} else {
						getApi().log("Trying to withdraw.... failed");
					}
					Sleep.sleepUntil(() -> getApi().getInventory().contains(withdraw.getName())
							&& getApi().getInventory().getAmount(
									withdraw.getName()) == (withdraw.getAmount() + withdraw.getAmountBeforeAction()),
							5000);
				}
				// if ()) {
				// withdraw.setCompletedTask(true);
				// }

			}

			// Depositing items
			for (BankItem deposit : itemsToDeposit) {

				// If can't find the item, then skip to the next one
				if (!getApi().getInventory().contains(deposit.getName())) {
					deposit.setInBankToBeSkipped(true);
					deposit.setCompletedTask(true);
					getApi().log("couldn't find the requested item id");
					continue;
				}

				deposit.setAmountBeforeAction(getApi().getInventory().getAmount(deposit.getName()));

				// When not having enough, set the value to the current value in inventory
				if (getApi().getInventory().getAmount(deposit.getName()) < deposit.getAmount()) {
					int price = (int) getApi().getInventory().getAmount(deposit.getName());
					// if (deposit.getPrice() > 0) {
					// deposit.setPrice(price);
					// getApi().log("[BANKTASK] set price to: "+price);
					// }
					deposit.setAmount(price);
				}

				if (getApi().getBank().deposit(deposit.getName(), deposit.getAmount())) {
					deposit.setCompletedTask(true);
				}

				boolean deposited = false;
				while (!deposited) {
					Sleep.sleepUntil(() -> getApi().getBank().deposit(deposit.getName(), deposit.getAmount()), 5000);
					deposited = getApi().getInventory().getAmount(deposit.getName()) <= 0;
					getApi().log("Waiting for item to deposit...");
				}

				Sleep.sleepUntil(() -> deposit.getAmountBeforeAction() + deposit.getAmount() == getApi().getInventory()
						.getAmount(deposit.getName()), 10000);
			}

			// Closes the bank
			getApi().getBank().close();
			Sleep.sleepUntil(() -> !getApi().getBank().isOpen(), 10000);
		}

	}

	private boolean allWithdrawn() {
		boolean done = true;
		for (BankItem sell : getItemsToWithdraw()) {
			if (!sell.isCompletedTask()) {
				done = false;
			}
		}
		return done;
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
	 * Withdrawed all?
	 * 
	 * @return
	 */
	private boolean withdrawingFinished() {
		boolean done = true;
		for (BankItem withdraw : itemsToWithdraw) {
			// Wil skip the selection
			if (withdraw.isInBankToBeSkipped()) {
				continue;
			}

			if ((!getApi().getInventory().contains(withdraw.getName())) || (getApi().getInventory()
					.getAmount(withdraw.getName()) != (withdraw.getAmount() + withdraw.getAmountBeforeAction()))) {
				done = false;
			}
		}
		return done;
	}

	public boolean withdrawnAll() {
		boolean withdrawnAll = true;
		for (BankItem withdraw : itemsToWithdraw) {
			if (!getApi().getInventory().contains(withdraw.getName())) {
				withdrawnAll = false;
			}
		}
		return withdrawnAll;
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
		getApi().log("log: " + depositingFinished() + " " + withdrawingFinished() + " " + !getApi().getBank().isOpen());
		return (depositingFinished() && withdrawingFinished() && !getApi().getBank().isOpen() && allDeposited()
				&& allWithdrawn() && withdrawnAll());
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
	 * @return the itemsToWithdraw
	 */
	public ArrayList<BankItem> getItemsToWithdraw() {
		return itemsToWithdraw;
	}

	/**
	 * @param toWithdraw
	 *            the itemsToWithdraw to set
	 */
	public void setItemsToWithdraw(ArrayList<BankItem> toWithdraw) {
		this.itemsToWithdraw = toWithdraw;
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
