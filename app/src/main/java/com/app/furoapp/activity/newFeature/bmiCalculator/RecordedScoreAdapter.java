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

public class RecordedScoreAdapter extends RecyclerView.Adapter<RecordedScoreAdapter.MyViewHolder> {
    Context context;
    List<RecordedBmiModel> recordedBmiModelList;

    public RecordedScoreAdapter(Context applicationContext, List<RecordedBmiModel> recordedBmiModelList) {
        this.context = applicationContext;
        this.recordedBmiModelList = recordedBmiModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recoded_score, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecordedBmiModel recordedBmiModel = recordedBmiModelList.get(position);
        holder.tvBmiScore.setText("" + recordedBmiModel.getBmi());
        holder.tvGender.setText("" + recordedBmiModel.getGender());
        holder.tvWaight.setText("" + recordedBmiModel.getWeight());
        holder.tvHeight.setText("" + recordedBmiModel.getHeight());

    }

    @Override
    public int getItemCount() {
        if (recordedBmiModelList != null && recordedBmiModelList.size() > 0) {
            return recordedBmiModelList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBmiScore, tvGender, tvWaight, tvHeight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBmiScore = itemView.findViewById(R.id.tvBmiScore);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvWaight = itemView.findViewById(R.id.tvWaight);
            tvHeight = itemView.findViewById(R.id.tvHeight);

        }
    }
}
