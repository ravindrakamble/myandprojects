package com.southradios.proj;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.View;

public class SplashscreenActivity extends Activity {

	private Elements elements;
	private boolean isOnline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkConnectivity();
		if(isOnline){
		setContentView(R.layout.splash_layout);
		Asynchtask asynchtask = (Asynchtask) new Asynchtask().execute("test");
		}else{
		setContentView(R.layout.try_again_page);
		}


	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		System.out.println("splashscreen destroy");
	}

	public Elements getUrlFromPhp() throws IOException {
		Document doc = Jsoup.connect("http://www.southradios.com/mobile_url.php").get();
		Elements elements = new Elements();
		elements.addAll(doc.select("tr"));
		return elements;
	}

	private class Asynchtask extends AsyncTask<String, Process, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			try {
				elements = getUrlFromPhp();
				ElementsBean.getInstance().setElements(elements);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Inside thread" + elements);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(ElementsBean.getInstance().getElements() != null){
				if(ElementsBean.getInstance().getElements().size()>0){
				startMainActivity();
				}else{
					setContentView(R.layout.try_again_page);
				}
			}else{
				setContentView(R.layout.try_again_page);
			}
			

		}

	}
	
	public void startMainActivity(){
		Intent intent = new Intent(SplashscreenActivity.this,MainActivity.class);
		finish();
		startActivity(intent);
	}
	
	public void refresh(View view) {

		Intent intent = getIntent();
	    finish();
	    startActivity(intent);
	    
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
