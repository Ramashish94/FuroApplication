package com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.RestorePlanResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeeklyDataAdapter extends RecyclerView.Adapter<WeeklyDataAdapter.MyViewHolder> {

    Context context;
    List<RestorePlanResponse> restorePlanResponseList;
    private Date date;


    public WeeklyDataAdapter(Context context, List<RestorePlanResponse> restorePlanResponseList) {
        this.context = context;
        this.restorePlanResponseList = restorePlanResponseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_monthly_alltime_water_intake_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RestorePlanResponse restorePlanResponse = restorePlanResponseList.get(position);
        holder.tvTotWaterAmountDrunk.setText("" + restorePlanResponse.getWeeklyData().getWeeklyTakenWaterInMl().toString() + " ml");
        holder.tvCountNosOfGlass.setText("" + restorePlanResponse.getWeeklyData().getWeeklyTakenGlassOfWater().toString());
        holder.tvRecommendedNosOfWaterGlasses.setText("" + restorePlanResponse.getWeeklyData().getWeeklyRecommendedGlassOfWater().toString() + " Glasses");
        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(restorePlanResponse.getCurrentPlan().getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);
        holder.tvDateWithDay.setText(getDate);
    }

    @Override
    public int getItemCount() {
        if (restorePlanResponseList != null && restorePlanResponseList.size() > 0) {
            return restorePlanResponseList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecommendedNosOfWaterGlasses, tvTotWaterAmountDrunk, tvCountNosOfGlass, tvDateWithDay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecommendedNosOfWaterGlasses = itemView.findViewById(R.id.tvRecommendedNosOfWaterGlasses);
            tvTotWaterAmountDrunk = itemView.findViewById(R.id.tvTotWaterAmountDrunk);
            tvCountNosOfGlass = itemView.findViewById(R.id.tvCountNosOfGlass);
            tvDateWithDay = itemView.findViewById(R.id.tvDateWithDay);
        }
    }

    public interface AllPlanClickCallBack {
        void getPlanClickCallBack(Integer id, String waterTakeInMl, String recommendedDurationInMins);
    }


}
