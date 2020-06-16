package com.example.saveus.wall_screen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.model.LoginResponse;
import com.example.model.Result;
import com.example.model.User;
import com.example.saveus.R;
import com.example.saveus.server.RequestManager;
import com.example.saveus.user_manager.UserManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserProfileFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = UserProfileFragment.class.getSimpleName();
    private static final String USER = "USER";
    private OnUserProfileFragmentListener mListener;
    private LinearLayout mEditBtnLL;
    private User mUser;
    private TextView mNameTV;
    private TextView mMailTV;
    private TextView mPhoneNumberTV;
    private TextView mBirthdayTV;
    private TextView mLanguaghTV;
    private TextView mGetNotificationTV;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    public static UserProfileFragment newInstance(User user) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(USER);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListeners();
        getUserFromServer();
    }


    private void getUserFromServer() {


        RequestManager.getUser(UserManager.getToken(getContext())).subscribe(new Observer<Result<LoginResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<LoginResponse> loginResponseResult) {

                setUserDetails(loginResponseResult.getData().getUser());
                mUser = loginResponseResult.getData().getUser();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }


    private void initViews() {


        mEditBtnLL = getView().findViewById(R.id.UPF_edit_btn_LL);
        mNameTV = getView().findViewById(R.id.UPF_name_TV);
        mMailTV = getView().findViewById(R.id.UPF_mail_TV);
        mPhoneNumberTV = getView().findViewById(R.id.UPF_phone_number_TV);
        mBirthdayTV = getView().findViewById(R.id.UPF_birthday_TV);
        mLanguaghTV = getView().findViewById(R.id.UPF_languagh_TV);
        mGetNotificationTV = getView().findViewById(R.id.UPF_get_notification_TV);


    }


    private void initListeners() {

        mEditBtnLL.setOnClickListener(this);
    }


    private void setUserDetails(User user) {


        mNameTV.setText(user.getName());
        mMailTV.setText(user.getEmail());
        mPhoneNumberTV.setText(user.getPhone());
        mBirthdayTV.setText(user.getDateOfBirth());
        if (user.isRegisteredForNotifications()) {

            mGetNotificationTV.setText(getResources().getString(R.string.yes));
        } else {
            mGetNotificationTV.setText(getResources().getString(R.string.no));

        }

        if (user.getLanguage() != null){

            mLanguaghTV.setText(user.getLanguage());
        }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserProfileFragmentListener) {
            mListener = (OnUserProfileFragmentListener) context;
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
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.UPF_edit_btn_LL:

                mListener.onEditProfileClicked(mUser);

                break;


        }

    }


    public interface OnUserProfileFragmentListener {

        void onEditProfileClicked(User mUser);
    }
}
