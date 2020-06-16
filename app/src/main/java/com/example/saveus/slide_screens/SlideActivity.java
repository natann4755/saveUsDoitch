package com.example.saveus.slide_screens;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.saveus.LoginActivity;
import com.example.saveus.R;
import com.example.saveus.user_manager.UserManager;
import com.google.android.material.tabs.TabLayout;

import static com.example.saveus.SplahActivity.DEVICE_ID;


public class SlideActivity extends FragmentActivity implements OnBoardingFragment1.OnFragmentInteractionListener,
        OnBoardingFragment2.OnFragmentInteractionListener,
        OnBoardingFragment3.OnFragmentInteractionListener {


    private ViewPager mViewPager;
    private MyPagerAdapter mSlideScreensAdapter;
    private String mDeviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        initViews();
        setTheIndicator();
        initOnPageChangeListener();
    }


    /**
     * find views
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {

        mViewPager = findViewById(R.id.SA_viewPager_VP);
        mSlideScreensAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mDeviceId = getIntent().getStringExtra(DEVICE_ID);

    }





    /**
     * set the slide screens indicator
     */
    private void setTheIndicator() {

        mViewPager.setAdapter(mSlideScreensAdapter);
        TabLayout tabLayout = findViewById(R.id.SA_tab_layout_TL);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        tabLayout.setupWithViewPager(mViewPager, true);

//        mViewPager.setCurrentItem(2);



    }






    /**
     * listener to swipe in last page of the view pager
     */
    private void initOnPageChangeListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private boolean isLastPageSwiped;
            private int counterPageScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {

                if (position == 2 && positionOffset == 0 && !isLastPageSwiped) {
                    if (counterPageScroll != 0) {
                        isLastPageSwiped = true;
                        startNextActivity();

                    }
                    counterPageScroll++;
                } else {
                    counterPageScroll = 0;
                }

            }





            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    private void startNextActivity() {

        // TODO if user loged in allrady move to main activity

        UserManager.setFirstTimeInsideTheApp(false, this);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(DEVICE_ID, mDeviceId);
        startActivity(intent);
        finish();


    }


    /**
     * class adapter for the slide screens
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * get the item of the screen position to call the slide screens
         *
         * @param position int, position of the view pager
         * @return slide screen fragment
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return OnBoardingFragment1.newInstance();
                case 1:
                    return OnBoardingFragment2.newInstance();
                case 2:
                    return OnBoardingFragment3.newInstance();

                default:
                    return OnBoardingFragment1.newInstance();
            }
        }


        @Override
        public int getCount() {
            return 3;
        }
    }
}
