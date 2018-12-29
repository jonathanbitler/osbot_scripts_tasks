package osbot_scripts.task;

import java.io.IOException;

public interface Task {
	
	/**
	 * The name of the script
	 * @return
	 */
	public String scriptName();

	/**
	 * 
	 * @return
	 */
	public int requiredConfigQuestStep();
	
	/**
	 * 
	 * @return
	 */
	public boolean ranOnStart();
	
	/**
	 * 
	 */
	public void onStart();
	
	/**
	 * The condition to when it may run
	 * @return
	 */
	public boolean startCondition();
	
	/**
	 * When it is running, the loop
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void loop() throws InterruptedException, IOException;
	
	/**
	 * When is it finished and can continue to the next task?
	 * @return
	 */
	public boolean finished();
	
}
