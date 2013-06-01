package id.fruity.usg.remote;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.ClinicConverter;
import id.fruity.usg.database.converter.CommentConverter;
import id.fruity.usg.database.converter.DoctorConverter;
import id.fruity.usg.database.converter.OfficerConverter;
import id.fruity.usg.database.converter.PatientConverter;
import id.fruity.usg.database.converter.PhotoConverter;
import id.fruity.usg.database.converter.PregnancyConverter;
import id.fruity.usg.database.converter.ServeConverter;
import id.fruity.usg.database.converter.UserConverter;
import id.fruity.usg.database.converter.ValidationConverter;
import id.fruity.usg.database.converter.WorksOnConverter;
import id.fruity.usg.database.table.entry.Clinic;
import id.fruity.usg.database.table.entry.Comment;
import id.fruity.usg.database.table.entry.Doctor;
import id.fruity.usg.database.table.entry.Officer;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Pregnancy;
import id.fruity.usg.database.table.entry.Serve;
import id.fruity.usg.database.table.entry.USGTableEntry;
import id.fruity.usg.database.table.entry.User;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.database.table.entry.WorksOn;

import java.util.ArrayList;

import android.app.Activity;

public class Synchonization {
	public static Activity A;
	private static USGDBHelper helper;
	
	public static ArrayList<User> getNewUser(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<User> users = UserConverter.convertAll(helper.getNewUser(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Patient> getNewPatient(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Patient> users = PatientConverter.convertAll(helper.getNewPatient(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Doctor> getNewDoctor(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Doctor> users = DoctorConverter.convertAll(helper.getNewDoctor(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Officer> getNewOfficer(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Officer> users = OfficerConverter.convertAll(helper.getNewOfficer(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Clinic> getNewClinic(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Clinic> users = ClinicConverter.convertAll(helper.getNewClinic(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Pregnancy> getNewPregnancy(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Pregnancy> users = PregnancyConverter.convertAll(helper.getNewPregnancy(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Photo> getNewPhoto(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Photo> users = PhotoConverter.convertAll(helper.getNewPhoto(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Serve> getNewServe(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Serve> users = ServeConverter.convertAll(helper.getNewServe(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<WorksOn> getNewWorksOn(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<WorksOn> users = WorksOnConverter.convertAll(helper.getNewWorksOn(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Validation> getNewValidation(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Validation> users = ValidationConverter.convertAll(helper.getNewValidation(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Comment> getNewComment(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Comment> users = CommentConverter.convertAll(helper.getNewComment(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<User> getUpdateUser(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<User> users = UserConverter.convertAll(helper.getUpdateUser(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Patient> getUpdatePatient(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Patient> users = PatientConverter.convertAll(helper.getUpdatePatient(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Doctor> getUpdateDoctor(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Doctor> users = DoctorConverter.convertAll(helper.getUpdateDoctor(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Officer> getUpdateOfficer(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Officer> users = OfficerConverter.convertAll(helper.getUpdateOfficer(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Clinic> getUpdateClinic(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Clinic> users = ClinicConverter.convertAll(helper.getUpdateClinic(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Pregnancy> getUpdatePregnancy(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Pregnancy> users = PregnancyConverter.convertAll(helper.getUpdatePregnancy(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Photo> getUpdatePhoto(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Photo> users = PhotoConverter.convertAll(helper.getUpdatePhoto(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Serve> getUpdateServe(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Serve> users = ServeConverter.convertAll(helper.getUpdateServe(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<WorksOn> getUpdateWorksOn(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<WorksOn> users = WorksOnConverter.convertAll(helper.getUpdateWorksOn(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Validation> getUpdateValidation(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Validation> users = ValidationConverter.convertAll(helper.getUpdateValidation(lastSync));
		helper.close();
		return users;
	}
	
	public static ArrayList<Comment> getUpdateComment(long lastSync) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		ArrayList<Comment> users = CommentConverter.convertAll(helper.getUpdateComment(lastSync));
		helper.close();
		return users;
	}
	
	
	public static void updateUserEntriesFromServer(User[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			User e = UserConverter.convert(helper.getUserByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateUser(e);
			}
		}
		helper.close();
	}
	
	public static void updateClinicEntriesFromServer(Clinic[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Clinic e = ClinicConverter.convert(helper.getClinicByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateClinic(e);
			}
		}
		helper.close();
	}
	
	public static void updateOfficerEntriesFromServer(Officer[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Officer e = OfficerConverter.convert(helper.getOfficerByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateOfficer(e);
			}
		}
		helper.close();
	}
	
	public static void updateDoctorEntriesFromServer(Doctor[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Doctor e = DoctorConverter.convert(helper.getDoctorByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateDoctor(e);
			}
		}
		helper.close();
	}
	
	public static void updatePatientEntriesFromServer(Patient[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Patient e = PatientConverter.convert(helper.getPatientByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updatePatient(e);
			}
		}
		helper.close();
	}
	
	public static void updatePregnancyEntriesFromServer(Pregnancy[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Pregnancy e = PregnancyConverter.convert(helper.getPregnancyByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updatePregnancy(e);
			}
		}
		helper.close();
	}
	
	public static void updatePhotoEntriesFromServer(Photo[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Photo e = PhotoConverter.convert(helper.getPhotoByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updatePhoto(e);
			}
		}
		helper.close();
	}
	
	public static void updateServeEntriesFromServer(Serve[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Serve e = ServeConverter.convert(helper.getServeByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateServe(e);
			}
		}
		helper.close();
	}
	
	public static void updateWorksOnEntriesFromServer(WorksOn[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			WorksOn e = WorksOnConverter.convert(helper.getWorksOnByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateWorksOn(e);
			}
		}
		helper.close();
	}
	
	public static void updateCommentEntriesFromServer(Comment[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Comment e = CommentConverter.convert(helper.getCommentByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateComment(e);
			}
		}
		helper.close();
	}
	
	public static void updateValidationEntriesFromServer(Validation[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			Validation e = ValidationConverter.convert(helper.getValidationByServerId(entries[i].getServerIdArgs()));
			if(e != null){
				e.onUpdatedByServer(entries[i]);
				helper.updateValidation(e);
			}
		}
		helper.close();
	}
	
	public static void addUserEntriesFromServer(User[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertUser(entries[i]);
		}
		helper.close();
	}
	
	public static void addClinicEntriesFromServer(Clinic[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertClinic(entries[i]);
		}
		helper.close();
	}
	
	public static void addOfficerEntriesFromServer(Officer[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertOfficer(entries[i]);
		}
		helper.close();
	}
	
	public static void addDoctorEntriesFromServer(Doctor[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertDoctor(entries[i]);
		}
		helper.close();
	}
	
	public static void addPatientEntriesFromServer(Patient[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertPatient(entries[i]);
		}
		helper.close();
	}
	
	public static void addPregnancyEntriesFromServer(Pregnancy[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertPregnancy(entries[i]);
		}
		helper.close();
	}
	
	public static void addPhotoEntriesFromServer(Photo[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			int lastPhotoNumber = helper.getLastIndexOfPhotos(entries[i].getIdPasien(), entries[i].getNoKandungan());
			entries[i].onServerAdd(lastPhotoNumber+1);
			helper.insertPhoto(entries[i]);
		}
		helper.close();
	}
	
	public static void addServeEntriesFromServer(Serve[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertServe(entries[i]);
		}
		helper.close();
	}
	
	public static void addWorksOnEntriesFromServer(WorksOn[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			entries[i].onServerAdd();
			helper.insertWorksOn(entries[i]);
		}
		helper.close();
	}
	
	public static void addCommentEntriesFromServer(Comment[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			int lastNumber = helper.getLastIndexofKomentar(entries[i].getIdPasien());
			entries[i].onServerAdd(lastNumber+1);
			helper.insertComment(entries[i]);
		}
		helper.close();
	}
	
	public static void addValidationEntriesFromServer(Validation[] entries){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(int i = 0; i < entries.length; i++){
			int localPhotoNumber = helper.getPhotoNumberOf(entries[i].getServerId4(), entries[i].getServerId2(), entries[i].getServerId2());
			entries[i].onServerAdd(localPhotoNumber);
			helper.insertValidation(entries[i]);
		}
		helper.close();
	}
	
	public static <T extends USGTableEntry> void onNewEntriesSent(ArrayList<T> entries) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(T e : entries){
			e.onSentSuccess();
			if(e instanceof Clinic)
				helper.updateClinic((Clinic)e);
			else if (e instanceof Comment)
				helper.updateComment((Comment)e);
			else if (e instanceof Doctor)
				helper.updateDoctor((Doctor)e);
			else if (e instanceof Officer)
				helper.updateOfficer((Officer)e);
			else if (e instanceof Patient)
				helper.updatePatient((Patient)e);
			else if (e instanceof Photo)
				helper.updatePhoto((Photo)e);
			else if (e instanceof Pregnancy)
				helper.updatePregnancy((Pregnancy)e);
			else if (e instanceof Serve)
				helper.updateServe((Serve)e);
			else if (e instanceof User)
				helper.updateUser((User)e);
			else if (e instanceof WorksOn)
				helper.updateWorksOn((WorksOn)e);
			else if (e instanceof Validation)
				helper.updateValidation((Validation)e);
		}
		helper.close();
	}
	
	public static <T extends USGTableEntry> void onUpdateEntriesSent(ArrayList<T> entries) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		for(T e : entries){
			e.setDirty(false);
			if(e instanceof Clinic)
				helper.updateClinic((Clinic)e);
			else if (e instanceof Comment)
				helper.updateComment((Comment)e);
			else if (e instanceof Doctor)
				helper.updateDoctor((Doctor)e);
			else if (e instanceof Officer)
				helper.updateOfficer((Officer)e);
			else if (e instanceof Patient)
				helper.updatePatient((Patient)e);
			else if (e instanceof Photo)
				helper.updatePhoto((Photo)e);
			else if (e instanceof Pregnancy)
				helper.updatePregnancy((Pregnancy)e);
			else if (e instanceof Serve)
				helper.updateServe((Serve)e);
			else if (e instanceof User)
				helper.updateUser((User)e);
			else if (e instanceof WorksOn)
				helper.updateWorksOn((WorksOn)e);
			else if (e instanceof Validation)
				helper.updateValidation((Validation)e);
		}
		helper.close();
	}
	
	public static void updatePhotoServerId(String ktp, int pregnancyNumber, int localPhotoNumber, int serverPhotoNumber){
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		Photo p = PhotoConverter.convert(helper.getPhoto(ktp, pregnancyNumber, localPhotoNumber));
		if(p!=null){
			p.onSentSuccessCont(serverPhotoNumber);
			helper.updatePhoto(p);
		}
		helper.close();
	}

	public static void updateCommentServerId(String ktp,
			int localCommentNumber, int serverCommentNumber) {
		if(helper == null){
			helper = new USGDBHelper(A);
		}
		helper.open();
		Comment c = CommentConverter.convert(helper.getComment(ktp, localCommentNumber));
		if(c!=null){
			c.onSentSuccessCont(serverCommentNumber);
			helper.updateComment(c);
		}
		helper.close();
	}
	
}
