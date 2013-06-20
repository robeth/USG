package id.fruity.usg.experiment;

public class USGConstant {
	private int imageId;
	private String imageFilename;
	private float hc;
	private float bpd;
	private float x;
	private float y;
	private float a;
	private float b;
	private float tetha;
	private float ratio;
	public USGConstant(int imageId, String imageFilename, float hc, float bpd,
			float x, float y, float a, float b, float tetha, float ratio) {
		super();
		this.imageId = imageId;
		this.imageFilename = imageFilename;
		this.hc = hc;
		this.bpd = bpd;
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.tetha = tetha;
		this.ratio = ratio;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public float getHc() {
		return hc;
	}
	public void setHc(float hc) {
		this.hc = hc;
	}
	public float getBpd() {
		return bpd;
	}
	public void setBpd(float bpd) {
		this.bpd = bpd;
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
	public float getA() {
		return a * 0.5f;
	}
	public void setA(float a) {
		this.a = a;
	}
	public float getB() {
		return b* 0.5f;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getTetha() {
		return tetha * -1;
	}
	public void setTetha(float tetha) {
		this.tetha = tetha;
	}
	public float getRatio() {
		return ratio;
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}
	
}
