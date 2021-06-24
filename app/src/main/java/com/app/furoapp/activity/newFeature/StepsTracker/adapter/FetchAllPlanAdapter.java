package com.app.furoapp.activity.newFeature.StepsTracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.Datum;

import java.util.List;

public class FetchAllPlanAdapter extends RecyclerView.Adapter<FetchAllPlanAdapter.MyViewHolder> {
    Context context;
    List<Datum> datumList;
    public TimeSlotClickCallBack timeSlotClickCallBack;
    private int row_index = -1;
    int count = 0;
    public int getPosition;


    public FetchAllPlanAdapter(Context context, List<Datum> datumList, TimeSlotClickCallBack timeSlotClickCallBack) {
        this.context = context;
        this.datumList = datumList;
        this.timeSlotClickCallBack = timeSlotClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fetch_all_slot, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Datum datum = datumList.get(position);
        getPosition = position + 1;
        holder.tvSlotCount.setText("Slot " + getPosition + " - ");
        holder.tvSlotTime.setText("" + datum.getTimeslot());

        holder.llTimeSlot.setOnClickListener(v -> {
            timeSlotClickCallBack.timeSlotClick(datum.getId(), datum.getTimeslot());
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
//            clicked=true;
            //holder.tvAge.setTextColor(Color.parseColor("#19CFE6"));
        } else {
//           clicked=false;
            //holder.tvAge.setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        if (datumList != null && datumList.size() > 0) {
            return datumList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSlotCount, tvSlotTime;
        public LinearLayout llTimeSlot, llTimeDeleteSlot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlotCount = itemView.findViewById(R.id.tvSlotCount);
            tvSlotTime = itemView.findViewById(R.id.tvSlotTime);
            llTimeSlot = itemView.findViewById(R.id.llTimeSlot);
            llTimeDeleteSlot = itemView.findViewById(R.id.llTimeDeleteSlot);
        }
    }

    public interface TimeSlotClickCallBack {
        void timeSlotClick(Integer id, String timeslot);
    }
}
