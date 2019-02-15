package osbot_scripts.qp7.progress;

import java.util.ArrayList;
import java.util.Arrays;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;

public class WalkToGrandExchangeIfNotThere {

	/**
	 * Position lists
	 */
	private static final ArrayList<Position> VARROCK_WEST_BANK_TO_GE_POSITIONS = new ArrayList<Position>(
			Arrays.asList(new Position(3183, 3435, 0), new Position(3183, 3435, 0), new Position(3183, 3436, 0),
					new Position(3183, 3437, 0), new Position(3183, 3438, 0), new Position(3183, 3439, 0),
					new Position(3183, 3440, 0), new Position(3183, 3441, 0), new Position(3183, 3442, 0),
					new Position(3183, 3443, 0), new Position(3183, 3444, 0), new Position(3183, 3445, 0),
					new Position(3183, 3446, 0), new Position(3183, 3447, 0), new Position(3182, 3448, 0),
					new Position(3181, 3448, 0), new Position(3180, 3448, 0), new Position(3179, 3449, 0),
					new Position(3178, 3450, 0), new Position(3177, 3451, 0), new Position(3176, 3452, 0),
					new Position(3175, 3453, 0), new Position(3174, 3454, 0), new Position(3173, 3455, 0),
					new Position(3172, 3456, 0), new Position(3171, 3457, 0), new Position(3170, 3458, 0),
					new Position(3169, 3459, 0), new Position(3168, 3460, 0), new Position(3167, 3461, 0),
					new Position(3166, 3462, 0), new Position(3166, 3463, 0), new Position(3166, 3464, 0),
					new Position(3166, 3465, 0), new Position(3166, 3466, 0), new Position(3166, 3467, 0),
					new Position(3166, 3468, 0), new Position(3166, 3469, 0), new Position(3166, 3470, 0),
					new Position(3166, 3471, 0), new Position(3166, 3472, 0), new Position(3166, 3473, 0),
					new Position(3166, 3474, 0), new Position(3166, 3475, 0), new Position(3166, 3476, 0),
					new Position(3166, 3477, 0), new Position(3166, 3478, 0), new Position(3166, 3479, 0),
					new Position(3166, 3480, 0), new Position(3166, 3481, 0), new Position(3166, 3482, 0),
					new Position(3166, 3483, 0), new Position(3165, 3484, 0), new Position(3164, 3485, 0)));

