package com.example.tabactionbar;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.ListFragment;
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
public class MeetGroupFragment extends ListFragment{
	
	public static FragmentManager fm;
	private static GroupListViewAdapter adapter;
	private static ArrayList<GroupModel> groupList;
	private String cNum;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.course_fragment, null);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	  groupList = new ArrayList<GroupModel>();
	  adapter = new GroupListViewAdapter(getActivity(), groupList);
	  adapter.setNotifyOnChange(true);
	  setListAdapter(adapter);
	  setGroupList();
	}

	/**
	 * refreshes the grouplist that is used by the adapter to show all
	 * of the groups
	 */
	private void setGroupList() {
		ParseQuery query = new ParseQuery(MeetFragment.GROUP_FIELD);
		query.whereEqualTo("coursenum", cNum);
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> tList, ParseException e) {
				if (e == null) {
					if (!tList.isEmpty()) {
						for (ParseObject obj:tList) {
							groupList.add(new GroupModel(obj.getString("name"),obj.getString("location"),
									obj.getString("type"), obj.getString("style"),obj.getInt("begin_h"),
									obj.getInt("begin_m"),obj.getInt("end_h"),obj.getInt("end_m"),obj.getInt("capacity"),
									obj.getString("am_pm"),obj.getBoolean("visible"),obj.getBoolean("private")));
						}
					}
					adapter.notifyDataSetChanged();
				}
				else {
					// do something if the query is not successful
				}
			}
			
		});
		
	}
	
	/**
	 * updates the listview for the user to notice their new group
	 */
	public void updateGroupList() {
		groupList.removeAll(groupList);
		setGroupList();
	}
	
	/**
	 * Set the course number for this fragemnt
	 * @param courseNum
	 */
	public void setCourseNum(String courseNum) {
		cNum = courseNum;
	}
	
	/**
	 * get the course number for the current course the user
	 * is focused on
	 * @return
	 */
	public String getCourseNum() {
		return cNum;
	}
	
	public ArrayList<GroupModel> getGroupList() {
		return groupList;
	}
	
}
