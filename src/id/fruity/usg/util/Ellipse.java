package id.fruity.usg.util;

public class Ellipse {
	public static float keliling(float a, float b){
		int digits = 5;
		float x = Math.max(a, b);
		float y = Math.min(a, b);
		
		float tolerance = (float)Math.sqrt(Math.pow(0.5,digits));
		if(digits * y < tolerance * x){
			return 4*x;
		}
		float s = 0;
		float m = 1;
		
		while(x - y > tolerance * y){
			x = 0.5f * (x+y);
			y = (float)Math.sqrt(x * y);
			m *= 2;
			s += m * Math.pow(x-y, 2);
		}
		return (float)(Math.PI * (Math.pow(a+b, 2) - s) / (x+y));
	}
	
	public static float area(float a, float b){
		return -1;
	}
}
