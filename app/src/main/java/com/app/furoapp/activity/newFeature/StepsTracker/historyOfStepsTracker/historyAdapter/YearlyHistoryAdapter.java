package com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.historyAdapter;

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
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse.YearlyDataList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class YearlyHistoryAdapter extends RecyclerView.Adapter<YearlyHistoryAdapter.MyViewHolder> {
    Context context;
    List<YearlyDataList> yearlyDataListList;
    private int row_index = -1;
    public Date date;


    public YearlyHistoryAdapter(Context context, List<YearlyDataList> yearlyDataListList) {
        this.context = context;
        this.yearlyDataListList = yearlyDataListList;
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
        YearlyDataList yearlyDataList = yearlyDataListList.get(position);
        holder.tvTotSteps.setText("" + yearlyDataList.getCountSteps());
        holder.tvDailyAverage.setText("" + yearlyDataList.getDailyAverage() + " m");
        holder.tvTime.setText("" + yearlyDataList.getTime() + " Minutes");
        holder.tvCalories.setText("" + yearlyDataList.getCalories() + " Cal");

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(yearlyDataList.getMonth());
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
        if (yearlyDataListList != null && yearlyDataListList.size() > 0) {
            return yearlyDataListList.size();
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