	private static final ArrayList<Position> RIMMONGTON_DEPOSIT_AREA_TO_GE_POSITIONS = new ArrayList<Position>(
			Arrays.asList(new Position(3045, 3235, 0), new Position(3045, 3235, 0), new Position(3044, 3235, 0),
					new Position(3043, 3236, 0), new Position(3042, 3237, 0), new Position(3042, 3238, 0),
					new Position(3042, 3239, 0), new Position(3042, 3240, 0), new Position(3042, 3241, 0),
					new Position(3042, 3242, 0), new Position(3042, 3243, 0), new Position(3042, 3244, 0),
					new Position(3042, 3245, 0), new Position(3042, 3246, 0), new Position(3042, 3247, 0),
					new Position(3042, 3248, 0), new Position(3042, 3249, 0), new Position(3043, 3250, 0),
					new Position(3043, 3251, 0), new Position(3043, 3252, 0), new Position(3043, 3253, 0),
					new Position(3043, 3254, 0), new Position(3043, 3255, 0), new Position(3043, 3256, 0),
					new Position(3043, 3257, 0), new Position(3043, 3258, 0), new Position(3043, 3259, 0),
					new Position(3043, 3260, 0), new Position(3044, 3260, 0), new Position(3044, 3261, 0),
					new Position(3044, 3262, 0), new Position(3044, 3263, 0), new Position(3044, 3264, 0),
					new Position(3044, 3265, 0), new Position(3044, 3266, 0), new Position(3044, 3267, 0),
					new Position(3044, 3268, 0), new Position(3044, 3269, 0), new Position(3044, 3270, 0),
					new Position(3045, 3271, 0), new Position(3045, 3272, 0), new Position(3045, 3273, 0),
					new Position(3045, 3274, 0), new Position(3045, 3275, 0), new Position(3046, 3276, 0),
					new Position(3047, 3277, 0), new Position(3047, 3278, 0), new Position(3047, 3279, 0),
					new Position(3048, 3280, 0), new Position(3049, 3280, 0), new Position(3050, 3281, 0),
					new Position(3051, 3282, 0), new Position(3052, 3283, 0), new Position(3052, 3284, 0),
					new Position(3052, 3285, 0), new Position(3052, 3286, 0), new Position(3052, 3287, 0),
					new Position(3052, 3288, 0), new Position(3052, 3289, 0), new Position(3052, 3290, 0),
					new Position(3052, 3291, 0), new Position(3052, 3292, 0), new Position(3052, 3293, 0),
					new Position(3052, 3294, 0), new Position(3052, 3295, 0), new Position(3052, 3296, 0),
					new Position(3052, 3297, 0), new Position(3052, 3298, 0), new Position(3052, 3299, 0),
					new Position(3052, 3300, 0), new Position(3052, 3301, 0), new Position(3052, 3302, 0),
					new Position(3052, 3303, 0), new Position(3053, 3304, 0), new Position(3054, 3305, 0),
					new Position(3055, 3305, 0), new Position(3056, 3306, 0), new Position(3056, 3307, 0),
					new Position(3056, 3308, 0), new Position(3056, 3309, 0), new Position(3056, 3310, 0),
					new Position(3057, 3311, 0), new Position(3057, 3312, 0), new Position(3057, 3313, 0),
					new Position(3058, 3313, 0), new Position(3058, 3314, 0), new Position(3059, 3315, 0),
					new Position(3060, 3316, 0), new Position(3061, 3317, 0), new Position(3061, 3318, 0),
					new Position(3062, 3318, 0), new Position(3063, 3319, 0), new Position(3063, 3320, 0),
					new Position(3063, 3321, 0), new Position(3063, 3322, 0), new Position(3063, 3323, 0),
					new Position(3063, 3324, 0), new Position(3063, 3325, 0), new Position(3063, 3326, 0),
					new Position(3063, 3327, 0), new Position(3063, 3328, 0), new Position(3063, 3329, 0),
					new Position(3063, 3330, 0), new Position(3063, 3331, 0), new Position(3063, 3332, 0),
					new Position(3063, 3333, 0), new Position(3063, 3334, 0), new Position(3063, 3335, 0),
					new Position(3063, 3336, 0), new Position(3064, 3337, 0), new Position(3065, 3338, 0),
					new Position(3066, 3339, 0), new Position(3067, 3340, 0), new Position(3068, 3341, 0),
					new Position(3069, 3342, 0), new Position(3070, 3343, 0), new Position(3071, 3344, 0),
					new Position(3072, 3345, 0), new Position(3072, 3346, 0), new Position(3072, 3347, 0),
					new Position(3072, 3348, 0), new Position(3072, 3349, 0), new Position(3072, 3350, 0),
					new Position(3072, 3351, 0), new Position(3072, 3352, 0), new Position(3072, 3353, 0),
					new Position(3072, 3354, 0), new Position(3072, 3355, 0), new Position(3072, 3356, 0),
					new Position(3072, 3357, 0), new Position(3072, 3358, 0), new Position(3072, 3359, 0),
					new Position(3072, 3360, 0), new Position(3072, 3361, 0), new Position(3072, 3362, 0),
					new Position(3072, 3363, 0), new Position(3072, 3364, 0), new Position(3072, 3365, 0),
					new Position(3072, 3366, 0), new Position(3072, 3367, 0), new Position(3072, 3368, 0),
					new Position(3073, 3369, 0), new Position(3073, 3370, 0), new Position(3073, 3371, 0),
					new Position(3073, 3372, 0), new Position(3073, 3373, 0), new Position(3073, 3374, 0),
					new Position(3073, 3375, 0), new Position(3073, 3376, 0), new Position(3073, 3377, 0),
					new Position(3073, 3378, 0), new Position(3073, 3379, 0), new Position(3073, 3380, 0),
					new Position(3073, 3381, 0), new Position(3073, 3382, 0), new Position(3073, 3383, 0),
					new Position(3074, 3384, 0), new Position(3075, 3385, 0), new Position(3076, 3386, 0),
					new Position(3077, 3387, 0), new Position(3078, 3388, 0), new Position(3079, 3389, 0),
					new Position(3080, 3390, 0), new Position(3081, 3391, 0), new Position(3082, 3392, 0),
					new Position(3083, 3393, 0), new Position(3084, 3394, 0), new Position(3085, 3394, 0),
					new Position(3086, 3395, 0), new Position(3086, 3396, 0), new Position(3086, 3397, 0),
					new Position(3087, 3398, 0), new Position(3088, 3399, 0), new Position(3089, 3400, 0),
					new Position(3090, 3401, 0), new Position(3090, 3402, 0), new Position(3090, 3403, 0),
					new Position(3090, 3404, 0), new Position(3091, 3405, 0), new Position(3091, 3406, 0),
					new Position(3091, 3407, 0), new Position(3091, 3408, 0), new Position(3091, 3409, 0),
					new Position(3091, 3410, 0), new Position(3091, 3411, 0), new Position(3092, 3412, 0),
					new Position(3093, 3413, 0), new Position(3094, 3414, 0), new Position(3095, 3415, 0),
					new Position(3096, 3416, 0), new Position(3096, 3417, 0), new Position(3097, 3417, 0),
					new Position(3098, 3418, 0), new Position(3099, 3419, 0), new Position(3100, 3420, 0),
					new Position(3101, 3420, 0), new Position(3102, 3420, 0), new Position(3103, 3420, 0),
					new Position(3104, 3420, 0), new Position(3105, 3420, 0), new Position(3106, 3420, 0),
					new Position(3107, 3420, 0), new Position(3108, 3420, 0), new Position(3109, 3420, 0),
					new Position(3110, 3420, 0), new Position(3111, 3420, 0), new Position(3112, 3420, 0),
					new Position(3113, 3420, 0), new Position(3114, 3421, 0), new Position(3114, 3422, 0),
					new Position(3115, 3423, 0), new Position(3116, 3424, 0), new Position(3117, 3425, 0),
					new Position(3117, 3426, 0), new Position(3117, 3427, 0), new Position(3117, 3428, 0),
					new Position(3117, 3429, 0), new Position(3117, 3430, 0), new Position(3118, 3431, 0),
					new Position(3119, 3432, 0), new Position(3120, 3433, 0), new Position(3120, 3434, 0),
					new Position(3120, 3435, 0), new Position(3121, 3435, 0), new Position(3122, 3436, 0),
					new Position(3123, 3437, 0), new Position(3123, 3438, 0), new Position(3124, 3439, 0),
					new Position(3125, 3440, 0), new Position(3126, 3440, 0), new Position(3127, 3440, 0),
					new Position(3128, 3441, 0), new Position(3129, 3442, 0), new Position(3130, 3443, 0),
					new Position(3131, 3444, 0), new Position(3132, 3445, 0), new Position(3133, 3446, 0),
					new Position(3134, 3447, 0), new Position(3134, 3448, 0), new Position(3134, 3449, 0),
					new Position(3134, 3450, 0), new Position(3135, 3451, 0), new Position(3136, 3452, 0),
					new Position(3137, 3453, 0), new Position(3138, 3454, 0), new Position(3139, 3454, 0),
					new Position(3140, 3454, 0), new Position(3141, 3454, 0), new Position(3142, 3454, 0),
					new Position(3143, 3454, 0), new Position(3144, 3454, 0), new Position(3145, 3454, 0),
					new Position(3146, 3454, 0), new Position(3147, 3454, 0), new Position(3148, 3454, 0),
					new Position(3149, 3455, 0), new Position(3150, 3456, 0), new Position(3151, 3457, 0),
					new Position(3152, 3458, 0), new Position(3153, 3459, 0), new Position(3154, 3459, 0),
					new Position(3155, 3459, 0), new Position(3156, 3459, 0), new Position(3157, 3460, 0),
					new Position(3158, 3461, 0), new Position(3159, 3462, 0), new Position(3160, 3463, 0),
					new Position(3160, 3464, 0), new Position(3160, 3465, 0), new Position(3161, 3465, 0),
					new Position(3162, 3465, 0), new Position(3163, 3466, 0), new Position(3163, 3467, 0),
					new Position(3163, 3468, 0), new Position(3163, 3469, 0), new Position(3163, 3470, 0),
					new Position(3163, 3471, 0), new Position(3163, 3472, 0), new Position(3163, 3473, 0),
					new Position(3163, 3474, 0), new Position(3163, 3475, 0), new Position(3163, 3476, 0),
					new Position(3163, 3477, 0), new Position(3163, 3478, 0), new Position(3163, 3479, 0),
					new Position(3163, 3480, 0), new Position(3163, 3481, 0), new Position(3163, 3482, 0),
					new Position(3163, 3483, 0), new Position(3164, 3484, 0)));
	private static final ArrayList<Position> RIMMONGTON_CLAY_AREA_TO_GE_POSITIONS = new ArrayList<Position>(
			Arrays.asList(new Position(2985, 3240, 0), new Position(2985, 3240, 0), new Position(2985, 3241, 0),
					new Position(2985, 3242, 0), new Position(2985, 3243, 0), new Position(2984, 3244, 0),
					new Position(2983, 3245, 0), new Position(2982, 3246, 0), new Position(2982, 3247, 0),
					new Position(2981, 3248, 0), new Position(2980, 3249, 0), new Position(2979, 3250, 0),
					new Position(2978, 3251, 0), new Position(2978, 3252, 0), new Position(2979, 3253, 0),
					new Position(2980, 3254, 0), new Position(2981, 3255, 0), new Position(2981, 3256, 0),
					new Position(2981, 3257, 0), new Position(2981, 3258, 0), new Position(2981, 3259, 0),
					new Position(2982, 3260, 0), new Position(2982, 3261, 0), new Position(2982, 3262, 0),
					new Position(2983, 3263, 0), new Position(2984, 3263, 0), new Position(2985, 3264, 0),
					new Position(2986, 3265, 0), new Position(2987, 3266, 0), new Position(2988, 3267, 0),
					new Position(2989, 3268, 0), new Position(2989, 3269, 0), new Position(2990, 3270, 0),
					new Position(2991, 3271, 0), new Position(2992, 3272, 0), new Position(2993, 3273, 0),
					new Position(2994, 3274, 0), new Position(2995, 3275, 0), new Position(2996, 3276, 0),
					new Position(2997, 3277, 0), new Position(2998, 3278, 0), new Position(2999, 3279, 0),
					new Position(3000, 3280, 0), new Position(3001, 3281, 0), new Position(3001, 3282, 0),
					new Position(3002, 3283, 0), new Position(3002, 3284, 0), new Position(3003, 3285, 0),
					new Position(3003, 3286, 0), new Position(3003, 3287, 0), new Position(3003, 3288, 0),
					new Position(3003, 3289, 0), new Position(3003, 3290, 0), new Position(3003, 3291, 0),
					new Position(3003, 3292, 0), new Position(3003, 3293, 0), new Position(3003, 3294, 0),
					new Position(3004, 3295, 0), new Position(3005, 3296, 0), new Position(3005, 3297, 0),
					new Position(3005, 3298, 0), new Position(3005, 3299, 0), new Position(3005, 3300, 0),
					new Position(3005, 3301, 0), new Position(3005, 3302, 0), new Position(3005, 3303, 0),
					new Position(3005, 3304, 0), new Position(3005, 3305, 0), new Position(3005, 3306, 0),
					new Position(3005, 3307, 0), new Position(3006, 3308, 0), new Position(3007, 3309, 0),
					new Position(3008, 3310, 0), new Position(3009, 3311, 0), new Position(3010, 3312, 0),
					new Position(3011, 3313, 0), new Position(3012, 3313, 0), new Position(3013, 3314, 0),
					new Position(3014, 3314, 0), new Position(3015, 3314, 0), new Position(3016, 3314, 0),
					new Position(3017, 3314, 0), new Position(3018, 3314, 0), new Position(3019, 3314, 0),
					new Position(3020, 3314, 0), new Position(3021, 3314, 0), new Position(3022, 3314, 0),
					new Position(3023, 3314, 0), new Position(3024, 3314, 0), new Position(3025, 3315, 0),
					new Position(3026, 3316, 0), new Position(3027, 3317, 0), new Position(3028, 3317, 0),
					new Position(3029, 3317, 0), new Position(3030, 3318, 0), new Position(3031, 3318, 0),
					new Position(3032, 3318, 0), new Position(3033, 3318, 0), new Position(3034, 3319, 0),
					new Position(3034, 3320, 0), new Position(3035, 3321, 0), new Position(3036, 3321, 0),
					new Position(3037, 3321, 0), new Position(3038, 3321, 0), new Position(3039, 3322, 0),
					new Position(3040, 3323, 0), new Position(3041, 3324, 0), new Position(3042, 3325, 0),
					new Position(3043, 3326, 0), new Position(3044, 3326, 0), new Position(3045, 3326, 0),
					new Position(3046, 3326, 0), new Position(3047, 3326, 0), new Position(3048, 3326, 0),
					new Position(3049, 3326, 0), new Position(3050, 3326, 0), new Position(3051, 3326, 0),
					new Position(3052, 3326, 0), new Position(3053, 3326, 0), new Position(3054, 3326, 0),
					new Position(3055, 3326, 0), new Position(3056, 3327, 0), new Position(3057, 3327, 0),
					new Position(3058, 3327, 0), new Position(3059, 3327, 0), new Position(3060, 3327, 0),
					new Position(3061, 3327, 0), new Position(3061, 3328, 0), new Position(3061, 3329, 0),
					new Position(3061, 3330, 0), new Position(3061, 3331, 0), new Position(3061, 3332, 0),
					new Position(3061, 3333, 0), new Position(3061, 3334, 0), new Position(3061, 3335, 0),
					new Position(3061, 3336, 0), new Position(3061, 3337, 0), new Position(3061, 3338, 0),
					new Position(3061, 3339, 0), new Position(3061, 3340, 0), new Position(3061, 3341, 0),
					new Position(3061, 3342, 0), new Position(3061, 3343, 0), new Position(3061, 3344, 0),
					new Position(3061, 3345, 0), new Position(3061, 3346, 0), new Position(3061, 3347, 0),
					new Position(3061, 3348, 0), new Position(3061, 3349, 0), new Position(3061, 3350, 0),
					new Position(3061, 3351, 0), new Position(3061, 3352, 0), new Position(3061, 3353, 0),
					new Position(3061, 3354, 0), new Position(3061, 3355, 0), new Position(3061, 3356, 0),
					new Position(3061, 3357, 0), new Position(3061, 3358, 0), new Position(3061, 3359, 0),
					new Position(3061, 3360, 0), new Position(3061, 3361, 0), new Position(3061, 3362, 0),
					new Position(3062, 3363, 0), new Position(3063, 3364, 0), new Position(3064, 3365, 0),
					new Position(3065, 3366, 0), new Position(3066, 3367, 0), new Position(3066, 3368, 0),
					new Position(3066, 3369, 0), new Position(3066, 3370, 0), new Position(3066, 3371, 0),
					new Position(3066, 3372, 0), new Position(3066, 3373, 0), new Position(3066, 3374, 0),
					new Position(3067, 3375, 0), new Position(3068, 3376, 0), new Position(3069, 3377, 0),
					new Position(3070, 3378, 0), new Position(3071, 3379, 0), new Position(3072, 3380, 0),
					new Position(3073, 3381, 0), new Position(3073, 3382, 0), new Position(3073, 3383, 0),
					new Position(3074, 3384, 0), new Position(3075, 3385, 0), new Position(3076, 3386, 0),
					new Position(3077, 3387, 0), new Position(3078, 3388, 0), new Position(3079, 3389, 0),
					new Position(3080, 3390, 0), new Position(3081, 3391, 0), new Position(3082, 3392, 0),
					new Position(3083, 3393, 0), new Position(3084, 3394, 0), new Position(3085, 3394, 0),
					new Position(3086, 3395, 0), new Position(3086, 3396, 0), new Position(3086, 3397, 0),
					new Position(3087, 3398, 0), new Position(3088, 3399, 0), new Position(3089, 3400, 0),
					new Position(3090, 3401, 0), new Position(3090, 3402, 0), new Position(3090, 3403, 0),
					new Position(3090, 3404, 0), new Position(3091, 3405, 0), new Position(3091, 3406, 0),
					new Position(3091, 3407, 0), new Position(3091, 3408, 0), new Position(3091, 3409, 0),
					new Position(3091, 3410, 0), new Position(3091, 3411, 0), new Position(3092, 3412, 0),
					new Position(3093, 3413, 0), new Position(3094, 3414, 0), new Position(3095, 3415, 0),
					new Position(3096, 3416, 0), new Position(3096, 3417, 0), new Position(3097, 3417, 0),
					new Position(3098, 3418, 0), new Position(3099, 3419, 0), new Position(3100, 3420, 0),
					new Position(3101, 3420, 0), new Position(3102, 3420, 0), new Position(3103, 3420, 0),
					new Position(3104, 3420, 0), new Position(3105, 3420, 0), new Position(3106, 3420, 0),
					new Position(3107, 3420, 0), new Position(3108, 3420, 0), new Position(3109, 3420, 0),
					new Position(3110, 3420, 0), new Position(3111, 3420, 0), new Position(3112, 3420, 0),
					new Position(3113, 3420, 0), new Position(3114, 3421, 0), new Position(3114, 3422, 0),
					new Position(3115, 3423, 0), new Position(3116, 3424, 0), new Position(3117, 3425, 0),
					new Position(3117, 3426, 0), new Position(3117, 3427, 0), new Position(3117, 3428, 0),
					new Position(3117, 3429, 0), new Position(3117, 3430, 0), new Position(3118, 3431, 0),
					new Position(3119, 3432, 0), new Position(3120, 3433, 0), new Position(3120, 3434, 0),
					new Position(3120, 3435, 0), new Position(3121, 3435, 0), new Position(3122, 3436, 0),
					new Position(3123, 3437, 0), new Position(3123, 3438, 0), new Position(3124, 3439, 0),
					new Position(3125, 3440, 0), new Position(3126, 3440, 0), new Position(3127, 3440, 0),
					new Position(3128, 3441, 0), new Position(3129, 3442, 0), new Position(3130, 3443, 0),
					new Position(3131, 3444, 0), new Position(3132, 3445, 0), new Position(3133, 3446, 0),
					new Position(3134, 3447, 0), new Position(3134, 3448, 0), new Position(3134, 3449, 0),
					new Position(3134, 3450, 0), new Position(3135, 3451, 0), new Position(3136, 3452, 0),
					new Position(3137, 3453, 0), new Position(3138, 3454, 0), new Position(3139, 3454, 0),
					new Position(3140, 3454, 0), new Position(3141, 3454, 0), new Position(3142, 3454, 0),
					new Position(3143, 3454, 0), new Position(3144, 3454, 0), new Position(3145, 3454, 0),
					new Position(3146, 3454, 0), new Position(3147, 3454, 0), new Position(3148, 3454, 0),
					new Position(3149, 3455, 0), new Position(3150, 3456, 0), new Position(3151, 3457, 0),
					new Position(3152, 3458, 0), new Position(3153, 3459, 0), new Position(3154, 3459, 0),
					new Position(3155, 3459, 0), new Position(3156, 3459, 0), new Position(3157, 3460, 0),
					new Position(3158, 3461, 0), new Position(3159, 3462, 0), new Position(3160, 3463, 0),
					new Position(3160, 3464, 0), new Position(3160, 3465, 0), new Position(3161, 3465, 0),
					new Position(3162, 3465, 0), new Position(3163, 3466, 0), new Position(3163, 3467, 0),
					new Position(3163, 3468, 0), new Position(3163, 3469, 0), new Position(3163, 3470, 0),
					new Position(3163, 3471, 0), new Position(3163, 3472, 0), new Position(3163, 3473, 0),
					new Position(3163, 3474, 0), new Position(3163, 3475, 0), new Position(3163, 3476, 0),
					new Position(3163, 3477, 0), new Position(3163, 3478, 0), new Position(3163, 3479, 0),
					new Position(3163, 3480, 0), new Position(3163, 3481, 0), new Position(3163, 3482, 0),
					new Position(3163, 3483, 0), new Position(3163, 3484, 0), new Position(3164, 3485, 0)));

