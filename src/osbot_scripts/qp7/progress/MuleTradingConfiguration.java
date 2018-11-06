package osbot_scripts.qp7.progress;

import java.util.HashMap;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.bot.utils.BotCommands;
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

	}

	private static final Area GRAND_EXCHANGE = new Area(
			new int[][] { { 3160, 3489 }, { 3169, 3489 }, { 3169, 3483 }, { 3160, 3483 } });

	private HashMap<String, Integer> itemMap = new HashMap<String, Integer>();

	private boolean tradingDone = false;

	@Override
	public void onLoop() throws InterruptedException {
		log("Running the side loop..");

		// Not the mule

		if (tradingDone) {
			getScript().stop();
			if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {
				DatabaseUtilities.updateStageProgress(this, "MINING_IRON_ORE", 0, getEvent().getUsername());
				BotCommands.killProcess(getScript());
			} else {
				DatabaseUtilities.updateStageProgress(this, "UNKNOWN", 0, getEvent().getUsername());
				BotCommands.killProcess(getScript());
			}
			return;
		}

		log(getEvent().getAccountStage());

		if (getEvent().getAccountStage().equalsIgnoreCase("MULE-TRADING")) {

			// Not in g.e.? Walk to it
			walkToGrandExchange();

			// Not having the coins right now, getting it from the bank
			if (!getInventory().contains(995) && !getTrade().isCurrentlyTrading() && !getTrade().isFirstInterfaceOpen()
					&& !getTrade().isSecondInterfaceOpen()) {

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
				if (itemMap.size() == 0) {
					log("items to trade set to: coins " + (int) getInventory().getAmount(995));
					itemMap.put("Coins", (int) getInventory().getAmount(995));
				}
			}

			if (itemMap.size() > 0) {
				trade(getEvent().getTradeWith(), itemMap, true);
			}

			// Open bank
			if (!getBank().isOpen() && !getTrade().isCurrentlyTrading() && !getTrade().isFirstInterfaceOpen()
					&& !getTrade().isSecondInterfaceOpen()) {
				getBank().open();
				Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
			}

			if (getBank().isOpen() && !getTrade().isFirstInterfaceOpen() && !getTrade().isSecondInterfaceOpen()
					&& getInventory().getAmount("Coins") <= 0 && getBank().getAmount("Coins") <= 0) {
				tradingDone = true;
				log("Trading is done!");
			}

		}
		// The mule itself
		else

		{

			// Not in g.e.? Walk to it
			walkToGrandExchange();

			trade(getEvent().getTradeWith(), new HashMap<String, Integer>(), false);

			if (getInventory().contains(995) && !getTrade().isCurrentlyTrading()) {

				// Open bank
				if (!getBank().isOpen()) {
					getBank().open();
					Sleep.sleepUntil(() -> getBank().isOpen(), 20000);
				} else {

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

		}

	}

	public void trade(String name, HashMap<String, Integer> itemSet, boolean acceptLast) {
		String cleanName = name.replaceAll(" ", "\\u00a0");
		Player player = getScript().getPlayers().closest(cleanName);
		if (player != null && !isTrading() && player.interact("trade with")) {
			new ConditionalSleep(3000, 4000) {
				@Override
				public boolean condition() {
					return isTrading();
				}
			}.sleep();
		}

		if (isTrading() && getTrade().isFirstInterfaceOpen()) {
			if (!tradeOfferMatches(itemSet)) {
				for (String item : itemSet.keySet()) {
					if (!getTrade().getOurOffers().contains(item)) {
						if (getTrade().offer(item, itemSet.get(item))) {
							new ConditionalSleep(2000, 3000) {

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
					if (WidgetActionFilter.interactTil(this, "Accept", 335, 11, new ConditionalSleep(1500, 2000) {
						@Override
						public boolean condition() {
							return getTrade().isSecondInterfaceOpen();
						}
					})) {
						new ConditionalSleep(3000, 4000) {
							@Override
							public boolean condition() {
								return getTrade().isSecondInterfaceOpen();
							}
						}.sleep();
					}

				} else if (!acceptLast && !hasAccepted()) {
					if (WidgetActionFilter.interactTil(this, "Accept", 335, 11, new ConditionalSleep(1500, 2000) {
						@Override
						public boolean condition() {
							return hasAccepted();
						}
					})) {
						new ConditionalSleep(3000, 4000) {

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
				if (WidgetActionFilter.interactTil(this, "Accept", 334, 25, new ConditionalSleep(1500, 2000) {

					@Override
					public boolean condition() {
						return !isTrading();
					}
				})) {
					new ConditionalSleep(3000, 4000) {

						@Override
						public boolean condition() {
							return getTrade().isSecondInterfaceOpen();
						}
					}.sleep();
				}

			} else if (!acceptLast && !hasAccepted()) {
				if (WidgetActionFilter.interactTil(this, "Accept", 334, 25, new ConditionalSleep(1500, 2000) {

					@Override
					public boolean condition() {
						return !isTrading();
					}
				})) {
					new ConditionalSleep(3000, 4000) {
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

	private boolean tradeOfferMatches(HashMap<String, Integer> itemSet) {
		for (String item : itemSet.keySet()) {
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
		if (!GRAND_EXCHANGE.contains(myPlayer())) {
			getWalking().webWalk(GRAND_EXCHANGE);
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
