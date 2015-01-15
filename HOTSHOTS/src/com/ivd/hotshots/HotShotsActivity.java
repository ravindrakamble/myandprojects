package com.ivd.hotshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ivd.rotateImage.ExifUtil;
import com.ivd.util.Utility;

public class HotShotsActivity extends Activity {

	RelativeLayout footer;
	ImageView hotshots;
	private static final int SPLASH_DURATION = 1500;
	private boolean isSpeakButtonLongPressed = false;

	public void StartHandler() {
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(HotShotsActivity.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				finish();
			}
		}, SPLASH_DURATION);
	}

	public void ShowHome() {
		Intent intent = new Intent(HotShotsActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_hot_shots);


		hotshots = (ImageView) findViewById(R.id.hotshots);
		footer = (RelativeLayout) findViewById(R.id.footer_parent);
		footer.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub

				hotshots.setVisibility(View.VISIBLE);
//				loadImage();
				new LoadImage().execute("");
				isSpeakButtonLongPressed = true;
				// StartHandler();
				return true;
			}
		});

		footer.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				// We're only interested in when the button is released.
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// We're only interested in anything if our speak button is
					// currently pressed.
					if (isSpeakButtonLongPressed) {
						// Do something when the button is released.
						isSpeakButtonLongPressed = false;
						ShowHome();
					}
				}
				return false;
			}
		});
		WelComeAlert();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hot_shots, menu);
		return true;
	}

	public void WelComeAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				HotShotsActivity.this);

		builder.setMessage("To see the image long press the HOTSHOTS");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				dialog.dismiss();
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public void loadImage() {

		AppDelegate delegate = (AppDelegate) getApplicationContext();
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory() + "/Android/data/"
						+ getApplicationContext().getPackageName() + "/Files");

		File file = new File(mediaStorageDir, delegate.hotshots.getImagename());
		Log.e("ERROR", delegate.hotshots.getImagename());

		 FileInputStream streamIn;
			try {
				streamIn = new FileInputStream(file);
				  Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
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
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}





		//FileInputStream streamIn;
//		BitmapFactory.Options options = new BitmapFactory.Options();
//
//		//streamIn = new FileInputStream(file);
//		options.inJustDecodeBounds = true;
//		options.inSampleSize = calculateInSampleSize(options);
//
//		// Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
//		options.inJustDecodeBounds = false;
//		//Bitmap bitmap = BitmapFactory.decodeStream(streamIn, null, options);
//		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		//hotshots.setImageBitmap(bmp);

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
	public  int calculateInSampleSize(
            BitmapFactory.Options options) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
    int reqWidth = metrics.widthPixels;
    int reqHeight = metrics.heightPixels;
    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// replaces the default 'Back' button action
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
				|| keyCode == KeyEvent.KEYCODE_VOLUME_UP
				|| keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
				|| keyCode == KeyEvent.KEYCODE_POWER) {

			ShowHome();
			Utility.ShowNotification(this, "IMAGE HAS BEEN DELETED");

		} else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
				&& keyCode == KeyEvent.KEYCODE_POWER) {

			ShowHome();
			Utility.ShowNotification(this, "IMAGE HAS BEEN DELETED");
		}
		return true;
	}

	class LoadImage extends AsyncTask<String, Long, Void>{

		Bitmap bmp;
		@Override
		protected Void doInBackground(String... params) {

			AppDelegate delegate = (AppDelegate) getApplicationContext();
			File mediaStorageDir = new File(
					Environment.getExternalStorageDirectory() + "/Android/data/"
							+ getApplicationContext().getPackageName() + "/Files");

			File file = new File(mediaStorageDir, delegate.hotshots.getImagename());
			Log.e("ERROR", delegate.hotshots.getImagename());

			 FileInputStream streamIn;
				try {
					streamIn = new FileInputStream(file);
					  Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
					  //hotshots.setImageBitmap(bitmap);


						int width = bitmap.getWidth();
						int height = bitmap.getHeight();


						if (height > width) {
							setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
							this.bmp = bitmap;
						} else {

							Matrix matrix = new Matrix();
							matrix.postRotate(delegate.angle);
							this.bmp = Bitmap.createBitmap(bitmap, 0, 0,
									bitmap.getWidth(), bitmap.getHeight(), matrix, true);

						}
						streamIn.close();
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

/*
			// TODO Auto-generated method stub
			AppDelegate delegate = (AppDelegate) getApplicationContext();
			File mediaStorageDir = new File(
					Environment.getExternalStorageDirectory() + "/Android/data/"
							+ getApplicationContext().getPackageName() + "/Files");

			File file = new File(mediaStorageDir, delegate.hotshots.getImagename());
			Log.e("ERROR", delegate.hotshots.getImagename());

			String filePath = file.getPath();
		FileInputStream streamIn;
			BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			streamIn = new FileInputStream(file);
			Bitmap bitmap = BitmapFactory.decodeStream(streamIn, null, options);

				ExifInterface exif;
				int angle = 0;
				try {
					exif = new ExifInterface(mediaStorageDir + "/" + delegate.hotshots.getImagename());
					int exiforientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
					if (exiforientation == 3) {
						angle = 180;
					} else if (exiforientation == 6) {
						angle = 90;
					} else if (exiforientation == 8) {
						angle = 270;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}     //Since API Level 5

					this.bmp = ExifUtil.rotateBitmap(filePath, bitmap, angle);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}


			return null;*/
				return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			hotshots.setImageBitmap(this.bmp);
		}

	}

	public Bitmap getRotateBMP(String filepath, Bitmap bitmap){
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

		Utility.ShowNotification(this, " Image detected "+orientation);
		return  rotateBitmap(bitmap, orientation);
	}

	public  Bitmap rotateBitmap(Bitmap bitmap, int orientation) {


	        Matrix matrix = new Matrix();
	        switch (orientation) {
	            case ExifInterface.ORIENTATION_NORMAL:
	            	Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_NORMAL);
	                return bitmap;
	            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
	            	Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_FLIP_HORIZONTAL);
	                matrix.setScale(-1, 1);
	                break;
	            case ExifInterface.ORIENTATION_ROTATE_180:
	            	Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_ROTATE_180);
	                matrix.setRotate(180);
	                break;
	            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
	            	Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_FLIP_VERTICAL);
	                matrix.setRotate(180);
	                matrix.postScale(-1, 1);
	                break;
	            case ExifInterface.ORIENTATION_TRANSPOSE:
	            	Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_TRANSPOSE);
	                matrix.setRotate(90);
	                matrix.postScale(-1, 1);
	                break;
	           case ExifInterface.ORIENTATION_ROTATE_90:
	        	   Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_ROTATE_90);
	               matrix.setRotate(90);
	               break;
	           case ExifInterface.ORIENTATION_TRANSVERSE:
	        	   Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_TRANSVERSE);
	               matrix.setRotate(-90);
	               matrix.postScale(-1, 1);
	               break;
	           case ExifInterface.ORIENTATION_ROTATE_270:
	        	   Utility.ShowNotification(this, ""+ExifInterface.ORIENTATION_ROTATE_270);
	               matrix.setRotate(-90);
	               break;
	           default:
	               return bitmap;
	        }
	        try {
	            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	            bitmap.recycle();
	            return bmRotated;
	        }
	        catch (OutOfMemoryError e) {
	            e.printStackTrace();
	            return null;
	        }



	}

}
