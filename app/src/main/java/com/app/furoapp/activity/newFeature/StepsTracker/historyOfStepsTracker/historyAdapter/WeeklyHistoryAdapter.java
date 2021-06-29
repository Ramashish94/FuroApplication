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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse.CurrentWeekStepCounter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeeklyHistoryAdapter extends RecyclerView.Adapter<WeeklyHistoryAdapter.MyViewHolder> {
    Context context;
    List<WeeklyDataList> weeklyDataList;
    List<CurrentWeekStepCounter> currentWeekStepCounterList;
    WeekClickCallBack weekClickCallBack;
    private int row_index = -1;
    public Date date;

    public WeeklyHistoryAdapter(Context context, List<WeeklyDataList> weeklyDataList,  WeekClickCallBack weekClickCallBack) {
        this.context = context;
        this.weeklyDataList = weeklyDataList;
    }

    public WeeklyHistoryAdapter(Context context, List<CurrentWeekStepCounter> currentWeekStepCounterList) {
        this.context = context;
        this.currentWeekStepCounterList = currentWeekStepCounterList;
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
        /*WeeklyDataList weeklyData = weeklyDataList.get(position);
        holder.tvTotSteps.setText("" + weeklyData.getCountSteps());
        holder.tvDailyAverage.setText("" + weeklyData.getDailyAverage() + " m");
        holder.tvTime.setText("" + weeklyData.getTime() + " Minutes");
        holder.tvCalories.setText("" + weeklyData.getCalories() + " Cal");*/

        CurrentWeekStepCounter currentWeekStepCounter = currentWeekStepCounterList.get(position);
        holder.tvTotSteps.setText("" + currentWeekStepCounter.getCountSteps());
        holder.tvDailyAverage.setText("" + currentWeekStepCounter.getDailyAverage() + " m");
        holder.tvTime.setText("" + currentWeekStepCounter.getTime() + " Minutes");
        holder.tvCalories.setText("" + currentWeekStepCounter.getCalories() + " Cal");
        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));

        try {
            date = dateFormat.parse(currentWeekStepCounter.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);
        holder.tvDateWithDays.setText(getDate);


    }

    @Override
    public int getItemCount() {

        if (currentWeekStepCounterList != null && currentWeekStepCounterList.size() > 0) {
            return currentWeekStepCounterList.size();
        } else if (weeklyDataList != null && weeklyDataList.size() > 0) {
            return weeklyDataList.size();
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

    public interface WeekClickCallBack {
        void weekClick(Integer id, String timeslot);
    }

}
