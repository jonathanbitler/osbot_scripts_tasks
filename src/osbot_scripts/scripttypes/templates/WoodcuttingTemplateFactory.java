package osbot_scripts.scripttypes.templates;

import osbot_scripts.bot.utils.RandomUtil;
import osbot_scripts.scripttypes.woodcutting.logs.LogsCuttingWestOfVarrock;
import osbot_scripts.scripttypes.woodcutting.oak.OakCuttingEastOfVarrock;
import osbot_scripts.scripttypes.woodcutting.oak.OakCuttingNorthOfVarrock;
import osbot_scripts.scripttypes.woodcutting.oak.OakCuttingWestOfVarrock;
import osbot_scripts.scripttypes.woodcutting.oak.OakCuttingWestOfVarrockUnder;

public class WoodcuttingTemplateFactory {

	public static WoodcuttingLocationTemplate getWoodcuttingOakTemplate() {

		int randomValue = RandomUtil.getRandomNumberInRange(0, 4);

		switch (randomValue) {
		case 0:
			return new OakCuttingEastOfVarrock();

		case 1:
			return new OakCuttingNorthOfVarrock();

		case 2:
			return new OakCuttingWestOfVarrock();

		case 3:
			return new OakCuttingWestOfVarrockUnder();
		}

		// try {
		// ArrayList<WoodcuttingLocationTemplate> woodCuttingTemplates = new
		// ArrayList<WoodcuttingLocationTemplate>();
		// Class<?>[] woodCuttingTemplatesFoundInPackage =
		// ClassLoader.getClasses("osbot_scripts.scripttypes.woodcutting.oak");
		//
		// for (Class<?> foundClasses : woodCuttingTemplatesFoundInPackage) {
		// System.out.println(foundClasses.getSimpleName());
		//
		// WoodcuttingLocationTemplate newInstanceOfWoodcuttingTemplate =
		// (WoodcuttingLocationTemplate) foundClasses.newInstance();
		// woodCuttingTemplates.add(newInstanceOfWoodcuttingTemplate);
		// }
		//
		// int randomValue = RandomUtil.getRandomNumberInRange(0,
		// woodCuttingTemplates.size() - 1);
		// return woodCuttingTemplates.get(randomValue);
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InstantiationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return new LogsCuttingWestOfVarrock();
	}
}
