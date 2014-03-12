package com.example.tabactionbar;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class GroupListViewAdapter extends ArrayAdapter<GroupModel>{

	private final List<GroupModel> list;
	private final Activity context;
	private final GroupListViewAdapter self = this;
	
	public GroupListViewAdapter(Activity context, List<GroupModel> list) {
		super(context, R.layout.group_textview, list);
		this.list = list;
		this.context = context;
	}

	static class ViewHolder {
        protected TextView name, loc;
        protected Button attend, notAttend;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.group_textview, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.group_name);
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.loc = (TextView) view.findViewById(R.id.group_location);
            viewHolder.loc.setTextColor(Color.GRAY);
            RelativeLayout buttonLayout = (RelativeLayout) view.findViewById(R.id.button_layout);
            Log.e("The list", ParseUser.getCurrentUser().getList("attending").toString()+" and the view name: "+list.get(position).getName());
            if (ParseUser.getCurrentUser().getList("attending").contains(list.get(position).getName())) {
            	createAttendTextView(buttonLayout);
            	createNotAttendButton(buttonLayout, viewHolder);
            	Log.e("attending = true", "attending is true for position: "+position);
            }else if (ParseUser.getCurrentUser().getList("notAttending").contains(list.get(position).getName())) {
            	createNotAttendTextView(buttonLayout);
            	createAttendButton(buttonLayout, viewHolder);
            	Log.e("notAttending = true", "notAttending is true for position: "+position);
            }else {
            	Log.e("nothing set in the db", "regular buttons created");
            	createAttendButton(buttonLayout, viewHolder);
            	createNotAttendButton(buttonLayout, viewHolder);
            }
            view.setTag(viewHolder);
        } else {
            view = convertView;
            RelativeLayout buttonLayout = (RelativeLayout) view.findViewById(R.id.button_layout);
            ViewHolder holder = (ViewHolder) view.getTag();
            Log.e("Testing view name", holder.name.getText().toString());
            
//            // check if the group is in the user's attending or non-attending list
            if (ParseUser.getCurrentUser().getList("attending").contains(list.get(position).getName())) {
            	buttonLayout.removeAllViews();
            	createAttendTextView(buttonLayout);
            	createNotAttendButton(buttonLayout, holder);
            }else if (ParseUser.getCurrentUser().getList("notAttending").contains(list.get(position).getName())) {
            	buttonLayout.removeAllViews();
            	createNotAttendTextView(buttonLayout);
            	createAttendButton(buttonLayout, holder);
            }
//            ((ViewHolder) view.getTag()).attend.setTag(list.get(position));
//            ((ViewHolder) view.getTag()).notAttend.setTag(list.get(position));

        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.loc.setText(list.get(position).getLocation());
        return view;
    }
	
	private void createAttendButton(RelativeLayout buttonLayout,
			final ViewHolder viewHolder) {
		viewHolder.attend = new Button(context);
        viewHolder.attend.setId(2);
        viewHolder.attend.setText(R.string.attend);
        viewHolder.attend.setLayoutParams(new RelativeLayout.LayoutParams(180, 50));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.attend.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.group_name);
        viewHolder.attend.setLayoutParams(layoutParams);
        viewHolder.attend.setTextSize(12);
        Log.e("Inside view", "about to set the listener for attend");
        viewHolder.attend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// add user to the groups list
				Log.e("Attend", "should do something that sets the attendance "+viewHolder.name.getText().toString());
