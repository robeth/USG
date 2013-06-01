package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.ServeTable;
import android.content.ContentValues;

public class Serve extends USGTableEntry {
	@Expose
	@SerializedName("local_clinic_id")
	private int idPuskesmas;
	@Expose
	@SerializedName("local_ktp")
	private String idPasien;
	@Expose
	@SerializedName("clinic_id")
	private int serverId;
	@Expose
	@SerializedName("ktp")
	private String serverId2;
	
	public Serve(int serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, int idPuskesmas,
			String idPasien, String serverId2) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idPuskesmas = idPuskesmas;
		this.idPasien = idPasien;
		this.serverId = serverId;
		this.serverId2 = serverId2;
	}
	
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(ServeTable.C_ID_PUSKESMAS, idPuskesmas);
		cv.put(ServeTable.C_ID_PASIEN, idPasien);
		cv.put(ServeTable.C_SERVER_ID2_PASIEN, serverId2);
		
		cv.put(ServeTable.C_DIRTY, isDirty()?1:0);
		cv.put(ServeTable.C_ISACTIVE, isActive()?1:0);
		cv.put(ServeTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(ServeTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(ServeTable.C_SERVER_ID, serverId);
		return cv;
	}

	public int getIdPuskesmas() {
		return idPuskesmas;
	}

	public void setIdPuskesmas(int idPuskesmas) {
		this.idPuskesmas = idPuskesmas;
	}
	
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idPuskesmas,""+idPasien};
	}

	@Override
	public String toString() {
		return "Serve [idPuskesmas=" + idPuskesmas + ", idPasien=" + idPasien
				+ ", serverId=" + serverId + ", serverId2=" + serverId2 + "]";
	}
	
	public void onServerAdd(){
		idPuskesmas = serverId;
		idPasien = serverId2;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idPuskesmas;
		serverId2 = idPasien;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Serve other){
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
	
}
