package osbot_scripts;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.framework.WalkTask;
import osbot_scripts.objectHandling.LadderAndDoor;
import osbot_scripts.taskhandling.TaskHandler;
import osbot_scripts.util.Sleep;

@ScriptManifest(author = "pim97", info = "OBSTACLE_TEST", logo = "", name = "OBSTACLE_TEST", version = 1.0)
public class ObstacleTest extends Script {

	private boolean isLadderOrSomething(String name) {
		if (name.toLowerCase().contains("ladder") || name.toLowerCase().contains("staircase")) {
			return true;
		}
		return false;
	}

	public void handleClostestObject(List<LadderAndDoor> laddersAndDoors) throws InterruptedException {
		for (LadderAndDoor door : laddersAndDoors) {

			boolean finish = false;

			if (!finish) {
				Area beginArea = !door.isReverse() ? door.getBeginArea() : door.getFinalArea();
				Area finalAreaToSuccess = !door.isReverse() ? door.getFinalArea() : door.getBeginArea();

				if (finalAreaToSuccess.contains(myPlayer())) {
					log("Successfully finished object");
					break;
				}

				List<RS2Object> ladders = getObjects().getAll().stream()
						.filter(obj -> beginArea.contains(obj.getX(), obj.getY()) && isLadderOrSomething(obj.getName()))
						.collect(Collectors.toList());

				Collections.sort(ladders, new Comparator<RS2Object>() {
					public int compare(RS2Object s1, RS2Object s2) {
						int distanceObj1 = door.getFinalArea().getRandomPosition().distance(s1);
						int distanceObj2 = door.getFinalArea().getRandomPosition().distance(s2);
						return Integer.compare(distanceObj1, distanceObj2);
					}
				});

				ladders.forEach(lad -> log(lad.getName() + " " + myPosition().distance(lad)));

				log("Not finished yet with object");

				if (ladders.size() == 0) {
					log("Couldn't find an object with the name: " + door.getName());
					break;
				}
				int index = 0;
				RS2Object ladder = ladders.get(index);
				if (!getMap().canReach(ladder)) {
					List<Position> pos = getDoorHandler().generatePath(ladder);

					if (pos != null) {
						for (Position p : pos) {
							getDoorHandler().handleNextObstacle(p.getArea(1));
							Sleep.sleepUntil(() -> getMap().canReach(ladder), 1000);
						}
					}

					Sleep.sleepUntil(() -> getMap().canReach(ladder), 10000, 1000);

					if (!getMap().canReach(ladder)) {
						log("Couldn't use the object");
						break;
					}
				}
				log("Interacting with object");
				ladders.get(index)
						.interact((finalAreaToSuccess.getRandomPosition().getZ() > myPlayer().getZ()) ? "climb-up"
								: "climb-down");

				Sleep.sleepUntil(() -> finalAreaToSuccess.contains(myPlayer()), 10000, 1000);
			}
		}
	}

	Area area = new Area(
			new int[][] { { 3162, 3310 }, { 3167, 3310 }, { 3167, 3304 }, { 3162, 3304 }, { 3162, 3310 } });

	public static ArrayList<Position> generateStraightPath(Position start, Position end) {
		ArrayList<Position> path = new ArrayList<Position>();
		int currentX = start.getX();
		int currentY = start.getY();
		int endX = end.getX();
		int endY = end.getY();
		int dX = (currentX < endX) ? 1 : -1;
		int dY = (currentY < endY) ? 1 : -1;
		while (currentX != endX || currentY != endY) {
			if (currentX != endX) {
				currentX += dX;
			}
			if (currentY != endY) {
				currentY += dY;
			}
			path.add(new Position(currentX, currentY, 0));
		}
		return path;
	}

	private Position tile;

	@Override
	public int onLoop() throws InterruptedException {

		if (!getClient().isLoggedIn()) {
			stop(false);
			return random(1);
		}

		// int radius = 13;
		// Position endPos = new Position(3154, 3435, 1);
		// ArrayList<Position> positions =
		// generateStraightPath(myPlayer().getPosition(), endPos);
		// for (Position pos : positions) {
		// boolean isAtFinalArea = endPos.getArea(radius).contains(myPlayer().getX(),
		// myPlayer().getY());
		// if (pos.distance(myPlayer()) < radius && !isAtFinalArea) {
		// continue;
		// }
		//
		// if (!getMap().canReach(pos)) {
		// List<Position> pos2 = getDoorHandler().generatePath(pos);
		//
		// if (pos2 != null) {
		// for (Position p : pos2) {
		// getDoorHandler().handleNextObstacle(p.getArea(1));
		// Sleep.sleepUntil(() -> getMap().canReach(pos), 1000);
		// }
		// }
		// }
		//
		// if (isAtFinalArea && endPos.getZ() != myPlayer().getZ()) {
		// log("Have to walk up!");
		// handleClostestObject(new ArrayList<LadderAndDoor>(Arrays.asList(
		// new LadderAndDoor("staircase", (endPos.getZ() > myPlayer().getZ()) ?
		// "climb-up" : "climb-down",
		// false, myPlayer().getArea(radius), endPos.getArea(radius)))));
		// }
		//
		// tile = pos;
		// getWalking().walk(pos);
		// }

//		handleClostestObject(new ArrayList<LadderAndDoor>(Arrays.asList(new LadderAndDoor(false,
//				new Area(new int[][] { { 3156, 3437 }, { 3161, 3437 }, { 3161, 3440 }, { 3164, 3440 }, { 3165, 3439 },
//						{ 3165, 3432 }, { 3156, 3432 }, { 3156, 3437 } }),
//				new Area(new int[][] { { 3151, 3440 }, { 3159, 3440 }, { 3159, 3430 }, { 3151, 3430 }, { 3151, 3440 } })
//						.setPlane(1)))));

		// handleClostestObject("ladder", "climb-up",
		// ,
		// ,
		// false);
		//
		// handleClostestObject("ladder", "climb-down",
		// new Area(
		// new int[][] { { 3162, 3310 }, { 3167, 3310 }, { 3167, 3304 }, { 3162, 3304 },
		// { 3162, 3310 } }),
		// new Area(new int[][] { { 3162, 3310 }, { 3166, 3310 }, { 3166, 3304 }, {
		// 3162, 3304 }, { 3162, 3310 } })
		// .setPlane(1),
		// true);
		//
		stop(false);

		return random(500, 1000);
	}

	@Override
	public void onPaint(Graphics2D g) {
		// for (Position pos : aroundPlayerPositions) {
		// g.draw(pos.getPolygon(getBot()));
		// }
		if (tile != null) {
			g.draw(tile.getPolygon(getBot()));
		}
	}

	@Override
	public void onStart() throws InterruptedException {

	}

}
