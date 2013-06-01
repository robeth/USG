package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.PatientTable;
import android.content.ContentValues;


public class Patient extends USGTableEntry{
	@Expose
	@SerializedName("local_ktp")
	private String idPasien;
	@Expose
	@SerializedName("name")
	private String name;
	@Expose
	@SerializedName("address")
	private String address;
	@Expose
	@SerializedName("phone")
	private String phone;
	@Expose
	@SerializedName("birthdate")
	private long birthdate;
	@Expose
	@SerializedName("filename")
	private String filename;
	@Expose
	@SerializedName("description")
	private String description;
	@Expose
	@SerializedName("ktp")
	private String serverId;
	public Patient(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idPasien, String name,
			String address, String phone, long birthdate, String filename, String description) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idPasien = idPasien;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.birthdate = birthdate;
		this.filename = filename;
		this.description = description;
		this.serverId = serverId;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(PatientTable.C_ID, idPasien);
		cv.put(PatientTable.C_NAME, name);
		cv.put(PatientTable.C_ADDRESS, address);
		cv.put(PatientTable.C_PHONE, phone);
		cv.put(PatientTable.C_BIRTHDATE, birthdate);
		cv.put(PatientTable.C_DESCRIPTION, description);
		cv.put(PatientTable.C_FILENAME, filename);
		
		cv.put(PatientTable.C_DIRTY, isDirty()?1:0);
		cv.put(PatientTable.C_ISACTIVE, isActive()?1:0);
		cv.put(PatientTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(PatientTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(PatientTable.C_SERVER_ID, serverId);
		return cv;
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
	public String getDescription() {
		return description;
	}
	public long getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(long birthdate) {
		this.birthdate = birthdate;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idPasien};
	}
	@Override
	public String toString() {
		return "Patient [idPasien=" + idPasien + ", name=" + name
				+ ", address=" + address + ", phone=" + phone + ", birthdate="
				+ birthdate + ", filename=" + filename + ", description="
				+ description + ", serverId=" + serverId + "]";
	}
	
	public void onServerAdd(){
		idPasien = serverId;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idPasien;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Patient other){
		this.name = other.getName();
		this.address = other.getAddress();
		this.phone = other.getPhone();
		this.birthdate = other.getBirthdate();
		this.description = other.getDescription();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
