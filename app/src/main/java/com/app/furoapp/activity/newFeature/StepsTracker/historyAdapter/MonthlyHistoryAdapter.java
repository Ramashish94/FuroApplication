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
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.Datum;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyData;

import java.util.List;

public class MonthlyHistoryAdapter extends RecyclerView.Adapter<MonthlyHistoryAdapter.MyViewHolder> {
    Context context;
    List<MonthlyData> monthlyDataList;

    private int row_index = -1;


    public MonthlyHistoryAdapter(Context context, List<MonthlyData> monthlyDataList) {
        this.context = context;
        this.monthlyDataList = monthlyDataList;
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
        MonthlyData monthlyData = monthlyDataList.get(position);
        holder.tvTotSteps.setText("" + monthlyData.getTotalSteps());
        holder.tvDailyAverage.setText("" + monthlyData.getDailyAverage() +" m");
        holder.tvTime.setText("" + monthlyData.getTime()+" Minutes");
        holder.tvCalories.setText("" + monthlyData.getCalories()+" Cal");

    }

    @Override
    public int getItemCount() {
        if (monthlyDataList != null && monthlyDataList.size() > 0) {
            return monthlyDataList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTotSteps, tvDailyAverage, tvTime, tvCalories;
        public LinearLayout llTimeSlot, llTimeDeleteSlot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotSteps = itemView.findViewById(R.id.tvTotSteps);
            tvDailyAverage = itemView.findViewById(R.id.tvDailyAverage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCalories = itemView.findViewById(R.id.tvCalories);
        }
    }

    public interface TimeSlotClickCallBack {
        void timeSlotClick(Integer id, String timeslot);
    }
}
