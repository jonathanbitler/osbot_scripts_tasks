package osbot_scripts.framework;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.GrandExchange.Box;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class GrandExchangeTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private ArrayList<BankItem> itemsToBuy = new ArrayList<BankItem>();

	private ArrayList<BankItem> itemsToSell = new ArrayList<BankItem>();

	private LoginEvent login;

	private Script script;

	private int tries;

	/**
	 * 
	 * @param scriptName
	 * @param questProgress
	 * @param questConfig
	 * @param prov
	 * @param area
	 * @param objectId
	 */
	public GrandExchangeTask(String scriptName, int questProgress, MethodProvider prov, BankItem[] toBuy,
			BankItem[] toSell, LoginEvent login, Script script) {

		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setItemsToBuy(new ArrayList<BankItem>(Arrays.asList(toBuy)));
		setItemsToSell(new ArrayList<BankItem>(Arrays.asList(toSell)));
		setLogin(login);
		setScript(script);
	}

	public GrandExchangeTask(MethodProvider prov, BankItem[] toBuy, BankItem[] toSell, LoginEvent login,
			Script script) {

		setProv(prov);
		setItemsToBuy(new ArrayList<BankItem>(Arrays.asList(toBuy)));
		setItemsToSell(new ArrayList<BankItem>(Arrays.asList(toSell)));
		setLogin(login);
		setScript(script);
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

	private boolean withdrawn[] = new boolean[2];

	private void collectToInventory() {
		// Collecting items
		if (!getApi().getGrandExchange().isOpen()) {
			// Collect all to the bank at end
			openGrandExchange();

			// Collecting the item
			RS2Widget coll = getApi().getWidgets().get(465, 6, 0);
			Sleep.sleepUntil(() -> coll != null, 10000);

			if (coll != null) {
				coll.interact("Collect to inventory");
			}

			// Closes the grand exchange
			getApi().getGrandExchange().close();
		} else {
			// Collecting the item
			RS2Widget coll = getApi().getWidgets().get(465, 6, 0);
			Sleep.sleepUntil(() -> coll != null, 10000);

			if (coll != null) {
				coll.interact("Collect to inventory");
			}
		}
	}

	private void depositItems() throws InterruptedException {
		if (!getApi().getBank().isOpen()) {
			getApi().getBank().open();
			Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);

			// Finding the deposit button
			RS2Widget depositAll = getApi().getWidgets().get(12, 41);
			Sleep.sleepUntil(() -> depositAll != null, 10000);

			// Wait until it clicked on the button and inventory is empty
			if (depositAll != null) {
				depositAll.interact("Deposit inventory");
			}
			Sleep.sleepUntil(() -> getApi().getInventory().isEmpty(), 10000);
			getApi().log("[GRAND EXCHANGE] inventory deposited");
		}

	}

	private void withdrawAllItemsNeeded() {

		if (getItemsToSell().size() == 0) {
//			withdrawn[0] = true;
		}

		// Withdrawing all the items you want to sell
		for (BankItem sell : getItemsToSell()) {
			if (sell.isCompletedTask()) {
//				withdrawn[0] = true;
				continue;
			}

			int amountInBank = (int) getApi().getBank().getAmount(sell.getName());
			if (amountInBank <= 0) {
				sell.setCompletedTask(true);
//				withdrawn[0] = true;
				continue;
			}
			if (amountInBank < sell.getAmount()) {
				sell.setAmount(amountInBank);
			}

			if (sell.isWithdrawNoted()) {
				getApi().getBank().enableMode(BankMode.WITHDRAW_NOTE);
				Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE), 10000);
			} else if (!sell.isWithdrawNoted() && getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE)) {
				getApi().getBank().enableMode(BankMode.WITHDRAW_ITEM);
				Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_ITEM), 10000);
			}
			if (getApi().getBank().withdraw(sell.getName(), sell.getAmount())) {
//				withdrawn[0] = true;
			}
		}

		if (getItemsToBuy().size() == 0) {
//			withdrawn[1] = true;
		}

		for (BankItem buy : getItemsToBuy()) {
			if (buy.isCompletedTask()) {
//				withdrawn[1] = true;
				continue;
			}

			int coinsRequired = buy.getPrice() * buy.getAmount();
			if (getApi().getBank().withdraw(995, coinsRequired)) {
//				withdrawn[1] = true;
			}
		}
	}

	/**
	 * 
	 * @param box
	 * @return
	 */
	private int getUsedBox(Box box) {
		int number = 0;
		if (box == Box.BOX_1) {
			number = 7;
		} else if (box == Box.BOX_2) {
			number = 8;
		} else if (box == Box.BOX_3) {
			number = 9;
		}
		return number;
	}

	private void abortOffer(int number) {
		RS2Widget widget = getApi().getWidgets().get(465, number, 2);
		Sleep.sleepUntil(() -> getApi().getWidgets().containingActions(465, "Abort offer").size() > 0, 10000);

		if (widget != null) {
			widget.interact("Abort offer");

			// Collecting the item
			collectToInventory();
		}
	}
	

	@Override
	public void loop() throws InterruptedException {
		if (!ranOnStart()) {
			onStart();
		}

		tries++;

		// If the player is not in the grand exchange area, then walk to it
		if (!new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
				.contains(getApi().myPlayer())) {
			Position ge = new Position(3165, 3487, 0);
			getApi().getWalking().webWalk(ge);
			getApi().log("The player has a grand exchange task but isn't there, walking to there");
		}

		// First collect everything to the inventory is there's a collect option
		collectToInventory();

		// If the inventory is full, deposit everything first
		// if (getApi().getInventory().isFull()) {
		if (!getApi().getBank().isOpen()) {

			// Deposting all the items first
			depositItems();

			// Withdrawing all items
			withdrawAllItemsNeeded();
		}

		// Must have withdrawn the items from the bank before continueing
//		if (!withdrawn[0] || !withdrawn[1]) {
//			return;
//		}

		// Sell the items, if the items can't be sold, then it takes the item from the
		// bank to sell as an extra bankup
		for (BankItem sell : getItemsToSell()) {
			// Has already sold everything
			if (sell.isCompletedTask()) {
				continue;
			}

			getApi().log("Trying to sell item: " + sell.getName() + " " + sell.getItemId() + " " + sell.getAmount());

			// Open the G.E.
			openGrandExchange();

			// Item id must be better than -1 & price be more than 0
			Box box = getBox();
			boolean sellItem = getApi().getGrandExchange().sellItem(
					(sell.isWithdrawNoted() ? (sell.getItemId() + 1) : sell.getItemId()), sell.getPrice(),
					sell.getAmount());

			if (!sell.isCompletedTask() && sellItem) {
				getApi().log("Selling the item progress continued");
				getApi().log("Using the box: " + box + " to sell item: " + sell.getName());

				Sleep.sleepUntil(
						() -> getApi().getGrandExchange().getStatus(box) != GrandExchange.Status.EMPTY
								&& getApi().getGrandExchange().getStatus(box) != GrandExchange.Status.INITIALIZING_SALE,
						10000);

				getApi().log("Current box status: " + getApi().getGrandExchange().getStatus(box));

				// Sale not instant sold
				if (getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.PENDING_SALE) {
					int number = getUsedBox(box);

					// Aborting the offer
					abortOffer(number);

					sell.setPrice((int) (sell.getPrice() * 0.75));
					sell.setCompletedTask(false);
					getApi().log("Item price decreased to " + sell.getPrice());

				}

				// Sale successful
				if (getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.FINISHED_SALE
						|| getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.COMPLETING_SALE) {
					getApi().log("Sell was successfull, collecting to inventory");

					// Collecting the item
					collectToInventory();

					sell.setCompletedTask(true);
				}

			} else if (!sell.isCompletedTask() && !sellItem) {
				getApi().log("Couldn't sell item, will try next time with a new task");
//				if (sell.isCompletedTask()) {
//					sell.setCompletedTask(false);
//				}
				// sell.setCompletedTask(true);
			}
		}

		// Just some cooldown
		Thread.sleep(2500);

		for (BankItem buy : getItemsToBuy()) {

			// Has already bought everything
			if (buy.isCompletedTask()) {
				continue;
			}

			// Open G.E. interface
			openGrandExchange();

			Box box = getBox();
			getApi().log("trying to buy item: " + buy.getItemId() + " " + buy.getName() + " " + buy.getPrice() + " "
					+ buy.getAmount());
			boolean buyItem = getApi().getGrandExchange().buyItem(buy.getItemId(), buy.getName(), buy.getPrice(),
					buy.getAmount());
			getApi().log("trying to buy item: " + buy.getName());

			if (!buy.isCompletedTask() && buyItem) {

				Sleep.sleepUntil(
						() -> getApi().getGrandExchange().getStatus(box) != GrandExchange.Status.EMPTY
								&& getApi().getGrandExchange().getStatus(box) != GrandExchange.Status.INITIALIZING_BUY,
						10000);

				getApi().log("box status: " + getApi().getGrandExchange().getStatus(box));

				// Buy was not instant
				if (getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.PENDING_BUY
						|| getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.FINISHED_BUY) {
					int number = getUsedBox(box);

					// Aborting the offer
					abortOffer(number);

					// Collecting the item
					collectToInventory();

					buy.setPrice((int) (buy.getPrice() * 1.25));
					getApi().log("Price increased to " + buy.getPrice());
					buy.setCompletedTask(false);
				}

				// Buy was instant
				if (getApi().getGrandExchange().getStatus(box) == GrandExchange.Status.COMPLETING_BUY) {
					getApi().log("Buy was successfull");
					buy.setCompletedTask(true);

					// Collecting the item
					collectToInventory();
				}

			} else if (!buy.isCompletedTask() && !buyItem) {
				getApi().log("Didn't have enough money");
			}

		}

		// Just some cooldown
		Thread.sleep(2500);

		// Collect to inventory
		collectToInventory();

		// Deposit all items
		depositItems();

		// Close the bank
		getApi().getBank().close();

	}

	public void openGrandExchange() {
		// Is it open?
		if (!getApi().getGrandExchange().isOpen()) {
			NPC npc = getApi().getNpcs().closest(n -> n.getId() == 2149);
			if (npc != null) {
				npc.interact("Exchange");
			}
			Sleep.sleepUntil(() -> getApi().getGrandExchange().isOpen(), 10000);
		}

	}

	/**
	 * 
	 * @return
	 */
	private Box getBox() {
		Box finalBox = null;
		Box[] box = { Box.BOX_1, Box.BOX_2, Box.BOX_3 };

		for (int i = 0; i < 3; i++) {
			if (getApi().getGrandExchange().buyItems(box[i])
					&& getApi().getGrandExchange().getStatus(box[i]) == GrandExchange.Status.EMPTY) {
				finalBox = box[i];
				break;
			}
		}
		return finalBox;
	}

	/**
	 * Are all the items sold?
	 * 
	 * @return
	 */
	private boolean allSold() {
		boolean done = true;
		for (BankItem sell : getItemsToSell()) {
			if (!sell.isCompletedTask()) {
				done = false;
			}
		}
		return done;
	}

	private boolean allBought() {
		boolean done = true;
		for (BankItem sell : getItemsToBuy()) {
			if (!sell.isCompletedTask()) {
				done = false;
			}
		}
		return done;
	}

	@Override
	public boolean finished() {
		// getApi().log(allSold()+" "+allBought()+"
		// "+!getApi().getGrandExchange().isOpen());
		// getApi().log(allSold() && allBought() &&
		// !getApi().getGrandExchange().isOpen());
		return (allSold() && allBought()) || (tries > 2)
				|| (getItemsToBuy().size() == 0 && getItemsToSell().size() == 0);
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
	 * @return the itemsToBuy
	 */
	public ArrayList<BankItem> getItemsToBuy() {
		return itemsToBuy;
	}

	/**
	 * @param itemsToBuy
	 *            the itemsToBuy to set
	 */
	public void setItemsToBuy(ArrayList<BankItem> itemsToBuy) {
		this.itemsToBuy = itemsToBuy;
	}

	/**
	 * @return the itemsToSell
	 */
	public ArrayList<BankItem> getItemsToSell() {
		return itemsToSell;
	}

	/**
	 * @param itemsToSell
	 *            the itemsToSell to set
	 */
	public void setItemsToSell(ArrayList<BankItem> itemsToSell) {
		this.itemsToSell = itemsToSell;
	}

	public LoginEvent getLogin() {
		return login;
	}

	public void setLogin(LoginEvent login) {
		this.login = login;
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
