package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.PhotoTable;
import android.content.ContentValues;

public class Photo extends USGTableEntry {
	@Expose
	@SerializedName("local_photo_number")
	private int noPhoto;
	@Expose
	@SerializedName("analyze_timestamp")
	private long autoDateLong;
	@Expose
	@SerializedName("filename")
	private String filename;
	@Expose
	@SerializedName("x")
	private float x;
	@Expose
	@SerializedName("y")
	private float y;
	@Expose
	@SerializedName("a")
	private float a;
	@Expose
	@SerializedName("b")
	private float b;
	@Expose
	@SerializedName("tetha")
	private float theta;
	@Expose
	@SerializedName("scale")
	private float scale;
	@Expose
	@SerializedName("method")
	private String method;
	@Expose
	@SerializedName("local_ktp")
	private String idPasien;
	@Expose
	@SerializedName("local_pregnancy_number")
	private int noKandungan;
	@Expose
	@SerializedName("local_officer_ktp")
	private String idPetugas;
	@Expose
	@SerializedName("photo_number")
	private int serverId;
	@Expose
	@SerializedName("ktp")
	private String serverId2;
	@Expose
	@SerializedName("pregnancy_number")
	private int serverId3;
	@Expose
	@SerializedName("officer_ktp")
	private String serverId4;
	
	public Photo(int serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, int noPhoto, long sDate, String filename,
			float x, float y, float a, float b, float theta, float scale, String method,
			String idPasien, int noKandungan, String idPetugas, String serverId2,
			int serverId3, String serverId4) {
		super(isDirty, isActive, modifyDate, createDate);
		this.noPhoto = noPhoto;
		this.autoDateLong = sDate;
		this.filename = filename;
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.theta = theta;
		this.scale = scale;
		this.method = method;
		this.idPasien = idPasien;
		this.noKandungan = noKandungan;
		this.idPetugas = idPetugas;
		this.serverId = serverId;
		this.serverId2 = serverId2;
		this.serverId3 = serverId3;
		this.serverId4 = serverId4;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(PhotoTable.C_ID, noPhoto);
		cv.put(PhotoTable.C_AUTO_DATE, autoDateLong);
		cv.put(PhotoTable.C_FILENAME, filename);
		cv.put(PhotoTable.C_X, ""+x);
		cv.put(PhotoTable.C_Y, ""+y);
		cv.put(PhotoTable.C_A, ""+a);
		cv.put(PhotoTable.C_B, ""+b);
		cv.put(PhotoTable.C_THETA, ""+theta);
		cv.put(PhotoTable.C_SCALE, ""+scale);
		cv.put(PhotoTable.C_METHOD, method);
		cv.put(PhotoTable.C_ID_PASIEN, idPasien);
		cv.put(PhotoTable.C_ID_KANDUNGAN, noKandungan);
		cv.put(PhotoTable.C_ID_PETUGAS, idPetugas);
		cv.put(PhotoTable.C_SERVER_ID2_PASIEN, serverId2);
		cv.put(PhotoTable.C_SERVER_ID3_KANDUNGAN, serverId3);
		cv.put(PhotoTable.C_SERVER_ID4_OFFICER, serverId4);
		
		cv.put(PhotoTable.C_DIRTY, isDirty()?1:0);
		cv.put(PhotoTable.C_ISACTIVE, isActive()?1:0);
		cv.put(PhotoTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(PhotoTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(PhotoTable.C_SERVER_ID, serverId);
		return cv;
	}
	public int getNoPhoto() {
		return noPhoto;
	}
	
	public long getAutoDateString() {
		return autoDateLong;
	}
	public void setAutoDateString(long autoDateLong) {
		this.autoDateLong = autoDateLong;
	}
	public void setNoPhoto(int noPhoto) {
		this.noPhoto = noPhoto;
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getA() {
		return a;
	}
	public void setA(float a) {
		this.a = a;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public float getTheta() {
		return theta;
	}
	public void setTheta(float theta) {
		this.theta = theta;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public int getNoKandungan() {
		return noKandungan;
	}
	public void setNoKandungan(int noKandungan) {
		this.noKandungan = noKandungan;
	}

	public long getAutoDateLong() {
		return autoDateLong;
	}
	public void setAutoDateLong(long autoDateLong) {
		this.autoDateLong = autoDateLong;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+noPhoto,""+idPasien, ""+noKandungan};
	}
	@Override
	public String toString() {
		return "Photo [noPhoto=" + noPhoto + ", autoDateLong=" + autoDateLong
				+ ", filename=" + filename + ", x=" + x + ", y=" + y + ", a="
				+ a + ", b=" + b + ", theta=" + theta + ", scale=" + scale
				+ ", method=" + method + ", idPasien=" + idPasien
				+ ", noKandungan=" + noKandungan + ", idPetugas=" + idPetugas
				+ ", serverId=" + serverId + ", serverId2=" + serverId2
				+ ", serverId3=" + serverId3 + ", serverId4=" + serverId4 + "]";
	}
	
	
	
	public String getIdPasien() {
		return idPasien;
	}
	public void setIdPasien(String idPasien) {
		this.idPasien = idPasien;
	}
	public void onServerAdd(int photoNumber){
		noPhoto = photoNumber;
		idPasien = serverId2;
		noKandungan = serverId3;
		idPetugas = serverId4;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId2 = idPasien;
		serverId3 = noKandungan;
		serverId4 = idPetugas;
		setDirty(false);
	}
	
	public void onSentSuccessCont(int serverPhotoNumber){
		serverId = serverPhotoNumber;
	}
	
	public void onUpdatedByServer(Photo other){
		this.autoDateLong = other.getAutoDateLong();
		this.x = other.getX();
		this.y = other.getY();
		this.a = other.getA();
		this.b = other.getB();
		this.theta = other.getTheta();
		this.scale = other.getScale();
		this.method = other.getMethod();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
}
