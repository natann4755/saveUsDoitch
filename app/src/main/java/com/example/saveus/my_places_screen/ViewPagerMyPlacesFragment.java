package com.example.saveus.my_places_screen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.model.MyLocation;
import com.example.saveus.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ViewPagerMyPlacesFragment extends Fragment implements ViewPagerMyPlacesAdapter.ViewPagerMyPlacesAdapterListener {

    private static final String IVRIT = "עברית";
    private OnViewPagerMyPlacesFragmentListener mListener;
    private RecyclerView mRecylerViewRV;
    private ViewPagerMyPlacesAdapter mViewPagerMyPlacesAdapter;
    private LinearLayout mAddPlaceBtnLL;
    private ImageView mAddPlaceIconIV;
    private TextView mAddPlaceTextTV;

    public ViewPagerMyPlacesFragment() {
        // Required empty public constructor
    }

    public static ViewPagerMyPlacesFragment newInstance() {
        ViewPagerMyPlacesFragment fragment = new ViewPagerMyPlacesFragment();
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
        return inflater.inflate(R.layout.fragment_view_pager_my_places, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        serRecyclerView();
        setAddPlaceButton();
        ifLanguageIsLtrRotateBtn();
    }



    private void initViews() {

        mRecylerViewRV = Objects.requireNonNull(getView()).findViewById(R.id.VPMPF_recycler_view_RV);
        mAddPlaceBtnLL = Objects.requireNonNull(getView()).findViewById(R.id.VPMPF_add_place_LL);
        mAddPlaceIconIV = Objects.requireNonNull(getView()).findViewById(R.id.VPMPF_add_place_icon_IV);
        mAddPlaceTextTV = Objects.requireNonNull(getView()).findViewById(R.id.VPMPF_add_place_TV);


    }


    private void serRecyclerView() {

        List<MyLocation> myLocationList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            MyLocation myLocation = new MyLocation();
            myLocation.setLocation("יפו 127 ירושלים");
            myLocation.setStartTime("10:45");
            myLocation.setEndTime("13:45");
            myLocation.setSumTime("00:03:13");
            myLocation.setLatitude(31.786905);
            myLocation.setLongitude(35.209289);
            myLocation.setDate("2020-3-3");

            myLocationList.add(myLocation);

        }

        mRecylerViewRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewPagerMyPlacesAdapter = new ViewPagerMyPlacesAdapter(this, myLocationList);
        mRecylerViewRV.setAdapter(mViewPagerMyPlacesAdapter);
    }




    private void setAddPlaceButton() {


        mAddPlaceBtnLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onAddPlaceClicked();
            }
        });
    }



    private void ifLanguageIsLtrRotateBtn() {

        if (!Locale.getDefault().getDisplayLanguage().equals(IVRIT)){

            mAddPlaceBtnLL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.rigth_corner_layout_ltr));
        }else {

            mAddPlaceBtnLL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.rigth_corner_layout));

        }

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnViewPagerMyPlacesFragmentListener) {
            mListener = (OnViewPagerMyPlacesFragmentListener) context;
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

    
    @Override
    public void onMyPlaseAdapterItemClicked(MyLocation myLocation) {
        
        mListener.onMyPlaseAdapterItemClicked(myLocation);
    }


    public interface OnViewPagerMyPlacesFragmentListener {

        void onAddPlaceClicked();

        void onMyPlaseAdapterItemClicked(MyLocation myLocation);
    }
}
