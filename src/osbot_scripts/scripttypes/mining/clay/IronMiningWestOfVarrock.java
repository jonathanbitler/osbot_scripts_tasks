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
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3183, 3435, 0), new Position(3183, 3435, 0),
				new Position(3183, 3434, 0), new Position(3183, 3433, 0), new Position(3182, 3432, 0),
				new Position(3181, 3432, 0), new Position(3180, 3431, 0), new Position(3179, 3430, 0),
				new Position(3178, 3430, 0), new Position(3177, 3430, 0), new Position(3176, 3429, 0),
				new Position(3175, 3428, 0), new Position(3174, 3427, 0), new Position(3173, 3426, 0),
				new Position(3172, 3425, 0), new Position(3172, 3424, 0), new Position(3172, 3423, 0),
				new Position(3172, 3422, 0), new Position(3172, 3421, 0), new Position(3172, 3420, 0),
				new Position(3172, 3419, 0), new Position(3172, 3418, 0), new Position(3172, 3417, 0),
				new Position(3172, 3416, 0), new Position(3172, 3415, 0), new Position(3172, 3414, 0),
				new Position(3172, 3413, 0), new Position(3172, 3412, 0), new Position(3172, 3411, 0),
				new Position(3172, 3410, 0), new Position(3172, 3409, 0), new Position(3172, 3408, 0),
				new Position(3172, 3407, 0), new Position(3172, 3406, 0), new Position(3172, 3405, 0),
				new Position(3172, 3404, 0), new Position(3172, 3403, 0), new Position(3172, 3402, 0),
				new Position(3172, 3401, 0), new Position(3172, 3400, 0), new Position(3172, 3399, 0),
				new Position(3172, 3398, 0), new Position(3172, 3397, 0), new Position(3172, 3396, 0),
				new Position(3172, 3395, 0), new Position(3172, 3394, 0), new Position(3172, 3393, 0),
				new Position(3172, 3392, 0), new Position(3172, 3391, 0), new Position(3172, 3390, 0),
				new Position(3173, 3389, 0), new Position(3174, 3388, 0), new Position(3175, 3387, 0),
				new Position(3176, 3386, 0), new Position(3177, 3385, 0), new Position(3178, 3384, 0),
				new Position(3179, 3383, 0), new Position(3180, 3382, 0), new Position(3181, 3381, 0),
				new Position(3182, 3380, 0), new Position(3182, 3379, 0), new Position(3182, 3378, 0),
				new Position(3182, 3377, 0), new Position(3182, 3376, 0), new Position(3182, 3375, 0),
				new Position(3183, 3374, 0), new Position(3183, 3373, 0), new Position(3183, 3372, 0),
				new Position(3182, 3372, 0), new Position(3181, 3371, 0), new Position(3180, 3371, 0),
				new Position(3180, 3370, 0), new Position(3180, 3369, 0)));
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
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3180, 3368, 0), new Position(3180, 3368, 0),
				new Position(3180, 3369, 0), new Position(3180, 3370, 0), new Position(3180, 3371, 0),
				new Position(3181, 3371, 0), new Position(3182, 3371, 0), new Position(3183, 3372, 0),
				new Position(3183, 3373, 0), new Position(3183, 3374, 0), new Position(3182, 3375, 0),
				new Position(3182, 3376, 0), new Position(3182, 3377, 0), new Position(3182, 3378, 0),
				new Position(3182, 3379, 0), new Position(3182, 3380, 0), new Position(3181, 3381, 0),
				new Position(3181, 3382, 0), new Position(3181, 3383, 0), new Position(3181, 3384, 0),
				new Position(3181, 3385, 0), new Position(3181, 3386, 0), new Position(3181, 3387, 0),
				new Position(3181, 3388, 0), new Position(3181, 3389, 0), new Position(3181, 3390, 0),
				new Position(3180, 3391, 0), new Position(3179, 3392, 0), new Position(3178, 3393, 0),
				new Position(3177, 3394, 0), new Position(3176, 3395, 0), new Position(3175, 3396, 0),
				new Position(3174, 3397, 0), new Position(3173, 3398, 0), new Position(3172, 3399, 0),
				new Position(3172, 3400, 0), new Position(3172, 3401, 0), new Position(3172, 3402, 0),
				new Position(3172, 3403, 0), new Position(3172, 3404, 0), new Position(3172, 3405, 0),
				new Position(3172, 3406, 0), new Position(3172, 3407, 0), new Position(3172, 3408, 0),
				new Position(3172, 3409, 0), new Position(3172, 3410, 0), new Position(3172, 3411, 0),
				new Position(3172, 3412, 0), new Position(3172, 3413, 0), new Position(3172, 3414, 0),
				new Position(3172, 3415, 0), new Position(3172, 3416, 0), new Position(3172, 3417, 0),
				new Position(3172, 3418, 0), new Position(3172, 3419, 0), new Position(3172, 3420, 0),
				new Position(3172, 3421, 0), new Position(3172, 3422, 0), new Position(3172, 3423, 0),
				new Position(3172, 3424, 0), new Position(3172, 3425, 0), new Position(3173, 3425, 0),
				new Position(3174, 3425, 0), new Position(3175, 3425, 0), new Position(3176, 3426, 0),
				new Position(3177, 3427, 0), new Position(3178, 3428, 0), new Position(3179, 3429, 0),
				new Position(3180, 3430, 0), new Position(3181, 3431, 0), new Position(3182, 3432, 0),
				new Position(3182, 3433, 0), new Position(3182, 3434, 0), new Position(3182, 3435, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3485, 0), new Position(3164, 3485, 0), new Position(3164, 3484, 0),
						new Position(3164, 3483, 0), new Position(3164, 3482, 0), new Position(3164, 3481, 0),
						new Position(3164, 3480, 0), new Position(3164, 3479, 0), new Position(3164, 3478, 0),
						new Position(3164, 3477, 0), new Position(3164, 3476, 0), new Position(3164, 3475, 0),
						new Position(3164, 3474, 0), new Position(3164, 3473, 0), new Position(3164, 3472, 0),
						new Position(3164, 3471, 0), new Position(3164, 3470, 0), new Position(3164, 3469, 0),
						new Position(3164, 3468, 0), new Position(3164, 3467, 0), new Position(3164, 3466, 0),
						new Position(3164, 3465, 0), new Position(3164, 3464, 0), new Position(3165, 3463, 0),
						new Position(3166, 3462, 0), new Position(3167, 3462, 0), new Position(3168, 3461, 0),
						new Position(3169, 3460, 0), new Position(3170, 3459, 0), new Position(3171, 3458, 0),
						new Position(3172, 3457, 0), new Position(3173, 3456, 0), new Position(3174, 3455, 0),
						new Position(3175, 3454, 0), new Position(3176, 3453, 0), new Position(3177, 3452, 0),
						new Position(3178, 3451, 0), new Position(3179, 3451, 0), new Position(3180, 3450, 0),
						new Position(3181, 3449, 0), new Position(3182, 3448, 0), new Position(3182, 3447, 0),
						new Position(3182, 3446, 0), new Position(3182, 3445, 0), new Position(3182, 3444, 0),
						new Position(3182, 3443, 0), new Position(3182, 3442, 0), new Position(3182, 3441, 0),
						new Position(3182, 3440, 0), new Position(3182, 3439, 0), new Position(3182, 3438, 0),
						new Position(3182, 3437, 0), new Position(3182, 3436, 0), new Position(3183, 3435, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToMiningPosition() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3485, 0), new Position(3164, 3485, 0),
				new Position(3164, 3484, 0), new Position(3164, 3483, 0), new Position(3164, 3482, 0),
				new Position(3164, 3481, 0), new Position(3164, 3480, 0), new Position(3164, 3479, 0),
				new Position(3164, 3478, 0), new Position(3164, 3477, 0), new Position(3164, 3476, 0),
				new Position(3164, 3475, 0), new Position(3164, 3474, 0), new Position(3164, 3473, 0),
				new Position(3164, 3472, 0), new Position(3164, 3471, 0), new Position(3164, 3470, 0),
				new Position(3164, 3469, 0), new Position(3164, 3468, 0), new Position(3164, 3467, 0),
				new Position(3164, 3466, 0), new Position(3164, 3465, 0), new Position(3164, 3464, 0),
				new Position(3164, 3463, 0), new Position(3164, 3462, 0), new Position(3164, 3461, 0),
				new Position(3165, 3460, 0), new Position(3166, 3459, 0), new Position(3166, 3458, 0),
				new Position(3166, 3457, 0), new Position(3167, 3456, 0), new Position(3168, 3455, 0),
				new Position(3169, 3454, 0), new Position(3169, 3453, 0), new Position(3170, 3452, 0),
				new Position(3171, 3451, 0), new Position(3172, 3450, 0), new Position(3173, 3449, 0),
				new Position(3174, 3448, 0), new Position(3175, 3447, 0), new Position(3175, 3446, 0),
				new Position(3175, 3445, 0), new Position(3175, 3444, 0), new Position(3175, 3443, 0),
				new Position(3175, 3442, 0), new Position(3175, 3441, 0), new Position(3175, 3440, 0),
				new Position(3175, 3439, 0), new Position(3175, 3438, 0), new Position(3175, 3437, 0),
				new Position(3175, 3436, 0), new Position(3175, 3435, 0), new Position(3175, 3434, 0),
				new Position(3175, 3433, 0), new Position(3175, 3432, 0), new Position(3175, 3431, 0),
				new Position(3175, 3430, 0), new Position(3175, 3429, 0), new Position(3175, 3428, 0),
				new Position(3174, 3427, 0), new Position(3173, 3426, 0), new Position(3172, 3425, 0),
				new Position(3172, 3424, 0), new Position(3172, 3423, 0), new Position(3172, 3422, 0),
				new Position(3172, 3421, 0), new Position(3172, 3420, 0), new Position(3172, 3419, 0),
				new Position(3172, 3418, 0), new Position(3172, 3417, 0), new Position(3172, 3416, 0),
				new Position(3172, 3415, 0), new Position(3172, 3414, 0), new Position(3172, 3413, 0),
				new Position(3172, 3412, 0), new Position(3172, 3411, 0), new Position(3172, 3410, 0),
				new Position(3172, 3409, 0), new Position(3172, 3408, 0), new Position(3172, 3407, 0),
				new Position(3172, 3406, 0), new Position(3172, 3405, 0), new Position(3172, 3404, 0),
				new Position(3172, 3403, 0), new Position(3172, 3402, 0), new Position(3172, 3401, 0),
				new Position(3172, 3400, 0), new Position(3172, 3399, 0), new Position(3172, 3398, 0),
				new Position(3172, 3397, 0), new Position(3172, 3396, 0), new Position(3172, 3395, 0),
				new Position(3172, 3394, 0), new Position(3172, 3393, 0), new Position(3172, 3392, 0),
				new Position(3172, 3391, 0), new Position(3172, 3390, 0), new Position(3173, 3389, 0),
				new Position(3174, 3388, 0), new Position(3175, 3387, 0), new Position(3176, 3386, 0),
				new Position(3177, 3385, 0), new Position(3178, 3384, 0), new Position(3179, 3383, 0),
				new Position(3180, 3382, 0), new Position(3181, 3381, 0), new Position(3182, 3380, 0),
				new Position(3182, 3379, 0), new Position(3182, 3378, 0), new Position(3182, 3377, 0),
				new Position(3182, 3376, 0), new Position(3182, 3375, 0), new Position(3183, 3374, 0),
				new Position(3183, 3373, 0), new Position(3183, 3372, 0), new Position(3182, 3372, 0),
				new Position(3181, 3371, 0), new Position(3180, 3371, 0), new Position(3180, 3370, 0),
				new Position(3180, 3369, 0), new Position(3180, 3368, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area exactMiningPositionArea() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3174, 3370 }, { 3180, 3370 }, { 3180, 3365 }, { 3174, 3365 } });
	}

}
