package com.whiterabbitplanner;

import info.whiterabbit.tabswipe.adapter.TabsPagerAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    
    
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    
    long dateSelected;

    private String[] tabs = {"Contacts", "Home", "Calendar" };
    
    String title;
    String location;
    String date;
    String time;
    String description;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        for(String tab_name: tabs)
        {
        	actionBar.addTab(actionBar.newTab().setText(tab_name)
        			.setTabListener(this));
        }
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
        {
            @Override
            public void onPageSelected(int position) 
            {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        
        //Set 'Home' Tab as default
        viewPager.setCurrentItem(1,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	// Inflate the menu items for use in the action bar
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_activity_actions, menu);
    	return super.onCreateOptionsMenu(menu);
        
    	
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	//long dateSelected;
    	
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add)
        {
			//Intent intent = new Intent(Intent.ACTION_INSERT);
			Intent intent = new Intent(Intent.ACTION_EDIT);
	        intent.setType("vnd.android.cursor.item/event");
			if(intent.resolveActivity(getPackageManager()) != null)
			{
				startActivity(intent);	
			}

        }
        	//Toast.makeText(this, "Create Event", Toast.LENGTH_SHORT).show();
        	
        	//TextView dateField = (TextView)findViewById(R.id.event_date);
        	/*
        	View dialogView = View.inflate(this, R.layout.popup_event,  null);
         	
        	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        	dialog.setView(dialogView);
        	/*
        	final EditText date = (EditText)findViewById(R.id.event_date);
			final Calendar myCalendar = Calendar.getInstance();
		    final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener()
		    {
		    	@Override
		    	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		    	{
		    		myCalendar.set(Calendar.YEAR, year);
		    		myCalendar.set(Calendar.MONTH, monthOfYear);
		    		myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		    		
		    		String myFormat = "MM/dd/yyyy";
		    		SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.US);
		    		date.setText(format.format(myCalendar.getTime()));
		    	}
		    };
			
			date.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					new DatePickerDialog(getActivity(), date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				}
			});
        	
        	dialog.setPositiveButton("Save Event", new DialogInterface.OnClickListener()
        	{
        		public void onClick(DialogInterface dialog, int whichButton)
        		{
        			EditText title = (EditText)((Dialog)dialog).findViewById(R.id.event_title);
        			String titleString = title.getText().toString();
        			EditText date = (EditText)((Dialog)dialog).findViewById(R.id.event_date);
        			String dateString = date.getText().toString();
        			EditText time = (EditText)((Dialog)dialog).findViewById(R.id.event_time);
        			String timeString = time.getText().toString();
        			EditText location = (EditText)((Dialog)dialog).findViewById(R.id.event_location);
        			String locationString = location.getText().toString();
        			EditText length = (EditText)((Dialog)dialog).findViewById(R.id.event_length);
        			String lengthString = length.getText().toString();
        			EditText description = (EditText)((Dialog)dialog).findViewById(R.id.event_description);
        			String descriptionString = description.getText().toString();
        			
        			if((titleString.length()==0)&&
        			   (dateString.length()==0)&&
        			   (timeString.length()==0)&&
        			   (locationString.length()==0)&&
        			   (lengthString.length()==0)&&
        			   (descriptionString.length()==0))
        			{
        				dialog.dismiss();
        			}
        			//String toastHappy = titleString+"\n"+dateString+"\n"+timeString+"\n"+locationString+"\n"+lengthString+"\n"+descriptionString;
        			//Toast.makeText(getActivity(), toastHappy, Toast.LENGTH_SHORT).show();
        			
        			String splitDate[] = dateString.split("/");
        			String splitTime[] = timeString.split(":|;");
        			Calendar eventInformation = Calendar.getInstance();
        			eventInformation.clear();
        			//eventInformation.add(Calendar.MONTH, Integer.parseInt(splitDate[0]));
        			//eventInformation.add(Calendar.DAY_OF_MONTH, Integer.parseInt(splitDate[1]));
        			//eventInformation.add(Calendar.YEAR, Integer.parseInt(splitDate[2]));
        			//eventInformation.add(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
        			//eventInformation.add(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
        			//eventInformation.add(Calendar.MILLISECOND, 0);
        			eventInformation.set(Integer.parseInt(splitDate[0]),
        								 Integer.parseInt(splitDate[1]),
        								 Integer.parseInt(splitDate[2]),
        								 Integer.parseInt(splitTime[0]),
        								 Integer.parseInt(splitTime[1]));
        			
        			String datestuff = "Month: "+Integer.parseInt(splitDate[0])+
        							   "\nDay: "+Integer.parseInt(splitDate[1])+
        							   "\nYear: "+Integer.parseInt(splitDate[2])+
        							   "\nHour: "+Integer.parseInt(splitTime[0])+
        							   "\nMinutes: "+Integer.parseInt(splitTime[1]);
        			
        			SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
        			Toast.makeText(getActivity(), datestuff, Toast.LENGTH_LONG).show();
        			long milliseconds = eventInformation.getTimeInMillis();
        			long addTime = (long) (10000*Double.parseDouble(lengthString));
        			long endTime = milliseconds+addTime;
        			
        			//Toast.makeText(getActivity(), "milliseconds:\n"+milliseconds, Toast.LENGTH_LONG).show();
        			
        			String dateMillis = Integer.parseInt(splitDate[2])+Integer.parseInt(splitDate[1])+Integer.parseInt(splitDate[0])+
        								Integer.parseInt(splitTime[0])+Integer.parseInt(splitTime[1])+"00";
        			long millis = Long.parseLong(dateMillis);
        			
        			GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(splitDate[0])-1,
        														  Integer.parseInt(splitDate[1]),
        														  Integer.parseInt(splitDate[2]),
        														  Integer.parseInt(splitTime[0]),
        														  Integer.parseInt(splitTime[1]));
        			*/
        				//.setData(Events.CONTENT_URI)
        				//.putExtra(Events.TITLE, titleString)
        				//.putExtra(Events.EVENT_LOCATION, locationString)
        				//.putExtra(Events.DTSTART, millis)
        				//.putExtra(Events.DTEND, millis)
        				//.putExtra(Events.DESCRIPTION, descriptionString);        			
        		//}
        	//});
        	//dialog.setTitle("Add Event");
        	//dialog.show();
        return false;
        }
        //return super.onOptionsItemSelected(item);
   //}

    protected Context getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
    {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
    {
    	
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }


}
