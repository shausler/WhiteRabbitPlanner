package com.whiterabbitplanner;

import java.util.Collections;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ListActivity;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.widget.AdapterView;
import android.widget.AbsListView;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
	//ListView object to display contacts
	ListView mContactsList;
	//This is the Adapter being used to display the list's data
	SimpleCursorAdapter mCursorAdapter;
		
		
	//These are the Contacts rows that we will retrieve
	static final String[] PROJECTION = new String[] {ContactsContract.Data._ID, 
													 ContactsContract.Data.LOOKUP_KEY,
													 ContactsContract.Data.DISPLAY_NAME_PRIMARY};
	//The column index for the _ID column
	private static final int CONTACT_ID_INDEX = 0;
	//The column index for the LOOKUP_KEY column
	private static final int LOOKUP_KEY_INDEX = 1;
	//The column index for the DISPLAY_NAME_PRIMARY column
	private static final int DISPLAY_NAME_PRIMARY_INDEX = 2;
	
	
	//This is the select criteria
	static final String SELECTION = "((" +
			ContactsContract.Data.DISPLAY_NAME_PRIMARY + " NOTNULL) AND (" +
			ContactsContract.Data.DISPLAY_NAME_PRIMARY + " != '' ))";
	
	private final static String[] FROM_COLUMNS = { Contacts.DISPLAY_NAME_PRIMARY };
	private final static int[] TO_IDS = { android.R.id.text1 };

	long mContactId;
	String mContactKey;
	Uri mContactUri;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
		return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		//Gets the ListView from the View list of the parent activity
		mContactsList = (ListView)getActivity().findViewById(R.id.list);
		mContactsList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		//Gets a CursorAdapter
		mCursorAdapter = new SimpleCursorAdapter( getActivity(), R.layout.contacts_list_item, null, FROM_COLUMNS, TO_IDS, 0 );
		mContactsList.setAdapter(mCursorAdapter);
		
		//Set the item click listener to be the current fragment.
		mContactsList.setOnItemClickListener(this);
		
		//Initializes the loader
		getLoaderManager().initLoader(0, null, this);
	}

	//Called when a new Loader needs to be created
	public Loader<Cursor> onCreateLoader(int loaderId, Bundle args)
	{
		/*
		 * Makes search string into pattern and stores it in selection array
		 */
		return new CursorLoader( getActivity(), Contacts.CONTENT_URI, PROJECTION, SELECTION, null, Contacts.DISPLAY_NAME_PRIMARY + " ASC" /* this sorts the order */  );
	}
	
	//Called when a previously created loader has finished loading
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		//Swap the new cursor in. (The framework will take care of closing the
		//old cursor once we return
		mCursorAdapter.swapCursor(cursor);
	}
	
	//Called when a previously created loader is reset, making the data unavailable
	public void onLoaderReset(Loader<Cursor> loader)
	{
		//Delete the reference to the existing Cursor
		mCursorAdapter.swapCursor(null);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View item, int position, long rowID)
	{
		
		
		//Get the Cursor
		Cursor cursor = ((CursorAdapter)parent.getAdapter()).getCursor();
		//Move to the selected contact
		cursor.moveToPosition(position);
		//Get the _ID value
		mContactId = cursor.getLong(CONTACT_ID_INDEX);
		//Get the selected LOOKUP KEY
		mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
		//Create the contact's content URI
		mContactUri = Contacts.getLookupUri(mContactId, mContactKey);
		/*
		 * You can use mContactUri as the content URI for retrieving the details for a contact
		 */	
	}
	
	public void insertContact(String name, String email, String secondEmail, String phone, String secondPhone, String notes, String postal) {
	    Intent intent = new Intent(Intent.ACTION_INSERT);
	    intent.setType(Contacts.CONTENT_TYPE);
	    intent.putExtra(Intents.Insert.NAME, name);
	    intent.putExtra(Intents.Insert.EMAIL, email);
	    intent.putExtra(Intents.Insert.SECONDARY_EMAIL, secondEmail);
	    intent.putExtra(Intents.Insert.PHONE, phone);
	    intent.putExtra(Intents.Insert.SECONDARY_PHONE, secondPhone);
	    intent.putExtra(Intents.Insert.NOTES, notes);
	    intent.putExtra(Intents.Insert.POSTAL, postal);
	    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}
	
}