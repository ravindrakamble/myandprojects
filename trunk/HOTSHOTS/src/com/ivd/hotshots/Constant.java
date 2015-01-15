package com.ivd.hotshots;

import android.app.Activity;
import android.graphics.Typeface;

public class Constant {


	
	public static String REGULER_FONT = "fonts/New Athletic M54.ttf";

	public static String URL = "http://demo.ivdisplays.net/hotshots/webservices/api.php";
	
	
	
	
	public static Typeface getTypeface(Activity activity){
		return Typeface.createFromAsset(activity.getAssets(),Constant.REGULER_FONT);
	}
}
