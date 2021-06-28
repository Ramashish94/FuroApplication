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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.AllTimeCounter;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;
import com.app.furoapp.activity.newFeature.bmiCalculator.adapter.AgeAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class YearFilterAdapter extends RecyclerView.Adapter<YearFilterAdapter.MyViewHolder> {
    Context context;
    List<AgeModelTest> ageModelTestList;
    public YearClickCallBack yearClickCallBack;
    private int row_index = -1;
    public Boolean clicked = false;


    public YearFilterAdapter(Context context, List<AgeModelTest> ageModelTestList, YearClickCallBack yearClickCallBack) {
        this.context = context;
        this.ageModelTestList = ageModelTestList;
        this.yearClickCallBack = yearClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_year_filter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgeModelTest ageModelTest = ageModelTestList.get(position);
        holder.tvYear.setText("" + ageModelTest.getAge());
        // holder.tvWakeTime.setText("" + ageModelTest.getAge());

        holder.llYear.setOnClickListener(v -> {
            yearClickCallBack.yearSelectItem(Integer.parseInt(ageModelTest.getAge()));
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
            holder.ivYearCheck.setVisibility(View.VISIBLE);
//            holder.tvAge.setTextColor(Color.parseColor("#19CFE6"));
        } else {
            holder.ivYearCheck.setVisibility(View.INVISIBLE);
            //  holder.tvAge.setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        if (ageModelTestList != null && ageModelTestList.size() > 0) {
            return ageModelTestList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvYear;
        public ImageView ivYearCheck;
        public LinearLayout llYear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tvYear);
            ivYearCheck = itemView.findViewById(R.id.ivYearCheck);
            llYear = itemView.findViewById(R.id.llYear);

        }
    }

    public interface YearClickCallBack {
        void yearSelectItem(int year);
    }
}
