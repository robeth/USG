package id.fruity.usg.remote;

import id.fruity.usg.PatientListActivity;
import id.fruity.usg.database.table.entry.Clinic;
import id.fruity.usg.database.table.entry.Comment;
import id.fruity.usg.database.table.entry.Doctor;
import id.fruity.usg.database.table.entry.Officer;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Pregnancy;
import id.fruity.usg.database.table.entry.Serve;
import id.fruity.usg.database.table.entry.User;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.database.table.entry.WorksOn;
import id.fruity.usg.util.Preference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RemoteUtils {
	private static final String TAG = "RemoteUtils";
	private static final String TEST1 = "http://192.168.43.211:8000/polls/update/1/";
	private static final String BASE_URL = "http://192.168.43.211:8000/polls/update/";
	private static ArrayList<User> newUsers, updateUsers;
	private static ArrayList<Patient> newPatients, updatePatients;
	private static ArrayList<Pregnancy> newPregnancies, updatePregnancies;
	private static ArrayList<Photo> newPhotos, updatePhotos;
	private static ArrayList<Doctor> newDoctors, updateDoctors;
	private static ArrayList<Clinic> newClinics, updateClinics;
	private static ArrayList<Officer> newOfficers, updateOfficers;
	private static ArrayList<Serve> newServes, updateServes;
	private static ArrayList<WorksOn> newWorksOns, updateWorksOns;
	private static ArrayList<Validation> newValidations, updateValidations;
	private static ArrayList<Comment> newComments, updateComments;

	private static String synchronize() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + lastSync + "/";
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); 
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
		try{
			HttpPost post = new HttpPost(url);
			
			//Create request JSON
			
			//Timestamp
			json.put("timestamp", lastSync);
			//Add Tables
			JSONObject add = new JSONObject();
			
			//UserTable
			JSONArray addUserTable = new JSONArray();
			newUsers = Synchonization.getNewUser(lastSync);
			for(User user : newUsers){
				addUserTable.put(new JSONObject(gson.toJson(user)));
			}
			JSONArray addPatientTable = new JSONArray();
			newPatients = Synchonization.getNewPatient(lastSync);
			for(Patient p : newPatients){
				addPatientTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addPregnancyTable = new JSONArray();
			newPregnancies = Synchonization.getNewPregnancy(lastSync);
			for(Pregnancy p : newPregnancies){
				addPregnancyTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addPhotoTable = new JSONArray();
			newPhotos = Synchonization.getNewPhoto(lastSync);
			for(Photo p : newPhotos){
				addPhotoTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addDoctorTable = new JSONArray();
			newDoctors = Synchonization.getNewDoctor(lastSync);
			for(Doctor d : newDoctors){
				addDoctorTable.put(new JSONObject(gson.toJson(d)));
			}
			JSONArray addClinicTable = new JSONArray();
			newClinics = Synchonization.getNewClinic(lastSync);
			for(Clinic c : newClinics){
				addClinicTable.put(new JSONObject(gson.toJson(c)));
			}
			JSONArray addOfficerTable = new JSONArray();
			newOfficers = Synchonization.getNewOfficer(lastSync);
			for(Officer o : newOfficers){
				addOfficerTable.put(new JSONObject(gson.toJson(o)));
			}
			JSONArray addServeTable = new JSONArray();
			newServes = Synchonization.getNewServe(lastSync);
			for(Serve s : newServes){
				addServeTable.put(new JSONObject(gson.toJson(s)));
			}
			JSONArray addWorksOnTable = new JSONArray();
			newWorksOns = Synchonization.getNewWorksOn(lastSync);
			for(WorksOn w : newWorksOns){
				addWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}
			JSONArray addValidationTable = new JSONArray();
			newValidations = Synchonization.getNewValidation(lastSync);
			for(Validation v : newValidations){
				addValidationTable.put(new JSONObject(gson.toJson(v)));
			}
			JSONArray addCommentTable = new JSONArray();
			newComments = Synchonization.getNewComment(lastSync);
			for(Comment c : newComments){
				addCommentTable.put(new JSONObject(gson.toJson(c)));
			}
			
			//Update Tables
			JSONObject update = new JSONObject();
			
			//UserTable
			JSONArray updateUserTable = new JSONArray();
			updateUsers = Synchonization.getUpdateUser(lastSync);
			for(User user : updateUsers){
				updateUserTable.put(new JSONObject(gson.toJson(user)));
			}
			JSONArray updatePatientTable = new JSONArray();
			updatePatients = Synchonization.getUpdatePatient(lastSync);
			for(Patient p : updatePatients){
				updatePatientTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updatePregnancyTable = new JSONArray();
			updatePregnancies = Synchonization.getUpdatePregnancy(lastSync);
			for(Pregnancy p : updatePregnancies){
				updatePregnancyTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updatePhotoTable = new JSONArray();
			updatePhotos = Synchonization.getUpdatePhoto(lastSync);
			for(Photo p : updatePhotos){
				updatePhotoTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updateDoctorTable = new JSONArray();
			updateDoctors = Synchonization.getUpdateDoctor(lastSync);
			for(Doctor d : updateDoctors){
				updateDoctorTable.put(new JSONObject(gson.toJson(d)));
			}
			JSONArray updateClinicTable = new JSONArray();
			updateClinics= Synchonization.getUpdateClinic(lastSync);
			for(Clinic c : updateClinics){
				updateClinicTable.put(new JSONObject(gson.toJson(c)));
			}
			JSONArray updateOfficerTable = new JSONArray();
			updateOfficers = Synchonization.getUpdateOfficer(lastSync);
			for(Officer o : updateOfficers){
				updateOfficerTable.put(new JSONObject(gson.toJson(o)));
			}
			JSONArray updateServeTable = new JSONArray();
			updateServes= Synchonization.getUpdateServe(lastSync);
			for(Serve s : updateServes){
				updateServeTable.put(new JSONObject(gson.toJson(s)));
			}
			JSONArray updateWorksOnTable = new JSONArray();
			updateWorksOns= Synchonization.getUpdateWorksOn(lastSync);
			for(WorksOn w : updateWorksOns){
				updateWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}
			JSONArray updateValidationTable = new JSONArray();
			updateValidations = Synchonization.getUpdateValidation(lastSync);
			for(Validation v : updateValidations){
				updateValidationTable.put(new JSONObject(gson.toJson(v)));
			}
			JSONArray updateCommentTable = new JSONArray();
			updateComments = Synchonization.getUpdateComment(lastSync);
			for(Comment c : updateComments){
				updateCommentTable.put(new JSONObject(gson.toJson(c)));
			}
			
			add.put("user", addUserTable);
			add.put("doctor", addDoctorTable);
			add.put("clinic", addClinicTable);
			add.put("officer", addOfficerTable);
			add.put("patient", addPatientTable);
			add.put("pregnancy", addPregnancyTable);
			add.put("photo", addPhotoTable);
			add.put("serve", addServeTable);
			add.put("works_on", addWorksOnTable);
			add.put("validation", addValidationTable);
			add.put("comment", addCommentTable);
			
			update.put("user", updateUserTable);
			update.put("doctor", updateDoctorTable);
			update.put("clinic", updateClinicTable);
			update.put("officer", updateOfficerTable);
			update.put("patient", updatePatientTable);
			update.put("pregnancy", updatePregnancyTable);
			update.put("photo", updatePhotoTable);
			update.put("serve", updateServeTable);
			update.put("works_on", updateWorksOnTable);
			update.put("validation", updateValidationTable);
			update.put("comment", updateCommentTable);
			
			json.put("add", add);
			json.put("update", update);
			
			Log.d("Send Proto", addPatientTable.toString());
			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);
	
			/* Checking response */
			if (response != null) {
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("RemoteUtils", "Failed to download file");
				}
			}
		} catch(Exception e){
			 e.printStackTrace();
	         Log.d("Error", e.getMessage());
		}

		return builder.toString();
	}
	
	
	public static void startAsyn(Activity a) {
		DownloadWebPageTask d = new DownloadWebPageTask();
		d.execute("Go!");
	}

	private static class DownloadWebPageTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {
			return synchronize();
		}

		@Override
		protected void onPostExecute(String result) {
			PatientListActivity.notifyResult(result);
			try {
				JSONObject root = new JSONObject(result);
				JSONObject addRoot = root.getJSONObject("add");
				JSONObject updateRoot = root.getJSONObject("update");
				JSONObject confirmAddRoot = root.getJSONObject("confirm_add");
				JSONArray photoConfirm = confirmAddRoot.getJSONArray("photo");
				JSONArray commentConfirm = confirmAddRoot.getJSONArray("comment");
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				
				//Confirm Handle
				Synchonization.onNewEntriesSent(newUsers);
				Synchonization.onNewEntriesSent(newDoctors);
				Synchonization.onNewEntriesSent(newClinics);
				Synchonization.onNewEntriesSent(newOfficers);
				Synchonization.onNewEntriesSent(newPatients);
				Synchonization.onNewEntriesSent(newPregnancies);
				Synchonization.onNewEntriesSent(newPhotos);
				Synchonization.onNewEntriesSent(newServes);
				Synchonization.onNewEntriesSent(newWorksOns);
				Synchonization.onNewEntriesSent(newValidations);
				Synchonization.onNewEntriesSent(newComments);
				
				for(int i = 0; i < photoConfirm.length(); i++ ){
					JSONObject j = photoConfirm.getJSONObject(i);
					int serverPhotoNumber = j.getInt("global_number");
					int localPhotoNumber = j.getInt("local_number");
					int pregnancyNumber = j.getInt("pregnancy_number");
					String ktp = j.getString("ktp");
					Synchonization.updatePhotoServerId(ktp, pregnancyNumber, localPhotoNumber, serverPhotoNumber);
				}
				
				for(int i = 0; i < commentConfirm.length(); i++ ){
					JSONObject j = commentConfirm.getJSONObject(i);
					int serverCommentNumber = j.getInt("global_number");
					int localCommentNumber = j.getInt("local_number");
					String ktp = j.getString("ktp");
					Synchonization.updateCommentServerId(ktp, localCommentNumber, serverCommentNumber);
				}
				
				Synchonization.onUpdateEntriesSent(updateUsers);
				Synchonization.onUpdateEntriesSent(updateDoctors);
				Synchonization.onUpdateEntriesSent(updateClinics);
				Synchonization.onUpdateEntriesSent(updateOfficers);
				Synchonization.onUpdateEntriesSent(updatePatients);
				Synchonization.onUpdateEntriesSent(updatePregnancies);
				Synchonization.onUpdateEntriesSent(updatePhotos);
				Synchonization.onUpdateEntriesSent(updateServes);
				Synchonization.onUpdateEntriesSent(updateWorksOns);
				Synchonization.onUpdateEntriesSent(updateValidations);
				Synchonization.onUpdateEntriesSent(updateComments);
				
				
				//Add handle
				User[] users = gson.fromJson(addRoot.getJSONArray("user").toString(), User[].class);
				Doctor[] doctors = gson.fromJson(addRoot.getJSONArray("user").toString(), Doctor[].class);
				Clinic[] clinics = gson.fromJson(addRoot.getJSONArray("user").toString(), Clinic[].class);
				Officer[] officers = gson.fromJson(addRoot.getJSONArray("user").toString(), Officer[].class);
				Patient[] patients = gson.fromJson(addRoot.getJSONArray("user").toString(), Patient[].class);
				Pregnancy[] pregnancies = gson.fromJson(addRoot.getJSONArray("user").toString(), Pregnancy[].class);
				Photo[] photos = gson.fromJson(addRoot.getJSONArray("user").toString(), Photo[].class);
				Serve[] serves = gson.fromJson(addRoot.getJSONArray("user").toString(), Serve[].class);
				WorksOn[] worksOns = gson.fromJson(addRoot.getJSONArray("user").toString(), WorksOn[].class);
				Validation[] validations = gson.fromJson(addRoot.getJSONArray("user").toString(), Validation[].class);
				Comment[] comments = gson.fromJson(addRoot.getJSONArray("user").toString(), Comment[].class);
				
				Synchonization.addUserEntriesFromServer(users);
				Synchonization.addDoctorEntriesFromServer(doctors);
				Synchonization.addClinicEntriesFromServer(clinics);
				Synchonization.addOfficerEntriesFromServer(officers);
				Synchonization.addPatientEntriesFromServer(patients);
				Synchonization.addPregnancyEntriesFromServer(pregnancies);
				Synchonization.addPhotoEntriesFromServer(photos);
				Synchonization.addServeEntriesFromServer(serves);
				Synchonization.addWorksOnEntriesFromServer(worksOns);
				Synchonization.addValidationEntriesFromServer(validations);
				Synchonization.addCommentEntriesFromServer(comments);
				
				//Update handle
				users = gson.fromJson(updateRoot.getJSONArray("user").toString(), User[].class);
				doctors = gson.fromJson(updateRoot.getJSONArray("user").toString(), Doctor[].class);
				clinics = gson.fromJson(updateRoot.getJSONArray("user").toString(), Clinic[].class);
				officers = gson.fromJson(updateRoot.getJSONArray("user").toString(), Officer[].class);
				patients = gson.fromJson(updateRoot.getJSONArray("user").toString(), Patient[].class);
				pregnancies = gson.fromJson(updateRoot.getJSONArray("user").toString(), Pregnancy[].class);
				photos = gson.fromJson(updateRoot.getJSONArray("user").toString(), Photo[].class);
				serves = gson.fromJson(updateRoot.getJSONArray("user").toString(), Serve[].class);
				worksOns = gson.fromJson(updateRoot.getJSONArray("user").toString(), WorksOn[].class);
				validations = gson.fromJson(updateRoot.getJSONArray("user").toString(), Validation[].class);
				comments = gson.fromJson(updateRoot.getJSONArray("user").toString(), Comment[].class);
				
				Synchonization.updateUserEntriesFromServer(users);
				Synchonization.updateDoctorEntriesFromServer(doctors);
				Synchonization.updateClinicEntriesFromServer(clinics);
				Synchonization.updateOfficerEntriesFromServer(officers);
				Synchonization.updatePatientEntriesFromServer(patients);
				Synchonization.updatePregnancyEntriesFromServer(pregnancies);
				Synchonization.updatePhotoEntriesFromServer(photos);
				Synchonization.updateServeEntriesFromServer(serves);
				Synchonization.updateWorksOnEntriesFromServer(worksOns);
				Synchonization.updateValidationEntriesFromServer(validations);
				Synchonization.updateCommentEntriesFromServer(comments);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
