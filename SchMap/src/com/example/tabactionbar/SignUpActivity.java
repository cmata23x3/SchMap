package com.example.tabactionbar;

import org.json.JSONArray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	public final static String USERNAME = "com.example.myfirstapp.USERNAME";
	public final static String PASSWORD = "com.example.myfirstapp.PASSWORD";
	public final static String FROMSIGNUP = "com.example.myfirstapp.FROMSIGNUP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent fromLogin = getIntent();
		String email = fromLogin.getStringExtra(LogInActivity.USERNAME);
		((EditText) findViewById(R.id.email_sign_up)).setText(email);
		
		// logging
		ParseObject signup = new ParseObject("SignUpActivity");
		signup.put("action", "just arriving");
		signup.saveInBackground();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void signUpUser(View v) {
		final Intent login = new Intent(this, LogInActivity.class);
		final EditText email = (EditText) findViewById(R.id.email_sign_up);
		final EditText password = (EditText) findViewById(R.id.password_sign_up);
		final EditText confirm_password = (EditText) findViewById(R.id.confirm_password_sign_up);
		
		String email_text = email.getText().toString();
		String confirm_text = confirm_password.getText().toString();
		String password_text = password.getText().toString();
		
		login.putExtra(USERNAME, email_text);
		login.putExtra(PASSWORD, password_text);
		login.putExtra(FROMSIGNUP, true);
		
		if (confirm_text.equals(password_text)) {
			//if the password are correct then redirect to the login page
			ParseUser newUser = new ParseUser();
			newUser.setUsername(email_text);
			newUser.setPassword(password_text);
			newUser.setEmail(email_text);
			newUser.put(MeetCourseFragment.COURSE_FIELD, new JSONArray());
			newUser.put("attending", new JSONArray());
			newUser.put("notAttending", new JSONArray());
			
			newUser.signUpInBackground(new SignUpCallback() {
				
				@Override
				public void done(ParseException e) {
				    if (e == null) {
				    	startActivity(login);
				    } else {
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    	email.setText("");
				    	password.setText("");
				    	confirm_password.setText("");
				    }
				  }
				});
			
			// logging signup
			ParseObject signup = new ParseObject("SignUpActivity");
			signup.put("action", "signing up");
			signup.saveInBackground();
			
		}else {
			//some information as to why the user was unable to sign in 
			//with this user login should be mentioned
			email.setText("");
	    	password.setText("");
	    	confirm_password.setText("");
		}
	}

}
