package com.southradios.proj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.R.string;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RadioListViewAdapter extends BaseAdapter {
	
	
	FavoriteRadioDatabaseAdapter dbAdapter;
	

	public RadioListViewAdapter() {
		
		
		 Elements elements = ElementsBean.getInstance().getElements();
		
		if(elements != null){
			System.out.println("Element size :" +elements.size());
			for (int i = 1; i < elements.size(); i++) {	//i initialized to 1 to exclude header
				
				String radioRow = elements.get(i).text();
				
				String[] radioItem = radioRow.split(",");
				
				if(radioItem[4].trim().equals("S")){
					//System.out.println("inside s flag");
				radioListObj.add(new RadioListBean(Integer.parseInt(radioItem[0].trim()), radioItem[1].trim(), radioItem[3].trim(), radioItem[2].trim(), R.drawable.southradio_icon));
				}
			}
		/*radioListObj.add(new RadioListBean(1, "A R Rahman radio", "From and for A R Rahman fans", elements.get(0).text(), R.drawable.ar_rahman_radio));	
		radioListObj.add(new RadioListBean(2, "ARR lite radio", "A.R.Rahman lite melodies", elements.get(1).text(), R.drawable.arr_lite_radio));
		radioListObj.add(new RadioListBean(3, "Ilayamaan radio", "Play Ilayaraja and A R Rahman hits", elements.get(2).text(), R.drawable.ilayamaan_radio));
		radioListObj.add(new RadioListBean(4, "Ilayamagan radio", "Ilayaraja and his son hits", elements.get(3).text(), R.drawable.ilayamagan_radio));
		radioListObj.add(new RadioListBean(5, "Ilayaraja lite radio", "Ilayaraja lite melodies", elements.get(4).text(), R.drawable.ilayaraja_lite_radio));
		radioListObj.add(new RadioListBean(6, "Ilayaraja radio", "Ilayaraja fans club", elements.get(5).text(), R.drawable.ilayaraja_radio));
		radioListObj.add(new RadioListBean(7, "Puthu Paadal radio", "Tamil Latest Hits", elements.get(6).text(), R.drawable.puthu_paadal_radio));
		radioListObj.add(new RadioListBean(8, "SPB radio", "A Voice for Mesmerization", elements.get(7).text(), R.drawable.spb_radio));
		radioListObj.add(new RadioListBean(9, "Tamil Beat radio", "Listen Tamil Hits",elements.get(8).text(), R.drawable.tamil_beat_radio));
		radioListObj.add(new RadioListBean(10, "YSR radio", "Son of Ilayaraja", elements.get(9).text(), R.drawable.yuvan_shankar_raja_radio));*/
		}else{
			System.out.println("Elements null");
		}
		

		
	}
	

	
	private ArrayList<RadioListBean> radioListObj = new ArrayList<RadioListBean>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return radioListObj.size();
	}

	@Override
	public RadioListBean getItem(int index) {
		// TODO Auto-generated method stub
		return radioListObj.get(index);
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

		RadioListBean radioListBeanObj = radioListObj.get(index);

		TextView time_view_obj = (TextView) view.findViewById(R.id.radioName);
		time_view_obj.setText(radioListBeanObj.getRadioName());

		TextView note_view_obj = (TextView) view.findViewById(R.id.radioCaption);
		note_view_obj.setText(radioListBeanObj.getRadioCaption());
		
		ImageView imageView = (ImageView) view.findViewById(R.id.radioIcon);
		imageView.setBackgroundResource(radioListBeanObj.getRadioIcon());

		return view;
	
	} 

}
