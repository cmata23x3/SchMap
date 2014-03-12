package com.example.tabactionbar;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ImageButtonAdapter extends BaseAdapter{

	private Context mContext;
	private List<Object> mThumbIds;
	
	public ImageButtonAdapter(Context c, List<Object> courseList) {
		mContext = c;
		mThumbIds = courseList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button imageButton;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageButton = new Button(mContext);
            imageButton.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageButton.setPadding(8, 8, 8, 8);
        } else {
            imageButton = (Button) convertView;
        }

        final String courseNum = (String) mThumbIds.get(position);
        imageButton.setText(courseNum);
        imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				MeetFragment.switchGroupFragment(courseNum);
			}
		});
        return imageButton;
	}
	
	public void updateList(List<Object> newList) {
		mThumbIds = newList;
		this.notifyDataSetChanged();
	}

}
