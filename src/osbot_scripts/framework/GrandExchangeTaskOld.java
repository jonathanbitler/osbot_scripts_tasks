package osbot_scripts.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.GrandExchange.Box;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.DoricsQuestConfig;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class GrandExchangeTaskOld extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private ArrayList<BankItem> itemsToBuy = new ArrayList<BankItem>();

	private ArrayList<BankItem> itemsToSell = new ArrayList<BankItem>();

	private LoginEvent login;

	private Script script;

	private QuestStep quest;

	public GrandExchangeTaskOld(MethodProvider prov, BankItem[] toBuy, BankItem[] toSell, LoginEvent login, Script script,
			QuestStep quest) {

		setProv(prov);
		setItemsToBuy(new ArrayList<BankItem>(Arrays.asList(toBuy)));
		setItemsToSell(new ArrayList<BankItem>(Arrays.asList(toSell)));
		setLogin(login);
		setScript(script);
		setQuest(quest);
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

	private boolean hasToCollect() {
		RS2Widget coll = getApi().getWidgets().get(465, 6, 1);

		if (coll != null && coll.isVisible()) {
			return true;
		}
		return false;
	}

	private void collect() {
		if (!getApi().getGrandExchange().isOpen()) {
			// Collect all to the bank at end
			openGrandExchange();
		}

		// Collecting the item
		RS2Widget coll = getApi().getWidgets().get(465, 6, 1);
		Sleep.sleepUntil(() -> coll != null, 10000);

		if (coll != null) {
			coll.interact();
		}
	}

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

	private void depositItems() throws InterruptedException, IOException {
		while (!getApi().getBank().isOpen() && !getApi().getInventory().isEmpty()) {

			if (getApi().getGrandExchange().isOpen()) {
				getApi().getGrandExchange().close();
			}

			getApi().getBank().open();
			Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);

			// Waiting a few seconds before depositing again
			boolean depositBoolean = getApi().getBank().depositAll();

			while (!depositBoolean) {
				getApi().log("Trying to deposit..");

				getApi().getBank().open();

				Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 10000);

				getApi().getBank().depositAll();

				Sleep.sleepUntil(() -> getApi().getInventory().isEmpty(), 2000);

				if (getApi().getInventory().isEmpty()) {
					depositBoolean = true;
				}
			}
			getApi().log("Successfully deposited");

			if (getQuest() != null) {
				// Also looping with quest
				try {
					getQuest().onLoop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Sleep.sleepUntil(() -> getApi().getInventory().isEmpty(), 10000);
			getApi().log("[GRAND EXCHANGE] inventory deposited");
		}

	}

	private BankItem containsOnBuyList(int id) {
		for (BankItem sell : getItemsToBuy()) {
			if (sell.getItemId() == id) {
				return sell;
			}
		}
		return null;
	}

	private void withdrawAllItemsNeeded() throws IOException {

		try {
			depositItems();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (getItemsToSell().size() == 0) {
			// withdrawn[0] = true;
		}

		for (BankItem buy : getItemsToBuy()) {
			if (buy.isCompletedTask()) {
				// withdrawn[1] = true;
				continue;
			}

			// Already has enough
			if (getApi().getBank().getAmount(buy.getName()) >= buy.getAmount()) {
				buy.setCompletedTask(true);
				getApi().log("set compelted for: " + buy.getName() + " to: " + buy.isCompletedTask());
				continue;
			}

			int coinsRequired = buy.getPrice() * buy.getAmount();
			if (getApi().getBank().withdraw(995, coinsRequired)) {
				// withdrawn[1] = true;
			}
		}

		// Withdrawing all the items you want to sell
		for (BankItem sell : getItemsToSell()) {
			if (sell.isCompletedTask()) {
				// withdrawn[0] = true;
				continue;
			}

			while (!getApi().getBank().isOpen()) {
				try {
					getApi().getBank().open();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			int amountInBank = (int) getApi().getBank().getAmount(sell.getName());
			if (amountInBank <= 0) {
				sell.setCompletedTask(true);
				// withdrawn[0] = true;
				continue;
			}

			if (sell.getAmount() > amountInBank) {
				getApi().log("didnt have: " + sell.getAmount() + " to sell, so set to: " + amountInBank);
				sell.setAmount(amountInBank);
			}

			BankItem onBoth = containsOnBuyList(sell.getItemId());
			getApi().log("ONBOTH: " + onBoth);

			if (onBoth != null) {
				if (sell.getAmount() - onBoth.getAmount() > 0) {
					sell.setAmount(sell.getAmount() - onBoth.getAmount());
					getApi().log("on both lists detected, set to: " + sell.getAmount() + " on " + sell.getName());
				} else if (sell.getAmount() - onBoth.getAmount() == 0) {
					sell.setCompletedTask(true);
					onBoth.setCompletedTask(true);
					getApi().log("completed! " + onBoth.getName() + " " + sell.getName());
				} else if (sell.getAmount() - onBoth.getAmount() < 0) {
					sell.setCompletedTask(true);
					onBoth.setAmount(onBoth.getAmount() + (onBoth.getAmount() - sell.getAmount()));
					getApi().log("modified " + onBoth.getAmount() + " " + onBoth.getName());
				}
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
				// withdrawn[0] = true;
			}
		}

		if (getItemsToBuy().size() == 0) {
			// withdrawn[1] = true;
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

	private boolean containgItemsToSellInInventory() {
		for (BankItem containg : getItemsToSell()) {
			if (!getApi().getInventory().contains(containg.getName()) && !containg.isCompletedTask()) {
				return false;
			}
		}
		return true;
	}

	private boolean containgItemsToBuyInInventory() {
		for (BankItem containg : getItemsToSell()) {
			if (!getApi().getInventory().contains(995) && !containg.isCompletedTask()) {
				return false;
			}
		}
		return true;
	}

	private static final ArrayList<Position> GE_PATH = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3446, 0), new Position(3183, 3451, 0),
					new Position(3174, 3456, 0), new Position(3165, 3461, 0), new Position(3165, 3461, 0),
					new Position(3165, 3471, 0), new Position(3165, 3481, 0), new Position(3164, 3486, 0)));

	private int normalTries;

	private int buyTries;

	private int sellTries;

	private boolean toAbortOffers() {
		return !getApi().getWidgets().containingActions(465, "Abort offer").isEmpty();
	}

	private void abortOffers() throws InterruptedException {
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
			MethodProvider.sleep(MethodProvider.random(800, 1200));
			getApi().getGrandExchange().collect();
		}
	}

	private boolean finished = false;

	private int withdrawTries = 0;

	@Override
	public void loop() throws InterruptedException, IOException {
		if (!ranOnStart()) {
			onStart();
		}

		if (getApi() != null && getApi().getClient() != null && getApi().getClient().isLoggedIn() && login != null
				&& login.hasFinished()) {
			MandatoryEventsExecution ev = new MandatoryEventsExecution(getApi(), login);
			ev.fixedMode();
			ev.fixedMode2();
			// ev.executeAllEvents();
		}

		GE ge = new GE(getScript());

		normalTries++;

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

		if (getItemsToSell().size() != 0 && !containgItemsToSellInInventory()) {
			if (!getApi().getBank().isOpen()) {

				// Deposting all the items first
				depositItems();

				// Withdrawing all items
				withdrawAllItemsNeeded();
			}
		}

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

			boolean finished = false;

			// Loop, waiting for the task to get finished
			while (!finished) {

				if (sellTries > 5) {

					// If the selling tries if bigger than 10 and the inventory doesn't contain the
					// item anymore, then setting it to true
					if (!getApi().getInventory().contains(sell.getName())) {
						sell.setCompletedTask(true);
					}
					break;
				}

				// Opening the g.e. if it wasn't open already
				if (!getApi().getGrandExchange().isOpen()) {
					ge.openGe();
					abortOffers();
				}

				// New conditional sleep for selling items
				finished = new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return ge.createSellOffer(sell.getItemId(), sell.getName(), sell.getPrice(), sell.getAmount(),
								true, true, true);
					}
				}.sleep();

				if (!finished) {
					// Waiting 5 seconds to set the price lower when not finished
					Thread.sleep(5000);

					// If it didn't sell, then setting to price of the item lower
					if (sellTries > 1) {
						int sellPrice = (sell.getPrice() - (sell.getPrice() * sell.getAmount() / 100)) > 1
								? (sell.getPrice() - (sell.getPrice() * sell.getAmount() / 100))
								: 1;

						getApi().log(sell.getName() + " set sell price to: " + sellPrice);
					}

				} else {
					// Task has completed, setting the item to true
					sell.setCompletedTask(true);
				}

				while (hasToCollect()) {
					collect();

					getApi().log("Waiting for all items to collect");
					Thread.sleep(2500);
				}

				sellTries++;

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

			boolean finished = false;

			while (!finished) {

				// int coinsInInventory = (int) getApi().getInventory().getAmount(995);
				// int coinsInBank = (int) getApi().getBank().getAmount(995);
				//
				// int ironInInventory = (int) getApi().getInventory().getAmount("Iron ore");
				// int ironInBank = (int) getApi().getBank().getAmount("Iron ore");
				// int clayInInventory = (int) getApi().getInventory().getAmount("Clay");
				// int clayInBank = (int) getApi().getBank().getAmount("Clay");
				// int totalAccountValue = (coinsInBank + coinsInInventory)
				// + ((ironInInventory + ironInBank + clayInInventory + clayInBank) * 90);

				// Checking if account has enough money to begin with
				// if (totalAccountValue < (buy.getAmount() * buy.getPrice())) {
				// getApi().log("not enough coins, closing g.e. and opening bank!");
				// ge.closeGE();
				//
				// Sleep.sleepUntil(() -> !getApi().getGrandExchange().isOpen(), 5000);
				//
				// // Trying with withdrawing all items to sell to get money
				// withdrawAllItemsNeeded();
				//
				// // Waiting till the bank is not open anymore
				// Sleep.sleepUntil(() -> !getApi().getBank().isOpen(), 5000);
				//
				// // If the total value is less than it can buy a bronze pickaxe (for it to
				// // continue for now, not OO, TODO: dynamically instead of hard coded)
				// if (totalAccountValue < Pickaxe.BRONZE.getPrice() &&
				// getApi().getBank().isOpen() && buyTries > 15) {
				// DatabaseUtilities.updateStageProgress(getApi(),
				// AccountStage.OUT_OF_MONEY.name(), 0,
				// login.getUsername(), login);
				// getApi().log("Not enough money.. closing for next stage");
				// BotCommands.waitBeforeKill(getApi(), "BECAUSE OF NOT HAVING ENOUGH MONEY
				// R01");
				// }
				//
				// // Sleeping for 2500 msec
				// Thread.sleep(2500);
				//
				// getApi().log("current tries of buying without enough money: " + buyTries);
				// }

				// Opening the g.e. if it wasn't open already
				if (!getApi().getGrandExchange().isOpen()) {
					ge.openGe();
					abortOffers();
				}

				// Executing the task to buy an offer
				finished = new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return ge.createBuyOffer(buy.getItemId(), buy.getName(), buy.getPrice(), buy.getAmount(), true,
								true, true);
					}
				}.sleep();

				if (!finished) {
					// If the buy wasn't suscessful, then sleep for 5 secondds
					Thread.sleep(5000);

					while (!getApi().getInventory().contains(995)
							|| (getApi().getInventory().getAmount(995) < (buy.getPrice() * buy.getAmount()))) {

						if (getApi().getGrandExchange().isOpen()) {
							getApi().getGrandExchange().close();
						}

						getApi().getBank().open();
						Sleep.sleepUntil(() -> getApi().getBank().isOpen(), 5000);
						getApi().getBank().withdrawAll(995);

						if (withdrawTries > 5) {
							getApi().log("RESETTING");
//							if (getQuest() != null) {
								// getQuest().resetStage(null);

								// finished = true;

								while (!getApi().getBank().isOpen()) {
									try {
										getApi().getBank().open();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									try {
										Thread.sleep(1500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								for (BankItem sell : itemsToSell) {
									if (sell.isCompletedTask()) {
										System.out.println("already completed!");
									}

									getApi().getBank().withdraw(sell.getName(), sell.getAmount());
								}

								getApi().log("SET TO : " + withdrawTries + " SO IT CAN CONTINUE WITH TASK!");

								break;
//							} else {
//								// finished = true;
//								getApi().log("SET TO : " + withdrawTries + " SO IT CAN CONTINUE WITH TASK!");
//								// DatabaseUtilities.updateStageProgress(getApi(),
//								// AccountStage.OUT_OF_MONEY.name(), 0,
//								// login.getUsername(), login);
//								// DatabaseUtilities.updateAccountStatusInDatabase(getApi(), "OUT_OF_MONEY",
//								// getLogin().getUsername(), getLogin());
//								//
//								// getApi().log("Not enough money.. closing for next stage");
//								// BotCommands.waitBeforeKill(getApi(), "BECAUSE OF NOT HAVING ENOUGH MONEY
//								// R01");
//								break;
//							}

						}
						getApi().log("current withdraw tries: " + withdrawTries);

						withdrawTries++;

						Thread.sleep(2500);
					}

					// If couldn't buy more than 0 times, then set the price higher
					if (buyTries > 1) {
						buy.setPrice((getApi().getInventory().getAmount(995) >= (buy.getPrice() * buy.getAmount()))
								? (buy.getPrice() + (buy.getPrice() * buy.getAmount() / 100))
								: (int) (getApi().getInventory().getAmount(995) / buy.getAmount()));

						getApi().log("set buy price to: " + buy.getPrice());
					}

				} else {
					// Successfull
					buy.setCompletedTask(true);
					getApi().log("Successfully bought the items!");
				}

				while (hasToCollect()) {
					collect();

					getApi().log("Waiting for all items to collect");
					Thread.sleep(2500);
				}

				// When the buying tries is bigger than 10, then skip back to the selling
				if (buyTries > 3) {
					break;
				}

				buyTries++;
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

		for (BankItem sell : getItemsToBuy()) {
			getApi().log("sell : " + sell.getName() + " " + sell.isCompletedTask());
		}

		for (BankItem sell : getItemsToBuy()) {
			getApi().log("buy : " + sell.getName() + " " + sell.isCompletedTask());
		}

	}

	public void openGrandExchange() {
		// Is it open?
		if (!getApi().getGrandExchange().isOpen()) {
			NPC npc = getApi().getNpcs().closest(n -> n.getName().equalsIgnoreCase("Grand Exchange Clerk"));
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
		System.out.println("GE task finished: "
				+ ((allSold() && allBought()) || (getQuest() != null && buyTries > 8 && !getQuest().isQuest())
						|| (getItemsToBuy().size() == 0 && getItemsToSell().size() == 0)));

		return ((allSold() && allBought()) || (getQuest() != null && buyTries > 8 && !getQuest().isQuest())
				|| (getItemsToBuy().size() == 0 && getItemsToSell().size() == 0)) || (finished);
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
