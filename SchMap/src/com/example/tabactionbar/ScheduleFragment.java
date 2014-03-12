package com.example.tabactionbar;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.parse.ParseObject;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ScheduleFragment extends Fragment {

        //@Override
//      public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                      Bundle savedInstanceState) {

//              return (LinearLayout) inflater.inflate(R.layout.schedule, container, false);
        //}

//}
Button[] fullMonth = new Button[42];
private final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
                                                "July", "August", "September", "October", "November", "December"};
private final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
private int todaysDay;
private int todaysMonth;
private int todaysYear;

private int shownDay;
private int shownMonth;
private int shownYear;

private int offsetForFirstDay;

// Top row
TextView monthDisplay;
Button previousMonth, nextMonth;

// Week 1
Button a1, a2, a3, a4, a5, a6, a7;

// Week 2
Button b1, b2, b3, b4, b5, b6, b7;

// Week 3
Button c1, c2, c3, c4, c5, c6, c7;

// Week 4
Button d1, d2, d3, d4, d5, d6, d7;

// Week 5
Button e1, e2, e3, e4, e5, e6, e7;

// Week 6
Button f1, f2, f3, f4, f5, f6, f7;

// Below Calendar
Button addEvent;
View view;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//onClick setup for the check boxes
		View view1 = inflater.inflate(R.layout.schedule, container, false);
		this.view=view1;
        // Collecting the date
        Calendar c = Calendar.getInstance();
        todaysDay = c.get(Calendar.DAY_OF_MONTH);
        todaysMonth = c.get(Calendar.MONTH);
        todaysYear = c.get(Calendar.YEAR);

        shownDay = 50;
        shownMonth = todaysMonth;
        shownYear = todaysYear;

        offsetForFirstDay = (c.get(Calendar.DAY_OF_WEEK)) - todaysDay % 7;

        // Declaration of top row
        monthDisplay = (TextView) view.findViewById(R.id.tvMonth);
        previousMonth = (Button) view.findViewById(R.id.bPreviousMonth);
        nextMonth = (Button) view.findViewById(R.id.bNextMonth);

        addEvent = (Button) view.findViewById(R.id.bAddEvent);

        // Declaration of calendar
        // Week 1
        a1 = (Button) view.findViewById(R.id.bA1);
        a2 = (Button) view.findViewById(R.id.bA2);
        a3 = (Button) view.findViewById(R.id.bA3);
        a4 = (Button) view.findViewById(R.id.bA4);
        a5 = (Button) view.findViewById(R.id.bA5);
        a6 = (Button) view.findViewById(R.id.bA6);
        a7 = (Button) view.findViewById(R.id.bA7);

        // Week 2
        b1 = (Button) view.findViewById(R.id.bB1);
        b2 = (Button) view.findViewById(R.id.bB2);
        b3 = (Button) view.findViewById(R.id.bB3);
        b4 = (Button) view.findViewById(R.id.bB4);
        b5 = (Button) view.findViewById(R.id.bB5);
        b6 = (Button) view.findViewById(R.id.bB6);
        b7 = (Button) view.findViewById(R.id.bB7);

        // Week 3
        c1 = (Button) view.findViewById(R.id.bC1);
        c2 = (Button) view.findViewById(R.id.bC2);
        c3 = (Button) view.findViewById(R.id.bC3);
        c4 = (Button) view.findViewById(R.id.bC4);
        c5 = (Button) view.findViewById(R.id.bC5);
        c6 = (Button) view.findViewById(R.id.bC6);
        c7 = (Button) view.findViewById(R.id.bC7);

        // Week 4
        d1 = (Button) view.findViewById(R.id.bD1);
        d2 = (Button) view.findViewById(R.id.bD2);
        d3 = (Button) view.findViewById(R.id.bD3);
        d4 = (Button) view.findViewById(R.id.bD4);
        d5 = (Button) view.findViewById(R.id.bD5);
        d6 = (Button) view.findViewById(R.id.bD6);
        d7 = (Button) view.findViewById(R.id.bD7);

        // Week 5
        e1 = (Button) view.findViewById(R.id.bE1);
        e2 = (Button) view.findViewById(R.id.bE2);
        e3 = (Button) view.findViewById(R.id.bE3);
        e4 = (Button) view.findViewById(R.id.bE4);
        e5 = (Button) view.findViewById(R.id.bE5);
        e6 = (Button) view.findViewById(R.id.bE6);
        e7 = (Button) view.findViewById(R.id.bE7);

        // Week 6
        f1 = (Button) view.findViewById(R.id.bF1);
        f2 = (Button) view.findViewById(R.id.bF2);
        f3 = (Button) view.findViewById(R.id.bF3);
        f4 = (Button) view.findViewById(R.id.bF4);
        f5 = (Button) view.findViewById(R.id.bF5);
        f6 = (Button) view.findViewById(R.id.bF6);
        f7 = (Button) view.findViewById(R.id.bF7);

