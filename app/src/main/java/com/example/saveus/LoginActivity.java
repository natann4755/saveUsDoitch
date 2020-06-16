package com.example.saveus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Login;
import com.example.model.LoginResponse;
import com.example.model.Result;
import com.example.model.User;
import com.example.saveus.server.RequestManager;
import com.example.saveus.user_manager.UserManager;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.example.saveus.SplahActivity.DEVICE_ID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private TextView mSkipBtnTV;
    private TextView mLoginBtnTV;
    private TextView mBylawsBtnTV;
    private TextView mTermsBtnTV;
    private String mDeviceId;
    private EditText mNameEditTextET;
    private EditText mMailEditTextET;
    private EditText mPhoneNumberEditTextET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();
        initListeners();
//        getDeviceId();
    }


    private void initViews() {


        mSkipBtnTV = findViewById(R.id.LA_skip_btn_TV);
        mLoginBtnTV = findViewById(R.id.LA_login_btn_TV);
        mBylawsBtnTV = findViewById(R.id.LA_bylaws_TV);
        mTermsBtnTV = findViewById(R.id.LA_terms_TV);
        mDeviceId = getIntent().getStringExtra(DEVICE_ID);
        mNameEditTextET = findViewById(R.id.LA_name_ET);
        mMailEditTextET = findViewById(R.id.LA_mail_ET);
        mPhoneNumberEditTextET = findViewById(R.id.LA_phone_number_ET);

    }


    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {

        mSkipBtnTV.setOnClickListener(this);
        mLoginBtnTV.setOnClickListener(this);
        mBylawsBtnTV.setOnClickListener(this);
        mTermsBtnTV.setOnClickListener(this);

        mNameEditTextET.setOnTouchListener(this);
        mMailEditTextET.setOnTouchListener(this);
        mPhoneNumberEditTextET.setOnTouchListener(this);

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {


            case R.id.LA_skip_btn_TV:

                setViewClickedAnimation(view);
                onSkipBtnClicked();

                break;


            case R.id.LA_login_btn_TV:

                setViewClickedAnimation(view);
                onLoginBtnClicked();

                break;


            case R.id.LA_bylaws_TV:

                openDialog();

                break;


            case R.id.LA_terms_TV:

                openDialog();

                break;

        }

    }





    private void openDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_view, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }






    private void onSkipBtnClicked() {

        User user = new User();
        user.setDeviceId(mDeviceId);
        
        RequestManager.login(user).subscribe(new Observer<Result<LoginResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<LoginResponse> loginResponseResult) {

                UserManager.setToken(loginResponseResult.getData().getToken(), LoginActivity.this);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });




    }


    private void onLoginBtnClicked() {

        if (checkIfAllFieldsAreFilled(new EditText[]{mNameEditTextET, mMailEditTextET, mPhoneNumberEditTextET})) {

            if (isEmailValid(mMailEditTextET.getText().toString())) {

                User user = new User();
                user.setName(mNameEditTextET.getText().toString());
                user.setEmail(mMailEditTextET.getText().toString());
                user.setPhone(mPhoneNumberEditTextET.getText().toString());
                user.setDeviceId(mDeviceId);

                RequestManager.login(user).subscribe(new Observer<Result<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<LoginResponse> loginResponseResult) {

                        String s =  loginResponseResult.getData().getToken()+"";
                        Log.e("tag",s);getApplicationContext();
                        UserManager.setToken(loginResponseResult.getData().getToken(), LoginActivity.this);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }else {

                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show();
            }

        }

    }




    /**
     * check if all fields are filled and if not change background color
     *
     * @param editTexts EditText[], list of all fields edit text
     * @return boolean allFilled
     */
    private boolean checkIfAllFieldsAreFilled(EditText[] editTexts) {

        boolean allFieldsAreFilled = true;

        for (EditText currentField : editTexts) {

            if (currentField.getText().toString().length() <= 0) {

                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.add_btn_color));
                ViewCompat.setBackgroundTintList(currentField, colorStateList);
                allFieldsAreFilled = false;
            }

        }

        return allFieldsAreFilled;

    }






    /**
     * method is used for checking valid email id format.
     *
     * @param email String
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }





    private void setViewClickedAnimation(View view) {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);

    }



    /**
     * on touch on edit text change the edit text background
     *
     * @param view        View
     * @param motionEvent MotionEvent
     * @return boolean
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        EditText editText = (EditText) view;

        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.loginEditTextColor));
        ViewCompat.setBackgroundTintList(editText, colorStateList);


        return false;

    }




}
