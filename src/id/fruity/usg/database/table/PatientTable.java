package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Patient;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PatientTable extends USGTable<Patient> {
	public static final String TABLE_NAME = "patient",
		C_ID = "ktp",
		C_NAME = "name",
		C_ADDRESS = "address",
		C_PHONE = "phone",
		C_BIRTHDATE = "birthdate",
		C_FILENAME = "filename",
		C_DESCRIPTION = "description",
		CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
				+ C_ID + " text not null primary key, "
				+ C_NAME + " text not null,"
				+ C_ADDRESS + " text, "
				+ C_PHONE + " text, "
				+ C_BIRTHDATE + " integer, "
				+ C_FILENAME + " text, "
				+ C_DESCRIPTION + " text, "
				+ C_DIRTY + " integer, "
				+ C_ISACTIVE + " integer, "
				+ C_MODIFYSTAMP + " integer, "
				+ C_CREATESTAMP + " integer, "
				+ C_SERVER_ID + " text);";
	private static final String[] ALL_COLUMNS = {
		C_ID,
		C_NAME,
		C_ADDRESS,
		C_PHONE,
		C_BIRTHDATE,
		C_FILENAME,
		C_DESCRIPTION,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID
	};
	private static final String PRIMARY_CLAUSE = C_ID+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=?";
	public PatientTable(){
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
		Cursor c = database.rawQuery("select max("+C_ID+") from "+TABLE_NAME, null);
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