/////////////////////////////////////////////////////////////////////
        // Creating Grid
        fullMonth[0] = a1;
        fullMonth[1] = a2;
        fullMonth[2] = a3;
        fullMonth[3] = a4;
        fullMonth[4] = a5;
        fullMonth[5] = a6;
        fullMonth[6] = a7;

        fullMonth[7] = b1;
        fullMonth[8] = b2;
        fullMonth[9] = b3;
        fullMonth[10] = b4;
        fullMonth[11] = b5;
        fullMonth[12] = b6;
        fullMonth[13] = b7;

        fullMonth[14] = c1;
        fullMonth[15] = c2;
        fullMonth[16] = c3;
        fullMonth[17] = c4;
        fullMonth[18] = c5;
        fullMonth[19] = c6;
        fullMonth[20] = c7;

        fullMonth[21] = d1;
        fullMonth[22] = d2;
        fullMonth[23] = d3;
        fullMonth[24] = d4;
        fullMonth[25] = d5;
        fullMonth[26] = d6;
        fullMonth[27] = d7;

        fullMonth[28] = e1;
        fullMonth[29] = e2;
        fullMonth[30] = e3;
        fullMonth[31] = e4;
        fullMonth[32] = e5;
        fullMonth[33] = e6;
        fullMonth[34] = e7;

        fullMonth[35] = f1;
        fullMonth[36] = f2;
        fullMonth[37] = f3;
        fullMonth[38] = f4;
        fullMonth[39] = f5;
        fullMonth[40] = f6;
        fullMonth[41] = f7;
/////////////////////////////////////////////////////////////////////
        // Action Listeners
        previousMonth.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        shownMonth--;
                        if(shownMonth < 0){
                                shownMonth = 11;
                                shownYear--;
                        }
                        int monthLength = DAYS_IN_MONTH[shownMonth];
                        if(shownMonth == 1 && shownYear % 4 == 0)
                                monthLength = 29;
                        offsetForFirstDay += (7- monthLength%7);

                        if(offsetForFirstDay < 0)
                                offsetForFirstDay *= -1;

                        offsetForFirstDay %= 7;

                        if (shownMonth == todaysMonth && shownYear == todaysYear){
                                shownDay = todaysDay;
                        }else{
                                shownDay = 50;
                        }

                        generateCalendar(shownDay, shownMonth, shownYear);
                }
        });

        nextMonth.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int monthLength = DAYS_IN_MONTH[shownMonth];
                        if(shownMonth == 1 && shownYear % 4 == 0)
                                monthLength = 29;
                        offsetForFirstDay += (monthLength%7);
                        offsetForFirstDay %= 7;

                        shownMonth++;
                        if(shownMonth > 11){
                                shownMonth = 0;
                                shownYear++;
                        }

                        if (shownMonth == todaysMonth && shownYear == todaysYear){
                                shownDay = todaysDay;
                        }else{
                                shownDay = 50;
                        }

                        generateCalendar(shownDay, shownMonth, shownYear);
                }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        addEventNow();
                }
        });

        // Week 1 Action Listeners
        a1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a1.getText());
                }
        });

        a2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a2.getText());

                }
        });
        a3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a3.getText());

                }
        });
        a4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a4.getText());

                }
        });
        a5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a5.getText());

                }
        });
        a6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a6.getText());

                }
        });
        a7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) a7.getText());

                }
        });
