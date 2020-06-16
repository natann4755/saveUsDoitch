package com.example.saveus.my_places_screen;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.model.MyLocation;
import com.example.saveus.R;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;


public class AddPlaceFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = AddPlaceFragment.class.getSimpleName();
    private static final String MY_LOCATION = "MY_LOCATION";
    private OnAddPlaceFragmentListener mListener;
    private RelativeLayout mAddDateRL;
    private LinearLayout mCloseReportLL;
    private TextView mDeleteReportTV;
    private LinearLayout mEndTimeLL;
    private TextView mSaveReportTV;
    private LinearLayout mStartTimeLL;
    private TextView mDateTV;
    private TextView mStartTimeTV;
    private TextView mEndTimeTV;
    private MyLocation mMyLocation;
    private TextView mTitleTV;
    private EditText mLocationET;


    public AddPlaceFragment() {

    }


    public static AddPlaceFragment newInstance(MyLocation myLocation) {
        AddPlaceFragment fragment = new AddPlaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(MY_LOCATION, myLocation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mMyLocation = (MyLocation) getArguments().getSerializable(MY_LOCATION);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_place, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initListeners();

        if (mMyLocation != null) {

            setLocationForEdit();
        }
    }


    private void setLocationForEdit() {


        mTitleTV.setText(getResources().getString(R.string.edit_location));

        mLocationET.setText(mMyLocation.getLocation());
        mStartTimeTV.setText(mMyLocation.getStartTime());
        mEndTimeTV.setText(mMyLocation.getEndTime());
        mDateTV.setText(mMyLocation.getDate());


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.hideToolBar();


    }


    private void initViews() {

        mTitleTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_title_TV);
        mLocationET = Objects.requireNonNull(getView()).findViewById(R.id.APF_location_ET);
        mAddDateRL = Objects.requireNonNull(getView()).findViewById(R.id.APF_add_date_RL);
        mCloseReportLL = Objects.requireNonNull(getView()).findViewById(R.id.APF_close_report_LL);
        mDeleteReportTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_delete_report_TV);
        mEndTimeLL = Objects.requireNonNull(getView()).findViewById(R.id.APF_end_time_LL);
        mSaveReportTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_save_report_TV);
        mStartTimeLL = Objects.requireNonNull(getView()).findViewById(R.id.APF_start_time_LL);
        mDateTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_date_TV);
        mStartTimeTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_start_time_TV);
        mEndTimeTV = Objects.requireNonNull(getView()).findViewById(R.id.APF_end_time_TV);


    }


    private void initListeners() {

        mAddDateRL.setOnClickListener(this);
        mCloseReportLL.setOnClickListener(this);
        mDeleteReportTV.setOnClickListener(this);
        mEndTimeLL.setOnClickListener(this);
        mSaveReportTV.setOnClickListener(this);
        mStartTimeLL.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.APF_add_date_RL:

                startCalenderDialog();

                break;

            case R.id.APF_close_report_LL:

                setViewClickedAnimation(mCloseReportLL);
                mListener.onCloseAddPlaceClicked();

                break;

            case R.id.APF_delete_report_TV:


                break;

            case R.id.APF_end_time_LL:

                endTimeDialog(mEndTimeTV);

                break;

            case R.id.APF_save_report_TV:


                break;

            case R.id.APF_start_time_LL:

                startTimeDialog(mStartTimeTV);

                break;


        }

    }


    private void setViewClickedAnimation(View view) {

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        view.startAnimation(animation);

    }


    private void startTimeDialog(final TextView textView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, 4, 24, true);

        timePickerDialog.show();


    }


    private void endTimeDialog(final TextView textView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, 4, 24, true);

        timePickerDialog.show();


    }


    private void startCalenderDialog() {

        // Process to get Current Date
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(Objects.requireNonNull(getContext()), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                mDateTV.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK));
        dpd.show();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddPlaceFragmentListener) {
            mListener = (OnAddPlaceFragmentListener) context;
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


    public interface OnAddPlaceFragmentListener {

        void onCloseAddPlaceClicked();

        void hideToolBar();
    }
}
