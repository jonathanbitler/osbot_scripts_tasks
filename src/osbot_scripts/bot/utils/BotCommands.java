package osbot_scripts.bot.utils;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.database.DatabaseUtilities;

public class BotCommands {

	/**
	 * 
	 * @param pid
	 */
	public static void killProcess(MethodProvider api, Script bot) {
		try {
			DatabaseUtilities.updateLoginStatus(api.getBot().getUsername(), "DEFAULT");
			UsernameCheck.checkUsername(api);
			bot.stop();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
//        try {
//            if (System.getProperty("os.name").startsWith("Windows")) {
//                Runtime.getRuntime().exec("Taskkill /PID " + pid + " /F");
//            } else {
//                Runtime.getRuntime().exec("kill -9 " + pid);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
	
}
