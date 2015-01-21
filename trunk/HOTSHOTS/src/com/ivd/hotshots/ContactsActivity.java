package com.ivd.hotshots;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ivd.http.UiUpdator;
import com.ivd.http.RestResponse.StatusCode;
import com.ivd.http.models.ContactData;
import com.ivd.http.models.ContactDataResp;
import com.ivd.http.models.ContactLisetResp;
import com.ivd.http.models.RegResponse;
import com.ivd.models.Contacts;
import com.ivd.models.Hotshots;
import com.ivd.models.Registration;
import com.ivd.util.AppConstants;
import com.ivd.util.Utility;

public class ContactsActivity extends RootActivity implements UiUpdator {

	private ListView contact_list;
	ContactAdapter adapter;
	private SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_contacts);
		SetHeader();
		AppDelegate delegate = (AppDelegate) getApplicationContext();

		contact_list = (ListView) findViewById(R.id.contact_list);

		adapter = new ContactAdapter(this, delegate.contactList);
		contact_list.setAdapter(adapter);
		contact_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				AppDelegate delegate = (AppDelegate) getApplicationContext();
				Contacts contact = delegate.contactList.get(position);
				if (contact.getStatus() == 0) {
					contact.setStatus(1);
				} else {
					contact.setStatus(0);
				}
				adapter.notifyDataSetChanged();
			}

		});
		//SetContacts();
		sendGetContacts();
	}


	private void sendGetContacts(){
		ContactData data = new ContactData();

		sharedpreferences = getSharedPreferences(AppConstants.IVD_PREF, Context.MODE_PRIVATE);
		data.setUser_id(sharedpreferences.getString(AppConstants.KEY_USER_ID, ""));

		Type type = new TypeToken<ContactLisetResp[]>(){}.getType();
		sendRequest(AppConstants.REQUEST_CONTACT_LIST, data, type);

		showProgressDialog();
	}
	public void SetContacts() {
		AppDelegate delegate = (AppDelegate) getApplicationContext();
		for (int i = 0; i < delegate.contactList.size(); i++) {
			Contacts contact = delegate.contactList.get(i);
			contact.setStatus(0);
		}
		adapter.notifyDataSetChanged();
	}

	public void SetHeader() {
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("CONTACTS");
	}

	public void Send(View v) {

		AppDelegate delegate = (AppDelegate) getApplicationContext();
		for (int i = 0; i < delegate.contactList.size(); i++) {
			Contacts contact = delegate.contactList.get(i);
			if (contact.getStatus() == 1) {
				Hotshots hotshot = new Hotshots();
				hotshot.setAnswer(delegate.answer);
				hotshot.setQuestion(delegate.question);
				hotshot.setName(contact.getName());
				hotshot.setImagename(delegate.picname);
				hotshot.setBlurimage(delegate.blurImageName);
				hotshot.setAngle(delegate.angle);
				delegate.hotshotList.add(hotshot);
				sendHotshot(hotshot);
			}

		}

	}

	private void sendHotshot(Hotshots hotshot){
		Type type = new TypeToken<RegResponse>(){}.getType();
		sendRequest(AppConstants.REQUEST_UPLOAD_HOTSHOT, hotshot, type);

		showProgressDialog();
	}
	class ContactAdapter extends ArrayAdapter<Contacts> {

		private final Context context;
		private final List<Contacts> values;

		public ContactAdapter(Context context, List<Contacts> values) {
			super(context, R.layout.sender_contact_layout, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.sender_contact_layout,
					parent, false);

			TextView name = (TextView) rowView.findViewById(R.id.name);
			ImageView chckBox = (ImageView) rowView.findViewById(R.id.trashBtn);

			Contacts contact = values.get(position);

			name.setText(contact.getName());

			if (contact.getStatus() == 0) {
				chckBox.setImageResource(R.drawable.whitebutton);

			} else {
				chckBox.setImageResource(R.drawable.yellow);
			}

			return rowView;
		}
	}

	@Override
	public void updateUI(int requestCode, StatusCode statusCode,
			int responseCode, Type data) {
		if(statusCode == StatusCode.SUCCESS){
			if(requestCode == AppConstants.REQUEST_UPLOAD_HOTSHOT){
				finish();
			}else if(requestCode == AppConstants.REQUEST_CONTACT_LIST){
				//sendHotshot(null);
			}
		}else{
			Utility.ShowNotification(this, getString(R.string.error_failed_to_register));
		}
		hideProgressDialog();
	}

}
