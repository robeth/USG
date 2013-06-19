package id.fruity.usg.database;

import id.fruity.usg.database.table.USGTable;
import id.fruity.usg.database.table.WorksOnTable;
import id.fruity.usg.database.table.DoctorTable;
import id.fruity.usg.database.table.PregnancyTable;
import id.fruity.usg.database.table.CommentTable;
import id.fruity.usg.database.table.ServeTable;
import id.fruity.usg.database.table.PatientTable;
import id.fruity.usg.database.table.OfficerTable;
import id.fruity.usg.database.table.ClinicTable;
import id.fruity.usg.database.table.PhotoTable;
import id.fruity.usg.database.table.UserTable;
import id.fruity.usg.database.table.ValidationTable;
import id.fruity.usg.database.table.entry.WorksOn;
import id.fruity.usg.database.table.entry.Doctor;
import id.fruity.usg.database.table.entry.Pregnancy;
import id.fruity.usg.database.table.entry.Comment;
import id.fruity.usg.database.table.entry.Serve;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.database.table.entry.Officer;
import id.fruity.usg.database.table.entry.Clinic;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.User;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.util.DateUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class USGDBHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "USG.db";
	private SQLiteDatabase database;
	private UserTable userTable;
	private ClinicTable puskesmasTable;
	private OfficerTable petugasTable;
	private DoctorTable dokterTable;
	private PatientTable pasienTable;
	private PregnancyTable kandunganTable;
	private PhotoTable usgFotoTable;
	private WorksOnTable bjTable;
	private ServeTable melayaniTable;
	private CommentTable komentarTable;
	private ValidationTable validasiTable;
	private static USGDBHelper instance;
	
	
	private USGDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		userTable = new UserTable();
		puskesmasTable = new ClinicTable();
		petugasTable = new OfficerTable();
		dokterTable = new DoctorTable();
		pasienTable = new PatientTable();
		kandunganTable = new PregnancyTable();
		usgFotoTable = new PhotoTable();
		bjTable = new WorksOnTable();
		melayaniTable = new ServeTable();
		komentarTable = new CommentTable();
		validasiTable = new ValidationTable();
	}
	
	public static USGDBHelper getInstance(Context context){
		if(instance == null){
			instance = new USGDBHelper(context);
		}
		return instance;
	}

	public void onCreate(SQLiteDatabase database) {
		userTable.onCreate(database);
		puskesmasTable.onCreate(database);
		petugasTable.onCreate(database);
		dokterTable.onCreate(database);
		pasienTable.onCreate(database);
		kandunganTable.onCreate(database);
		usgFotoTable.onCreate(database);
		bjTable.onCreate(database);
		melayaniTable.onCreate(database);
		komentarTable.onCreate(database);
		validasiTable.onCreate(database);
	}

	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		userTable.onUpgrade(database, oldVersion, newVersion);
		puskesmasTable.onUpgrade(database, oldVersion, newVersion);
		petugasTable.onUpgrade(database, oldVersion, newVersion);
		dokterTable.onUpgrade(database, oldVersion, newVersion);
		pasienTable.onUpgrade(database, oldVersion, newVersion);
		kandunganTable.onUpgrade(database, oldVersion, newVersion);
		usgFotoTable.onUpgrade(database, oldVersion, newVersion);
		bjTable.onUpgrade(database, oldVersion, newVersion);
		melayaniTable.onUpgrade(database, oldVersion, newVersion);
		komentarTable.onUpgrade(database, oldVersion, newVersion);
		validasiTable.onUpgrade(database, oldVersion, newVersion);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public void open() throws SQLException {
		database = this.getWritableDatabase();
	}

	public void close() {
		database.close();
	}
	
	public void test(){
		//Insert table user
		User  u1 = new User("-1", true,true,82233213123L,82233213123L,"ktp1","user1","passuser1","User 1","Address 1","user1@gmail.com","+6285655228693","This is User1", -1, -1);
		User  u2 = new User("-1", true,true,82233213123L,82233213123L,"ktp2","user2","passuser2","User 2","Address 2","user2@gmail.com","+6285655228693","This is User2", -1, -1);
		User  u3 = new User("-1", true,true,82233213123L,82233213123L,"ktp3","user3","passuser3","User 3","Address 3","user3@gmail.com","+6285655228693","This is User3", -1, -1);
		User  u4 = new User("-1", true,true,82233213123L,82233213123L,"ktp4","user4","passuser4","User 4","Address 4","user4@gmail.com","+6285655228693","This is User4", -1, -1);
		User  u5 = new User("-1", true,true,82233213123L,82233213123L,"ktp5","user5","passuser5","User 5","Address 5","user5@gmail.com","+6285655228693","This is User5", -1, -1);
		User  u6 = new User("-1", true,true,82233213123L,82233213123L,"ktp6","user6","passuser6","User 6","Address 6","user6@gmail.com","+6285655228693","This is User6", -1, -1);
		User  u7 = new User("-1", true,true,82233213123L,82233213123L,"ktp7","user7","passuser7","User 7","Address 7","user7@gmail.com","+6285655228693","This is User7", -1, -1);
		User  u8 = new User("-1", true,true,82233213123L,82233213123L,"ktp8","user8","passuser8","User 8","Address 8","user8@gmail.com","+6285655228693","This is User8", -1, -1);
		User  u9 = new User("-1", true,true,82233213123L,82233213123L,"ktp9","user9","passuser9","User 9","Address 9","user9@gmail.com","+6285655228693","This is User9", -1, -1);
		User  u10 = new User("-1", true,true,82233213123L,82233213123L,"ktp10","user10","passuser10","User 10","Address 10","user10@gmail.com","+6285655228693","This is User10", -1, -1);
		userTable.insert(database, u1);
		userTable.insert(database, u2);
		userTable.insert(database, u3);
		userTable.insert(database, u4);
		userTable.insert(database, u5);
		userTable.insert(database, u6);
		userTable.insert(database, u7);
		userTable.insert(database, u8);
		userTable.insert(database, u9);
		userTable.insert(database, u10);

		
		
		//Insert table dokter
		Doctor d1 = new Doctor("-1", true, true, 82233213123L, 82233213123L, "ktp1", 1);
		Doctor d2 = new Doctor("-1", true, true, 82233213123L, 82233213123L, "ktp2", 2);
		Doctor d3 = new Doctor("-1", true, true, 82233213123L, 82233213123L, "ktp3", 3);
		Doctor d4 = new Doctor("-1", true, true, 82233213123L, 82233213123L, "ktp4", 4);
		Doctor d5 = new Doctor("-1", true, true, 82233213123L, 82233213123L, "ktp5", 5);
		dokterTable.insert(database, d1);
		dokterTable.insert(database, d2);
		dokterTable.insert(database, d3);
		dokterTable.insert(database, d4);
		dokterTable.insert(database, d5);

		
		
		Clinic p1 = new Clinic(-1, true, true, 82233213123L, 82233213123L, 1, "Puskesmas 1", "Addr Puskesmas 1", "Kota 1", "Prov 1", "+62315318242", "Puskesmas 1");
		Clinic p2 = new Clinic(-1, true, true, 82233213123L, 82233213123L, 2, "Puskesmas 2", "Addr Puskesmas 2", "Kota 2", "Prov 2", "+62315318242", "Puskesmas 2");
		Clinic p3 = new Clinic(-1, true, true, 82233213123L, 82233213123L, 3, "Puskesmas 3", "Addr Puskesmas 3", "Kota 3", "Prov 3", "+62315318242", "Puskesmas 3");
		puskesmasTable.insert(database, p1);
		puskesmasTable.insert(database, p2);
		puskesmasTable.insert(database, p3);
		
		Officer pe1 = new Officer("-1", true, true, 82233213123L, 82233213123L, "ktp6", 1, 1,-1);
		Officer pe2 = new Officer("-1", true, true, 82233213123L, 82233213123L, "ktp7", 2, 1, -1);
		Officer pe3 = new Officer("-1", true, true, 82233213123L, 82233213123L, "ktp8", 3, 2, -1);
		Officer pe4 = new Officer("-1", true, true, 82233213123L, 82233213123L, "ktp9", 4, 2, -1);
		Officer pe5 = new Officer("-1", true, true, 82233213123L, 82233213123L, "ktp10", 5, 3, -1);
		petugasTable.insert(database, pe1);
		petugasTable.insert(database, pe2);
		petugasTable.insert(database, pe3);
		petugasTable.insert(database, pe4);
		petugasTable.insert(database, pe5);
		
		Patient pa1 = new Patient("-1", true, true, 82233213123L, 82233213123L, "ktp101", "Pasien 1", "Alamat Pasien 1", "+62", 82233213123L,"a.jpg","Deskripsi Pasien 1",-1,-1);
		Patient pa2 = new Patient("-1", true, true, 82233213123L, 82233213123L, "ktp102", "Pasien 2", "Alamat Pasien 2", "+62", 82233213123L,"a.jpg","Deskripsi Pasien 2",-1,-1);
		Patient pa3 = new Patient("-1", true, true, 82233213123L, 82233213123L, "ktp103", "Pasien 3", "Alamat Pasien 3", "+62", 82233213123L,"a.jpg","Deskripsi Pasien 3",-1,-1);
		Patient pa4 = new Patient("-1", true, true, 82233213123L, 82233213123L, "ktp104", "Pasien 4", "Alamat Pasien 4", "+62", 82233213123L,"a.jpg","Deskripsi Pasien 4",-1,-1);
		Patient pa5 = new Patient("-1", true, true, 82233213123L, 82233213123L, "ktp105", "Pasien 5", "Alamat Pasien 5", "+62", 82233213123L, "a.jpg","Deskripsi Pasien 5",-1,-1);
		pasienTable.insert(database, pa1);
		pasienTable.insert(database, pa2);
		pasienTable.insert(database, pa3);
		pasienTable.insert(database, pa4);
		pasienTable.insert(database, pa5);
		
		Pregnancy k1 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 1, "ktp101", true, "-1");
		Pregnancy k2 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 2, "ktp101", false,"-1");
		Pregnancy k3 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 1, "ktp102", true, "-1");
		Pregnancy k4 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 2, "ktp102", false,"-1");
		Pregnancy k5 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 1, "ktp103", true, "-1");
		Pregnancy k6 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 2, "ktp103", false,"-1");
		Pregnancy k7 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 1, "ktp104", true, "-1");
		Pregnancy k8 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 2, "ktp104", false,"-1");
		Pregnancy k9 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 1, "ktp105", true, "-1");
		Pregnancy k10 = new Pregnancy(-1, true, true, 82233213123L, 82233213123L, 2, "ktp105", false, "-1");
		kandunganTable.insert(database, k1);
		kandunganTable.insert(database, k2);
		kandunganTable.insert(database, k3);
		kandunganTable.insert(database, k4);
		kandunganTable.insert(database, k5);
		kandunganTable.insert(database, k6);
		kandunganTable.insert(database, k7);
		kandunganTable.insert(database, k8);
		kandunganTable.insert(database, k9);
		kandunganTable.insert(database, k10);
		
		Photo f1 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 1 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp101", 1, "ktp6", "-1", -1, "-1", -1, -1);
		Photo f2 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 1 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp101", 1, "ktp6", "-1", -1, "-1", -1, -1);
		Photo f3 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 1 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp101", 2, "ktp6", "-1", -1, "-1", -1, -1);
		Photo f4 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 1 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp101", 2, "ktp6", "-1", -1, "-1", -1, -1);
		Photo f5 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 2 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp102", 1, "ktp7", "-1", -1, "-1", -1, -1);
		Photo f6 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 2 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp102", 1, "ktp7", "-1", -1, "-1", -1, -1);
		Photo f7 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 2 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp102", 2, "ktp7", "-1", -1, "-1", -1, -1);
		Photo f8 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 2 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp102", 2, "ktp7", "-1", -1, "-1", -1, -1);
		Photo f9 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 3 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp103", 1, "ktp8", "-1", -1, "-1", -1, -1);
		Photo f10 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 3 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp103", 1, "ktp8", "-1", -1, "-1", -1, -1);
		Photo f11 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 3 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp103", 2, "ktp8", "-1", -1, "-1", -1, -1);
		Photo f12 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 3 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp103", 2, "ktp8", "-1", -1, "-1", -1, -1);
		Photo f13 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 4 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp104", 1, "ktp9", "-1", -1, "-1", -1, -1);
		Photo f14 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 4 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp104", 1, "ktp9", "-1", -1, "-1", -1, -1);
		Photo f15 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 4 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp104", 2, "ktp9", "-1", -1, "-1", -1, -1);
		Photo f16 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 4 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp104", 2, "ktp9", "-1", -1, "-1", -1, -1);
		Photo f17 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 5 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp105", 1, "ktp10", "-1", -1, "-1", -1, -1);
		Photo f18 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 5 1", -1, -1, -1, -1, -1, -1,"RHT", "ktp105", 1, "ktp10", "-1", -1, "-1", -1, -1);
		Photo f19 = new Photo(-1, true, true, 82233213123L, 82233213123L, 1, 82233213123L, "foto 1 pasien 5 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp105", 2, "ktp10", "-1", -1, "-1", -1, -1);
		Photo f20 = new Photo(-1, true, true, 82233213123L, 82233213123L, 2, 82233213123L, "foto 2 pasien 5 2", -1, -1, -1, -1, -1, -1,"RHT", "ktp105", 2, "ktp10", "-1", -1, "-1", -1, -1);
		usgFotoTable.insert(database, f1);
		usgFotoTable.insert(database, f2);
		usgFotoTable.insert(database, f3);
		usgFotoTable.insert(database, f4);
		usgFotoTable.insert(database, f5);
		usgFotoTable.insert(database, f6);
		usgFotoTable.insert(database, f7);
		usgFotoTable.insert(database, f8);
		usgFotoTable.insert(database, f9);
		usgFotoTable.insert(database, f10);
		usgFotoTable.insert(database, f11);
		usgFotoTable.insert(database, f12);
		usgFotoTable.insert(database, f13);
		usgFotoTable.insert(database, f14);
		usgFotoTable.insert(database, f15);
		usgFotoTable.insert(database, f16);
		usgFotoTable.insert(database, f17);
		usgFotoTable.insert(database, f18);
		usgFotoTable.insert(database, f19);
		usgFotoTable.insert(database, f20);
		
		Serve mel1 = new Serve(-1, true, true, 82233213123L, 82233213123L, 1, "ktp101", "-1");
		Serve mel2 = new Serve(-1, true, true, 82233213123L, 82233213123L, 1, "ktp102", "-1");
		Serve mel3 = new Serve(-1, true, true, 82233213123L, 82233213123L, 2, "ktp103", "-1");
		Serve mel4 = new Serve(-1, true, true, 82233213123L, 82233213123L, 2, "ktp104", "-1");
		Serve mel5 = new Serve(-1, true, true, 82233213123L, 82233213123L, 3, "ktp105", "-1");
		melayaniTable.insert(database, mel1);
		melayaniTable.insert(database, mel2);
		melayaniTable.insert(database, mel3);
		melayaniTable.insert(database, mel4);
		melayaniTable.insert(database, mel5);
		
		WorksOn bj1 = new WorksOn("-1", true, true, 82233213123L, 82233213123L, "ktp1", 1, -1);
		WorksOn bj2 = new WorksOn("-1", true, true, 82233213123L, 82233213123L, "ktp2", 1, -1);
		WorksOn bj3 = new WorksOn("-1", true, true, 82233213123L, 82233213123L, "ktp3", 2, -1);
		WorksOn bj4 = new WorksOn("-1", true, true, 82233213123L, 82233213123L, "ktp4", 2, -1);
		WorksOn bj5 = new WorksOn("-1", true, true, 82233213123L, 82233213123L, "ktp5", 3, -1);
		bjTable.insert(database, bj1);
		bjTable.insert(database, bj2);
		bjTable.insert(database, bj3);
		bjTable.insert(database, bj4);
		bjTable.insert(database, bj5);
		
		Comment ko1 = new Comment(-1, true, true, 82233213123L, 82233213123L, 1,"ktp1", "ktp101","ktp6", true,"Kehamilan 2 kamu ya..", false, "-1", "-1", "-1");
		Comment ko2 = new Comment(-1, true, true, 82233213123L, 82233213123L, 1,"ktp2", "ktp102","ktp7", true,"Kehamilan 2 kamu ya..", false, "-1", "-1", "-1");
		Comment ko3 = new Comment(-1, true, true, 82233213123L, 82233213123L, 1,"ktp3", "ktp103","ktp8", true,"Kehamilan 2 kamu ya..", false, "-1", "-1", "-1");
		Comment ko4 = new Comment(-1, true, true, 82233213123L, 82233213123L, 1,"ktp4", "ktp104","ktp9", true,"Kehamilan 2 kamu ya..", false, "-1", "-1", "-1");
		Comment ko5 = new Comment(-1, true, true, 82233213123L, 82233213123L, 1,"ktp5", "ktp105","ktp10", true,"Kehamilan 2 kamu ya..", false, "-1", "-1", "-1");
		komentarTable.insert(database, ko1);
		komentarTable.insert(database, ko2);
		komentarTable.insert(database, ko3);
		komentarTable.insert(database, ko4);
		komentarTable.insert(database, ko5);
		
		
		Validation val1 = new Validation("-1", true, true, 82233213123L, 82233213123L, "ktp1", 1, 2, "ktp101", -1, -1, -1, -1, -1, false, -1, -1, "-1");
		Validation val2 = new Validation("-1", true, true, 82233213123L, 82233213123L, "ktp2", 1, 2, "ktp102", -1, -1, -1, -1, -1, false, -1, -1, "-1");
		Validation val3 = new Validation("-1", true, true, 82233213123L, 82233213123L, "ktp3", 1, 2, "ktp103", -1, -1, -1, -1, -1, false, -1, -1, "-1");
		Validation val4 = new Validation("-1", true, true, 82233213123L, 82233213123L, "ktp4", 1, 2, "ktp104", -1, -1, -1, -1, -1, false, -1, -1, "-1");
		Validation val5 = new Validation("-1", true, true, 82233213123L, 82233213123L, "ktp5", 1, 2, "ktp105", -1, -1, -1, -1, -1, false, -1, -1, "-1");
		validasiTable.insert(database, val1);
		validasiTable.insert(database, val2);
		validasiTable.insert(database, val3);
		validasiTable.insert(database, val4);
		validasiTable.insert(database, val5);
