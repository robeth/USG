package id.fruity.usg;

public class PatientInfo {
	private int photoId;
	private String name;
	private String address;
	private String phone;
	private String birthDate;
	private String info;
	private String lastCheck;
	private String patientId;
	private int numberOfUSG;
	
	public PatientInfo(String patientId, int photoId, String name, String address, String phone,
			String birthDate, String info, String lastCheck, int numberOfUSG) {
		super();
		this.patientId = patientId;
		this.photoId = photoId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.birthDate = birthDate;
		this.info = info;
		this.lastCheck = lastCheck;
		this.numberOfUSG = numberOfUSG;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(String lastCheck) {
		this.lastCheck = lastCheck;
	}

	public int getNumberOfUSG() {
		return numberOfUSG;
	}

	public void setNumberOfUSG(int numberOfUSG) {
		this.numberOfUSG = numberOfUSG;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	
	
}
