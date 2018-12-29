package osbot_scripts.framework;

import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import osbot_scripts.util.Sleep;

public class GE {

	Script s;
	boolean logging = false;

	public GE(Script s) {
		this.s = s;
	}

	/**
	 * ItemID itemName - For input search price - desired buying price quantity -
	 * desired buy quantity collectIfNeeded - Attempts to collect all items from GE
	 * main window if there's no empty box abortIfNeeded - If collecting fails,
	 * tries to abort the offer with the same item. If not found tries to abort the
	 * first found pending box. collectFailsafe - if true - collects everything
	 * possible first
	 * 
	 * KNOWN ISSUES: If there is a another buy/sale with the same item then it will
	 * think that it's the buy/sale we're initialized (need to distinguish things by
	 * quantity and price etc.)
	 */
	public boolean createBuyOffer(int itemId, String itemName, int price, int quantity, boolean collectIfNeeded,
			boolean abortIfNeeded, boolean collectFailsafe) throws InterruptedException {

		boolean bought = false;
		if (init(itemId, collectIfNeeded, abortIfNeeded, collectFailsafe, "Buy")) {
			s.grandExchange.buyItem(itemId, itemName, price, quantity);
			new ConditionalSleep(3000, 500) {
				@Override
				public boolean condition() throws InterruptedException {
					return !s.grandExchange.isOfferScreenOpen();
				}
			}.sleep();
			bought = checkItemOffer(itemId, "Buy");

		}
		log("Buying offer ended");
		s.sleep(s.random(800, 1400));

		return bought;

	}

