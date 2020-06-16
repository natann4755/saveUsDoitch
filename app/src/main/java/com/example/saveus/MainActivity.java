package com.example.saveus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.model.MyLocation;
import com.example.model.User;
import com.example.saveus.alerts_screen.AlertsFragment;
import com.example.saveus.my_places_screen.AddPlaceFragment;
import com.example.saveus.my_places_screen.MyPlacesFragment;
import com.example.saveus.my_places_screen.ViewPagerMapFragment;
import com.example.saveus.my_places_screen.ViewPagerMyPlacesFragment;
import com.example.saveus.utils.ActivityRunning;
import com.example.saveus.utils.FragmentHelper;
import com.example.saveus.wall_screen.EditProfileFragment;
import com.example.saveus.wall_screen.UserProfileFragment;
import com.example.saveus.wall_screen.WallFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        WallFragment.OnWallFragmentListener, MyPlacesFragment.OnMyPlacesFragmentListener,
        ViewPagerMyPlacesFragment.OnViewPagerMyPlacesFragmentListener,
        ViewPagerMapFragment.OnViewPagerMapFragmentListener,
        AddPlaceFragment.OnAddPlaceFragmentListener,
        AlertsFragment.OnAlertsFragmentListener,
        UserProfileFragment.OnUserProfileFragmentListener,
        EditProfileFragment.OnEditProfileFragmentListener {

    private static final int GET_LOCATION = 4533;
    private BottomNavigationView mBottomNavigationViewBNV;
    private FragmentHelper mFragmentHelper;
    private WallFragment mWallFragment;
    private MyPlacesFragment mMyPlacesFragment;
    private AddPlaceFragment mAddPlaceFragment;
    private Toolbar mToolBarTB;
    private View mEmptyViewV;
    private AlertsFragment mAlertsFragment;
    private LinearLayout mUserProfileLL;
    private UserProfileFragment mUserProfileFragment;
    private EditProfileFragment mEditProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        initListeners();
        setBottomNavigationViewIconsColor();
        setBottomNavigationViewListener();
        getLocationPermission();


    }


    @Override
    public void onBackPressed() {

        if (mFragmentHelper.isCurrent(MyPlacesFragment.TAG) ||
                mFragmentHelper.isCurrent(AlertsFragment.TAG) ||
                mFragmentHelper.isCurrent(UserProfileFragment.TAG)) {

            startWallFragment();
            updateNavigationBarState(R.id.MA_action_main_item);


        } else if (mFragmentHelper.isCurrent(WallFragment.TAG)) {

            finish();

        } else {

            if (mFragmentHelper.isCurrent(AddPlaceFragment.TAG) ||
                    mFragmentHelper.isCurrent(EditProfileFragment.TAG)) {

                showToolBar();

            }

            super.onBackPressed();
        }


    }

    private void getLocationPermission() {

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, GET_LOCATION);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, GET_LOCATION);

        } else {

            startWallFragment();
        }
    }


    private void initViews() {

        mBottomNavigationViewBNV = findViewById(R.id.MA_bottom_navigation_BNV);
        mToolBarTB = findViewById(R.id.MA_toolbar_TB);
        mEmptyViewV = findViewById(R.id.MA_empty_view_V);
        mUserProfileLL = findViewById(R.id.MA_user_profile_LL);
        mFragmentHelper = new FragmentHelper(this, new ActivityRunning());
    }


    private void initListeners() {

        mUserProfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onUserProfileClicked();

            }
        });

    }


    private void onUserProfileClicked() {

        if (mUserProfileFragment == null) {
            mUserProfileFragment = UserProfileFragment.newInstance(null);
        }


        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mUserProfileFragment, UserProfileFragment.TAG, null);


    }


    /**
     * set selected and unselected icons if needed
     */
    private void updateNavigationBarState(int itemId) {

        Menu menu = mBottomNavigationViewBNV.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == itemId) {
                item.setChecked(true);
                break;
            }
        }
    }

    private void setBottomNavigationViewListener() {

        mBottomNavigationViewBNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.MA_action_main_item:

                        startWallFragment();
                        showToolBar();

                        break;

                    case R.id.MA_action_my_places_item:

                        startMyPlacesFragment();
                        showToolBar();

                        break;

                    case R.id.MA_action_alerts_item:

                        startAlertsFragment();
                        showToolBar();

                        break;
                }

                return true;
            }
        });


    }


    private void startAlertsFragment() {


        if (mAlertsFragment == null) {
            mAlertsFragment = AlertsFragment.newInstance();
        }


        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mAlertsFragment, AlertsFragment.TAG, null);

    }


    private void startMyPlacesFragment() {


        if (mMyPlacesFragment == null) {
            mMyPlacesFragment = MyPlacesFragment.newInstance();
        }


        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mMyPlacesFragment, MyPlacesFragment.TAG, null);


    }


    private void setBottomNavigationViewIconsColor() {

        mBottomNavigationViewBNV.setItemIconTintList(null);

    }


    private void startWallFragment() {


        if (mWallFragment == null) {
            mWallFragment = WallFragment.newInstance();
        }


        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mWallFragment, WallFragment.TAG, null);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case GET_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startWallFragment();
                }else {

                    finish();

                    // TODO add dialog that we need  location permission
                }


        }

    }


    @Override
    public void onAddPlaceClicked() {


        mAddPlaceFragment = AddPlaceFragment.newInstance(null);

        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mAddPlaceFragment, AddPlaceFragment.TAG, AddPlaceFragment.TAG);


    }


    @Override
    public void onMyPlaseAdapterItemClicked(MyLocation myLocation) {

        mAddPlaceFragment = AddPlaceFragment.newInstance(myLocation);


        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mAddPlaceFragment, AddPlaceFragment.TAG, AddPlaceFragment.TAG);

    }


    public void showToolBar() {

        mToolBarTB.setVisibility(View.VISIBLE);
        mEmptyViewV.setVisibility(View.GONE);

    }


    @Override
    public void hideToolBar() {

        mToolBarTB.setVisibility(View.GONE);
        mEmptyViewV.setVisibility(View.VISIBLE);

    }


    @Override
    public void onCloseAddPlaceClicked() {

        onBackPressed();

    }


    @Override
    public void onSaveUserClicked(User user) {


        mUserProfileFragment = UserProfileFragment.newInstance(user);

        showToolBar();
        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mUserProfileFragment, UserProfileFragment.TAG, null);

    }


    @Override
    public void onEditProfileClicked(User mUser) {


        if (mEditProfileFragment == null) {
            mEditProfileFragment = EditProfileFragment.newInstance(mUser);
        }

        mFragmentHelper.replaceFragment(R.id.MA_content_frame_FL, mEditProfileFragment, EditProfileFragment.TAG, EditProfileFragment.TAG);


    }
}
