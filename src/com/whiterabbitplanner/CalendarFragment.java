package com.whiterabbitplanner;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.tyczj.extendedcalendarview.Day;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment{
	
	public CalendarFragment(){}
	
	//@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
	    int startDayYear = 2014, startDayMonth = 01, startDayDay = 01, startTimeHour = 10, startTimeMin = 45, 
	    endDayYear = 2014, endDayMonth = 01, endDayDay = 01, endTimeHour = 11, endTimeMin = 30;
	    
		/*Day d = new Day(mActivity, endDayDay, endDayYear, endDayMonth);
		ContentValues values = new ContentValues();
	    values.put(CalendarProvider.COLOR, Event.COLOR_RED);
	    values.put(CalendarProvider.DESCRIPTION, "Run away");
	    values.put(CalendarProvider.LOCATION, "Home");
	    values.put(CalendarProvider.EVENT, "Escape Plan");

	    Calendar cal = Calendar.getInstance();

	    cal.set(startDayYear, startDayMonth, startDayDay, startTimeHour, startTimeMin);
	    values.put(CalendarProvider.START, cal.getTimeInMillis());
	    values.put(CalendarProvider.START_DAY, julianDay);
	    TimeZone tz = TimeZone.getDefault();

	    cal.set(endDayYear, endDayMonth, endDayDay, endTimeHour, endTimeMin);
	    int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

	    values.put(CalendarProvider.END, cal.getTimeInMillis());
	    values.put(CalendarProvider.END_DAY, endDayJulian);

	    Uri uri = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);
		*/
	    
		View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
		
		return rootView;
	}
}