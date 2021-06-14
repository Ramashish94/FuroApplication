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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeData;

import java.util.List;

public class AllTimeHistoryAdapter extends RecyclerView.Adapter<AllTimeHistoryAdapter.MyViewHolder> {
    Context context;
    List<AllTimeData> allTimeDataList;
    private int row_index = -1;


    public AllTimeHistoryAdapter(Context context, List<AllTimeData> allTimeDataList) {
        this.context = context;
        this.allTimeDataList = allTimeDataList;
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
        AllTimeData allTimeData = allTimeDataList.get(position);
        holder.tvTotSteps.setText("" + allTimeData.getTotalSteps());
        holder.tvDailyAverage.setText("" + allTimeData.getDailyAverage() + " m");
        holder.tvTime.setText("" + allTimeData.getTime() + " Minutes");
        holder.tvCalories.setText("" + allTimeData.getCalories() + " Cal");
    }

    @Override
    public int getItemCount() {
        if (allTimeDataList != null && allTimeDataList.size() > 0) {
            return allTimeDataList.size();
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
