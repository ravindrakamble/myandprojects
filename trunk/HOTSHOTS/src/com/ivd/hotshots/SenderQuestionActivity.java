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

public class SenderQuestionActivity extends Activity {

	EditText questionView;
	
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
		setContentView(R.layout.activity_sender_question);
		
		questionView = (EditText) findViewById(R.id.questionView);
		
		Utility.setupUIKeyboardListner(findViewById(R.id.parent), this);
		Utility.HideKeyboardOnStart(this);
		//SetValue();
		
	}

	public void SetValue(){
		questionView.setText("This is a question");
		
		
	}
	private Boolean verify() {

		Boolean isVerify = true;

		String question = questionView.getText().toString();
		

		if (question.length() <= 0) {
			Utility.ShowNotification(this, "Please provide question");
			return false;
		}
	
		return isVerify;
	}
	public void Confirm(View v) {
		Boolean isVerify = verify();
		if (isVerify == true) {
			
			AppDelegate delegate = (AppDelegate) getApplicationContext();
			delegate.question = questionView.getText().toString();
			ShowAnswer();
			finish();
			
			
			
		}
	}

	public void ShowAnswer(){
		Intent intent = new Intent(this,SenderAnswerActivity.class);
		startActivity(intent);
	}

}
