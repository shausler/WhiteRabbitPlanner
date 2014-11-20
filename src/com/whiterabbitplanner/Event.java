package com.whiterabbitplanner;

import java.util.Date;

import android.text.format.Time;

public class Event {

	Date date;
	Time time;
	boolean priority;
	String soundLocation;
	String notificationText;
	Contact people[];
	
	//if given everything
	public Event(Date date1, Time time1, boolean priority1, String soundLocation1, String notificationText1, Contact people1[])
	{
		date = date1;
		time = time1;
		priority = priority1;
		soundLocation = soundLocation1;
		notificationText = notificationText1;
		people = people1;
	}
	
	//if not given contacts
	public Event(Date date1, Time time1, boolean priority1, String soundLocation1, String notificationText1)
	{
		date = date1;
		time = time1;
		priority = priority1;
		soundLocation = soundLocation1;
		notificationText = notificationText1;
	}
	
	
	
	public boolean isTriggered()
	{
		//if event time on event day is passed
		return false;
	}
	
}
