package com.southradios.proj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PaadalgalRadiosActivity extends Activity {

	private boolean isOnline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paadal_radios_layout);
		ListView paadalRadioListView = (ListView) findViewById(R.id.paadalRadioListView);
		final PaadalRadioListViewAdapter paadalRadioListViewAdapter = new PaadalRadioListViewAdapter();
		paadalRadioListView.setAdapter(paadalRadioListViewAdapter);
		
		OnItemClickListener onCickListner = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub

				Intent sIntent = new Intent(PaadalgalRadiosActivity.this, PlayServices.class);
			
				System.out.println("RadioPosition 1st from PaadalRadioList: "+position);
				
				sIntent.putExtra("radioPositionToSV", position);
				sIntent.putExtra("fromFlagToSV", "Paadal");
				sIntent.putExtra("NotificationToSV", "No");
				
				System.out.println("main");
				
				//startActivity(aIntent);
				checkConnectivity();
				if(isOnline){
				startService(sIntent);
				}else{

					AlertDialog alertDialog = new AlertDialog.Builder(PaadalgalRadiosActivity.this).create();
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
		};
		paadalRadioListView.setOnItemClickListener(onCickListner);

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
