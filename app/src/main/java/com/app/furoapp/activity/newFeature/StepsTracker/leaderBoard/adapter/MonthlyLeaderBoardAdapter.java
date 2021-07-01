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
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.MonthlyDataCount;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonthlyLeaderBoardAdapter extends RecyclerView.Adapter<MonthlyLeaderBoardAdapter.MyViewHolder> {
    Context context;
    List<MonthlyDataCount> monthlyDataCountList;
    public MonthlyClickCallBack monthlyClickCallBack;
    private int row_index = -1;


    public MonthlyLeaderBoardAdapter(Context context, List<MonthlyDataCount> monthlyDataCountList, MonthlyClickCallBack monthlyClickCallBack) {
        this.context = context;
        this.monthlyDataCountList = monthlyDataCountList;
        this.monthlyClickCallBack = monthlyClickCallBack;
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
        MonthlyDataCount monthlyDataCount = monthlyDataCountList.get(position);
        if (monthlyDataCount.getUser().getImage() != null) {
            Picasso.with(context).load(monthlyDataCount.getUser().getImage()).error(R.drawable.ic_userimageiconss).into(holder.ivUserImage);
        }
        holder.tvRank.setText("" + monthlyDataCount.getPosition());
        holder.tvName.setText("" + monthlyDataCount.getUser().getUsername());
        holder.tvScore.setText("" + monthlyDataCount.getCountSteps());

        holder.llLeadBoardItem.setOnClickListener(v -> {
            monthlyClickCallBack.monthlyItemClick(position, monthlyDataCount.getUser().getUsername(), monthlyDataCount.getCountSteps());
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
        if (monthlyDataCountList != null && monthlyDataCountList.size() > 0) {
            return monthlyDataCountList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvScore, tvName, tvRank;
        public LinearLayout llLeadBoardItem, llTimeDeleteSlot;
        public ImageView ivUserImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvName = itemView.findViewById(R.id.tvName);
            llLeadBoardItem = itemView.findViewById(R.id.llLeadBoardItem);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvRank = itemView.findViewById(R.id.tvRank);

        }
    }

    public interface MonthlyClickCallBack {
        void monthlyItemClick(int position, String name, String score);
    }
}
