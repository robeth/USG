package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.WorksOnTable;
import android.content.ContentValues;

public class WorksOn extends USGTableEntry{
	@Expose
	@SerializedName("local_ktp")
	private String idDokter;
	@Expose
	@SerializedName("local_clinic_id")
	private int idPuskesmas;
	@Expose
	@SerializedName("ktp")
	private String serverId;
	@Expose
	@SerializedName("clinic_id")
	private int serverId2;
	
	public WorksOn(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idDokter,
			int idPuskesmas, int serverId2) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idDokter = idDokter;
		this.idPuskesmas = idPuskesmas;
		this.serverId = serverId;
		this.serverId2 = serverId2;
	}


	public int getIdPuskesmas() {
		return idPuskesmas;
	}

	public void setIdPuskesmas(int idPuskesmas) {
		this.idPuskesmas = idPuskesmas;
	}


	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(WorksOnTable.C_ID_DOKTER, idDokter);
		cv.put(WorksOnTable.C_ID_PUSKESMAS, idPuskesmas);
		cv.put(WorksOnTable.C_SERVER_ID2_PUSKESMAS, serverId2);
		
		cv.put(WorksOnTable.C_DIRTY, isDirty()?1:0);
		cv.put(WorksOnTable.C_ISACTIVE, isActive()?1:0);
		cv.put(WorksOnTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(WorksOnTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(WorksOnTable.C_SERVER_ID, serverId);
		return cv;
	}

	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idDokter, ""+idPuskesmas};
	}


	@Override
	public String toString() {
		return "WorksOn [idDokter=" + idDokter + ", idPuskesmas=" + idPuskesmas
				+ ", serverId=" + serverId + ", serverId2=" + serverId2 + "]";
	}
	
	public void onServerAdd(){
		idDokter = serverId;
		idPuskesmas = serverId2;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idDokter;
		serverId2 = idPuskesmas;
		setDirty(false);
	}
	
	public void onUpdatedByServer(WorksOn other){
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
