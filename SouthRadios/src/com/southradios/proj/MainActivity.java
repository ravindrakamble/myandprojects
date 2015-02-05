package com.southradios.proj;




import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends ActivityGroup {

	TabHost mTabHost;
	TabSpec mTabSpec;
	private String fromRadioServiceStatus;
	private ImageView radio_Icon_MPV;
	private ImageView radio_Error_MPV;
	private TextView radio_Name_MPV;
	private RelativeLayout nowPlaying_Layout_MPV;
	private RadioListViewAdapter adapterMP;
	private ProgressBar progress_MPV;
	private TextView radio_Status_MPV;
	private ImageView playStopMP;
	private SharedPreferences prefs;
	private String prefName = "MyPref";
	private boolean boolMusicPlaying;
	private String isplaying_FromSV;
	private ImageView radio_fav_MPV;
	
	private int radioPositionMP;
	private int radioIdMP;
	private String radioNameMP;
	private String radioCaptionMP;
	private int radioIconMP;
	private String radioUrlMP;
	private String radioFromFlagMP;
	
	private boolean isOnline;
	private boolean isFavorite;
	
	private Elements elements;
	ElementsBean elementsBean;
	
	 //int a=0;
	 
	FavoriteRadioDatabaseAdapter dbAdapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		checkConnectivity();
			
			dbAdapter = new FavoriteRadioDatabaseAdapter(getBaseContext());
			elements = ElementsBean.getInstance().getElements();
				
		
			
		System.out.println("Inside non refresh.");
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar_home);

		Intent fIntent = new Intent(this, RadiosActivity.class);
		Intent sIntent = new Intent(this, FavoritesAtivity.class);
		Intent tIntent = new Intent(this, PaadalgalRadiosActivity.class);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);

		mTabHost.setup(this.getLocalActivityManager());
		// mTabHost.setup();
		mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);

		mTabSpec = mTabHost.newTabSpec("Tab1");
		mTabSpec.setContent(fIntent);
		mTabSpec.setIndicator(createTabView(mTabHost.getContext(), "RADIOS"));
		mTabHost.addTab(mTabSpec);

		mTabSpec = mTabHost.newTabSpec("Tab2");
		mTabSpec.setContent(sIntent);
		mTabSpec.setIndicator(createTabView(mTabHost.getContext(), "FAVORITES"));
		mTabHost.addTab(mTabSpec);

		mTabSpec = mTabHost.newTabSpec("Tab3");
		mTabSpec.setContent(tIntent);
		mTabSpec.setIndicator(createTabView(mTabHost.getContext(), "PAADALRADIOS"));
		mTabHost.addTab(mTabSpec);
		

		//adapterMP = new RadioListViewAdapter();
		init();
		
			/*System.out.println("Inside refresh.");
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.try_again_page);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_bar_home);*/
		

