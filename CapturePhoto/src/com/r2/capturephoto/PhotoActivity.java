package com.r2.capturephoto;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class PhotoActivity extends Activity {
	int orientation;
	int angle;
	ImageView imgPhoto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		orientation = intent.getIntExtra("orientation", 1);
		angle = intent.getIntExtra("angle", 0);
		if (orientation == 1) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		setContentView(R.layout.photo);

		imgPhoto = (ImageView)findViewById(R.id.imageSelected);
		imgPhoto.setImageBitmap(MainActivity.bitmap);

	}

}
