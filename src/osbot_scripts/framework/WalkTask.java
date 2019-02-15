package osbot_scripts.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.objectHandling.LadderAndDoor;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.task.Task;
import osbot_scripts.task.TaskSkeleton;
import osbot_scripts.util.Sleep;

public class WalkTask extends TaskSkeleton implements Task {

	private boolean ranOnStart = false;

	private boolean webWalking = false;

	private Area finishArea;

	private Area beginArea;

	private Script script;

	private LoginEvent login;

	private boolean walkPathWithoutSteps;

	private QuestStep quest;

	private List<LadderAndDoor> ladder;

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			List<Position> pathToWalk, Area finishArea, Script script, LoginEvent login, boolean webWalk,
			boolean walkPathWithoutSteps, QuestStep step) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(pathToWalk);
		setPathToWalkCopy(pathToWalk);
		setConfigQuestId(configQuestId);
		setFinishArea(finishArea);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		this.quest = step;
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			List<Position> pathToWalk, Area finishArea, Script script, LoginEvent login, boolean webWalk,
			boolean walkPathWithoutSteps, QuestStep step, List<LadderAndDoor> ladder) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(pathToWalk);
		setPathToWalkCopy(pathToWalk);
		setConfigQuestId(configQuestId);
		setFinishArea(finishArea);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		setLadder(ladder);
		this.quest = step;
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			List<Position> pathToWalk, Area beginArea, Area finishArea, Script script, LoginEvent login,
			boolean webWalk, boolean walkPathWithoutSteps, QuestStep step) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(pathToWalk);
		setConfigQuestId(configQuestId);
		setFinishArea(finishArea);
		setPathToWalkCopy(pathToWalk);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		setBeginArea(beginArea);
		this.quest = step;
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov,
			List<Position> pathToWalk, Area beginArea, Area finishArea, Script script, LoginEvent login,
			boolean webWalk, boolean walkPathWithoutSteps, QuestStep step, List<LadderAndDoor> ladder) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(pathToWalk);
		setConfigQuestId(configQuestId);
		setFinishArea(finishArea);
		setPathToWalkCopy(pathToWalk);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		setBeginArea(beginArea);
		setLadder(ladder);
		this.quest = step;
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Position pathToWalk,
			boolean deleteAlreadyFound, Area finishArea, Script script, LoginEvent login, boolean webWalk,
			boolean walkPathWithoutSteps, QuestStep step) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(Arrays.asList(pathToWalk));
		setConfigQuestId(configQuestId);
		setWebWalking(deleteAlreadyFound);
		setFinishArea(finishArea);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		this.quest = step;
	}

	public WalkTask(String scriptName, int questProgress, int questConfig, MethodProvider prov, Position pathToWalk,
			boolean deleteAlreadyFound, Area finishArea, Script script, LoginEvent login, boolean webWalk,
			boolean walkPathWithoutSteps, QuestStep step, List<LadderAndDoor> ladder) {
		setScriptName(scriptName);
		setProv(prov);
		setCurrentQuestProgress(questProgress);
		setPathToWalk(Arrays.asList(pathToWalk));
		setConfigQuestId(configQuestId);
		setWebWalking(deleteAlreadyFound);
		setFinishArea(finishArea);
		setScript(script);
		setLogin(login);
		setWebWalking(webWalk);
		setWalkPathWithoutSteps(walkPathWithoutSteps);
		setLadder(ladder);
		this.quest = step;
	}

	//

	private List<Position> pathToWalk;

	private List<Position> pathToWalkCopy;

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
				if (getApi().myPlayer().getArea(20).contains(getPathToWalk().get(i))) {
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

	private static final Area COOKING_ASSISTANT_WRONG_PLACE = new Area(
			new int[][] { { 3205, 3225 }, { 3205, 3218 }, { 3213, 3218 }, { 3213, 3227 }, { 3205, 3227 } });

	public Area getArea(Position pos, int size) {
		return new Area(pos.translate(-size, -size), pos.translate(size, size));
	}

	private void checkifInCookingAssistancePlace() {
		if (COOKING_ASSISTANT_WRONG_PLACE.contains(getApi().myPlayer())) {
			getApi().log("Got stuck my cooking place door, trying to get out!");
			ArrayList<Position> pos = new ArrayList<Position>(Arrays.asList(new Position(3208, 3213, 0),
					new Position(3211, 3209, 0), new Position(3214, 3218, 0), new Position(3224, 3218, 0),
					new Position(3226, 3218, 0), new Position(3234, 3224, 0), new Position(3236, 3225, 0)));

			for (int i = 0; i < pos.size(); i++) {
				getApi().getDoorHandler().handleNextObstacle(getPathToWalk().get(i));
				if (!getApi().getWalking().walk(getPathToWalk().get(i))) {
					failToWalk++;
				}
			}
		}
	}

	private void failedToWalkToPositionSoSetToWalkingStuck() {
		if (failToWalk > 10 && getScript() != null && getLogin() != null) {
			DatabaseUtilities.updateAccountStatusInDatabase(getApi(), "WALKING_STUCK", login.getUsername(), login);
			BotCommands.killProcess(getApi(), getScript(), "BECAUSE OF NOT BEING ABLE TO WALK", login);
		}
	}

	private void walkIfIsCurrentlyWalkingStuck() {
		Area areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath = getFinishArea() == null
				? getArea(getPathToWalk().get(getPathToWalk().size() - 1), 3)
				: getFinishArea();

		if (getLogin() != null && getLogin().getAccountStage().equalsIgnoreCase("WALKING-STUCK")) {
			// getApi().getWalking().webWalk(areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath);
		}
	}

	private boolean containsInPathOnWalking() {
		boolean contains = false;
		for (Position p : getPathToWalk()) {
			if (p.getArea(10).contains(getApi().myPlayer())) {
				contains = true;
			}
		}
		return contains;
	}

	public static final ArrayList<Position> RIMMINGTON_DEPOSIT_BOX_TO_FALADOR_BANK = new ArrayList<Position>(
			Arrays.asList(new Position(3045, 3235, 0), new Position(3045, 3235, 0), new Position(3044, 3235, 0),
					new Position(3043, 3236, 0), new Position(3042, 3237, 0), new Position(3042, 3238, 0),
					new Position(3042, 3239, 0), new Position(3042, 3240, 0), new Position(3042, 3241, 0),
					new Position(3042, 3242, 0), new Position(3042, 3243, 0), new Position(3042, 3244, 0),
					new Position(3042, 3245, 0), new Position(3042, 3246, 0), new Position(3042, 3247, 0),
					new Position(3042, 3248, 0), new Position(3042, 3249, 0), new Position(3041, 3250, 0),
					new Position(3041, 3251, 0), new Position(3040, 3252, 0), new Position(3039, 3253, 0),
					new Position(3038, 3254, 0), new Position(3037, 3255, 0), new Position(3036, 3256, 0),
					new Position(3035, 3257, 0), new Position(3035, 3258, 0), new Position(3035, 3259, 0),
					new Position(3034, 3260, 0), new Position(3034, 3261, 0), new Position(3034, 3262, 0),
					new Position(3033, 3263, 0), new Position(3032, 3264, 0), new Position(3032, 3265, 0),
					new Position(3032, 3266, 0), new Position(3032, 3267, 0), new Position(3031, 3267, 0),
					new Position(3030, 3268, 0), new Position(3029, 3268, 0), new Position(3028, 3269, 0),
					new Position(3027, 3270, 0), new Position(3026, 3270, 0), new Position(3025, 3270, 0),
					new Position(3024, 3271, 0), new Position(3023, 3271, 0), new Position(3022, 3271, 0),
					new Position(3021, 3271, 0), new Position(3021, 3272, 0), new Position(3021, 3273, 0),
					new Position(3020, 3274, 0), new Position(3019, 3274, 0), new Position(3018, 3274, 0),
					new Position(3017, 3274, 0), new Position(3016, 3274, 0), new Position(3015, 3275, 0),
					new Position(3014, 3276, 0), new Position(3013, 3277, 0), new Position(3012, 3278, 0),
					new Position(3011, 3279, 0), new Position(3011, 3280, 0), new Position(3011, 3281, 0),
					new Position(3011, 3282, 0), new Position(3011, 3283, 0), new Position(3010, 3284, 0),
					new Position(3009, 3285, 0), new Position(3008, 3286, 0), new Position(3007, 3287, 0),
					new Position(3007, 3288, 0), new Position(3007, 3289, 0), new Position(3007, 3290, 0),
					new Position(3007, 3291, 0), new Position(3007, 3292, 0), new Position(3007, 3293, 0),
					new Position(3007, 3294, 0), new Position(3007, 3295, 0), new Position(3007, 3296, 0),
					new Position(3007, 3297, 0), new Position(3007, 3298, 0), new Position(3007, 3299, 0),
					new Position(3007, 3300, 0), new Position(3007, 3301, 0), new Position(3007, 3302, 0),
					new Position(3007, 3303, 0), new Position(3007, 3304, 0), new Position(3007, 3305, 0),
					new Position(3007, 3306, 0), new Position(3007, 3307, 0), new Position(3007, 3308, 0),
					new Position(3007, 3309, 0), new Position(3007, 3310, 0), new Position(3007, 3311, 0),
					new Position(3007, 3312, 0), new Position(3007, 3313, 0), new Position(3007, 3314, 0),
					new Position(3007, 3315, 0), new Position(3007, 3316, 0), new Position(3007, 3317, 0),
					new Position(3007, 3318, 0), new Position(3007, 3319, 0), new Position(3007, 3320, 0),
					new Position(3007, 3321, 0), new Position(3007, 3322, 0), new Position(3007, 3323, 0),
					new Position(3007, 3324, 0), new Position(3007, 3325, 0), new Position(3007, 3326, 0),
					new Position(3007, 3327, 0), new Position(3007, 3328, 0), new Position(3007, 3329, 0),
					new Position(3007, 3330, 0), new Position(3007, 3331, 0), new Position(3007, 3332, 0),
					new Position(3007, 3333, 0), new Position(3007, 3334, 0), new Position(3007, 3335, 0),
					new Position(3007, 3336, 0), new Position(3007, 3337, 0), new Position(3007, 3338, 0),
					new Position(3007, 3339, 0), new Position(3007, 3340, 0), new Position(3007, 3341, 0),
					new Position(3007, 3342, 0), new Position(3007, 3343, 0), new Position(3007, 3344, 0),
					new Position(3007, 3345, 0), new Position(3007, 3346, 0), new Position(3007, 3347, 0),
					new Position(3007, 3348, 0), new Position(3007, 3349, 0), new Position(3007, 3350, 0),
					new Position(3007, 3351, 0), new Position(3007, 3352, 0), new Position(3007, 3353, 0),
					new Position(3007, 3354, 0), new Position(3007, 3355, 0), new Position(3007, 3356, 0),
					new Position(3007, 3357, 0), new Position(3007, 3358, 0), new Position(3008, 3359, 0),
					new Position(3009, 3359, 0), new Position(3010, 3359, 0), new Position(3011, 3359, 0),
					new Position(3012, 3359, 0), new Position(3012, 3358, 0), new Position(3012, 3357, 0),
					new Position(3011, 3356, 0)));

	public void handleClostestObject(List<LadderAndDoor> laddersAndDoors) throws InterruptedException {
		for (LadderAndDoor door : laddersAndDoors) {
			//

			if (!door.getBeginArea().contains(getApi().myPlayer())) {
				getApi().getWalking().walk(door.getBeginArea());

				Sleep.sleepUntil(() -> door.getBeginArea().contains(getApi().myPlayer()), 1000);
				if (!door.getBeginArea().contains(getApi().myPlayer())) {
					continue;
				}
			}

			boolean finish = false;

			if (!finish) {
				Area beginArea = !door.isReverse() ? door.getBeginArea() : door.getFinalArea();
				Area finalAreaToSuccess = !door.isReverse() ? door.getFinalArea() : door.getBeginArea();

				if (finalAreaToSuccess.contains(getApi().myPlayer())) {
					getApi().log("Successfully finished object");
					break;
				}

				List<RS2Object> ladders = getApi().getObjects().getAll().stream()
						.filter(obj -> beginArea.contains(obj.getX(), obj.getY())
								&& obj.getName().toLowerCase().equalsIgnoreCase(door.getName()))
						.collect(Collectors.toList());

				Collections.sort(ladders, new Comparator<RS2Object>() {
					public int compare(RS2Object s1, RS2Object s2) {
						int distanceObj1 = door.getFinalArea().getRandomPosition().distance(s1);
						int distanceObj2 = door.getFinalArea().getRandomPosition().distance(s2);
						return Integer.compare(distanceObj1, distanceObj2);
					}
				});

				ladders.forEach(lad -> getApi().log(lad.getName() + " " + getApi().myPosition().distance(lad)));

				getApi().log("Not finished yet with object");

				if (ladders.size() == 0) {
					getApi().log("Couldn't find an object with the name: " + door.getName());
					break;
				}
				int index = 0;
				RS2Object ladder = ladders.get(index);
				if (!getApi().getMap().canReach(ladder)) {
					List<Position> pos = getApi().getDoorHandler().generatePath(ladder);

					getApi().log("generated path: " + pos);

					if (pos != null) {
						for (Position p : pos) {
							getApi().log("handlign next obj");
							getApi().getDoorHandler().handleNextObstacle(p.getArea(1));
							Sleep.sleepUntil(() -> getApi().getMap().canReach(ladder), 1000);
						}
					}

					Sleep.sleepUntil(() -> getApi().getMap().canReach(ladder), 10000, 1000);

					if (!getApi().getMap().canReach(ladder)) {
						getApi().log("Couldn't use the object");
						break;
					}
				}
				getApi().log("Interacting with object");
				ladders.get(index).interact(door.getInteraction());

				Sleep.sleepUntil(() -> finalAreaToSuccess.contains(getApi().myPlayer()), 10000, 1000);
			}
		}
	}

	private Position getPositionWithoutObject(Area area) {
		for (Position p : area.getPositions()) {
			if (getApi().getObjects().closest(obj -> obj.getX() == p.getX() && obj.getY() == p.getY()) == null) {
				return p;
			}
		}
		return null;
	}

	private long lastAnimation = System.currentTimeMillis();

	private long currentlyActive = -1;

	private long maxActive = 10_000;

	private Thread checkThread = null;

	private void handleObject() {
		if (getLadder() == null || getLadder().size() <= 0) {
			return;
		}
		for (LadderAndDoor door : getLadder()) {
			// if (door.getBeginArea().contains(getApi().myPlayer())) {
			getApi().getDoorHandler().handleNextObstacle(door.getBeginArea());
			// }
		}
	}

	@Override
	public void loop() {

		getApi().log("thread: " + checkThread);
		if ((checkThread == null) || (checkThread != null && !checkThread.isAlive())) {
			checkThread = new Thread(() -> {
				while (true) {
					currentlyActive += 400;

					if ((currentlyActive > maxActive) || (getFinishArea().contains(getApi().myPlayer()))) {
						getApi().log("breaked thread...");
						break;
					}
					if (getApi().myPlayer().isMoving()) {
						lastAnimation = System.currentTimeMillis();
					}
					if ((!getApi().myPlayer().isMoving()) && (System.currentTimeMillis() - lastAnimation) > 5_000) {
						getApi().log("moving " + getApi().myPlayer().isMoving() + " "
								+ (System.currentTimeMillis() - lastAnimation));

						// getApi().getDoorHandler().handleNextObstacle(area)
						handleObject();
					}
					getApi().log("thread is active... " + currentlyActive);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			checkThread.start();
		}

		boolean shouldWebWalk = (getBeginArea() != null
				&& (!getBeginArea().contains(getApi().myPlayer()) && !containsInPathOnWalking())) || (isWebWalking());
		boolean shouldNormalWalk = isWalkPathWithoutSteps();
		Area areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath = getFinishArea() == null
				? getArea(getPathToWalk().get(getPathToWalk().size() - 1), 3)
				: getFinishArea();
		boolean hasLadderInPath = getLadder() != null;

		Position pos2 = getPositionWithoutObject(areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath);
		boolean cantReach = !getApi().getMap().canReach(pos2);
		boolean inArea = pos2.getArea(25).contains(getApi().myPlayer());
		getApi().log("reach: " + cantReach + " in area: " + inArea + " " + pos2);

		handleObject();

		checkifInCookingAssistancePlace();
		failedToWalkToPositionSoSetToWalkingStuck();
		walkIfIsCurrentlyWalkingStuck();

		if (hasLadderInPath) {
			try {
				handleClostestObject(getLadder());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (shouldWebWalk) {
			getApi().log("Initializing webwalking... fail to walk: " + failToWalk);
			DatabaseUtilities.insertLoggingMessage(getApi(), quest.getEvent(), "WEB_WALKING",
					"WALKTASK FROM: " + (getApi().myPlayer().getPosition()) + " TO: "
							+ (getFinishArea() != null ? getFinishArea().getPositions() : "null") + " for: "
							+ this.getClass().getSimpleName());
			// if
			// (!getApi().getWalking().webWalk(areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath))
			// {
			// failToWalk++;
			// }
		} else if (shouldNormalWalk) {
			if (getPathToWalk().get(0).getZ() == getApi().myPlayer().getZ()) {
				getApi().log("Initializing normal walking... fail to walk: " + failToWalk);

				if (!getApi().getWalking().walkPath(getPathToWalk())) {
					failToWalk++;
				}
			} else {
				getApi().log("Not walking because of not the same height");
			}
		}

		// At finish should contain area
		if (getFinishArea() != null && !getFinishArea().contains(getApi().myPlayer())) {
			tryToWalkToFinishArea();
		}

	}

	public Position getValidPosition(List<Position> positions) {
		for (Position p : positions) {
			if (getApi().getMap().canReach(p)) {
				return p;
			}
		}
		return null;
	}

	public Position getValidPosition(Area area) {
		for (Position p : area.getPositions()) {
			if (getApi().getMap().canReach(p)) {
				return p;
			}
		}
		return null;
	}

	public Position getValidPositionAndNoObject(Area area) {
		if (getPositionWithoutObject(area) == null) {
			for (Position p : area.getPositions()) {
				if (getApi().getMap().canReach(p)) {
					return p;
				}
			}
		}
		return null;
	}

	private void tryToWalkToFinishArea() {
		Area areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath = getFinishArea() == null
				? getArea(getPathToWalk().get(getPathToWalk().size() - 1), 3)
				: getFinishArea();
		Position p = getValidPositionAndNoObject(areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath);

		// if
		// (getFinishArea().getRandomPosition().getArea(15).contains(getApi().myPlayer()))
		// {
		if (!getFinishArea().contains(getApi().myPlayer())) {
			getApi().getWalking().walk(p);
			getApi().log("walkings to correct area ");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// }

		if (!getFinishArea().contains(getApi().myPlayer())) {
			DatabaseUtilities.insertLoggingMessage(getApi(), quest.getEvent(), "WEB_WALKING",
					"WALKTASK 2 FROM: " + (getApi().myPlayer().getPosition()) + " TO: "
							+ (getFinishArea() != null ? getFinishArea().getPositions() : "null") + " for: "
							+ this.getClass().getSimpleName());

			// getApi().getWalking().webWalk(areaToWalkToIfIsWebWalkingBasedOnEndOfWalkingPath);
		}

	}

	@Override
	public boolean finished() {
		// TODO Auto-generated method stub
		int pos = getApi().myPlayer().getPosition().getZ();
		int pos2 = getPathToWalk().get(getPathToWalk().size() - 1).getZ();
		getApi().log(pos + "" + pos2 + " " + getFinishArea().contains(getApi().myPlayer()));

		if (isWalkPathWithoutSteps()) {
			return getFinishArea().contains(getApi().myPlayer());
		}
		if (getApi().myPlayer().getPosition().getZ() > 0) {
			return (getPathToWalk().size() == 0) || (pos == pos2 && getFinishArea().contains(getApi().myPlayer()));
		}
		return (getPathToWalk().size() == 0) || (pos == pos2 && getFinishArea().contains(getApi().myPlayer()));
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

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

	/**
	 * @return the login
	 */
	public LoginEvent getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(LoginEvent login) {
		this.login = login;
	}

	/**
	 * @return the walkPathWithoutSteps
	 */
	public boolean isWalkPathWithoutSteps() {
		return walkPathWithoutSteps;
	}

	/**
	 * @param walkPathWithoutSteps
	 *            the walkPathWithoutSteps to set
	 */
	public void setWalkPathWithoutSteps(boolean walkPathWithoutSteps) {
		this.walkPathWithoutSteps = walkPathWithoutSteps;
	}

	/**
	 * @return the beginArea
	 */
	public Area getBeginArea() {
		return beginArea;
	}

	/**
	 * @param beginArea
	 *            the beginArea to set
	 */
	public void setBeginArea(Area beginArea) {
		this.beginArea = beginArea;
	}

	/**
	 * @return the pathToWalkCopy
	 */
	public List<Position> getPathToWalkCopy() {
		return pathToWalkCopy;
	}

	/**
	 * @param pathToWalkCopy
	 *            the pathToWalkCopy to set
	 */
	public void setPathToWalkCopy(List<Position> pathToWalkCopy) {
		this.pathToWalkCopy = pathToWalkCopy;
	}

	/**
	 * @return the ladder
	 */
	public List<LadderAndDoor> getLadder() {
		return ladder;
	}

	/**
	 * @param ladder
	 *            the ladder to set
	 */
	public void setLadder(List<LadderAndDoor> ladder) {
		this.ladder = ladder;
	}

}
