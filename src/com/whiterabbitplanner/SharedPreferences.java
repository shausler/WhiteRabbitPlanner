package com.whiterabbitplanner;

import java.io.FileOutputStream;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.text.format.Time;

public class SharedPreferences extends Fragment {

	/* needs the line 
	 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	 * inside the manifest.xml
	 
	
	
	//Retrieving your SharedPreferences, or app data, from inside a Fragment
	public String getSharedPreferences()
	{
		Context context = getActivity();
		SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}
	
	//Retrieving SharedPreferences (from inside a Activity)
	public String getGeneralPreferences()
	{
		//retrieves a default shared preference file that belongs to the activity, you don't need to supply a name.
		android.content.SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
	}
	
	//writing to the SharedPreferences file
	public void writeSharedPreferences()
	{
		android.content.SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		editor.putInt("Value", 1);
		editor.commit();
	
	}
	
	//Read from SharedPreferences
	public String getPreferences()
	{
		//
		int defaultValue = 0;
		android.content.SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		//int defaultValue = getResources().getInteger(R.string.saved_high_score_default); //the default int value
		long highScore = sharedPref.getInt(getString("Bob"), defaultValue); //search for "Bob"
	}
	
	Date d;
	Time t;
	Event ev1 = new Event(d,t,false, "user/sounds", "You're late!");
	
	public Void eventToInternal(String filename, Event event)
	{
		FileOutputStream outputStream;
		
		try {
		  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		  outputStream.write(event.toString());
		  outputStream.
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	*/
}
