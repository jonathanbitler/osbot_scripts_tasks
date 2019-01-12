package osbot_scripts.scripttypes.mining.clay;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;

public class IronMiningWestOfVarrock implements MiningLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3180, 3443 }, { 3180, 3433 }, { 3186, 3433 }, { 3186, 3443 } });
	}

	@Override
	public ArrayList<Position> gePositionsFromBankToMiningSpot() {
		ArrayList<Position>[] pos = new ArrayList[6];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3192, 3432, 0),
				new Position(3196, 3430, 0), new Position(3199, 3421, 0), new Position(3200, 3418, 0),
				new Position(3200, 3408, 0), new Position(3200, 3406, 0), new Position(3210, 3404, 0),
				new Position(3211, 3404, 0), new Position(3210, 3394, 0), new Position(3209, 3390, 0),
				new Position(3209, 3380, 0), new Position(3209, 3378, 0), new Position(3200, 3375, 0),
				new Position(3191, 3372, 0), new Position(3182, 3369, 0), new Position(3177, 3367, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3182, 3426, 0),
				new Position(3182, 3425, 0), new Position(3181, 3415, 0), new Position(3181, 3410, 0),
				new Position(3188, 3403, 0), new Position(3190, 3400, 0), new Position(3200, 3400, 0),
				new Position(3203, 3407, 0), new Position(3209, 3406, 0), new Position(3209, 3396, 0),
				new Position(3209, 3392, 0), new Position(3205, 3383, 0), new Position(3201, 3374, 0),
				new Position(3191, 3371, 0), new Position(3181, 3368, 0), new Position(3179, 3367, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3182, 3435, 0), new Position(3174, 3429, 0),
				new Position(3168, 3421, 0), new Position(3168, 3420, 0), new Position(3164, 3411, 0),
				new Position(3160, 3402, 0), new Position(3159, 3402, 0), new Position(3159, 3392, 0),
				new Position(3159, 3385, 0), new Position(3163, 3376, 0), new Position(3167, 3367, 0),
				new Position(3177, 3368, 0), new Position(3180, 3368, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3183, 3430, 0),
				new Position(3174, 3426, 0), new Position(3171, 3424, 0), new Position(3170, 3414, 0),
				new Position(3169, 3407, 0), new Position(3169, 3397, 0), new Position(3169, 3387, 0),
				new Position(3169, 3384, 0), new Position(3175, 3379, 0), new Position(3178, 3369, 0),
				new Position(3179, 3366, 0), new Position(3178, 3367, 0)));
		pos[4] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3436, 0), new Position(3182, 3429, 0),
				new Position(3192, 3428, 0), new Position(3202, 3427, 0), new Position(3205, 3427, 0),
				new Position(3212, 3419, 0), new Position(3212, 3419, 0), new Position(3211, 3409, 0),
				new Position(3211, 3407, 0), new Position(3211, 3397, 0), new Position(3211, 3393, 0),
				new Position(3210, 3383, 0), new Position(3209, 3376, 0), new Position(3199, 3374, 0),
				new Position(3196, 3373, 0), new Position(3187, 3370, 0), new Position(3178, 3367, 0)));
		pos[5] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3435, 0), new Position(3180, 3430, 0),
				new Position(3171, 3426, 0), new Position(3170, 3425, 0), new Position(3167, 3415, 0),
				new Position(3164, 3405, 0), new Position(3164, 3404, 0), new Position(3168, 3395, 0),
				new Position(3170, 3390, 0), new Position(3177, 3389, 0), new Position(3178, 3379, 0),
				new Position(3178, 3377, 0), new Position(3179, 3367, 0), new Position(3179, 3367, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getExactMiningSpotPosition() {
		return new ArrayList<Position>(Arrays.asList(new Position(3175, 3367, 0)));
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
		ArrayList<Position>[] pos = new ArrayList[5];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3180, 3366, 0), new Position(3189, 3370, 0),
				new Position(3198, 3374, 0), new Position(3199, 3375, 0), new Position(3207, 3381, 0),
				new Position(3211, 3383, 0), new Position(3211, 3393, 0), new Position(3211, 3400, 0),
				new Position(3206, 3409, 0), new Position(3205, 3410, 0), new Position(3200, 3419, 0),
				new Position(3197, 3423, 0), new Position(3192, 3430, 0), new Position(3182, 3431, 0),
				new Position(3181, 3431, 0), new Position(3183, 3434, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3180, 3368, 0), new Position(3176, 3377, 0),
				new Position(3173, 3382, 0), new Position(3177, 3391, 0), new Position(3171, 3399, 0),
				new Position(3166, 3407, 0), new Position(3169, 3416, 0), new Position(3172, 3425, 0),
				new Position(3177, 3430, 0), new Position(3182, 3430, 0), new Position(3183, 3435, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3181, 3367, 0), new Position(3190, 3370, 0),
				new Position(3199, 3373, 0), new Position(3208, 3376, 0), new Position(3210, 3386, 0),
				new Position(3211, 3389, 0), new Position(3212, 3399, 0), new Position(3212, 3408, 0),
				new Position(3203, 3412, 0), new Position(3202, 3412, 0), new Position(3197, 3421, 0),
				new Position(3195, 3424, 0), new Position(3194, 3430, 0), new Position(3184, 3432, 0),
				new Position(3184, 3432, 0), new Position(3182, 3435, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3181, 3367, 0), new Position(3177, 3376, 0),
				new Position(3175, 3382, 0), new Position(3172, 3392, 0), new Position(3171, 3397, 0),
				new Position(3166, 3406, 0), new Position(3163, 3410, 0), new Position(3166, 3420, 0),
				new Position(3166, 3421, 0), new Position(3174, 3427, 0), new Position(3175, 3428, 0),
				new Position(3182, 3435, 0), new Position(3183, 3436, 0)));
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
		return new Area(new int[][] { { 3174, 3370 }, { 3180, 3370 }, { 3180, 3365 }, { 3174, 3365 } });
	}

}
