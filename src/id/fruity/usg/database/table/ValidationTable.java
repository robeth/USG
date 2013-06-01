package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.Validation;
import android.database.sqlite.SQLiteDatabase;

public class ValidationTable extends USGTable<Validation> {
	public static final String TABLE_NAME = "validation",
			C_ID_DOKTER = "doctor_ktp",
			C_ID_USGFOTO = "photo_number",
			C_ID_USGKANDUNGAN = "pregnancy_number",
			C_ID_USGPASIEN = "patient_ktp",
			C_A = "a",
			C_B = "b",
			C_X = "x",
			C_Y = "y",
			C_THETA = "theta",
			C_HASSEEN = "has_seen",
			C_SERVER_ID2_FOTO = "id_server2_photo",
			C_SERVER_ID3_KANDUNGAN = "id_server3_pregnancy",
			C_SERVER_ID4_PASIEN = "id_server4_patient",
			CREATE_STATEMENT = "create table " + TABLE_NAME + " ( "
					+ C_ID_DOKTER + " text not null, "
					+ C_ID_USGFOTO + " integer not null, "
					+ C_ID_USGKANDUNGAN + " integer not null, "
					+ C_ID_USGPASIEN + " text not null, "
					+ C_A + " real, "
					+ C_B + " real, "
					+ C_X + " real, "
					+ C_Y + " real,"
					+ C_THETA + " real, "
					+ C_HASSEEN + " integer, "
					+ C_DIRTY + " integer, "
					+ C_ISACTIVE + " integer, "
					+ C_MODIFYSTAMP + " integer, "
					+ C_CREATESTAMP + " integer, "
					+ C_SERVER_ID + " text, "
					+ C_SERVER_ID2_FOTO + " integer, "
					+ C_SERVER_ID3_KANDUNGAN + " integer, "
					+ C_SERVER_ID4_PASIEN + " text, "
					+ "primary key (" + C_ID_DOKTER + ", " 
					+ C_ID_USGFOTO + ", " + C_ID_USGKANDUNGAN + ", " 
					+ C_ID_USGPASIEN + "), "
					+ "foreign key (" + C_ID_DOKTER + ") references "
					+ DoctorTable.TABLE_NAME + "(" + DoctorTable.C_ID_USER + "), "
					+ "foreign key (" + C_ID_USGFOTO + ", " + C_ID_USGKANDUNGAN + ", "
					+ C_ID_USGPASIEN + " ) references " + PhotoTable.TABLE_NAME
					+ "(" + PhotoTable.C_ID + ", " + PhotoTable.C_ID_KANDUNGAN + ","
					+ PhotoTable.C_ID_PASIEN + "));";
	private static final String[] ALL_COLUMNS = new String[]{
		C_ID_DOKTER,
		C_ID_USGFOTO,
		C_ID_USGKANDUNGAN,
		C_ID_USGPASIEN,
		C_A,
		C_B,
		C_X,
		C_Y,
		C_THETA,
		C_HASSEEN,
		C_DIRTY,
		C_ISACTIVE,
		C_MODIFYSTAMP,
		C_CREATESTAMP,
		C_SERVER_ID,
		C_SERVER_ID2_FOTO,
		C_SERVER_ID3_KANDUNGAN,
		C_SERVER_ID4_PASIEN
	};
	private static final String PRIMARY_CLAUSE = C_ID_DOKTER+"=? AND "+C_ID_USGFOTO+"=? AND " + C_ID_USGKANDUNGAN+"=? AND "+C_ID_USGPASIEN+"=?";
	private static final String NEW_CLAUSE = C_ISACTIVE+"=1 AND ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_FOTO
			+"=? OR "+C_SERVER_ID3_KANDUNGAN+"=? OR "+C_SERVER_ID4_PASIEN
			+"=? OR "+C_CREATESTAMP+" > ?)";
	private static final String UPDATE_CLAUSE = "NOT ("+C_SERVER_ID+"=? OR "+C_SERVER_ID2_FOTO
			+"=? OR "+C_SERVER_ID3_KANDUNGAN+"=? OR "+C_SERVER_ID4_PASIEN
			+"=?) AND ("+ C_MODIFYSTAMP+" > ? OR "+C_DIRTY+"=1)";
	private static final String SERVER_ID_CLAUSE = C_SERVER_ID+"=? AND "+C_SERVER_ID2_FOTO+"=? AND " + C_SERVER_ID3_KANDUNGAN+"=? AND "+C_SERVER_ID4_PASIEN+"=?";
	
	public ValidationTable(){
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
