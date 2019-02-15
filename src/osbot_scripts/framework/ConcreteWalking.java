package osbot_scripts.framework;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.qp7.progress.WalkToGrandExchangeIfNotThere;

public class ConcreteWalking {

	public static void walkToGe(MethodProvider api, LoginEvent login) {
		// If the player is not in the grand exchange area, then walk to it
		WalkToGrandExchangeIfNotThere.walk(api, login);
	}

}
