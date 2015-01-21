package com.ivd.hotshots;


import com.ivd.util.AppConstants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {


	private static final int SPLASH_DURATION = 1500;
	private SharedPreferences sharedpreferences;
	String pns;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_splash);
		/*GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		pns = GCMRegistrar.getRegistrationId(this);
		if (pns.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
			pns= GCMRegistrar.getRegistrationId(this);
			Log.e("PNS TOKEN",pns);
		}*/
		StartHandler();
	}
	public void StartHandler() {
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				/*AppDelegate delegate = (AppDelegate)getApplicationContext();
				delegate.getFromMemory();

				if(delegate.isRegistered == 1){
					StartMainActivity();
				}else{
					StartRegistryActivity();
				}
				*/

				/*sharedpreferences = getSharedPreferences(AppConstants.IVD_PREF, Context.MODE_PRIVATE);
				String user_id = sharedpreferences.getString(AppConstants.KEY_USER_ID, "");

				if(user_id != null && user_id.trim().length() > 0){
					StartMainActivity();
				}else{
					StartRegistryActivity();
				}*/
				StartRegistryActivity();
				SplashActivity.this.finish();
			}
		}, SPLASH_DURATION);
	}

	public void StartMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void StartRegistryActivity(){
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
	}

}
