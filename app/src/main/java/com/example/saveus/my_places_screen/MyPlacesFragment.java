package com.example.saveus.my_places_screen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saveus.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class MyPlacesFragment extends Fragment {


    public static final String TAG = MyPlacesFragment.class.getSimpleName();
    private OnMyPlacesFragmentListener mListener;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyPlacesPagerAdapter mPagerAdapter;

    public MyPlacesFragment() {
        // Required empty public constructor
    }

    public static MyPlacesFragment newInstance() {
        MyPlacesFragment fragment = new MyPlacesFragment();
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
        return inflater.inflate(R.layout.fragment_my_places, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        setViewPager();
    }


    private void initViews() {

        mTabLayout = Objects.requireNonNull(getView()).findViewById(R.id.MPF_tabLayout_TL);
        mViewPager = Objects.requireNonNull(getView()).findViewById(R.id.MPF_viewpager_VP);

    }


    private void setViewPager() {


        mPagerAdapter = new MyPlacesPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyPlacesFragmentListener) {
            mListener = (OnMyPlacesFragmentListener) context;
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


    public interface OnMyPlacesFragmentListener {


    }


    private class MyPlacesPagerAdapter extends FragmentPagerAdapter {


        public MyPlacesPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:

                    return ViewPagerMyPlacesFragment.newInstance();

                case 1:

                    return ViewPagerMapFragment.newInstance();


                default:

                    return ViewPagerMyPlacesFragment.newInstance();

            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        /**
         * set page title in tab layout
         *
         * @param position int
         * @return CharSequence, title
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.my_places);
                case 1:
                    return getString(R.string.on_map);
                default:
                    return getString(R.string.my_places);
            }
        }

    }
}
