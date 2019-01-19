package osbot_scripts.scripttypes.types;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;

import osbot_scripts.config.Config;
import osbot_scripts.qp7.progress.DoricsQuestConfig;
import osbot_scripts.scripttypes.ScriptAbstract;
import osbot_scripts.taskhandling.TaskHandler;

public class MiningType extends ScriptAbstract {

	public boolean pickupBronzePickaxe() {
		getQuest().resetStage(getQuest().getEvent().getScript());

		getProvider().getWalking().webWalk(new Area(new int[][] { { 3228, 3226 }, { 3227, 3225 }, { 3227, 3220 },
				{ 3231, 3220 }, { 3231, 3225 }, { 3230, 3226 }, { 3228, 3226 } }).setPlane(2));
		GroundItem item = getProvider().getGroundItems().closest("Bronze pickaxe");
		if (item != null) {
			item.interact();
		}
		getProvider().log("Going to pick up bronze pickaxe!");

		if (getProvider().getInventory().contains("Bronze pickaxe")) {
			getProvider().log("Got bronze pickaxe, walking to position!");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(1);
			return true;
		}
		return false;
	}

	public void walkToMiningArea() {
		getProvider().getWalking().webWalk(DoricsQuestConfig.MINING_AREA);
		getProvider().getWalking().walkPath(DoricsQuestConfig.MINING_POSITION);
	}

	@Override
	public void loop() {
		getProvider().log("Executing side script");

		boolean onPickaxeFloor = new Area(new int[][] { { 3228, 3226 }, { 3227, 3225 }, { 3227, 3220 }, { 3231, 3220 },
				{ 3231, 3225 }, { 3230, 3226 }, { 3228, 3226 } }).setPlane(2).contains(getProvider().myPlayer())
				|| new Area(new int[][] { { 3228, 3226 }, { 3227, 3225 }, { 3227, 3220 }, { 3231, 3220 },
						{ 3231, 3225 }, { 3230, 3226 }, { 3228, 3226 } }).setPlane(1)
								.contains(getProvider().myPlayer());

		boolean finishPickupPickaxe = false;
		boolean inLumbridge = new Area(new int[][] { { 3214, 3228 }, { 3215, 3208 }, { 3231, 3211 }, { 3229, 3228 },
				{ 3220, 3230 }, { 3214, 3228 } }).contains(getProvider().myPlayer());
		boolean doesntHaveAnyPickaxe = Config.doesntHaveAnyPickaxe(getProvider());
		boolean inMiningZone = DoricsQuestConfig.MINING_AREA.contains(getProvider().myPlayer());

		// Picking up pickaxe
		while (!finishPickupPickaxe) {
			if (inLumbridge && doesntHaveAnyPickaxe) {
				finishPickupPickaxe = pickupBronzePickaxe();
			} else if (getProvider().getBank().isOpen() && doesntHaveAnyPickaxe) {
				finishPickupPickaxe = pickupBronzePickaxe();
			} else {
				finishPickupPickaxe = true;
			}
		}

		// Walking back to mining area after it has died
		if (!doesntHaveAnyPickaxe && inLumbridge) {
			walkToMiningArea();
		}

		// On pickaxe area
		if (onPickaxeFloor) {
			boolean finishWalkingBack = false;

			while (!finishWalkingBack) {
				walkToMiningArea();

				if (inMiningZone) {
					finishWalkingBack = true;
				}
			}
		}

	}

	@Override
	public void taskOutTaskAttempts(TaskHandler tasks) {

	}

}
