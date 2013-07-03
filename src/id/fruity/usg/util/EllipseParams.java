package id.fruity.usg.util;

public class EllipseParams {
	private float a, b, x, y, theta, scale;
	
	

	public EllipseParams(float a, float b, float x, float y, float theta,
			float scale) {
		super();
		this.a = a;
		this.b = b;
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	private float circumference(){
		return Ellipse.keliling(this.a, this.b);
	}
}
