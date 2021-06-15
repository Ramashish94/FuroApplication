package com.app.furoapp.activity.newFeature.bmiCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.fetchBmiDataModel.Bmi;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FetchRecordAdapter extends RecyclerView.Adapter<FetchRecordAdapter.MyViewHolder> {
    Context context;
    List<Bmi> bmiList;
    private Date date;

    public FetchRecordAdapter(Context applicationContext, List<Bmi> bmiList) {
        this.context = applicationContext;
        this.bmiList = bmiList;
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
        Bmi bmi = bmiList.get(position);
        holder.tvBmiScore.setText("" + bmi.getBmi());
        holder.tvGender.setText("" + bmi.getGender());
        holder.tvWaight.setText("" + bmi.getWeight()+" KG");
        holder.tvHeight.setText("" + bmi.getHeight());

        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(bmi.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        String getDate = dateFormat1.format(date);
        holder.tvDate.setText(getDate);
    }

    @Override
    public int getItemCount() {
        if (bmiList != null && bmiList.size() > 0) {
            return bmiList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBmiScore, tvGender, tvWaight, tvHeight, tvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBmiScore = itemView.findViewById(R.id.tvBmiScore);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvWaight = itemView.findViewById(R.id.tvWaight);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }
}
