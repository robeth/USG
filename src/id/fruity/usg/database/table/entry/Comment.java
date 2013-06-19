package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.CommentTable;
import android.content.ContentValues;

public class Comment extends USGTableEntry {
	@Expose
	@SerializedName("local_comment_number")
	private int noComment;
	@Expose
	@SerializedName("local_doctor_ktp")
	private String idDokter;
	@Expose
	@SerializedName("local_patient_ktp")
	private String idPasien;
	@Expose
	@SerializedName("local_officer_ktp")
	private String idPetugas;
	@Expose
	@SerializedName("from_doctor")
	private boolean isFromDoctor;
	@Expose
	@SerializedName("content")
	private String content;
	@Expose
	@SerializedName("has_seen")
	private boolean hasSeen;
	@Expose
	@SerializedName("comment_number")
	private int serverId;
	@Expose
	@SerializedName("doctor_ktp")
	private String serverId2;
	@Expose
	@SerializedName("patient_ktp")
	private String serverId3;
	@Expose
	@SerializedName("officer_ktp")
	private String serverId4;
	public Comment(){}
	public Comment(int serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, int noComment, String idDokter,
			String idPasien, String idPetugas, boolean isFromDoctor, String content, boolean hasSeen,
			String serverId2, String serverId3, String serverId4) {
		super(isDirty, isActive, modifyDate, createDate);
		this.noComment = noComment;
		this.idDokter = idDokter;
		this.idPasien = idPasien;
		this.idPetugas = idPetugas;
		this.isFromDoctor = isFromDoctor;
		this.content = content;
		this.hasSeen = hasSeen;
		this.serverId = serverId;
		this.serverId2 = serverId2;
		this.serverId3 = serverId3;
		this.serverId4 = serverId4;
	}
	
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(CommentTable.C_ID_NOCOMMENT, noComment);
		cv.put(CommentTable.C_ID_DOKTER, idDokter);
		cv.put(CommentTable.C_ID_USGPASIEN, idPasien);
		cv.put(CommentTable.C_ID_PETUGAS, idPetugas);
		cv.put(CommentTable.C_FROM_DOCTOR, isFromDoctor? 1 : 0);
		cv.put(CommentTable.C_CONTENT, content);
		cv.put(CommentTable.C_HASSEEN, hasSeen?1:0);
		cv.put(CommentTable.C_SERVER_ID2_DOKTER, serverId2);
		cv.put(CommentTable.C_SERVER_ID3_PASIEN , serverId3);
		cv.put(CommentTable.C_SERVER_ID4_PETUGAS , serverId4);
		
		cv.put(CommentTable.C_DIRTY, isDirty()?1:0);
		cv.put(CommentTable.C_ISACTIVE, isActive()?1:0);
		cv.put(CommentTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(CommentTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(CommentTable.C_SERVER_ID, serverId);
		return cv;
	}
	
	public boolean isFromDoctor() {
		return isFromDoctor;
	}

	public void setFromDoctor(boolean isFromDoctor) {
		this.isFromDoctor = isFromDoctor;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isHasSeen() {
		return hasSeen;
	}
	public void setHasSeen(boolean hasSeen) {
		this.hasSeen = hasSeen;
	}

	public int getNoComment() {
		return noComment;
	}

	public void setNoComment(int noComment) {
		this.noComment = noComment;
	}
	
	
	
	

	public String getServerId3() {
		return serverId3;
	}
	public void setServerId3(String serverId3) {
		this.serverId3 = serverId3;
	}
	public String getIdPasien() {
		return idPasien;
	}
	public void setIdPasien(String idPasien) {
		this.idPasien = idPasien;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+noComment,""+idPasien};
	}

	@Override
	public String toString() {
		return "Comment [noComment=" + noComment + ", idDokter=" + idDokter
				+ ", idPasien=" + idPasien + ", idPetugas=" + idPetugas
				+ ", isFromDoctor=" + isFromDoctor + ", content=" + content
				+ ", hasSeen=" + hasSeen + ", serverId=" + serverId
				+ ", serverId2=" + serverId2 + ", serverId3=" + serverId3
				+ ", serverId4=" + serverId4 + "]";
	}
	
	public void onServerAdd(int commentNumber) {
		noComment = commentNumber;
		idDokter = serverId2;
		idPasien = serverId3;
		idPetugas = serverId4;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId2 = idDokter;
		serverId3 = idPasien;
		serverId4 = idPetugas;
		setDirty(false);
	}
	
	public void onSentSuccessCont(int serverCommentNumber){
		serverId = serverCommentNumber;
	}
	
	public void onUpdatedByServer(Comment other){
		this.isFromDoctor = other.isFromDoctor;
		this.content = other.getContent();
		this.hasSeen = other.isHasSeen();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}
	
}
