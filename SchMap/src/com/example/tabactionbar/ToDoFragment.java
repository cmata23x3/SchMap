package com.example.tabactionbar;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ToDoFragment extends Fragment{
	
	public static FragmentManager fm;
	private static Fragment currentFrag;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setupInitialLayout();
		
		return (LinearLayout) inflater.inflate(R.layout.todo, container, false);
	}

	/**
	 * Initializes the initial ListView layout for the Todo tab
	 */
	private void setupInitialLayout() {
		
		currentFrag = new InitListTodoFragment();
		fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.contentFragment, currentFrag);
//		transaction.addToBackStack(null);
		transaction.commit();
		
	}
	
	/**
	 * Popup a Dialog box for the user to enter the task information
	 */
	public static void updateListFragment(String tableName, HashMap<String,String> map) {
		Log.e("TodoFragment", "Saving the task to the DB " + tableName);
		
		InitListTodoFragment.updateView(currentFrag.getActivity(), tableName, map);

	}

}
