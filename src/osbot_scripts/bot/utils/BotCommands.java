package osbot_scripts.bot.utils;

import org.osbot.rs07.script.Script;

public class BotCommands {

	/**
	 * 
	 * @param pid
	 */
	public static void killProcess(Script bot) {
		try {
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
