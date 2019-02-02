package osbot_scripts.bottypes;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.framework.AccountStage;

public class RimmingtonMining extends BotDefaults implements BotType {

	@Override
	public AccountStage getNextTask(MethodProvider provider) {
		// First do dorics quest
		if (provider.getQuests().getQuestPoints() < 1) {
			stagesToDo.add(AccountStage.QUEST_DORICS_QUEST);
		} else {
			// Then do mining to 15
			stagesToDo.add(AccountStage.MINING_RIMMINGTON_CLAY);
		}
		// Cant be negative
		int max = (stagesToDo.size() - 1) < 0 ? 0 : (stagesToDo.size() - 1);
		int random = RandomUtil.getRandomNumberInRange(0, max);
		AccountStage stage = stagesToDo.get(random);

		return stage;

	}

}
