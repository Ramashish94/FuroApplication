package com.app.furoapp.activity.newFeature.StepsTracker.historyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyDataList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DaysGraphAdapter extends RecyclerView.Adapter<DaysGraphAdapter.MyViewHolder> {
    Context context;
    List<WeeklyDataList> weeklyDataListList;
    private int row_index = -1;
    public Date date;


    public DaysGraphAdapter(Context context, List<WeeklyDataList> weeklyDataListList) {
        this.context = context;
        this.weeklyDataListList = weeklyDataListList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_days_graph, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeeklyDataList weeklyDataList = weeklyDataListList.get(position);
        //holder.tvDays.setText("" + weeklyDataList.getCreatedAt());
        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(weeklyDataList.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("EEE");/*dd MMM,*/
        String getDate = dateFormat1.format(date);
        holder.tvDays.setText(getDate);

    }

    @Override
    public int getItemCount() {
        if (weeklyDataListList != null && weeklyDataListList.size() > 0) {
            return weeklyDataListList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDays;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDays = itemView.findViewById(R.id.tvDays);
        }
    }

}