//					ParseQuery query = new ParseQuery(MeetFragment.GROUP_FIELD);
//					query.whereEqualTo("coursenum", cNum);
//					query.findInBackground(new FindCallback() {
//
//						@Override
//						public void done(List<ParseObject> tList, ParseException e) {
//							if (e == null) {
//								if (!tList.isEmpty()) {
//									for (ParseObject obj:tList) {
//										if (obj.getString("name").equals(viewHolder.name.getText().toString())) {
//											obj.addAllUnique("attendees", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
//										}
//									}
//								}
//							}
//							else {
//								// do something if the query is not successful
//							}
//						}
//						
//					});
				ParseUser currentUser = ParseUser.getCurrentUser();
				currentUser.addAllUnique("attending", Arrays.asList(viewHolder.name.getText().toString()));
				currentUser.removeAll("notAttending", Arrays.asList(viewHolder.name.getText().toString()));
				currentUser.saveInBackground();
				self.notifyDataSetChanged();
				
				// log the user's attendance of a group
				ParseObject attending = new ParseObject("GroupMeet");
				attending.put("action", "clicked attending");
				attending.saveInBackground();
			}
        	
        });
        buttonLayout.addView(viewHolder.attend);
		
	}

	private void createNotAttendTextView(RelativeLayout buttonLayout) {
		TextView notAttending = new TextView(context);
    	notAttending.setText("Not Attending");
    	notAttending.setLayoutParams(new RelativeLayout.LayoutParams(180, 50));
    	RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) notAttending.getLayoutParams();
    	layoutParams.addRule(RelativeLayout.ALIGN_LEFT, 2);
        layoutParams.addRule(RelativeLayout.BELOW, 2);
        notAttending.setGravity(Gravity.CENTER);
        notAttending.setLayoutParams(layoutParams);
//    	buttonLayout.removeView(holder.notAttend);
    	buttonLayout.addView(notAttending);
	}

	private void createNotAttendButton(RelativeLayout buttonLayout, final ViewHolder viewHolder) {
		viewHolder.notAttend = new Button(context);
//            viewHolder.attend.setId(2);
        viewHolder.notAttend.setText(R.string.not_attend);
        viewHolder.notAttend.setLayoutParams(new RelativeLayout.LayoutParams(180, 50));
        RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams)viewHolder.notAttend.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_LEFT, 2);
        layoutParams.addRule(RelativeLayout.BELOW, 2);
//            layoutParams.height = 30;
//            layoutParams.width = 120;
        viewHolder.notAttend.setLayoutParams(layoutParams);
        viewHolder.notAttend.setTextSize(12);
        Log.e("Inside view", "about to set the listener for notAttend");
        viewHolder.notAttend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// add the user to the groups notattending list
				// or just add the group to the user and add the user to the group's channel
				Log.e("Not Attending", "Do something for not attending");
//					ParseQuery query = new ParseQuery(MeetFragment.GROUP_FIELD);
//					query.whereEqualTo("coursenum", cNum);
//					query.findInBackground(new FindCallback() {
//
//						@Override
//						public void done(List<ParseObject> tList, ParseException e) {
//							if (e == null) {
//								if (!tList.isEmpty()) {
//									for (ParseObject obj:tList) {
//										if (obj.getString("name").equals(viewHolder.name.getText().toString())) {
//											// add th 
//											obj.removeAll("attendees", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
//											obj.addAllUnique("non-attendees", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
//										}
//									}
//								}
//							}
//							else {
//								// do something if the query is not successful
//							}
//						}
//						
//					});
				ParseUser currentUser = ParseUser.getCurrentUser();
				currentUser.removeAll("attending", Arrays.asList(viewHolder.name.getText().toString()));
				currentUser.addAllUnique("notAttending", Arrays.asList(viewHolder.name.getText().toString()));
				currentUser.saveInBackground();
				self.notifyDataSetChanged();
				
				// log the user's not-attendance of a group
				ParseObject attending = new ParseObject("GroupMeet");
				attending.put("action", "clicked not-attending");
				attending.saveInBackground();
			}
        	
        });
        buttonLayout.addView(viewHolder.notAttend);
		
	}

	private void createAttendTextView(RelativeLayout buttonLayout) {
		TextView attending = new TextView(context);
    	attending.setText("Attending");
    	attending.setLayoutParams(new RelativeLayout.LayoutParams(180, 50));
    	attending.setId(2);
    	RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) attending.getLayoutParams();
    	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.group_name);
        attending.setGravity(Gravity.CENTER);
        attending.setLayoutParams(layoutParams);
//    	buttonLayout.removeView(holder.attend);
    	buttonLayout.addView(attending);	
	}

	@Override
	public boolean areAllItemsEnabled() {
	    return true;
	}
	
	@Override
	public boolean isEnabled(int position) {
	    return true;
	}
	
}
