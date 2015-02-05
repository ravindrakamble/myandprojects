package com.southradios.proj;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class PlayServices extends Service implements OnPreparedListener,
		OnErrorListener, OnBufferingUpdateListener ,OnInfoListener {

	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	public static final String BROADCAST_ACTION = "com.example.southradiosdev.serviceIntent";
	public static final String SEEK_BAR_ACTION = "com.example.southradiosdev.seekIntent";
	
	private static final int NOTIFICATION_ID = 1;
	
	Intent serviceIntent;
	Intent seekIntent;

	private RadioListViewAdapter radioAdapter = new RadioListViewAdapter();
	private PaadalRadioListViewAdapter paadalAdapter = new PaadalRadioListViewAdapter();
	private FavRadioListViewAdapter favAdapter = new FavRadioListViewAdapter();
	
	private int radioPosition_SV;
	private String fromflag;
	
	private int radioId;
	private String radioNameSv;
	private String radioCaptionSV;
	private int radioIconSV;
	private String radioUrlSV;

	private TelephonyManager telephonyManager;

	private PhoneStateListener phoneStateListener;
	private boolean isPausedInCall = false;
	
	private boolean isOnline;

	

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		 
		serviceIntent = new Intent(BROADCAST_ACTION);
		seekIntent = new Intent(SEEK_BAR_ACTION);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.reset();

	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {

		//System.out.println("Buffering : " + arg1);
		//System.out.println("Position : "+mediaPlayer.getCurrentPosition());
		//seekIntent.putExtra("SeekBarPosition", mediaPlayer.getCurrentPosition());
		//sendBroadcast(seekIntent);
		
		//Toast.makeText(getBaseContext(), "Buffering : "+arg1, 1).show();
		
		/*if(arg1>0 && arg1<100){
		serviceIntent.putExtra("opening", "Buffering "+arg1+"%");
		sendBroadcast(serviceIntent);
		}else{
			serviceIntent.putExtra("opening", "Playing");
			sendBroadcast(serviceIntent);
		}*/
		
		
		
		serviceIntent.putExtra("radioPositionFromSV", radioPosition_SV);
		serviceIntent.putExtra("radioIdSV", radioId);
		serviceIntent.putExtra("radioNameFromSV", radioNameSv);
		serviceIntent.putExtra("radioCaptionFromSV", radioCaptionSV);
		serviceIntent.putExtra("radioIconFromSV", radioIconSV);
		serviceIntent.putExtra("radioURLFromSV", radioUrlSV);
		serviceIntent.putExtra("radioFromFlagV", fromflag);
		sendBroadcast(serviceIntent);

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
/*		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			Toast.makeText(this,"MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra, Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Toast.makeText(this, "MEDIA ERROR SERVER DIED " + extra, Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this, "MEDIA ERROR UNKNOWN " + extra, Toast.LENGTH_SHORT).show();
			break;
		}*/
		
		
		//sendBroadcast(serviceIntent);
		

		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			serviceIntent.putExtra("opening", "Error");
			Toast.makeText(this,"MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra,Toast.LENGTH_SHORT).show();
			sendBroadcast(serviceIntent);
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			serviceIntent.putExtra("opening", "Error");
			Toast.makeText(this, "MEDIA ERROR SERVER DIED " + extra,Toast.LENGTH_SHORT).show();
			sendBroadcast(serviceIntent);
			break;
		/*case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this, "MEDIA ERROR UNKNOWN " + extra,Toast.LENGTH_SHORT).show();
			break;*/
		}
		return false;
	
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		
		System.out.println("In on prepared.");
		//Toast.makeText(getBaseContext(), "Playing", Toast.LENGTH_SHORT).show();
		
		mediaPlayer.start();
		serviceIntent.putExtra("opening", "Playing");
		sendBroadcast(serviceIntent);
		
		// Insert notification start
		initNotification();
		
		System.out.println("In on prepared end.");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		// Manage incoming phone calls during playback. Pause mp on incoming,
				// resume on hangup.
				// -----------------------------------------------------------------------------------
				// Get the telephony manager
				
				telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				
				phoneStateListener = new PhoneStateListener() {
					

					@Override
					public void onCallStateChanged(int state, String incomingNumber) {
						// String stateString = "N/A";
						
						switch (state) {
						case TelephonyManager.CALL_STATE_OFFHOOK:
						case TelephonyManager.CALL_STATE_RINGING:
							if (mediaPlayer != null) {
								pauseMedia();
								isPausedInCall = true;
							}

							break;
						case TelephonyManager.CALL_STATE_IDLE:
							// Phone idle. Start playing.
							if (mediaPlayer != null) {
								if (isPausedInCall) {
									isPausedInCall = false;
									playMedia();
								}

							}
							break;
						}

					}
				};

				// Register the listener with the telephony manager
				telephonyManager.listen(phoneStateListener,
						PhoneStateListener.LISTEN_CALL_STATE);

		
		
		// Get the position of the Radio tp play from RadioPageActivity
		
		
		
		
		
		fromflag = intent.getExtras().getString("fromFlagToSV");
		radioPosition_SV = intent.getExtras().getInt("radioPositionToSV");
		
		if(fromflag.equals("Radios")){
			
			radioId = radioAdapter.getItem(radioPosition_SV).getRadioId();
			radioNameSv = radioAdapter.getItem(radioPosition_SV).getRadioName();
			radioCaptionSV = radioAdapter.getItem(radioPosition_SV).getRadioCaption();
			radioIconSV = radioAdapter.getItem(radioPosition_SV).getRadioIcon();
			radioUrlSV = radioAdapter.getItem(radioPosition_SV).getRadioUrl();
			
		}else if(fromflag.equals("Paadal")){
			radioId = paadalAdapter.getItem(radioPosition_SV).getPaadalRadioId();
			radioNameSv = paadalAdapter.getItem(radioPosition_SV).getPaadalRadioName();
			radioCaptionSV = paadalAdapter.getItem(radioPosition_SV).getPaadalRadioCaption();
			radioIconSV = paadalAdapter.getItem(radioPosition_SV).getPaadalRadioIcon();
			radioUrlSV = paadalAdapter.getItem(radioPosition_SV).getPaadalRadioUrl();
			
		}else if(fromflag.equals("Fav")){
			radioId = intent.getExtras().getInt("radioIdToSV");
			radioNameSv = intent.getExtras().getString("radioNameToSV");
			radioCaptionSV = intent.getExtras().getString("radioCaptionToSV");
			radioIconSV = intent.getExtras().getInt("radioIconToSV");
			radioUrlSV = intent.getExtras().getString("radioUrlToSV");
		}
		
		
		
		serviceIntent.putExtra("radioPositionFromSV", radioPosition_SV);
		serviceIntent.putExtra("radioIdSV", radioId);
		serviceIntent.putExtra("radioNameFromSV", radioNameSv);
		serviceIntent.putExtra("radioCaptionFromSV", radioCaptionSV);
		serviceIntent.putExtra("radioIconFromSV", radioIconSV);
		serviceIntent.putExtra("radioURLFromSV", radioUrlSV);
		serviceIntent.putExtra("radioFromFlagV", fromflag);
		sendBroadcast(serviceIntent);
		
		System.out.println("RadioPosition 2nd in playserv : "+radioPosition_SV);
		System.out.println("inside on strat");
		
		if(mediaPlayer.isPlaying()){
			System.out.println("Playing");
			mediaPlayer.stop();
			mediaPlayer.reset();
		}else{
			System.out.println("Not playing");
		}
		
		// Insert notification start
		//initNotification();
		
		//play(radioAdapter.getItem(radioPosition_SV).getRadioUrl());
		play(radioUrlSV);
		return START_NOT_STICKY;

	}

	protected void play(String radioUrl) {
	
		serviceIntent.putExtra("opening", "Opening");
		sendBroadcast(serviceIntent);
		mediaPlayer.reset();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(radioUrl);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mediaPlayer.prepareAsync();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.release();
		}
		
		// Cancel the notification
		cancelNotification();
	}
	
	// Create Notification
	private void initNotification() {
		
		System.out.println("Notification");
	NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	int icon = R.drawable.notification_icon_final;
	long when = System.currentTimeMillis();
	Notification notification = new Notification(icon, null , when);
	notification.flags = Notification.FLAG_ONGOING_EVENT;

	RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
	contentView.setImageViewResource(R.id.radioIcon_N, radioIconSV);
	contentView.setTextViewText(R.id.radioName_N, radioNameSv);
	contentView.setTextViewText(R.id.radioCaption_N, radioCaptionSV);
	
	notification.contentView = contentView;
	
	//Intent notificationIntent = new Intent(this, MainActivity.class);  //Commented for blamk radio issue
	Intent notificationIntent = new Intent(this, SplashscreenActivity.class);
	Bundle bundle_N = new Bundle();
	bundle_N.putInt("radioPositionToMA", radioPosition_SV);
	System.out.println("Radio Position to notification : " +radioPosition_SV);
	bundle_N.putString("NotificationToMA", "Yes");
	
	if(mediaPlayer.isPlaying()){
		bundle_N.putString("mediaPlayerStatus", "Playing");	
	}else{
		bundle_N.putString("mediaPlayerStatus", "No");
	}
	
	notificationIntent.putExtras(bundle_N);
	
	/*// For Navigation Start
	TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
	// Adds the back stack
	stackBuilder.addParentStack(RadioPageActivity.class);
	// Adds the Intent to the top of the stack
	stackBuilder.addNextIntent(notificationIntent);
	// For Navigation End
*/	
	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0); //Commented for navigation
	
	//PendingIntent contentIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	notification.contentIntent = contentIntent;
	
	mNotificationManager.notify(NOTIFICATION_ID, notification);
}

	// Cancel Notification
	private void cancelNotification() {
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(NOTIFICATION_ID);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		//Toast.makeText(getBaseContext(), "Buffering : " +what+" , "+extra, Toast.LENGTH_SHORT).show();
		
		checkConnectivity();
		if (isOnline) {
			if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
				serviceIntent.putExtra("opening", "Buffering");
				sendBroadcast(serviceIntent);
			} else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
				serviceIntent.putExtra("opening", "Playing");
				sendBroadcast(serviceIntent);
			}
		} else {
			serviceIntent.putExtra("opening", "No Network");
			sendBroadcast(serviceIntent);
			stopSelf();
			//cancelNotification();
		}
		
		return false;
	}
	
	// Add for Telephony Manager
	public void pauseMedia() {
		// Log.v(TAG, "Pause Media");
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}

	}
	
	// Add for Telephony Manager
	public void playMedia() {
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
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
