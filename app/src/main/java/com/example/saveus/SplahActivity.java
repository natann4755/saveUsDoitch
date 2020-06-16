package com.example.saveus;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.example.saveus.slide_screens.SlideActivity;
import com.example.saveus.user_manager.UserManager;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.Objects;

public class SplahActivity extends AppCompatActivity {

    public static final String DEVICE_ID = "DEVICE_ID";
    private ProgressBar mSplashProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

        initView();
        setTheProgressBar();
        getDeviceId();
    }


    private void initView() {

        mSplashProgressBar = findViewById(R.id.SA_progressbar_PB);
    }


    /**
     * set splash progress bar
     */
    private void setTheProgressBar() {

        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mSplashProgressBar, "progress", 1000);
//        progressAnimator.setDuration(2000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

//        progressAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                startNextActivity();
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });


    }



    private void getDeviceId() {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                AdvertisingIdClient.Info adInfo = null;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(SplahActivity.this);

                } catch (IOException e) {
                    // Unrecoverable error connecting to Google Play services (e.g.,
                    // the old version of the service doesn't support getting AdvertisingId).

                } catch (GooglePlayServicesNotAvailableException e) {
                    // Google Play services is not available entirely.
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
                final String id = Objects.requireNonNull(adInfo).getId();
                final boolean isLAT = adInfo.isLimitAdTrackingEnabled();

                startNextActivity(id);
//        String android_id = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);


            }
        });


        thread.start();

    }




    private void startNextActivity(String id) {

        if (UserManager.getFirstTimeInsideTheApp(this)) {

            Intent intent = new Intent(this, SlideActivity.class);
            intent.putExtra(DEVICE_ID, id);
            startActivity(intent);
            finish();

        } else {

            if (UserManager.getToken(this) != null) {

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {

                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(DEVICE_ID, id);
                startActivity(intent);
                finish();
            }

        }


    }

}
