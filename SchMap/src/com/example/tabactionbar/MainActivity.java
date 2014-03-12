package com.example.tabactionbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	
	static ArrayList<String> testObjects = new ArrayList<String>();
	private final String todo = "Tasks";
	private Dialog dialog;
	private ArrayList<String> courseList;
	private String style, location;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
				
//		Intent fromLoginIntent = getIntent();
//		String email = fromLoginIntent.getStringExtra(LogInActivity.USERNAME);
		
		setupPushNotifications();
		
		ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		String todoLabel = getResources().getString(R.string.todo);
		Tab tab = actionBar.newTab();
		tab.setText(todoLabel);
		TabListener<ToDoFragment> tl = new TabListener<ToDoFragment>(this,
				todoLabel, ToDoFragment.class);
		tab.setTabListener(tl);
		actionBar.addTab(tab);

		String meetLabel = getResources().getString(R.string.meet);
		tab = actionBar.newTab();
		tab.setText(meetLabel);
		TabListener<MeetFragment> tl2 = new TabListener<MeetFragment>(this,
				meetLabel, MeetFragment.class);
		tab.setTabListener(tl2);
		actionBar.addTab(tab);
		
		final String mapLabel = getResources().getString(R.string.map);
		tab = actionBar.newTab();
		tab.setText(mapLabel);
		TabListener<MapFragFake> tl3 = new TabListener<MapFragFake>(this,
				mapLabel,MapFragFake.class);
		tab.setTabListener(tl3);
		actionBar.addTab(tab);
		
		String scheduleLabel = getResources().getString(R.string.schedule);
		tab = actionBar.newTab();
		tab.setText(scheduleLabel);
		TabListener<ScheduleFragment> tl4 = new TabListener<ScheduleFragment>(this,
				scheduleLabel, ScheduleFragment.class);
		tab.setTabListener(tl4);
		actionBar.addTab(tab);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.logout:
	            logOut();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void logOut() {
        ParseUser.logOut();
		final Intent loginIntent = new Intent(this, LogInActivity.class);
		startActivity(loginIntent);
	}
	
	private void setupPushNotifications() {
		PushService.subscribe(this, "", MainActivity.class);
//		PushService.setDefaultPushCallback(this, MainActivity.class);
	}

	private class TabListener<T> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
			
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			// Check if the fragment is already initialized

		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.show(mFragment);
			}
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.hide(mFragment);
			}
		}
	}
	
	
	
	

	/**
	 * Popups the dialog in order to create a new task
	 * @param v
	 */
	public void createTask(View v) {
		Log.e("MainActivity", "The createTask method was called in this class");
//		MapFragFake.addMarkerScedule("Just added", 42.34434, -71.09571, "poop");
		Dialog dialog = getTaskDialog();
		dialog.show();
	}
	
	/**
	 * Create Task Dialog Window
	 * @return
	 */
	private Dialog getTaskDialog() {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.create_task_dialog);
		dialog.setTitle(R.string.createTask);
		
		// Add button functionality to create
		Button newTaskButton = (Button) dialog.findViewById(R.id.submit_new_task);
		newTaskButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				submitNewTask(v);
			}
			
		});
		return dialog;
	}
	
	/**
	 * Persists the task in the Database and calls the ListFragment
	 * in order to update the list of activities
	 * @param v
	 */
	private void submitNewTask(View v) {
		Log.e("MainActivity", "The submitNewTask method was called in this class");

		HashMap<String, String> map = new HashMap<String,String>();
		
		// get the name from the editText of the popup window
		EditText taskName = (EditText) dialog.findViewById(R.id.edit_task_name_text);
		String name = taskName.getText().toString();
		taskName.setText("");
		dialog.dismiss();
		Log.e("MainActivity", "The value of taskName:" + name);

		
		// Map only a title/name of task
		map.put("name", name);
		map.put("creator", ParseUser.getCurrentUser().getUsername());
		ToDoFragment.updateListFragment(todo, map);
		
		// logging
		ParseObject log = new ParseObject("Todo_List");
		log.put("action", "added task");
		log.saveInBackground();
	}
	
	/**
	 * Called through the Meet fragment, and pops up a dialog window
	 * in order to select a course to add
	 * @param v
	 */
	public void showCourseAdder(View v) {
		final View view = v;
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.add_course_dialog);
		dialog.setTitle(R.string.addCourse);
		Button mButton = (Button) dialog.findViewById(R.id.add_course_button);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.e("Main", "addCourse");
				addCourseToUser(view);
			}
		});
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MeetFragment.COURSES);        
		AutoCompleteTextView coursePick = (AutoCompleteTextView) dialog.findViewById(R.id.auto_complete_course);
		coursePick.setThreshold(1);
		coursePick.setAdapter(adapter);
		dialog.show();

	}
	
	/**
	 * Adds the course to the User's course list and makes a new course
	 * with
	 * @param v
	 */
	public void addCourseToUser(View v) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		String courseNum = ((AutoCompleteTextView) dialog.findViewById(R.id.auto_complete_course)).getText().toString();
    	currentUser.addAllUnique("courses", Arrays.asList(courseNum));
    	currentUser.saveInBackground();
    	addCourseChannel(courseNum);
    	addUserToCourse(courseNum);
    	
    	// logging
    	ParseObject log = new ParseObject("MeetFragment");
		log.put("action", "adding course");
		log.saveInBackground();
	}
	
	/**
	 * Adds the User to the course list of Users
	 */
	private void addUserToCourse(final String courseNum) {
		ParseQuery query = new ParseQuery(MeetFragment.COURSE_FIELD);
		query.whereEqualTo("number", courseNum);
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> courseList, ParseException e) {
		        if (e == null && !courseList.isEmpty()) {
		            //update the User object
		        	ParseObject object = courseList.get(0);
		        	object.addAllUnique("users", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
		        	object.saveInBackground();
		        } else if (e == null && courseList.isEmpty()){
		        	Log.e("adding course", "made it");

		        	if (MeetFragment.COURSES.contains(courseNum)) {
		        		//create new course in DB
		        		ParseObject object = new ParseObject(MeetFragment.COURSE_FIELD);
		        		object.put("number", courseNum);
		        		object.put("users", new JSONArray().put(ParseUser.getCurrentUser().getUsername()));
		        		object.put("groups", new JSONArray());
		        		object.saveInBackground();
		        	}
		        } else {
		        	Log.d("course", "Error: " + e.getMessage());
		        }
		        MeetCourseFragment.updateCourseGridView();
	        	dialog.dismiss();
		    }
		});
	}
	
	/**
	 * Display a dialog box for creating a group
	 * @param v
	 */
	public void showCourseGroupDialog(View v) {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.add_group_dialog);
		dialog.setTitle(R.string.addGroup);
		Button mButton = (Button) dialog.findViewById(R.id.create_group_dialog_button);
		Log.e("Main", "about to add onclicklistener");
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.e("Main", "addGroup");
				addGroupToCourse();
			}
		});
		Spinner locSpinner = (Spinner) dialog.findViewById(R.id.location_dropdown);
		setLocSpinnerList(locSpinner);
		locSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				location = parent.getItemAtPosition(pos).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
				
			}
			
		});
		Spinner styleSpinner = (Spinner) dialog.findViewById(R.id.style_dropdown);
		setStyleSpinnerList(styleSpinner);
		styleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				style = parent.getItemAtPosition(pos).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
				
			}
			
		});
		dialog.show();
	}
	
	private void setStyleSpinnerList(Spinner styleSpinner) {
		List<String> list = new ArrayList<String>();
		list.add("Highly Social");
		list.add("Frequent Discussion");
		list.add("Occassional Discussion");
		list.add("Infrequent Conversation");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		styleSpinner.setAdapter(dataAdapter);
		
	}

	private void setLocSpinnerList(Spinner locSpinner) {
		List<String> list = new ArrayList<String>();
		for (String loc: MeetFragment.locations.keySet()) {
			list.add(loc);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locSpinner.setAdapter(dataAdapter);
		
	}
	
	private void addGroupToCourse() {
		// get all of the fields necessary for group creation in the table
		final EditText gNameField = (EditText) dialog.findViewById(R.id.group_name);
		final RadioButton psetR = (RadioButton) dialog.findViewById(R.id.psetRadio);
		final RadioButton studyR = (RadioButton) dialog.findViewById(R.id.studyRadio);
		final TimePicker begin = (TimePicker) dialog.findViewById(R.id.timePicker1);
		final TimePicker end = (TimePicker) dialog.findViewById(R.id.timePicker2);
		final EditText capacity = (EditText) dialog.findViewById(R.id.group_capacity);
		final RadioButton visibleR = (RadioButton) dialog.findViewById(R.id.visibleRadio);
		final RadioButton privateR = (RadioButton) dialog.findViewById(R.id.Private);
		final MeetGroupFragment frag = MeetFragment.getCurrentGroupFrag();
		
		if (!frag.getGroupList().contains(gNameField.getText().toString())) {
			// add the group to the course group list
			Log.e("Main", "Inside the addGroupToCourse");
			ParseQuery query = new ParseQuery(MeetFragment.COURSE_FIELD);
			query.whereEqualTo("number", frag.getCourseNum());
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> tList, ParseException e) {
					if (e == null) {
						if (!tList.isEmpty()) {
							ParseObject course = tList.get(0);
							course.addAllUnique("groups", Arrays.asList(gNameField.getText().toString()));
							course.saveInBackground();
							
							Log.e("addGroupToCourse", "after all variable assignments: "+location+" "+style+""+psetR.isChecked()+" "+begin.getCurrentHour()+" "+begin.getCurrentMinute()+" "+
									end.getCurrentHour()+" "+end.getCurrentMinute()+" "+Integer.parseInt(capacity.getText().toString())+" "+visibleR.isChecked()+" "+privateR.isChecked());
							
							String type = "";
							if (psetR.isChecked()) {
								if (studyR.isChecked()) {
									type = "Pset & Study";
								}else {
									type = "Pset";
								}
							}else if (studyR.isChecked()) {
								type = "Study";
							}
							
							int begin_h = begin.getCurrentHour();
							int begin_m = begin.getCurrentMinute();
							int end_h = end.getCurrentHour();
							int end_m = end.getCurrentMinute();
							int capacityI = Integer.parseInt(capacity.getText().toString());
							String am_pm = "nothing";
							boolean visible = visibleR.isChecked();
							boolean priv = privateR.isChecked();
							Log.e("addGroupToCourse", "after all variable assignments: "+psetR.isChecked()+" "+begin+" "+
							end_h+" "+end_m+" "+capacityI+" "+am_pm+" "+visible+" "+priv);
							addGroup(gNameField.getText().toString(),location,type,style,begin_h,begin_m,
									end_h,end_m,capacityI,am_pm,visible,priv);
							dialog.dismiss();
						}
					}
					else {
						// do something if the query is not successful
					}
				}
				
			});
			
			// logging
			ParseObject log = new ParseObject("CourseFragment");
			log.put("action", "creating group");
			log.saveInBackground();
		}
		else {
			// present an alert dialog stating the inability to create an identical group
			dialog.dismiss();
			new AlertDialog.Builder(this)
		    .setTitle("Duplicate Group")
		    .setMessage("Can Not Create Duplicate Group Name")
		    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // simply close the alert dialog
		        	dialog.dismiss();
		        }
		     })
		    .show();
		}
	}
	
	/**
	 * Adds the specified group to the course
	 * @param gName
	 */
	private void addGroup(String gName, String location, String type, String style,
			int begin_h, int begin_m, int end_h, int end_m, int capacity,
			String am_pm, boolean visible, boolean priv) {
		final MeetGroupFragment frag = MeetFragment.getCurrentGroupFrag();
		ParseObject group = new ParseObject(MeetFragment.GROUP_FIELD);
		group.put("channelName", "T"+frag.getCourseNum().replace(".", "_"));
		group.put("name", gName);
		group.put("coursenum", frag.getCourseNum());
		group.put("location", location);
		group.put("type", type);
		group.put("style", style);
		group.put("begin_h", begin_h);
		group.put("begin_m", begin_m);
		group.put("end_h", end_h);
		group.put("end_m", end_m);
		group.put("capacity", capacity);
		group.put("am_pm", am_pm);
		group.put("visible", visible);
		group.put("private", priv);
//		group.put("attendees", new JSONArray());
//		group.put("non-attendees", new JSONArray());
		group.saveInBackground();
		Log.e("addGroupToCourse", "after all variable assignments");
		pushNewGroupNotification(frag.getCourseNum(),gName);
		frag.updateGroupList();
		
		List<Double> locals = MeetFragment.locations.get(location);
		MapFragFake.addMarkerMeet(gName, locals.get(0), locals.get(1), "");
	}
	
	/**
	 * Pushes a notification to all users of this specific course
	 * so that they are aware of the newly made group
	 * @param gName
	 */
	private void pushNewGroupNotification(String course, String gName) {
		String courseChannel = "T"+course.replace(".", "_");
		ParsePush push = new ParsePush();
		push.setChannel(courseChannel);
		push.setMessage("The group "+gName+" was recently created");
		push.sendInBackground();
		
	}
	
	/**
	 * add the user to the group channel
	 */
	private void addCourseChannel(String courseNum) {
		String courseChannel = "T"+courseNum.replace(".", "_");
		PushService.subscribe(getBaseContext(), courseChannel, MainActivity.class);
	}

	public void showDeleteCourseDialog(View v) {
		courseList = new ArrayList<String>();
		for (Object course: ParseUser.getCurrentUser().getList("courses")) {
			courseList.add((String) course);
		}
		final ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseList);
		courseAdapter.setNotifyOnChange(true);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.delete_listview_dialog);
		dialog.setTitle(R.string.removeCourseButton);
		
		ListView courseView = (ListView) dialog.findViewById(android.R.id.list);
		courseView.setAdapter(courseAdapter);
		courseView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.e("Main-onItemSelected", "the item selected: "+l.getItemAtPosition(position).toString());
				deleteCourse((String) l.getItemAtPosition(position));
				courseList.remove(position);
				courseAdapter.notifyDataSetChanged();
				MeetCourseFragment.updateCourseGridView();
			}
			
		});
		
		Button mButton = (Button) dialog.findViewById(R.id.done_deleting_courses);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.e("Main", "deleted Course");
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	public void deleteCourse(String courseNum) {
//		final MeetGroupFragment frag = MeetFragment.getCurrentGroupFrag();
//		String courseNum = frag.getCourseNum();
		deleteCourseFromUser(courseNum);
		deleteUserFromCourse(courseNum);
		removeCourseChannel(courseNum);
		
		// logging
		ParseObject log = new ParseObject("MeetFragment");
		log.put("action", "deleting course");
		log.saveInBackground();
	}

	private void deleteUserFromCourse(String courseNum) {		
		ParseQuery query = new ParseQuery(MeetFragment.COURSE_FIELD);
		query.whereEqualTo("number", courseNum);
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> courseList, ParseException e) {
		        if (e == null && !courseList.isEmpty()) {
		            //delete the User object
		        	ParseObject obj = courseList.get(0);
		        	ParseUser user = ParseUser.getCurrentUser();
		        	obj.removeAll("users", Arrays.asList(user.getUsername()));
		        	user.removeAll("courses", Arrays.asList(obj.getString("number")));
		        	user.saveInBackground();
		        	obj.saveInBackground();
//		        	MeetFragment.revertHome();
		        }
		        else {
		        	// there was an error that should be handled
		        }
		    }
		});
		        	
		
	}

	private void deleteCourseFromUser(String courseNum) {
		ParseUser.getCurrentUser().removeAll("courses", Arrays.asList(courseNum));
	}
	
	private void removeCourseChannel(String courseNum) {
		String courseChannel = "T"+courseNum.replace(".", "_");
		PushService.unsubscribe(getBaseContext(), courseChannel);
	}

}
