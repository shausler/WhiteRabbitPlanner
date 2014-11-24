package com.whiterabbitplanner;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ContactInserterFragment extends Fragment{
	
	public ContactInserterFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_contact_inserter, container, false);
		
		// keyboard will not cover text its editing
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		// Text fields and submit and cancel buttons
		final EditText name = (EditText) rootView.findViewById(R.id.name);
		final EditText email = (EditText) rootView.findViewById(R.id.email);
		final EditText secEmail = (EditText) rootView.findViewById(R.id.secondEmail);
		final EditText phone = (EditText) rootView.findViewById(R.id.phone);
		final EditText secPhone = (EditText) rootView.findViewById(R.id.secondPhone);
		final EditText notes = (EditText) rootView.findViewById(R.id.notes);
		final EditText postal = (EditText) rootView.findViewById(R.id.postal);
		Button submitContact = (Button) rootView.findViewById(R.id.button_insert);
		//Button cancelContact = (Button) rootView.findViewById(R.id.button_cancel); //not working yet, use back

		// intent insert on click
		submitContact.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				insertContact(name.getText().toString(), email.getText().toString(), secEmail.getText().toString(), phone.getText().toString(), secPhone.getText().toString(), notes.getText().toString(), postal.getText().toString());
			}
		});
		
		/*// revert to contacts page on cancel
		cancelContact.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Create new fragment and transaction
				
				Fragment newFragment = new ContactsFragment();
	    	    android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    	    
	    	    // Replace whatever is in the fragment_container view with this fragment,
	    	    // and add the transaction to the back stack
	    	     transaction.setCustomAnimations(R.animator.enter_slide, R.animator.exit_slide); //Later. 
	    	    // http://stackoverflow.com/questions/4932462/animate-the-transition-between-fragments
	    	    // http://android-er.blogspot.com/2012/02/apply-animation-on-button.html
	    	       	    
	    	    transaction.replace(R.id.fragment_contact_inserter, newFragment);
	    	    
	    	    //transaction.replace(R.id.fragment_contact_inserter, newFragment);
	    	    transaction.addToBackStack(null);
	    	    
	    	    // Commit the transaction
	    	    transaction.commit();
			}
		});*/
		
		return rootView;
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
