package osbot_scripts.scripttypes.woodcutting.oak;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.WoodcuttingLocationTemplate;

public class OakCuttingNorthOfVarrock implements WoodcuttingLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3250, 3423 }, { 3250, 3419 }, { 3258, 3419 }, { 3258, 3423 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3253, 3420, 0), new Position(3252, 3428, 0),
				new Position(3245, 3429, 0), new Position(3239, 3437, 0), new Position(3233, 3445, 0),
				new Position(3235, 3455, 0), new Position(3237, 3462, 0), new Position(3241, 3471, 0),
				new Position(3245, 3480, 0), new Position(3246, 3481, 0), new Position(3245, 3491, 0),
				new Position(3244, 3501, 0), new Position(3244, 3502, 0), new Position(3239, 3507, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3253, 3420, 0), new Position(3254, 3428, 0),
				new Position(3246, 3428, 0), new Position(3247, 3438, 0), new Position(3248, 3445, 0),
				new Position(3246, 3455, 0), new Position(3244, 3462, 0), new Position(3245, 3472, 0),
				new Position(3246, 3482, 0), new Position(3246, 3484, 0), new Position(3245, 3494, 0),
				new Position(3245, 3500, 0), new Position(3241, 3506, 0)));
		pos[2] = new ArrayList<Position>(
				Arrays.asList(new Position(3254, 3419, 0), new Position(3253, 3428, 0), new Position(3246, 3429, 0),
						new Position(3245, 3439, 0), new Position(3244, 3449, 0), new Position(3243, 3459, 0),
						new Position(3242, 3464, 0), new Position(3244, 3474, 0), new Position(3246, 3484, 0),
						new Position(3248, 3489, 0), new Position(3245, 3498, 0), new Position(3242, 3507, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area getAreaOfWoodcuttingLocation() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 3230, 3519 }, { 3229, 3508 }, { 3235, 3502 }, { 3252, 3502 }, { 3250, 3519 } });
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
				Arrays.asList(new Position(3239, 3506, 0), new Position(3243, 3497, 0), new Position(3246, 3488, 0),
						new Position(3245, 3478, 0), new Position(3244, 3468, 0), new Position(3244, 3462, 0),
						new Position(3244, 3452, 0), new Position(3244, 3442, 0), new Position(3244, 3432, 0),
						new Position(3245, 3429, 0), new Position(3252, 3422, 0), new Position(3253, 3420, 0)));
		pos[1] = new ArrayList<Position>(Arrays.asList(new Position(3239, 3508, 0), new Position(3245, 3502, 0),
				new Position(3245, 3492, 0), new Position(3245, 3482, 0), new Position(3245, 3476, 0),
				new Position(3248, 3466, 0), new Position(3249, 3462, 0), new Position(3247, 3452, 0),
				new Position(3245, 3442, 0), new Position(3243, 3432, 0), new Position(3242, 3427, 0),
				new Position(3252, 3428, 0), new Position(3252, 3428, 0), new Position(3252, 3420, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3165, 3484, 0), new Position(3165, 3474, 0),
				new Position(3165, 3464, 0), new Position(3164, 3462, 0), new Position(3173, 3458, 0),
				new Position(3182, 3454, 0), new Position(3186, 3451, 0), new Position(3194, 3445, 0),
				new Position(3202, 3439, 0), new Position(3210, 3433, 0), new Position(3210, 3432, 0),
				new Position(3220, 3431, 0), new Position(3230, 3430, 0), new Position(3233, 3429, 0),
				new Position(3243, 3428, 0), new Position(3252, 3428, 0), new Position(3253, 3420, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3486, 0), new Position(3164, 3476, 0), new Position(3164, 3466, 0),
						new Position(3170, 3458, 0), new Position(3175, 3453, 0), new Position(3176, 3443, 0),
						new Position(3177, 3433, 0), new Position(3178, 3430, 0), new Position(3188, 3429, 0),
						new Position(3198, 3428, 0), new Position(3203, 3427, 0), new Position(3213, 3427, 0),
						new Position(3223, 3427, 0), new Position(3228, 3428, 0), new Position(3238, 3428, 0),
						new Position(3248, 3428, 0), new Position(3254, 3428, 0), new Position(3254, 3420, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3483, 0), new Position(3173, 3486, 0),
				new Position(3181, 3489, 0), new Position(3191, 3488, 0), new Position(3194, 3488, 0),
				new Position(3195, 3478, 0), new Position(3196, 3468, 0), new Position(3197, 3463, 0),
				new Position(3203, 3455, 0), new Position(3209, 3447, 0), new Position(3209, 3446, 0),
				new Position(3213, 3437, 0), new Position(3215, 3430, 0), new Position(3225, 3428, 0),
				new Position(3233, 3427, 0), new Position(3243, 3427, 0), new Position(3253, 3427, 0),
				new Position(3254, 3428, 0), new Position(3253, 3421, 0)));
		pos[3] = new ArrayList<Position>(Arrays.asList(new Position(3167, 3486, 0), new Position(3176, 3489, 0),
				new Position(3178, 3490, 0), new Position(3188, 3489, 0), new Position(3194, 3489, 0),
				new Position(3195, 3479, 0), new Position(3196, 3469, 0), new Position(3197, 3461, 0),
				new Position(3204, 3454, 0), new Position(3211, 3448, 0), new Position(3221, 3451, 0),
				new Position(3222, 3451, 0), new Position(3227, 3460, 0), new Position(3230, 3465, 0),
				new Position(3240, 3464, 0), new Position(3244, 3464, 0), new Position(3245, 3454, 0),
				new Position(3246, 3444, 0), new Position(3246, 3440, 0), new Position(3247, 3430, 0),
				new Position(3247, 3427, 0), new Position(3254, 3420, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToWoodcuttingPosition() {
		ArrayList<Position>[] pos = new ArrayList[4];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3167, 3488, 0), new Position(3177, 3489, 0),
				new Position(3187, 3490, 0), new Position(3191, 3491, 0), new Position(3197, 3483, 0),
				new Position(3197, 3473, 0), new Position(3197, 3463, 0), new Position(3198, 3456, 0),
				new Position(3207, 3451, 0), new Position(3214, 3447, 0), new Position(3221, 3454, 0),
				new Position(3228, 3461, 0), new Position(3234, 3465, 0), new Position(3244, 3465, 0),
				new Position(3245, 3465, 0), new Position(3245, 3475, 0), new Position(3245, 3485, 0),
				new Position(3246, 3493, 0), new Position(3242, 3502, 0), new Position(3239, 3507, 0)));
		pos[1] = new ArrayList<Position>(
				Arrays.asList(new Position(3164, 3484, 0), new Position(3164, 3474, 0), new Position(3164, 3464, 0),
						new Position(3165, 3461, 0), new Position(3155, 3462, 0), new Position(3145, 3463, 0),
						new Position(3136, 3464, 0), new Position(3134, 3474, 0), new Position(3132, 3482, 0),
						new Position(3133, 3492, 0), new Position(3134, 3502, 0), new Position(3135, 3512, 0),
						new Position(3136, 3519, 0), new Position(3146, 3519, 0), new Position(3156, 3519, 0),
						new Position(3166, 3519, 0), new Position(3176, 3519, 0), new Position(3186, 3519, 0),
						new Position(3196, 3519, 0), new Position(3206, 3519, 0), new Position(3211, 3518, 0),
						new Position(3220, 3514, 0), new Position(3229, 3510, 0), new Position(3238, 3506, 0)));
		pos[2] = new ArrayList<Position>(Arrays.asList(new Position(3169, 3489, 0), new Position(3179, 3489, 0),
				new Position(3189, 3489, 0), new Position(3196, 3490, 0), new Position(3197, 3480, 0),
				new Position(3198, 3470, 0), new Position(3199, 3460, 0), new Position(3199, 3454, 0),
				new Position(3208, 3450, 0), new Position(3213, 3448, 0), new Position(3214, 3438, 0),
				new Position(3214, 3433, 0), new Position(3222, 3438, 0), new Position(3230, 3443, 0),
				new Position(3234, 3446, 0), new Position(3237, 3455, 0), new Position(3240, 3464, 0),
				new Position(3245, 3466, 0), new Position(3245, 3476, 0), new Position(3245, 3486, 0),
				new Position(3245, 3496, 0), new Position(3244, 3500, 0), new Position(3238, 3507, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

}
