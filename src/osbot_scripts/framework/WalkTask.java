package osbot_scripts.framework;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

import com.mysql.cj.log.Log;

import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;

public class WalkTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private boolean webWalking = false;

	private Area finishArea;

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			List<Position> pathToWalk, Area finishArea) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(pathToWalk);
		setConfigQuestId(configQuestId);
		setFinishArea(finishArea);
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Position pathToWalk,
			boolean deleteAlreadyFound, Area finishArea) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(Arrays.asList(pathToWalk));
		setConfigQuestId(configQuestId);
		setWebWalking(deleteAlreadyFound);
		setFinishArea(finishArea);
	}

	private List<Position> pathToWalk;

	@Override
	public String scriptName() {
		// TODO Auto-generated method stub
		return this.getScriptName();
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return correctStepInQuest();
	}

	@Override
	public void onStart() {
		int delete = -1;
		// TODO Auto-generated method stub
		if (!isWebWalking()) {
			for (int i = 0; i < getPathToWalk().size(); i++) {
				if (getProv().myPlayer().getArea(20).contains(getPathToWalk().get(i))) {
					delete = (i - 1);
					// getProv().log("set delete to: "+delete);
				}
			}
			if (delete > 0) {
				int index = 0;
				Iterator<Position> itr = getPathToWalk().iterator();
				while (itr.hasNext()) {
					Position pos = itr.next();
					// getProv().log(index+" "+delete);
					if (index < delete) {
						itr.remove();
					}
					index++;
				}
			}
		}
		ranOnStart = true;
	}

	@Override
	public boolean ranOnStart() {
		// TODO Auto-generated method stub
		return ranOnStart;
	}

	/**
	 * @param ranOnStart
	 *            the ranOnStart to set
	 */
	public void setRanOnStart(boolean ranOnStart) {
		this.ranOnStart = ranOnStart;
	}

	private int failToWalk = 0;

	@Override
	public void loop() {
		// if (!ranOnStart) {
		onStart();
		// }

		if (ranOnStart) {
			if (failToWalk > 3) {
//				for (int i = 0; i < getPathToWalk().size(); i++) {
					int i = 0;
					int posX = getPathToWalk().get(i).getX();
					int posY = getPathToWalk().get(i).getY();
					int plusOne = i + 1;

					if ((plusOne) > (getPathToWalk().size() - 1)) {
						return;
					}
					int posX2 = getPathToWalk().get(plusOne).getX();
					int posY2 = getPathToWalk().get(plusOne).getY();
					int diffX = (posX2 - posX) / 2;
					int diffY = (posY2 - posY) / 2;
					Position newPosition = getPathToWalk().get(i).translate(diffX, diffY);

					getProv().log("send position set to: " + diffX + " " + diffY + " " + newPosition.getX() + " "
							+ newPosition.getY());
					
					getProv().getDoorHandler().handleNextObstacle(newPosition);
					if (getProv().getWalking().walk(newPosition)) {
						failToWalk = 0;
					} else {
						getProv().log("still failed to walk..");
					}

//				}
			}
			failToWalk++;
		}

		if (isWebWalking()) {
			getProv().log("WALKING!KLJ");
			getProv().getWalking().webWalk(getPathToWalk().get(0));
		} else {

			getProv().log("size to walk: " + getPathToWalk().size());
			long lastWalk = System.currentTimeMillis();
			
			for (int i = 0; i < getPathToWalk().size(); i++) {
				// while (!getProv().myPlayer().getArea(20).contains(getPathToWalk().get(i))) {
				getProv().getDoorHandler().handleNextObstacle(getPathToWalk().get(i));
				if (!getProv().getWalking().walk(getPathToWalk().get(i))) {
				}
				long endWalk = System.currentTimeMillis();
				long took = (endWalk - lastWalk) / 3600;
				getProv().log("taking next path.. (" + i + "/" + getPathToWalk().size() + ")"+" took: "+took+" seconds");
				// getProv().log("waiting for walking sleep..");
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
			}
		}
	}

	@Override
	public boolean finished() {
		// TODO Auto-generated method stub
		int pos = getProv().myPlayer().getPosition().getZ();
		int pos2 = getPathToWalk().get(getPathToWalk().size() - 1).getZ();

		if (getProv().myPlayer().getPosition().getZ() > 0) {
			return getPathToWalk().size() == 0 || (pos == pos2 && getFinishArea().contains(getProv().myPlayer()));
		}
		return getPathToWalk().size() == 0 || pos == pos2 && getFinishArea().contains(getProv().myPlayer());
	}

	/**
	 * @return the pathToWalk
	 */
	public List<Position> getPathToWalk() {
		return pathToWalk;
	}

	/**
	 * @param pathToWalk
	 *            the pathToWalk to set
	 */
	public void setPathToWalk(List<Position> pathToWalk) {
		this.pathToWalk = pathToWalk;
	}

	@Override
	public int requiredConfigQuestStep() {
		// TODO Auto-generated method stub
		return getCurrentQuestProgress();
	}

	/**
	 * @return the webWalking
	 */
	public boolean isWebWalking() {
		return webWalking;
	}

	/**
	 * @param webWalking
	 *            the webWalking to set
	 */
	public void setWebWalking(boolean webWalking) {
		this.webWalking = webWalking;
	}

	/**
	 * @return the finishArea
	 */
	public Area getFinishArea() {
		return finishArea;
	}

	/**
	 * @param finishArea
	 *            the finishArea to set
	 */
	public void setFinishArea(Area finishArea) {
		this.finishArea = finishArea;
	}

}
