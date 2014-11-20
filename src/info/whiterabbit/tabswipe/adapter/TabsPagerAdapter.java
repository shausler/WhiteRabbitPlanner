package info.whiterabbit.tabswipe.adapter;

import com.whiterabbitplanner.CalendarFragment;
import com.whiterabbitplanner.ContactsFragment;
import com.whiterabbitplanner.HomeFragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TabsPagerAdapter extends FragmentPagerAdapter
{
	public TabsPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index)
	{
		switch( index )
		{
			case 0:
				// Contacts fragment activity
				return new ContactsFragment();
			case 1:
				// Home fragment activity
				return new HomeFragment();
			case 2:
				// Calendar fragment activity
				return new CalendarFragment();
		}
		return null;
	}
	
	@Override
	public int getCount()
	{
		// get item count - equal to number of tabs
		return 3;
	}
}
