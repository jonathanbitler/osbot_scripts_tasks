package osbot_scripts.timeout;

import java.io.IOException;
import java.util.ArrayList;

import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class TimeoutTest {

	public static void main(String args[]) {

		try {
			ArrayList<WoodcuttingLocationTemplate> woodCuttingTemplates = new ArrayList<WoodcuttingLocationTemplate>();
			Class<?>[] a = ClassLoader.getClasses("osbot_scripts.scripttypes.woodcutting.oak");

			for (Class<?> foundClasses : a) {
				System.out.println(foundClasses.getSimpleName());

				WoodcuttingLocationTemplate z = (WoodcuttingLocationTemplate) foundClasses.newInstance();
				woodCuttingTemplates.add(z);
			}


			for (int i = 0; i < 100; i++) {
				int randomValue = RandomUtil.getRandomNumberInRange(0, woodCuttingTemplates.size() - 1);
				System.out.println(woodCuttingTemplates.get(randomValue));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Timeouts timeout = new MiningTimeout();
		//
		// System.out.println(timeout.getTimeoutClasses());
	}

}
