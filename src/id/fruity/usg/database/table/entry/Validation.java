package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.ValidationTable;
import android.content.ContentValues;

public class Validation extends USGTableEntry {
	@Expose
	@SerializedName("local_doctor_ktp")
	private String idDokter;
	@Expose
	@SerializedName("local_photo_number")
	private int noPhoto;
	@Expose
	@SerializedName("local_pregnancy_number")
	private int noKandungan;
	@Expose
	@SerializedName("local_patient_ktp")
	private String noPasien;
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
	@SerializedName("has_seen")
	private boolean hasSeen;
	@Expose
	@SerializedName("doctor_ktp")
	private String serverId;
	@Expose
	@SerializedName("photo_number")
	private int serverId2;
	@Expose
	@SerializedName("pregnancy_number")
	private int serverId3;
	@Expose
	@SerializedName("patient_ktp")
	private String serverId4;
	public Validation(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idDokter, int noPhoto,
			int noKandungan, String noPasien, float x, float y, float a, float b,
			float theta, boolean hasSeen, int serverId2, int serverId3, String serverId4) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idDokter = idDokter;
		this.noPhoto = noPhoto;
		this.noKandungan = noKandungan;
		this.noPasien = noPasien;
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.theta = theta;
		this.hasSeen = hasSeen;
		this.serverId = serverId;
		this.serverId2 = serverId2;
		this.serverId3 = serverId3;
		this.serverId4 = serverId4;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(ValidationTable.C_ID_DOKTER, idDokter);
		cv.put(ValidationTable.C_ID_USGFOTO, noPhoto);
		cv.put(ValidationTable.C_ID_USGKANDUNGAN, noKandungan);
		cv.put(ValidationTable.C_ID_USGPASIEN, noPasien);
		cv.put(ValidationTable.C_X, ""+x);
		cv.put(ValidationTable.C_Y, ""+y);
		cv.put(ValidationTable.C_A, ""+a);
		cv.put(ValidationTable.C_B, ""+b);
		cv.put(ValidationTable.C_THETA, ""+theta);
		cv.put(ValidationTable.C_HASSEEN, hasSeen? 1 : 0);
		cv.put(ValidationTable.C_SERVER_ID2_FOTO, serverId2);
		cv.put(ValidationTable.C_SERVER_ID3_KANDUNGAN, serverId3);
		cv.put(ValidationTable.C_SERVER_ID4_PASIEN, serverId4);
		
		cv.put(ValidationTable.C_DIRTY, isDirty()? 1:0);
		cv.put(ValidationTable.C_ISACTIVE, isActive()? 1:0);
		cv.put(ValidationTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(ValidationTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(ValidationTable.C_SERVER_ID, serverId);
		return cv;
	}

	public int getNoPhoto() {
		return noPhoto;
	}
	public void setNoPhoto(int noPhoto) {
		this.noPhoto = noPhoto;
	}
	public int getNoKandungan() {
		return noKandungan;
	}
	public void setNoKandungan(int noKandungan) {
		this.noKandungan = noKandungan;
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
	public boolean isHasSeen() {
		return hasSeen;
	}
	public void setHasSeen(boolean hasSeen) {
		this.hasSeen = hasSeen;
	}
	

	
	public String getIdDokter() {
		return idDokter;
	}
	public void setIdDokter(String idDokter) {
		this.idDokter = idDokter;
	}
	public String getNoPasien() {
		return noPasien;
	}
	public void setNoPasien(String noPasien) {
		this.noPasien = noPasien;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getServerId2() {
		return serverId2;
	}
	public void setServerId2(int serverId2) {
		this.serverId2 = serverId2;
	}
	public int getServerId3() {
		return serverId3;
	}
	public void setServerId3(int serverId3) {
		this.serverId3 = serverId3;
	}
	public String getServerId4() {
		return serverId4;
	}
	public void setServerId4(String serverId4) {
		this.serverId4 = serverId4;
	}
	@Override
	public String toString() {
		return "Validation [idDokter=" + idDokter + ", noPhoto=" + noPhoto
				+ ", noKandungan=" + noKandungan + ", noPasien=" + noPasien
				+ ", x=" + x + ", y=" + y + ", a=" + a + ", b=" + b
				+ ", theta=" + theta + ", hasSeen=" + hasSeen + ", serverId="
				+ serverId + ", serverId2=" + serverId2 + ", serverId3="
				+ serverId3 + ", serverId4=" + serverId4 + "]";
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idDokter,""+noPhoto, ""+noKandungan, ""+noPasien};
	}
	
	public void onServerAdd(int photoNumber){
		idDokter = serverId;
		noPhoto = photoNumber;
		noKandungan = serverId3;
		noPasien = serverId4;
		setDirty(false);
	}
	
	@Override
	public void onSentSuccess(){
		serverId = idDokter;
		serverId3 = noKandungan;
		serverId4 = noPasien;
		setDirty(false);
	}
	
	public void onSentSuccessCont(int serverPhotoNumber){
		serverId = idDokter;
	}
	
	public void onUpdatedByServer(Validation other){
		this.x = other.getX();
		this.y = other.getY();
		this.a = other.getA();
		this.b = other.getB();
		this.theta = other.getTheta();
		this.hasSeen = other.isHasSeen();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
	
}
