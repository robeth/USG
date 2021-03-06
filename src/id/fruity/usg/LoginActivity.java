package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.remote.RemoteUtils;
import id.fruity.usg.remote.Synchonization;
import id.fruity.usg.remote.RemoteUtils.SyncCallback;
import id.fruity.usg.util.Preference;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {
	private RelativeLayout loginContainer;
	private EditText usernameField, passwordField;
	private Button loginButton;
	private USGDBHelper helper;
	private ProgressDialog progressDialog;
	private OnClickListener loginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			LoginTask t = new LoginTask();
			t.execute();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Preference.A = this;
		Synchonization.A = this;
		if (Preference.isLoggedIn()) {
			gotoHomeScreen(Preference.getLastUsername(), true);
			return;
		}

		this.deleteDatabase(USGDBHelper.DATABASE_NAME);
		helper = USGDBHelper.getInstance(this);
		helper.open();
		//helper.echo();
		helper.test();
		helper.close();
		setContentView(R.layout.login);
		loginContainer = (RelativeLayout) findViewById(R.id.login_container);
		usernameField = (EditText) findViewById(R.id.username);
		passwordField = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login);

		loginButton.setOnClickListener(loginListener);
		gotoHomeScreen("user6", false);
		
	}

	private void gotoHomeScreen(String username, boolean status) {
		Log.d("Letus go", "username:"+username);
		Intent i = new Intent(this, PatientListActivity.class);
		Bundle b = new Bundle();
		b.putString("username", username);
		i.putExtras(b);
		
		if(!status){
			Preference.setLastUsername(username);
			Preference.setLoggedIn(true);
			Preference.setLastSync(-1);
		}
		startActivity(i);
		finish();
	}
	
	private void onLoginProcessComplete(boolean status){
		if(status){
			gotoHomeScreen(usernameField.getText().toString(),false);
		} else {
			usernameField.setText("");
			passwordField.setText("");
		}
	}

	private class LoginTask extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Login...");
			progressDialog
					.setMessage("Contacting server, please wait...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean res = RemoteUtils.login(usernameField.getText().toString(), 
					passwordField.getText().toString());
			return res;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			onLoginProcessComplete(result);
		}
	}
}
