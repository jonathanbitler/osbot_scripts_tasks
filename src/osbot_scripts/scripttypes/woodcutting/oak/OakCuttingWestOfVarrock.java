package osbot_scripts.scripttypes.woodcutting.oak;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class OakCuttingWestOfVarrock implements WoodcuttingLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3180, 3443 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3443 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3181, 3426, 0),
				new Position(3181, 3426, 0), new Position(3171, 3426, 0), new Position(3168, 3426, 0),
				new Position(3166, 3416, 0), new Position(3166, 3416, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3182, 3434, 0),
				new Position(3184, 3429, 0), new Position(3176, 3428, 0), new Position(3168, 3427, 0),
				new Position(3169, 3421, 0), new Position(3167, 3418, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3182, 3435, 0), new Position(3182, 3433, 0),
				new Position(3182, 3428, 0), new Position(3175, 3430, 0), new Position(3169, 3422, 0),
				new Position(3166, 3418, 0), new Position(3167, 3423, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area getAreaOfWoodcuttingLocation() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3158, 3422 }, { 3148, 3395 }, { 3163, 3366 }, { 3175, 3381 }, { 3180, 3394 },
				{ 3174, 3396 }, { 3174, 3430 }, { 3154, 3423 }, { 3158, 3422 } });
	}

	@Override
	public Area getAreaOfOperating() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3134, 3516 }, { 3121, 3496 }, { 3110, 3490 }, { 3100, 3468 }, { 3104, 3441 },
				{ 3112, 3431 }, { 3106, 3420 }, { 3114, 3394 }, { 3143, 3386 }, { 3152, 3366 }, { 3162, 3349 },
				{ 3172, 3344 }, { 3181, 3347 }, { 3199, 3372 }, { 3217, 3381 }, { 3305, 3380 }, { 3304, 3383 },
				{ 3296, 3384 }, { 3297, 3455 }, { 3327, 3455 }, { 3331, 3522 }, { 3136, 3522 }, { 3134, 3516 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromWoodcuttingSpotToBank() {
		// TODO Auto-generated method stub
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3166, 3417, 0), new Position(3169, 3423, 0), new Position(3178, 3428, 0),
						new Position(3178, 3428, 0), new Position(3183, 3436, 0), new Position(3183, 3437, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3418, 0), new Position(3161, 3424, 0),
				new Position(3169, 3430, 0), new Position(3177, 3432, 0), new Position(3184, 3436, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3166, 3414, 0), new Position(3171, 3422, 0),
				new Position(3179, 3427, 0), new Position(3185, 3430, 0), new Position(3182, 3435, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3167, 3416, 0), new Position(3167, 3423, 0),
				new Position(3171, 3424, 0), new Position(3176, 3429, 0), new Position(3184, 3428, 0),
				new Position(3182, 3434, 0), new Position(3183, 3436, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3484, 0), new Position(3166, 3474, 0),
				new Position(3166, 3474, 0), new Position(3164, 3464, 0), new Position(3163, 3461, 0),
				new Position(3171, 3455, 0), new Position(3172, 3455, 0), new Position(3181, 3453, 0),
				new Position(3182, 3443, 0), new Position(3182, 3441, 0), new Position(3182, 3435, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3485, 0), new Position(3159, 3476, 0),
				new Position(3158, 3475, 0), new Position(3164, 3470, 0), new Position(3167, 3462, 0),
				new Position(3168, 3454, 0), new Position(3178, 3453, 0), new Position(3183, 3453, 0),
				new Position(3182, 3443, 0), new Position(3182, 3442, 0), new Position(3183, 3436, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3165, 3485, 0), new Position(3169, 3476, 0), new Position(3164, 3468, 0),
						new Position(3164, 3459, 0), new Position(3173, 3454, 0), new Position(3179, 3450, 0),
						new Position(3182, 3441, 0), new Position(3183, 3439, 0), new Position(3183, 3436, 0)));
		pos[3] = new ArrayList<Position>(
				Arrays.asList(new Position(3165, 3485, 0), new Position(3165, 3477, 0), new Position(3161, 3471, 0),
						new Position(3165, 3467, 0), new Position(3166, 3459, 0), new Position(3175, 3455, 0),
						new Position(3181, 3452, 0), new Position(3182, 3442, 0), new Position(3183, 3438, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToWoodcuttingPosition() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3482, 0), new Position(3163, 3472, 0), new Position(3163, 3464, 0),
						new Position(3170, 3457, 0), new Position(3175, 3453, 0), new Position(3175, 3443, 0),
						new Position(3175, 3437, 0), new Position(3172, 3428, 0), new Position(3169, 3420, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3163, 3479, 0), new Position(3163, 3469, 0), new Position(3163, 3463, 0),
						new Position(3158, 3454, 0), new Position(3153, 3445, 0), new Position(3152, 3444, 0),
						new Position(3146, 3436, 0), new Position(3144, 3433, 0), new Position(3148, 3424, 0),
						new Position(3151, 3419, 0), new Position(3161, 3417, 0), new Position(3166, 3416, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3486, 0), new Position(3164, 3476, 0),
				new Position(3164, 3473, 0), new Position(3166, 3463, 0), new Position(3166, 3460, 0),
				new Position(3175, 3456, 0), new Position(3179, 3455, 0), new Position(3176, 3446, 0),
				new Position(3174, 3441, 0), new Position(3173, 3431, 0), new Position(3173, 3430, 0),
				new Position(3168, 3422, 0), new Position(3166, 3419, 0), new Position(3169, 3417, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

}
