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
import com.app.furoapp.model.communitymembers.Member;
import com.app.furoapp.model.contentFeedDetail.Body;
import com.app.furoapp.model.content_feed.Motivation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityMembersAdapter extends RecyclerView.Adapter<CommunityMembersAdapter.MyViewHolder> {
    private List<Member> members;
    private Context context;
    @NonNull
    @Override
    public CommunityMembersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_members, parent, false);

        return new CommunityMembersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityMembersAdapter.MyViewHolder holder, int position) {
        Member member = members.get(position);
        holder.userName.setText(member.getName());
        Picasso.with(context).load(member.getImage()).error(R.drawable.profile_circle).into(holder.userProfile);

    }

    public CommunityMembersAdapter(List<Member> members, Context context) {
        this.members = members;
        this.context = context;


    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView userProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.communitymembername);
            userProfile= itemView.findViewById(R.id.communitymembernameprofile);
        }
    }
}
