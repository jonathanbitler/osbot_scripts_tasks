package osbot_scripts.timeout;

public class TimeoutTest {

	
	public static void main(String args[]) {
		Timeouts timeout = new MiningTimeout();
		
		System.out.println(timeout.getTimeoutClasses());
	}
	
}
