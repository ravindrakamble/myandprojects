package com.ivd.hotshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivd.util.Utility;

public class ReceiverQuestionActivity extends Activity {

EditText questionView;

ImageView hotshot;
	
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
		setContentView(R.layout.activity_receiver_question);
		
		questionView = (EditText) findViewById(R.id.questionView);
		hotshot = (ImageView) findViewById(R.id.hotshot);
		
		
		loadImage();
		AlphaAnimation alpha = new AlphaAnimation(0.1F, 0.1F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends
		hotshot.startAnimation(alpha);
		
		Utility.setupUIKeyboardListner(findViewById(R.id.parent), this);
		Utility.HideKeyboardOnStart(this);
		SetValue();
		
	}

	public void SetValue(){
		
		
		AppDelegate delegate  = (AppDelegate) getApplicationContext();
		questionView.setText(delegate.hotshots.getQuestion());
		
		
	}
	
	public void Confirm(View v) {
		
		ShowAnswer();
	}

	public void ShowAnswer(){
		Intent intent = new Intent(this,ReciverAnswerActivity.class);
		startActivity(intent);
	}
	
	public void loadImage(){
		
		AppDelegate delegate = (AppDelegate)getApplicationContext();
		 File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
		            + "/Android/data/"
		            + getApplicationContext().getPackageName()
		            + "/Files"); 

		  File file = new File(mediaStorageDir, delegate.hotshots.getImagename()); //or any other format supported

		  FileInputStream streamIn;
		try {
			streamIn = new FileInputStream(file);
			  Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
			 
				
			  int width = bitmap.getWidth();
				int height = bitmap.getHeight();

				
				if (height > width) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					hotshot.setImageBitmap(bitmap);
				} else {

					Matrix matrix = new Matrix();
					matrix.postRotate(delegate.angle);
					Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);

					hotshot.setImageBitmap(rotatedBitmap);
				}
			 
			  streamIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
}
