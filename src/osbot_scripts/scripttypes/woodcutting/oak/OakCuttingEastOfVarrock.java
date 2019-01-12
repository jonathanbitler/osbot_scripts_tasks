package osbot_scripts.scripttypes.woodcutting.oak;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class OakCuttingEastOfVarrock implements WoodcuttingLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3250, 3423 }, { 3250, 3419 }, { 3258, 3419 }, { 3258, 3424 }, { 3250, 3424 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3253, 3421, 0), new Position(3253, 3428, 0), new Position(3263, 3428, 0),
						new Position(3265, 3428, 0), new Position(3275, 3429, 0), new Position(3281, 3429, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3253, 3421, 0), new Position(3259, 3428, 0),
				new Position(3269, 3428, 0), new Position(3274, 3428, 0), new Position(3281, 3427, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3254, 3421, 0), new Position(3251, 3427, 0),
				new Position(3256, 3428, 0), new Position(3264, 3431, 0), new Position(3267, 3426, 0),
				new Position(3273, 3427, 0), new Position(3279, 3429, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area getAreaOfWoodcuttingLocation() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3278, 3438 }, { 3269, 3430 }, { 3277, 3421 }, { 3288, 3425 }, { 3291, 3433 },
				{ 3284, 3438 } });
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
				Arrays.asList(new Position(3280, 3428, 0), new Position(3270, 3430, 0), new Position(3268, 3430, 0),
						new Position(3260, 3428, 0), new Position(3254, 3428, 0), new Position(3253, 3420, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3278, 3428, 0), new Position(3268, 3427, 0),
				new Position(3264, 3426, 0), new Position(3256, 3429, 0), new Position(3253, 3420, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3281, 3433, 0), new Position(3272, 3429, 0), new Position(3271, 3428, 0),
						new Position(3261, 3428, 0), new Position(3256, 3428, 0), new Position(3252, 3420, 0)));
		pos[3] = new ArrayList<Position>(
				Arrays.asList(new Position(3278, 3425, 0), new Position(3272, 3428, 0), new Position(3262, 3429, 0),
						new Position(3261, 3429, 0), new Position(3254, 3427, 0), new Position(3252, 3419, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3484, 0), new Position(3165, 3474, 0),
				new Position(3165, 3464, 0), new Position(3164, 3462, 0), new Position(3173, 3457, 0),
				new Position(3182, 3452, 0), new Position(3187, 3450, 0), new Position(3195, 3444, 0),
				new Position(3203, 3438, 0), new Position(3207, 3436, 0), new Position(3216, 3432, 0),
				new Position(3225, 3428, 0), new Position(3227, 3428, 0), new Position(3237, 3428, 0),
				new Position(3247, 3428, 0), new Position(3251, 3428, 0), new Position(3253, 3420, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3485, 0), new Position(3175, 3486, 0),
				new Position(3185, 3487, 0), new Position(3194, 3489, 0), new Position(3194, 3479, 0),
				new Position(3194, 3469, 0), new Position(3195, 3461, 0), new Position(3203, 3455, 0),
				new Position(3211, 3449, 0), new Position(3212, 3447, 0), new Position(3214, 3437, 0),
				new Position(3216, 3431, 0), new Position(3226, 3430, 0), new Position(3236, 3429, 0),
				new Position(3246, 3428, 0), new Position(3251, 3427, 0), new Position(3253, 3419, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3485, 0), new Position(3165, 3475, 0),
				new Position(3165, 3465, 0), new Position(3166, 3457, 0), new Position(3175, 3452, 0),
				new Position(3183, 3448, 0), new Position(3184, 3438, 0), new Position(3184, 3429, 0),
				new Position(3194, 3429, 0), new Position(3204, 3429, 0), new Position(3210, 3428, 0),
				new Position(3220, 3428, 0), new Position(3230, 3428, 0), new Position(3240, 3428, 0),
				new Position(3245, 3429, 0), new Position(3251, 3421, 0), new Position(3253, 3421, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3163, 3483, 0), new Position(3164, 3473, 0),
				new Position(3165, 3463, 0), new Position(3165, 3458, 0), new Position(3175, 3457, 0),
				new Position(3180, 3457, 0), new Position(3188, 3452, 0), new Position(3196, 3447, 0),
				new Position(3197, 3446, 0), new Position(3205, 3440, 0), new Position(3212, 3434, 0),
				new Position(3222, 3431, 0), new Position(3232, 3428, 0), new Position(3234, 3428, 0),
				new Position(3244, 3429, 0), new Position(3249, 3429, 0), new Position(3253, 3421, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToWoodcuttingPosition() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3485, 0), new Position(3174, 3487, 0),
				new Position(3183, 3489, 0), new Position(3193, 3489, 0), new Position(3195, 3489, 0),
				new Position(3195, 3479, 0), new Position(3195, 3469, 0), new Position(3195, 3460, 0),
				new Position(3203, 3454, 0), new Position(3211, 3448, 0), new Position(3212, 3448, 0),
				new Position(3213, 3438, 0), new Position(3213, 3429, 0), new Position(3223, 3429, 0),
				new Position(3233, 3429, 0), new Position(3243, 3429, 0), new Position(3245, 3428, 0),
				new Position(3255, 3428, 0), new Position(3265, 3428, 0), new Position(3275, 3428, 0),
				new Position(3280, 3427, 0), new Position(3280, 3430, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3485, 0), new Position(3174, 3483, 0),
				new Position(3179, 3482, 0), new Position(3184, 3489, 0), new Position(3194, 3489, 0),
				new Position(3196, 3489, 0), new Position(3197, 3479, 0), new Position(3197, 3476, 0),
				new Position(3198, 3466, 0), new Position(3199, 3456, 0), new Position(3200, 3455, 0),
				new Position(3210, 3453, 0), new Position(3220, 3451, 0), new Position(3223, 3451, 0),
				new Position(3230, 3459, 0), new Position(3235, 3465, 0), new Position(3243, 3460, 0),
				new Position(3246, 3458, 0), new Position(3246, 3448, 0), new Position(3246, 3438, 0),
				new Position(3246, 3436, 0), new Position(3250, 3428, 0), new Position(3260, 3427, 0),
				new Position(3266, 3427, 0), new Position(3276, 3427, 0), new Position(3280, 3427, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3486, 0), new Position(3165, 3476, 0),
				new Position(3165, 3466, 0), new Position(3164, 3464, 0), new Position(3172, 3458, 0),
				new Position(3180, 3452, 0), new Position(3182, 3451, 0), new Position(3191, 3446, 0),
				new Position(3198, 3442, 0), new Position(3206, 3437, 0), new Position(3214, 3432, 0),
				new Position(3218, 3429, 0), new Position(3228, 3429, 0), new Position(3238, 3429, 0),
				new Position(3238, 3428, 0), new Position(3248, 3428, 0), new Position(3258, 3428, 0),
				new Position(3268, 3428, 0), new Position(3273, 3428, 0), new Position(3281, 3428, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

}
