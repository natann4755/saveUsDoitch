package com.example.saveus.wall_screen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class WallFragment extends Fragment {


    public static final String TAG = WallFragment.class.getSimpleName();
    private OnWallFragmentListener mListener;
    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationManager mLocationManager;
    private Location gps_loc;
    private Location network_loc;
    private Location final_loc;
    private double latitude;
    private double longitude;
    private RelativeLayout mStartBtnRL;
    private RelativeLayout mStopBtnRL;
    private Chronometer mChronometerTimerCM;
    private String userCountry;
    private String userAddress;
    private boolean isFirstTime = true;
    private View mRootView;

    public WallFragment() {
        // Required empty public constructor
    }

    public static WallFragment newInstance() {
        WallFragment fragment = new WallFragment();
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

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_wall, container, false);
        }
        return mRootView;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (mMapView != null) {

            isFirstTime = false;
        }

        initViews();
        initListeners();
        getLocation(savedInstanceState);


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
                    LatLng sydney = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Title").snippet("Marker Description"));
                    // For zooming functionality


                    addOtherUsersMarkers(googleMap);
                    addMyMarkers(googleMap);

                    if (isFirstTime) {

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(13).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    } else {

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(13).build();
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }


                }
            });
        }

    }


    private void addMyMarkers(GoogleMap googleMap) {

        // latitude and longitude
        double latitude = 31.806253;
        double longitude = 35.103138;

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
        marker.icon(bitmapDescriptorFromVector(getContext(), R.drawable.my_marker_icon));
        googleMap.addMarker(marker);


    }


    private void addOtherUsersMarkers(GoogleMap googleMap) {

        // latitude and longitude
        double latitude = 31.803964;
        double longitude = 35.100004;

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
        marker.icon(bitmapDescriptorFromVector(getContext(), R.drawable.others_marker_icon));
        googleMap.addMarker(marker);


    }




    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }




    private void initViews() {

        mMapView = (MapView) Objects.requireNonNull(getView()).findViewById(R.id.mapView);
        mLocationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);
        mStartBtnRL = getView().findViewById(R.id.WF_start_btn_RL);
        mStopBtnRL = getView().findViewById(R.id.WF_stop_btn_RL);
        mChronometerTimerCM = (Chronometer) getView().findViewById(R.id.WF_chronometer_timer_CM);

    }


    private void initListeners() {

        mStartBtnRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStartBtnRL.setVisibility(View.GONE);
                mStopBtnRL.setVisibility(View.VISIBLE);

                mChronometerTimerCM.setBase(SystemClock.elapsedRealtime());
                setStopBtnTimer();

            }
        });

        mStopBtnRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showElapsedTime();
                mChronometerTimerCM.setBase(SystemClock.elapsedRealtime());

                mStopBtnRL.setVisibility(View.GONE);
                mStartBtnRL.setVisibility(View.VISIBLE);


            }
        });
    }


    private void showElapsedTime() {

        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometerTimerCM.getBase();

        try {

            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                userCountry = addresses.get(0).getCountryName();
                userAddress = addresses.get(0).getAddressLine(0);
            } else {
                userCountry = "Unknown";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Toast.makeText(getContext(), "זמן: " + elapsedMillis + "  milliseconds\n " + "מיקום: " + userCountry + ", " + userAddress,
                Toast.LENGTH_LONG).show();
    }


    private void setStopBtnTimer() {

        mChronometerTimerCM.start();

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
        if (context instanceof OnWallFragmentListener) {
            mListener = (OnWallFragmentListener) context;
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


    public interface OnWallFragmentListener {

    }
}
