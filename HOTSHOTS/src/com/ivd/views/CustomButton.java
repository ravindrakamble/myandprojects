package com.ivd.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button{

	public CustomButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}
	
	   public void init() {
	        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/NewAthleticM54.ttf");
	        setTypeface(tf ,1);

	    }

}
