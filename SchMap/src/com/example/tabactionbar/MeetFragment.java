package com.example.tabactionbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeetFragment extends Fragment{
	
	public static FragmentManager fm;
	private static MeetGroupFragment currentGroupFrag;
	private static Fragment currentFrag;
//	private Button addButton;
	static final String COURSE_FIELD = "Courses";
	static final String GROUP_FIELD = "Groups";
	static final ArrayList<String> COURSES = new ArrayList<String>(Arrays.asList(
											  "8.01","8.02","8.03","8.04","18.01",
											  "18.02","18.03","9.00","9.01","9.65",
											  "6.00","6.01","6.02","6.002","6.003",
											  "6.004","6.005","6.006","7.012","7.013",
											  "7.014","7.02"
											));
	static final HashMap<String,List<Double>> locations;
	static
    {
        locations = new HashMap<String, List<Double>>();
        ParseQuery location = new ParseQuery("Static_Markers");
        location.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> locs, ParseException arg1) {
				
				for (ParseObject loc: locs) {
					locations.put(loc.getString("location_name"), 
							Arrays.asList(loc.getParseGeoPoint("point").getLatitude(),
									loc.getParseGeoPoint("point").getLongitude()));
				}
				
			}
		});
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setupInitialLayout();
		View view = inflater.inflate(R.layout.meet, container, false);
		
		// logging
		ParseObject log = new ParseObject("MeetFragment");
		log.put("action", "just arrived");
		log.saveInBackground();
		
		return view;
	}

	/**
	 * Initializes the initial ListView layout for the Todo tab
	 */
	private void setupInitialLayout() {
		
		currentFrag = new MeetCourseFragment();
		fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.meetContentFragment, currentFrag);
//		transaction.addToBackStack(null);
		transaction.commit();
		
	}

	/**
	 * Switches the fragments in the Meet tab 
	 * @param i integer corresponding to the type of fragment transition needed to be made
	 * @param courseName used to search the course information from the DB
	 */
	public static void switchGroupFragment(String courseNum) {

		currentGroupFrag = new MeetGroupFragment();
		currentGroupFrag.setCourseNum(courseNum);
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.meetContentFragment, currentGroupFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public static MeetGroupFragment getCurrentGroupFrag() {
		return currentGroupFrag;
	}
	
	public static void revertHome() {
		currentFrag = new MeetCourseFragment();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.meetContentFragment, currentFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
