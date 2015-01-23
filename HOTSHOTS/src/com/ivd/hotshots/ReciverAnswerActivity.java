package com.ivd.hotshots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.ivd.http.UiUpdator;
import com.ivd.http.models.RegResponse;
import com.ivd.util.AppConstants;
import com.ivd.util.Utility;

@SuppressLint("NewApi")
public class ReciverAnswerActivity extends RootActivity implements UiUpdator {

	EditText answerView;
	int count = 10;
	ImageView hotshot;
	private RelativeLayout try_again_layout;

	private RelativeLayout delete_layout;

	private LinearLayout answerbox;
	TextView guess;

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
		setContentView(R.layout.activity_reciver_answer);

		answerView = (EditText) findViewById(R.id.answerView);
		try_again_layout = (RelativeLayout) findViewById(R.id.try_again_layout);
		//transparentView = (View) findViewById(R.id.transparentView);
		delete_layout = (RelativeLayout) findViewById(R.id.delete_layout);
		//transparentDeleteView = (View) findViewById(R.id.transparentDeleteView);
		answerbox = (LinearLayout) findViewById(R.id.answerbox);
		guess = (TextView) findViewById(R.id.guessText);

		try_again_layout.setVisibility(View.INVISIBLE);
		delete_layout.setVisibility(View.INVISIBLE);




		Utility.setupUIKeyboardListner(findViewById(R.id.parent), this);
		Utility.HideKeyboardOnStart(this);
		// SetValue();

		hotshot = (ImageView) findViewById(R.id.hotshot);

		loadImage();
		AlphaAnimation alpha = new AlphaAnimation(0.1F, 0.1F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends
		hotshot.startAnimation(alpha);

	}

	public void SetValue() {
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

	public void SUBMIT(View v) {
		Boolean isVerify = verify();
		if (isVerify == true) {

			AppDelegate delegate = (AppDelegate) getApplicationContext();
			String answer = answerView.getText().toString();
			if (answer.compareToIgnoreCase(delegate.hotshots.getAnswer()) == 0) {
				ShowAnswer();
			} else {
				count--;
				delegate.count = count;
				if (count == 0) {

					ShowDelete();
					sendUpdateMessage();
				} else {
					ShowTryAgain();
				}
			}

		}
	}

	public void ShowAnswer() {
		Intent intent = new Intent(this, HotShotsActivity.class);
		startActivity(intent);
	}

	@SuppressLint("NewApi")
	public void ShowTryAgain() {
		/*Intent intent = new Intent(this, TryAgainActivity.class);
		startActivity(intent);*/
		guess.setText(count+" GUESS REMAINING");
		try_again_layout.setVisibility(View.VISIBLE);
		delete_layout.setVisibility(View.INVISIBLE);
		answerView.setEnabled(false);

		answerbox.setAlpha(128);
	}

	@SuppressLint("NewApi")
	public void ShowDelete() {
		/*Intent intent = new Intent(this, DeleteActivity.class);
		startActivity(intent);*/
		try_again_layout.setVisibility(View.INVISIBLE);
		delete_layout.setVisibility(View.VISIBLE);
		answerbox.setEnabled(false);

		answerbox.setAlpha(128);

	}

	private void sendUpdateMessage(){
		Type type = new TypeToken<RegResponse>(){}.getType();
		sendRequest(AppConstants.REQUEST_UPDATE_MESSAGE, null, type);

		showProgressDialog();
	}
	public void TRYAGAIN(View v){



		try_again_layout.setVisibility(View.INVISIBLE);
		delete_layout.setVisibility(View.INVISIBLE);
		answerView.setEnabled(true);

		answerbox.setAlpha(255);
	}


	public void BACK(View v){
		Intent intent = new Intent(this,
				MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
			this.finish();
	}
	public void loadImage() {

		AppDelegate delegate = (AppDelegate) getApplicationContext();
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory() + "/Android/data/"
						+ getApplicationContext().getPackageName() + "/Files");

		File file = new File(mediaStorageDir, delegate.hotshots.getImagename()); // or
																					// any
																					// other
																					// format
																					// supported

		FileInputStream streamIn;
		try {
			streamIn = new FileInputStream(file);
			Bitmap bitmap = BitmapFactory.decodeStream(streamIn); // This gets
																	// the image

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
