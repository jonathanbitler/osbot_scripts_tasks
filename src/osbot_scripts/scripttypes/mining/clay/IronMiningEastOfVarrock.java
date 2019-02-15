package osbot_scripts.scripttypes.mining.clay;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;

public class IronMiningEastOfVarrock implements MiningLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3250, 3424 }, { 3250, 3419 }, { 3258, 3419 }, { 3258, 3424 }, { 3250, 3424 } });
	}

	@Override
	public ArrayList<Position> gePositionsFromBankToMiningSpot() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3251, 3420, 0), new Position(3251, 3420, 0), new Position(3252, 3421, 0),
						new Position(3253, 3422, 0), new Position(3253, 3423, 0), new Position(3253, 3424, 0),
						new Position(3253, 3425, 0), new Position(3254, 3426, 0), new Position(3255, 3426, 0),
						new Position(3256, 3427, 0), new Position(3257, 3428, 0), new Position(3258, 3428, 0),
						new Position(3259, 3428, 0), new Position(3260, 3428, 0), new Position(3261, 3428, 0),
						new Position(3262, 3428, 0), new Position(3263, 3428, 0), new Position(3264, 3428, 0),
						new Position(3265, 3428, 0), new Position(3266, 3428, 0), new Position(3267, 3428, 0),
						new Position(3268, 3428, 0), new Position(3269, 3428, 0), new Position(3270, 3428, 0),
						new Position(3271, 3428, 0), new Position(3272, 3428, 0), new Position(3273, 3428, 0),
						new Position(3274, 3427, 0), new Position(3275, 3426, 0), new Position(3276, 3425, 0),
						new Position(3277, 3424, 0), new Position(3277, 3423, 0), new Position(3277, 3422, 0),
						new Position(3277, 3421, 0), new Position(3278, 3420, 0), new Position(3279, 3419, 0),
						new Position(3280, 3419, 0), new Position(3280, 3418, 0), new Position(3281, 3417, 0),
						new Position(3281, 3416, 0), new Position(3281, 3415, 0), new Position(3282, 3415, 0),
						new Position(3283, 3414, 0), new Position(3284, 3413, 0), new Position(3285, 3412, 0),
						new Position(3285, 3411, 0), new Position(3286, 3410, 0), new Position(3287, 3409, 0),
						new Position(3288, 3408, 0), new Position(3288, 3407, 0), new Position(3288, 3406, 0),
						new Position(3289, 3405, 0), new Position(3289, 3404, 0), new Position(3290, 3403, 0),
						new Position(3290, 3402, 0), new Position(3290, 3401, 0), new Position(3290, 3400, 0),
						new Position(3290, 3399, 0), new Position(3290, 3398, 0), new Position(3290, 3397, 0),
						new Position(3290, 3396, 0), new Position(3290, 3395, 0), new Position(3290, 3394, 0),
						new Position(3290, 3393, 0), new Position(3290, 3392, 0), new Position(3290, 3391, 0),
						new Position(3290, 3390, 0), new Position(3290, 3389, 0), new Position(3290, 3388, 0),
						new Position(3290, 3387, 0), new Position(3290, 3386, 0), new Position(3290, 3385, 0),
						new Position(3290, 3384, 0), new Position(3290, 3383, 0), new Position(3290, 3382, 0),
						new Position(3290, 3381, 0), new Position(3290, 3380, 0), new Position(3290, 3379, 0),
						new Position(3290, 3378, 0), new Position(3290, 3377, 0), new Position(3290, 3376, 0),
						new Position(3290, 3375, 0), new Position(3290, 3374, 0), new Position(3289, 3373, 0),
						new Position(3288, 3372, 0), new Position(3287, 3371, 0), new Position(3287, 3370, 0),
						new Position(3287, 3369, 0), new Position(3287, 3368, 0), new Position(3287, 3367, 0)));
		return new RandomLocation(pos).getRandomPosition();

	}

	@Override
	public ArrayList<Position> getExactMiningSpotPosition() {
		return new ArrayList<Position>(Arrays.asList(new Position(3286, 3368, 0)));
	}

	@Override
	public Area getAreaOfMiningLocation() {
		return new Area(new int[][] { { 3284, 3371 }, { 3288, 3371 }, { 3288, 3366 }, { 3284, 3366 }, { 3284, 3371 } });
	}

	@Override
	public Area getAreaOfOperating() {
		return new Area(new int[][] { { 3122, 3525 }, { 3337, 3523 }, { 3337, 3328 }, { 3135, 3359 }, { 3124, 3528 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromMiningSpotToBank() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3287, 3367, 0), new Position(3287, 3367, 0), new Position(3287, 3368, 0),
						new Position(3287, 3369, 0), new Position(3287, 3370, 0), new Position(3287, 3371, 0),
						new Position(3287, 3372, 0), new Position(3287, 3373, 0), new Position(3288, 3374, 0),
						new Position(3289, 3375, 0), new Position(3289, 3376, 0), new Position(3290, 3377, 0),
						new Position(3290, 3378, 0), new Position(3290, 3379, 0), new Position(3290, 3380, 0),
						new Position(3290, 3381, 0), new Position(3290, 3382, 0), new Position(3290, 3383, 0),
						new Position(3290, 3384, 0), new Position(3290, 3385, 0), new Position(3290, 3386, 0),
						new Position(3290, 3387, 0), new Position(3290, 3388, 0), new Position(3290, 3389, 0),
						new Position(3290, 3390, 0), new Position(3290, 3391, 0), new Position(3290, 3392, 0),
						new Position(3290, 3393, 0), new Position(3290, 3394, 0), new Position(3290, 3395, 0),
						new Position(3290, 3396, 0), new Position(3290, 3397, 0), new Position(3290, 3398, 0),
						new Position(3290, 3399, 0), new Position(3290, 3400, 0), new Position(3290, 3401, 0),
						new Position(3290, 3402, 0), new Position(3290, 3403, 0), new Position(3290, 3404, 0),
						new Position(3290, 3405, 0), new Position(3290, 3406, 0), new Position(3290, 3407, 0),
						new Position(3289, 3408, 0), new Position(3288, 3409, 0), new Position(3287, 3410, 0),
						new Position(3286, 3411, 0), new Position(3285, 3412, 0), new Position(3284, 3412, 0),
						new Position(3283, 3413, 0), new Position(3282, 3414, 0), new Position(3281, 3415, 0),
						new Position(3281, 3416, 0), new Position(3281, 3417, 0), new Position(3281, 3418, 0),
						new Position(3280, 3419, 0), new Position(3279, 3419, 0), new Position(3279, 3420, 0),
						new Position(3279, 3421, 0), new Position(3278, 3422, 0), new Position(3277, 3423, 0),
						new Position(3277, 3424, 0), new Position(3276, 3424, 0), new Position(3275, 3425, 0),
						new Position(3274, 3426, 0), new Position(3273, 3426, 0), new Position(3272, 3426, 0),
						new Position(3271, 3426, 0), new Position(3270, 3426, 0), new Position(3269, 3426, 0),
						new Position(3268, 3426, 0), new Position(3267, 3426, 0), new Position(3266, 3426, 0),
						new Position(3265, 3426, 0), new Position(3264, 3426, 0), new Position(3263, 3426, 0),
						new Position(3262, 3426, 0), new Position(3261, 3426, 0), new Position(3260, 3427, 0),
						new Position(3259, 3428, 0), new Position(3258, 3428, 0), new Position(3257, 3428, 0),
						new Position(3256, 3428, 0), new Position(3255, 3427, 0), new Position(3254, 3426, 0),
						new Position(3254, 3425, 0), new Position(3254, 3424, 0), new Position(3254, 3423, 0),
						new Position(3253, 3422, 0), new Position(3252, 3421, 0), new Position(3251, 3420, 0)));

		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3485, 0), new Position(3164, 3485, 0),
				new Position(3164, 3484, 0), new Position(3164, 3483, 0), new Position(3164, 3482, 0),
				new Position(3164, 3481, 0), new Position(3164, 3480, 0), new Position(3164, 3479, 0),
				new Position(3164, 3478, 0), new Position(3164, 3477, 0), new Position(3164, 3476, 0),
				new Position(3164, 3475, 0), new Position(3164, 3474, 0), new Position(3164, 3473, 0),
				new Position(3164, 3472, 0), new Position(3164, 3471, 0), new Position(3164, 3470, 0),
				new Position(3164, 3469, 0), new Position(3164, 3468, 0), new Position(3165, 3467, 0),
				new Position(3166, 3466, 0), new Position(3167, 3465, 0), new Position(3168, 3465, 0),
				new Position(3169, 3465, 0), new Position(3170, 3465, 0), new Position(3171, 3464, 0),
				new Position(3172, 3463, 0), new Position(3173, 3462, 0), new Position(3174, 3461, 0),
				new Position(3175, 3460, 0), new Position(3176, 3459, 0), new Position(3177, 3458, 0),
				new Position(3178, 3457, 0), new Position(3179, 3456, 0), new Position(3180, 3456, 0),
				new Position(3181, 3456, 0), new Position(3182, 3456, 0), new Position(3183, 3456, 0),
				new Position(3184, 3455, 0), new Position(3185, 3454, 0), new Position(3186, 3453, 0),
				new Position(3187, 3452, 0), new Position(3188, 3451, 0), new Position(3189, 3450, 0),
				new Position(3190, 3449, 0), new Position(3191, 3448, 0), new Position(3192, 3448, 0),
				new Position(3193, 3448, 0), new Position(3194, 3448, 0), new Position(3195, 3448, 0),
				new Position(3196, 3448, 0), new Position(3197, 3447, 0), new Position(3198, 3446, 0),
				new Position(3199, 3445, 0), new Position(3200, 3444, 0), new Position(3201, 3443, 0),
				new Position(3202, 3442, 0), new Position(3203, 3442, 0), new Position(3203, 3441, 0),
				new Position(3204, 3440, 0), new Position(3205, 3439, 0), new Position(3206, 3438, 0),
				new Position(3207, 3438, 0), new Position(3208, 3437, 0), new Position(3209, 3437, 0),
				new Position(3210, 3437, 0), new Position(3211, 3437, 0), new Position(3212, 3437, 0),
				new Position(3213, 3437, 0), new Position(3214, 3436, 0), new Position(3215, 3435, 0),
				new Position(3216, 3434, 0), new Position(3217, 3433, 0), new Position(3218, 3432, 0),
				new Position(3219, 3431, 0), new Position(3220, 3430, 0), new Position(3221, 3430, 0),
				new Position(3222, 3430, 0), new Position(3223, 3430, 0), new Position(3224, 3430, 0),
				new Position(3225, 3430, 0), new Position(3226, 3430, 0), new Position(3227, 3430, 0),
				new Position(3228, 3430, 0), new Position(3229, 3430, 0), new Position(3230, 3430, 0),
				new Position(3231, 3430, 0), new Position(3232, 3430, 0), new Position(3233, 3430, 0),
				new Position(3234, 3430, 0), new Position(3235, 3430, 0), new Position(3236, 3430, 0),
				new Position(3237, 3430, 0), new Position(3238, 3430, 0), new Position(3239, 3430, 0),
				new Position(3240, 3430, 0), new Position(3241, 3430, 0), new Position(3242, 3430, 0),
				new Position(3243, 3430, 0), new Position(3244, 3430, 0), new Position(3245, 3430, 0),
				new Position(3246, 3430, 0), new Position(3247, 3430, 0), new Position(3248, 3430, 0),
				new Position(3249, 3430, 0), new Position(3250, 3429, 0), new Position(3251, 3428, 0),
				new Position(3252, 3427, 0), new Position(3253, 3426, 0), new Position(3253, 3425, 0),
				new Position(3253, 3424, 0), new Position(3253, 3423, 0), new Position(3253, 3422, 0),
				new Position(3253, 3421, 0), new Position(3252, 3420, 0)));
		return new RandomLocation(pos).getRandomPosition();

	}

	@Override
	public ArrayList<Position> getPositionsFromGeToMiningPosition() {
		ArrayList<Position>[] pos = new ArrayList[1];

		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3486, 0), new Position(3164, 3486, 0), new Position(3164, 3485, 0),
						new Position(3164, 3484, 0), new Position(3164, 3483, 0), new Position(3164, 3482, 0),
						new Position(3164, 3481, 0), new Position(3164, 3480, 0), new Position(3164, 3479, 0),
						new Position(3164, 3478, 0), new Position(3164, 3477, 0), new Position(3164, 3476, 0),
						new Position(3164, 3475, 0), new Position(3164, 3474, 0), new Position(3164, 3473, 0),
						new Position(3164, 3472, 0), new Position(3164, 3471, 0), new Position(3164, 3470, 0),
						new Position(3164, 3469, 0), new Position(3164, 3468, 0), new Position(3165, 3467, 0),
						new Position(3166, 3466, 0), new Position(3167, 3465, 0), new Position(3168, 3465, 0),
						new Position(3169, 3465, 0), new Position(3170, 3465, 0), new Position(3171, 3464, 0),
						new Position(3172, 3463, 0), new Position(3173, 3462, 0), new Position(3174, 3461, 0),
						new Position(3175, 3460, 0), new Position(3176, 3459, 0), new Position(3177, 3458, 0),
						new Position(3178, 3457, 0), new Position(3179, 3456, 0), new Position(3180, 3456, 0),
						new Position(3181, 3456, 0), new Position(3182, 3456, 0), new Position(3183, 3456, 0),
						new Position(3184, 3455, 0), new Position(3185, 3454, 0), new Position(3186, 3453, 0),
						new Position(3187, 3452, 0), new Position(3188, 3451, 0), new Position(3189, 3450, 0),
						new Position(3190, 3449, 0), new Position(3191, 3448, 0), new Position(3192, 3448, 0),
						new Position(3193, 3448, 0), new Position(3194, 3448, 0), new Position(3195, 3448, 0),
						new Position(3196, 3448, 0), new Position(3197, 3447, 0), new Position(3198, 3446, 0),
						new Position(3199, 3445, 0), new Position(3200, 3444, 0), new Position(3201, 3443, 0),
						new Position(3202, 3442, 0), new Position(3203, 3442, 0), new Position(3203, 3441, 0),
						new Position(3204, 3440, 0), new Position(3205, 3439, 0), new Position(3206, 3438, 0),
						new Position(3207, 3438, 0), new Position(3208, 3437, 0), new Position(3209, 3437, 0),
						new Position(3210, 3437, 0), new Position(3211, 3437, 0), new Position(3212, 3437, 0),
						new Position(3213, 3437, 0), new Position(3214, 3436, 0), new Position(3215, 3435, 0),
						new Position(3216, 3434, 0), new Position(3217, 3433, 0), new Position(3218, 3432, 0),
						new Position(3219, 3431, 0), new Position(3220, 3430, 0), new Position(3221, 3430, 0),
						new Position(3222, 3430, 0), new Position(3223, 3430, 0), new Position(3224, 3430, 0),
						new Position(3225, 3430, 0), new Position(3226, 3430, 0), new Position(3227, 3430, 0),
						new Position(3228, 3430, 0), new Position(3229, 3430, 0), new Position(3230, 3430, 0),
						new Position(3231, 3430, 0), new Position(3232, 3430, 0), new Position(3233, 3430, 0),
						new Position(3234, 3430, 0), new Position(3235, 3430, 0), new Position(3236, 3430, 0),
						new Position(3237, 3430, 0), new Position(3238, 3430, 0), new Position(3239, 3430, 0),
						new Position(3240, 3430, 0), new Position(3241, 3430, 0), new Position(3242, 3430, 0),
						new Position(3243, 3430, 0), new Position(3244, 3430, 0), new Position(3245, 3430, 0),
						new Position(3246, 3430, 0), new Position(3247, 3430, 0), new Position(3248, 3430, 0),
						new Position(3249, 3430, 0), new Position(3250, 3430, 0), new Position(3251, 3430, 0),
						new Position(3252, 3430, 0), new Position(3253, 3430, 0), new Position(3254, 3430, 0),
						new Position(3255, 3430, 0), new Position(3256, 3430, 0), new Position(3257, 3430, 0),
						new Position(3258, 3430, 0), new Position(3259, 3430, 0), new Position(3260, 3430, 0),
						new Position(3261, 3430, 0), new Position(3262, 3430, 0), new Position(3263, 3430, 0),
						new Position(3264, 3430, 0), new Position(3265, 3430, 0), new Position(3266, 3430, 0),
						new Position(3267, 3430, 0), new Position(3268, 3430, 0), new Position(3269, 3430, 0),
						new Position(3270, 3430, 0), new Position(3271, 3430, 0), new Position(3272, 3429, 0),
						new Position(3273, 3428, 0), new Position(3274, 3427, 0), new Position(3275, 3426, 0),
						new Position(3276, 3425, 0), new Position(3277, 3424, 0), new Position(3277, 3423, 0),
						new Position(3277, 3422, 0), new Position(3277, 3421, 0), new Position(3278, 3420, 0),
						new Position(3279, 3419, 0), new Position(3280, 3419, 0), new Position(3280, 3418, 0),
						new Position(3281, 3417, 0), new Position(3281, 3416, 0), new Position(3281, 3415, 0),
						new Position(3282, 3415, 0), new Position(3283, 3414, 0), new Position(3284, 3413, 0),
						new Position(3285, 3412, 0), new Position(3285, 3411, 0), new Position(3286, 3410, 0),
						new Position(3287, 3409, 0), new Position(3288, 3408, 0), new Position(3288, 3407, 0),
						new Position(3288, 3406, 0), new Position(3289, 3405, 0), new Position(3289, 3404, 0),
						new Position(3290, 3403, 0), new Position(3290, 3402, 0), new Position(3290, 3401, 0),
						new Position(3290, 3400, 0), new Position(3290, 3399, 0), new Position(3290, 3398, 0),
						new Position(3290, 3397, 0), new Position(3290, 3396, 0), new Position(3290, 3395, 0),
						new Position(3290, 3394, 0), new Position(3290, 3393, 0), new Position(3290, 3392, 0),
						new Position(3290, 3391, 0), new Position(3290, 3390, 0), new Position(3290, 3389, 0),
						new Position(3290, 3388, 0), new Position(3290, 3387, 0), new Position(3290, 3386, 0),
						new Position(3290, 3385, 0), new Position(3290, 3384, 0), new Position(3290, 3383, 0),
						new Position(3290, 3382, 0), new Position(3290, 3381, 0), new Position(3290, 3380, 0),
						new Position(3290, 3379, 0), new Position(3290, 3378, 0), new Position(3290, 3377, 0),
						new Position(3290, 3376, 0), new Position(3290, 3375, 0), new Position(3290, 3374, 0),
						new Position(3289, 3373, 0), new Position(3288, 3372, 0), new Position(3287, 3371, 0),
						new Position(3287, 3370, 0), new Position(3287, 3369, 0), new Position(3287, 3368, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area exactMiningPositionArea() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3284, 3370 }, { 3288, 3370 }, { 3288, 3366 }, { 3284, 3366 }, { 3284, 3370 } });
	}

}
