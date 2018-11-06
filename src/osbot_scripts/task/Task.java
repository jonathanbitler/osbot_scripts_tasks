package osbot_scripts.task;

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
	 */
	public void loop() throws InterruptedException;
	
	/**
	 * When is it finished and can continue to the next task?
	 * @return
	 */
	public boolean finished();
	
}
