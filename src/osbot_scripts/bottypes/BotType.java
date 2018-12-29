package osbot_scripts.bottypes;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.AccountStage;

public interface BotType {

	public AccountStage getNextTask(MethodProvider provider);

}
