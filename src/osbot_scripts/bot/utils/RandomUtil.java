package osbot_scripts.bot.utils;

import java.util.ArrayList;
import java.util.Random;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.config.Config;
import osbot_scripts.framework.AccountStage;

public class RandomUtil {

	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumberInRange(int min, int max) {

		// if (min >= max) {
		// throw new IllegalArgumentException("max must be greater than min");
		// }

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	/**
	 * 
	 * @return
	 */
	public static AccountStage gextNextAccountStage(MethodProvider prov) {
		ArrayList<AccountStage> stagesToDo = new ArrayList<AccountStage>();

		// When quest cooks assistant isn't completed yet
		if (prov.getConfigs().get(29) < 2) {
			stagesToDo.add(AccountStage.QUEST_COOK_ASSISTANT);
		}

		// When quest romeon and juliet isn't completed yet
		if (prov.getConfigs().get(144) < 100) {
			stagesToDo.add(AccountStage.QUEST_ROMEO_AND_JULIET);
		}

		// Dorics quest
		if (prov.getConfigs().get(31) < 100) {
			stagesToDo.add(AccountStage.QUEST_DORICS_QUEST);
		}

		// When quest sheep shearer isn't completed yet
//		if (prov.getConfigs().get(179) < 21) {
//			stagesToDo.add(AccountStage.QUEST_SHEEP_SHEARER);
//		}
		// When mining is lower than 15 and having all quests compteted
		if (prov.getQuests().getQuestPoints() >= 7 && prov.getSkills().getStatic(Skill.MINING) < 30) {
			stagesToDo.add(AccountStage.MINING_LEVEL_TO_15);
		}

		// When everything is completed
		if (stagesToDo.size() == 0) {
			stagesToDo.add(AccountStage.MINING_IRON_ORE);
			// int randomNum = getRandomNumberInRange(0, 1);
			// if (randomNum == 0) {
			// stagesToDo.add(AccountStage.MINING_IRON_ORE);
			// } else {
			// stagesToDo.add(AccountStage.RIMMINGTON_IRON_ORE);
			// }
		}

		// Cant be negative
		int max = (stagesToDo.size() - 1) < 0 ? 0 : (stagesToDo.size() - 1);
		int random = getRandomNumberInRange(0, max);
		AccountStage stage = stagesToDo.get(random);

		// TODO revert
		if (!Config.TEST) {
			return stage;
		} else {
			return AccountStage.QUEST_DORICS_QUEST;
		}
	}

}
