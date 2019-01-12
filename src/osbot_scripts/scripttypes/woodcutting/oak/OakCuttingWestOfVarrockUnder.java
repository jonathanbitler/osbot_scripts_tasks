package osbot_scripts.scripttypes.woodcutting.oak;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class OakCuttingWestOfVarrockUnder implements WoodcuttingLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3180, 3443 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3443 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3181, 3430, 0),
				new Position(3171, 3427, 0), new Position(3170, 3427, 0), new Position(3166, 3418, 0),
				new Position(3165, 3417, 0), new Position(3168, 3408, 0), new Position(3169, 3405, 0),
				new Position(3171, 3395, 0), new Position(3171, 3393, 0), new Position(3166, 3386, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3181, 3435, 0), new Position(3182, 3430, 0),
				new Position(3172, 3427, 0), new Position(3172, 3427, 0), new Position(3164, 3427, 0),
				new Position(3165, 3417, 0), new Position(3165, 3416, 0), new Position(3171, 3410, 0),
				new Position(3165, 3403, 0), new Position(3162, 3393, 0), new Position(3162, 3392, 0),
				new Position(3167, 3389, 0), new Position(3166, 3387, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3435, 0), new Position(3175, 3430, 0), new Position(3167, 3425, 0),
						new Position(3159, 3420, 0), new Position(3158, 3410, 0), new Position(3158, 3401, 0),
						new Position(3161, 3392, 0), new Position(3162, 3389, 0), new Position(3166, 3386, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area getAreaOfWoodcuttingLocation() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3148, 3393 }, { 3161, 3363 }, { 3176, 3375 }, { 3180, 3384 }, { 3181, 3398 },
				{ 3174, 3397 }, { 3167, 3405 }, { 3151, 3400 } });
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
				Arrays.asList(new Position(3165, 3386, 0), new Position(3169, 3395, 0), new Position(3171, 3400, 0),
						new Position(3169, 3410, 0), new Position(3167, 3418, 0), new Position(3172, 3426, 0),
						new Position(3174, 3429, 0), new Position(3182, 3431, 0), new Position(3183, 3436, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3169, 3386, 0), new Position(3170, 3396, 0),
				new Position(3170, 3398, 0), new Position(3165, 3407, 0), new Position(3163, 3410, 0),
				new Position(3165, 3420, 0), new Position(3166, 3422, 0), new Position(3173, 3429, 0),
				new Position(3183, 3429, 0), new Position(3185, 3429, 0), new Position(3182, 3435, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3169, 3386, 0), new Position(3171, 3396, 0),
				new Position(3172, 3403, 0), new Position(3169, 3410, 0), new Position(3171, 3418, 0),
				new Position(3173, 3427, 0), new Position(3182, 3428, 0), new Position(3182, 3436, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3170, 3386, 0), new Position(3165, 3395, 0),
				new Position(3160, 3404, 0), new Position(3160, 3405, 0), new Position(3159, 3415, 0),
				new Position(3158, 3421, 0), new Position(3166, 3426, 0), new Position(3169, 3428, 0),
				new Position(3179, 3428, 0), new Position(3184, 3428, 0), new Position(3182, 3435, 0)));
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
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3484, 0), new Position(3164, 3474, 0),
				new Position(3164, 3470, 0), new Position(3163, 3460, 0), new Position(3162, 3454, 0),
				new Position(3154, 3448, 0), new Position(3151, 3445, 0), new Position(3148, 3435, 0),
				new Position(3146, 3429, 0), new Position(3151, 3420, 0), new Position(3152, 3419, 0),
				new Position(3158, 3411, 0), new Position(3160, 3408, 0), new Position(3162, 3398, 0),
				new Position(3164, 3388, 0), new Position(3165, 3385, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3165, 3483, 0), new Position(3163, 3473, 0), new Position(3162, 3469, 0),
						new Position(3166, 3460, 0), new Position(3167, 3456, 0), new Position(3173, 3448, 0),
						new Position(3176, 3443, 0), new Position(3173, 3433, 0), new Position(3172, 3429, 0),
						new Position(3169, 3419, 0), new Position(3169, 3418, 0), new Position(3166, 3408, 0),
						new Position(3165, 3405, 0), new Position(3165, 3395, 0), new Position(3165, 3387, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3163, 3483, 0), new Position(3170, 3476, 0),
				new Position(3173, 3472, 0), new Position(3164, 3468, 0), new Position(3166, 3458, 0),
				new Position(3166, 3456, 0), new Position(3175, 3451, 0), new Position(3176, 3450, 0),
				new Position(3176, 3440, 0), new Position(3176, 3434, 0), new Position(3172, 3425, 0),
				new Position(3170, 3420, 0), new Position(3169, 3410, 0), new Position(3168, 3405, 0),
				new Position(3169, 3395, 0), new Position(3169, 3386, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

}