	private static final ArrayList<Position> FALADOR_BANK_TO_GE_POSITIONS = new ArrayList<Position>(
			Arrays.asList(new Position(3013, 3355, 0), new Position(3013, 3355, 0), new Position(3013, 3356, 0),
					new Position(3013, 3357, 0), new Position(3013, 3358, 0), new Position(3012, 3359, 0),
					new Position(3011, 3359, 0), new Position(3010, 3359, 0), new Position(3009, 3359, 0),
					new Position(3008, 3359, 0), new Position(3008, 3358, 0), new Position(3008, 3357, 0),
					new Position(3008, 3356, 0), new Position(3008, 3355, 0), new Position(3008, 3354, 0),
					new Position(3008, 3353, 0), new Position(3008, 3352, 0), new Position(3008, 3351, 0),
					new Position(3008, 3350, 0), new Position(3008, 3349, 0), new Position(3008, 3348, 0),
					new Position(3008, 3347, 0), new Position(3008, 3346, 0), new Position(3008, 3345, 0),
					new Position(3008, 3344, 0), new Position(3008, 3343, 0), new Position(3008, 3342, 0),
					new Position(3008, 3341, 0), new Position(3008, 3340, 0), new Position(3008, 3339, 0),
					new Position(3008, 3338, 0), new Position(3008, 3337, 0), new Position(3008, 3336, 0),
					new Position(3008, 3335, 0), new Position(3008, 3334, 0), new Position(3008, 3333, 0),
					new Position(3008, 3332, 0), new Position(3008, 3331, 0), new Position(3008, 3330, 0),
					new Position(3008, 3329, 0), new Position(3008, 3328, 0), new Position(3008, 3327, 0),
					new Position(3008, 3326, 0), new Position(3008, 3325, 0), new Position(3008, 3324, 0),
					new Position(3008, 3323, 0), new Position(3008, 3322, 0), new Position(3009, 3322, 0),
					new Position(3010, 3322, 0), new Position(3011, 3322, 0), new Position(3012, 3322, 0),
					new Position(3013, 3322, 0), new Position(3013, 3323, 0), new Position(3014, 3323, 0),
					new Position(3015, 3323, 0), new Position(3016, 3323, 0), new Position(3017, 3323, 0),
					new Position(3018, 3323, 0), new Position(3019, 3323, 0), new Position(3020, 3323, 0),
					new Position(3021, 3323, 0), new Position(3022, 3323, 0), new Position(3023, 3323, 0),
					new Position(3024, 3324, 0), new Position(3025, 3325, 0), new Position(3026, 3325, 0),
					new Position(3027, 3325, 0), new Position(3028, 3325, 0), new Position(3029, 3325, 0),
					new Position(3030, 3326, 0), new Position(3031, 3326, 0), new Position(3032, 3326, 0),
					new Position(3033, 3326, 0), new Position(3034, 3326, 0), new Position(3035, 3326, 0),
					new Position(3036, 3326, 0), new Position(3037, 3326, 0), new Position(3038, 3326, 0),
					new Position(3039, 3326, 0), new Position(3040, 3326, 0), new Position(3041, 3326, 0),
					new Position(3042, 3326, 0), new Position(3043, 3326, 0), new Position(3044, 3326, 0),
					new Position(3045, 3326, 0), new Position(3046, 3326, 0), new Position(3047, 3326, 0),
					new Position(3048, 3326, 0), new Position(3049, 3326, 0), new Position(3050, 3326, 0),
					new Position(3051, 3326, 0), new Position(3052, 3326, 0), new Position(3053, 3326, 0),
					new Position(3054, 3326, 0), new Position(3055, 3326, 0), new Position(3056, 3327, 0),
					new Position(3057, 3327, 0), new Position(3058, 3327, 0), new Position(3059, 3327, 0),
					new Position(3060, 3327, 0), new Position(3061, 3327, 0), new Position(3061, 3328, 0),
					new Position(3061, 3329, 0), new Position(3061, 3330, 0), new Position(3061, 3331, 0),
					new Position(3061, 3332, 0), new Position(3061, 3333, 0), new Position(3061, 3334, 0),
					new Position(3062, 3335, 0), new Position(3063, 3336, 0), new Position(3064, 3337, 0),
					new Position(3065, 3338, 0), new Position(3066, 3339, 0), new Position(3067, 3340, 0),
					new Position(3067, 3341, 0), new Position(3067, 3342, 0), new Position(3067, 3343, 0),
					new Position(3068, 3344, 0), new Position(3069, 3345, 0), new Position(3070, 3346, 0),
					new Position(3070, 3347, 0), new Position(3070, 3348, 0), new Position(3071, 3349, 0),
					new Position(3071, 3350, 0), new Position(3071, 3351, 0), new Position(3071, 3352, 0),
					new Position(3071, 3353, 0), new Position(3071, 3354, 0), new Position(3071, 3355, 0),
					new Position(3071, 3356, 0), new Position(3071, 3357, 0), new Position(3071, 3358, 0),
					new Position(3071, 3359, 0), new Position(3071, 3360, 0), new Position(3071, 3361, 0),
					new Position(3071, 3362, 0), new Position(3071, 3363, 0), new Position(3071, 3364, 0),
					new Position(3071, 3365, 0), new Position(3071, 3366, 0), new Position(3071, 3367, 0),
					new Position(3072, 3368, 0), new Position(3073, 3369, 0), new Position(3073, 3370, 0),
					new Position(3073, 3371, 0), new Position(3073, 3372, 0), new Position(3073, 3373, 0),
					new Position(3073, 3374, 0), new Position(3073, 3375, 0), new Position(3073, 3376, 0),
					new Position(3073, 3377, 0), new Position(3073, 3378, 0), new Position(3073, 3379, 0),
					new Position(3073, 3380, 0), new Position(3073, 3381, 0), new Position(3073, 3382, 0),
					new Position(3073, 3383, 0), new Position(3074, 3384, 0), new Position(3075, 3385, 0),
					new Position(3076, 3386, 0), new Position(3077, 3387, 0), new Position(3078, 3388, 0),
					new Position(3079, 3389, 0), new Position(3080, 3390, 0), new Position(3081, 3391, 0),
					new Position(3082, 3392, 0), new Position(3083, 3393, 0), new Position(3084, 3394, 0),
					new Position(3085, 3394, 0), new Position(3086, 3395, 0), new Position(3086, 3396, 0),
					new Position(3086, 3397, 0), new Position(3087, 3398, 0), new Position(3088, 3399, 0),
					new Position(3089, 3400, 0), new Position(3090, 3401, 0), new Position(3090, 3402, 0),
					new Position(3090, 3403, 0), new Position(3090, 3404, 0), new Position(3091, 3405, 0),
					new Position(3091, 3406, 0), new Position(3091, 3407, 0), new Position(3091, 3408, 0),
					new Position(3091, 3409, 0), new Position(3091, 3410, 0), new Position(3091, 3411, 0),
					new Position(3092, 3412, 0), new Position(3093, 3413, 0), new Position(3094, 3414, 0),
					new Position(3095, 3415, 0), new Position(3096, 3416, 0), new Position(3096, 3417, 0),
					new Position(3097, 3417, 0), new Position(3098, 3418, 0), new Position(3099, 3419, 0),
					new Position(3100, 3420, 0), new Position(3101, 3420, 0), new Position(3102, 3420, 0),
					new Position(3103, 3420, 0), new Position(3104, 3420, 0), new Position(3105, 3420, 0),
					new Position(3106, 3420, 0), new Position(3107, 3420, 0), new Position(3108, 3420, 0),
					new Position(3109, 3420, 0), new Position(3110, 3420, 0), new Position(3111, 3420, 0),
					new Position(3112, 3420, 0), new Position(3113, 3420, 0), new Position(3114, 3421, 0),
					new Position(3114, 3422, 0), new Position(3115, 3423, 0), new Position(3116, 3424, 0),
					new Position(3117, 3425, 0), new Position(3117, 3426, 0), new Position(3117, 3427, 0),
					new Position(3117, 3428, 0), new Position(3117, 3429, 0), new Position(3117, 3430, 0),
					new Position(3118, 3431, 0), new Position(3119, 3432, 0), new Position(3120, 3433, 0),
					new Position(3120, 3434, 0), new Position(3120, 3435, 0), new Position(3121, 3435, 0),
					new Position(3122, 3436, 0), new Position(3123, 3437, 0), new Position(3123, 3438, 0),
					new Position(3124, 3439, 0), new Position(3125, 3440, 0), new Position(3126, 3440, 0),
					new Position(3127, 3440, 0), new Position(3128, 3441, 0), new Position(3129, 3442, 0),
					new Position(3130, 3443, 0), new Position(3131, 3444, 0), new Position(3132, 3445, 0),
					new Position(3133, 3446, 0), new Position(3134, 3447, 0), new Position(3134, 3448, 0),
					new Position(3134, 3449, 0), new Position(3134, 3450, 0), new Position(3135, 3451, 0),
					new Position(3136, 3452, 0), new Position(3137, 3453, 0), new Position(3138, 3454, 0),
					new Position(3139, 3454, 0), new Position(3140, 3454, 0), new Position(3141, 3454, 0),
					new Position(3142, 3454, 0), new Position(3143, 3454, 0), new Position(3144, 3454, 0),
					new Position(3145, 3454, 0), new Position(3146, 3454, 0), new Position(3147, 3454, 0),
					new Position(3148, 3454, 0), new Position(3149, 3455, 0), new Position(3150, 3456, 0),
					new Position(3151, 3457, 0), new Position(3152, 3458, 0), new Position(3153, 3459, 0),
					new Position(3154, 3459, 0), new Position(3155, 3459, 0), new Position(3156, 3459, 0),
					new Position(3157, 3460, 0), new Position(3158, 3461, 0), new Position(3159, 3462, 0),
					new Position(3160, 3463, 0), new Position(3160, 3464, 0), new Position(3160, 3465, 0),
					new Position(3161, 3465, 0), new Position(3162, 3465, 0), new Position(3163, 3466, 0),
					new Position(3163, 3467, 0), new Position(3163, 3468, 0), new Position(3163, 3469, 0),
					new Position(3163, 3470, 0), new Position(3163, 3471, 0), new Position(3163, 3472, 0),
					new Position(3163, 3473, 0), new Position(3163, 3474, 0), new Position(3163, 3475, 0),
					new Position(3163, 3476, 0), new Position(3163, 3477, 0), new Position(3163, 3478, 0),
					new Position(3163, 3479, 0), new Position(3163, 3480, 0), new Position(3163, 3481, 0),
					new Position(3163, 3482, 0), new Position(3163, 3483, 0), new Position(3163, 3484, 0),
					new Position(3164, 3485, 0)));