//		validasiTable.delete(database, val1);
//		komentarTable.delete(database, ko1);
//		bjTable.delete(database, bj1);
//		melayaniTable.delete(database, mel1);
//		usgFotoTable.delete(database, f1);
//		kandunganTable.delete(database, k1);
//		pasienTable.delete(database, pa1);
//		petugasTable.delete(database, pe1);
//		puskesmasTable.delete(database, p1);
//		dokterTable.delete(database, d1);
//		userTable.delete(database, u1);
	}
	
	public boolean isUserExist(String username){
		Cursor c = database.rawQuery("select * from "+UserTable.TABLE_NAME+" where "+UserTable.C_USERNAME+"=?", new String[]{username});
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}
	
	public boolean isUserPassMatch(String username, String password){
		Cursor c = database.rawQuery("select * from "+UserTable.TABLE_NAME+" where "+UserTable.C_USERNAME+"=? and "+UserTable.C_PASSWORD+"=? ", new String[]{username, password});
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}
	
	public String getIdOfUsername(String username){
		Cursor c = database.rawQuery("select "+UserTable.C_ID+" from "+UserTable.TABLE_NAME+" where "+UserTable.C_USERNAME+"=?", new String[]{username});
		c.moveToFirst();
		String result = c.getString(0);
		c.close();
		return result;
	}
	
	public boolean isDoctor(String idUser){
		Cursor c = database.rawQuery("select * from "+DoctorTable.TABLE_NAME+" where "+DoctorTable.C_ID_USER+"=?", new String[]{""+idUser});
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}
	
	public boolean isPetugas(String idUser){
		Cursor c = database.rawQuery("select * from "+OfficerTable.TABLE_NAME+" where "+OfficerTable.C_ID_USER+"=?", new String[]{""+idUser});
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}
	
	public int getPuskesmasId(String idUser){
		Log.d("USG DB Helper", "IdUser:"+idUser);
		Cursor c = database.rawQuery("select "+ OfficerTable.C_ID_PUSKEMAS+" from "+OfficerTable.TABLE_NAME+ " where "+OfficerTable.C_ID_USER+ "=?", new String[]{""+idUser});
		c.moveToFirst();
		int result = c.getInt(0);
		c.close();
		return result;
	}
	
	public int getDokterId(String userId) {
		Log.d("USG DB Helper", "get dokter IdUser:"+userId);
		Cursor c = database.rawQuery("select "+ DoctorTable.C_ID_DOKTER+" from "+DoctorTable.TABLE_NAME+ " where "+DoctorTable.C_ID_USER+ "=?", new String[]{""+userId});
		c.moveToFirst();
		int result = c.getInt(0);
		c.close();
		return result;
	}
	
	public int getPetugasId(String userId) {
		Log.d("USG DB Helper", "get petugas IdUser:"+userId);
		Cursor c = database.rawQuery("select "+ OfficerTable.C_ID_PETUGAS+" from "+OfficerTable.TABLE_NAME+ " where "+OfficerTable.C_ID_USER+ "=?", new String[]{""+userId});
		c.moveToFirst();
		int result = c.getInt(0);
		c.close();
		return result;
	}
	
	public Cursor getPatientOverview(int puskesmasId){
		return database.rawQuery("select a."+PatientTable.C_ID+", a."+PatientTable.C_NAME+", b.totalfoto, b.terakhir, c.totalpesanbaru, d.totalvalidasibaru"
				+ " from (select x."+ PatientTable.C_ID+ ", x."+PatientTable.C_NAME+" from "+PatientTable.TABLE_NAME+" x, "+ServeTable.TABLE_NAME+" y,"+ClinicTable.TABLE_NAME+" z where x."+PatientTable.C_ID+" = y."+ServeTable.C_ID_PASIEN+" and y."+ServeTable.C_ID_PUSKESMAS+" = z."+ClinicTable.C_ID+" and z."+ClinicTable.C_ID+" =?) a left outer join "
				+ "(select "+PhotoTable.C_ID_PASIEN+" as id_pasien, count(*) as totalfoto ,max("+PhotoTable.C_CREATESTAMP+") as terakhir from "+PhotoTable.TABLE_NAME+" group by "+PhotoTable.C_ID_PASIEN+")b on a."+PatientTable.C_ID+" = b.id_pasien left outer join "
				+ "(select "+CommentTable.C_ID_USGPASIEN+" as id_pasien, count(*) as totalpesanbaru from "+CommentTable.TABLE_NAME+" where "+CommentTable.C_HASSEEN+" = 0 and "+CommentTable.C_FROM_DOCTOR+"=1 group by "+CommentTable.C_ID_USGPASIEN+") c on a."+PatientTable.C_ID+" = c.id_pasien left outer join "
				+ "(select "+ValidationTable.C_ID_USGPASIEN+" as id_pasien, count(*) as totalvalidasibaru from "+ValidationTable.TABLE_NAME+" where "+ValidationTable.C_HASSEEN+" = 0 group by "+ValidationTable.C_ID_USGPASIEN+") d on a."+PatientTable.C_ID+" = d.id_pasien", new String[]{""+puskesmasId});
	}
	
	public Cursor getPatientOverviewDoctor(String ktpDoctor){
		return database.rawQuery("select distinct(a."+PatientTable.C_ID+"), a."+PatientTable.C_NAME+", b.totalfoto, b.terakhir, c.totalpesanbaru, d.totalvalidasibaru"
				+ " from (select x."+ PatientTable.C_ID+ ", x."+PatientTable.C_NAME+" from "+PatientTable.TABLE_NAME+" x, "+ServeTable.TABLE_NAME+" y,"+ClinicTable.TABLE_NAME+" z where x."+PatientTable.C_ID+" = y."+ServeTable.C_ID_PASIEN+" and y."+ServeTable.C_ID_PUSKESMAS+" = z."+ClinicTable.C_ID+" and z."+ClinicTable.C_ID+" in (select CC."+ClinicTable.C_ID+" from "+ClinicTable.TABLE_NAME+" CC, "+DoctorTable.TABLE_NAME+" DD, "+WorksOnTable.TABLE_NAME+" WW where CC."+ClinicTable.C_ID+" = WW."+WorksOnTable.C_ID_PUSKESMAS+" and DD."+DoctorTable.C_ID_USER+" = WW."+WorksOnTable.C_ID_DOKTER+" and DD."+DoctorTable.C_ID_USER+"=?)) a left outer join "
				+ "(select "+PhotoTable.C_ID_PASIEN+" as id_pasien, count(*) as totalfoto ,max("+PhotoTable.C_CREATESTAMP+") as terakhir from "+PhotoTable.TABLE_NAME+" group by "+PhotoTable.C_ID_PASIEN+")b on a."+PatientTable.C_ID+" = b.id_pasien left outer join "
				+ "(select "+CommentTable.C_ID_USGPASIEN+" as id_pasien, count(*) as totalpesanbaru from "+CommentTable.TABLE_NAME+" where "+CommentTable.C_HASSEEN+" = 0 and "+CommentTable.C_ID_DOKTER+" <> ? "+" group by "+CommentTable.C_ID_USGPASIEN+") c on a."+PatientTable.C_ID+" = c.id_pasien left outer join "
				+ "(select "+ValidationTable.C_ID_USGPASIEN+" as id_pasien, count(*) as totalvalidasibaru from "+ValidationTable.TABLE_NAME+" where "+ValidationTable.C_HASSEEN+" = 0 group by "+ValidationTable.C_ID_USGPASIEN+") d on a."+PatientTable.C_ID+" = d.id_pasien", new String[]{ktpDoctor, ktpDoctor});
	}
	

	
	public int getPhotoNumberOf(String serverPatientKtp, int serverPregnancyNumber, int serverPhotoNumber){
		Cursor c = database.query(PhotoTable.TABLE_NAME, new String[]{PhotoTable.C_ID}, 
				PhotoTable.C_SERVER_ID+"=? AND "+PhotoTable.C_SERVER_ID2_PASIEN+"=? AND "+PhotoTable.C_SERVER_ID3_KANDUNGAN+"=? ", 
				new String[]{""+serverPhotoNumber, ""+serverPatientKtp, ""+serverPregnancyNumber}, null, null, null);
		int result = -1;
		if(c.getCount() > 0){
			c.moveToFirst();
			result = c.getInt(0);
		}
		c.close();
		return result;
	}
	
	public Cursor getKandunganOfPatient(String idPasien){
		return database.query(PregnancyTable.TABLE_NAME, kandunganTable.getAllColumns(), PregnancyTable.C_ID_PASIEN+"=? and "+PregnancyTable.C_ISACTIVE+"=1", new String[]{""+idPasien}, null, null, null);
	}
	
	public Cursor getPhotosOfKandungan(String idPasien, int noKandungan){
		return database.query(PhotoTable.TABLE_NAME, usgFotoTable.getAllColumns(), PhotoTable.C_ID_PASIEN+"=? and "+PhotoTable.C_ID_KANDUNGAN + "=? and "+PhotoTable.C_ISACTIVE+"=1", new String[]{""+idPasien, ""+noKandungan}, null, null,null);
	}
	
	public Cursor getPhotos(String patientId, int kandunganId, int photoId){
		Log.d("Get Photo Item", "Pat:"+patientId + "- kan:"+kandunganId+" - phot:"+photoId);
		return usgFotoTable.getItem(database, new String[]{""+photoId, patientId, ""+kandunganId});
	}
	
	public Cursor getKomentarOf(String idPasien){
		return database.rawQuery("select a.nama, b."+CommentTable.C_FROM_DOCTOR+", b."+CommentTable.C_CONTENT+", b."+CommentTable.C_CREATESTAMP+", b."+CommentTable.C_HASSEEN+
				" from "+ CommentTable.TABLE_NAME+" b left outer join (select c."+DoctorTable.C_ID_USER+" as id_user, d."+UserTable.C_NAME+" as nama from "+DoctorTable.TABLE_NAME+" c, "+UserTable.TABLE_NAME+" d where c."+DoctorTable.C_ID_USER+" = d."+UserTable.C_ID+") a "+
				 "on  a.id_user = b."+CommentTable.C_ID_DOKTER+" where  b."+CommentTable.C_ID_USGPASIEN+" =?", new String[]{""+idPasien});
	}

	public Cursor getPasien(String patientId) {
		return pasienTable.getItem(database, new String[]{""+patientId});
	}
	
	public Cursor getPhoto(String ktp, int pregnancyNumber, int photoNumber) {
		return usgFotoTable.getItem(database, new String[]{""+photoNumber, ktp, ""+pregnancyNumber});
	}

	public Cursor getUser(String ktp) {
		return userTable.getItem(database, new String[]{ktp});
	}
	
	public Cursor getDoctor(String ktp) {
		return dokterTable.getItem(database, new String[]{ktp});
	}
	
	public Cursor getOfficer(String ktp) {
		return petugasTable.getItem(database, new String[]{ktp});
	}
	
	public Cursor getClinic(int clinicId) {
		return puskesmasTable.getItem(database, new String[]{""+clinicId});
	}
	
	public Cursor getComment(String ktp, int commentNumber) {
		return komentarTable.getItem(database, new String[]{""+commentNumber, ktp});
	}
	
	public Cursor getValidations(int photoNumber, int pregnancyNumber, String userKtp) {
		return database.query(ValidationTable.TABLE_NAME, validasiTable.getAllColumns(), 
				ValidationTable.C_ID_USGFOTO+"=? and "+ValidationTable.C_ID_USGKANDUNGAN + 
				"=? and "+ValidationTable.C_ID_USGPASIEN+"=?", new String[]{""+photoNumber, ""+pregnancyNumber, userKtp}, 
				null, null, null);
	}
	
	public Cursor getValidation(String doctorKtp, int photoNumber, int pregnancyNumber, String userKtp) {
		return validasiTable.getItem(database, new String[]{doctorKtp, ""+photoNumber, ""+pregnancyNumber, userKtp});
	}
	
	public int getLastIndexofKomentar(String pasienId){
		return komentarTable.getLastLocalIndex(database, pasienId);
	}
	
	public int getLastIndexOfPhotos(String patientId, int kandunganId) {
		return usgFotoTable.getLastLocalIndex(database, patientId, kandunganId);
	}
	
	public int getLastIndexOfPatient(){
		return pasienTable.getLastLocalIndex(database);
	}
	
	public boolean updateUser(User u){
		return userTable.update(database, u);
	}
	
	public boolean updateDoctor(Doctor d){
		return dokterTable.update(database, d);
	}
	
	public boolean updateOfficer(Officer o){
		return petugasTable.update(database, o);
	}
	
	public boolean updateClinic(Clinic c){
		return puskesmasTable.update(database, c);
	}
	
	public boolean updateWorksOn(WorksOn w){
		return bjTable.update(database, w);
	}
	
	public boolean updateComment(Comment k){
		return komentarTable.update(database, k);
	}
	
	public boolean updateValidation(Validation v){
		return validasiTable.update(database, v);
	}
	
	public boolean updatePatient(Patient p){
		return pasienTable.update(database, p);
	}

	public boolean updateServe(Serve m){
		return melayaniTable.update(database, m);
	}

	public boolean updatePregnancy(Pregnancy k) {
		return kandunganTable.update(database, k);
		
	}
	
	public boolean updatePhoto(Photo uf){
		return usgFotoTable.update(database, uf);
	}
	
	public int insertUser(User u){
		return userTable.insert(database, u);
	}
	
	public int insertDoctor(Doctor d){
		return dokterTable.insert(database, d);
	}
	
	public int insertOfficer(Officer o){
		return petugasTable.insert(database, o);
	}
	
	public int insertClinic(Clinic c){
		return puskesmasTable.insert(database, c);
	}
	
	public int insertWorksOn(WorksOn w){
		return bjTable.insert(database, w);
	}
	
	public int insertComment(Comment k){
		return komentarTable.insert(database, k);
	}
	
	public int insertValidation(Validation v){
		return validasiTable.insert(database, v);
	}
	
	public int insertPatient(Patient p){
		return pasienTable.insert(database, p);
	}

	public int insertServe(Serve m){
		return melayaniTable.insert(database, m);
	}

	public int insertPregnancy(Pregnancy k) {
		return kandunganTable.insert(database, k);
		
	}
	
	public int insertPhoto(Photo uf){
		return usgFotoTable.insert(database, uf);
	}
	
	public Cursor getNewUser(long lastSync){
		return userTable.getCursorOfNewItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getNewPatient(long lastSync){
		return pasienTable.getCursorOfNewItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getNewDoctor(long lastSync){
		return dokterTable.getCursorOfNewItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getNewOfficer(long lastSync){
		return petugasTable.getCursorOfNewItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getNewClinic(long lastSync){
		return puskesmasTable.getCursorOfNewItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getNewPregnancy(long lastSync){
		return kandunganTable.getCursorOfNewItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getNewPhoto(long lastSync){
		return usgFotoTable.getCursorOfNewItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public Cursor getNewServe(long lastSync){
		return melayaniTable.getCursorOfNewItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getNewWorksOn(long lastSync){
		return bjTable.getCursorOfNewItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getNewValidation(long lastSync){
		return validasiTable.getCursorOfNewItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public Cursor getNewComment(long lastSync){
		return komentarTable.getCursorOfNewItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public Cursor getExpiredPatientPhoto(){
		return database.query(PatientTable.TABLE_NAME, pasienTable.getAllColumns(), 
				PatientTable.C_PHOTOTIMESTAMP+" < "+PatientTable.C_SERVERPHOTOTIMESTAMP+" and "+PatientTable.C_ISACTIVE+"=1", null, 
				null, null, null);
	}
	
	public Cursor getExpiredUSGPhoto(){
		return database.query(PhotoTable.TABLE_NAME, usgFotoTable.getAllColumns(), 
				PhotoTable.C_PHOTOTIMESTAMP+" <"+PhotoTable.C_SERVERPHOTOTIMESTAMP+" and "+PhotoTable.C_ISACTIVE+"=1", null, 
				null, null, null);
	}
	
	public Cursor getFreshPatientPhoto(){
		return database.query(PatientTable.TABLE_NAME, pasienTable.getAllColumns(), 
				PatientTable.C_PHOTOTIMESTAMP+" > "+PatientTable.C_SERVERPHOTOTIMESTAMP+" and "+PatientTable.C_ISACTIVE+"=1", null, 
				null, null, null);
	}
	
	public Cursor getFreshUSGPhoto(){
		return database.query(PhotoTable.TABLE_NAME, usgFotoTable.getAllColumns(), 
				PhotoTable.C_PHOTOTIMESTAMP+" > "+PhotoTable.C_SERVERPHOTOTIMESTAMP+" and "+PhotoTable.C_ISACTIVE+"=1", null, 
				null, null, null);
	}
	
	public Cursor getUpdateUser(long lastSync){
		return userTable.getCursorOfUpdateItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getUpdatePatient(long lastSync){
		return pasienTable.getCursorOfUpdateItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getUpdateDoctor(long lastSync){
		return dokterTable.getCursorOfUpdateItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getUpdateOfficer(long lastSync){
		return petugasTable.getCursorOfUpdateItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getUpdateClinic(long lastSync){
		return puskesmasTable.getCursorOfUpdateItems(database, new String[]{"-1",""+lastSync});
	}
	
	public Cursor getUpdatePregnancy(long lastSync){
		return kandunganTable.getCursorOfUpdateItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getUpdatePhoto(long lastSync){
		return usgFotoTable.getCursorOfUpdateItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public Cursor getUpdateServe(long lastSync){
		return melayaniTable.getCursorOfUpdateItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getUpdateWorksOn(long lastSync){
		return bjTable.getCursorOfUpdateItems(database, new String[]{"-1","-1",""+lastSync});
	}
	
	public Cursor getUpdateValidation(long lastSync){
		return validasiTable.getCursorOfUpdateItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public Cursor getUpdateComment(long lastSync){
		return komentarTable.getCursorOfUpdateItems(database, new String[]{"-1","-1","-1","-1",""+lastSync});
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public UserTable getUserTable() {
		return userTable;
	}

	public ClinicTable getPuskesmasTable() {
		return puskesmasTable;
	}

	public OfficerTable getPetugasTable() {
		return petugasTable;
	}

	public DoctorTable getDokterTable() {
		return dokterTable;
	}

	public PatientTable getPasienTable() {
		return pasienTable;
	}

	public PregnancyTable getKandunganTable() {
		return kandunganTable;
	}

	public PhotoTable getUsgFotoTable() {
		return usgFotoTable;
	}

	public WorksOnTable getBjTable() {
		return bjTable;
	}

	public ServeTable getMelayaniTable() {
		return melayaniTable;
	}

	public CommentTable getKomentarTable() {
		return komentarTable;
	}

	public ValidationTable getValidasiTable() {
		return validasiTable;
	}

	public boolean isKandunganExist(String patientId, int noKandungan) {
		Cursor c = kandunganTable.getItem(database, new String[]{""+noKandungan, ""+patientId});
		boolean isExist = c.getCount() > 0;
		c.close();
		return isExist;
	}
	
	
	public Cursor getClinicByServerId(String[] serverIdArgs){
		return puskesmasTable.getItemOnServerId(database, serverIdArgs);
	}

	public Cursor getCommentByServerId(String[] serverIdArgs){
		return komentarTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getDoctorByServerId(String[] serverIdArgs){
		return dokterTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getOfficerByServerId(String[] serverIdArgs){
		return petugasTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getPatientByServerId(String[] serverIdArgs){
		return pasienTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getPregnancyByServerId(String[] serverIdArgs){
		return kandunganTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getPhotoByServerId(String[] serverIdArgs){
		return usgFotoTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getServeByServerId(String[] serverIdArgs){
		return melayaniTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getUserByServerId(String[] serverIdArgs){
		return userTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getValidationByServerId(String[] serverIdArgs){
		return validasiTable.getItemOnServerId(database, serverIdArgs);
	}
	public Cursor getWorksOnByServerId(String[] serverIdArgs){
		return bjTable.getItemOnServerId(database, serverIdArgs);
	}
	
	public void deletePatient(String patientId){
		ContentValues cv = new ContentValues();
		long timestamp = DateUtils.getCurrentLong();
		cv.put(USGTable.C_MODIFYSTAMP, timestamp);
		cv.put(USGTable.C_ISACTIVE, 0);
		cv.put(USGTable.C_DIRTY, 1);
		String[] args = {patientId};
		database.update(PatientTable.TABLE_NAME, cv, pasienTable.getPrimaryClause(), args);
		database.update(PregnancyTable.TABLE_NAME, cv, PregnancyTable.C_ID_PASIEN+"=?", args);
		database.update(PhotoTable.TABLE_NAME, cv, PhotoTable.C_ID_PASIEN+"=?", args);
		database.update(CommentTable.TABLE_NAME, cv, CommentTable.C_ID_USGPASIEN+"=?", args);
		database.update(ValidationTable.TABLE_NAME, cv, ValidationTable.C_ID_USGPASIEN+"=?", args);		
	}
	
	public void deletePregnancy(String patientId, int pregnancyId) {
		ContentValues cv = new ContentValues();
		cv.put(USGTable.C_ISACTIVE, 0);
		cv.put(USGTable.C_DIRTY, 1);
		long timestamp = DateUtils.getCurrentLong();
		cv.put(USGTable.C_MODIFYSTAMP, timestamp);
		String[] args = {patientId, ""+pregnancyId};
		database.update(PregnancyTable.TABLE_NAME, cv, PregnancyTable.C_ID_PASIEN+"=? and "+PregnancyTable.C_ID+"=?", args);
		database.update(PhotoTable.TABLE_NAME, cv, PhotoTable.C_ID_PASIEN+"=? and "+PhotoTable.C_ID_KANDUNGAN+"=?", args);
		database.update(ValidationTable.TABLE_NAME, cv, ValidationTable.C_ID_USGPASIEN+"=? and "+ValidationTable.C_ID_USGKANDUNGAN+"=?", args);			
	}

	public void deletePhoto(String patientId, int pregnancyId, int photoId) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		long timestamp = DateUtils.getCurrentLong();
		cv.put(USGTable.C_MODIFYSTAMP, timestamp);
		cv.put(USGTable.C_ISACTIVE, 0);
		cv.put(USGTable.C_DIRTY, 1);
		String[] args = {patientId, ""+pregnancyId, ""+photoId};
		database.update(PhotoTable.TABLE_NAME, cv, PhotoTable.C_ID_PASIEN+"=? and "+PhotoTable.C_ID_KANDUNGAN+"=? and "+PhotoTable.C_ID+"=?", args);
		database.update(ValidationTable.TABLE_NAME, cv, ValidationTable.C_ID_USGPASIEN+"=? and "+ValidationTable.C_ID_USGKANDUNGAN+"=? and "+ValidationTable.C_ID_USGFOTO+"=?", args);
	}

	public void updateValidationPhotoNumber(String ktp, int pregnancyNumber,
			int localPhotoNumber, int serverPhotoNumber) {
		ContentValues cv = new ContentValues();
		cv.put(ValidationTable.C_SERVER_ID2_FOTO, serverPhotoNumber);
		String[] args = {ktp, ""+pregnancyNumber, ""+localPhotoNumber};
		database.update(ValidationTable.TABLE_NAME, cv, ValidationTable.C_ID_USGPASIEN+"=? and "+ValidationTable.C_ID_USGKANDUNGAN+"=? and "+ValidationTable.C_ID_USGFOTO+"=?", args);
	}

	public void markMessagesAsOfficer(String patientId) {
		ContentValues cv = new ContentValues();
		long timestamp = DateUtils.getCurrentLong();
		cv.put(USGTable.C_MODIFYSTAMP, timestamp);
		cv.put(CommentTable.C_HASSEEN, 1);
		cv.put(USGTable.C_DIRTY, 1);
		database.update(CommentTable.TABLE_NAME, cv, CommentTable.C_FROM_DOCTOR+"=1 and "+CommentTable.C_ID_USGPASIEN+"=? and "+CommentTable.C_HASSEEN+"=0", new String[]{patientId});
	}
	
	public void markMessagesAsDoctor(String patientId, String userId) {
		ContentValues cv = new ContentValues();
		long timestamp = DateUtils.getCurrentLong();
		cv.put(USGTable.C_MODIFYSTAMP, timestamp);
		cv.put(CommentTable.C_HASSEEN, 1);
		cv.put(USGTable.C_DIRTY, 1);
		database.update(CommentTable.TABLE_NAME, cv, CommentTable.C_HASSEEN+"=0 and "+CommentTable.C_ID_USGPASIEN+"=? and ("+CommentTable.C_FROM_DOCTOR+"=0 or "+CommentTable.C_ID_DOKTER+" <> ?)", new String[]{patientId, userId});
	}

	public void echo() {
		// TODO Auto-generated method stub
		Log.d("User", UserTable.CREATE_STATEMENT);
		Log.d("Officer", OfficerTable.CREATE_STATEMENT);
		Log.d("Doctor", DoctorTable.CREATE_STATEMENT);
		Log.d("Clinic", ClinicTable.CREATE_STATEMENT);
		Log.d("Patient", PatientTable.CREATE_STATEMENT);
		Log.d("Pregnancy", PregnancyTable.CREATE_STATEMENT);
		Log.d("Photo", PhotoTable.CREATE_STATEMENT);
		Log.d("Serve", ServeTable.CREATE_STATEMENT);
		Log.d("WorksOn", WorksOnTable.CREATE_STATEMENT);
		Log.d("Validation", ValidationTable.CREATE_STATEMENT);
		Log.d("Comment", CommentTable.CREATE_STATEMENT);
	}
}
