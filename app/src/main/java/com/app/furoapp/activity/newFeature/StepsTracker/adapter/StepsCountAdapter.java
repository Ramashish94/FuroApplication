package com.app.furoapp.activity.newFeature.StepsTracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.List;

public class StepsCountAdapter extends RecyclerView.Adapter<StepsCountAdapter.MyViewHolder> {
    Context context;
    List<AgeModelTest> ageModelTestList;
    public StepsClickCallBack stepsClickCallBack;
    private int row_index = -1;
    public Boolean clicked = false;


    public StepsCountAdapter(Context applicationContext, List<AgeModelTest> ageModelTestList, StepsClickCallBack stepsClickCallBack) {
        this.context = applicationContext;
        this.ageModelTestList = ageModelTestList;
        this.stepsClickCallBack = stepsClickCallBack;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_steps_count, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgeModelTest ageModelTest = ageModelTestList.get(position);
        holder.tvNumber.setText("" + ageModelTest.getAge());

        holder.tvNumber.setOnClickListener(v -> {
            stepsClickCallBack.stepSelectItem(position, ageModelTest.getAge());
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
//            clicked=true;
            holder.tvNumber.setTextColor(Color.parseColor("#19CFE6"));
        } else {
//           clicked=false;
            holder.tvNumber.setTextColor(Color.parseColor("#979797"));
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
        public TextView tvNumber, tvWakeTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
        }
    }

    public interface StepsClickCallBack {
        void stepSelectItem(int position, String age);
    }
}
