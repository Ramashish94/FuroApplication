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
import com.app.furoapp.model.profile.AllTime;
import com.app.furoapp.model.profile.Monthly;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileMonthlyAdapter extends RecyclerView.Adapter<ProfileMonthlyAdapter.MyViewHolder> {

    private List<Monthly> monthlyList;
    private Context context;

    @NonNull
    @Override
    public ProfileMonthlyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_profile_your_activity, parent, false);

        return new ProfileMonthlyAdapter.MyViewHolder(itemView);
    }

    public ProfileMonthlyAdapter( List<Monthly> monthlyList,Context context) {
        this.monthlyList = monthlyList;
        this.context= context;


    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMonthlyAdapter.MyViewHolder holder, int position) {

        holder.tv_name.setText(monthlyList.get(position).getName());
        holder.tv_time.setText(monthlyList.get(position).getTime());
        holder.tv_count.setText(monthlyList.get(position).getActivityCount());
        holder.tv_dur.setText(monthlyList.get(position).getAcitivityDuration());
        holder.tv_dt.setText(monthlyList.get(position).getDate());
        Picasso.with(context).load(monthlyList.get(position).getBlueIcon()).placeholder(R.drawable.bullets).error(R.drawable.ic_squads).into(holder.img_profile);

    }

    @Override
    public int getItemCount() {

        return monthlyList.size();

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
