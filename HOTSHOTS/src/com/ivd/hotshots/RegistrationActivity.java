package com.ivd.hotshots;


import java.io.IOException;

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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ivd.util.Utility;

public class RegistrationActivity extends Activity {

	EditText nameText;
	EditText emailText;
	EditText mobileText;
	EditText referelText;
	ProgressDialog progressDialog = null;
	private String resultString = "";
	RelativeLayout  footer_parent;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		Utility.HideSoftKeyboard(this);

		return super.onTouchEvent(event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_registration);
		
		nameText = (EditText) findViewById(R.id.nameText);
		emailText = (EditText) findViewById(R.id.emailText);
		mobileText =(EditText) findViewById(R.id.mobileText);
		referelText = (EditText) findViewById(R.id.referelText);
//		footer_parent =(RelativeLayout) findViewById(R.id.footer_parent);
//		footer_parent.setEnabled(false);
//		footer_parent.setAlpha(10);
		Utility.setupUIKeyboardListner(findViewById(R.id.parent), this);
		Utility.HideKeyboardOnStart(this);
		SetValue();
		
	}

	public void SetValue(){
		nameText.setText("Shane Bell");
		emailText.setText("sbell1@gmail.com");
		mobileText.setText("+61641547160");
		referelText.setText("1");
		
	}
	private Boolean verify() {

		Boolean isVerify = true;

		String name = nameText.getText().toString();
		String email = emailText.getText().toString();
		
		String mobile = mobileText.getText().toString();
		

		if (mobile.length() <= 0) {
			Utility.ShowNotification(this, "Please provide mobile number");
			return false;
		}
		if (email.length() <= 0) {
			Utility.ShowNotification(this, "Please enter a email.");
			return false;
		}
		if (name.length() <= 0) {
			Utility.ShowNotification(this, "Please provide name");
			return false;
		}
		
		return isVerify;
	}
	public void Save(View v) {
		Boolean isVerify = verify();
		if (isVerify == true) {
			
			AppDelegate delegate = (AppDelegate) getApplicationContext();
			delegate.name = nameText.getText().toString();
			delegate.email = emailText.getText().toString();
			delegate.mobile = mobileText.getText().toString();;
			delegate.referredID = referelText.getText().toString();
			delegate.uid = "1";
			delegate.isRegistered =1;
			
			delegate.setInMemory();
			ShowMainActivity();
			finish();
			
		//	new AppRegistration().execute("");
			
		}
	}

	public void ShowMainActivity(){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	
	class AppRegistration extends AsyncTask<String, Long, Void> {

		DefaultHttpClient mHttpClient;
		String _data;

		public AppRegistration() {
			serverCommunication();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(RegistrationActivity.this, "",
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

				AppDelegate delegate = (AppDelegate) getApplicationContext();
				delegate.name = nameText.getText().toString();
				delegate.email = emailText.getText().toString();
				delegate.mobile = mobileText.getText().toString();;
				delegate.referredID = referelText.getText().toString();
				
				delegate.isRegistered =1;
				
				delegate.uid = obj.getString("user_id");
				
				
				
				delegate.setInMemory();
				ShowMainActivity();
				finish();
				
				Utility.ShowNotification(RegistrationActivity.this,
						"Registered Successfully");
				
			

			} catch (Exception e) {
				Utility.ShowNotification(RegistrationActivity.this,
						"Problem Occured");
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String baseUrl = Constant.URL;
		

			String action = "register";

			try {
				HttpPost _httpPost = new HttpPost(baseUrl);
				MultipartEntity multipartEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);

				multipartEntity.addPart("action", new StringBody(action));
				multipartEntity.addPart("device_type",
						new StringBody("android"));
//				multipartEntity.addPart("device_id", new StringBody(
//						GCMRegistrar.getRegistrationId(getApplicationContext())));
				multipartEntity.addPart("device_id", new StringBody(
						"1234567890"));

				multipartEntity.addPart("name", new StringBody(nameText
						.getText().toString()));

				multipartEntity.addPart("email", new StringBody(emailText
						.getText().toString()));
				multipartEntity.addPart("mobile", new StringBody(mobileText
						.getText().toString()));
				multipartEntity.addPart("ref_id", new StringBody(referelText
						.getText().toString()));

				

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

}
