package com.ivd.hotshots;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class InformationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_information);
		SetSettings();
		SetHeader();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.information, menu);
		return true;
	}

	public void SetHeader(){
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("INFORMATION");
	}
	public void SetSettings(){
		ImageButton settingBtn=(ImageButton)findViewById(R.id.settingBtn);
		settingBtn.setEnabled(false);
		settingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void Back(View v){
		
		this.finish();
	}
	public void AboutUS(View v){
		 Bundle bundle = new Bundle();
	      //assign the values (key, value pairs)
	      bundle.putString("title", "ABOUT US");
	      bundle.putString("content", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
	      Intent intent = new Intent(InformationActivity.this,
					InfoActivity.class);
	      intent.putExtras(bundle);
			startActivity(intent);
	}
	
	
	public void PRIVACY(View v){
		 Bundle bundle = new Bundle();
	      //assign the values (key, value pairs)
	      bundle.putString("title", "PRIVACY POLICY");
	      bundle.putString("content", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
	      Intent intent = new Intent(InformationActivity.this,
					InfoActivity.class);
	      intent.putExtras(bundle);
			startActivity(intent);
	}
	
	
	public void HELP(View v){
		 Bundle bundle = new Bundle();
	      //assign the values (key, value pairs)
	      bundle.putString("title", "HELP");
	      bundle.putString("content", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
	      Intent intent = new Intent(InformationActivity.this,
					InfoActivity.class);
	      intent.putExtras(bundle);
			startActivity(intent);
	}
	
	
	public void TERMS(View v){
		 Bundle bundle = new Bundle();
	      //assign the values (key, value pairs)
	      bundle.putString("title", "TERMS OF USE");
	      bundle.putString("content", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
	      Intent intent = new Intent(InformationActivity.this,
					InfoActivity.class);
	      intent.putExtras(bundle);
			startActivity(intent);
	}
	
	public void Contacts(View v){
		 Intent intent = new Intent(InformationActivity.this,
					ContactBuildActivity.class);
	      
			startActivity(intent);
	}
	
	
	
	
}
