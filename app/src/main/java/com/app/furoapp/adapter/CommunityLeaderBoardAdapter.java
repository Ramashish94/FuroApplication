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
import com.app.furoapp.model.communitydetail.Leadership;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityLeaderBoardAdapter extends RecyclerView.Adapter<CommunityLeaderBoardAdapter.MyViewHolder> {
    private Context context;
    private List<Leadership> leadershipList;

    @NonNull
    @Override
    public CommunityLeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_leader_board_row_user_listview, parent, false);

        return new CommunityLeaderBoardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityLeaderBoardAdapter.MyViewHolder holder, int position) {
        Leadership leadership = leadershipList.get(position);
        holder.userName.setText(leadership.getName());
        holder.distance.setText(leadership.getTarget());
        Picasso.with(context).load(leadership.getImage()).error(R.drawable.profile_circle).into(holder.imageView);

    }


    public CommunityLeaderBoardAdapter(List<Leadership> leadershipList, Context context) {

        this.leadershipList = leadershipList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return leadershipList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView distance, userName;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.leaderboardprofile);
            distance = itemView.findViewById(R.id.communityLeaderdistancee);
            userName = itemView.findViewById(R.id.communityLeaderuser_namee);
        }
    }
}
