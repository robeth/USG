package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity{
	private RelativeLayout loginContainer;
	private EditText username, password;
	private Button loginButton;
	private USGDBHelper helper;
	
	private OnClickListener loginListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean isValid = helper.isUserPassMatch(username.getText().toString(), password.getText().toString());
			
			if(isValid){
				Intent i = new Intent(LoginActivity.this, PatientListActivity.class);
				Bundle b = new Bundle();
				b.putString("username", username.getText().toString());
				i.putExtras(b);
				
				startActivity(i);
				helper.close();
				LoginActivity.this.finish();
			}
		}
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		this.deleteDatabase(USGDBHelper.DATABASE_NAME);
		helper = new USGDBHelper(this);
		helper.open();
		helper.test();
		
		setContentView(R.layout.login);
		loginContainer = (RelativeLayout) findViewById(R.id.login_container);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login);
		
    	loginButton.setOnClickListener(loginListener);
		
	}
}
