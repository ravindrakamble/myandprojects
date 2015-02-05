package com.southradios.proj;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class FavRadioListViewAdapter extends BaseAdapter {

	
	public FavRadioListViewAdapter() {
		
	}
	
	
	
	
	


	public void setFavRadioListObj(int id, String radioName, String radioCaption, String radioUrl, int radioIcon) {
		this.favRadioListObj.add(new FavRadioListBean(id, radioName, radioCaption, radioUrl, radioIcon)); 
	}
	
	public void removeFavRadioListObj() {
		this.favRadioListObj.clear(); 
	}
	

	
	
	
	





	private ArrayList<FavRadioListBean> favRadioListObj = new ArrayList<FavRadioListBean>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return favRadioListObj.size();
	}

	@Override
	public FavRadioListBean getItem(int index) {
		// TODO Auto-generated method stub
		return this.favRadioListObj.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		// TODO Auto-generated method stub

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.singleradiolayout, parent, false);
		}

		FavRadioListBean favRadioListBeanObj = favRadioListObj.get(index);

		TextView time_view_obj = (TextView) view.findViewById(R.id.radioName);
		time_view_obj.setText(favRadioListBeanObj.getFavRadioName());

		TextView note_view_obj = (TextView) view.findViewById(R.id.radioCaption);
		note_view_obj.setText(favRadioListBeanObj.getFavRadioCaption());
		
		ImageView imageView = (ImageView) view.findViewById(R.id.radioIcon);
		imageView.setBackgroundResource(favRadioListBeanObj.getFavRadioIcon());

		return view;
	
	} 

}
