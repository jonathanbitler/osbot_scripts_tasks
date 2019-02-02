package osbot_scripts.scripttypes.mining.clay;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import osbot_scripts.scripttypes.RandomLocation;
import osbot_scripts.scripttypes.templates.MiningLocationTemplate;

public class ClayMiningRimmington implements MiningLocationTemplate {

	@Override
	public Area getBankAreaLocation() {
		return new Area(new int[][] { { 3009, 3358 }, { 3009, 3355 }, { 3019, 3355 }, { 3019, 3358 }, { 3009, 3358 } });
	}

	@Override
	public ArrayList<Position> gePositionsFromBankToMiningSpot() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(
				Arrays.asList(new Position(3014, 3356, 0), new Position(3014, 3356, 0), new Position(3013, 3357, 0),
						new Position(3013, 3358, 0), new Position(3012, 3359, 0), new Position(3011, 3359, 0),
						new Position(3010, 3359, 0), new Position(3009, 3359, 0), new Position(3008, 3359, 0),
						new Position(3008, 3358, 0), new Position(3008, 3357, 0), new Position(3008, 3356, 0),
						new Position(3008, 3355, 0), new Position(3008, 3354, 0), new Position(3008, 3353, 0),
						new Position(3008, 3352, 0), new Position(3008, 3351, 0), new Position(3008, 3350, 0),
						new Position(3008, 3349, 0), new Position(3008, 3348, 0), new Position(3008, 3347, 0),
						new Position(3008, 3346, 0), new Position(3008, 3345, 0), new Position(3008, 3344, 0),
						new Position(3008, 3343, 0), new Position(3008, 3342, 0), new Position(3008, 3341, 0),
						new Position(3008, 3340, 0), new Position(3008, 3339, 0), new Position(3008, 3338, 0),
						new Position(3008, 3337, 0), new Position(3008, 3336, 0), new Position(3008, 3335, 0),
						new Position(3008, 3334, 0), new Position(3008, 3333, 0), new Position(3008, 3332, 0),
						new Position(3008, 3331, 0), new Position(3008, 3330, 0), new Position(3008, 3329, 0),
						new Position(3008, 3328, 0), new Position(3008, 3327, 0), new Position(3008, 3326, 0),
						new Position(3008, 3325, 0), new Position(3008, 3324, 0), new Position(3008, 3323, 0),
						new Position(3008, 3322, 0), new Position(3008, 3321, 0), new Position(3008, 3320, 0),
						new Position(3008, 3319, 0), new Position(3008, 3318, 0), new Position(3007, 3317, 0),
						new Position(3007, 3316, 0), new Position(3007, 3315, 0), new Position(3007, 3314, 0),
						new Position(3007, 3313, 0), new Position(3007, 3312, 0), new Position(3007, 3311, 0),
						new Position(3007, 3310, 0), new Position(3007, 3309, 0), new Position(3007, 3308, 0),
						new Position(3007, 3307, 0), new Position(3007, 3306, 0), new Position(3007, 3305, 0),
						new Position(3007, 3304, 0), new Position(3007, 3303, 0), new Position(3007, 3302, 0),
						new Position(3007, 3301, 0), new Position(3007, 3300, 0), new Position(3007, 3299, 0),
						new Position(3007, 3298, 0), new Position(3007, 3297, 0), new Position(3007, 3296, 0),
						new Position(3007, 3295, 0), new Position(3007, 3294, 0), new Position(3007, 3293, 0),
						new Position(3007, 3292, 0), new Position(3007, 3291, 0), new Position(3007, 3290, 0),
						new Position(3007, 3289, 0), new Position(3007, 3288, 0), new Position(3006, 3287, 0),
						new Position(3005, 3286, 0), new Position(3004, 3285, 0), new Position(3003, 3284, 0),
						new Position(3002, 3283, 0), new Position(3002, 3282, 0), new Position(3002, 3281, 0),
						new Position(3001, 3280, 0), new Position(3000, 3279, 0), new Position(2999, 3278, 0),
						new Position(2998, 3277, 0), new Position(2997, 3276, 0), new Position(2996, 3275, 0),
						new Position(2995, 3274, 0), new Position(2994, 3273, 0), new Position(2993, 3272, 0),
						new Position(2992, 3271, 0), new Position(2991, 3270, 0), new Position(2990, 3269, 0),
						new Position(2989, 3268, 0), new Position(2988, 3268, 0), new Position(2987, 3267, 0),
						new Position(2986, 3266, 0), new Position(2985, 3265, 0), new Position(2984, 3264, 0),
						new Position(2983, 3263, 0), new Position(2983, 3262, 0), new Position(2982, 3261, 0),
						new Position(2982, 3260, 0), new Position(2982, 3259, 0), new Position(2982, 3258, 0),
						new Position(2982, 3257, 0), new Position(2982, 3256, 0), new Position(2981, 3255, 0),
						new Position(2980, 3254, 0), new Position(2979, 3253, 0), new Position(2978, 3252, 0),
						new Position(2978, 3251, 0), new Position(2979, 3250, 0), new Position(2980, 3249, 0),
						new Position(2980, 3248, 0), new Position(2980, 3247, 0), new Position(2980, 3246, 0),
						new Position(2981, 3245, 0), new Position(2982, 3244, 0), new Position(2983, 3243, 0),
						new Position(2984, 3242, 0), new Position(2985, 3241, 0), new Position(2986, 3240, 0)));
		return new RandomLocation(pos).getRandomPosition();

	}

	@Override
	public ArrayList<Position> getExactMiningSpotPosition() {
		return new ArrayList<Position>(Arrays.asList(new Position(2986, 3240, 0)));
	}

	@Override
	public Area getAreaOfMiningLocation() {
		return new Area(new int[][] { { 2984, 3243 }, { 2990, 3243 }, { 2990, 3237 }, { 2984, 3238 }, { 2984, 3243 } });
	}

	@Override
	public Area getAreaOfOperating() {
		return new Area(new int[][] { { 2943, 3534 }, { 3334, 3534 }, { 3325, 3337 }, { 3016, 3195 }, { 2889, 3230 },
				{ 2897, 3387 } });
	}

	@Override
	public ArrayList<Position> getPositionsFromMiningSpotToBank() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(2984, 3239, 0), new Position(2984, 3239, 0),
				new Position(2984, 3240, 0), new Position(2984, 3241, 0), new Position(2984, 3242, 0),
				new Position(2983, 3243, 0), new Position(2982, 3244, 0), new Position(2981, 3245, 0),
				new Position(2981, 3246, 0), new Position(2981, 3247, 0), new Position(2981, 3248, 0),
				new Position(2980, 3249, 0), new Position(2979, 3250, 0), new Position(2978, 3251, 0),
				new Position(2978, 3252, 0), new Position(2979, 3253, 0), new Position(2980, 3254, 0),
				new Position(2981, 3255, 0), new Position(2981, 3256, 0), new Position(2981, 3257, 0),
				new Position(2981, 3258, 0), new Position(2981, 3259, 0), new Position(2982, 3260, 0),
				new Position(2982, 3261, 0), new Position(2982, 3262, 0), new Position(2983, 3263, 0),
				new Position(2984, 3263, 0), new Position(2985, 3264, 0), new Position(2986, 3265, 0),
				new Position(2987, 3266, 0), new Position(2988, 3267, 0), new Position(2989, 3268, 0),
				new Position(2989, 3269, 0), new Position(2990, 3270, 0), new Position(2991, 3271, 0),
				new Position(2992, 3272, 0), new Position(2993, 3273, 0), new Position(2994, 3274, 0),
				new Position(2995, 3275, 0), new Position(2996, 3276, 0), new Position(2997, 3277, 0),
				new Position(2998, 3278, 0), new Position(2999, 3279, 0), new Position(3000, 3280, 0),
				new Position(3001, 3281, 0), new Position(3001, 3282, 0), new Position(3002, 3283, 0),
				new Position(3002, 3284, 0), new Position(3003, 3285, 0), new Position(3003, 3286, 0),
				new Position(3003, 3287, 0), new Position(3003, 3288, 0), new Position(3003, 3289, 0),
				new Position(3003, 3290, 0), new Position(3003, 3291, 0), new Position(3003, 3292, 0),
				new Position(3003, 3293, 0), new Position(3003, 3294, 0), new Position(3004, 3295, 0),
				new Position(3005, 3296, 0), new Position(3005, 3297, 0), new Position(3005, 3298, 0),
				new Position(3005, 3299, 0), new Position(3005, 3300, 0), new Position(3005, 3301, 0),
				new Position(3005, 3302, 0), new Position(3005, 3303, 0), new Position(3005, 3304, 0),
				new Position(3005, 3305, 0), new Position(3005, 3306, 0), new Position(3005, 3307, 0),
				new Position(3005, 3308, 0), new Position(3005, 3309, 0), new Position(3005, 3310, 0),
				new Position(3005, 3311, 0), new Position(3005, 3312, 0), new Position(3005, 3313, 0),
				new Position(3005, 3314, 0), new Position(3005, 3315, 0), new Position(3005, 3316, 0),
				new Position(3005, 3317, 0), new Position(3005, 3318, 0), new Position(3005, 3319, 0),
				new Position(3005, 3320, 0), new Position(3005, 3321, 0), new Position(3006, 3322, 0),
				new Position(3006, 3323, 0), new Position(3006, 3324, 0), new Position(3006, 3325, 0),
				new Position(3006, 3326, 0), new Position(3006, 3327, 0), new Position(3006, 3328, 0),
				new Position(3006, 3329, 0), new Position(3006, 3330, 0), new Position(3006, 3331, 0),
				new Position(3006, 3332, 0), new Position(3006, 3333, 0), new Position(3006, 3334, 0),
				new Position(3006, 3335, 0), new Position(3006, 3336, 0), new Position(3006, 3337, 0),
				new Position(3006, 3338, 0), new Position(3006, 3339, 0), new Position(3006, 3340, 0),
				new Position(3006, 3341, 0), new Position(3006, 3342, 0), new Position(3006, 3343, 0),
				new Position(3006, 3344, 0), new Position(3006, 3345, 0), new Position(3006, 3346, 0),
				new Position(3006, 3347, 0), new Position(3006, 3348, 0), new Position(3006, 3349, 0),
				new Position(3006, 3350, 0), new Position(3007, 3351, 0), new Position(3007, 3352, 0),
				new Position(3007, 3353, 0), new Position(3007, 3354, 0), new Position(3007, 3355, 0),
				new Position(3007, 3356, 0), new Position(3007, 3357, 0), new Position(3007, 3358, 0),
				new Position(3008, 3359, 0), new Position(3009, 3359, 0), new Position(3010, 3359, 0),
				new Position(3011, 3359, 0), new Position(3012, 3359, 0), new Position(3012, 3358, 0),
				new Position(3012, 3357, 0), new Position(3013, 3356, 0)));

		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToBank() {
		ArrayList<Position>[] pos = new ArrayList[1];
		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3164, 3483, 0), new Position(3164, 3483, 0),
				new Position(3164, 3482, 0), new Position(3164, 3481, 0), new Position(3164, 3480, 0),
				new Position(3164, 3479, 0), new Position(3164, 3478, 0), new Position(3164, 3477, 0),
				new Position(3164, 3476, 0), new Position(3164, 3475, 0), new Position(3164, 3474, 0),
				new Position(3164, 3473, 0), new Position(3164, 3472, 0), new Position(3164, 3471, 0),
				new Position(3164, 3470, 0), new Position(3164, 3469, 0), new Position(3164, 3468, 0),
				new Position(3164, 3467, 0), new Position(3163, 3466, 0), new Position(3162, 3465, 0),
				new Position(3161, 3465, 0), new Position(3160, 3465, 0), new Position(3160, 3464, 0),
				new Position(3160, 3463, 0), new Position(3159, 3462, 0), new Position(3158, 3461, 0),
				new Position(3157, 3460, 0), new Position(3156, 3460, 0), new Position(3155, 3460, 0),
				new Position(3154, 3460, 0), new Position(3153, 3459, 0), new Position(3152, 3458, 0),
				new Position(3151, 3457, 0), new Position(3150, 3456, 0), new Position(3149, 3456, 0),
				new Position(3148, 3456, 0), new Position(3147, 3456, 0), new Position(3146, 3456, 0),
				new Position(3145, 3456, 0), new Position(3144, 3456, 0), new Position(3143, 3456, 0),
				new Position(3142, 3456, 0), new Position(3141, 3456, 0), new Position(3140, 3456, 0),
				new Position(3139, 3455, 0), new Position(3138, 3454, 0), new Position(3137, 3453, 0),
				new Position(3137, 3452, 0), new Position(3137, 3451, 0), new Position(3137, 3450, 0),
				new Position(3136, 3449, 0), new Position(3135, 3448, 0), new Position(3134, 3447, 0),
				new Position(3133, 3447, 0), new Position(3132, 3446, 0), new Position(3131, 3445, 0),
				new Position(3130, 3444, 0), new Position(3129, 3443, 0), new Position(3128, 3443, 0),
				new Position(3127, 3442, 0), new Position(3126, 3441, 0), new Position(3125, 3440, 0),
				new Position(3125, 3439, 0), new Position(3124, 3438, 0), new Position(3123, 3437, 0),
				new Position(3122, 3437, 0), new Position(3121, 3436, 0), new Position(3120, 3435, 0),
				new Position(3120, 3434, 0), new Position(3120, 3433, 0), new Position(3120, 3432, 0),
				new Position(3119, 3431, 0), new Position(3119, 3430, 0), new Position(3119, 3429, 0),
				new Position(3119, 3428, 0), new Position(3118, 3427, 0), new Position(3118, 3426, 0),
				new Position(3117, 3425, 0), new Position(3116, 3424, 0), new Position(3115, 3423, 0),
				new Position(3114, 3422, 0), new Position(3114, 3421, 0), new Position(3113, 3421, 0),
				new Position(3112, 3421, 0), new Position(3111, 3421, 0), new Position(3110, 3421, 0),
				new Position(3109, 3421, 0), new Position(3108, 3421, 0), new Position(3107, 3421, 0),
				new Position(3106, 3421, 0), new Position(3105, 3421, 0), new Position(3104, 3421, 0),
				new Position(3103, 3421, 0), new Position(3102, 3421, 0), new Position(3101, 3421, 0),
				new Position(3100, 3420, 0), new Position(3099, 3419, 0), new Position(3098, 3418, 0),
				new Position(3098, 3417, 0), new Position(3098, 3416, 0), new Position(3098, 3415, 0),
				new Position(3097, 3415, 0), new Position(3096, 3414, 0), new Position(3095, 3413, 0),
				new Position(3095, 3412, 0), new Position(3095, 3411, 0), new Position(3095, 3410, 0),
				new Position(3094, 3409, 0), new Position(3094, 3408, 0), new Position(3094, 3407, 0),
				new Position(3093, 3406, 0), new Position(3092, 3405, 0), new Position(3091, 3404, 0),
				new Position(3090, 3403, 0), new Position(3090, 3402, 0), new Position(3090, 3401, 0),
				new Position(3090, 3400, 0), new Position(3089, 3399, 0), new Position(3088, 3398, 0),
				new Position(3087, 3397, 0), new Position(3087, 3396, 0), new Position(3087, 3395, 0),
				new Position(3087, 3394, 0), new Position(3087, 3393, 0), new Position(3087, 3392, 0),
				new Position(3086, 3391, 0), new Position(3086, 3390, 0), new Position(3086, 3389, 0),
				new Position(3085, 3388, 0), new Position(3085, 3387, 0), new Position(3085, 3386, 0),
				new Position(3084, 3385, 0), new Position(3084, 3384, 0), new Position(3084, 3383, 0),
				new Position(3084, 3382, 0), new Position(3084, 3381, 0), new Position(3084, 3380, 0),
				new Position(3083, 3379, 0), new Position(3083, 3378, 0), new Position(3082, 3378, 0),
				new Position(3082, 3377, 0), new Position(3081, 3376, 0), new Position(3080, 3375, 0),
				new Position(3080, 3374, 0), new Position(3080, 3373, 0), new Position(3080, 3372, 0),
				new Position(3079, 3371, 0), new Position(3078, 3370, 0), new Position(3077, 3369, 0),
				new Position(3076, 3368, 0), new Position(3076, 3367, 0), new Position(3076, 3366, 0),
				new Position(3076, 3365, 0), new Position(3076, 3364, 0), new Position(3076, 3363, 0),
				new Position(3075, 3362, 0), new Position(3074, 3361, 0), new Position(3073, 3360, 0),
				new Position(3073, 3359, 0), new Position(3073, 3358, 0), new Position(3072, 3357, 0),
				new Position(3072, 3356, 0), new Position(3072, 3355, 0), new Position(3072, 3354, 0),
				new Position(3072, 3353, 0), new Position(3072, 3352, 0), new Position(3072, 3351, 0),
				new Position(3072, 3350, 0), new Position(3072, 3349, 0), new Position(3072, 3348, 0),
				new Position(3072, 3347, 0), new Position(3072, 3346, 0), new Position(3072, 3345, 0),
				new Position(3071, 3344, 0), new Position(3070, 3343, 0), new Position(3069, 3342, 0),
				new Position(3068, 3341, 0), new Position(3067, 3340, 0), new Position(3067, 3339, 0),
				new Position(3066, 3338, 0), new Position(3065, 3337, 0), new Position(3064, 3336, 0),
				new Position(3064, 3335, 0), new Position(3064, 3334, 0), new Position(3064, 3333, 0),
				new Position(3064, 3332, 0), new Position(3064, 3331, 0), new Position(3064, 3330, 0),
				new Position(3063, 3329, 0), new Position(3062, 3328, 0), new Position(3061, 3327, 0),
				new Position(3060, 3327, 0), new Position(3059, 3327, 0), new Position(3058, 3327, 0),
				new Position(3057, 3327, 0), new Position(3056, 3327, 0), new Position(3055, 3327, 0),
				new Position(3054, 3327, 0), new Position(3053, 3327, 0), new Position(3052, 3327, 0),
				new Position(3051, 3327, 0), new Position(3050, 3327, 0), new Position(3049, 3327, 0),
				new Position(3048, 3327, 0), new Position(3047, 3327, 0), new Position(3046, 3327, 0),
				new Position(3045, 3327, 0), new Position(3044, 3327, 0), new Position(3043, 3327, 0),
				new Position(3042, 3327, 0), new Position(3041, 3327, 0), new Position(3040, 3327, 0),
				new Position(3039, 3327, 0), new Position(3038, 3327, 0), new Position(3037, 3327, 0),
				new Position(3036, 3327, 0), new Position(3035, 3327, 0), new Position(3034, 3327, 0),
				new Position(3033, 3327, 0), new Position(3032, 3327, 0), new Position(3031, 3327, 0),
				new Position(3030, 3327, 0), new Position(3029, 3327, 0), new Position(3028, 3327, 0),
				new Position(3027, 3327, 0), new Position(3026, 3327, 0), new Position(3025, 3327, 0),
				new Position(3024, 3326, 0), new Position(3023, 3326, 0), new Position(3022, 3326, 0),
				new Position(3021, 3326, 0), new Position(3020, 3326, 0), new Position(3019, 3326, 0),
				new Position(3018, 3326, 0), new Position(3017, 3326, 0), new Position(3016, 3325, 0),
				new Position(3015, 3324, 0), new Position(3014, 3323, 0), new Position(3013, 3323, 0),
				new Position(3013, 3322, 0), new Position(3012, 3322, 0), new Position(3011, 3322, 0),
				new Position(3010, 3322, 0), new Position(3009, 3322, 0), new Position(3008, 3322, 0),
				new Position(3008, 3323, 0), new Position(3008, 3324, 0), new Position(3008, 3325, 0),
				new Position(3008, 3326, 0), new Position(3008, 3327, 0), new Position(3008, 3328, 0),
				new Position(3008, 3329, 0), new Position(3008, 3330, 0), new Position(3008, 3331, 0),
				new Position(3008, 3332, 0), new Position(3008, 3333, 0), new Position(3008, 3334, 0),
				new Position(3008, 3335, 0), new Position(3008, 3336, 0), new Position(3008, 3337, 0),
				new Position(3008, 3338, 0), new Position(3008, 3339, 0), new Position(3008, 3340, 0),
				new Position(3008, 3341, 0), new Position(3008, 3342, 0), new Position(3008, 3343, 0),
				new Position(3008, 3344, 0), new Position(3008, 3345, 0), new Position(3008, 3346, 0),
				new Position(3008, 3347, 0), new Position(3008, 3348, 0), new Position(3008, 3349, 0),
				new Position(3008, 3350, 0), new Position(3008, 3351, 0), new Position(3008, 3352, 0),
				new Position(3008, 3353, 0), new Position(3008, 3354, 0), new Position(3008, 3355, 0),
				new Position(3008, 3356, 0), new Position(3008, 3357, 0), new Position(3008, 3358, 0),
				new Position(3008, 3359, 0), new Position(3009, 3359, 0), new Position(3010, 3359, 0),
				new Position(3011, 3359, 0), new Position(3012, 3359, 0), new Position(3012, 3358, 0),
				new Position(3012, 3357, 0), new Position(3012, 3356, 0), new Position(3013, 3355, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public ArrayList<Position> getPositionsFromGeToMiningPosition() {
		ArrayList<Position>[] pos = new ArrayList[1];

		pos[0] = new ArrayList<Position>(Arrays.asList(new Position(3166, 3484, 0), new Position(3166, 3484, 0),
				new Position(3166, 3483, 0), new Position(3166, 3482, 0), new Position(3166, 3481, 0),
				new Position(3166, 3480, 0), new Position(3166, 3479, 0), new Position(3166, 3478, 0),
				new Position(3165, 3477, 0), new Position(3164, 3476, 0), new Position(3164, 3475, 0),
				new Position(3164, 3474, 0), new Position(3164, 3473, 0), new Position(3164, 3472, 0),
				new Position(3164, 3471, 0), new Position(3164, 3470, 0), new Position(3164, 3469, 0),
				new Position(3163, 3468, 0), new Position(3163, 3467, 0), new Position(3163, 3466, 0),
				new Position(3163, 3465, 0), new Position(3163, 3464, 0), new Position(3163, 3463, 0),
				new Position(3163, 3462, 0), new Position(3163, 3461, 0), new Position(3163, 3460, 0),
				new Position(3163, 3459, 0), new Position(3163, 3458, 0), new Position(3163, 3457, 0),
				new Position(3162, 3456, 0), new Position(3162, 3455, 0), new Position(3161, 3454, 0),
				new Position(3160, 3453, 0), new Position(3159, 3452, 0), new Position(3158, 3451, 0),
				new Position(3157, 3450, 0), new Position(3156, 3449, 0), new Position(3155, 3449, 0),
				new Position(3154, 3449, 0), new Position(3153, 3449, 0), new Position(3152, 3449, 0),
				new Position(3151, 3448, 0), new Position(3150, 3447, 0), new Position(3149, 3446, 0),
				new Position(3148, 3445, 0), new Position(3147, 3444, 0), new Position(3146, 3443, 0),
				new Position(3145, 3443, 0), new Position(3144, 3443, 0), new Position(3143, 3443, 0),
				new Position(3142, 3443, 0), new Position(3141, 3442, 0), new Position(3140, 3441, 0),
				new Position(3139, 3440, 0), new Position(3138, 3440, 0), new Position(3137, 3440, 0),
				new Position(3136, 3439, 0), new Position(3136, 3438, 0), new Position(3135, 3437, 0),
				new Position(3134, 3436, 0), new Position(3133, 3436, 0), new Position(3132, 3436, 0),
				new Position(3131, 3436, 0), new Position(3130, 3435, 0), new Position(3130, 3434, 0),
				new Position(3129, 3433, 0), new Position(3128, 3432, 0), new Position(3127, 3431, 0),
				new Position(3127, 3430, 0), new Position(3126, 3429, 0), new Position(3125, 3428, 0),
				new Position(3124, 3427, 0), new Position(3123, 3426, 0), new Position(3122, 3425, 0),
				new Position(3121, 3424, 0), new Position(3120, 3424, 0), new Position(3119, 3424, 0),
				new Position(3118, 3424, 0), new Position(3117, 3424, 0), new Position(3116, 3424, 0),
				new Position(3115, 3423, 0), new Position(3114, 3422, 0), new Position(3114, 3421, 0),
				new Position(3113, 3421, 0), new Position(3112, 3421, 0), new Position(3111, 3421, 0),
				new Position(3110, 3421, 0), new Position(3109, 3421, 0), new Position(3108, 3421, 0),
				new Position(3107, 3421, 0), new Position(3106, 3421, 0), new Position(3105, 3421, 0),
				new Position(3104, 3421, 0), new Position(3103, 3421, 0), new Position(3102, 3421, 0),
				new Position(3101, 3421, 0), new Position(3100, 3420, 0), new Position(3099, 3419, 0),
				new Position(3098, 3418, 0), new Position(3098, 3417, 0), new Position(3098, 3416, 0),
				new Position(3098, 3415, 0), new Position(3097, 3415, 0), new Position(3096, 3414, 0),
				new Position(3095, 3413, 0), new Position(3095, 3412, 0), new Position(3095, 3411, 0),
				new Position(3095, 3410, 0), new Position(3094, 3409, 0), new Position(3094, 3408, 0),
				new Position(3093, 3407, 0), new Position(3092, 3406, 0), new Position(3091, 3405, 0),
				new Position(3091, 3404, 0), new Position(3090, 3403, 0), new Position(3090, 3402, 0),
				new Position(3090, 3401, 0), new Position(3090, 3400, 0), new Position(3089, 3399, 0),
				new Position(3088, 3398, 0), new Position(3087, 3397, 0), new Position(3087, 3396, 0),
				new Position(3087, 3395, 0), new Position(3087, 3394, 0), new Position(3087, 3393, 0),
				new Position(3087, 3392, 0), new Position(3086, 3391, 0), new Position(3086, 3390, 0),
				new Position(3086, 3389, 0), new Position(3085, 3388, 0), new Position(3085, 3387, 0),
				new Position(3085, 3386, 0), new Position(3084, 3385, 0), new Position(3084, 3384, 0),
				new Position(3084, 3383, 0), new Position(3084, 3382, 0), new Position(3084, 3381, 0),
				new Position(3084, 3380, 0), new Position(3083, 3379, 0), new Position(3083, 3378, 0),
				new Position(3082, 3378, 0), new Position(3082, 3377, 0), new Position(3081, 3376, 0),
				new Position(3080, 3375, 0), new Position(3080, 3374, 0), new Position(3080, 3373, 0),
				new Position(3080, 3372, 0), new Position(3079, 3371, 0), new Position(3078, 3370, 0),
				new Position(3077, 3369, 0), new Position(3076, 3368, 0), new Position(3076, 3367, 0),
				new Position(3076, 3366, 0), new Position(3076, 3365, 0), new Position(3076, 3364, 0),
				new Position(3076, 3363, 0), new Position(3075, 3362, 0), new Position(3074, 3361, 0),
				new Position(3073, 3360, 0), new Position(3073, 3359, 0), new Position(3073, 3358, 0),
				new Position(3072, 3357, 0), new Position(3072, 3356, 0), new Position(3072, 3355, 0),
				new Position(3072, 3354, 0), new Position(3072, 3353, 0), new Position(3072, 3352, 0),
				new Position(3072, 3351, 0), new Position(3072, 3350, 0), new Position(3072, 3349, 0),
				new Position(3072, 3348, 0), new Position(3072, 3347, 0), new Position(3072, 3346, 0),
				new Position(3072, 3345, 0), new Position(3071, 3344, 0), new Position(3070, 3343, 0),
				new Position(3069, 3342, 0), new Position(3068, 3341, 0), new Position(3067, 3340, 0),
				new Position(3067, 3339, 0), new Position(3066, 3338, 0), new Position(3065, 3337, 0),
				new Position(3064, 3336, 0), new Position(3064, 3335, 0), new Position(3064, 3334, 0),
				new Position(3064, 3333, 0), new Position(3064, 3332, 0), new Position(3064, 3331, 0),
				new Position(3064, 3330, 0), new Position(3063, 3329, 0), new Position(3062, 3328, 0),
				new Position(3061, 3327, 0), new Position(3060, 3327, 0), new Position(3059, 3327, 0),
				new Position(3058, 3327, 0), new Position(3057, 3327, 0), new Position(3056, 3327, 0),
				new Position(3055, 3327, 0), new Position(3054, 3327, 0), new Position(3053, 3327, 0),
				new Position(3052, 3327, 0), new Position(3051, 3327, 0), new Position(3050, 3327, 0),
				new Position(3049, 3327, 0), new Position(3048, 3327, 0), new Position(3047, 3327, 0),
				new Position(3046, 3327, 0), new Position(3045, 3327, 0), new Position(3044, 3327, 0),
				new Position(3043, 3327, 0), new Position(3042, 3326, 0), new Position(3041, 3325, 0),
				new Position(3040, 3324, 0), new Position(3039, 3323, 0), new Position(3038, 3323, 0),
				new Position(3037, 3323, 0), new Position(3036, 3322, 0), new Position(3035, 3321, 0),
				new Position(3035, 3320, 0), new Position(3034, 3319, 0), new Position(3033, 3319, 0),
				new Position(3032, 3319, 0), new Position(3031, 3319, 0), new Position(3030, 3318, 0),
				new Position(3029, 3317, 0), new Position(3028, 3317, 0), new Position(3027, 3317, 0),
				new Position(3026, 3316, 0), new Position(3025, 3315, 0), new Position(3024, 3315, 0),
				new Position(3023, 3315, 0), new Position(3022, 3314, 0), new Position(3021, 3314, 0),
				new Position(3020, 3314, 0), new Position(3019, 3314, 0), new Position(3018, 3314, 0),
				new Position(3017, 3314, 0), new Position(3016, 3314, 0), new Position(3015, 3314, 0),
				new Position(3014, 3314, 0), new Position(3013, 3314, 0), new Position(3012, 3314, 0),
				new Position(3011, 3313, 0), new Position(3011, 3312, 0), new Position(3010, 3311, 0),
				new Position(3009, 3310, 0), new Position(3009, 3309, 0), new Position(3009, 3308, 0),
				new Position(3008, 3307, 0), new Position(3007, 3306, 0), new Position(3007, 3305, 0),
				new Position(3007, 3304, 0), new Position(3007, 3303, 0), new Position(3007, 3302, 0),
				new Position(3007, 3301, 0), new Position(3007, 3300, 0), new Position(3007, 3299, 0),
				new Position(3007, 3298, 0), new Position(3007, 3297, 0), new Position(3007, 3296, 0),
				new Position(3007, 3295, 0), new Position(3007, 3294, 0), new Position(3007, 3293, 0),
				new Position(3007, 3292, 0), new Position(3007, 3291, 0), new Position(3007, 3290, 0),
				new Position(3007, 3289, 0), new Position(3007, 3288, 0), new Position(3006, 3287, 0),
				new Position(3005, 3286, 0), new Position(3004, 3285, 0), new Position(3003, 3284, 0),
				new Position(3002, 3283, 0), new Position(3002, 3282, 0), new Position(3002, 3281, 0),
				new Position(3001, 3280, 0), new Position(3000, 3279, 0), new Position(2999, 3278, 0),
				new Position(2998, 3277, 0), new Position(2997, 3276, 0), new Position(2996, 3275, 0),
				new Position(2995, 3274, 0), new Position(2994, 3273, 0), new Position(2993, 3272, 0),
				new Position(2992, 3271, 0), new Position(2991, 3270, 0), new Position(2990, 3269, 0),
				new Position(2989, 3268, 0), new Position(2988, 3268, 0), new Position(2987, 3267, 0),
				new Position(2986, 3266, 0), new Position(2985, 3265, 0), new Position(2984, 3264, 0),
				new Position(2983, 3263, 0), new Position(2983, 3262, 0), new Position(2982, 3261, 0),
				new Position(2982, 3260, 0), new Position(2982, 3259, 0), new Position(2982, 3258, 0),
				new Position(2982, 3257, 0), new Position(2982, 3256, 0), new Position(2981, 3255, 0),
				new Position(2980, 3254, 0), new Position(2979, 3253, 0), new Position(2978, 3252, 0),
				new Position(2978, 3251, 0), new Position(2979, 3250, 0), new Position(2980, 3249, 0),
				new Position(2980, 3248, 0), new Position(2980, 3247, 0), new Position(2980, 3246, 0),
				new Position(2981, 3245, 0), new Position(2982, 3244, 0), new Position(2983, 3243, 0)));
		return new RandomLocation(pos).getRandomPosition();
	}

	@Override
	public Area exactMiningPositionArea() {
		// TODO Auto-generated method stub
		return new Area(new int[][] { { 2984, 3242 }, { 2990, 3242 }, { 2990, 3237 }, { 2985, 3238 } });
	}

}
