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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.MonthlyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse.MonthStepCounter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonthlyHistoryAdapter extends RecyclerView.Adapter<MonthlyHistoryAdapter.MyViewHolder> {
    Context context;
    List<MonthlyDataList> monthlyDataList;
    List<MonthStepCounter> monthStepCounterList;
    TimeSlotClickCallBack timeSlotClickCallBack;
    private int row_index = -1;
    public Date date;

    public MonthlyHistoryAdapter(Context context, List<MonthlyDataList> monthlyDataList, TimeSlotClickCallBack timeSlotClickCallBack) {
        this.context = context;
        this.monthlyDataList = monthlyDataList;
    }

    public MonthlyHistoryAdapter(Context context, List<MonthStepCounter> monthStepCounterList) {
        this.context = context;
        this.monthStepCounterList = monthStepCounterList;
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
       /* MonthlyDataList monthlyData = monthlyDataList.get(position);
        holder.tvTotSteps.setText("" + monthlyData.getCountSteps());
        holder.tvDailyAverage.setText("" + monthlyData.getDailyAverage() + " m");
        holder.tvTime.setText("" + monthlyData.getTime() + " Minutes");
        holder.tvCalories.setText("" + monthlyData.getCalories() + " Cal");*/

        MonthStepCounter monthStepCounter = monthStepCounterList.get(position);
        holder.tvTotSteps.setText("" + monthStepCounter.getCountSteps());
        holder.tvDailyAverage.setText("" + monthStepCounter.getDailyAverage() + " m");
        holder.tvTime.setText("" + monthStepCounter.getTime() + " Minutes");
        holder.tvCalories.setText("" + monthStepCounter.getCalories() + " Cal");


        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(monthStepCounter.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);
        holder.tvDateWithDays.setText(getDate);

    }

    @Override
    public int getItemCount() {
       /* if (monthlyDataList != null && monthlyDataList.size() > 0) {
            return monthlyDataList.size();
            {
                return 0;
            }
        } else*/ if (monthStepCounterList != null && monthStepCounterList.size() > 0) {
            return monthStepCounterList.size();
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

    public interface TimeSlotClickCallBack {
        void timeSlotClick(Integer id, String timeslot);
    }
}
