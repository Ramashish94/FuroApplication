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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeData;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyData;

import java.util.List;

public class WeeklyHistoryAdapter extends RecyclerView.Adapter<WeeklyHistoryAdapter.MyViewHolder> {
    Context context;
    List<WeeklyData> weeklyDataList;
    private int row_index = -1;


    public WeeklyHistoryAdapter(Context context, List<WeeklyData> weeklyDataList) {
        this.context = context;
        this.weeklyDataList = weeklyDataList;
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
        WeeklyData weeklyData = weeklyDataList.get(position);
        holder.tvTotSteps.setText("" + weeklyData.getTotalSteps());
        holder.tvDailyAverage.setText("" + weeklyData.getDailyAverage());
        holder.tvTime.setText("" + weeklyData.getTime());
        holder.tvCalories.setText("" + weeklyData.getCalories());
    }

    @Override
    public int getItemCount() {
        if (weeklyDataList != null && weeklyDataList.size() > 0) {
            return weeklyDataList.size();
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


}
