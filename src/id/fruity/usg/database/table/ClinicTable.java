package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Clinic;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ClinicTable extends USGTable<Clinic> {
	public static final String TABLE_NAME = "clinic",
			C_ID = "clinic_id",
			C_NAME = "name",
			C_ROAD_ADDRESS = "address",
			C_CITY = "city",
			C_PROVINCE = "province",
			C_PHONE = "phone",
			C_DESCRIPTION = "description",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( " + C_ID
					+ " integer not null primary key, " + C_NAME
					+ " text not null, " + C_ROAD_ADDRESS + " text, " + C_CITY
					+ " text, " + C_PROVINCE + " text, " + C_PHONE + " text, "
					+ C_DESCRIPTION + " text, " + C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, " + C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, " + C_SERVER_ID + " integer);";
	private static final String[] ALL_COLUMNS = new String[] { C_ID, C_NAME,
			C_ROAD_ADDRESS, C_CITY, C_PROVINCE, C_PHONE, C_DESCRIPTION,
			C_DIRTY, C_ISACTIVE, C_MODIFYSTAMP, C_CREATESTAMP, C_SERVER_ID };
	private static final String PRIMARY_CLAUSE = C_ID + "=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=?) AND ("+C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=?";
	public ClinicTable() {
		super(TABLE_NAME, CREATE_STATEMENT);
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

	public int getLastLocalIndex(SQLiteDatabase database) {
		Log.d("Tale " + TABLE_NAME, "getLastIndex");
		Cursor c = database.rawQuery("select max(" + C_ID + ") from "
				+ TABLE_NAME, null);
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
