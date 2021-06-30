package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter;

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
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.WeeklyDataList;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.WeeklyDataCount;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeeklyLeaderBoardAdapter extends RecyclerView.Adapter<WeeklyLeaderBoardAdapter.MyViewHolder> {
    Context context;
    List<WeeklyDataCount> weeklyDataCountList;
    public WeeklyItemClickCallBack weeklyItemClickCallBack;
    private int row_index = -1;


    public WeeklyLeaderBoardAdapter(Context context, List<WeeklyDataCount> weeklyDataCountList, WeeklyItemClickCallBack weeklyItemClickCallBack) {
        this.context = context;
        this.weeklyDataCountList = weeklyDataCountList;
        this.weeklyItemClickCallBack = weeklyItemClickCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lead_board, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeeklyDataCount weeklyDataCount = weeklyDataCountList.get(position);
        if (weeklyDataCount.getUser().getImage() != null) {
            Picasso.with(context).load(weeklyDataCount.getUser().getImage()).error(R.drawable.ic_userimageiconss).into(holder.ivUserImage);
        }
        holder.tvName.setText("" + weeklyDataCount.getUser().getUsername());
        holder.tvScore.setText("" + weeklyDataCount.getCountSteps());

        holder.llLeadBoardItem.setOnClickListener(v -> {
            weeklyItemClickCallBack.weeklyItemClick(position, weeklyDataCount.getUser().getUsername(), weeklyDataCount.getCountSteps());
            row_index = position;
            notifyDataSetChanged();
        });

//        if (row_index == position) {
//           clicked=true;
//            holder.tvScore.setTextColor(Color.parseColor("#19CFE6"));
//            holder.tvName.setTextColor(Color.parseColor("#19CFE6"));
//        } else {
//           clicked=false;
//            holder.tvScore.setTextColor(Color.parseColor("#19CFE6"));
//            holder.tvName.setTextColor(Color.parseColor("#19CFE6"));
//
//        }


    }

    @Override
    public int getItemCount() {
        if (weeklyDataCountList != null && weeklyDataCountList.size() > 0) {
            return weeklyDataCountList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvScore, tvName;
        public LinearLayout llLeadBoardItem, llTimeDeleteSlot;
        public ImageView ivUserImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvName = itemView.findViewById(R.id.tvName);
            llLeadBoardItem = itemView.findViewById(R.id.llLeadBoardItem);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
        }
    }

    public interface WeeklyItemClickCallBack {
        void weeklyItemClick(int position, String name, String score);
    }
}
