package com.app.furoapp.activity.newFeature.StepsTracker.filterAdater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.List;

public class WeekFilterAdapter extends RecyclerView.Adapter<WeekFilterAdapter.MyViewHolder> {
    Context context;
    List<WeekFilterModel> weekFilterModelList;
    public WeekClickCallBack weekClickCallBack;
    private int row_index = -1;
    public Boolean clicked = false;
    public int getPosition;

    public WeekFilterAdapter(Context context, List<WeekFilterModel> weekFilterModelList, WeekClickCallBack weekClickCallBack) {
        this.context = context;
        this.weekFilterModelList = weekFilterModelList;
        this.weekClickCallBack = weekClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_fiter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeekFilterModel weekFilterModel = weekFilterModelList.get(position);
        getPosition = position + 1;
        holder.tvWeekNos.setText("Week " + getPosition + " - ");
        holder.tvYear.setText("" + weekFilterModel.getWeek());
        holder.tvWeekNos.setTextColor(Color.parseColor("#19CFE6"));

        holder.llYear.setOnClickListener(v -> {
            weekClickCallBack.weekSelectItem(weekFilterModel.getWeek());
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
            holder.ivYearCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivYearCheck.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (weekFilterModelList != null && weekFilterModelList.size() > 0) {
            return weekFilterModelList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvYear, tvWeekNos;
        public ImageView ivYearCheck;
        public LinearLayout llYear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tvYear);
            ivYearCheck = itemView.findViewById(R.id.ivYearCheck);
            llYear = itemView.findViewById(R.id.llYear);
            tvWeekNos = itemView.findViewById(R.id.tvWeekNos);

        }
    }

    public interface WeekClickCallBack {
        void weekSelectItem(String week);
    }
}
