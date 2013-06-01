package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.PregnancyTable;
import android.content.ContentValues;

public class Pregnancy extends USGTableEntry {
	@Expose
	@SerializedName("local_pregnancy_number")
	private int noKandungan;
	@Expose
	@SerializedName("local_patient_id")
	private String idPasien;
	@Expose
	@SerializedName("is_finish")
	private boolean isFinish;
	@Expose
	@SerializedName("pregnancy_number")
	private int serverId;
	@Expose
	@SerializedName("patient_id")
	private String serverId2;
	public Pregnancy(int serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, int noKandungan,
			String idPasien, boolean isFinish, String serverId2) {
		super(isDirty, isActive, modifyDate, createDate);
		this.noKandungan = noKandungan;
		this.idPasien = idPasien;
		this.isFinish = isFinish;
		this.serverId = serverId;
		this.serverId2 = serverId2;
	}
	
	public int getNoKandungan() {
		return noKandungan;
	}
	public void setNoKandungan(int noKandungan) {
		this.noKandungan = noKandungan;
	}

	public boolean isFinish() {
		return isFinish;
	}
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(PregnancyTable.C_ID, noKandungan);
		cv.put(PregnancyTable.C_ID_PASIEN, idPasien);
		cv.put(PregnancyTable.C_ISFINISH, isFinish);
		cv.put(PregnancyTable.C_SERVER_ID2_PASIEN, serverId2);
		
		cv.put(PregnancyTable.C_DIRTY, isDirty()?1:0);
		cv.put(PregnancyTable.C_ISACTIVE, isActive()?1:0);
		cv.put(PregnancyTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(PregnancyTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(PregnancyTable.C_SERVER_ID, serverId);
		return cv;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+noKandungan,""+idPasien};
	}

	@Override
	public String toString() {
		return "Pregnancy [noKandungan=" + noKandungan + ", idPasien="
				+ idPasien + ", isFinish=" + isFinish + ", serverId="
				+ serverId + ", serverId2=" + serverId2 + "]";
	}
	
	public void onServerAdd(){
		noKandungan = serverId;
		idPasien = serverId2;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = noKandungan;
		serverId2 = idPasien;
		setDirty(false);
	}
	
	public void onUpdatedByServer(Pregnancy other){
		this.isFinish = other.isFinish();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
	
}
