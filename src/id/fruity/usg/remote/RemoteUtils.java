package id.fruity.usg.remote;

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
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Preference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RemoteUtils {
	private static final String TAG = "RemoteUtils";
	private static final String TEST1 = "http://192.168.43.211:8000/polls/update/1/";
	private static final String BASE_URL = "http://192.168.43.211:8000/polls/";
	private static final String USER_SUB_URL = "mobile/sync/user/",
			DOCTOR_SUB_URL = "mobile/sync/doctor/",
			CLINIC_SUB_URL = "mobile/sync/clinic/",
			OFFICER_SUB_URL = "mobile/sync/officer/",
			PATIENT_SUB_URL = "mobile/sync/patient/",
			PREGNANCY_SUB_URL = "mobile/sync/pregnancy/",
			PHOTO_SUB_URL = "mobile/sync/photo/",
			SERVE_SUB_URL = "mobile/sync/serve/",
			WORKSON_SUB_URL = "mobile/sync/workson/",
			COMMENT_SUB_URL = "mobile/sync/comment/",
			VALIDATION_SUB_URL = "mobile/sync/validation/",
			PHOTOUSER_SUB_URL = "mobile/sync/photo/user/",
			PHOTOUSG_SUB_URL = "mobile/sync/photo/usg/",
			DOWNLOADUSER_SUB_URL = "mobile/download/photo/user/",
			DOWNLOADUSG_SUB_URL = "mobile/download/photo/usg/";
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

	public static void uploadPhoto() {
		String url = BASE_URL + PHOTOUSG_SUB_URL;
		String imagePath = Environment.getExternalStorageDirectory()
				+ File.separator + "USG Apps" + File.separator + "USG"
				+ File.separator + "USG_20130519_194645.jpg";
		Log.d("Image path", imagePath + " -- " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);

		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			entity.addPart("ktp", new StringBody("1122334455"));
			entity.addPart("image", new FileBody(new File(imagePath)));
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost, localContext);
			Log.d("After sending photo", "Res"
					+ response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			Log.d("After sending photo", "Error");
			e.printStackTrace();
		}
	}

	public static void downloadPhoto(String downloadUrl, String imagePath) {
		Log.d("Image path", imagePath + " -- " + downloadUrl);

		try {
			URL url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ktp", "1122334455"));

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "UTF-8"));
			writer.write(getQuery(params));
			conn.connect();

			InputStream inputStream = null;
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = conn.getInputStream();
			}

			try {
				final File targetFile = new File(imagePath);
				FileOutputStream fos = new FileOutputStream(targetFile);
				int size = 1024 * 1024;
				byte[] buf = new byte[size];
				int byteRead;
				while (((byteRead = inputStream.read(buf)) != -1)) {
					fos.write(buf, 0, byteRead);
				}
				fos.close();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}

		} catch (IOException e) {
			Log.d("After download photo", "Error");
			e.printStackTrace();
		}
	}

	private static String getQuery(List<NameValuePair> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	private static String synchronize() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + lastSync + "/";

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		try {
			HttpPost post = new HttpPost(url);

			// Create request JSON

			// Timestamp
			json.put("timestamp", lastSync);
			// Add Tables
			JSONObject add = new JSONObject();

			// UserTable
			JSONArray addUserTable = new JSONArray();
			newUsers = Synchonization.getNewUser(lastSync);
			for (User user : newUsers) {
				addUserTable.put(new JSONObject(gson.toJson(user)));
			}
			JSONArray addPatientTable = new JSONArray();
			newPatients = Synchonization.getNewPatient(lastSync);
			for (Patient p : newPatients) {
				addPatientTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addPregnancyTable = new JSONArray();
			newPregnancies = Synchonization.getNewPregnancy(lastSync);
			for (Pregnancy p : newPregnancies) {
				addPregnancyTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addPhotoTable = new JSONArray();
			newPhotos = Synchonization.getNewPhoto(lastSync);
			for (Photo p : newPhotos) {
				addPhotoTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray addDoctorTable = new JSONArray();
			newDoctors = Synchonization.getNewDoctor(lastSync);
			for (Doctor d : newDoctors) {
				addDoctorTable.put(new JSONObject(gson.toJson(d)));
			}
			JSONArray addClinicTable = new JSONArray();
			newClinics = Synchonization.getNewClinic(lastSync);
			for (Clinic c : newClinics) {
				addClinicTable.put(new JSONObject(gson.toJson(c)));
			}
			JSONArray addOfficerTable = new JSONArray();
			newOfficers = Synchonization.getNewOfficer(lastSync);
			for (Officer o : newOfficers) {
				addOfficerTable.put(new JSONObject(gson.toJson(o)));
			}
			JSONArray addServeTable = new JSONArray();
			newServes = Synchonization.getNewServe(lastSync);
			for (Serve s : newServes) {
				addServeTable.put(new JSONObject(gson.toJson(s)));
			}
			JSONArray addWorksOnTable = new JSONArray();
			newWorksOns = Synchonization.getNewWorksOn(lastSync);
			for (WorksOn w : newWorksOns) {
				addWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}
			JSONArray addValidationTable = new JSONArray();
			newValidations = Synchonization.getNewValidation(lastSync);
			for (Validation v : newValidations) {
				addValidationTable.put(new JSONObject(gson.toJson(v)));
			}
			JSONArray addCommentTable = new JSONArray();
			newComments = Synchonization.getNewComment(lastSync);
			for (Comment c : newComments) {
				addCommentTable.put(new JSONObject(gson.toJson(c)));
			}

			// Update Tables
			JSONObject update = new JSONObject();

			// UserTable
			JSONArray updateUserTable = new JSONArray();
			updateUsers = Synchonization.getUpdateUser(lastSync);
			for (User user : updateUsers) {
				updateUserTable.put(new JSONObject(gson.toJson(user)));
			}
			JSONArray updatePatientTable = new JSONArray();
			updatePatients = Synchonization.getUpdatePatient(lastSync);
			for (Patient p : updatePatients) {
				updatePatientTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updatePregnancyTable = new JSONArray();
			updatePregnancies = Synchonization.getUpdatePregnancy(lastSync);
			for (Pregnancy p : updatePregnancies) {
				updatePregnancyTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updatePhotoTable = new JSONArray();
			updatePhotos = Synchonization.getUpdatePhoto(lastSync);
			for (Photo p : updatePhotos) {
				updatePhotoTable.put(new JSONObject(gson.toJson(p)));
			}
			JSONArray updateDoctorTable = new JSONArray();
			updateDoctors = Synchonization.getUpdateDoctor(lastSync);
			for (Doctor d : updateDoctors) {
				updateDoctorTable.put(new JSONObject(gson.toJson(d)));
			}
			JSONArray updateClinicTable = new JSONArray();
			updateClinics = Synchonization.getUpdateClinic(lastSync);
			for (Clinic c : updateClinics) {
				updateClinicTable.put(new JSONObject(gson.toJson(c)));
			}
			JSONArray updateOfficerTable = new JSONArray();
			updateOfficers = Synchonization.getUpdateOfficer(lastSync);
			for (Officer o : updateOfficers) {
				updateOfficerTable.put(new JSONObject(gson.toJson(o)));
			}
			JSONArray updateServeTable = new JSONArray();
			updateServes = Synchonization.getUpdateServe(lastSync);
			for (Serve s : updateServes) {
				updateServeTable.put(new JSONObject(gson.toJson(s)));
			}
			JSONArray updateWorksOnTable = new JSONArray();
			updateWorksOns = Synchonization.getUpdateWorksOn(lastSync);
			for (WorksOn w : updateWorksOns) {
				updateWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}
			JSONArray updateValidationTable = new JSONArray();
			updateValidations = Synchonization.getUpdateValidation(lastSync);
			for (Validation v : updateValidations) {
				updateValidationTable.put(new JSONObject(gson.toJson(v)));
			}
			JSONArray updateCommentTable = new JSONArray();
			updateComments = Synchonization.getUpdateComment(lastSync);
			for (Comment c : updateComments) {
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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
		}

		return builder.toString();
	}

	public static boolean syncUser() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + USER_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);

			JSONObject add = new JSONObject();
			JSONArray addUserTable = new JSONArray();
			newUsers = Synchonization.getNewUser(lastSync);
			for (User user : newUsers) {
				addUserTable.put(new JSONObject(gson.toJson(user)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateUserTable = new JSONArray();
			updateUsers = Synchonization.getUpdateUser(lastSync);
			for (User user : updateUsers) {
				updateUserTable.put(new JSONObject(gson.toJson(user)));
			}

			add.put("user", addUserTable);
			update.put("user", updateUserTable);
			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}

		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newUsers);
			Synchonization.onUpdateEntriesSent(updateUsers);

			User[] users = gson.fromJson(addRoot.getJSONArray("user")
					.toString(), User[].class);
			Synchonization.addUserEntriesFromServer(users);
			users = gson.fromJson(updateRoot.getJSONArray("user").toString(),
					User[].class);
			Synchonization.updateUserEntriesFromServer(users);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncDoctor() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + DOCTOR_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);

			JSONObject add = new JSONObject();
			JSONArray addDoctorTable = new JSONArray();
			newDoctors = Synchonization.getNewDoctor(lastSync);
			for (Doctor d : newDoctors) {
				addDoctorTable.put(new JSONObject(gson.toJson(d)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateDoctorTable = new JSONArray();
			updateDoctors = Synchonization.getUpdateDoctor(lastSync);
			for (Doctor d : updateDoctors) {
				updateDoctorTable.put(new JSONObject(gson.toJson(d)));
			}

			add.put("doctor", addDoctorTable);
			update.put("doctor", updateDoctorTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newDoctors);
			Synchonization.onUpdateEntriesSent(updateDoctors);

			Doctor[] doctors = gson.fromJson(addRoot.getJSONArray("doctor")
					.toString(), Doctor[].class);
			Synchonization.addDoctorEntriesFromServer(doctors);
			doctors = gson.fromJson(updateRoot.getJSONArray("doctor")
					.toString(), Doctor[].class);
			Synchonization.updateDoctorEntriesFromServer(doctors);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncClinic() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + CLINIC_SUB_URL;

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);

			JSONObject add = new JSONObject();
			JSONArray addClinicTable = new JSONArray();
			newClinics = Synchonization.getNewClinic(lastSync);
			for (Clinic c : newClinics) {
				addClinicTable.put(new JSONObject(gson.toJson(c)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateClinicTable = new JSONArray();
			updateClinics = Synchonization.getUpdateClinic(lastSync);
			for (Clinic c : updateClinics) {
				updateClinicTable.put(new JSONObject(gson.toJson(c)));
			}

			add.put("clinic", addClinicTable);
			update.put("clinic", updateClinicTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newClinics);
			Synchonization.onUpdateEntriesSent(updateClinics);

			Clinic[] clinics = gson.fromJson(addRoot.getJSONArray("clinic")
					.toString(), Clinic[].class);
			Synchonization.addClinicEntriesFromServer(clinics);
			clinics = gson.fromJson(updateRoot.getJSONArray("clinic")
					.toString(), Clinic[].class);
			Synchonization.updateClinicEntriesFromServer(clinics);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncOfficer() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + OFFICER_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();

			JSONArray addOfficerTable = new JSONArray();
			newOfficers = Synchonization.getNewOfficer(lastSync);
			for (Officer o : newOfficers) {
				addOfficerTable.put(new JSONObject(gson.toJson(o)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateOfficerTable = new JSONArray();
			updateOfficers = Synchonization.getUpdateOfficer(lastSync);
			for (Officer o : updateOfficers) {
				updateOfficerTable.put(new JSONObject(gson.toJson(o)));
			}

			add.put("officer", addOfficerTable);
			update.put("officer", updateOfficerTable);

			json.put("add", add);
			json.put("update", update);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}

		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newOfficers);
			Synchonization.onUpdateEntriesSent(updateOfficers);

			Officer[] officers = gson.fromJson(addRoot.getJSONArray("officer")
					.toString(), Officer[].class);
			Synchonization.addOfficerEntriesFromServer(officers);
			officers = gson.fromJson(updateRoot.getJSONArray("officer")
					.toString(), Officer[].class);
			Synchonization.updateOfficerEntriesFromServer(officers);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean syncPatient() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + PATIENT_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addPatientTable = new JSONArray();
			newPatients = Synchonization.getNewPatient(lastSync);
			for (Patient p : newPatients) {
				addPatientTable.put(new JSONObject(gson.toJson(p)));
			}

			JSONObject update = new JSONObject();
			JSONArray updatePatientTable = new JSONArray();
			updatePatients = Synchonization.getUpdatePatient(lastSync);
			for (Patient p : updatePatients) {
				updatePatientTable.put(new JSONObject(gson.toJson(p)));
			}

			add.put("patient", addPatientTable);
			update.put("patient", updatePatientTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newPatients);
			Synchonization.onUpdateEntriesSent(updatePatients);

			Patient[] patients = gson.fromJson(addRoot.getJSONArray("patient")
					.toString(), Patient[].class);
			Synchonization.addPatientEntriesFromServer(patients);
			patients = gson.fromJson(updateRoot.getJSONArray("patient")
					.toString(), Patient[].class);
			Synchonization.updatePatientEntriesFromServer(patients);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncPregnancy() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + PREGNANCY_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addPregnancyTable = new JSONArray();
			newPregnancies = Synchonization.getNewPregnancy(lastSync);
			for (Pregnancy p : newPregnancies) {
				addPregnancyTable.put(new JSONObject(gson.toJson(p)));
			}

			JSONObject update = new JSONObject();
			JSONArray updatePregnancyTable = new JSONArray();
			updatePregnancies = Synchonization.getUpdatePregnancy(lastSync);
			for (Pregnancy p : updatePregnancies) {
				updatePregnancyTable.put(new JSONObject(gson.toJson(p)));
			}

			add.put("pregnancy", addPregnancyTable);
			update.put("pregnancy", updatePregnancyTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newPregnancies);
			Synchonization.onUpdateEntriesSent(updatePregnancies);

			Pregnancy[] pregnancies = gson.fromJson(
					addRoot.getJSONArray("pregnancy").toString(),
					Pregnancy[].class);
			Synchonization.addPregnancyEntriesFromServer(pregnancies);
			pregnancies = gson.fromJson(updateRoot.getJSONArray("pregnancy")
					.toString(), Pregnancy[].class);
			Synchonization.updatePregnancyEntriesFromServer(pregnancies);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncPhoto() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + PHOTO_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addPhotoTable = new JSONArray();
			newPhotos = Synchonization.getNewPhoto(lastSync);
			for (Photo p : newPhotos) {
				addPhotoTable.put(new JSONObject(gson.toJson(p)));
			}

			JSONObject update = new JSONObject();
			JSONArray updatePhotoTable = new JSONArray();
			updatePhotos = Synchonization.getUpdatePhoto(lastSync);
			for (Photo p : updatePhotos) {
				updatePhotoTable.put(new JSONObject(gson.toJson(p)));
			}

			add.put("photo", addPhotoTable);
			update.put("photo", updatePhotoTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			JSONObject confirmAddRoot = root.getJSONObject("confirm_add");
			JSONArray photoConfirm = confirmAddRoot.getJSONArray("photo");

			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newPhotos);
			for (int i = 0; i < photoConfirm.length(); i++) {
				JSONObject j = photoConfirm.getJSONObject(i);
				int serverPhotoNumber = j.getInt("global_number");
				int localPhotoNumber = j.getInt("local_number");
				int pregnancyNumber = j.getInt("pregnancy_number");
				String ktp = j.getString("ktp");
				Synchonization.updatePhotoServerId(ktp, pregnancyNumber,
						localPhotoNumber, serverPhotoNumber);
			}
			Synchonization.onUpdateEntriesSent(updatePhotos);

			Photo[] photos = gson.fromJson(addRoot.getJSONArray("photo")
					.toString(), Photo[].class);
			Synchonization.addPhotoEntriesFromServer(photos);
			photos = gson.fromJson(updateRoot.getJSONArray("photo").toString(),
					Photo[].class);
			Synchonization.updatePhotoEntriesFromServer(photos);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncServe() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + SERVE_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addServeTable = new JSONArray();
			newServes = Synchonization.getNewServe(lastSync);
			for (Serve s : newServes) {
				addServeTable.put(new JSONObject(gson.toJson(s)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateServeTable = new JSONArray();
			updateServes = Synchonization.getUpdateServe(lastSync);
			for (Serve s : updateServes) {
				updateServeTable.put(new JSONObject(gson.toJson(s)));
			}

			add.put("serve", addServeTable);
			update.put("serve", updateServeTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newServes);
			Synchonization.onUpdateEntriesSent(updateServes);

			Serve[] serves = gson.fromJson(addRoot.getJSONArray("serve")
					.toString(), Serve[].class);
			Synchonization.addServeEntriesFromServer(serves);
			serves = gson.fromJson(updateRoot.getJSONArray("serve").toString(),
					Serve[].class);
			Synchonization.updateServeEntriesFromServer(serves);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncWorksOn() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + WORKSON_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addWorksOnTable = new JSONArray();
			newWorksOns = Synchonization.getNewWorksOn(lastSync);
			for (WorksOn w : newWorksOns) {
				addWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateWorksOnTable = new JSONArray();
			updateWorksOns = Synchonization.getUpdateWorksOn(lastSync);
			for (WorksOn w : updateWorksOns) {
				updateWorksOnTable.put(new JSONObject(gson.toJson(w)));
			}

			add.put("works_on", addWorksOnTable);
			update.put("works_on", updateWorksOnTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newWorksOns);
			Synchonization.onUpdateEntriesSent(updateWorksOns);

			WorksOn[] worksOns = gson.fromJson(addRoot.getJSONArray("works_on")
					.toString(), WorksOn[].class);
			Synchonization.addWorksOnEntriesFromServer(worksOns);
			worksOns = gson.fromJson(updateRoot.getJSONArray("works_on")
					.toString(), WorksOn[].class);
			Synchonization.updateWorksOnEntriesFromServer(worksOns);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncComment() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + COMMENT_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addCommentTable = new JSONArray();
			newComments = Synchonization.getNewComment(lastSync);
			for (Comment c : newComments) {
				addCommentTable.put(new JSONObject(gson.toJson(c)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateCommentTable = new JSONArray();
			updateComments = Synchonization.getUpdateComment(lastSync);
			for (Comment c : updateComments) {
				updateCommentTable.put(new JSONObject(gson.toJson(c)));
			}

			add.put("comment", addCommentTable);
			update.put("comment", updateCommentTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			JSONObject confirmAddRoot = root.getJSONObject("confirm_add");
			JSONArray commentConfirm = confirmAddRoot.getJSONArray("comment");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newComments);
			for (int i = 0; i < commentConfirm.length(); i++) {
				JSONObject j = commentConfirm.getJSONObject(i);
				int serverCommentNumber = j.getInt("global_number");
				int localCommentNumber = j.getInt("local_number");
				String ktp = j.getString("ktp");
				Synchonization.updateCommentServerId(ktp, localCommentNumber,
						serverCommentNumber);
			}
			Synchonization.onUpdateEntriesSent(updateComments);

			Comment[] comments = gson.fromJson(addRoot.getJSONArray("comment")
					.toString(), Comment[].class);
			Synchonization.addCommentEntriesFromServer(comments);
			comments = gson.fromJson(updateRoot.getJSONArray("comment")
					.toString(), Comment[].class);
			Synchonization.updateCommentEntriesFromServer(comments);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean syncValidation() {
		long lastSync = Preference.getLastSync();
		String url = BASE_URL + VALIDATION_SUB_URL;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpResponse response;
		JSONObject json = new JSONObject();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		try {
			HttpPost post = new HttpPost(url);
			json.put("timestamp", lastSync);
			JSONObject add = new JSONObject();
			JSONArray addValidationTable = new JSONArray();
			newValidations = Synchonization.getNewValidation(lastSync);
			for (Validation v : newValidations) {
				addValidationTable.put(new JSONObject(gson.toJson(v)));
			}

			JSONObject update = new JSONObject();
			JSONArray updateValidationTable = new JSONArray();
			updateValidations = Synchonization.getUpdateValidation(lastSync);
			for (Validation v : updateValidations) {
				updateValidationTable.put(new JSONObject(gson.toJson(v)));
			}

			add.put("validation", addValidationTable);
			update.put("validation", updateValidationTable);

			json.put("add", add);
			json.put("update", update);

			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader(HTTP.CONTENT_TYPE, "application/json");
			response = client.execute(post);

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
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Error", e.getMessage());
			return false;
		}
		JSONObject root;
		try {
			root = new JSONObject(builder.toString());
			JSONObject addRoot = root.getJSONObject("add");
			JSONObject updateRoot = root.getJSONObject("update");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
			Synchonization.onNewEntriesSent(newValidations);
			Synchonization.onUpdateEntriesSent(updateValidations);

			Validation[] validations = gson.fromJson(
					addRoot.getJSONArray("validation").toString(),
					Validation[].class);
			Synchonization.addValidationEntriesFromServer(validations);
			validations = gson.fromJson(updateRoot.getJSONArray("validation")
					.toString(), Validation[].class);
			Synchonization.updateValidationEntriesFromServer(validations);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static SyncCallback SC;

	public static void startAsyn(Activity a, SyncCallback sc) {
		SC = sc;
		DownloadWebPageTask d = new DownloadWebPageTask();
		d.execute();
	}

	public static void startAsyn2(Activity a) {
		DownloadWebPageTask2 d = new DownloadWebPageTask2();
		d.execute();
	}

	public static void startAsyn3(Activity a) {
		DownloadImageTask d = new DownloadImageTask();
		d.execute(0);
	}

	private static class DownloadWebPageTask extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			boolean result = false;
			int counter = 0;

			result = syncUser();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncDoctor();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncClinic();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncOfficer();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncPatient();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncPregnancy();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncPhoto();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncServe();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncWorksOn();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncComment();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			result = syncValidation();
			if (SC != null)
				SC.onSyncProgress(++counter, result);

			return null;
		}
	}

	private static class DownloadWebPageTask2 extends
			AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			uploadPhoto();
			return null;
		}
	}

	public static abstract class SyncCallback {
		public abstract void onSyncProgress(int i, boolean result);
	}

	private static class DownloadImageTask extends
			AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int i = params[0];
			long currentTime = DateUtils.getCurrentLong();
			String basePath = Environment.getExternalStorageDirectory()
					+ File.separator + "USG Apps" + File.separator + "USG"
					+ File.separator + "new_" + currentTime + ".jpg";

			if (i == 0) {
				downloadPhoto(BASE_URL + DOWNLOADUSER_SUB_URL, basePath);
			} else {
				downloadPhoto(BASE_URL + DOWNLOADUSER_SUB_URL, basePath);
			}
			return null;
		}

	}

}
