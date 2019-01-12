package osbot_scripts.scripttypes.types;

import osbot_scripts.scripttypes.ScriptAbstract;
import osbot_scripts.taskhandling.TaskHandler;

public class WoodcuttingType extends ScriptAbstract {
	
	@Override
	public void loop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskOutTaskAttempts(TaskHandler tasks) {
		// TODO Auto-generated method stub

		if (tasks.getProvider().getDialogues().isPendingContinuation()) {
			tasks.getProvider().getDialogues().clickContinue();
		}
	}

}