/////////////////////////////////////////////////////////////////////
        // Week 2 Action Listeners

        b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b1.getText());

                }
        });

        b2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b2.getText());

                }
        });
        b3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b3.getText());

                }
        });
        b4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b4.getText());

                }
        });
        b5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b5.getText());

                }
        });
        b6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b6.getText());

                }
        });
        b7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) b7.getText());

                }
        });
/////////////////////////////////////////////////////////////////////
        // Week 3 Action Listeners

        c1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c1.getText());

                }
        });

        c2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c2.getText());

                }
        });
        c3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c3.getText());

                }
        });
        c4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c4.getText());

                }
        });
        c5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c5.getText());

                }
        });
        c6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c6.getText());

                }
        });
        c7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) c7.getText());

                }
        });
/////////////////////////////////////////////////////////////////////
        // Week 4 Action Listeners

        d1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d1.getText());

                }
        });

        d2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d2.getText());

                }
        });
        d3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d3.getText());

                }
        });
        d4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d4.getText());

                }
        });
        d5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d5.getText());

                }
        });
        d6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d6.getText());

                }
        });
        d7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) d7.getText());

                }
        });
/////////////////////////////////////////////////////////////////////
        // Week 5 Action Listeners

        e1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e1.getText());

                }
        });

        e2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e2.getText());

                }
        });
        e3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e3.getText());

                }
        });
        e4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e4.getText());

                }
        });
        e5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e5.getText());

                }
        });
        e6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e6.getText());

                }
        });
        e7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) e7.getText());

                }
        });
////////////////////////////////////////////////////////////////////
        // Week 5 Action Listeners

        f1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f1.getText());

                }
        });

        f2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f2.getText());

                }
        });
        f3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f3.getText());

                }
        });
        f4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f4.getText());

                }
        });
        f5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f5.getText());

                }
        });
        f6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f6.getText());

                }
        });
        f7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        // TODO Auto-generated method stub
                        findEventsForThisDay((String) f7.getText());

                }
        });
        generateCalendar(todaysDay, todaysMonth, todaysYear);
        
        // logging
        ParseObject log = new ParseObject("ScheduleFragment");
     	log.put("action", "just arrived");
     	log.saveInBackground();
     		
		return view;

}

