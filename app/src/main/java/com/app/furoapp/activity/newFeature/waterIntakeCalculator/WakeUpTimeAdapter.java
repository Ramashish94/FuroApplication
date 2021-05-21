package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.List;

public class WakeUpTimeAdapter extends RecyclerView.Adapter<WakeUpTimeAdapter.MyViewHolder> {

    Context context;
    List<AgeModelTest> ageModelTestList;

    public WakeUpTimeAdapter(Context applicationContext, List<AgeModelTest> ageModelTestList) {
        this.context = applicationContext;
        this.ageModelTestList = ageModelTestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_age_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgeModelTest ageModelTest = ageModelTestList.get(position);
        holder.llWakeUpTime.setVisibility(View.VISIBLE);
        holder.llAge.setVisibility(View.GONE);
        holder.tvWakeTime.setText("" + ageModelTest.getAge());
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
        public TextView tvAge, tvWakeTime;
        public LinearLayout llWakeUpTime,llAge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWakeTime = itemView.findViewById(R.id.tvWakeTime);
            llWakeUpTime = itemView.findViewById(R.id.llWakeUpTime);
            llAge = itemView.findViewById(R.id.llAge);

        }
    }
}
