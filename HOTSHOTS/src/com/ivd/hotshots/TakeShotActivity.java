package com.ivd.hotshots;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class TakeShotActivity extends Activity {

	int angle=0;
	int imageStatus = 0;
	final CharSequence[] uploadOptions = { "Choose From Gallery" };
	
	private static final int REQUEST_CODE_GALLERY = 2;
	private static final int REQUEST_CODE_CAMERA = 1;
	private String picname = "profile.jpg";
	private String blurImage = "blur.jpg";
	
	private String filepath1 = Environment.getExternalStorageDirectory()
			+ File.separator + picname;
	
	private String filepath2 = Environment.getExternalStorageDirectory()
			+ File.separator + blurImage;
	
	Dialog dialog;
	int height;
	int width;
	Bitmap bmp = null;
	 private Bitmap operation =null;
	ProgressDialog progressDialog = null;
	Intent intent;
	Intent photoPickerIntent;

	private String filepath;
	private String imagename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_take_shot);
		SetSettings();
	}

	public void SetSettings(){
		ImageButton settingBtn=(ImageButton)findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TakeShotActivity.this,
						InformationActivity.class);
				startActivity(intent);
				
				
			}
		});
	}
	
	public void OpenCamera(View v){
		
		imagename = picname;
		filepath = filepath1;
		
	Uri outputFileUri = Uri
				.fromFile(new File(filepath));
		 intent = new Intent(
				"android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				outputFileUri);
		intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	public void OpenGallery(View v){
		
		imagename = picname;
		 photoPickerIntent = new Intent(
				Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				filepath);
		startActivityForResult(photoPickerIntent,
				REQUEST_CODE_GALLERY);
	}
	
	
	

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public Bitmap decodeSampledBitmapFromResource(int reqWidth, int reqHeight) {

		File f = new File(filepath);
		if (f.exists()) {
		// Log.e("FILE EXIST", "TRUE");
			//Utility.ShowNotification(DesignActivity.this,"File EXist");
		}

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		BitmapFactory.decodeFile(filepath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filepath, options);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		
		case REQUEST_CODE_GALLERY:
			
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA ,MediaStore.Images.Media.ORIENTATION};
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String tempPath = cursor.getString(columnIndex);
				filepath =tempPath;
				 angle =  cursor.getInt(cursor.getColumnIndex(filePathColumn[1]));
				cursor.close();
				
				if(angle == 0){
					angle = 90;
				}
				
				new BlurImage().execute("");

			}
			break;
			
		case REQUEST_CODE_CAMERA:
			
			if (resultCode == Activity.RESULT_OK) {
				
				Uri outputFileUri = Uri
						.fromFile(new File(filepath));
				String[] filePathColumn = { MediaStore.Images.Media.ORIENTATION };
				Cursor cursor = getContentResolver().query(outputFileUri,
						filePathColumn, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					angle =  cursor.getInt(cursor.getColumnIndex(filePathColumn[0]));
					cursor.close();
				}
				
				if(angle == 0){
					ExifInterface exif;
					try {
						exif = new ExifInterface(outputFileUri.getPath());
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

				}
				if(angle == 0){
					angle = 90;
				}
				
				Log.e("Angle", ""+angle);
				new BlurImage().execute("");
			}
			break;

		}

	}
	
	public void ImageProcessing(){
		 height = 800;
			width = 480;
			bmp = decodeSampledBitmapFromResource(width, height);
			
		storeImage(bmp);
		
		
		CreateBlurImage();
	}
	
	public void storeImage(Bitmap image) {
	    File pictureFile = getOutputMediaFile();
	    if (pictureFile == null) {
	        Log.d("DEBUG",
	                "Error creating media file, check storage permissions: ");// e.getMessage());
	        return;
	    } 
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.d("DEBUG", "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d("DEBUG", "Error accessing file: " + e.getMessage());
	    }  
	}
	
	public  File getOutputMediaFile(){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this. 
	    File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + "/Android/data/"
	            + getApplicationContext().getPackageName()
	            + "/Files"); 

	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    } 
	    // Create a media file name
	  
	    File mediaFile;
	      
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + imagename);  
	    return mediaFile;
	} 
	
	public void ShowQuestion(){
		
		AppDelegate delegate = (AppDelegate) getApplicationContext();
		delegate.picname = picname;
		delegate.blurImageName = blurImage;
		delegate.angle = angle;
		Intent intent = new Intent(this,SenderQuestionActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void CreateBlurImage(){
		imagename = blurImage;
		filepath = filepath2;
		operation = bmp;
		dark();
		
	}
	public void gray(){
	      operation= Bitmap.createBitmap(bmp.getWidth(),
	      bmp.getHeight(),bmp.getConfig());

	      double red = 0.33;
	      double green = 0.59;
	      double blue = 0.11;

	   for(int i=0; i<bmp.getWidth(); i++){
	      for(int j=0; j<bmp.getHeight(); j++){
	         int p = bmp.getPixel(i, j);
	         int r = Color.red(p);
	         int g = Color.green(p);
	         int b = Color.blue(p);

	         r = (int) red * r;
	         g = (int) green * g;
	         b = (int) blue * b;

	         operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
	         }
	      }
	   
	   storeImage(operation);
	   
	     
	   }

	   public void bright(){
	      operation= Bitmap.createBitmap(bmp.getWidth(),
	      bmp.getHeight(),bmp.getConfig());


	      for(int i=0; i<bmp.getWidth(); i++){
	         for(int j=0; j<bmp.getHeight(); j++){
	            int p = bmp.getPixel(i, j);
	            int r = Color.red(p);
	            int g = Color.green(p);
	            int b = Color.blue(p);
	            int alpha = Color.alpha(p);

	            r = 100  +  r;
	            g = 100  + g;
	            b = 100  + b;
	            alpha = 100 + alpha;

	            operation.setPixel(i, j, Color.argb(alpha, r, g, b));
	         }
	      }
	      storeImage(operation);
	   }

	   public void dark(){
	      operation= Bitmap.createBitmap(bmp.getWidth(),
	      bmp.getHeight(),bmp.getConfig());


	      for(int i=0; i<bmp.getWidth(); i++){
	         for(int j=0; j<bmp.getHeight(); j++){
	            int p = bmp.getPixel(i, j);
	            int r = Color.red(p);
	            int g = Color.green(p);
	            int b = Color.blue(p);
	            int alpha = Color.alpha(p);

	            r =  r - 100;
	            g =  g - 100;
	            b =  b - 100;
	            alpha = alpha -80;
	            operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));


	          }
	       }

	      storeImage(operation);
	   }
	   
	   
	   
	   class BlurImage extends AsyncTask<String, Long, Void>{

		   @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				progressDialog = ProgressDialog.show(TakeShotActivity.this, "",
						" Loading Image..", true);
			}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			ImageProcessing();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			// showAlertDiaplg(_data);
			

			progressDialog.dismiss();
			ShowQuestion();
		

		}
		   
	   }
	   
	/*
	   public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
		     int rotate = 0;
		     try {
		         context.getContentResolver().notifyChange(imageUri, null);
		         File imageFile = new File(imagePath);
		         ExifInterface exif = new ExifInterface(
		                 imageFile.getAbsolutePath());
		         int orientation = exif.getAttributeInt(
		                 ExifInterface.TAG_ORIENTATION,
		                 ExifInterface.ORIENTATION_NORMAL);

		         switch (orientation) {
		         case ExifInterface.ORIENTATION_ROTATE_270:
		             rotate = 270;
		             break;
		         case ExifInterface.ORIENTATION_ROTATE_180:
		             rotate = 180;
		             break;
		         case ExifInterface.ORIENTATION_ROTATE_90:
		             rotate = 90;
		             break;
		         }


		         Log.d("", "Exit orientation: " + orientation);
		     } catch (Exception e) {
		         e.printStackTrace();
		     }
		    return rotate;
		 }
	   
	  */

}
