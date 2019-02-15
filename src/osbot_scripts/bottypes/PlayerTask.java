package osbot_scripts.bottypes;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.scripttypes.mining.clay.ClayMiningRimmington;
import osbot_scripts.scripttypes.mining.clay.ClayMiningWestOfVarrock;
import osbot_scripts.scripttypes.mining.clay.IronMiningEastOfVarrock;
import osbot_scripts.scripttypes.mining.clay.IronMiningWestOfVarrock;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;

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

	private String typeWhereToMine = "";

	/**
	 * Returns the type of the bot that it currently is
	 * 
	 * @return
	 */
	public BotType getBotType(MethodProvider api, LoginEvent event) {
		String type = DatabaseUtilities.getScriptConfigValue(api, event);

		api.log("Type found to follow: " + type);
		if (type.equalsIgnoreCase("CLAY_ORE")) {
			setTypeWhereToMine("CLAY_ORE");
			return new ClayOre();
		} else if (type.equalsIgnoreCase("OAK_LOGS")) {
			return new WoodcuttingType();
		} else if (type.equalsIgnoreCase("IRON_MINING")) {
			setTypeWhereToMine("IRON_MINING");
			return new IronMiningType();
		} else if (type.equalsIgnoreCase("RIMMINGTON_MINING")) {
			setTypeWhereToMine("RIMMINGTON_MINING");
			return new RimmingtonMining();
		} else if (type.equalsIgnoreCase("EAST_OF_VARROCK_IRON_MINING")) {
			setTypeWhereToMine("EAST_OF_VARROCK_IRON_MINING");
			return new RimmingtonMining();
		}
		return new Default();
	}

	/**
	 * Gets the mining template that's currently using
	 * 
	 * @return
	 */
	public MiningLocationTemplate getMiningTemplate() {
		switch (getTypeWhereToMine()) {
		case "EAST_OF_VARROCK_IRON_MINING":
			return new IronMiningEastOfVarrock();
		case "CLAY_ORE":
			return new ClayMiningWestOfVarrock();
		case "IRON_MINING":
			return new IronMiningWestOfVarrock();
		case "RIMMINGTON_MINING":
			return new ClayMiningRimmington();
		}
		return null;
	}

	/**
	 * @return the typeWhereToMine
	 */
	public String getTypeWhereToMine() {
		return typeWhereToMine;
	}

	/**
	 * @param typeWhereToMine
	 *            the typeWhereToMine to set
	 */
	public void setTypeWhereToMine(String typeWhereToMine) {
		this.typeWhereToMine = typeWhereToMine;
	}
}
