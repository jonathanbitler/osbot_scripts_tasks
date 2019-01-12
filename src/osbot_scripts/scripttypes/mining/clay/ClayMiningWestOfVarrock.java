package osbot_scripts.scripttypes.mining.clay;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;

public class ClayMiningWestOfVarrock implements MiningLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3180, 3443 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3443 } });
	}

	@Override
	public ArrayList<Position> gePositionsFromBankToMiningSpot() {
		ArrayList<Position>[] pos = new ArrayList[6];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3436, 0), new Position(3181, 3430, 0), new Position(3172, 3426, 0),
						new Position(3169, 3425, 0), new Position(3170, 3415, 0), new Position(3170, 3409, 0),
						new Position(3170, 3399, 0), new Position(3170, 3395, 0), new Position(3175, 3387, 0),
						new Position(3177, 3384, 0), new Position(3181, 3375, 0), new Position(3182, 3371, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3182, 3436, 0), new Position(3184, 3429, 0), new Position(3174, 3429, 0),
						new Position(3170, 3429, 0), new Position(3170, 3420, 0), new Position(3169, 3410, 0),
						new Position(3168, 3400, 0), new Position(3167, 3399, 0), new Position(3171, 3390, 0),
						new Position(3174, 3383, 0), new Position(3179, 3374, 0), new Position(3181, 3370, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3435, 0), new Position(3175, 3429, 0), new Position(3172, 3426, 0),
						new Position(3166, 3418, 0), new Position(3164, 3414, 0), new Position(3169, 3405, 0),
						new Position(3171, 3401, 0), new Position(3174, 3392, 0), new Position(3175, 3389, 0),
						new Position(3177, 3379, 0), new Position(3179, 3369, 0), new Position(3180, 3369, 0)));
		pos[3] = new ArrayList<Position>(
				Arrays.asList(new Position(3183, 3435, 0), new Position(3185, 3430, 0), new Position(3195, 3429, 0),
						new Position(3205, 3428, 0), new Position(3207, 3428, 0), new Position(3209, 3418, 0),
						new Position(3210, 3416, 0), new Position(3210, 3406, 0), new Position(3210, 3396, 0),
						new Position(3210, 3390, 0), new Position(3207, 3380, 0), new Position(3205, 3374, 0),
						new Position(3195, 3373, 0), new Position(3185, 3372, 0), new Position(3182, 3371, 0)));
		pos[4] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3426, 0),
				new Position(3183, 3416, 0), new Position(3183, 3406, 0), new Position(3182, 3404, 0),
				new Position(3192, 3401, 0), new Position(3200, 3399, 0), new Position(3207, 3407, 0),
				new Position(3207, 3407, 0), new Position(3211, 3403, 0), new Position(3210, 3393, 0),
				new Position(3209, 3383, 0), new Position(3209, 3383, 0), new Position(3200, 3379, 0),
				new Position(3191, 3375, 0), new Position(3182, 3371, 0), new Position(3181, 3371, 0)));
		pos[5] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3182, 3428, 0),
				new Position(3192, 3428, 0), new Position(3195, 3428, 0), new Position(3199, 3419, 0),
				new Position(3201, 3413, 0), new Position(3208, 3406, 0), new Position(3211, 3404, 0),
				new Position(3210, 3394, 0), new Position(3210, 3386, 0), new Position(3203, 3379, 0),
				new Position(3196, 3373, 0), new Position(3186, 3372, 0), new Position(3181, 3371, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getExactMiningSpotPosition() {
		return new ArrayList<Position>(Arrays.asList(new Position(3180, 3370, 0)));
	}

	@Override
	public Area getAreaOfMiningLocation() {
		return new Area(new int[][] { { 3179, 3379 }, { 3170, 3366 }, { 3175, 3363 }, { 3180, 3365 }, { 3184, 3373 },
				{ 3186, 3380 }, { 3182, 3381 } });
	}

	@Override
	public Area getAreaOfOperating() {
		return new Area(new int[][] { { 2942, 3519 }, { 3332, 3519 }, { 3335, 3328 }, { 3250, 3175 }, { 2934, 3294 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromMiningSpotToBank() {
		ArrayList<Position>[] pos = new ArrayList[7];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3370, 0), new Position(3176, 3377, 0),
				new Position(3169, 3384, 0), new Position(3168, 3387, 0), new Position(3168, 3397, 0),
				new Position(3168, 3407, 0), new Position(3169, 3413, 0), new Position(3172, 3422, 0),
				new Position(3175, 3430, 0), new Position(3183, 3435, 0), new Position(3183, 3436, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3370, 0), new Position(3180, 3380, 0),
				new Position(3178, 3387, 0), new Position(3176, 3395, 0), new Position(3169, 3400, 0),
				new Position(3169, 3410, 0), new Position(3169, 3420, 0), new Position(3168, 3420, 0),
				new Position(3174, 3427, 0), new Position(3182, 3433, 0), new Position(3184, 3435, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3184, 3369, 0), new Position(3174, 3371, 0),
				new Position(3165, 3373, 0), new Position(3169, 3382, 0), new Position(3172, 3390, 0),
				new Position(3166, 3398, 0), new Position(3163, 3401, 0), new Position(3165, 3411, 0),
				new Position(3167, 3421, 0), new Position(3167, 3421, 0), new Position(3174, 3428, 0),
				new Position(3176, 3429, 0), new Position(3183, 3434, 0), new Position(3183, 3436, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3182, 3370, 0), new Position(3180, 3380, 0),
				new Position(3179, 3386, 0), new Position(3179, 3395, 0), new Position(3169, 3396, 0),
				new Position(3169, 3396, 0), new Position(3169, 3405, 0), new Position(3168, 3415, 0),
				new Position(3168, 3420, 0), new Position(3173, 3427, 0), new Position(3182, 3428, 0),
				new Position(3183, 3435, 0), new Position(3183, 3436, 0)));
		pos[4] = new ArrayList<Position>(Arrays.asList(new Position(3180, 3369, 0), new Position(3189, 3373, 0),
				new Position(3196, 3376, 0), new Position(3206, 3378, 0), new Position(3208, 3378, 0),
				new Position(3210, 3388, 0), new Position(3210, 3388, 0), new Position(3211, 3398, 0),
				new Position(3211, 3402, 0), new Position(3201, 3405, 0), new Position(3192, 3408, 0),
				new Position(3191, 3418, 0), new Position(3191, 3418, 0), new Position(3181, 3421, 0),
				new Position(3180, 3421, 0), new Position(3181, 3431, 0), new Position(3182, 3435, 0)));
		pos[5] = new ArrayList<Position>(Arrays.asList(new Position(3181, 3370, 0), new Position(3191, 3373, 0),
				new Position(3200, 3375, 0), new Position(3208, 3379, 0), new Position(3210, 3389, 0),
				new Position(3211, 3392, 0), new Position(3210, 3402, 0), new Position(3210, 3406, 0),
				new Position(3211, 3416, 0), new Position(3211, 3421, 0), new Position(3204, 3428, 0),
				new Position(3203, 3429, 0), new Position(3193, 3429, 0), new Position(3183, 3429, 0),
				new Position(3181, 3429, 0), new Position(3183, 3434, 0)));
		pos[6] = new ArrayList<Position>(Arrays.asList(new Position(3181, 3370, 0), new Position(3191, 3373, 0),
				new Position(3191, 3373, 0), new Position(3201, 3374, 0), new Position(3209, 3375, 0),
				new Position(3211, 3385, 0), new Position(3211, 3385, 0), new Position(3211, 3395, 0),
				new Position(3211, 3399, 0), new Position(3206, 3407, 0), new Position(3199, 3399, 0),
				new Position(3199, 3399, 0), new Position(3189, 3401, 0), new Position(3187, 3401, 0),
				new Position(3183, 3410, 0), new Position(3180, 3415, 0), new Position(3181, 3425, 0),
				new Position(3181, 3427, 0), new Position(3183, 3436, 0)));
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
	public ArrayList<Position> getPositionsFromGeToMiningPosition() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3483, 0), new Position(3164, 3473, 0),
				new Position(3163, 3463, 0), new Position(3162, 3461, 0), new Position(3156, 3453, 0),
				new Position(3150, 3445, 0), new Position(3147, 3439, 0), new Position(3148, 3429, 0),
				new Position(3148, 3420, 0), new Position(3154, 3412, 0), new Position(3160, 3404, 0),
				new Position(3162, 3399, 0), new Position(3169, 3392, 0), new Position(3173, 3387, 0),
				new Position(3178, 3379, 0), new Position(3183, 3371, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3484, 0), new Position(3164, 3474, 0),
				new Position(3164, 3472, 0), new Position(3165, 3462, 0), new Position(3165, 3458, 0),
				new Position(3171, 3450, 0), new Position(3173, 3446, 0), new Position(3174, 3436, 0),
				new Position(3175, 3430, 0), new Position(3171, 3421, 0), new Position(3167, 3414, 0),
				new Position(3168, 3404, 0), new Position(3169, 3395, 0), new Position(3175, 3387, 0),
				new Position(3179, 3378, 0), new Position(3182, 3370, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3483, 0), new Position(3163, 3473, 0),
				new Position(3163, 3473, 0), new Position(3165, 3463, 0), new Position(3166, 3461, 0),
				new Position(3175, 3456, 0), new Position(3180, 3453, 0), new Position(3182, 3443, 0),
				new Position(3183, 3439, 0), new Position(3182, 3429, 0), new Position(3181, 3424, 0),
				new Position(3172, 3420, 0), new Position(3167, 3418, 0), new Position(3168, 3408, 0),
				new Position(3169, 3398, 0), new Position(3169, 3398, 0), new Position(3172, 3388, 0),
				new Position(3174, 3379, 0), new Position(3178, 3370, 0), new Position(3179, 3367, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3166, 3481, 0), new Position(3163, 3472, 0),
				new Position(3164, 3462, 0), new Position(3165, 3457, 0), new Position(3173, 3450, 0),
				new Position(3173, 3450, 0), new Position(3172, 3440, 0), new Position(3172, 3438, 0),
				new Position(3169, 3428, 0), new Position(3168, 3423, 0), new Position(3168, 3413, 0),
				new Position(3168, 3403, 0), new Position(3169, 3398, 0), new Position(3173, 3389, 0),
				new Position(3177, 3381, 0), new Position(3179, 3371, 0), new Position(3180, 3369, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area exactMiningPositionArea() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3184, 3374 }, { 3179, 3374 }, { 3179, 3369 }, { 3184, 3369 } });
	}

}
