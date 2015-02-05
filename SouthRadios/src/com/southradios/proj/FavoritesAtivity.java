package com.southradios.proj;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class FavoritesAtivity extends Activity {

	private boolean isOnline;
	
	FavoriteRadioDatabaseAdapter dbAdapter;
	FavRadioListViewAdapter favRadioListViewAdapter = new FavRadioListViewAdapter();

	MainActivity mainActivity;
	ListView favRadioListView;
	TextView  noFavTV;
	boolean isFav;
	
	Elements elements = ElementsBean.getInstance().getElements();
	
	@Override
	protected void onResume() {
	super.onResume();
	
	//For update the urls in Fav tab
/*	Elements elements = null;
	try {
		elements = getUrlFromPhp();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	System.out.println("Resume from fav activity.");
	Cursor c = dbAdapter.getRecords();
	favRadioListViewAdapter.removeFavRadioListObj();
	noFavTV = (TextView) findViewById(R.id.noFav);
	
	if(c.moveToFirst()){
		isFav = true;

		
		for (int i = 0; i < c.getCount(); i++,c.moveToNext()) {
			System.out.println("RadioId : "+c.getInt(0));
			//System.out.println("RadioName : "+c.getString(1));
			//System.out.println("RadioCaption : "+c.getString(2));
			//System.out.println("RadioUrl : "+c.getString(3));
			//System.out.println("RadioIcon : "+c.getInt(4));
			
			for (int j = 1; j < elements.size(); j++) {
				String radioRow = elements.get(j).text();
				String[] radioItem = radioRow.split(",");
				if((Integer.parseInt(radioItem[0].trim()) == c.getInt(0))){
					
					
					Resources resources = getResources();
					int resId = resources.getIdentifier(c.getString(4), "drawable", "com.southradios.proj");
					favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2), radioItem[2].trim(),resId);
				}
			}
			
/*			if (c.getInt(0) >= 0 && c.getInt(0) <= 9) {
				favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2), elements.get(c.getInt(0)-1).text(),c.getInt(4));
				System.out.println("URL from radio obj");
			} else {

				favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2), c.getString(3),c.getInt(4));
			}*/

		}
	}else{
		isFav = false;
		}
	
		if (isFav) {
			noFavTV.setVisibility(View.GONE);
		}else{
		noFavTV.setVisibility(View.VISIBLE);
		noFavTV.setText("No Radio Added To Favorites");
	}
	favRadioListView.setAdapter(favRadioListViewAdapter);
	
	if (!c.isClosed()) {
		c.close();
		}
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		System.out.println("oncreate from fav activity");
		
		//For update the urls in Fav tab
/*		Elements elements = null;
		try {
			elements = getUrlFromPhp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		dbAdapter = new FavoriteRadioDatabaseAdapter(getBaseContext());
		Cursor c = dbAdapter.getRecords();
		
		
		if (c.moveToFirst()) {
			isFav = true;
			for (int i = 0; i < c.getCount(); i++, c.moveToNext()) {
				//System.out.println("RadioId : " + c.getInt(0));
				//System.out.println("RadioName : " + c.getString(1));
				//System.out.println("RadioCaption : " + c.getString(2));
				//System.out.println("RadioUrl : " + c.getString(3));
				//System.out.println("RadioIcon : " + c.getInt(4));
				
				for (int j = 1; j < elements.size(); j++) {
					String radioRow = elements.get(j).text();
					String[] radioItem = radioRow.split(",");
					if((Integer.parseInt(radioItem[0].trim()) == c.getInt(0))){
						//System.out.println("inside s flag");
						
						Resources resources = getResources();
						int resId = resources.getIdentifier(c.getString(4), "drawable", "com.southradios.proj");
						favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2), radioItem[2].trim(),resId);
					}
				}
				

/*				if (c.getInt(0) >= 0 && c.getInt(0) <= 9) {
					favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2),elements.get(c.getInt(0)-1).text(),c.getInt(4));
				} else {

					favRadioListViewAdapter.setFavRadioListObj(c.getInt(0),c.getString(1), c.getString(2), c.getString(3),c.getInt(4));
				}*/

			}
		}else{
			isFav = false;
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites_layout);
		favRadioListView = (ListView) findViewById(R.id.favRadioListView);
		noFavTV = (TextView) findViewById(R.id.noFav);
		favRadioListView.setAdapter(favRadioListViewAdapter);
		
		if(isFav){
			noFavTV.setVisibility(View.GONE);
		}else{
			noFavTV.setVisibility(View.VISIBLE);
			noFavTV.setText("No Radio Added To Favorites");
		}
		
		OnItemClickListener onCickListner = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub

				Intent sIntent = new Intent(FavoritesAtivity.this, PlayServices.class);
			
				System.out.println("RadioPosition 1st from PaadalRadioList: "+position);
				
				sIntent.putExtra("radioPositionToSV", position);
				sIntent.putExtra("fromFlagToSV", "Fav");
				sIntent.putExtra("radioIdToSV", favRadioListViewAdapter.getItem(position).getFavRadioId());
				sIntent.putExtra("radioNameToSV", favRadioListViewAdapter.getItem(position).getFavRadioName());
				sIntent.putExtra("radioCaptionToSV", favRadioListViewAdapter.getItem(position).getFavRadioCaption());
				sIntent.putExtra("radioUrlToSV", favRadioListViewAdapter.getItem(position).getFavRadioUrl());
				sIntent.putExtra("radioIconToSV", favRadioListViewAdapter.getItem(position).getFavRadioIcon());
				sIntent.putExtra("NotificationToSV", "No");
				
				System.out.println("main");
				
				//startActivity(aIntent);
				checkConnectivity();
				if(isOnline){
				startService(sIntent);
				}else{

					AlertDialog alertDialog = new AlertDialog.Builder(FavoritesAtivity.this).create();
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
		favRadioListView.setOnItemClickListener(onCickListner);

		if (!c.isClosed()) {
			//dbAdapter.close();
			c.close();
			}
		
	}

/*	public Elements getUrlFromPhp() throws IOException{
		Document doc = Jsoup.connect("http://www.southradios.com/mobile_url.php").get();
		Elements elements = new Elements();
		elements.addAll(doc.select("td"));
		return elements;
	}*/
	
	private void checkConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting() || 
			cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
			isOnline = true;
		else
			isOnline = false;
	}
	
}