	/**
	 * Areas
	 */
	private static final Area FALADOR_BANK_AREA = new Area(new int[][] { { 3009, 3359 }, { 3019, 3359 }, { 3019, 3357 },
			{ 3022, 3357 }, { 3022, 3355 }, { 3009, 3355 } });
	private static final Area RIMMONGTON_CLAY_MINING_AREA = new Area(
			new int[][] { { 2981, 3244 }, { 2991, 3244 }, { 2991, 3235 }, { 2981, 3235 } });
	private static final Area DEPOSIT_RIMMINGTON_AREA = new Area(
			new int[][] { { 3040, 3238 }, { 3053, 3238 }, { 3053, 3233 }, { 3040, 3233 } });
	private static final Area VARROCK_BANK_AREA = new Area(
			new int[][] { { 3180, 3448 }, { 3186, 3448 }, { 3186, 3433 }, { 3180, 3433 } });

	private static void tryToWalkToFinishAreaWithoutWebWalking(MethodProvider api) {
		int tries = 0;
		Area geArea = new Area(new int[][] { { 3160, 3494 }, { 3169, 3494 }, { 3169, 3494 }, { 3169, 3485 },
				{ 3160, 3485 }, { 3160, 3494 } });

		while (!geArea.contains(api.myPlayer()) || tries > 10) {
			api.log("trying to walk to g.e. without webwalking...");
			api.getWalking().walk(geArea);
			api.log("trying to walk to g.e. without webwalking...");
			tries++;
		}
	}

