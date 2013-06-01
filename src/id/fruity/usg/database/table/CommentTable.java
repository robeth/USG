package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Comment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentTable extends USGTable<Comment> {
	public static final String TABLE_NAME = "comment",
			C_ID_NOCOMMENT = "comment_number",
			C_ID_DOKTER = "doctor_ktp",
			C_ID_USGPASIEN = "patient_ktp",
			C_ID_PETUGAS = "officer_ktp",
			C_FROM_DOCTOR = "from_doctor",
			C_CONTENT = "content",
			C_HASSEEN = "has_seen",
			C_SERVER_ID2_DOKTER = "id_server2_doctor",
			C_SERVER_ID3_PASIEN = "id_server3_patient",
			C_SERVER_ID4_PETUGAS = "id_server4_officer",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID_NOCOMMENT + " integer not null, "
					+ C_ID_DOKTER + " text not null, "
					+ C_ID_USGPASIEN + " text not null, "
					+ C_ID_PETUGAS + " text not null, "
					+ C_FROM_DOCTOR + " integer not null, "
					+ C_CONTENT + " text not null, "
					+ C_HASSEEN + " integer, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " integer, "
					+ C_SERVER_ID2_DOKTER+ " text, "
					+ C_SERVER_ID3_PASIEN + " text, "
					+ C_SERVER_ID4_PETUGAS+ " text, "
					+ "primary key (" + C_ID_NOCOMMENT+"," + C_ID_USGPASIEN + "), "
					+ "foreign key (" + C_ID_DOKTER + ") references "
					+ DoctorTable.TABLE_NAME + "(" + DoctorTable.C_ID_USER + "), "
					+ "foreign key (" + C_ID_USGPASIEN + " ) references " + PatientTable.TABLE_NAME
					+ "(" + PatientTable.C_ID+ "), "
					+ "foreign key ("+C_ID_PETUGAS+") references "+OfficerTable.TABLE_NAME
					+ "("+ OfficerTable.C_ID_USER +"));";
	private static final String[] ALL_COLUMNS = {
		C_ID_NOCOMMENT,
		C_ID_DOKTER,
		C_ID_USGPASIEN,
		C_ID_PETUGAS,
		C_FROM_DOCTOR,
		C_CONTENT,
		C_HASSEEN,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2_DOKTER,
		C_SERVER_ID3_PASIEN,
		C_SERVER_ID4_PETUGAS
	};
	private static final String PRIMARY_CLAUSE = C_ID_NOCOMMENT + "=? AND "+ C_ID_USGPASIEN + "=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_DOKTER
			+"=? OR "+C_SERVER_ID3_PASIEN+"=? OR "+C_SERVER_ID4_PETUGAS
			+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_DOKTER
			+"=? OR "+C_SERVER_ID3_PASIEN+"=? OR "+C_SERVER_ID4_PETUGAS
			+"=?) AND ("+C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=? AND "+ C_SERVER_ID3_PASIEN + "=?";
	
	public CommentTable(){
		super(TABLE_NAME,CREATE_STATEMENT);
	}
	
	public void onCreate(SQLiteDatabase database) {
		super.onCreate(database);

	}

	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		super.onUpgrade(database, oldVersion, newVersion);
	}

	@Override
	public String[] getAllColumns() {
		return ALL_COLUMNS;
	}

	@Override
	public String getPrimaryClause() {
		return PRIMARY_CLAUSE;
	}
	
	@Override
	public String getNewClause() {
		return NEW_CLAUSE;
	}
	
	@Override
	public String getUpdateClause(){
		return UPDATE_CLAUSE;
	}
	
	public int getLastLocalIndex(SQLiteDatabase database, String pasienId){
		Log.d("Tale "+TABLE_NAME, "getLastIndex");
		Cursor c = database.rawQuery("select max("+C_ID_NOCOMMENT+") from "+TABLE_NAME+ " where "+C_ID_USGPASIEN+" =?", new String[]{pasienId+""});
		c.moveToFirst();
		int result = c.getInt(0);
		c.close();
		return result;
	}

	@Override
	public String getServerIdClause(){
		return SERVER_ID_CLAUSE;
	}
}
