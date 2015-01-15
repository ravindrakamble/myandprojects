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
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BlurImageActivity extends Activity {

	RelativeLayout footer;
	ImageView hotshots;
	private static final int SPLASH_DURATION = 1500;
	
	 
	
	public void StartHandler() {
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(BlurImageActivity.this,
						ReceiverQuestionActivity.class);
				
				startActivity(intent);
					finish();
			}
		}, SPLASH_DURATION);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_blur_image);
		hotshots = (ImageView)findViewById(R.id.hotshots);
		footer = (RelativeLayout) findViewById(R.id.footer_parent);
		AlphaAnimation alpha = new AlphaAnimation(0.1F, 0.1F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends
		hotshots.startAnimation(alpha);
		loadImage();
		StartHandler();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hot_shots, menu);
		return true;
	}
	private Bitmap loadBitmapSafely(String filePath, int sampleSize){
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options optionsActual = new BitmapFactory.Options();
			optionsActual.inSampleSize = sampleSize;


			bitmap = BitmapFactory
					.decodeFile(filePath, optionsActual);
		} catch (OutOfMemoryError e) {
			loadBitmapSafely(filePath, sampleSize + 1);
		}

		return bitmap;
	}


	public void loadImage(){
		
			AppDelegate delegate = (AppDelegate)getApplicationContext();
			 File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
			            + "/Android/data/"
			            + getApplicationContext().getPackageName()
			            + "/Files"); 
		  File file = new File(mediaStorageDir, delegate.hotshots.getBlurimage()); //or any other format supported

			  FileInputStream streamIn;
			try {
				streamIn = new FileInputStream(file);
				  Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
				//  Drawable d = new BitmapDrawable(bitmap);
					//profile_image.setBackgroundDrawable(d);
				

				hotshots.setImageBitmap(bitmap);


				int width = bitmap.getWidth();
				int height = bitmap.getHeight();

				
				if (height > width) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					hotshots.setImageBitmap(bitmap);
				} else {

					Matrix matrix = new Matrix();
					matrix.postRotate(delegate.angle);
					Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight(), matrix, true);

					hotshots.setImageBitmap(rotatedBitmap);
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
