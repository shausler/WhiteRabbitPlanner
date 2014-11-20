package com.whiterabbitplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class ContactInserterFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_contact_inserter, container, false);
		return rootView;
	}
	
	/*public void showSoftKeyboard(View view) {
	    if (view.requestFocus()) {
	        InputMethodManager imm = (InputMethodManager)
	                this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	    }
	}
	*/
	
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