private void generateCalendar(int day, int month, int year){
        String display = MONTHS[month] + " " + Integer.toString(year);
        monthDisplay.setText(display);


        // Calculating the number of days in the month accounting for February
        int monthLength = DAYS_IN_MONTH[month];
        if(month == 1 && year % 4 == 0)
                monthLength = 29;



        // Loop through each day and update number displayed and events
        for(int dayIndex = 0; dayIndex < 42; dayIndex++){
                if(dayIndex >= offsetForFirstDay && dayIndex <= (monthLength + offsetForFirstDay - 1)){

                        fullMonth[dayIndex].getBackground().setVisible(true, false);

                        fullMonth[dayIndex].setClickable(true);
                        fullMonth[dayIndex].setText(Integer.toString(dayIndex-offsetForFirstDay + 1));
                        fullMonth[dayIndex].getBackground().setAlpha(1);
                        fullMonth[dayIndex].getBackground().clearColorFilter();
                        fullMonth[dayIndex].getBackground().setAlpha(255);


                        if(dayIndex == day){
                                String today = Integer.toString(dayIndex-offsetForFirstDay + 1) + " *";
                                fullMonth[dayIndex].setText(today);
                                fullMonth[dayIndex].getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                        }
                }else{
                        fullMonth[dayIndex].setClickable(false);
                        fullMonth[dayIndex].getBackground().clearColorFilter();
                        fullMonth[dayIndex].getBackground().setAlpha(0);
                        fullMonth[dayIndex].setText("");
                }

        }
}
/*
@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
}
*/
private void findEventsForThisDay(String day){
        if (day.length() >= 3){
                if(day.contains("*"))
                        Toast.makeText(view.getContext(),  "Today - No events planned.", Toast.LENGTH_LONG).show();

        }else if ( day.equals(Integer.toString(todaysDay + 1))  && shownMonth == todaysMonth && shownYear == todaysYear){
            Toast.makeText(view.getContext(),  "Tomorrow - No events planned.", Toast.LENGTH_LONG).show();

        }else if( day.equals(Integer.toString(todaysDay - 1))  && shownMonth == todaysMonth && shownYear == todaysYear){
            Toast.makeText(view.getContext(),  "Yesterday - No events planned.", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(view.getContext(), shownMonth + "/" + day + "/" +  shownYear + " - No events planned.",   Toast.LENGTH_SHORT).show();
        }
}
/*add an event to calendar*/
/*
private void addEvent() {
    ContentValues l_event = new ContentValues();
    l_event.put("calendar_id", m_selectedCalendarId);
    l_event.put("title", "roman10 calendar tutorial test");
    l_event.put("description", "This is a simple test for calendar api");
    l_event.put("eventLocation", "@home");
    l_event.put("dtstart", System.currentTimeMillis());
    l_event.put("dtend", System.currentTimeMillis() + 1800*1000);
    l_event.put("allDay", 0);
    //status: 0~ tentative; 1~ confirmed; 2~ canceled
    l_event.put("eventStatus", 1);
    //0~ default; 1~ confidential; 2~ private; 3~ public
    l_event.put("visibility", 0);
    //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
    l_event.put("transparency", 0);
    //0~ false; 1~ true
    l_event.put("hasAlarm", 1);
    Uri l_eventUri;
    if (Build.VERSION.SDK_INT >= 8 ) {
        l_eventUri = Uri.parse("content://com.android.calendar/events");
    } else {
        l_eventUri = Uri.parse("content://calendar/events");
    }
    Uri l_uri = this.getContentResolver().insert(l_eventUri, l_event);
    Log.v("++++++test", l_uri.toString());
}
*/
/*add an event through intent, this doesn't require any permission
* just send intent to android calendar
*/

private void addEventNow() {
   Intent l_intent = new Intent(Intent.ACTION_EDIT);
   l_intent.setType("vnd.android.cursor.item/event");
   //l_intent.putExtra("calendar_id", m_selectedCalendarId);  //this doesn't work
   l_intent.putExtra("title", "");
   l_intent.putExtra("description", "Sample calendar event");
   l_intent.putExtra("eventLocation", "@home");
   l_intent.putExtra("beginTime", System.currentTimeMillis());
   l_intent.putExtra("endTime", System.currentTimeMillis() + 1800*1000);
   l_intent.putExtra("allDay", 0);
   //status: 0~ tentative; 1~ confirmed; 2~ canceled
   l_intent.putExtra("eventStatus", 1);
   //0~ default; 1~ confidential; 2~ private; 3~ public
   l_intent.putExtra("visibility", 0);
   //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
   l_intent.putExtra("transparency", 0);
   //0~ false; 1~ true
   l_intent.putExtra("hasAlarm", 1);
   try {
       startActivity(l_intent);
       //MapFragFake.addMarkerScedule();
   } catch (Exception e) {
       Toast.makeText(this.getActivity().getApplicationContext(), "Sorry, no compatible calendar is found!", Toast.LENGTH_LONG).show();
   }
}

public static final String[] FIELDS = { CalendarContract.Calendars.NAME,
          CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
          CalendarContract.Calendars.CALENDAR_COLOR,
          CalendarContract.Calendars.VISIBLE };

          public static final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");

          ContentResolver contentResolver;
          Set<String> calendars = new HashSet<String>();

          public  void CalendarContentResolver(Context ctx) {
            contentResolver = ctx.getContentResolver();
          }

          public Set<String> getCalendars() {
            // Fetch a list of all calendars sync'd with the device and their display names
            Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS, null, null, null);

            try {
              if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                  String name = cursor.getString(0);
                  String displayName = cursor.getString(1);
                  // This is actually a better pattern:
                  String color = cursor.getString(cursor.getColumnIndex(
                          CalendarContract.Calendars.CALENDAR_COLOR));
                  Boolean selected = !cursor.getString(3).equals("0");
                  calendars.add(displayName);
                }
              }
            } catch (AssertionError ex) {
              // TODO: log exception and bail
            }
            return calendars;
          }


}
