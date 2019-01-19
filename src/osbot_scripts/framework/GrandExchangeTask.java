package osbot_scripts.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.DoricsQuestConfig;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class GrandExchangeTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private ArrayList<BankItem> itemsToBuy = new ArrayList<BankItem>();

	private ArrayList<BankItem> itemsToSell = new ArrayList<BankItem>();

	private LoginEvent login;

	private Script script;

	private QuestStep quest;

	/**
	 * 
	 * @param prov
	 * @param toBuy
	 * @param toSell
	 * @param login
	 * @param script
	 * @param quest
	 */
	public GrandExchangeTask(MethodProvider prov, BankItem[] toBuy, BankItem[] toSell, LoginEvent login, Script script,
			QuestStep quest) {

		setProv(prov);

		setItemsToBuy(new ArrayList<BankItem>(Arrays.asList(toBuy)));
		setItemsToSell(new ArrayList<BankItem>(Arrays.asList(toSell)));

		setLogin(login);
		setScript(script);
		setQuest(quest);

		ge = new GE(getScript());
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

	private GE ge;

	private int openBankDepositItemsAndTakeItems(boolean editPriceWhenTooMuchDifference) throws InterruptedException {
		getApi().log("Opening bank");
		openBank();

		getApi().log("Depositing all items");
		depositItems();

		getApi().log("Taking items to buy and sell");
		return takeItemsToBuyAndSell(editPriceWhenTooMuchDifference);
	}

	private int tries = 0;

	@Override
	public void loop() throws InterruptedException {

		// Walk to the G.E.
		walkToGrandExchangeIfNotThere();

		// Start bank tasks
		if (DatabaseUtilities.getScriptConfigValue(getApi(), getLogin()).equalsIgnoreCase("OAK_LOGS")) {
			openBankDepositItemsAndTakeItems(false);
		} else {
			openBankDepositItemsAndTakeItems(true);
		}

		getApi().log("Closing bank");
		closeBank();
		// End bank tasks

		// Start G.E. tasks
		getApi().log("Opening grand exchange");
		openGrandExchange();

		getApi().log("Aborting all offers if exists");
		abortOffers();

		getApi().log("Collecting");
		collectAllOffersIfDidntCollect();

		getApi().log("Sell all tasked items");
		boolean soldAll = false;
		while (!soldAll) {
			if (allItemsSold()) {
				soldAll = true;
				break;
			}
			soldAll = sellTaskedItems();
		}

		getApi().log("Buy all tasked items");
		boolean boughtAll = false;
		while (!boughtAll) {
			if (allItemsBought()) {
				boughtAll = true;
				break;
			}
			boughtAll = buyTaskedItems();
		}

		getApi().log("Collect all offers if it didn't");
		collectAllOffersIfDidntCollect();
		// End G.E. tasks

		tries++;
	}

	// Path to the G.E.
	private static final ArrayList<Position> GE_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3446, 0), new Position(3183, 3451, 0),
					new Position(3174, 3456, 0), new Position(3165, 3461, 0), new Position(3165, 3461, 0),
					new Position(3165, 3471, 0), new Position(3165, 3481, 0), new Position(3164, 3486, 0)));

	private void walkToGrandExchangeIfNotThere() {
		// If the player is not in the grand exchange area, then walk to it
		if (getApi().myPlayer() != null
				&& !new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
						.contains(getApi().myPlayer())) {

			// Contains in banking area varrock
			if (new Area(new int[][] { { 3180, 3438 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3438 } })
					.contains(getApi().myPlayer())) {
				getApi().getWalking().walkPath(GE_PATH);
			} else {
				getApi().getWalking().webWalk(
						new Area(new int[][] { { 3160, 3494 }, { 3168, 3494 }, { 3168, 3485 }, { 3160, 3485 } }));
				getApi().log("The player has a grand exchange task but isn't there, walking to there");
			}
		}
	}

	/**
	 * Are all the items sold?
	 * 
	 * @return
	 */
	private boolean allItemsSold() {
		boolean done = true;
		for (BankItem sell : itemsToSell) {
			getApi().log("ALL ITEMS SOLD: " + sell.isCompletedTask() + " " + sell.getName());
			if (!sell.isCompletedTask()) {
				done = false;
			}
		}
		return done;
	}

	/**
	 * Did the person collect all the offers?
	 */
	private void collectAllOffersIfDidntCollect() {
		// Collecting items
		if (!getApi().getGrandExchange().isOpen()) {
			// Collect all to the bank at end
			ge.openGe();

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

	/**
	 * Are all the items bought?
	 * 
	 * @return
	 */
	private boolean allItemsBought() {
		boolean done = true;
		for (BankItem buy : itemsToBuy) {
			getApi().log("ALL ITEMS BOUGHT: " + buy.isCompletedTask() + " " + buy.getName());
			if (!buy.isCompletedTask()) {
				done = false;
			}
		}
		return done;
	}

	private boolean buyTaskedItems() throws InterruptedException {
		for (BankItem buy : itemsToBuy) {
			getApi().log("BUY ITEM: " + buy.getName() + " COMPLETED" + buy.isCompletedTask());
			if (buy.isCompletedTask()) {
				continue;
			}

			int buyTries = 0;
			boolean finish = false;

			while (!finish) {
				openGrandExchange();

				finish = new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return ge.createBuyOffer(buy.getItemId(), buy.getName(), buy.getPrice(), buy.getAmount(), true,
								true, true);
					}
				}.sleep();

				// If selling was succesfull
				if (finish) {
					buy.setCompletedTask(finish);
					getApi().log("Buying successful of item: " + buy.getName() + " amount: " + buy.getAmount());
				} else {
					getApi().log("No success, current buy tries: " + buyTries);
					double increase = 1.13;
					int higheredSellPriceBy10Percent = (int) ((buy.getPrice() * buy.getAmount()) * (increase));

					if (buyTries > 0) {
						buy.setPrice(higheredSellPriceBy10Percent / buy.getAmount());
						getApi().log("Didn't finish while selling (price not correct?), setting to price to: "
								+ buy.getPrice() + " of " + buy.getName());
					}

					abortOffers();

					boolean notEnoughCoinsToBuyItem = ((getApi().getInventory().getAmount("Coins")
							+ openBankDepositItemsAndTakeItems(false)) < (higheredSellPriceBy10Percent));

					Sleep.sleepUntil(() -> !notEnoughCoinsToBuyItem, 10000);

					if ((!getQuest().isQuest()) && (notEnoughCoinsToBuyItem)) {
						buy.setCompletedTask(true);
						getApi().log("Doesn't have enough coins to buy the item, setting the item " + buy.getName()
								+ " to " + buy.isCompletedTask() + " for now!");
						finish = true;
					} else if ((getQuest().isQuest()) && (notEnoughCoinsToBuyItem)) {
						buy.setPrice((int) (getApi().getInventory().getAmount("Coins") / buy.getAmount()));
						getApi().log("Didn't have this amount of cash, setting to: " + buy.getPrice());

						if (buyTries > 2 && getQuest() != null && getQuest() instanceof DoricsQuestConfig) {
							String scriptName = getLogin() != null && getLogin().getScript() != null
									? getLogin().getScript()
									: null;

							getApi().log("Restarting application with script name: " + scriptName);
							getQuest().resetStage(scriptName);
							BotCommands.waitBeforeKill(getApi(), "ABC");
						}
					}

					if (buy.isCompletedTask()) {
						getApi().log("Breaking out of this loop, because buying already finished");
						break;
					}

					if (buyTries > 5) {
						getApi().log("Breaking out of this loop, because couldn't buy");
						break;
					}
					buyTries++;
				}

			}

			while (hasToCollect()) {
				collect();

				Thread.sleep(1500);
				getApi().log("Trying to collect items...");
			}

			getApi().log("Succesfully bought item: " + buy.getName() + " amount: " + buy.getAmount());
		}

		return allItemsBought();
	}

	/**
	 * Selling the items that were queued
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	private boolean sellTaskedItems() throws InterruptedException {

		for (BankItem sell : itemsToSell) {
			getApi().log("SELL ITEM: " + sell.getName() + " COMPLETED" + sell.isCompletedTask());
			if (sell.isCompletedTask()) {
				continue;
			}

			int sellTries = 0;
			boolean finish = false;

			getApi().log("Selling the item: " + sell.getName());
			while (!finish) {
				openGrandExchange();

				getApi().log("Going to checkout if selling");

				finish = new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return ge.createSellOffer(sell.getItemId(), sell.getName(), sell.getPrice(), sell.getAmount(),
								true, true, true);
					}
				}.sleep();

				if (finish) {
					sell.setCompletedTask(true);
					getApi().log("Selling successful of item: " + sell.getName() + " amount: " + sell.getAmount());
				} else {
					getApi().log("No success!");

					int loweredSellPriceBy10Percent = (int) (((sell.getPrice() * sell.getAmount()) * 0.9));

					if (sellTries > 0) {
						sell.setPrice(loweredSellPriceBy10Percent / sell.getAmount());
						getApi().log("Didn't finish while selling (price not correct?), setting to price to: "
								+ sell.getPrice() + " of " + sell.getName());
					}

					abortOffers();

					Sleep.sleepUntil(() -> getApi().getInventory().getAmount(sell.getName()) > 0, 7500);
					if (getApi().getInventory().getAmount(sell.getName()) <= 0) {
						openBankDepositItemsAndTakeItems(false);
					}

					if (sell.isCompletedTask()) {
						getApi().log("Breaking out of this loop, because selling already finished");
						break;
					}

					if (sellTries > 5) {
						getApi().log("Breaking out of this loop, because couldn't sell");
						break;
					}
					sellTries++;
				}
			}

			while (hasToCollect()) {
				collect();
				Thread.sleep(1500);
				getApi().log("Trying to collect items...");
			}

			getApi().log("Succesfully sold item: " + sell.getName() + " amount: " + sell.getAmount());
		}

		return allItemsSold();
	}

	/**
	 * Collects the items in the g.e.
	 */
	private void collect() {
		// Collecting the item
		RS2Widget coll = getApi().getWidgets().get(465, 6, 1);
		Sleep.sleepUntil(() -> coll != null, 10000);

		if (coll != null) {
			coll.interact();
		}
	}

	/**
	 * Are there any offers to collect? TODO: not only for 1st slot
	 * 
	 * @return
	 */
	private boolean hasToCollect() {
		RS2Widget coll = getApi().getWidgets().get(465, 6, 1);

		if (coll != null && coll.isVisible()) {
			return true;
		}
		return false;
	}

	/**
	 * If an offer is open, then return true
	 * 
	 * @return
	 */
	private boolean toAbortOffers() {
		return !getApi().getWidgets().containingActions(465, "Abort offer").isEmpty();
	}

	/**
	 * Aborting all current open offers
	 * 
	 * @throws InterruptedException
	 */
	private void abortOffers() {
		if (toAbortOffers()) {
			ArrayList<RS2Widget> toAbort = (ArrayList<RS2Widget>) getApi().getWidgets().containingActions(465,
					"Abort offer");
			for (int i = 0; i < toAbort.size(); i++) {
				toAbort.get(i).interact("Abort offer");
				int finalI = i;
				new ConditionalSleep(10000) {
					@Override
					public boolean condition() {
						return Arrays.asList(toAbort.get(finalI).getInteractOptions()).contains("Abort offer");
					}
				}.sleep();
			}

			Sleep.sleepUntil(() -> getApi().getGrandExchange().collect(), 10000);
		}
	}

	/**
	 * Openeing the grand exhcnage
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	private boolean openGrandExchange() {
		if (getApi().getGrandExchange().isOpen()) {
			return true;
		}

		boolean open = false;

		while (!open) {
			ge.openGe();

			open = getApi().getGrandExchange().isOpen();

			Sleep.sleepUntil(() -> getApi().getGrandExchange().isOpen(), 10000);
		}

		return open;
	}

	// /**
	// * When the item contains both in the sell and buy list, substract sell from
	// buy
	// */
	// private void inSellAndBuyTask() {
	// for (BankItem sell : itemsToSell) {
	// for (BankItem buy : itemsToBuy) {
	//
	// if (!sell.isCompletedTask() && !buy.isCompletedTask()
	// && sell.getName().equalsIgnoreCase(buy.getName())) {
	// int newSellAmount = sell.getAmount() - buy.getAmount();
	// sell.setAmount(newSellAmount > 0 ? newSellAmount : 0);
	//
	// if (newSellAmount >= 0) {
	// buy.setCompletedTask(true);
	// }
	// if (newSellAmount <= 0) {
	// sell.setCompletedTask(true);
	// buy.setCompletedTask(true);
	// }
	//
	// getApi().log("Set sell to: " + sell.isCompletedTask() + " " +
	// sell.getAmount()
	// + " because contained in both lists");
	//
	// }
	// }
	// }
	// }

	private GEPrice price = new GEPrice();

	private int takeItemsToBuyAndSell(boolean editPriceWhenTooMuchDifference) {
		for (BankItem sell : itemsToSell) {
			if (sell.isCompletedTask() || (getApi().getBank().getAmount(sell.getItemId()) <= 0
					&& getApi().getInventory().getAmount(sell.getItemId()) <= 0) || sell.getItemId() < 0) {
				getApi().log("Skipped to sell this items, didnt have this item in the bank " + sell.getName());
				sell.setCompletedTask(true);
				continue;
			}

			int gePrice = 0;
			try {
				gePrice = price.getSellingPrice(sell.getItemId());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int setPriceThroughTask = sell.getPrice();

			double difference = ((gePrice - setPriceThroughTask) / setPriceThroughTask) * 100;
			// When the buying price is difference too big, set to normal TODO add back
			if ((difference > 50 || difference <= 0) && (editPriceWhenTooMuchDifference)) {
				getApi().log(
						"There's too much difference between the set price and the selling price, set to selling price from g.e.");

				// So there's more chacne at selling instantly
				int newPrice = (int) (gePrice * 0.9);
				sell.setPrice(newPrice);
				getApi().log("Set item name: " + sell.getName() + " to selling price: " + newPrice);
			}

			// If player doesn't have enough, then lower it to the amount the player has
			int itemsOfThisInBank = (int) getApi().getBank().getAmount(sell.getName());

			if (itemsOfThisInBank < sell.getAmount()) {
				sell.setAmount(itemsOfThisInBank);
				getApi().log("Set to: " + itemsOfThisInBank + " of item: " + sell.getName()
						+ " because didnt have this amount");
			}

			// When the item contains both in the sell and buy list, substract sell from buy
			// inSellAndBuyTask();

			// Setting to noted or not depending on the items
			getApi().log("amount 12: " + (getApi().getInventory().getAmount(sell.getName() + " " + sell.getAmount())));

			boolean withdrawnSuccess = false;
			while (!withdrawnSuccess) {

				if (sell.isWithdrawNoted()) {
					getApi().getBank().enableMode(BankMode.WITHDRAW_NOTE);
					Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE), 2000);
				} else if (!sell.isWithdrawNoted()
						&& getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE)) {
					getApi().getBank().enableMode(BankMode.WITHDRAW_ITEM);
					Sleep.sleepUntil(() -> getApi().getBank().getWithdrawMode().equals(BankMode.WITHDRAW_ITEM), 2000);
				}

				Sleep.sleepUntil(() -> getApi().getBank().withdraw(sell.getName(), sell.getAmount()), 5000);

				withdrawnSuccess = getApi().getInventory().getAmount(sell.getName()) >= sell.getAmount();
			}

			getApi().log("Withdrawn item: " + (sell.getName()) + " items successfully!");
		}

		for (BankItem buy : itemsToBuy) {
			if (buy.isCompletedTask()) {
				continue;
			}

			int gePrice = 0;
			try {
				gePrice = price.getBuyingPrice(buy.getItemId());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			double difference = ((gePrice - buy.getPrice()) / buy.getPrice()) * 100;

			getApi().log("name: " + buy.getName() + " Got g.e. price: " + gePrice + " perc diff: " + difference
					+ " set price: " + buy.getPrice());

			// When the buying price is difference too big, set to normal
			if ((difference > 50 || difference <= 0) && (editPriceWhenTooMuchDifference)) {
				getApi().log(
						"There's too much difference between the set price and the buying price, set to buying price from g.e.");

				// So there's more chacne at selling instantly
				int newPrice = (int) (gePrice * 1.2);
				buy.setPrice(newPrice);
				getApi().log("Set item name: " + buy.getName() + " to buying price: " + newPrice);
			}

			if (getApi().getBank().getAmount(buy.getName()) >= buy.getAmount()) {
				buy.setCompletedTask(true);
				ifAlreadyHasThenDontSell(buy);
				getApi().log("Set to true, because already has this amount of item in bank!");
				continue;
			}

			try {
				openBank();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int coinsInBank = (int) getApi().getBank().getAmount("Coins");
			int coinsInInventory = (int) getApi().getInventory().getAmount("Coins");
			int itemCostTotal = buy.getAmount() * buy.getPrice();
			int totalCoins = coinsInBank + coinsInInventory;
			int extraCoinsAfterSelling = 0;
			try {
				extraCoinsAfterSelling = getAverageProfitsOfSellingItems();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int totalCoinsAfterSelling = totalCoins + extraCoinsAfterSelling;

			if ((!getQuest().isQuest()) && (totalCoinsAfterSelling < itemCostTotal)) {
				getApi().log("Player doesnt have enough coins to buy this item, to prevent getting stuck, setting "
						+ buy.getName() + " to completed so it can gather more!");
				getApi().log("totalCoinsAfterSelling: " + totalCoinsAfterSelling + " itemCostTotal: " + itemCostTotal);
				buy.setCompletedTask(true);
				continue;
			}
		}

		// Withdrawing all the money
		int coinsToWithdraw = 0;

		for (BankItem buy : itemsToBuy) {
			if (buy.isCompletedTask()) {
				continue;
			}

			coinsToWithdraw += (buy.getAmount() * buy.getPrice());
			getApi().log("increased with: " + (buy.getAmount() * buy.getPrice() + " for item: " + buy.getName()));
		}
		getApi().log("Total coins to withdraw: " + coinsToWithdraw + " should have because now");

		boolean enoughCoinsInInventory = false;
		while (!enoughCoinsInInventory) {

			// Open bank
			try {
				openBank();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Not enough in the bank
			int coinsInBank = (int) getApi().getBank().getAmount("Coins");
			if (coinsToWithdraw > coinsInBank) {
				coinsToWithdraw = coinsInBank;
			}

			// Withdraw
			getApi().getBank().withdraw("Coins", coinsToWithdraw);
			getApi().log("Waiting for coins to withdraw..");

			boolean gotInInventory = getApi().getInventory().getAmount("Coins") >= coinsToWithdraw;

			// Waiting on having it in the inventory
			Sleep.sleepUntil(() -> gotInInventory, 5000);

			// So has enough coins now?
			enoughCoinsInInventory = gotInInventory;
		}
		return (int) getApi().getBank().getAmount("Coins");
	}

	/**
	 * 
	 * @param buy
	 */
	private void ifAlreadyHasThenDontSell(BankItem buy) {
		for (BankItem sell : itemsToSell) {
			if (!sell.isCompletedTask() && !buy.isCompletedTask()
					&& (buy.getName().equalsIgnoreCase(sell.getName()) || buy.getItemId() == sell.getItemId())) {
				int decreaseFromSelling = sell.getAmount() - buy.getAmount() >= 0 ? sell.getAmount() - buy.getAmount()
						: 0;
				boolean alsoFinishedSelling = (decreaseFromSelling == 0);

				if (alsoFinishedSelling) {
					sell.setCompletedTask(true);
				}

				sell.setAmount(decreaseFromSelling);
			}
		}
	}

	/**
	 * The total of the average when selling the items for profit
	 * 
	 * @return
	 * @throws IOException
	 */
	private int getAverageProfitsOfSellingItems() throws IOException {
		int totalPrice = 0;
		for (BankItem sell : itemsToSell) {
			if (sell.isCompletedTask()) {
				continue;
			}

			int gePrice = price.getBuyingPrice(sell.getItemId());
			totalPrice += gePrice;
		}
		return totalPrice;
	}

	private boolean closeBank() throws InterruptedException {
		if (!getApi().getBank().isOpen()) {
			return true;
		}

		boolean finish = false;

		while (!finish) {
			getApi().getBank().close();
			Thread.sleep(1500);

			finish = !getApi().getBank().isOpen();
		}

		return finish;
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	private boolean depositItems() throws InterruptedException {

		// Opens the bank
		openBank();

		if (getApi().getInventory().isEmpty()) {
			return true;
		}

		boolean finish = false;

		while (!finish) {
			Sleep.sleepUntil(() -> getApi().getBank().depositAll(), 5000);
			finish = getApi().getInventory().isEmpty();
		}

		return finish;
	}

	/**
	 * Opens the bank for the G.E.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	private boolean openBank() throws InterruptedException {
		if (getApi().getBank().isOpen()) {
			return true;
		}
		boolean finish = false;

		while (!finish) {
			getApi().getBank().open();

			Thread.sleep(1500);
			finish = getApi().getBank().isOpen();
		}

		return finish;
	}

	@Override
	public boolean finished() {
		// boolean finishedBecauseEmptyLists = getItemsToBuy().size() == 0 ||
		// getItemsToSell().size() == 0;
		boolean finishedBecauseAllSoldAndAllBought = allItemsBought() && allItemsSold();

		return finishedBecauseAllSoldAndAllBought || tries > 3;
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
