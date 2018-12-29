package osbot_scripts.bottypes;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.framework.AccountStage;

public class Default extends BotDefaults implements BotType {

	@Override
	public AccountStage getNextTask(MethodProvider provider) {
		// When quest cooks assistant isn't completed yet
		if (provider.getConfigs().get(29) < 2) {
			stagesToDo.add(AccountStage.QUEST_COOK_ASSISTANT);
		}
		// When quest romeon and juliet isn't completed yet
		if (provider.getConfigs().get(144) < 100) {
			stagesToDo.add(AccountStage.QUEST_ROMEO_AND_JULIET);
		}
		// Dorics quest
		if (provider.getConfigs().get(31) < 100) {
			stagesToDo.add(AccountStage.QUEST_DORICS_QUEST);
		}
		// When mining is lower than 15 and having all quests compteted
		if (provider.getQuests().getQuestPoints() >= 7 && provider.getSkills().getStatic(Skill.MINING) < 31) {
			stagesToDo.add(AccountStage.MINING_LEVEL_TO_15);
		}
		// When everything is completed
		if (stagesToDo.size() == 0) {
			stagesToDo.add(AccountStage.MINING_IRON_ORE);
		}

		// Cant be negative
		int max = (stagesToDo.size() - 1) < 0 ? 0 : (stagesToDo.size() - 1);
		int random = RandomUtil.getRandomNumberInRange(0, max);
		AccountStage stage = stagesToDo.get(random);

		return stage;
	}

}
