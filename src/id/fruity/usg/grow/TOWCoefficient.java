package id.fruity.usg.grow;

public class TOWCoefficient {
	private float constant;
	private float medianMaternalHeight, aoMaternalHeight;
	private float medianMaternalWeight, aoMaternalWeight;
	private float seEthnic;
	private float parity0, parity1, parity2, parityMore;
	private float male, female;
	private float standardDeviation;
	private float mean;
	private float cv;
	private float centile;
	public static final TOWCoefficient UK = new TOWCoefficient(3454.4f, 163,
			8.427f, 64, 7.619f, 71.5f, 0, 111, 154.8f, 151.3f, 52.6f, -52.6f,
			389, 3455, 0.11f, 0.14f);

	public TOWCoefficient(float constant, float medianMaternalHeight,
			float aoMaternalHeight, float medianMaternalWeight,
			float aoMaternalWeight, float seEthnic, float parity0,
			float parity1, float parity2, float parityMore, float male,
			float female, float standardDeviation, float mean, float cv,
			float centile) {
		super();
		this.constant = constant;
		this.medianMaternalHeight = medianMaternalHeight;
		this.aoMaternalHeight = aoMaternalHeight;
		this.medianMaternalWeight = medianMaternalWeight;
		this.aoMaternalWeight = aoMaternalWeight;
		this.seEthnic = seEthnic;
		this.parity0 = parity0;
		this.parity1 = parity1;
		this.parity2 = parity2;
		this.parityMore = parityMore;
		this.male = male;
		this.female = female;
		this.standardDeviation = standardDeviation;
		this.mean = mean;
		this.cv = cv;
		this.centile = centile;
	}

	public float getConstant() {
		return constant;
	}

	public void setConstant(float constant) {
		this.constant = constant;
	}

	public float getMedianMaternalHeight() {
		return medianMaternalHeight;
	}

	public void setMedianMaternalHeight(float medianMaternalHeight) {
		this.medianMaternalHeight = medianMaternalHeight;
	}

	public float getAoMaternalHeight() {
		return aoMaternalHeight;
	}

	public void setAoMaternalHeight(float aoMaternalHeight) {
		this.aoMaternalHeight = aoMaternalHeight;
	}

	public float getMedianMaternalWeight() {
		return medianMaternalWeight;
	}

	public void setMedianMaternalWeight(float medianMaternalWeight) {
		this.medianMaternalWeight = medianMaternalWeight;
	}

	public float getAoMaternalWeight() {
		return aoMaternalWeight;
	}

	public void setAoMaternalWeight(float aoMaternalWeight) {
		this.aoMaternalWeight = aoMaternalWeight;
	}

	public float getSeEthnic() {
		return seEthnic;
	}

	public void setSeEthnic(float seEthnic) {
		this.seEthnic = seEthnic;
	}

	public float getParity0() {
		return parity0;
	}

	public void setParity0(float parity0) {
		this.parity0 = parity0;
	}

	public float getParity1() {
		return parity1;
	}

	public void setParity1(float parity1) {
		this.parity1 = parity1;
	}

	public float getParity2() {
		return parity2;
	}

	public void setParity2(float parity2) {
		this.parity2 = parity2;
	}

	public float getParityMore() {
		return parityMore;
	}

	public void setParityMore(float parityMore) {
		this.parityMore = parityMore;
	}

	public float getMale() {
		return male;
	}

	public void setMale(float male) {
		this.male = male;
	}

	public float getFemale() {
		return female;
	}

	public void setFemale(float female) {
		this.female = female;
	}

	public float getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public float getMean() {
		return mean;
	}

	public void setMean(float mean) {
		this.mean = mean;
	}

	public float getCv() {
		return cv;
	}

	public void setCv(float cv) {
		this.cv = cv;
	}

	public float getCentile() {
		return centile;
	}

	public void setCentile(float centile) {
		this.centile = centile;
	}

}
