package com.ivd.hotshots;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ContactBuildActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_contact_build);
		SetHeader();
	}

	public void SetHeader() {
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("CONTACT BUILD");
	}

	public void Back(View v) {

		this.finish();
	}

	public void InviteFriends(View v) {

		PopupMenu popup = new PopupMenu(ContactBuildActivity.this, v);
		// Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.contact_build, popup.getMenu());

		// registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(android.view.MenuItem item) {

				switch (item.getItemId()) {
				case R.id.one:
					Intent email = new Intent(Intent.ACTION_SEND);

					email.putExtra(
							Intent.EXTRA_SUBJECT,
							"I am using this amazing App to send HotShots. Why not download it and try for yourself");
					email.putExtra(Intent.EXTRA_TEXT, "");
					email.setType("message/rfc822");
					startActivity(Intent.createChooser(email,
							"Choose an Email client :"));
					break;

				case R.id.two:
					Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					sendIntent
							.putExtra(
									"sms_body",
									"I am using this amazing App to send HotShots. Why not download it and try for yourself");
					sendIntent.setType("vnd.android-dir/mms-sms");
					startActivity(Intent.createChooser(sendIntent,
							"Choose an Email client :"));
					break;

				case R.id.three:
					Intent sharingIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					sharingIntent.putExtra(
							android.content.Intent.EXTRA_SUBJECT, "HOTSHOTS");
					sharingIntent
							.putExtra(
									android.content.Intent.EXTRA_TEXT,
									"I am using this amazing App to send HotShots. Why not download it and try for yourself");

					startActivity(Intent.createChooser(sharingIntent,
							"INVITE FRIENDS THROUGH SOCIAL MEDIA"));
					break;

				default:
					break;
				}
				return true;
			}
		});

		popup.show();// showing popup menu
	}

	public void SYNCCONTACTS(View v) {
		Intent intent = new Intent(this, AddContactsActivity.class);
		startActivity(intent);
	}

}
