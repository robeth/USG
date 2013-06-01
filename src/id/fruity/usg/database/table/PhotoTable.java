package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Photo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PhotoTable extends USGTable<Photo> {
	public static final String TABLE_NAME = "photo",
			C_ID = "photo_number",
			C_AUTO_DATE = "analyze_timestamp",
			C_FILENAME = "filename",
			C_X = "x",
			C_Y = "y",
			C_A = "a",
			C_B = "b",
			C_THETA = "theta",
			C_SCALE = "scale",
			C_METHOD = "method",
			C_ID_PASIEN = "patient_ktp",
			C_ID_KANDUNGAN = "pregnancy_number",
			C_ID_PETUGAS = "officer_ktp",
			C_SERVER_ID2_PASIEN = "id_server2_patient",
			C_SERVER_ID3_KANDUNGAN = "id_server3_pregnancy",
			C_SERVER_ID4_OFFICER = "id_server4_officer",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID+ " integer not null, "
					+ C_ID_PASIEN + " integer not null, "
					+ C_ID_KANDUNGAN + " integer not null, "
					+ C_ID_PETUGAS + " integer not null, "
					+ C_AUTO_DATE + " integer, "
					+ C_FILENAME + " text not null, "
					+ C_X + " real, "
					+ C_Y + " real, "
					+ C_A + " real, "
					+ C_B + " real, "
					+ C_THETA + " real, "
					+ C_SCALE + " real, "
					+ C_METHOD + " text, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " integer, "
					+ C_SERVER_ID2_PASIEN + " text, "
					+ C_SERVER_ID3_KANDUNGAN + " integer, "
					+ C_SERVER_ID4_OFFICER + " text, "
					+ "primary key (" + C_ID + ", "+ C_ID_PASIEN + ", " + C_ID_KANDUNGAN + "), " 
					+ "foreign key (" + C_ID_PASIEN + ", " + C_ID_KANDUNGAN + ") references "
					+ PregnancyTable.TABLE_NAME+ " ( " + PregnancyTable.C_ID + ", " 
					+ PregnancyTable.C_ID_PASIEN + "));";
	private static final String[] ALL_COLUMNS = {
		C_ID,
		C_ID_PASIEN,
		C_ID_KANDUNGAN,
		C_ID_PETUGAS,
		C_AUTO_DATE,
		C_FILENAME,
		C_X,
		C_Y,
		C_A,
		C_B,
		C_THETA,
		C_SCALE,
		C_METHOD,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2_PASIEN,
		C_SERVER_ID3_KANDUNGAN,
		C_SERVER_ID4_OFFICER
	};
	private static final String PRIMARY_CLAUSE = C_ID+"=? AND "+C_ID_PASIEN+"=? AND " + C_ID_KANDUNGAN+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PASIEN
			+"=? OR "+C_SERVER_ID3_KANDUNGAN+"=? OR "+C_SERVER_ID4_OFFICER
			+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PASIEN
			+"=? OR "+C_SERVER_ID3_KANDUNGAN+"=? OR "+C_SERVER_ID4_OFFICER
			+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=? AND "+C_SERVER_ID2_PASIEN+"=? AND "+C_SERVER_ID3_KANDUNGAN+"=?";
	
	public PhotoTable(){
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
	
	public int getLastLocalIndex(SQLiteDatabase database, String pasienID, int kandunganId){
		Log.d("Tale "+TABLE_NAME, "getLastIndex");
		Cursor c = database.rawQuery("select max("+C_ID+") from "+TABLE_NAME+" where "+C_ID_PASIEN+"=? AND "+C_ID_KANDUNGAN+" =?", new String[]{""+pasienID, ""+kandunganId});
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
