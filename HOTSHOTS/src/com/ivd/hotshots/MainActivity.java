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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	
	int imageStatus = 0;
	final CharSequence[] uploadOptions = { "Choose From Gallery" };
	
	private static final int REQUEST_CODE_GALLERY = 2;
	private static final int REQUEST_CODE_CAMERA = 1;
	private String picname = "profile.jpg";
	//private String blurImage = "blur.jpg";
	
	private String filepath1 = Environment.getExternalStorageDirectory()
			+ File.separator + picname;
	
//	private String filepath2 = Environment.getExternalStorageDirectory()
//			+ File.separator + blurImage;
	
	Dialog dialog;
	int height;
	int width;
	Bitmap bmp = null;
	ProgressDialog progressDialog = null;
	

	private String filepath;
	private String imagename;
	Button notification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		notification = (Button) findViewById(R.id.notification);
		SetSettings();
		AppDelegate delegate = (AppDelegate) getApplicationContext();
		if(delegate.isRegistered ==1){
			delegate.isRegistered =0;
		ShowAddContacts();
		
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AppDelegate delegate = (AppDelegate) getApplicationContext();
		if(delegate.hotshotList.size()>0){
			notification.setVisibility(View.VISIBLE);
			notification.setText(""+delegate.hotshotList.size());
		}else{
			notification.setVisibility(View.INVISIBLE);
		}
	}
	public void ShowAddContacts(){
		
		Intent intent = new Intent(MainActivity.this,
				AddContactsActivity.class);
		startActivity(intent);
		
	}

	public void SetSettings(){
		ImageButton settingBtn=(ImageButton)findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						InformationActivity.class);
				startActivity(intent);
				
				
			}
		});
	}
	public void TakeHotShots(View v){
		
	/*	PopupMenu popup = new PopupMenu(MainActivity.this, v);
		// Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.camera_option, popup.getMenu());

		// registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(android.view.MenuItem item) {

				switch (item.getItemId()) {
				case R.id.four:
					
					OpenCamera();
					break;

				case R.id.five:
					OpenGallery();
					break;

			
				default:
					break;
				}
				return true;
			}
		});

		popup.show();// showing popup menu*/
		Intent intent = new Intent(MainActivity.this,
				TakeShotActivity.class);
		startActivity(intent);	
		
	}

	public void OpenHotShots(View v){
		Intent intent = new Intent(MainActivity.this,
				OpenShotsActivity.class);
		startActivity(intent);
		
	}
	
	//------------Open Camera Portion-------------------------//
	public void OpenCamera(){
		
		imagename = picname;
		filepath = filepath1;
		
	Uri outputFileUri = Uri
				.fromFile(new File(filepath));
		Intent intent = new Intent(
				"android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				outputFileUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	public void OpenGallery(){
		
		imagename = picname;
		Intent photoPickerIntent = new Intent(
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
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String tempPath = cursor.getString(columnIndex);
				filepath =tempPath;
				cursor.close();
				 height = 400;
					width = 400;
					bmp = decodeSampledBitmapFromResource(width, height);
			
				storeImage(bmp);
				
				
				ShowQuestion();

			}
			break;
			
		case REQUEST_CODE_CAMERA:
			
			if (resultCode == Activity.RESULT_OK) {
				
				height = 400;
				width = 400;
				bmp = decodeSampledBitmapFromResource(width, height);
			
				storeImage(bmp);
				
				ShowQuestion();
				

			}
			break;


			
		}
		
		

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
		delegate.picname = imagename;
		Intent intent = new Intent(this,SenderQuestionActivity.class);
		startActivity(intent);
		//finish();
	}
	
}
