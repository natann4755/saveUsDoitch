package com.example.saveus.my_places_screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.model.MyLocation;
import com.example.saveus.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mymarkerview;
    private final Context mContext;

    CustomInfoViewAdapter(Context context) {
        mymarkerview = ((Activity)context).getLayoutInflater().inflate(R.layout.map_custom_infowindow, null);
        mContext = context;
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }

    @SuppressLint("SetTextI18n")
    public View getInfoContents(Marker marker) {

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 87, mContext.getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 166, mContext.getResources().getDisplayMetrics());

        mymarkerview.setLayoutParams(new RelativeLayout.LayoutParams(width, height));

        mymarkerview.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_corner_map_info));

        TextView addressText = mymarkerview.findViewById(R.id.CIVA_adrress_TV);
        TextView dateText = mymarkerview.findViewById(R.id.CIVA_date_TV);
        TextView startAndEndText = mymarkerview.findViewById(R.id.CIVA_start_and_end_TV);
        TextView sumTimeText = mymarkerview.findViewById(R.id.CIVA_sum_time_TV);


        MyLocation myLocation = (MyLocation) marker.getTag();

        addressText.setText(myLocation.getLocation());
        dateText.setText(myLocation.getDate());
        startAndEndText.setText(myLocation.getStartTime() + " - " + myLocation.getEndTime());
        sumTimeText.setText(myLocation.getSumTime());

        return mymarkerview;
    }




}
