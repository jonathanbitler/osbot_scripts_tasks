package osbot_scripts.scripttypes.woodcutting.logs;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class LogsCuttingWestOfVarrock implements WoodcuttingLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3180, 3443 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3443 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3435, 0), new Position(3181, 3428, 0), new Position(3171, 3426, 0),
						new Position(3170, 3426, 0), new Position(3164, 3419, 0), new Position(3163, 3412, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3182, 3430, 0),
				new Position(3173, 3426, 0), new Position(3169, 3425, 0), new Position(3162, 3418, 0),
				new Position(3162, 3408, 0), new Position(3162, 3407, 0), new Position(3163, 3400, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3436, 0), new Position(3184, 3429, 0), new Position(3174, 3428, 0),
						new Position(3170, 3427, 0), new Position(3167, 3417, 0), new Position(3165, 3411, 0),
						new Position(3163, 3404, 0), new Position(3164, 3396, 0), new Position(3164, 3389, 0)));
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
		return new Area(new int[][] { { 3137, 3519 }, { 3137, 3464 }, { 3133, 3405 }, { 3161, 3361 }, { 3179, 3383 },
				{ 3180, 3397 }, { 3175, 3399 }, { 3179, 3420 }, { 3185, 3430 }, { 3196, 3433 }, { 3194, 3450 },
				{ 3183, 3459 }, { 3186, 3477 }, { 3190, 3510 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromWoodcuttingSpotToBank() {
		// TODO Auto-generated method stub
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3380, 0), new Position(3164, 3390, 0),
				new Position(3164, 3396, 0), new Position(3163, 3406, 0), new Position(3163, 3406, 0),
				new Position(3166, 3415, 0), new Position(3168, 3424, 0), new Position(3177, 3427, 0),
				new Position(3179, 3428, 0), new Position(3184, 3435, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3170, 3380, 0), new Position(3166, 3389, 0), new Position(3165, 3391, 0),
						new Position(3163, 3401, 0), new Position(3162, 3404, 0), new Position(3166, 3413, 0),
						new Position(3166, 3414, 0), new Position(3168, 3417, 0), new Position(3171, 3426, 0),
						new Position(3172, 3429, 0), new Position(3182, 3429, 0), new Position(3182, 3434, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3162, 3381, 0), new Position(3161, 3391, 0),
				new Position(3161, 3394, 0), new Position(3163, 3404, 0), new Position(3163, 3407, 0),
				new Position(3167, 3416, 0), new Position(3168, 3418, 0), new Position(3172, 3427, 0),
				new Position(3173, 3430, 0), new Position(3181, 3429, 0), new Position(3183, 3435, 0)));
		pos[3] = new ArrayList<Position>(
				Arrays.asList(new Position(3169, 3385, 0), new Position(3163, 3393, 0), new Position(3162, 3394, 0),
						new Position(3166, 3403, 0), new Position(3167, 3407, 0), new Position(3167, 3417, 0),
						new Position(3170, 3426, 0), new Position(3177, 3428, 0), new Position(3184, 3435, 0)));
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
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3160, 3487, 0), new Position(3165, 3480, 0),
				new Position(3164, 3470, 0), new Position(3164, 3465, 0), new Position(3166, 3455, 0),
				new Position(3167, 3452, 0), new Position(3175, 3446, 0), new Position(3176, 3445, 0),
				new Position(3173, 3435, 0), new Position(3172, 3431, 0), new Position(3170, 3421, 0),
				new Position(3169, 3414, 0), new Position(3164, 3407, 0), new Position(3164, 3397, 0),
				new Position(3164, 3389, 0), new Position(3164, 3380, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3167, 3486, 0), new Position(3162, 3484, 0), new Position(3165, 3476, 0),
						new Position(3164, 3467, 0), new Position(3163, 3457, 0), new Position(3163, 3457, 0),
						new Position(3156, 3450, 0), new Position(3153, 3448, 0), new Position(3146, 3441, 0),
						new Position(3141, 3436, 0), new Position(3147, 3428, 0), new Position(3153, 3420, 0),
						new Position(3154, 3419, 0), new Position(3158, 3410, 0), new Position(3162, 3401, 0),
						new Position(3163, 3400, 0), new Position(3164, 3390, 0), new Position(3165, 3381, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3486, 0), new Position(3161, 3478, 0), new Position(3164, 3468, 0),
						new Position(3167, 3458, 0), new Position(3167, 3458, 0), new Position(3171, 3449, 0),
						new Position(3173, 3446, 0), new Position(3182, 3450, 0), new Position(3181, 3441, 0),
						new Position(3182, 3431, 0), new Position(3182, 3430, 0), new Position(3173, 3426, 0),
						new Position(3169, 3424, 0), new Position(3168, 3414, 0), new Position(3167, 3410, 0),
						new Position(3162, 3402, 0), new Position(3163, 3392, 0), new Position(3164, 3387, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

}