/*		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {

			int radioPositionMP_N = bundle.getInt("radioPositionToMA");
			System.out.println("radio position from notification" + radioPositionMP_N);
			fromRadioServiceStatus = bundle.getString("NotificationToMA");
			isplaying_FromSV = bundle.getString("mediaPlayerStatus");
			
			
			//nowPlaying_Layout_MPV.setVisibility(View.VISIBLE);
			radio_Icon_MPV.setImageResource(adapterMP.getItem(radioPositionMP_N).getRadioIcon());
			
			radio_Status_MPV.setText(isplaying_FromSV.toString());
			playStopMP.setImageResource(R.drawable.stop);
			boolMusicPlaying=true;
			
		}*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		 System.out.println("Menu Item Clicked");
		switch (item.getItemId()) {
		case R.id.minimize:
			minimize();
			break;

		case R.id.exit:
			exit();
			break;
		}

		return false;
	}

	
	
	
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		//System.out.println("inside saved instance : "+radio_Status_MPV.getText());
	}



	private void init() {
		// TODO Auto-generated method stub
		radio_Icon_MPV = (ImageView) findViewById(R.id.radioIcon_small_MainPage);
		
		nowPlaying_Layout_MPV = (RelativeLayout) findViewById(R.id.playStopButtonLayout_MainPage);
		radio_Status_MPV = (TextView) findViewById(R.id.playingStatus_MainPage);
		progress_MPV = (ProgressBar) findViewById(R.id.statusProgressBar_MainPage);
		playStopMP = (ImageView) findViewById(R.id.playStopButton_MainPage);
		radio_Error_MPV = (ImageView) findViewById(R.id.errorMainPage);
		radio_fav_MPV = (ImageView) findViewById(R.id.favButton);
		registerReceiver(broadcastReceiver, new IntentFilter(PlayServices.BROADCAST_ACTION));
		

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.title);
		tv.setText(text);
		return view;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//System.out.println("Main activity destroy");
		unregisterReceiver(broadcastReceiver);
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//registerReceiver(broadcastReceiver, new IntentFilter(PlayServices.BROADCAST_ACTION));
		/*System.out.println("Main activity pause");
		
		System.out.println("playing flag in pause : " +boolMusicPlaying);
		System.out.println("radio position flag in pause : " +radioPositionMP);
		
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt("RadioPositionRestore", radioPositionMP);
		editor.putBoolean("RadioPlayBool", boolMusicPlaying);
		editor.commit();*/
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		registerReceiver(broadcastReceiver, new IntentFilter(PlayServices.BROADCAST_ACTION));
		
		/*System.out.println("Main activity resume");

		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		
		int savedRadioPosition = prefs.getInt("RadioPositionRestore", 0);
		boolean playing = prefs.getBoolean("RadioPlayBool", false);
		
		System.out.println("RadioPosition in resume : " + savedRadioPosition);
		
		if(playing){
		System.out.println("RadioPosition in resume : " + savedRadioPosition);
		radio_Icon_MPV.setImageResource(adapterMP.getItem(savedRadioPosition).getRadioIcon());
		playStopMP.setImageResource(R.drawable.stop);
		}
*/
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//System.out.println("Main activity stop");
	}

	public void nowPlaying(View view) {
		//System.out.println("Now playing clicked.");
		
		// Intent intent = new Intent(this,RadioPageActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		
		//openOptionsMenu();
	}
	
	
	public void refresh(View view) {
		//System.out.println("Now playing clicked.");
		
		// Intent intent = new Intent(this,RadioPageActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		
		//openOptionsMenu();
		
		Intent intent = getIntent();
	    finish();
	    startActivity(intent);
	}
	
	
	
	//For Share button
	public void share(View view) {
		//System.out.println("Share clicked");
		
		String shareRadioName;
		
		if(radioNameMP!=null){
			shareRadioName = radioNameMP;
		}else{
			shareRadioName = "SouthRadios";
		}
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Listen to "+shareRadioName+" from southradios.com");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
		
	}
	
	//For Favorites button
	public void favorites(View view) {
		System.out.println("Favorites Radio Name : "+radioNameMP);
		System.out.println("Favorites Radio Id : "+radioIdMP);
		
		Cursor c = dbAdapter.getRecords(radioIdMP);
		if(c.getCount()>0){
			dbAdapter.RemoveRecords(radioIdMP);
			radio_fav_MPV.setVisibility(View.VISIBLE);
			radio_fav_MPV.setImageResource(R.drawable.fav_off);
		}else{
			
			Resources resources = getResources();
			String radioIconName = resources.getResourceEntryName(radioIconMP);
			
			//dbAdapter.saveRecords(radioIdMP, radioNameMP, radioCaptionMP, radioUrlMP, radioIconMP);  /commented for fav issue
			dbAdapter.saveRecords(radioIdMP, radioNameMP, radioCaptionMP, radioUrlMP, radioIconName);
			radio_fav_MPV.setVisibility(View.VISIBLE);
			radio_fav_MPV.setImageResource(R.drawable.fav_on);
		}
		
		
		mTabHost.setCurrentTab(0);
		mTabHost.setCurrentTab(1);
		
		System.out.println("Record Saved");
		if (!c.isClosed()) {
			//dbAdapter.close();
			c.close();
			}
	}
	
	//For Exit button
	public void exit() {
		
		System.out.println("Exit");
		Intent sIntent = new Intent(this, PlayServices.class);
		stopService(sIntent);
		
		finish();
		//System.exit(0);
		
		
	}
	
	//For Exit button
	public void menu(View view) {
		
		System.out.println("Menu");
		openOptionsMenu();
		
		
	}
	
	//For Minimize button
	public void minimize() {
		finish();
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent serviceIntent) {
			// System.out.println("broadcaster....!");
			if (isOnline) {
				updateUI(serviceIntent);
			}

		}
	};
	
	
	

	
	

	private void updateUI(Intent serviceIntent) {
		
		String status = serviceIntent.getExtras().getString("opening");
		radioPositionMP = serviceIntent.getExtras().getInt("radioPositionFromSV");
		radioIdMP = serviceIntent.getExtras().getInt("radioIdSV");
		radioNameMP = serviceIntent.getExtras().getString("radioNameFromSV");
		radioCaptionMP = serviceIntent.getExtras().getString("radioCaptionFromSV");
		radioIconMP = serviceIntent.getExtras().getInt("radioIconFromSV");
		radioUrlMP = serviceIntent.getExtras().getString("radioURLFromSV");
		radioFromFlagMP = serviceIntent.getExtras().getString("radioFromFlagV");
		// int seek_position = serviceIntent.getIntExtra("SeekBarPosition", 0);
		
		//System.out.println("radio posiition : "+radioPositionMP);
		//System.out.println("Play Status" + status);

		
		nowPlaying_Layout_MPV.setVisibility(View.VISIBLE);
		Cursor c = dbAdapter.getRecords(radioIdMP);
		if(c.getCount()>0){
			radio_fav_MPV.setVisibility(View.VISIBLE);
			radio_fav_MPV.setImageResource(R.drawable.fav_on);
		}else{
			radio_fav_MPV.setVisibility(View.VISIBLE);
			radio_fav_MPV.setImageResource(R.drawable.fav_off);
		}
		radio_Icon_MPV.setImageResource(radioIconMP);
		
		if (!c.isClosed()) {
			c.close();
			}
		

		if (status != null) {

			if (status.equals("Playing")) {
				// startTimer();
				progress_MPV.setVisibility(View.GONE);
				if(!radio_Status_MPV.getText().toString().startsWith("Playing")){
				radio_Status_MPV.setText("Playing "+radioNameMP+" at 128K");	
				radio_Status_MPV.setSelected(true);
				
				}
				radio_Error_MPV.setVisibility(View.GONE);
				playStopMP.setImageResource(R.drawable.stop);
				boolMusicPlaying=true;

			} else if (status.equals("Opening")) {
				progress_MPV.setVisibility(View.VISIBLE);
				radio_Error_MPV.setVisibility(View.GONE);
				//radio_Status_MPV.setText(status.toString());
				radio_Status_MPV.setText("Opening "+radioNameMP);
				boolMusicPlaying=true;
				playStopMP.setImageResource(R.drawable.stop);
				
			} else if (status.startsWith("Buffering")) {
				progress_MPV.setVisibility(View.VISIBLE);
				radio_Error_MPV.setVisibility(View.GONE);
				radio_Status_MPV.setText(status.toString());
				playStopMP.setImageResource(R.drawable.stop);
			} else if (status.startsWith("Error")) {
				progress_MPV.setVisibility(View.GONE);
				radio_Error_MPV.setVisibility(View.VISIBLE);
				radio_Status_MPV.setText("Error please try again");
				playStopMP.setImageResource(R.drawable.play);
				Intent sIntent = new Intent(this, PlayServices.class);
				stopService(sIntent);
				boolMusicPlaying=false;
			}else if(status.startsWith("No Network")){
				progress_MPV.setVisibility(View.GONE);
				radio_Error_MPV.setVisibility(View.VISIBLE);
				radio_Status_MPV.setText("No Network please try again");
				playStopMP.setImageResource(R.drawable.play);
				//Intent sIntent = new Intent(this, PlayServices.class);
				//stopService(sIntent);
				boolMusicPlaying=false;
			}

		}

		// seekBar.setProgress(seek_position/1000);
	}
	
