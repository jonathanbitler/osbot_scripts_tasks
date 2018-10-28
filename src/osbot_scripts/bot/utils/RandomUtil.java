package osbot_scripts.bot.utils;

import java.util.Random;

import osbot_scripts.framework.AccountStage;

public class RandomUtil {

	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * 
	 * @return
	 */
	public static AccountStage gextNextAccountStage() {
		int random = getRandomNumberInRange(0, 3);
		if (random == 0) {
			return AccountStage.QUEST_COOK_ASSISTANT;
		} else if (random == 1) {
			return AccountStage.QUEST_ROMEO_AND_JULIET;
		} else if (random == 2) {
			return AccountStage.QUEST_SHEEP_SHEARER;
		}
		return null;
	}
	
}
