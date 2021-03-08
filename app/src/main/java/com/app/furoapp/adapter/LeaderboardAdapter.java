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
import com.app.furoapp.model.LeaderBoard.AllTime;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private List<AllTime> receiveChallenges;
    private Context context;

    ContentFeedDetailAdapter.ContentFeedDetailInterface contentFeedDetailInterface;
    @NonNull
    @Override
    public LeaderboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_leaderboard_list, parent, false);

        return new LeaderboardAdapter.MyViewHolder(itemView);
    }

    public LeaderboardAdapter(List<AllTime> receiveChallenges, Context context) {
        this.receiveChallenges = receiveChallenges;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.MyViewHolder holder, int position) {

        AllTime allTime = receiveChallenges.get(position);
        holder.tv_count.setText(allTime.getActivityCount());
        holder.tv_name.setText(allTime.getName());
        Picasso.with(context).load(allTime.getImage()).error(R.drawable.profile_circle).into(holder.circularImageView);

    }

    @Override
    public int getItemCount() {
        return receiveChallenges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_count;
        ImageView circularImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.ivUserProfileleaderboard);
            tv_name= itemView.findViewById(R.id.tv_name);
            tv_count= itemView.findViewById(R.id.tv_count);
        }
    }
}

