package osbot_scripts;

import java.io.IOException;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.GEPrice;
import osbot_scripts.util.Sleep;

public class PlayerPrice extends MethodProvider {

	/**
	 * Opens a bank
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	private boolean openBank() throws InterruptedException {
		boolean finish = false;

		while (!finish) {
			if (getBank().isOpen()) {
				break;
			}
			getBank().open();
			log("Opening bank...");

			Sleep.sleepUntil(() -> getBank().isOpen(), 10000);

			finish = getBank().isOpen();
		}
		return true;
	}

	public int getTotalAccountValue() throws InterruptedException {
		int ironAmount = -1;
		int clayAmount = -1;
		int totalAccountValue = -1;
		int coinsAmount = -1, coinsInventoryAmount = -1, ironAmountInventory = -1, clayAmountInventory = -1;

		openBank();

		ironAmount = (int) getBank().getAmount("Iron ore");
		ironAmountInventory = (int) getInventory().getAmount("Iron ore");

		clayAmountInventory = (int) getInventory().getAmount("Clay");
		clayAmount = (int) getBank().getAmount("Clay");

		coinsAmount = (int) getBank().getAmount(995);
		coinsInventoryAmount = (int) getInventory().getAmount(995);

		totalAccountValue += coinsAmount;
		totalAccountValue += coinsInventoryAmount;

		/**
		 * Prices for iron ore & clay ore
		 */
		int ironOrePrice = 0;
		try {
			ironOrePrice = (int) (new GEPrice().getBuyingPrice(440) * 0.9);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int clayOrePrice = 0;
		try {
			clayOrePrice = (int) (new GEPrice().getBuyingPrice(434) * 0.9);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		totalAccountValue += (ironAmount * clayOrePrice);
		totalAccountValue += (ironAmountInventory * clayOrePrice);
		totalAccountValue += (clayAmount * ironOrePrice);
		totalAccountValue += (clayAmountInventory * ironOrePrice);

		return totalAccountValue;
	}
}
