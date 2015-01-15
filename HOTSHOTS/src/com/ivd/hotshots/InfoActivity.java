package com.ivd.hotshots;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class InfoActivity extends Activity {

	TextView infoview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_info);
		
		infoview = (TextView) findViewById(R.id.InfoView);
		 Bundle bundle=getIntent().getExtras();
		 infoview.setText(bundle.getString("content"));
		 SetHeader();
	}

	public void SetHeader(){
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("INFORMATION");
		
		 Bundle bundle=getIntent().getExtras();
		 title.setText(bundle.getString("title"));
	}
	
public void Back(View v){
		
		this.finish();
	}

}
