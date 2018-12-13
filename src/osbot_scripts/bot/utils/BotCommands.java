package osbot_scripts.bot.utils;

import java.util.Set;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import osbot_scripts.database.DatabaseConnection;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.util.Sleep;

public class BotCommands {

	// public static boolean threadAlive() {
	// Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	// for (Thread t : threadSet) {
	// if (t.getName().equalsIgnoreCase(DatabaseUtilities.DATABASE_THREAD_NAME)) {
	// return t.isAlive();
	// }
	// }
	// return true;
	// }

	public static void waitBeforeKill(MethodProvider api, String reason) {
		try {
			api.log("QUITING BECAUSE OF: " + reason);
			DatabaseConnection.getDatabase().disconnect();
			Thread.sleep(3_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}

	/**
	 * 
	 * @param login
	 *            TODO
	 * @param pid
	 */
	public static void killProcess(MethodProvider api, Script bot, String reason, LoginEvent login) {
		try {
			// DatabaseUtilities.updateLoginStatus(api, api.getBot().getUsername(),
			// "DEFAULT");
			api.log("QUITING BECAUSE OF: " + reason);
			UsernameCheck.checkUsername(api, login);

			api.log("Waiting 3 seconds for get request to send!");
			try {
				DatabaseConnection.getDatabase().disconnect();
				Thread.sleep(3_000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bot.stop();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// if (System.getProperty("os.name").startsWith("Windows")) {
		// Runtime.getRuntime().exec("Taskkill /PID " + pid + " /F");
		// } else {
		// Runtime.getRuntime().exec("kill -9 " + pid);
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

}
