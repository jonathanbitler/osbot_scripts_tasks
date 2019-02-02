package osbot_scripts.bottypes;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;

public class PlayerTask {

	/**
	 * Singleton
	 */
	private static PlayerTask singleton = null;

	public static PlayerTask getSingleton() {
		if (singleton == null) {
			singleton = new PlayerTask();
		}
		return singleton;
	}

	/**
	 * Returns the type of the bot that it currently is
	 * 
	 * @return
	 */
	public BotType getBotType(MethodProvider api, LoginEvent event) {
		String type = DatabaseUtilities.getScriptConfigValue(api, event);

		api.log("Type found to follow: " + type);
		if (type.equalsIgnoreCase("CLAY_ORE")) {
			return new ClayOre();
		} else if (type.equalsIgnoreCase("OAK_LOGS")) {
			return new WoodcuttingType();
		} else if (type.equalsIgnoreCase("IRON_MINING")) {
			return new IronMiningType();
		} else if (type.equalsIgnoreCase("RIMMINGTON_MINING")) {
			return new RimmingtonMining();
		}
		return new Default();
	}
}
