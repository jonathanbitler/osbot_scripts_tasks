package osbot_scripts.qp7.progress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bottypes.BotType;
import osbot_scripts.bottypes.ClayOre;
import osbot_scripts.bottypes.IronMiningType;
import osbot_scripts.bottypes.PlayerTask;
import osbot_scripts.bottypes.RimmingtonMining;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.WidgetActionFilter;
import osbot_scripts.framework.GEPrice;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.hopping.WorldHop;
import osbot_scripts.scripttypes.mining.clay.ClayMiningRimmington;
import osbot_scripts.scripttypes.mining.clay.ClayMiningWestOfVarrock;
import osbot_scripts.scripttypes.mining.clay.IronMiningWestOfVarrock;
import osbot_scripts.util.Sleep;

public class TradingBody extends MethodProvider {

	public boolean tradingDone = false;

	public long timeout = -1;

	public int tries = 0;

	public String lastTradedPlayer = null;

	private QuestStep quest;

	public HashMap<String, Integer> itemMap = new HashMap<String, Integer>();

	private ArrayList<BankItem> itemsToMule = new ArrayList<BankItem>();

	public TradingBody(QuestStep quest) {
		this.setQuest(quest);
	}

	private void initializeItems() {
		if (itemsToMule.size() == 0) {
			BotType type = PlayerTask.getSingleton().getBotType((MethodProvider) quest, quest.getEvent());
			if (type instanceof ClayOre || type instanceof RimmingtonMining) {
				addClayToItemsToMule();
			} else if (type instanceof IronMiningType) {
				addIronToItemsToMule();
			}
			for (BankItem item : itemsToMule) {
				log("Following items to mule: " + item.getName());
			}
		}
	}

	private void addIronToItemsToMule() {
		// itemsToMule.add(new BankItem("Iron ore", 441, 440, true));
		itemsToMule.add(new BankItem("Clay", 435, 434));
		itemsToMule.add(new BankItem("Coins", 995));
	}

	private void addClayToItemsToMule() {
		itemsToMule.add(new BankItem("Clay", 435, 434));
		itemsToMule.add(new BankItem("Coins", 995));
	}

