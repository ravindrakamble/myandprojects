package com.ivd.hotshots;

import java.util.ArrayList;
import java.util.List;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ivd.models.Contacts;
import com.ivd.models.Hotshots;

@ReportsCrashes(formKey = "", // will not be used
formUri = "http://demo.ivdisplays.net/barapp/crasher/crasher.php"

)
public class AppDelegate extends Application {

	public int isRegistered;
	public String name;

	public String email;
	public String uid;
	public String mobile;
	public String referredID;
	public List<Contacts> contactList;
	public List<Hotshots> hotshotList;
	public String question;
	public String answer;
	public Hotshots hotshots;
	public int count;
	public String picname;
	public String blurImageName;
	public int angle=0;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		isRegistered = 0;
		contactList = new ArrayList<Contacts>();
		hotshotList = new ArrayList<Hotshots>();
		ACRA.init(this);
		super.onCreate();
	}

	public void setInMemory() {

		SharedPreferences shared = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		SharedPreferences.Editor editor = shared.edit();
		editor.putString("name", this.name);
		editor.putString("email", this.email);
		editor.putString("uid", this.uid);

		editor.putString("mobile", this.mobile);
		editor.putString("referredID", this.referredID);

		editor.putInt("isRegistered", 1);

		editor.commit();
	}

	public void getFromMemory() {

		SharedPreferences shared = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		this.name = shared.getString("name", "");
		this.email = shared.getString("email", "");
		this.uid = shared.getString("uid", "");

		this.mobile = shared.getString("mobile", "");
		this.referredID = shared.getString("referredID", "");

		this.isRegistered = shared.getInt("isRegistered", 0);
		Log.e("user ID", this.uid);
	}

	public void flusFromMemory() {

		SharedPreferences shared = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		SharedPreferences.Editor editor = shared.edit();
		editor.putString("name", "");
		editor.putString("email", "");
		editor.putString("uid", "");

		editor.putString("mobile", "");
		editor.putString("referredID", "");

		editor.putInt("isRegistered", 0);
		this.isRegistered = 0;

		editor.commit();
	}

}
