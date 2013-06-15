package id.fruity.usg.grow;

public class TOWData {
	private TOWCoefficient coefficient;
	private float maternalHeight;
	private float maternalWeight;
	private int parity;
	private int sex;
	public static final int UNKNOWN = 0,
			MALE = 1,
			FEMALE = 2;
	public TOWData(TOWCoefficient coefficient, float maternalHeight,
			float maternalWeight, int parity, int sex) {
		super();
		this.coefficient = coefficient;
		this.maternalHeight = maternalHeight;
		this.maternalWeight = maternalWeight;
		this.parity = parity;
		this.sex = sex;
	}
	public TOWCoefficient getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(TOWCoefficient coefficient) {
		this.coefficient = coefficient;
	}
	public float getMaternalHeight() {
		return maternalHeight;
	}
	public void setMaternalHeight(float maternalHeight) {
		this.maternalHeight = maternalHeight;
	}
	public float getMaternalWeight() {
		return maternalWeight;
	}
	public void setMaternalWeight(float maternalWeight) {
		this.maternalWeight = maternalWeight;
	}
	public int getParity() {
		return parity;
	}
	public void setParity(int parity) {
		this.parity = parity;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public float getConstantContribution(){
		return coefficient.getConstant();
	}
	public float getMaternalHeightContribution(){
		return (maternalHeight - coefficient.getMedianMaternalHeight()) * coefficient.getAoMaternalHeight();
	}
	public float getMaternalWeightContribution(){
		return (maternalWeight - coefficient.getMedianMaternalWeight()) * coefficient.getAoMaternalWeight();
	}
	public float getEthnicContribution(){
		return coefficient.getSeEthnic();
	}
	public float getParityContribution(){
		float res = 0;
		switch (parity){
			case 0 : res = coefficient.getParity0(); break;
			case 1 : res = coefficient.getParity1(); break;
			case 2 : res = coefficient.getParity2(); break;
			default : res = coefficient.getParityMore(); break;
		}
		return res;
	}
	public float getSexContribution(){
		float res = 0;
		switch(sex){
			case MALE : res = coefficient.getMale(); break;
			case FEMALE : res = coefficient.getFemale(); break;
		}
		return res;
	}
}
