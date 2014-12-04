package com.whiterabbitplanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.format.Time;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
	ListView eventList;
	public static ArrayList<String> eventName = new ArrayList<String>();
	public static ArrayList<String> startDates = new ArrayList<String>();
	public static ArrayList<String> endDates = new ArrayList<String>();
	public static ArrayList<String> descriptions = new ArrayList<String>();
	public static ArrayList<String> eventIDs = new ArrayList<String>();
	public static ArrayList<String> nameAndDate = new ArrayList<String>();
	public static ArrayList<String> locations = new ArrayList<String>();
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Context context = getActivity();
		ContentResolver contentResolver = context.getContentResolver();
		// Get current time
		long time = System.currentTimeMillis();
		String [] itemsList = {"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation", "original_id"};
		
		Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
				itemsList, null, null, null); // "calendar_id=1"
		cursor.moveToFirst();
		String[] calNames = new String[cursor.getCount()];
		
		eventName.clear();
		startDates.clear();
		endDates.clear();
		descriptions.clear();
		nameAndDate.clear();
		locations.clear();
		
		//int[] calIds = new int[cursor.getCount()];
		for (int i = 0; i < calNames.length; i++)
		{
			// If event is in the past, don't add it to the list
			Time currentTime = new Time();
			currentTime.setToNow();
			String now = currentTime.toString();
			String eventTime = cursor.getString(3);
			
			// Format Event Date & Time into readable format
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(eventTime));
			eventTime = formatter.format(calendar.getTime());
			String compareTime = eventTime.substring(0,8) + "T" + eventTime.substring(8);
			String nowTime = now.substring(0,15);
			
			
			
			if( compareDates(compareTime, nowTime) )
			{
				eventName.add(cursor.getString(1));
				descriptions.add(cursor.getString(2));
				startDates.add(getDate(Long.parseLong(cursor.getString(3))));
				calNames[i] = cursor.getString(1);
				nameAndDate.add(cursor.getString(1) + "\n" + getDate(Long.parseLong(cursor.getString(3))));
				locations.add(cursor.getString(5));
				//eventIDs.add(cursor.getString(6));
				//cursor.moveToNext();
			}
			cursor.moveToNext();
		}
		
		eventList = (ListView)getActivity().findViewById(R.id.list_home);		
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(),
				R.layout.home_list_item,
				nameAndDate ); //eventName
		
		eventList.setAdapter(arrayAdapter);
		
		//Set the item click listener to be the current fragment.
		eventList.setOnItemClickListener( new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
			{
				String value = (String)adapter.getItemAtPosition(position);
				String description = descriptions.get(position);
				String location = locations.get(position);
				String toastText = "";
				if(description.length() == 0 && location.length() == 0)
				{
					toastText = "No extra information entered in event.";
				}
				if(description.length() > 0 && location.length() == 0)
				{
					toastText = "Description:\n"+description;
				}
				if(description.length() == 0 && location.length() > 0)
				{
					toastText = "Location:\n"+location;
				}
				if(description.length() > 0 && location.length() > 0)
				{
					toastText = "Location:\n"+location+"\n\nDescription:\n"+description;
				}
				// assuming string and if you want to get the value on click of list item
				// do what you intend to do on click of listview row
				
				//Get the Cursor
				//Cursor cursor = ((CursorAdapter)adapter.getAdapter()).getCursor();
				//Move to the selected contact
				//cursor.moveToPosition(position);
				
				//String mDescription = cursor.getString(2);
				
				
				
				//Creates toast notification with contacts name
				//Used for testing retrieval of contact information
				Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static String getDate(long milliSeconds)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a\nMM/dd/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View item, int position, long rowID)
	{
	}

	boolean compareDates(String one, String two)
	{		
		// If year.one is greater than or equal to year.two
		if( Integer.parseInt(one.substring(0,4)) >= Integer.parseInt(two.substring(0,4))) 
		{
			//If year.one is greater than year.two
			if( Integer.parseInt(one.substring(0,4)) > Integer.parseInt(two.substring(0,4)))
			{
				return true;
			}
			// If month.one is greater than or equal to month.two
			if( Integer.parseInt(one.substring(4,6)) >= Integer.parseInt(two.substring(4,6)) )
			{
				// If month.one is greater than month.two
				if( Integer.parseInt(one.substring(4,6)) > Integer.parseInt(two.substring(4,6)) )
				{
					return true;
				}
				// If day.one is greater than or equal to day.two
				if( Integer.parseInt(one.substring(6,8)) >= Integer.parseInt(two.substring(6,8)))
				{
					// If day.one is greater than day.two
					if( Integer.parseInt(one.substring(6,8)) > Integer.parseInt(two.substring(6,8)))
					{
						return true;
					}
					//If hour.one is greater than or equal to hour.two
					if( Integer.parseInt(one.substring(9,11)) >= Integer.parseInt(two.substring(9,11)))
					{
						
						// If hour.one is greater than hour.two
						if( Integer.parseInt(one.substring(9,11)) > Integer.parseInt(two.substring(9,11)))
						{
							return true;
						}
						// If minute.one is greater than or equal to minute.two
						if( Integer.parseInt(one.substring(11,13)) >= Integer.parseInt(two.substring(11,13)))
						{
							// If minute.one is greater than minute.two
							if( Integer.parseInt(one.substring(11,13)) > Integer.parseInt(two.substring(11,13)))
							{
								return true;
							}
							// If seconds.one is greater than to seconds.two
							if( Integer.parseInt(one.substring(13)) > Integer.parseInt(two.substring(13)))
							{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
