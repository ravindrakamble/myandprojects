package com.ivd.hotshots;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivd.models.Hotshots;

public class OpenShotsActivity extends Activity {

	private ListView contact_list;
	ContactAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_open_shots);
		SetHeader();
		SetSettings();
		AppDelegate delegate = (AppDelegate) getApplicationContext();

		contact_list = (ListView) findViewById(R.id.contact_list);

		adapter = new ContactAdapter(this, delegate.hotshotList);
		contact_list.setAdapter(adapter);
		contact_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				AppDelegate delegate = (AppDelegate) getApplicationContext();
				delegate.hotshots = delegate.hotshotList.get(position);
			
				Intent intent = new Intent(OpenShotsActivity.this,
						BlurImageActivity.class);
				startActivity(intent);
			}

		});

	}

	public void SetSettings(){
		ImageButton settingBtn=(ImageButton)findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OpenShotsActivity.this,
						InformationActivity.class);
				startActivity(intent);
				
				
			}
		});
	}
	public void SetHeader() {
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("RECEIVED");
	}

	class ContactAdapter extends ArrayAdapter<Hotshots> {

		private final Context context;
		private final List<Hotshots> values;

		public ContactAdapter(Context context, List<Hotshots> values) {
			super(context, R.layout.sender_contact_layout, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.sender_contact_layout,
					parent, false);

			TextView name = (TextView) rowView.findViewById(R.id.name);
			ImageView chckBox = (ImageView) rowView.findViewById(R.id.trashBtn);

			Hotshots contact = values.get(position);

			name.setText(contact.getName());

			chckBox.setVisibility(View.GONE);

			return rowView;
		}
	}

}
