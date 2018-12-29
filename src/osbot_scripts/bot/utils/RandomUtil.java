package osbot_scripts.bot.utils;

import java.util.Random;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bottypes.BotType;
import osbot_scripts.bottypes.PlayerTask;
import osbot_scripts.events.LoginEvent;
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
	public static AccountStage gextNextAccountStage(MethodProvider prov, LoginEvent event) {
		BotType type = PlayerTask.getSingleton().getBotType(prov, event);
		return type.getNextTask(prov);
	}

}
