package osbot_scripts.scripttypes;

import osbot_scripts.taskhandling.TaskHandler;

public interface ScriptType {

	public void loop();
	
	public void taskOutTaskAttempts(TaskHandler tasks);
	
}