	public static void walk(MethodProvider api, LoginEvent login) {
		Player playerArea = api.myPlayer();
		Area geArea = new Area(new int[][] { { 3160, 3494 }, { 3169, 3494 }, { 3169, 3494 }, { 3169, 3485 },
				{ 3160, 3485 }, { 3160, 3494 } });

		// Not in G.E. area
		if (!geArea.contains(playerArea)) {

			if (geArea.getRandomPosition().getArea(15).contains(api.myPlayer())) {
				tryToWalkToFinishAreaWithoutWebWalking(api);
			}

			api.log("The player has a grand exchange task but isn't there, walking to there");
			if (FALADOR_BANK_AREA.contains(playerArea)) {
				api.getWalking().walkPath(FALADOR_BANK_TO_GE_POSITIONS);
			} else if (RIMMONGTON_CLAY_MINING_AREA.contains(playerArea)) {
				api.getWalking().walkPath(RIMMONGTON_CLAY_AREA_TO_GE_POSITIONS);
			} else if (DEPOSIT_RIMMINGTON_AREA.contains(playerArea)) {
				api.getWalking().walkPath(RIMMONGTON_DEPOSIT_AREA_TO_GE_POSITIONS);
			} else if (VARROCK_BANK_AREA.contains(playerArea)) {
				api.getWalking().walkPath(VARROCK_WEST_BANK_TO_GE_POSITIONS);
			} else {
				api.log("Couldn't find a path to the g.e, so was forced to web walk");
				api.getWalking().webWalk(geArea);
				DatabaseUtilities.insertLoggingMessage(api, login, "WEB_WALKING",
						"COULDN'T FIND PATH TO G.E. SO WEBWALKED " + api.myPosition());
			}
		}

	}
}
