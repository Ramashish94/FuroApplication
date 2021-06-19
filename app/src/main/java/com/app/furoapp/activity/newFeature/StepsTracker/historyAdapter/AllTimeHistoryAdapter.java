package com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllTimeHistoryAdapter extends RecyclerView.Adapter<AllTimeHistoryAdapter.MyViewHolder> {
    Context context;
    List<AllTimeCounter> allTimeCounterList;
    private int row_index = -1;
    public Date date;


    public AllTimeHistoryAdapter(Context context, List<AllTimeCounter> allTimeCounterList) {
        this.context = context;
        this.allTimeCounterList = allTimeCounterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AllTimeCounter allTimeCounter = allTimeCounterList.get(position);
        holder.tvTotSteps.setText("" + allTimeCounter.getCountSteps());
        holder.tvDailyAverage.setText("" + allTimeCounter.getDailyAverage() + " m");
        holder.tvTime.setText("" + allTimeCounter.getTime() + " Minutes");
        holder.tvCalories.setText("" + allTimeCounter.getCalories() + " Cal");

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(allTimeCounter.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);
        holder.tvDateWithDays.setText(getDate);

        //holder.tvDateWithDays.setText("" + allTimeCounter.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        if (allTimeCounterList != null && allTimeCounterList.size() > 0) {
            return allTimeCounterList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTotSteps, tvDailyAverage, tvTime, tvCalories, tvDateWithDays;
        public LinearLayout llTimeSlot, llTimeDeleteSlot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotSteps = itemView.findViewById(R.id.tvTotSteps);
            tvDailyAverage = itemView.findViewById(R.id.tvDailyAverage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvDateWithDays = itemView.findViewById(R.id.tvDateWithDays);
        }
    }

}
