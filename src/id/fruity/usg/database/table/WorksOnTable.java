package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.WorksOn;
import android.database.sqlite.SQLiteDatabase;

public class WorksOnTable extends USGTable<WorksOn>{
	public static final String TABLE_NAME = "works_on",
			C_ID_DOKTER = "doctor_ktp",
			C_ID_PUSKESMAS = "clinic_id",
			C_SERVER_ID2_PUSKESMAS= "id_server2_clinic",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID_DOKTER + " text not null, "
					+ C_ID_PUSKESMAS + " integer not null, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " text, "
					+ C_SERVER_ID2_PUSKESMAS + " integer, "
					+ "primary key ( "+ C_ID_DOKTER + ", " + C_ID_PUSKESMAS + "),"
					+ "foreign key ("+ C_ID_DOKTER + ") references "
					+ DoctorTable.TABLE_NAME + " ( "+ DoctorTable.C_ID_USER + " ), "
					+ "foreign key (" + C_ID_PUSKESMAS + ") references "
					+ ClinicTable.TABLE_NAME + " ( " + ClinicTable.C_ID + " ));";
	
	private static final String[] ALL_COLUMNS = new String[]{C_ID_DOKTER,
		C_ID_PUSKESMAS,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2_PUSKESMAS};
	private static final String PRIMARY_CLAUSE = C_ID_DOKTER+"=? AND "+C_ID_PUSKESMAS+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PUSKESMAS
			+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_PUSKESMAS
			+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID + "=? AND "+C_SERVER_ID2_PUSKESMAS+"=?";
	
	public WorksOnTable(){
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
