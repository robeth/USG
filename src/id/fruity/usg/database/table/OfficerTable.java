package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Officer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OfficerTable extends USGTable<Officer> {
	public static final String TABLE_NAME = "officer",
			C_ID_USER = "ktp",
			C_ID_PETUGAS = "officer_id",
			C_ID_PUSKEMAS = "clinic_id",
			C_SERVER_ID2 = "id_server2_clinic",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
				+ C_ID_USER + " text not null primary key, "
				+ C_ID_PETUGAS + " integer not null unique, "
				+ C_ID_PUSKEMAS + " integer, "
				+ C_DIRTY + " integer, "
				+ C_ISACTIVE + " integer, "
				+ C_MODIFYSTAMP + " integer, "
				+ C_CREATESTAMP + " integer, "
				+ C_SERVER_ID + " text, "
				+ C_SERVER_ID2 + " integer, "
				+ "foreign key ("+ C_ID_USER + ") references "
				+ UserTable.TABLE_NAME+ " ( "+ UserTable.C_ID + " ),"
				+ "foreign key (" + C_ID_PUSKEMAS + ") references "
				+ ClinicTable.TABLE_NAME + " (" + ClinicTable.C_ID + " ));";
	private static final String[] ALL_COLUMNS = {
		C_ID_USER,
		C_ID_PETUGAS,
		C_ID_PUSKEMAS,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2
	};
	private static final String PRIMARY_CLAUSE = C_ID_USER+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=?";
	public OfficerTable(){
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
	
	public int getLastLocalIndex(SQLiteDatabase database){
		Log.d("Tale "+TABLE_NAME, "getLastIndex");
		Cursor c = database.rawQuery("select max("+C_ID_PETUGAS+") from "+TABLE_NAME, null);
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
