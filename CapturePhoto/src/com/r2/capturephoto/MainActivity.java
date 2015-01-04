package com.r2.capturephoto;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	final static int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;

	private Button btnGallery;
	private Button btnCamera;
	private final String tag = "CaturePhoto";
	private int angle = 0;
	private int orientation;

	public static Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);

		btnGallery = (Button)findViewById(R.id.btn_gallery);
		btnCamera = (Button)findViewById(R.id.btn_camera);

		btnGallery.setOnClickListener(this);
		btnCamera.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()){
		case R.id.btn_gallery:
			angle = 0;
			intent = new Intent(Intent.ACTION_PICK,
				      Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, GALLERY_IMAGE_ACTIVITY_REQUEST_CODE);
			break;

		case R.id.btn_camera:
			angle = 0;
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
			break;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(tag, "" + requestCode);
		if ( requestCode == GALLERY_IMAGE_ACTIVITY_REQUEST_CODE) {

			if ( resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				createBitmap(selectedImageUri);
				showPhoto(selectedImageUri);
			}
		}else if ( requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

			if ( resultCode == RESULT_OK) {
				Log.i(tag, "" + data.getData());
				Uri selectedImageUri = data.getData();
				createBitmap(selectedImageUri);
				showPhoto(selectedImageUri);
			}
		}
	}

	private void showPhoto(Uri uri){
		Intent intent = new Intent(this, PhotoActivity.class);
		intent.putExtra("orientation", orientation);
		intent.putExtra("angle", angle);
		startActivity(intent);
	}
	private void createBitmap(Uri uri){
		Log.i(tag, "Angle : " + angle +  " URI :" + uri);
		//Find the angle for the image to rotate
		if(angle == 0){
			String[] columns = {MediaStore.Images.Media.ORIENTATION};
			Cursor cur =  getContentResolver().query(uri, columns, null, null, null);

			if (cur != null && cur.moveToFirst()) {
				angle = cur.getInt(cur.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
			}
		}
		orientation = 1;
		switch(angle){
		case 90:
			orientation = 1;
			break;

		case 180:
			orientation = 2;
			break;

		case 270:
			orientation = 1;
			break;
		}
		BitmapFactory.Options options=new BitmapFactory.Options();
		//To overcome the out of memory issue, original image is scaled by 4.
		options.inSampleSize = 4;
		Bitmap realImage = null;
		try {
			realImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

        Log.i(tag, "Width : " + realImage.getWidth() +  " Height :" + realImage.getHeight() + " ori:" + orientation);
        if(bitmap != null){
    		MainActivity.bitmap.recycle();
    		MainActivity.bitmap = null;
    	}
		//Rotate the image
        if(angle != 0){
        	Matrix matrix = new Matrix();
        	matrix.postRotate(angle);

        	bitmap = Bitmap.createBitmap(realImage, 0, 0, realImage.getWidth(), realImage.getHeight(),
        			matrix, true);
        }else{
        	bitmap = realImage;
        }
	}




}
