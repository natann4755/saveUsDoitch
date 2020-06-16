package com.example.saveus.my_places_screen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.model.MyLocation;
import com.example.saveus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;
import java.util.Objects;


public class ViewPagerMapFragment extends Fragment {


    private static final String IVRIT = "עברית";
    private OnViewPagerMapFragmentListener mListener;

    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationManager mLocationManager;
    private Location gps_loc;
    private Location network_loc;
    private Location final_loc;
    private double latitude;
    private double longitude;
    private LinearLayout mAddPlaceLL;

    public ViewPagerMapFragment() {
        // Required empty public constructor
    }


    public static ViewPagerMapFragment newInstance() {
        ViewPagerMapFragment fragment = new ViewPagerMapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_map, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListeners();
        getLocation(savedInstanceState);
        ifLanguageIsLtrRotateBtn();
    }

   


    private void getLocation(Bundle savedInstanceState) {



        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        } else {


            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }


            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {


                    try {

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        gps_loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        network_loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (gps_loc != null) {
                        final_loc = gps_loc;
                        latitude = final_loc.getLatitude();
                        longitude = final_loc.getLongitude();
                    } else if (network_loc != null) {
                        final_loc = network_loc;
                        latitude = final_loc.getLatitude();
                        longitude = final_loc.getLongitude();
                    } else {
                        latitude = 0.0;
                        longitude = 0.0;
                    }


                    googleMap = mMap;
                    googleMap.setMyLocationEnabled(true);
                    //To add marker
                    LatLng sydney = new LatLng(31.786905, 35.209289);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(sydney)
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.my_marker_icon))
                            .title("יפו 127 ירושלים")
                            .snippet("10:45-13:45");


                    MyLocation myLocation = new MyLocation();
                    myLocation.setDate("19/03/2020");
                    myLocation.setStartTime("10:45");
                    myLocation.setEndTime("13:45");
                    myLocation.setSumTime("00:03:13");
                    myLocation.setLocation("יפו 127 ירושלים");

                    googleMap.setInfoWindowAdapter(new CustomInfoViewAdapter(getContext()));

                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(myLocation);
//                    marker.showInfoWindow();


                    // For zooming functionality
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });
        }

    }





    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private void ifLanguageIsLtrRotateBtn() {

        if (!Locale.getDefault().getDisplayLanguage().equals(IVRIT)){

            mAddPlaceLL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.rigth_corner_layout_ltr));
        }else {

            mAddPlaceLL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.rigth_corner_layout));

        }

    }



    private void initViews() {

        mMapView = (MapView) Objects.requireNonNull(getView()).findViewById(R.id.VPMF_mapView_MV);
        mAddPlaceLL = Objects.requireNonNull(getView()).findViewById(R.id.VPMF_add_place_LL);
        mLocationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);


    }




    private void initListeners() {
        
        mAddPlaceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddPlaceClicked();
            }
        });
        
    }
    
    
    


    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMapView.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMapView.onPause();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMapView != null) {

            mMapView.onDestroy();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mMapView != null) {

            mMapView.onLowMemory();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewPagerMapFragmentListener) {
            mListener = (OnViewPagerMapFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnViewPagerMapFragmentListener {

        void onAddPlaceClicked();
    }
}
