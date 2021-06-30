package com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.LeadBoardModel;

import java.util.List;

public class LeaderBoardWeeklyAdapter extends RecyclerView.Adapter<LeaderBoardWeeklyAdapter.MyViewHolder> {
    Context context;
    List<LeadBoardModel> leadBoardModelList;
    public WeeklyItemClickCallBack weeklyItemClickCallBack;
    private int row_index = -1;


    public LeaderBoardWeeklyAdapter(Context context, List<LeadBoardModel> leadBoardModelList, WeeklyItemClickCallBack weeklyItemClickCallBack) {
        this.context = context;
        this.leadBoardModelList = leadBoardModelList;
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
        LeadBoardModel leadBoardModel = leadBoardModelList.get(position);
        holder.tvScore.setText("" + leadBoardModel.getScore());
        holder.tvName.setText("" + leadBoardModel.getName());

        holder.llLeadBoardItem.setOnClickListener(v -> {
            weeklyItemClickCallBack.weekItemClick(position, leadBoardModel.getName(), leadBoardModel.getScore());
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
        if (leadBoardModelList != null && leadBoardModelList.size() > 0) {
            return leadBoardModelList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvScore, tvName;
        public LinearLayout llLeadBoardItem, llTimeDeleteSlot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvName = itemView.findViewById(R.id.tvName);
            llLeadBoardItem = itemView.findViewById(R.id.llLeadBoardItem);
        }
    }

    public interface WeeklyItemClickCallBack {
        void weekItemClick(int position, String name, String score);
    }
}
