package com.app.furoapp.activity.newFeature.StepsTracker.filterAdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.StepCounterHistoryActivity;

import java.util.List;

public class MonthFilterAdapter extends RecyclerView.Adapter<MonthFilterAdapter.MyViewHolder> {
    List<String> allDates;
    Context context;
    public MonthlyClickCallBack monthlyClickCallBack;
    private int row_index = -1;
    public Boolean clicked = false;

    public MonthFilterAdapter(Context applicationContext, List<String> allDates, StepCounterHistoryActivity monthlyClickCallBack) {
        this.context = applicationContext;
        this.allDates = allDates;
        this.monthlyClickCallBack = monthlyClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_month_filter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvMonth.setText(allDates.get(position));
        // holder.tvWakeTime.setText("" + ageModelTest.getAge());

        holder.llYear.setOnClickListener(v -> {
            monthlyClickCallBack.monthlySelectItem(allDates.get(position));
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
            holder.ivMonthCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivMonthCheck.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return allDates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMonth;
        public ImageView ivMonthCheck;
        public LinearLayout llYear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            ivMonthCheck = itemView.findViewById(R.id.ivMonthCheck);
            llYear = itemView.findViewById(R.id.llYear);

        }
    }

    public interface MonthlyClickCallBack {
        void monthlySelectItem(String month);
    }

    public void setList(List<String> allDates) {
        this.allDates.clear();
        this.allDates = allDates;
        notifyDataSetChanged();
    }
}
