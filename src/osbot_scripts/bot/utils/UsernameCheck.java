package osbot_scripts.bot.utils;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;

public class UsernameCheck {

	public static void checkUsername(MethodProvider api, LoginEvent login) {

		api.log("username: " + api.myPlayer().getName());
		api.log("email: " + api.getBot().getUsername());

		DatabaseUtilities.updateAccountUsername(api, api.getBot().getUsername(), api.myPlayer().getName(), login);
		api.log("Inserted username into database corresponding with the email");
	}
}
