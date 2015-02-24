package com.r2apps.sfa.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.r2apps.sfa.R;
import com.r2apps.sfa.UiUpdator;
import com.r2apps.sfa.adapters.ProductListAdapter;
import com.r2apps.sfa.dao.Product;
import com.r2apps.sfa.http.RestResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravindra.kambale on 2/24/2015.
 */
public class Dashboard extends Fragment implements UiUpdator {

    private View rootView = null;
    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private ListView lstProducts;
    private ProductListAdapter productListAdapter;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dashboard, container, false);
        pie = (PieChart)rootView.findViewById(R.id.mySimplePieChart);
        lstProducts = (ListView)rootView.findViewById(R.id.list_products);
        new Handler().post(
                new Runnable() {
                    public void run() {
                        if(getActivity() != null){
                            getProducts();
                            drawPieChart();
                        }
                    }
                });

        setRetainInstance(true);
        return rootView;
    }
    @Override
    public void updateUI(int requestCode, RestResponse.StatusCode responseCode, Object data) {

    }

    private void getProducts() {
        List<Product> purchasedProducts = new ArrayList<Product>();
        Product product = new Product();
        product.name = "Licel";
        product.price = "100";
        purchasedProducts.add(product);
        productListAdapter = new ProductListAdapter(getActivity(), R.layout.only_product_row, purchasedProducts);
        lstProducts.setAdapter(productListAdapter);
    }
    public void drawPieChart(){
        s1 = new Segment("60 %", 20);
        s2 = new Segment("40 %", 5);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getActivity().getApplicationContext(), R.xml.pie_segment_formatter4);

        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getActivity().getApplicationContext(), R.xml.pie_segment_formatter2);

        sf2.getFillPaint().setMaskFilter(emf);



        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.WHITE);
        pie.getRenderer(PieRenderer.class).setDonutSize(0.3f, PieRenderer.DonutMode.PERCENT);
        pie.redraw();
    }
}
