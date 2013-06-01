package id.fruity.usg.model;

import java.util.ArrayList;
import android.database.Cursor;

public class PasienModel extends USGModel<PasienModel> {
	private int patientId;
	private String name;
	private String address;
	private String phone;
	private long birthdate;
	private String filename;
	private String description;
	
	public PasienModel(){}
	public PasienModel(int patientId, String name,
			String address, String phone, long birthdate, 
			String filename, String description) {
		super();
		this.patientId = patientId;
		this.birthdate = birthdate;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.description = description;
		this.filename = filename;
	}

	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
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
	public long getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(long birthdate) {
		this.birthdate = birthdate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public ArrayList<PasienModel> getItems(Cursor c) {
		ArrayList<PasienModel> pms = new ArrayList<PasienModel>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			PasienModel pm = cursorToItem(c);
			pms.add(pm);
			c.moveToNext();
		}
		c.close();
		return pms;
	}

	@Override
	public PasienModel cursorToItem(Cursor c) {
		return new PasienModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getString(5), c.getString(6));
	}

}
