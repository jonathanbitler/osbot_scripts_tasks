package osbot_scripts.bottypes;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.AccountStage;

public class IronMiningType extends BotDefaults implements BotType {

	@Override
	public AccountStage getNextTask(MethodProvider provider) {
		// When quest cooks assistant isn't completed yet
		if (provider.getConfigs().get(29) < 2) {
			return AccountStage.QUEST_COOK_ASSISTANT;
		}
		if (provider.getConfigs().get(179) < 21) {
			return AccountStage.QUEST_SHEEP_SHEARER;
		}
		// When quest romeon and juliet isn't completed yet
		if (provider.getConfigs().get(144) < 100) {
			return AccountStage.QUEST_ROMEO_AND_JULIET;
		}
		// Return woodcutting if all quests are done
		return AccountStage.MINING_LEVEL_TO_15;

	}

}
