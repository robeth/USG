package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Serve;
import android.database.sqlite.SQLiteDatabase;

public class ServeTable extends USGTable<Serve> {
	public static final String TABLE_NAME = "serve",
			C_ID_PUSKESMAS = "clinic_id",
			C_ID_PASIEN = "patient_ktp",
			C_SERVER_ID2_PASIEN = "id_server2_patient",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID_PUSKESMAS + " integer not null, "
					+ C_ID_PASIEN + " text not null, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " integer, "
					+ C_SERVER_ID2_PASIEN + " text, "
					+ "primary key (" + C_ID_PUSKESMAS + ", " + C_ID_PASIEN+ "), "
					+ "foreign key (" + C_ID_PUSKESMAS + ") references "
					+ ClinicTable.TABLE_NAME + "(" + ClinicTable.C_ID + "), "
					+ "foreign key (" + C_ID_PASIEN + ") references "
					+ PatientTable.TABLE_NAME + "(" + PatientTable.C_ID + ")); ";
	
	private static final String[] ALL_COLUMNS = {
		C_ID_PUSKESMAS,
		C_ID_PASIEN,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2_PASIEN
	};
	private static final String PRIMARY_CLAUSE = C_ID_PUSKESMAS+"=? AND "+C_ID_PASIEN+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PASIEN
			+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PASIEN
			+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=? AND "+C_SERVER_ID2_PASIEN+"=?";
	
	public ServeTable(){
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
	
	@Override
	public String getServerIdClause(){
		return SERVER_ID_CLAUSE;
	}
}
