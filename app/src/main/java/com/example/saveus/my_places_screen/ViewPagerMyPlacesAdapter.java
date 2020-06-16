package com.example.saveus.my_places_screen;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.MyLocation;
import com.example.saveus.R;

import java.util.List;

public class ViewPagerMyPlacesAdapter extends RecyclerView.Adapter<ViewPagerMyPlacesAdapter.ViewHolder> {


    private final ViewPagerMyPlacesAdapterListener mListener;
    private final List<MyLocation> mMyLocationList;

    public ViewPagerMyPlacesAdapter(ViewPagerMyPlacesAdapterListener listener, List<MyLocation> myLocationList) {

        mListener = listener;
        mMyLocationList = myLocationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_places_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.updateItem(mMyLocationList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onMyPlaseAdapterItemClicked(mMyLocationList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMyLocationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mLocation;
        private final TextView mStartAndEndTimeTV;
        private final TextView mSumTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mLocation = itemView.findViewById(R.id.MPI_location_TV);
            mStartAndEndTimeTV = itemView.findViewById(R.id.MPI_start_end_TV);
            mSumTime = itemView.findViewById(R.id.MPI_sum_time_TV);


        }

        @SuppressLint("SetTextI18n")
        void updateItem(MyLocation myLocation) {


            mLocation.setText(myLocation.getLocation());
            mStartAndEndTimeTV.setText(myLocation.getStartTime() + "-" + myLocation.getEndTime());
            mSumTime.setText(myLocation.getSumTime());


        }
    }


    public interface ViewPagerMyPlacesAdapterListener {


        void onMyPlaseAdapterItemClicked(MyLocation myLocation);
    }
}
