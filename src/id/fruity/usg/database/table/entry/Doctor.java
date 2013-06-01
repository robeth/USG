package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.DoctorTable;
import android.content.ContentValues;

public class Doctor extends USGTableEntry {
	@Expose
	@SerializedName("local_ktp")
	private String idUser;
	@Expose
	@SerializedName("doctor_id")
	private int idDokter;
	@Expose
	@SerializedName("ktp")
	private String serverId;
	
	public Doctor(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idUser, int idDokter) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idUser = idUser;
		this.idDokter = idDokter;
		this.serverId = serverId;
	}

	public int getIdDokter() {
		return idDokter;
	}
	public void setIdDokter(int idDokter) {
		this.idDokter = idDokter;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(DoctorTable.C_ID_DOKTER, idDokter);
		cv.put(DoctorTable.C_ID_USER, idUser);
		
		cv.put(DoctorTable.C_DIRTY, isDirty()?1:0);
		cv.put(DoctorTable.C_ISACTIVE, isActive()?1:0);
		cv.put(DoctorTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(DoctorTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(DoctorTable.C_SERVER_ID, serverId);
		return cv;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idUser};
	}

	@Override
	public String toString() {
		return "Doctor [idUser=" + idUser + ", idDokter=" + idDokter
				+ ", serverId=" + serverId + "]";
	}
	
	public void onServerAdd(){
		idUser = serverId;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idUser;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Doctor other){
		this.idDokter = other.getIdDokter();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
