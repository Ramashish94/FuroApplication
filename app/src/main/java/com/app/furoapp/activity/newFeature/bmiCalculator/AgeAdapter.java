package com.app.furoapp.activity.newFeature.bmiCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;

import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.MyViewHolder> {
    Context context;
    List<AgeModelTest> ageModelTestList;

    public AgeAdapter(Context applicationContext, List<AgeModelTest> ageModelTestList) {
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
        holder.tvAge.setText("" + ageModelTest.getAge());
       // holder.tvWakeTime.setText("" + ageModelTest.getAge());

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvWakeTime = itemView.findViewById(R.id.tvWakeTime);
        }
    }
}