	/**
	 * createSellOffer ItemID itemName - For input search price - desired selling
	 * price quantity - desired selling quantity collectIfNeeded - Attemps to
	 * collect all items from GE main window if there's no empty box abortIfNeeded -
	 * If collecting fails, tries to abort the offer with the same item. If not
	 * found tries to abort the first found pending box. collectFailsafe - if true -
	 * collects everything possible first
	 * 
	 * KNOWN ISSUES: If there is a another buy/sale with the same item then it will
	 * think that it's the buy/sale we're initialized (need to distinguish things by
	 * quantity and price etc.)
	 */
	public boolean createSellOffer(int itemId, String itemName, int price, int quantity, boolean collectIfNeeded,
			boolean abortIfNeeded, boolean collectFailsafe) throws InterruptedException {
		if (init(itemId, collectIfNeeded, abortIfNeeded, collectFailsafe, "Sale")) {
			if (s.inventory.contains(itemName)) {
				Item sellItem = s.getInventory().getItem(itemName);

				// Offer item
				sellItem.interact("Offer");

				// Set offer price
				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return s.grandExchange.setOfferPrice(price);
					}
				}.sleep();

				// Set offer quantity
				if (quantity != s.grandExchange.getOfferQuantity()) {
					new ConditionalSleep(3000, 500) {
						@Override
						public boolean condition() throws InterruptedException {
							return s.grandExchange.setOfferQuantity(quantity);
						}
					}.sleep();
				}

				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return s.grandExchange.confirm();
					}
				}.sleep();

				s.sleep(s.random(800, 1400));

				log("Put item on sale");

				boolean sold = false;
				sold = checkItemOffer(itemId, "Sale");
				s.sleep(s.random(1500, 1800));
				return sold;

			} else {
				log("Inventory doesn't contain item");
			}
		}

		s.sleep(s.random(800, 1400));

		return false;
	}

	/* Checks if there are any boxes to abort */
	public GrandExchange.Box checkAbortBoxes(int itemId) {

		GrandExchange.Box returningBox = null;
		String worldType = "F2P";
		if (s.worlds.isMembersWorld()) {
			worldType = "P2P";
		}

		/* Searching for pending box with item */
		for (GEBoxes boxes : GEBoxes.values()) {
			// Loops through F2P boxes only
			if (worldType == "F2P") {
				if (boxes.getType() == "F2P") {
					GrandExchange.Status status = s.grandExchange.getStatus(boxes.getBox());
					if (status == GrandExchange.Status.PENDING_BUY || status == GrandExchange.Status.PENDING_SALE
							|| status == GrandExchange.Status.CANCELLING_BUY
							|| status == GrandExchange.Status.CANCELLING_SALE) {

						if (s.grandExchange.getItemId(boxes.getBox()) == itemId) {
							returningBox = boxes.getBox();
							break;
						}
					}
				}
			} else {
				// Loops though all boxes
				GrandExchange.Status status = s.grandExchange.getStatus(boxes.getBox());
				if (status == GrandExchange.Status.PENDING_BUY || status == GrandExchange.Status.PENDING_SALE
						|| status == GrandExchange.Status.CANCELLING_BUY
						|| status == GrandExchange.Status.CANCELLING_SALE) {

					if (s.grandExchange.getItemId(boxes.getBox()) == itemId) {
						returningBox = boxes.getBox();
						break;
					}
				}
			}
		}

		/*
		 * If a pending box with the specified item was not found - Find first pending
		 * box
		 */
		if (returningBox == null) {
			/* Searching for pending box with item */
			for (GEBoxes boxes : GEBoxes.values()) {
				// Loops through F2P boxes only
				if (worldType == "F2P") {
					if (boxes.getType() == "F2P") {
						GrandExchange.Status status = s.grandExchange.getStatus(boxes.getBox());
						if (status == GrandExchange.Status.PENDING_BUY || status == GrandExchange.Status.PENDING_SALE
								|| status == GrandExchange.Status.CANCELLING_BUY
								|| status == GrandExchange.Status.CANCELLING_SALE) {

							returningBox = boxes.getBox();
							break;
						}
					}
				} else {
					// Loops though all boxes
					GrandExchange.Status status = s.grandExchange.getStatus(boxes.getBox());
					if (status == GrandExchange.Status.PENDING_BUY || status == GrandExchange.Status.PENDING_SALE
							|| status == GrandExchange.Status.CANCELLING_BUY
							|| status == GrandExchange.Status.CANCELLING_SALE) {

						returningBox = boxes.getBox();
						break;
					}
				}
			}
		}

		if (returningBox != null) {
			return returningBox;
		}
		return null;
	}

	/* Finds an empty box or collects the items to free all of them */
	public boolean checkBoxes() throws InterruptedException {

		for (GrandExchange.Box box : GrandExchange.Box.values()) {
			// F2P
			if (box.toString().equals("BOX_1") || box.toString().equals("BOX_2") || box.toString().equals("BOX_3")) {
				GrandExchange.Status status = s.grandExchange.getStatus(box);
				if (status == GrandExchange.Status.EMPTY) {
					log("Found empty box: " + box.toString());
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * goIntoOffer
	 * 
	 * @param box
	 * @return boolean
	 */
	public boolean goIntoOffer(GrandExchange.Box box) {
		if (s.grandExchange.isOfferScreenOpen()) {
			s.grandExchange.goBack();
			new ConditionalSleep(2000, 500) {
				@Override
				public boolean condition() throws InterruptedException {
					return !s.grandExchange.isOfferScreenOpen();
				}
			}.sleep();
		}
		log(s.grandExchange.getStatus(box).toString());

		// Getting box offer widget
		RS2Widget boxOffer = s.widgets.get(465, getOfferNum(box), 2);
		if (boxOffer != null) {
			log("OFFER BOX WIDGET IS NOT NULL. OFFER NUM " + getOfferNum(box));
			boxOffer.interact("View offer");
			new ConditionalSleep(2000, 500) {
				@Override
				public boolean condition() throws InterruptedException {
					return s.grandExchange.isOfferScreenOpen();
				}
			}.sleep();
			return true;
		}
		log("OFFER BOX WIDGET IS NULL");
		return false;
	}

	public boolean abort(GrandExchange.Box box) throws InterruptedException {
		RS2Widget abortWidget = s.widgets.get(465, 22, 0);
		if (abortWidget != null) {
			if (!abortWidget.isVisible()) {
				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return goIntoOffer(box);
					}
				}.sleep();
			}

			if (abortWidget.isVisible()) {
				abortWidget.interact("Abort Offer");
				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return !abortWidget.isVisible();
					}
				}.sleep();
			}

			if (collectItem(0, box, true)) {
				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						return !s.grandExchange.isOfferScreenOpen();
					}
				}.sleep();
				return true;
			}
		}

		return false;

	}

	/**
	 * getOfferNum NEED TO REWRITE BOX STUFF TO USING CUSTOM ENUM
	 */
	public int getOfferNum(GrandExchange.Box box) {

		for (GEBoxes boxes : GEBoxes.values()) {
			if (boxes.getBox() == box) {
				return boxes.getChildWidgetId();
			}
		}
		return 7; // Failproof - 7 is the first ID
	}

	public boolean isOfferFinished(GrandExchange.Box box) {
		GrandExchange.Status status = s.grandExchange.getStatus(box);
		if (status == GrandExchange.Status.FINISHED_BUY || status == GrandExchange.Status.FINISHED_SALE) {
			return true;
		}
		return false;
	}

	public boolean init(int itemId, boolean collectIfNeeded, boolean abortIfNeeded, boolean collectFailsafe,
			String type) throws InterruptedException {

		boolean finish = false;
		while (!finish) {
			if (s.getGrandExchange().isOpen()) {
				finish = true;
			}
			// Opening the g.e.
			openGe();

			finish = s.getGrandExchange().isOpen();
			s.log("Trying to open the g.e..");

			Sleep.sleepUntil(() -> s.getGrandExchange().isOpen(), 5000);
		}

		if (s.grandExchange.isOpen()) {

			// Work around for the box identification bug
			if (collectFailsafe) {
				if (s.grandExchange.isOfferScreenOpen()) {
					s.grandExchange.goBack();
					new ConditionalSleep(3000, 500) {
						@Override
						public boolean condition() throws InterruptedException {
							return !s.grandExchange.isOfferScreenOpen();
						}
					}.sleep();
				}
				s.grandExchange.collect();
				s.sleep(s.random(800, 1200));
			}

			if (checkBoxes()) {
				log("Found an empty box");
				return true;
			} else {
				log("Went into else. CollectIfNeeded: " + collectIfNeeded);
				// Checking if there is anything to collect
				if (collectIfNeeded || abortIfNeeded) {

					if (collectIfNeeded) {
						if (s.grandExchange.isOfferScreenOpen()) {
							s.grandExchange.goBack();
							new ConditionalSleep(3000, 500) {
								@Override
								public boolean condition() throws InterruptedException {
									return !s.grandExchange.isOfferScreenOpen();
								}
							}.sleep();
						}

						s.grandExchange.collect();
						s.sleep(s.random(1000, 1300));
					}

					// If we've emptied a box
					if (checkBoxes()) {
						return true;
					} else {
						if (abortIfNeeded) {
							log("IS IN ABORT SECTION");
							GrandExchange.Box box = checkAbortBoxes(itemId);
							if (box != null) {
								new ConditionalSleep(3000, 500) {
									@Override
									public boolean condition() throws InterruptedException {
										return goIntoOffer(box);
									}
								}.sleep();

								return abort(box);
							}
						}
					}
				}
			}

		} else {
			s.log("Grand Exchange is not open");
		}

		s.log("Did not find an empty box");
		return false;
	}

	/* Checks if item can be collected and collects it */
	public boolean checkItemOffer(int itemId, String type) throws InterruptedException {

		GrandExchange.Box[] usableBoxes = new GrandExchange.Box[] { GrandExchange.Box.BOX_1, GrandExchange.Box.BOX_2,
				GrandExchange.Box.BOX_3 };
		for (int i = 0; i < usableBoxes.length; i++) {
			GrandExchange.Box tempBox = usableBoxes[i];
			if (s.grandExchange.getItemId(tempBox) == itemId) {
				log("Looping through... " + tempBox.toString());
				log("Box status: " + s.grandExchange.getStatus(tempBox).toString());

				new ConditionalSleep(3000, 500) {
					@Override
					public boolean condition() throws InterruptedException {
						if (type == "Buy") {
							return s.grandExchange.getStatus(tempBox) == GrandExchange.Status.FINISHED_BUY;
						} else if (type == "Sale") {
							return s.grandExchange.getStatus(tempBox) == GrandExchange.Status.FINISHED_SALE;
						}
						return true;
					}
				}.sleep();
				log("Box status AFTER SLEEP: " + s.grandExchange.getStatus(tempBox).toString());
				if (isOfferFinished(tempBox)) {
					log("G.E. COLLECTING ITEM: " + itemId);
					return collectItem(itemId, usableBoxes[i], false);
				}
			}
		}
		return false;
	}

	/* Collects item and returns the amount collected */
	public boolean collectItem(int itemId, GrandExchange.Box box, boolean forAbort) {
		log("Went into collectItem()");
		// If the offer is finished

		boolean finished = false;
		while (!finished) {

			// For noted and unnoted TODO check is works
			finished = s.getInventory().contains(itemId) || s.getInventory().contains(itemId + 1);

			if (finished) {
				s.log("Successfully collected from G.e. Task!");
				return true;
			}

			if (isOfferFinished(box)) {
				log("Offer is finished");
				// If the box contains the same item ID
				if (s.grandExchange.getItemId(box) == itemId || forAbort) {
					log("Found a coressponding item in box");
					/* Get the collection widgets */
					// Widget 1
					RS2Widget itemCollectWidget1 = s.widgets.get(465, 23, 2);
					// Widget 2
					RS2Widget itemCollectWidget2 = s.widgets.get(465, 23, 3);

					boolean collected = false;
					// If the widget exists
					if (itemCollectWidget1 != null || itemCollectWidget2 != null) {
						if (itemCollectWidget1 != null) {
							log("WIDGET1 IS NOT NULL");
							// If the widget is visible
							if (!itemCollectWidget1.isVisible()) {
								if (goIntoOffer(box)) {
									log("Widget was not visible, so went to it");
								}
							}
							log("Collect from item collect widget");

							boolean w1Collect = false;
							// ALSO COULD BE Collect-items or BANK
							String[] w1Actions = itemCollectWidget1.getInteractActions();
							if (w1Actions != null) {
								List<String> widget1Actions = Arrays.asList(itemCollectWidget1.getInteractActions());

								if (widget1Actions.contains("Collect")) {
									itemCollectWidget1.interact("Collect");
									w1Collect = true;
								} else if (widget1Actions.contains("Collect-note")) {
									itemCollectWidget1.interact("Collect-note");
									w1Collect = true;
								} else if (widget1Actions.contains("Collect-item")) {
									itemCollectWidget1.interact("Collect-item");
									w1Collect = true;
								} else if (widget1Actions.contains("Collect-notes")) {
									itemCollectWidget1.interact("Collect-notes");
									w1Collect = true;
								} else if (widget1Actions.contains("Collect-items")) {
									itemCollectWidget1.interact("Collect-items");
									w1Collect = true;
								}

								if (w1Collect) {
									collected = true;
								}

								new ConditionalSleep(3000, 500) {
									@Override
									public boolean condition() throws InterruptedException {
										return !itemCollectWidget1.isVisible();
									}
								}.sleep();
							}

						}

						if (itemCollectWidget2 != null) {
							log("WIDGET2 IS NOT NULL");
							// If the widget is visible
							if (!itemCollectWidget2.isVisible()) {
								if (goIntoOffer(box)) {
									log("Widget was not visible, so went to it");
								}
							}
							log("Collect from item collect widget");
							String[] w2Actions = itemCollectWidget2.getInteractActions();

							if (w2Actions != null) {
								List<String> widget2Actions = Arrays.asList(itemCollectWidget2.getInteractActions());

								boolean w2Collect = false;
								if (widget2Actions.contains("Collect")) {
									itemCollectWidget2.interact("Collect");
									w2Collect = true;
								} else if (widget2Actions.contains("Collect-note")) {
									itemCollectWidget2.interact("Collect-note");
									w2Collect = true;
								} else if (widget2Actions.contains("Collect-item")) {
									itemCollectWidget2.interact("Collect-item");
									w2Collect = true;
								} else if (widget2Actions.contains("Collect-notes")) {
									itemCollectWidget2.interact("Collect-notes");
									w2Collect = true;
								} else if (widget2Actions.contains("Collect-items")) {
									itemCollectWidget2.interact("Collect-items");
									w2Collect = true;
								}

								if (w2Collect) {
									collected = true;
								}

								new ConditionalSleep(3000, 500) {
									@Override
									public boolean condition() throws InterruptedException {
										return !itemCollectWidget2.isVisible();
									}
								}.sleep();
							}
						}
						new ConditionalSleep(3000, 500) {

							@Override
							public boolean condition() throws InterruptedException {
								return !s.grandExchange.isOfferScreenOpen();
							}

						}.sleep();

						return collected;

					} else {
						log("WIDGET IS NULL");
						// If the widget is not present collect from header
						s.grandExchange.goBack();
						new ConditionalSleep(3000, 500) {

							@Override
							public boolean condition() throws InterruptedException {
								return !s.grandExchange.isOfferScreenOpen();
							}

						}.sleep();
						new ConditionalSleep(3000, 500) {

							@Override
							public boolean condition() throws InterruptedException {
								return s.grandExchange.collect();
							}

						}.sleep();
						return true;
					}
				}
			}
			s.log("Waiting for item to collect to inventory!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean openGe() {
		if (!s.grandExchange.isOpen()) {
			NPC clerk = s.npcs.closest("Grand Exchange Clerk");
			if (clerk != null) {
				clerk.interact("Exchange");
				new ConditionalSleep(2000, 200) {
					@Override
					public boolean condition() throws InterruptedException {
						return s.grandExchange.isOpen();
					}
				}.sleep();
				return false;
			} else {
				s.log("Clerk not found");
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean closeGE() {
		if (s.grandExchange.isOpen()) {
			new ConditionalSleep(2000, 200) {
				@Override
				public boolean condition() throws InterruptedException {
					return s.grandExchange.close();
				}
			}.sleep();
			return true;
		}
		return false;
	}

	public void log(String msg) {
		if (logging) {
			s.log(msg);
		}
	}
}