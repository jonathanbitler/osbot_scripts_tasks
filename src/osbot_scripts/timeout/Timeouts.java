package osbot_scripts.timeout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import osbot_scripts.task.TaskSkeleton;

public abstract class Timeouts {

	/**
	 * The timeout value
	 * 
	 * @return
	 */
	public abstract int getTimeoutValue(TaskSkeleton task);

	/**
	 * All the timeout classes
	 * 
	 * @return
	 */
	public List<Class> getTimeoutClasses() {
		try {
			List<Class> timeoutClasses = Arrays.asList(ClassLoader.getClasses("osbot_scripts.framework"));
			return timeoutClasses.stream().filter(c -> c.getSimpleName().contains("Task")).collect(Collectors.toList());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
