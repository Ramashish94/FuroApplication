package com.app.furoapp.activity.newFeature.StepsTracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.LeadBoardModel;

import java.util.List;

public class LeadBoardAdapter extends RecyclerView.Adapter<LeadBoardAdapter.MyViewHolder> {
    Context context;
    List<LeadBoardModel> leadBoardModelList;
    public LeadBoardClickCallBack leadBoardClickCallBack;
    private int row_index = -1;


    public LeadBoardAdapter(Context context, List<LeadBoardModel> leadBoardModelList, LeadBoardClickCallBack leadBoardClickCallBack) {
        this.context = context;
        this.leadBoardModelList = leadBoardModelList;
        this.leadBoardClickCallBack = leadBoardClickCallBack;
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
        holder.tvScore.setText("" + leadBoardModel.getName());
        holder.tvName.setText("" + leadBoardModel.getScore());

        holder.llLeadBoardItem.setOnClickListener(v -> {
            leadBoardClickCallBack.leadBoardItemClick(position, leadBoardModel.getName(), leadBoardModel.getScore());
            row_index = position;
            notifyDataSetChanged();
        });

        if (row_index == position) {
//            clicked=true;
            //holder.tvAge.setTextColor(Color.parseColor("#19CFE6"));
        } else {
//           clicked=false;
            //holder.tvAge.setTextColor(Color.parseColor("#FFFFFF"));
        }


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

    public interface LeadBoardClickCallBack {
        void leadBoardItemClick(int position, String name, String score);
    }
}
