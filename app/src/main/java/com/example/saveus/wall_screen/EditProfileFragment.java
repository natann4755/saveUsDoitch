package com.example.saveus.wall_screen;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.model.User;
import com.example.saveus.R;

import java.io.Serializable;
import java.util.Objects;


public class EditProfileFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = EditProfileFragment.class.getSimpleName();
    private OnEditProfileFragmentListener mListener;
    private RelativeLayout mBirthDateRL;
    private Spinner mDropdownSpinnerSP;
    private TextView mBirthDateTV;
    private LinearLayout mCloseEditLL;
    private TextView mSaveEditTV;
    private EditText mNameET;
    private EditText mMailET;
    private EditText mPhoneNUmberET;
    private CheckBox mCheckBoxCB;
    private static final String USER = "USER";
    private User mUser;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance(User user) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListeners();
        setLanguageSpinner();
        setAllFileds();
    }


    private void initViews() {

        mBirthDateRL = Objects.requireNonNull(getView()).findViewById(R.id.EPF_birth_time_RL);
        mBirthDateTV = Objects.requireNonNull(getView()).findViewById(R.id.EPF_birth_date_TV);
        mDropdownSpinnerSP = Objects.requireNonNull(getView()).findViewById(R.id.EPF_spinner_SP);
        mCloseEditLL = Objects.requireNonNull(getView()).findViewById(R.id.EPF_close_edit_LL);
        mSaveEditTV = Objects.requireNonNull(getView()).findViewById(R.id.EPF_save_TV);
        mNameET = Objects.requireNonNull(getView()).findViewById(R.id.EPF_name_ET);
        mMailET = Objects.requireNonNull(getView()).findViewById(R.id.EPF_mail_ET);
        mPhoneNUmberET = Objects.requireNonNull(getView()).findViewById(R.id.EPF_phone_number_ET);
        mCheckBoxCB = Objects.requireNonNull(getView()).findViewById(R.id.EPF_checkbok_CB);

    }


    private void initListeners() {

        mBirthDateRL.setOnClickListener(this);
        mCloseEditLL.setOnClickListener(this);
        mSaveEditTV.setOnClickListener(this);

    }


    private void setLanguageSpinner() {


        String[] items = new String[]{"עברית", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, items);
        mDropdownSpinnerSP.setAdapter(adapter);
    }


    private void setAllFileds() {

        mNameET.setText(mUser.getName());
        mMailET.setText(mUser.getEmail());
        mPhoneNUmberET.setText(mUser.getPhone());
        mBirthDateTV.setText(mUser.getDateOfBirth());
        mCheckBoxCB.setChecked(mUser.isRegisteredForNotifications());

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.hideToolBar();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditProfileFragmentListener) {
            mListener = (OnEditProfileFragmentListener) context;
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

            case R.id.EPF_birth_time_RL:

                startCalenderDialog();

                break;


            case R.id.EPF_close_edit_LL:

                mListener.onCloseAddPlaceClicked();


                break;


            case R.id.EPF_save_TV:

                saveUserProfile();

                break;

        }


    }


    private void saveUserProfile() {


        User user = new User();
//        user.setName(mNameET.getText().toString());
//        user.setMail(mMailET.getText().toString());
//        user.setPhoneUmber(mPhoneNUmberET.getText().toString());
//        user.setBirthday(mBirthDateTV.getText().toString());
//        user.setLanguage(mDropdownSpinnerSP.getSelectedItem().toString());
//        user.setGetNotification(mCheckBoxCB.isChecked());

        mListener.onSaveUserClicked(user);


    }


    private void startCalenderDialog() {

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(Objects.requireNonNull(getContext()), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mBirthDateTV.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

            }
        }, 1987, 10, 11);
        dpd.show();

    }


    public interface OnEditProfileFragmentListener {

        void hideToolBar();

        void onCloseAddPlaceClicked();

        void onSaveUserClicked(User user);
    }
}
