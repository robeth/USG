package id.fruity.usg.grow;

public class GestationalAge {
	/**
	 * Approximate gestational age using hc
	 * @param hc head circumferenc in mm
	 * @return gestational age in weeks
	 */
	public static float approximateGA1(float hc){
		return (float) (8.96 + 0.54 * hc + 0.0003 * Math.pow(hc, 3));
	}
	
	/**
	 * Approximate gestational age using bpd
	 * @param bpd biparietal diameter in cm
	 * @return gestational age in days
	 */
	public static float approximateGA2(float bpd){
		return (float)(58.65 + 1.0693 * bpd + 0.001382 * Math.pow(bpd,2));
	}
}
