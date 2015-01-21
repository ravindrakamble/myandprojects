package com.ivd.hotshots;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
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
import com.ivd.models.Contacts;
import com.ivd.util.AppConstants;
import com.ivd.util.Utility;

public class AddContactsActivity extends RootActivity implements UiUpdator {

	private ListView contact_list;
	ContactAdapter adapter;
	ProgressDialog progressDialog = null;
	private String resultString = "";
	private String mobileString="";
	private SharedPreferences sharedpreferences;
	List<Contacts> contactList = new ArrayList<Contacts>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_add_contacts);
		SetHeader();
		contact_list = (ListView) findViewById(R.id.contact_list);

		adapter = new ContactAdapter(this, contactList);
		contact_list.setAdapter(adapter);
		contact_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


				Contacts contact = contactList.get(position);
				if (contact.getStatus() == 0) {
					contact.setStatus(1);
				} else {
					contact.setStatus(0);
				}
				adapter.notifyDataSetChanged();
			}

		});
		RetriveContacts();
	}

	public void RetriveContacts(){
		Cursor cursor = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, Phone.DISPLAY_NAME + " ASC");
		while (cursor.moveToNext()) {
			Contacts contact = new Contacts();
			String name =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			contact.setName(name);
			contact.setPhone(phoneNumber);
			contact.setStatus(0);
			contactList.add(contact);
			}
		adapter.notifyDataSetChanged();
	}

	public void SetHeader(){
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("ADD CONTACTS");
	}
	public void SYNC(View v){

		mobileString = "";
		AppDelegate delegate = (AppDelegate) getApplicationContext();
		for(int i=0;i<contactList.size();i++){
			Contacts contact = contactList.get(i);
			if(contact.getStatus() == 1){
				delegate.contactList.add(contact);
				if(contact.getPhone() != null && contact.getPhone().trim().length() > 0){
					if(mobileString.length() > 0){
						mobileString = mobileString + "," + contact.getPhone();
					}else{
						mobileString = contact.getPhone();;
					}
				}
			}

		}

		sendSyncContactsRequest(mobileString);
	}


	private void sendSyncContactsRequest(String mobileNumbers){

		ContactData data = new ContactData();

		sharedpreferences = getSharedPreferences(AppConstants.IVD_PREF, Context.MODE_PRIVATE);
		data.setUser_id(sharedpreferences.getString(AppConstants.KEY_USER_ID, ""));
		data.setMobile(mobileNumbers);

		Type type = new TypeToken<ContactDataResp>(){}.getType();
		sendRequest(AppConstants.REQUEST_CONTACT_SYNC, data, type);

		showProgressDialog();
	}

	class ContactAdapter extends ArrayAdapter<Contacts> {

		private final Context context;
		private final List<Contacts> values;

		public ContactAdapter(Context context, List<Contacts> values) {
			super(context, R.layout.contact_layout, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.contact_layout, parent,
					false);

			TextView name = (TextView) rowView.findViewById(R.id.name);
			TextView number = (TextView) rowView.findViewById(R.id.number);
			//CheckBox chckBox = (CheckBox) rowView.findViewById(R.id.trashBtn);

			Contacts contact = values.get(position);

			name.setText(contact.getName());
			number.setText(contact.getPhone());
			ImageView chckBox = (ImageView) rowView.findViewById(R.id.trashBtn);



			name.setText(contact.getName());

			if (contact.getStatus() == 0) {
				chckBox.setImageResource(R.drawable.whitebutton);

			} else {
				chckBox.setImageResource(R.drawable.yellow);
			}

			return rowView;


			/*if (contact.getStatus() == 0) {
				chckBox.setChecked(false);

			} else {
				chckBox.setChecked(true);
			}
			final int pos = position;
			chckBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// is chkIos checked?

					Contacts cat = contactList.get(pos);
					if (((CheckBox) v).isChecked()) {

						cat.setStatus(1);
					} else {
						cat.setStatus(0);
					}
					adapter.notifyDataSetChanged();
				}
			});

			return rowView;
			*/
		}
	}


	class ContactSync extends AsyncTask<String, Long, Void> {

		DefaultHttpClient mHttpClient;
		String _data;

		public ContactSync() {
			serverCommunication();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(AddContactsActivity.this, "",
					" Please wait..", true);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			// showAlertDiaplg(_data);
			resultString = _data;
			Log.e("ERROR", "" + resultString);

			progressDialog.dismiss();
			try {
				JSONObject obj = new JSONObject(resultString);

				Utility.ShowNotification(AddContactsActivity.this, "Total sync contacts " +obj.getString("matched_contact"));
//				this.finish();



			} catch (Exception e) {
				Utility.ShowNotification(AddContactsActivity.this,
						"Problem Occured");
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String baseUrl = Constant.URL;
			AppDelegate delegate = (AppDelegate) getApplicationContext();

			String action = "contactsync";

			try {
				HttpPost _httpPost = new HttpPost(baseUrl);
				MultipartEntity multipartEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);

				multipartEntity.addPart("action", new StringBody(action));
				multipartEntity.addPart("user_id", new StringBody(delegate.uid));
				multipartEntity.addPart("mobile",
						new StringBody(mobileString));




				_httpPost.setEntity(multipartEntity);
				mHttpClient.execute(_httpPost, new WebserviceResponseHandler());
				Log.e("post", _httpPost.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("CLIENTPROTOCOLEXCEPTION", "TRUE");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("IOEXCEPTION", "TRUE");
			}

			return null;
		}

		public void serverCommunication() {
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			mHttpClient = new DefaultHttpClient(params);
		}

		@SuppressWarnings("rawtypes")
		private class WebserviceResponseHandler implements ResponseHandler {

			@Override
			public Object handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				// TODO Auto-generated method stub
				HttpEntity r_entity = response.getEntity();
				String responseString = EntityUtils.toString(r_entity);

				_data = responseString;
				return null;
			}

		}

	}

	@Override
	public void updateUI(int requestCode, StatusCode statusCode,
			int responseCode, Type data) {
		if(statusCode == StatusCode.SUCCESS){
			if(requestCode == AppConstants.REQUEST_CONTACT_SYNC){
				if(data instanceof ContactDataResp){
					ContactDataResp contactResponse = (ContactDataResp)data;
					Utility.ShowNotification(this, "Total sync contacts " + contactResponse.getMatched_contact()  );
					this.finish();
				}
			}
		}else{
			Utility.ShowNotification(this, getString(R.string.error_failed_to_register));
		}
		hideProgressDialog();
	}

}
