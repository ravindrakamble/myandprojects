package com.ivd.hotshots;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class TryAgainActivity extends Activity {

	TextView guess;
	
	ImageView hotshots;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_try_again);
		
		AppDelegate delegate =(AppDelegate) getApplicationContext();
		guess = (TextView) findViewById(R.id.guessText);
		
		guess.setText(delegate.count+" GUESS REMAINING");
		hotshots = (ImageView)findViewById(R.id.hotshot);
		
		
		
	}

	
	public void Save(View v) {
		finish();
	}

	
}