public void playStopButton (View view){

	Intent sIntent = new Intent(this, PlayServices.class);
	sIntent.putExtra("radioPositionToSV", radioPositionMP);
	sIntent.putExtra("fromFlagToSV", radioFromFlagMP);
	sIntent.putExtra("NotificationToSV", "No");
	
	
	if(boolMusicPlaying){
		stopService(sIntent);
		boolMusicPlaying=false;
		playStopMP.setImageResource(R.drawable.play);
		radio_Status_MPV.setText("Stopped");
		progress_MPV.setVisibility(View.INVISIBLE);
		//time = 0;
		//stopTimer();
		//seekBar.setProgress(0); // Reset the seekbar position.
		//unregisterReceiver(broadcastReceiver);
		System.out.println("Stopped");
	}else{
		checkConnectivity();
		if(isOnline){
		startService(sIntent);
		boolMusicPlaying=true;
		playStopMP.setImageResource(R.drawable.stop);
		}else{

			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			alertDialog.setTitle("Network Not Connected...");
			alertDialog.setMessage("Please connect to a network and try again");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// here you can add functions
				}
			});
			alertDialog.setIcon(R.drawable.network_icon);
			//buttonPlayStop.setBackgroundResource(R.drawable.playbuttonsm);
			alertDialog.show();
		
		}
		
	}
	
	

}

	private void checkConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting() || 
			cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
			isOnline = true;
		else
			isOnline = false;
	}
	

	


}
