package osbot_scripts.bot.utils;

import java.util.ArrayList;
import java.util.Random;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.AccountStage;

public class RandomUtil {

	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumberInRange(int min, int max) {

//		if (min >= max) {
//			throw new IllegalArgumentException("max must be greater than min");
//		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * 
	 * @return
	 */
	public static AccountStage gextNextAccountStage(MethodProvider prov) {
		ArrayList<AccountStage> stagesToDo = new ArrayList<AccountStage>();
	
		if (prov.getConfigs().get(29) < 2) {
			stagesToDo.add(AccountStage.QUEST_COOK_ASSISTANT);
		}
		if (prov.getConfigs().get(144) < 100) {
			stagesToDo.add(AccountStage.QUEST_ROMEO_AND_JULIET);
		}
		if (prov.getConfigs().get(179) < 21) {
			stagesToDo.add(AccountStage.QUEST_SHEEP_SHEARER);
		}
		//When can't add anything
		if (stagesToDo.size() == 0) {
			stagesToDo.add(AccountStage.UNKNOWN);
		}
		//Cant be negative
		int max = (stagesToDo.size() - 1) < 0 ? 0 : (stagesToDo.size() - 1);
		int random = getRandomNumberInRange(0, max);
		AccountStage stage = stagesToDo.get(random);
		return stage;
	}
	
}
