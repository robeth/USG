package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.OfficerTable;
import android.content.ContentValues;

public class Officer extends USGTableEntry {
	@Expose
	@SerializedName("local_ktp")
	private String idUser;
	@Expose
	@SerializedName("officer_id")
	private int idPetugas;
	@Expose
	@SerializedName("local_clinic_id")
	private int idPuskesmas;
	@Expose
	@SerializedName("ktp")
	private String serverId;
	@Expose
	@SerializedName("clinic_id")
	private int serverId2;
	
	public Officer(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idUser, int idPetugas,
			int idPuskesmas, int serverId2) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idUser = idUser;
		this.idPetugas = idPetugas;
		this.idPuskesmas = idPuskesmas;
		this.serverId = serverId;
		this.serverId2 = serverId2;
	}
	
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(OfficerTable.C_ID_USER, idUser);
		cv.put(OfficerTable.C_ID_PETUGAS, idPetugas);
		cv.put(OfficerTable.C_ID_PUSKEMAS, idPuskesmas);
		
		cv.put(OfficerTable.C_DIRTY, isDirty()?1:0);
		cv.put(OfficerTable.C_ISACTIVE, isActive()?1:0);
		cv.put(OfficerTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(OfficerTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(OfficerTable.C_SERVER_ID, serverId);
		cv.put(OfficerTable.C_SERVER_ID2, serverId2);
		return cv;
	}

	public int getIdPetugas() {
		return idPetugas;
	}
	public void setIdPetugas(int idPetugas) {
		this.idPetugas = idPetugas;
	}
	public int getIdPuskesmas() {
		return idPuskesmas;
	}
	public void setIdPuskesmas(int idPuskesmas) {
		this.idPuskesmas = idPuskesmas;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idUser};
	}

	@Override
	public String toString() {
		return "Officer [idUser=" + idUser + ", idPetugas=" + idPetugas
				+ ", idPuskesmas=" + idPuskesmas + ", serverId=" + serverId
				+ ", serverId2=" + serverId2 + "]";
	}
	
	public void onServerAdd(){
		idUser = serverId;
		idPuskesmas = serverId2;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idUser;
		serverId2 = idPuskesmas;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Officer other){
		this.idPetugas = other.getIdPetugas();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
