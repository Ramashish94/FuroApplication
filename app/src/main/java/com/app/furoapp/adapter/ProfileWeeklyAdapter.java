package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.ChallengeItemModel;
import com.app.furoapp.model.profile.AllTime;
import com.app.furoapp.model.profile.Weekly;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileWeeklyAdapter extends RecyclerView.Adapter<ProfileWeeklyAdapter.MyViewHolder> {

    private List<Weekly> weeklyList;
    private Context context;

    @NonNull
    @Override
    public ProfileWeeklyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_profile_your_activity, parent, false);

        return new ProfileWeeklyAdapter.MyViewHolder(itemView);
    }

    public ProfileWeeklyAdapter( List<Weekly> weeklyList,Context context) {
        this.weeklyList = weeklyList;
        this.context= context;


    }

    @Override
    public void onBindViewHolder(@NonNull ProfileWeeklyAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(weeklyList.get(position).getName());
        holder.tv_time.setText(weeklyList.get(position).getTime());
        holder.tv_count.setText(weeklyList.get(position).getActivityCount());
        holder.tv_dur.setText(weeklyList.get(position).getAcitivityDuration());
        holder.tv_dt.setText(weeklyList.get(position).getDate());
        Picasso.with(context).load(weeklyList.get(position).getBlueIcon()).placeholder(R.drawable.bullets).error(R.drawable.ic_squads).into(holder.img_profile);

    }

    @Override
    public int getItemCount() {

        return weeklyList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_dt, tv_dur, tv_count, tv_time;
        private ImageView img_profile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_dt = itemView.findViewById(R.id.tv_dt);
            tv_dur = itemView.findViewById(R.id.tv_dur);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_time = itemView.findViewById(R.id.tv_time);
            img_profile = itemView.findViewById(R.id.ivItemActivityItem);
        }
    }
}
