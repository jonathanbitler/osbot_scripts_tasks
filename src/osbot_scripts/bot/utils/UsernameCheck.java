package osbot_scripts.bot.utils;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;

public class UsernameCheck {

	public static void checkUsername(MethodProvider api) {

		api.log("username: " + api.myPlayer().getName());
		api.log("email: " + api.getBot().getUsername());

		DatabaseUtilities.updateAccountUsername(api, api.getBot().getUsername(), api.myPlayer().getName());
		api.log("Inserted username into database corresponding with the email");
	}
}
