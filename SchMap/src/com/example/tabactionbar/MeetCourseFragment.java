package com.example.tabactionbar;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.parse.ParseUser;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeetCourseFragment extends Fragment{
	
	private static ImageButtonAdapter mAdapter;
	static final String COURSE_FIELD = "courses";
	private static List<Object> courseList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		setupInitialLayout();
		courseList = ParseUser.getCurrentUser().getList(COURSE_FIELD);
//		final ArrayList<String> mThumbIds = new ArrayList<String>(Arrays.asList("7.02","5.111","6.02"));
		View view = inflater.inflate(R.layout.course_home_meet, container, false);
		GridView gridV = (GridView) view.findViewById(R.id.course_grid_view);
		mAdapter = new ImageButtonAdapter(view.getContext(), courseList);
		gridV.setAdapter(mAdapter);
		
		return view;
	}
	
	/**
	 * After the addition of a new course, the gridView layout must be
	 * updated. This method updates the adpater used to alter the view 
	 * of the grid layout
	 */
	public static void updateCourseGridView() {
		courseList = ParseUser.getCurrentUser().getList(COURSE_FIELD);
		mAdapter.updateList(courseList);
	}
}