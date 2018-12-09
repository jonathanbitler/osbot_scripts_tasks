package osbot_scripts.qp7.progress;

import java.util.HashMap;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.WidgetActionFilter;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class MuleTradingConfiguration extends QuestStep {

	public MuleTradingConfiguration(LoginEvent event, Script script) {
		super(-1, -1, AccountStage.MULE_TRADING, event, script, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {
		timeout = System.currentTimeMillis();

		// demo = new ThreadDemo();
		// demo.exchangeContext(this.getBot());
		// demo.setLoginEvent(getEvent());
		// new Thread(demo).start();
	}

	private ThreadDemo demo;

	// private static final Area GRAND_EXCHANGE = new Area(
	// new int[][] { { 3160, 3489 }, { 3169, 3489 }, { 3169, 3483 }, { 3160, 3483 }
	// });

	private HashMap<String, Integer> itemMap = new HashMap<String, Integer>();

	private boolean tradingDone = false;

	private boolean update = true;

	private long timeout = -1;

	private int tries = 0;

	@Override
	public void onLoop() throws InterruptedException {
		if (!getClient().isLoggedIn()) {
			return;
		}
		log("Running the side loop..");

		// If the player is not in the grand exchange area, then walk to it
		if (!new Area(new int[][] { { 3161, 3492 }, { 3168, 3492 }, { 3168, 3485 }, { 3161, 3485 } })
				.contains(myPlayer())) {
			getWalking()
					.webWalk(new Area(new int[][] { { 3161, 3492 }, { 3168, 3492 }, { 3168, 3485 }, { 3161, 3485 } }));
			 log("The player has a grand exchange task but isn't there, walking to there");
		}
		// Not the mule

		if (tradingDone) {
			if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {
				if (update) {
					DatabaseUtilities.updateStageProgress(this,
							RandomUtil.gextNextAccountStage(this).name().toUpperCase(), 0, getEvent().getUsername());
					DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getEmailTradeWith());
				}
				BotCommands.killProcess(this, getScript(), "BECAUSE OF DONE WITH MULE TRADING");
			} else {
				if (update) {
					DatabaseUtilities.updateStageProgress(this,
							RandomUtil.gextNextAccountStage(this).name().toUpperCase(), 0,
							getEvent().getEmailTradeWith());
					DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername());
				}
				BotCommands.killProcess(this, getScript(), "BECAUSE OF DONE WITH UNKNOWN TRADING");
			}
			getScript().stop();
			return;
		}

		log(getEvent().getAccountStage());

		tries++;

		if (tries > (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING") ? 30 : 80)) {
			tradingDone = true;
			update = false;
			log("Failed to trade it over");
		}

		if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {

			log("currently trading: " + getTrade().isCurrentlyTrading());
			if ((getTrade().isCurrentlyTrading() || getTrade().isFirstInterfaceOpen()
					|| getTrade().isSecondInterfaceOpen()) && itemMap.size() > 0) {
				trade(getEvent().getTradeWith(), itemMap, false);
				log("currently trading");
				return;
			}

			log("getting to trade...");

			// Not in g.e.? Walk to it
			walkToGrandExchange();

			// Not having the coins right now, getting it from the bank
			if (!getInventory().contains(995) && !getTrade().isCurrentlyTrading()) {

				// Open bank
				if (!getBank().isOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
				}

				Thread.sleep(5000);

				getBank().depositAll();
				Sleep.sleepUntil(() -> getInventory().isEmpty(), 20000);

				// Getting the cash from the bank
				getBank().withdraw(995, (int) getBank().getAmount(995));
				Sleep.sleepUntil(() -> !getInventory().isEmpty(), 20000);

			} else {
				// Putting items into the list to trade
				if (itemMap.size() == 0 && (int) getInventory().getAmount(995) > 0) {
					log("items to trade set to: coins " + (int) getInventory().getAmount(995));
					itemMap.put("Coins", (int) getInventory().getAmount(995));
				}
			}

			if (itemMap.size() > 0) {
				trade(getEvent().getTradeWith(), itemMap, false);
			}

			// Open bank
			if (!getInventory().contains(995) && !getTrade().isCurrentlyTrading()) {
				if (!getBank().isOpen() && !getTrade().isCurrentlyTrading() && !getTrade().isFirstInterfaceOpen()
						&& !getTrade().isSecondInterfaceOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
				}

				log("coins inv: " + getInventory().getAmount("Coins") + " coins bank: " + getBank().getAmount("Coins"));
				if (getBank().isOpen() && !getTrade().isCurrentlyTrading() && !getTrade().isFirstInterfaceOpen()
						&& !getTrade().isSecondInterfaceOpen() && getInventory().getAmount("Coins") <= 0
						&& getBank().getAmount("Coins") <= 0) {
					tradingDone = true;
					log("Trading is done!");
				}
			}
		}

		// The mule itself
		else

		{

			log("time: " + (System.currentTimeMillis() - timeout));

			// Not in g.e.? Walk to it
			walkToGrandExchange();

			trade(getEvent().getTradeWith(), new HashMap<String, Integer>(), true);

			if (getInventory().contains(995) && !getTrade().isCurrentlyTrading()) {

				// Open bank
				if (!getBank().isOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
				}

				if (getBank().isOpen()) {

					int totalAccountValue = (int) getBank().getAmount(995);

					log("[ESTIMATED MULE VALUE] account value is: " + totalAccountValue);
					if (getEvent() != null && getEvent().getUsername() != null && totalAccountValue > 0) {
						DatabaseUtilities.updateAccountValue(this, getEvent().getUsername(), totalAccountValue);
					}

					// Depositing the cash from the bank
					// if (getBank().deposit(995, (int) getBank().getAmount(995))) {
					if (getBank().depositAll()) {
						Sleep.sleepUntil(() -> getInventory().isEmpty(), 20000);
						if (getInventory().isEmpty()) {
							log("Trading is done!");
							tradingDone = true;
						}
					}
					Sleep.sleepUntil(() -> !getInventory().contains(995), 20000);
				}
			}
			// When the mule doesn't have coins and trading isnt done and the other player
			// is not near then check if it is already completed or not and hasn't completed
			// in 5 minutes
			else if (!getInventory().contains(995) && !tradingDone
					&& getPlayers().closest(getEvent().getTradeWith()) == null
					&& (System.currentTimeMillis() - timeout > 300_000)) {

				if (!getBank().isOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
				}
				if (getBank().isOpen()) {

					int inv = (int) getInventory().getAmount(995);
					int bank = (int) getBank().getAmount(995);

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
	}

	public void trade(String name, HashMap<String, Integer> itemSet, boolean acceptLast) throws InterruptedException {
		String cleanName = name.replaceAll(" ", "\\u00a0");
		Player player = getScript().getPlayers().closest(cleanName);
		if (player != null && !isTrading() && player.interact("trade with")) {
			log("1");
			new ConditionalSleep(10000) {
				@Override
				public boolean condition() {
					return isTrading();
				}
			}.sleep();
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

	private void walkToGrandExchange() {
		if (!new Area(new int[][] { { 3144, 3508 }, { 3144, 3471 }, { 3183, 3470 }, { 3182, 3509 } })
				.contains(myPlayer())) {
			getWalking()
					.webWalk(new Area(new int[][] { { 3160, 3494 }, { 3168, 3494 }, { 3168, 3485 }, { 3160, 3485 } }));
			log("The player has a grand exchange task but isn't there, walking to there");
		}
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return null;
	}

}
