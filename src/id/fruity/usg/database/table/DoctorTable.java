package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Doctor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DoctorTable extends USGTable<Doctor> {
	public static final String TABLE_NAME = "doctor",
			C_ID_USER = "ktp",
			C_ID_DOKTER = "doctor_id",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID_USER + " text not null primary key, "
					+ C_ID_DOKTER + " integer not null unique, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " text, "
					+ "foreign key ("+ C_ID_USER + ") references "
					+ UserTable.TABLE_NAME+ " ( "
					+ UserTable.C_ID + " ));";
	private static final String [] ALL_COLUMNS = {
		C_ID_USER,
		C_ID_DOKTER,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID
	};
	private static final String PRIMARY_CLAUSE = C_ID_USER+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=?";
	public DoctorTable(){
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
		Log.d("Table "+TABLE_NAME, "getLastIndex");
		Cursor c = database.rawQuery("select max("+C_ID_DOKTER+") from "+TABLE_NAME, null);
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
