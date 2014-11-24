package com.whiterabbitplanner;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
	public ContactsFragment(){
		
	}
	
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
	//The column index for the DISPLAY_NAME_PRIMARY column (????????Unused)
	//private static final int DISPLAY_NAME_PRIMARY_INDEX = 2;
	
	
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

		//Button Leading to inserter
		final Button toInserter = (Button) rootView.findViewById(R.id.to_inserter);
		//final Button cancelContact = (Button) rootView.findViewById(R.id.button_cancel);
		
		toInserter.setOnClickListener(new Button.OnClickListener() {
	    	@Override
	        public void onClick(View v) {
	    		// Create new fragment and transaction
	    		Fragment CIF = new ContactInserterFragment();
	    		
	    	    // consider using Java coding conventions (upper first char class names!!!)
	    	    android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    	    
	    	    /*// Replace whatever is in the fragment_container view with this fragment,
	    	    // and add the transaction to the back stack
	    	     transaction.setCustomAnimations(R.animator.enter_slide, R.animator.exit_slide); //Later. 
	    	    // http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
	    	    // http://android-er.blogspot.com/2012/02/apply-animation-on-button.html
	    	    */
	    	    
	    	    transaction.replace(R.id.fragment_contacts, CIF, "Inserter");
	    	    transaction.addToBackStack(null);
	    	    
	    	    // Commit the transaction
	    	    transaction.commit();
	    	}
	    });
	    return rootView;
	}
	
	public void buttonTransition(Button b,int transitionTime){
		
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
}