	public void handleGeneralSecond(boolean checkIfExistsInDatabase) throws InterruptedException {
		log("time: " + (System.currentTimeMillis() - timeout));

		// Not in g.e.? Walk to it
		walkToGrandExchange();

		// So they don't stack on eachother
		walkOutsideTheHotSpotToMeetEachother();

		log("last traded player: " + getTrade().getLastRequestingPlayer());

		if ((!Config.TRADE_OVER_CLAY && !getInventory().contains("Coins"))
				|| (Config.TRADE_OVER_CLAY && getInventory().isEmpty())) {

			// Player is trading you
			if (getTrade().getLastRequestingPlayer() != null
					&& getTrade().getLastRequestingPlayer().getName() != null) {
				lastTradedPlayer = getTrade().getLastRequestingPlayer().getName();
			}

			// When that player is not null
			if (lastTradedPlayer != null) { // &&
											// lastTradedPlayer.equalsIgnoreCase(getEvent().getTradeWith()))
											// {
				boolean inDatabase = DatabaseUtilities.accountContainsInDatabase(this, lastTradedPlayer,
						quest.getEvent());

				// Is in database? then trade
				if (!checkIfExistsInDatabase) {
					trade(lastTradedPlayer, new HashMap<String, Integer>(), true, "placeholder");
				} else if (checkIfExistsInDatabase && inDatabase) {
					trade(lastTradedPlayer, new HashMap<String, Integer>(), true, "placeholder");
				}

				log("Accepting trade request... doing actions...");
			} else if (getTrade().isCurrentlyTrading()) {
				trade(lastTradedPlayer, new HashMap<String, Integer>(), true, "placeholder");
				log("Trading....");
			} else if (getPlayers().closest(quest.getEvent().getTradeWith()) != null && getBank().isOpen()) {
				log("Player is near and having bank open, closing...");
				getBank().close();
			}

		} else {
			log("Waiting for the other player to send a request...");
		}

		if ((!Config.TRADE_OVER_CLAY && getInventory().contains(995)
				|| (Config.TRADE_OVER_CLAY && !getInventory().isEmpty())) && (!getTrade().isCurrentlyTrading())) {

			// Open bank
			if (!getBank().isOpen()) {
				getBank().open();
				Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
			}

			if (getBank().isOpen()) {

				// Depositing the cash from the bank
				// if (getBank().deposit(995, (int) getBank().getAmount(995))) {
				if (getBank().depositAll()) {
					Sleep.sleepUntil(() -> getInventory().isEmpty(), 20000);
					if (getInventory().isEmpty()) {
						log("Trading is done!");
						tradingDone = true;
					}
				}
				Sleep.sleepUntil(() -> !hasClayOrCoins(), 20000);
			}
		}
		// When the mule doesn't have coins and trading isnt done and the other player
		// is not near then check if it is already completed or not and hasn't completed
		// in 5 minutes
		else if ((Config.TRADE_OVER_CLAY && !getInventory().contains(995)
				|| (Config.TRADE_OVER_CLAY && getInventory().isEmpty()))
				&& (!tradingDone && getPlayers().closest(quest.getEvent().getTradeWith()) == null
						&& (System.currentTimeMillis() - timeout > 400_000))) {

			if (!getBank().isOpen()) {
				getBank().open();
				Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
			}
			if (getBank().isOpen()) {

				int inv = !Config.TRADE_OVER_CLAY ? (int) getInventory().getAmount(995) : getAmountOfItems();

				int bank = !Config.TRADE_OVER_CLAY ? (int) getBank().getAmount(995) : getAmountOfItems();

				if (inv <= 0 && bank > 0) {
					if (getBank().depositAll()) {
						Sleep.sleepUntil(() -> getInventory().isEmpty(), 20000);
						if (getInventory().isEmpty()) {
							log("Trading timeout is done!");

							tradingDone = true;
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the amount of items
	 * 
	 * @return
	 */
	private int getAmountOfItems() {
		int amount = 0;
		for (BankItem item : itemsToMule) {
			amount += getInventory().getAmount(item.getName());
		}
		return amount;
	}

	private boolean hasClayOrCoins() {
		boolean hasItem = false;
		for (BankItem item : itemsToMule) {
			if (getInventory().contains(item.getName())) {
				hasItem = true;
			}
		}
		return hasItem;
	}

	private boolean hasItemsThatShouldBeBanked() {
		return getInventory().contains(434);
	}

	private void walkOutsideTheHotSpotToMeetEachother() {
		if (WorldHop.shouldWeHop(this, myPlayer().getArea(0), 4) && !getTrade().isCurrentlyTrading() && !tradingDone) {
			log("Walking outside the hot spot!");
			getWalking().walk(new Area(
					new int[][] { { 3161, 3492 }, { 3161, 3486 }, { 3169, 3486 }, { 3169, 3493 }, { 3161, 3493 } }));
		} else {
			log("Didn't have to walk outside the hot spot!");
		}
	}

	public void handleGeneralFirst(String tradeType, String database) throws InterruptedException {
		initializeItems();

		// Not in g.e.? Walk to it
		walkToGrandExchange();

		// So they don't stack on eachother
		walkOutsideTheHotSpotToMeetEachother();

		log("currently trading: " + getTrade().isCurrentlyTrading());

		if ((getTrade().isCurrentlyTrading() || getTrade().isFirstInterfaceOpen() || getTrade().isSecondInterfaceOpen())
				&& itemMap.size() > 0) {
			trade(quest.getEvent().getTradeWith(), itemMap, false, tradeType);

			log("currently trading");
			return;
		}

		log("getting to trade...");

		if (!tradingDone) {
			// Not having the coins right now, getting it from the bank
			if (((!Config.TRADE_OVER_CLAY && (!getInventory().contains(995) || hasItemsThatShouldBeBanked()))
					|| (Config.TRADE_OVER_CLAY && (!hasClayOrCoins() || hasItemsThatShouldBeBanked())))
					&& (!getTrade().isCurrentlyTrading())) {

				log("one");

				// Open bank
				if (!getBank().isOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 5000);

					getAccountValueAndUpdateInDatabase(database);
				}

				getBank().depositAll();
				Sleep.sleepUntil(() -> getInventory().isEmpty(), 5000);

				// Getting the cash from the bank
				if (!Config.TRADE_OVER_CLAY) {
					getBank().withdraw(995, (int) getBank().getAmount(995));
				} else {
					// getBank().withdraw(995, (int) getBank().getAmount(995));
					for (BankItem item : itemsToMule) {
						getBank().enableMode(BankMode.WITHDRAW_NOTE);
						Sleep.sleepUntil(() -> getBank().getWithdrawMode().equals(BankMode.WITHDRAW_NOTE), 2000);
						getBank().withdraw(item.getName(), (int) getBank().getAmount(item.getName()));
					}
				}
				Sleep.sleepUntil(() -> !getInventory().isEmpty(), 5000);

			} else {
				// Putting items into the list to trade
				log("two");

				if (!Config.TRADE_OVER_CLAY) {
					if (itemMap.size() == 0 && (int) getInventory().getAmount(995) > 0) {
						log("items to trade set to: coins " + (int) getInventory().getAmount(995));
						itemMap.put("Coins", (int) getInventory().getAmount(995));
					}
				} else {
					if (itemMap.size() == 0 && hasClayOrCoins()) {
						for (BankItem item : itemsToMule) {
							if (getInventory().getAmount(item.getName()) > 0) {
								itemMap.put(item.getName(), 999_999_999);
							}
						}
					}
				}
			}
		}

		// getBank().close();

		if (!Config.TRADE_OVER_CLAY) {
			if (itemMap.size() > 0 && getInventory().contains(995) && !getTrade().isFirstInterfaceOpen()
					&& !getTrade().isSecondInterfaceOpen() && !getTrade().isCurrentlyTrading()) {
				trade(quest.getEvent().getTradeWith(), itemMap, false, tradeType);
			}
		} else {
			log("three");
			log("map size: " + itemMap.size() + " " + hasClayOrCoins() + " " + getTrade().isFirstInterfaceOpen() + " "
					+ getTrade().isSecondInterfaceOpen() + " " + getTrade().isCurrentlyTrading() + " "
					+ quest.getEvent().getTradeWith());

			if (itemMap.size() > 0 && hasClayOrCoins() && !getTrade().isFirstInterfaceOpen()
					&& !getTrade().isSecondInterfaceOpen() && !getTrade().isCurrentlyTrading()) {
				trade(quest.getEvent().getTradeWith(), itemMap, false, tradeType);
			}
		}

		// Open bank
		if (((!Config.TRADE_OVER_CLAY && !getInventory().contains(995))
				|| (Config.TRADE_OVER_CLAY && !hasClayOrCoins()))
				&& (!getTrade().isCurrentlyTrading() && !getTrade().isFirstInterfaceOpen()
						&& !getTrade().isSecondInterfaceOpen())) {

			boolean done = false;
			while (!done) {
				if (getBank().isOpen()) {
					log("Bank was already open");
					break;
				}
				getBank().open();

				Sleep.sleepUntil(() -> getBank().isOpen(), 5000);

				done = getBank().isOpen();

				Thread.sleep(1500);
			}

			log("coins inv: " + getInventory().getAmount("Coins") + " coins bank: " + getBank().getAmount("Coins"));

			if ((getBank().isOpen()) && ((!Config.TRADE_OVER_CLAY && getInventory().getAmount("Coins") <= 0
					&& getBank().getAmount("Coins") <= 0)
					|| (Config.TRADE_OVER_CLAY && !hasCoinsAndClayInBankAndInventory()))) {
				tradingDone = true;
				log("Trading is done!");
			}
		}
	}

	private boolean hasCoinsAndClayInBankAndInventory() {
		boolean hasItem = false;
		for (BankItem item : itemsToMule) {
			if (getInventory().contains(item.getName()) || (getBank().isOpen() && getBank().contains(item.getName()))) {
				hasItem = true;
			}
		}
		return hasItem;
	}

	public void handleRetrades(int tradeTimeout) throws InterruptedException {
		int newPartnerFindTries = 0;
		boolean newPartner = false;
		String tradeWith = null;
		int doubletrade = 0;

		while (!newPartner) {

			if (getTrade().getLastRequestingPlayer() != null
					&& getTrade().getLastRequestingPlayer().getName() != null) {
				tradeWith = getTrade().getLastRequestingPlayer().getName();
			}

			log("Found partner: " + tradeWith + " got current partner: " + quest.getEvent().getTradeWith());
			if (tradeWith != null && quest.getEvent().getTradeWith() != null
					&& (!tradeWith.equalsIgnoreCase(quest.getEvent().getTradeWith()) || doubletrade >= 5)) {
				newPartner = true;
			}

			if (tradeWith != null && quest.getEvent().getTradeWith() != null
					&& tradeWith.equalsIgnoreCase(quest.getEvent().getTradeWith())) {
				doubletrade++;
			}

			log("Currently looking for a new partner to trade with " + newPartnerFindTries);
			log("Currently at : " + newPartnerFindTries + " / " + tradeTimeout + " before logging out due to timeout");
			Thread.sleep(1500);

			// This is so the account doesn't stay logged in for more than 4 minutes at a
			// time and not finding a new partner to trade with
			if (newPartnerFindTries > tradeTimeout) {
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, quest.getEvent().getUsername(),
						quest.getEvent());
				BotCommands.killProcess(this, quest.getScript(), "BECAUSE OF DONE WITH UNKNOWN TRADING",
						quest.getEvent());
				quest.getScript().stop();
			}

			if (getBank().isOpen()) {
				getBank().close();
			}

			newPartnerFindTries++;
		}
		lastTradedPlayer = null;
		tries = 0;
		timeout = System.currentTimeMillis();
		tradingDone = false;
		newPartnerFindTries = 0;
		doubletrade = 0;
		log("Found a new partner to trade with!");
		log("Set trading with to: " + tradeWith);
		quest.getEvent().setTradeWith(tradeWith);
	}

	private boolean hasAccepted() {
		return WidgetActionFilter.containsText(this, 335, 30, "Waiting for other player...")
				|| WidgetActionFilter.containsText(this, 334, 4, "Waiting for other player...");
	}

	private boolean tradeOfferMatches(HashMap<String, Integer> itemSet) throws InterruptedException {
		for (String item : itemSet.keySet()) {
			Sleep.sleepUntil(() -> isTrading() && getTrade().getOurOffers().getItem(item) != null, 2000);
			if (isTrading() && getTrade().getOurOffers().getItem(item) == null) {
				log("Trade Offer Missing: " + item);
				return false;
			}
		}
		return true;
	}

	public boolean isTrading() {
		return getTrade().isCurrentlyTrading() || getTrade().isFirstInterfaceOpen()
				|| getTrade().isSecondInterfaceOpen();
	}

	public void walkToGrandExchange() {
		WalkToGrandExchangeIfNotThere.walk(this, quest.getEvent());
	}

	public void trade(String name, HashMap<String, Integer> itemSet, boolean acceptLast, String tradingType)
			throws InterruptedException {
		if (quest.getEvent().getAccountStage().equalsIgnoreCase(tradingType)) {
			Thread.sleep(4000);
		}
		String cleanName = name.replaceAll(" ", "\\u00a0");
		Player player = quest.getScript().getPlayers().closest(cleanName);

		log(quest.getEvent().getAccountStage() + " " + tradingType + " " + lastTradedPlayer);

		if (!quest.getEvent().getAccountStage().equalsIgnoreCase(tradingType)) {
			if (lastTradedPlayer != null) {
				if (player != null && !isTrading() && player.interact("trade with")) {
					log("1");
					new ConditionalSleep(10000) {
						@Override
						public boolean condition() {
							return isTrading();
						}
					}.sleep();
				}
			}
		} else {
			if (player != null && !isTrading() && player.interact("trade with")) {
				log("1");
				new ConditionalSleep(10000) {
					@Override
					public boolean condition() {
						return isTrading();
					}
				}.sleep();
			}
		}

		if (isTrading() && getTrade().isFirstInterfaceOpen()) {
			log("2");
			if (!tradeOfferMatches(itemSet)) {
				log("3");
				for (String item : itemSet.keySet()) {
					if (!getTrade().getOurOffers().contains(item)) {
						if (getTrade().offer(item, itemSet.get(item))) {
							log("4");
							new ConditionalSleep(10000) {

								@Override
								public boolean condition() {
									return getTrade().getOurOffers().contains(item);
								}
							}.sleep();
						}
					}
				}
			} else {
				if (acceptLast && getTrade().didOtherAcceptTrade()) {
					log("5");
					if (WidgetActionFilter.interactTil(this, "Accept", 335, 11, new ConditionalSleep(10000) {
						@Override
						public boolean condition() {
							return getTrade().isSecondInterfaceOpen();
						}
					})) {
						log("6");
						new ConditionalSleep(10000) {
							@Override
							public boolean condition() {
								return getTrade().isSecondInterfaceOpen();
							}
						}.sleep();
					}

				} else if (!acceptLast && !hasAccepted()) {
					log("7");
					if (WidgetActionFilter.interactTil(this, "Accept", 335, 11, new ConditionalSleep(10000) {
						@Override
						public boolean condition() {
							return hasAccepted();
						}
					})) {
						log("8");
						new ConditionalSleep(10000) {

							@Override
							public boolean condition() {
								return getTrade().isSecondInterfaceOpen();
							}
						}.sleep();
					}
				}

			}
		} else if (isTrading() && getTrade().isSecondInterfaceOpen()) {

			if (acceptLast && getTrade().didOtherAcceptTrade()) {
				log("9");
				if (WidgetActionFilter.interactTil(this, "Accept", 334, 25, new ConditionalSleep(10000) {

					@Override
					public boolean condition() {
						return !isTrading();
					}
				})) {
					log("10");
					new ConditionalSleep(10000) {

						@Override
						public boolean condition() {
							return getTrade().isSecondInterfaceOpen();
						}
					}.sleep();
				}

			} else if (!acceptLast && !hasAccepted()) {
				log("11");
				if (WidgetActionFilter.interactTil(this, "Accept", 334, 25, new ConditionalSleep(10000) {

					@Override
					public boolean condition() {
						return !isTrading();
					}
				})) {
					log("12");
					new ConditionalSleep(10000) {
						@Override
						public boolean condition() {
							return getTrade().isSecondInterfaceOpen();
						}
					}.sleep();
				}

			}
		}
	}

	public int getAccountValueAndUpdateInDatabase(String database) {
		int totalAccountValue = 0;
		if (!Config.TRADE_OVER_CLAY) {
			if (getBank().isOpen()) {
				totalAccountValue = (int) (getBank().getAmount("Coins") + getInventory().getAmount("Coins"));

				log("[ESTIMATED MULE VALUE] account value is: " + totalAccountValue);
				if (quest.getEvent() != null && quest.getEvent().getUsername() != null && totalAccountValue > 0) {
					if (database != null && database.length() > 0) {
						DatabaseUtilities.updateAccountValue(database, this, quest.getEvent().getUsername(),
								totalAccountValue, quest.getEvent());
					} else {
						DatabaseUtilities.updateAccountValue(this, quest.getEvent().getUsername(), totalAccountValue,
								quest.getEvent());
					}
				}
			}
		} else {
			if (getBank().isOpen()) {

				for (Item item : getBank().getItems()) {
					int gePrice = 0;
					try {
						gePrice = new GEPrice().getBuyingPrice(item.getId());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (item.getId() != 995) {
						totalAccountValue += (int) (getBank().getAmount(item.getName())
								+ getInventory().getAmount(item.getName())) * gePrice;
					} else {
						totalAccountValue += (int) (getBank().getAmount(item.getName())
								+ getInventory().getAmount(item.getName()));
					}
				}

				log("[ESTIMATED MULE VALUE] account value is: " + totalAccountValue);
				if (quest.getEvent() != null && quest.getEvent().getUsername() != null && totalAccountValue > 0) {
					if (database != null && database.length() > 0) {
						DatabaseUtilities.updateAccountValue(database, this, quest.getEvent().getUsername(),
								totalAccountValue, quest.getEvent());
					} else {
						DatabaseUtilities.updateAccountValue(this, quest.getEvent().getUsername(), totalAccountValue,
								quest.getEvent());
					}
				}
			}
		}
		return totalAccountValue;
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
