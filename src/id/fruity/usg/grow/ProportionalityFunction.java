package id.fruity.usg.grow;

public class ProportionalityFunction {
	public static final int DEFAULT_START_WEEK = 24;
	public static final int DEFAULT_LAST_WEEK = 42;
	public static final float DEFAULT_CENTILE_WEIGHT = 0.14f;

	public static double[] getIdealGWSeries(float tow) {
		return getGWSeries(tow, DEFAULT_START_WEEK, DEFAULT_LAST_WEEK, 0);
	}

	public static double[] getIdealGWSeries(float tow, int startWeek,
			int lastWeek) {
		return getGWSeries(tow, startWeek, lastWeek, 0);
	}

	public static double[] getLowerCentileGWSeries(float tow) {
		return getGWSeries(tow, DEFAULT_START_WEEK, DEFAULT_LAST_WEEK,
				-DEFAULT_CENTILE_WEIGHT);
	}

	public static double[] getLowerCentileGWSeries(float tow, int startWeek,
			int lastWeek, float centileWeight) {
		return getGWSeries(tow, startWeek, lastWeek, -centileWeight);
	}

	public static double[] getUpperCentileGWSeries(float tow) {
		return getGWSeries(tow, DEFAULT_START_WEEK, DEFAULT_LAST_WEEK,
				DEFAULT_CENTILE_WEIGHT);
	}
	
	public static double[] getUpperCentileGWSeries(float tow, int startWeek,
			int lastWeek, float centileWeight) {
		return getGWSeries(tow, startWeek, lastWeek, centileWeight);
	}

	public static double[] getGWSeries(float tow, int startWeek, int lastWeek,
			float centileWeight) {
		double[] res = new double[lastWeek - startWeek + 1];
		for (int counter = 0, i = startWeek; i <= lastWeek; i++, counter++) {
			res[counter] = (float) ((299.1 - (31.85 * i) + (1.094 * Math.pow(i, 2)) - (0.01055 * Math
					.pow(i, 3))) * 0.01 * tow * (float) (1 + centileWeight));
		}
		return res;
	}
	
	public static float idealTow(float tow, float age){
		return (float) ((299.1 - (31.85 * age) + (1.094 * Math.pow(age, 2)) - (0.01055 * Math
				.pow(age, 3))) * 0.01 * tow * (float) (1 + 0));
	}
	
	public static float lowerTow(float tow, float age){
		return (float) ((299.1 - (31.85 * age) + (1.094 * Math.pow(age, 2)) - (0.01055 * Math
				.pow(age, 3))) * 0.01 * tow * (float) (1  -DEFAULT_CENTILE_WEIGHT));
	}
	
	public static float upperTow(float tow, float age){
		return (float) ((299.1 - (31.85 * age) + (1.094 * Math.pow(age, 2)) - (0.01055 * Math
				.pow(age, 3))) * 0.01 * tow * (float) (1  +DEFAULT_CENTILE_WEIGHT));
	}
}
