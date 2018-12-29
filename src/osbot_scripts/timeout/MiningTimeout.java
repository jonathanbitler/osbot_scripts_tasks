package osbot_scripts.timeout;

import osbot_scripts.task.TaskSkeleton;

public class MiningTimeout extends Timeouts {

	@Override
	public int getTimeoutValue(TaskSkeleton task) {
		return -1;
//		for (Class<?> timeoutClass : getTimeoutClasses()) {
//			if (timeoutClass.getSimpleName().equalsIgnoreCase(task.getClass().getSimpleName())) {
//				
//			}
//		}

	}

}
