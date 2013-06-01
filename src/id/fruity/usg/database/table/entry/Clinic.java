package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.ClinicTable;
import android.content.ContentValues;

public class Clinic extends USGTableEntry {
	@Expose
	@SerializedName("local_clinic_id")
	private int clinicId;
	@Expose
	@SerializedName("name")
	private String name;
	@Expose
	@SerializedName("address")
	private String address;
	@Expose
	@SerializedName("city")
	private String city;
	@Expose
	@SerializedName("province")
	private String province;
	@Expose
	@SerializedName("phone")
	private String phone;
	@Expose
	@SerializedName("description")
	private String description;
	@Expose
	@SerializedName("clinic_id")
	private int serverId;
	
	public Clinic(int serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, int idPuskesmas, String name,
			String address, String city, String province, String phone,
			String description) {
		super(isDirty, isActive, modifyDate, createDate);
		this.clinicId = idPuskesmas;
		this.name = name;
		this.address = address;
		this.city = city;
		this.province = province;
		this.phone = phone;
		this.description = description;
		this.serverId = serverId;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(ClinicTable.C_ID, clinicId);
		cv.put(ClinicTable.C_NAME, name);
		cv.put(ClinicTable.C_ROAD_ADDRESS, address);
		cv.put(ClinicTable.C_CITY, city);
		cv.put(ClinicTable.C_PROVINCE, province);
		cv.put(ClinicTable.C_PHONE, phone);
		cv.put(ClinicTable.C_DESCRIPTION, description);
		
		cv.put(ClinicTable.C_DIRTY, isDirty()?1:0);
		cv.put(ClinicTable.C_ISACTIVE, isActive()?1:0);
		cv.put(ClinicTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(ClinicTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(ClinicTable.C_SERVER_ID, serverId);
		return cv;
	}
	public int getIdPuskesmas() {
		return clinicId;
	}
	public void setIdPuskesmas(int idPuskesmas) {
		this.clinicId = idPuskesmas;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+clinicId};
	}
	@Override
	public String toString() {
		return "Clinic [clinicId=" + clinicId + ", name=" + name + ", address="
				+ address + ", city=" + city + ", province=" + province
				+ ", phone=" + phone + ", description=" + description
				+ ", serverId=" + serverId + "]";
	}

	public void onServerAdd() {
		clinicId = serverId;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = clinicId;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Clinic other){
		this.name = other.getName();
		this.address = other.getAddress();
		this.city = other.getCity();
		this.province = other.getProvince();
		this.phone = other.getPhone();
		this.description = other.getDescription();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
