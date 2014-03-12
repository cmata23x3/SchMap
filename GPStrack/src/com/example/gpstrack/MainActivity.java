package com.example.gpstrack;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends Activity {
	CheckBox gps;
	CheckBox wifi;
	CheckBox both;
	TextView latView;
	TextView longView;
	TextView accuView;
	LocationManager locationManager; 
	MyListener locationListener;
	static boolean gpsOn;
	static boolean wifiOn;
	static boolean bothOn;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Parse.initialize(this, "AGzf1jUA64JLDe3Kr1etAOuIvTpQAfLZvUUmSl3x", "1bccOOc7hcRKx28QSPqPxXyvFoRywqJPS98H2egq");
        
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latView = (TextView) findViewById(R.id.latText);
        longView = (TextView) findViewById(R.id.longText);
        accuView = (TextView) findViewById(R.id.accuText);
        locationListener =  new MyListener(latView, longView, accuView);
        gps = (CheckBox) findViewById(R.id.GPSbox);
        
        gps.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked==true){
					locationManager.removeUpdates(locationListener);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
					(long)10*1000,
					(float)10,
					locationListener);
					gpsOn = true;
				}else {
					gpsOn = false;
					latView.setText("0.0");
					longView.setText("0.0");
					accuView.setText("0.0");
				}
			}
		});
        
        wifi = (CheckBox) findViewById(R.id.WIFIbox);
        wifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked==true){
					locationManager.removeUpdates(locationListener);
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
					(long)10*1000,
					(float)10,
					locationListener);
					wifiOn = true;
				}else {
					wifiOn = false;
					latView.setText("0.0");
					longView.setText("0.0");
					accuView.setText("0.0");
				}
			}
		});
        
        both = (CheckBox) findViewById(R.id.GPSWIFI);
        both.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked==true){
					locationManager.removeUpdates(locationListener);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
					(long)10*1000,
					(float)10,
					locationListener); 
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
							(long)10*1000,
							(float)10,
							locationListener); 
					bothOn = true;
				}else {
					bothOn = false;
					latView.setText("0.0");
					longView.setText("0.0");
					accuView.setText("0.0");
				}
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

class MyListener implements LocationListener{
	private TextView lat;
	private TextView longi;
	private TextView accu;
	public MyListener(TextView lat, TextView longi, TextView Accu){
		this.lat = lat;
		this.longi=longi;
		this.accu=Accu;
	}

	@Override
	public void onLocationChanged(Location location) {
	    double latitude = location.getLatitude();
	    double longitude = location.getLongitude();
	    double accuracy = location.getAccuracy();

	    longi.setText(Double.toString(longitude));
	    lat.setText(Double.toString(latitude));
	    accu.setText(Double.toString(accuracy));
	    
	    if (MainActivity.gpsOn && !MainActivity.wifiOn && !MainActivity.bothOn) {
	    	ParseObject locations = new ParseObject("LocationGPS");
	    	locations.put("Longitude", longitude);
	    	locations.put("Latitude", latitude);
	    	locations.put("Accuracy", accuracy);
	    	locations.saveInBackground();
	    }
	    else if (MainActivity.wifiOn && !MainActivity.gpsOn && !MainActivity.bothOn) {
	    	ParseObject locations = new ParseObject("LocationNewtork");
	    	locations.put("Longitude", longitude);
	    	locations.put("Latitude", latitude);
	    	locations.put("Accuracy", accuracy);
	    	locations.saveInBackground();
	    }
	    else if (MainActivity.bothOn && !MainActivity.gpsOn && !MainActivity.wifiOn) {
	    	ParseObject locations = new ParseObject("LocationGPS_Network");
	    	locations.put("Longitude", longitude);
	    	locations.put("Latitude", latitude);
	    	locations.put("Accuracy", accuracy);
	    	locations.saveInBackground();
	    }
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
}
