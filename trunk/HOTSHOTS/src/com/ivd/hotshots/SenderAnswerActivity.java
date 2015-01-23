package com.ivd.hotshots;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.ivd.util.Utility;

public class SenderAnswerActivity extends Activity {

EditText answerView;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Utility.HideSoftKeyboard(this);

		return super.onTouchEvent(event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_sender_answer);

		answerView = (EditText) findViewById(R.id.answerView);

		Utility.setupUIKeyboardListner(findViewById(R.id.parent), this);
		Utility.HideKeyboardOnStart(this);
		//SetValue();

	}

	public void SetValue(){
		answerView.setText("Answer");


	}
	private Boolean verify() {

		Boolean isVerify = true;

		String answer = answerView.getText().toString();


		if (answer.length() <= 0) {
			Utility.ShowNotification(this, "Please provide answer");
			return false;
		}

		return isVerify;
	}
	public void Contacts(View v) {
		Boolean isVerify = verify();
		if (isVerify == true) {

			AppDelegate delegate = (AppDelegate) getApplicationContext();
			delegate.answer = answerView.getText().toString();
			ShowAnswer();
			finish();



		}
	}

	public void ShowAnswer(){
		Intent intent = new Intent(this,ContactsActivity.class);
		startActivity(intent);
	}
}
