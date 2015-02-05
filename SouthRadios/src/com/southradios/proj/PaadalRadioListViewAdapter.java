package com.southradios.proj;

import java.util.ArrayList;

import org.jsoup.select.Elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PaadalRadioListViewAdapter extends BaseAdapter {

	public PaadalRadioListViewAdapter() {
		
		 Elements elements = ElementsBean.getInstance().getElements();
			
		if (elements != null) {
			for (int i = 1; i < elements.size(); i++) { // i initialized to 1 to exclude header
														

				String radioRow = elements.get(i).text();
				String[] radioItem = radioRow.split(",");
				if (radioItem[4].trim().equals("P")) {

					paadalRadioListObj.add(new PaadalRadioListBean(Integer.parseInt(radioItem[0].trim()), radioItem[1].trim(), radioItem[3].trim(), radioItem[2].trim(), R.drawable.paadalradio_icon));

				}
			}
		} else {
			System.out.println("Elements null");
		}
		
/*		paadalRadioListObj.add(new PaadalRadioListBean(11, "A9 radio", "PaadalRadios", "http://195.154.217.103:8175/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(12, "Carnatic radio", "PaadalRadios", "http://67.228.150.184:7220/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(13, "DJSiran Tamil FM", "PaadalRadios", "http://streaming.radionomy.com/DJSiran-Tamil-FM", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(14, "ETR Music Channel", "PaadalRadios", "http://174.36.206.197:8052/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(15, "Ex Tamil radio", "PaadalRadios", "http://192.99.8.192:2026/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(16, "Foxx FM", "PaadalRadios", "http://67.228.177.123:9000/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(17, "Geetham New songs radio", "PaadalRadios", "http://www.geethamradio.com:8020/new_hifi.mp3", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(18, "Geetham Old songs 60's radio", "PaadalRadios", "http://www.geethamradio.com:8020/old_hifi.mp3", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(19, "Geetham Party type songs radio", "PaadalRadios", "http://www.geethamradio.com:8020/party_hifi.mp3", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(20, "Geetham Tamil 80's radio", "PaadalRadios", "http://www.geethamradio.com:8020/80s_hifi.mp3", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(21, "Idhayam FM", "PaadalRadios", "http://s8.voscast.com:9284/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(22, "Pulikalinkural radio", "PaadalRadios", "http://174.36.206.197:8090/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(23, "Puradsi FM", "PaadalRadios", "http://puradsifm.net:9994/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(24, "Tamil ClubHouse FM", "PaadalRadios", "http://67.159.5.57:9540/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(25, "TamilKuyil radio", "PaadalRadios", "http://live.tamilkuyilradio.com:8095/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(26, "Tamilmelisai FM", "PaadalRadios", "http://uk4-vn.mixstream.net:8092/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(27, "Thenral World radio", "PaadalRadios", "http://sc1.byinter.net:8010/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(28, "TSR Live Tamil radio", "PaadalRadios", "http://67.159.5.57:9152/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(29, "Vanavil FM", "PaadalRadios", "http://178.250.55.110:8000/", R.drawable.paadalradio_icon));
		paadalRadioListObj.add(new PaadalRadioListBean(30, "Vegam Tamil radio", "PaadalRadios", "http://80.86.81.39:8000/", R.drawable.paadalradio_icon));*/
		
		
	}
	
	private ArrayList<PaadalRadioListBean> paadalRadioListObj = new ArrayList<PaadalRadioListBean>();

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paadalRadioListObj.size();
	}

	@Override
	public PaadalRadioListBean getItem(int index) {
		// TODO Auto-generated method stub
		return paadalRadioListObj.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.singleradiolayout, parent, false);
		}

		PaadalRadioListBean paadalRadioListBeanObj = paadalRadioListObj.get(index);

		TextView time_view_obj = (TextView) view.findViewById(R.id.radioName);
		time_view_obj.setText(paadalRadioListBeanObj.getPaadalRadioName());

		TextView note_view_obj = (TextView) view.findViewById(R.id.radioCaption);
		note_view_obj.setText(paadalRadioListBeanObj.getPaadalRadioCaption());
		
		ImageView imageView = (ImageView) view.findViewById(R.id.radioIcon);
		imageView.setBackgroundResource(paadalRadioListBeanObj.getPaadalRadioIcon());

		return view;
		
	}

}
