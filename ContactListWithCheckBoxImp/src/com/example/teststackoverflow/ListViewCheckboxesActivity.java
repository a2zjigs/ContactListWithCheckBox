package com.example.teststackoverflow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewCheckboxesActivity extends Activity {

	MyCustomAdapter dataAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactlist);

		// Generate list View from ArrayList
		displayListView();

		checkButtonClick();

	}

	private void displayListView() {
		ArrayList<Contacts> countryList = new ArrayList<Contacts>();

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				//Name.add(name);
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						//MobileNUmber.add(phoneNumber);
						Contacts country = new Contacts(name, phoneNumber, false);
						countryList.add(country);
					}
					pCur.close();
				}
			}
		}

		// Array list of countries
		
//		Country country = new Country("AFG", "Afghanistan", false);
//		countryList.add(country);
//		country = new Country("ALB", "Albania", true);
//		countryList.add(country);
//		country = new Country("DZA", "Algeria", false);
//		countryList.add(country);
//		country = new Country("ASM", "American Samoa", true);
//		countryList.add(country);
//		country = new Country("AND", "Andorra", true);
//		countryList.add(country);
//		country = new Country("AGO", "Angola", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
//		country = new Country("AIA", "Anguilla", false);
//		countryList.add(country);
		

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.contact_item,countryList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// When clicked, show a toast with the TextView text
				Contacts country = (Contacts) parent.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),"Clicked on Row: " + country.getName(),Toast.LENGTH_LONG).show();
			}
		});

	}

	private class MyCustomAdapter extends ArrayAdapter<Contacts> {

		private ArrayList<Contacts> contactList;

		public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Contacts> contactlist) {
			super(context, textViewResourceId, contactlist);
			this.contactList = new ArrayList<Contacts>();
			this.contactList.addAll(contactlist);
		}

		private class ViewHolder {
			TextView Number;
			CheckBox Name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.contact_item, null);

				holder = new ViewHolder();
				holder.Number = (TextView) convertView.findViewById(R.id.code);
				holder.Name = (CheckBox) convertView.findViewById(R.id.checkBox1);
				convertView.setTag(holder);

				holder.Name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Contacts country = (Contacts) cb.getTag();
						Toast.makeText(getApplicationContext(),"Clicked on Checkbox: " + cb.getText() + " is "+ cb.isChecked(), Toast.LENGTH_LONG).show();
						country.setSelected(cb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Contacts country = contactList.get(position);
			holder.Number.setText(country.getCode());
			holder.Name.setText(country.getName());
			holder.Name.setChecked(country.isSelected());
			holder.Name.setTag(country);

			return convertView;

		}

	}

	private void checkButtonClick() {

		Button myButton = (Button) findViewById(R.id.findSelected);
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				StringBuffer responseText = new StringBuffer();
				responseText.append("The following were selected...\n");

				ArrayList<Contacts> countryList = dataAdapter.contactList;
				for (int i = 0; i < countryList.size(); i++) {
					Contacts country = countryList.get(i);
					if (country.isSelected()) {
						responseText.append("\n" + country.getName());
					}
				}

				Toast.makeText(getApplicationContext(), responseText,
						Toast.LENGTH_LONG).show();

			}
		});

	}

}
