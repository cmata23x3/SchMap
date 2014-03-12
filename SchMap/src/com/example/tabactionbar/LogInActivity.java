package com.example.tabactionbar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends Activity {

	public final static String USERNAME = "com.example.myfirstapp.USERNAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		
		Parse.initialize(this, "AGzf1jUA64JLDe3Kr1etAOuIvTpQAfLZvUUmSl3x", "1bccOOc7hcRKx28QSPqPxXyvFoRywqJPS98H2egq");
//		ParseUser.logOut();
//		Log.e("Login",ParseUser.getCurrentUser().getUsername());
		Intent fromSignup = getIntent();
		if (ParseUser.getCurrentUser() != null && !fromSignup.getBooleanExtra(SignUpActivity.FROMSIGNUP, false)){
			final Intent mapIntent = new Intent(this, MainActivity.class);
			mapIntent.putExtra(USERNAME, ParseUser.getCurrentUser().getUsername());
			Log.e("Login","The user: "+ParseUser.getCurrentUser().getUsername()+" is already logged in");
			startActivity(mapIntent);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}
	
	public void signUp(View v) {
		Intent signUp = new Intent(this, SignUpActivity.class);
		signUp.putExtra(USERNAME, ((EditText) findViewById(R.id.email)).getText().toString());
		startActivity(signUp);
		finish();
	}
	
	public void loginUser(View v) {
		final Intent mapIntent = new Intent(this, MainActivity.class);
		
		String email = ((EditText) findViewById(R.id.email)).getText().toString();
		String password = ((EditText) findViewById(R.id.password)).getText().toString();
		
		mapIntent.putExtra(USERNAME, email);
		
		ParseUser.logInInBackground(email, password, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
			    	((EditText) findViewById(R.id.email)).setText("");
			    	((EditText) findViewById(R.id.password)).setText("");
			    	startActivity(mapIntent);
			    	
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    	((EditText) findViewById(R.id.email)).setText("");
			    	((EditText) findViewById(R.id.password)).setText("");
			    	Log.e("MYAPP", "exception", e);			    }
			  }
			});
		
	}

}
