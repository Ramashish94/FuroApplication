package com.app.furoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.CommunityMembers;
import com.app.furoapp.model.communitydetail.Communities;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.List;

public class MyCommunityDetailAdapter extends RecyclerView.Adapter<MyCommunityDetailAdapter.MyViewHolder> {
    Context context;
    List<Communities>communitiesList;

    @NonNull
    @Override
    public MyCommunityDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_community_detail, parent, false);


        return new MyCommunityDetailAdapter.MyViewHolder(itemView);
    }

    public MyCommunityDetailAdapter(List<Communities> communitiesList, Context context) {
        this.communitiesList = communitiesList;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(@NonNull MyCommunityDetailAdapter.MyViewHolder holder, int position) {
        Communities communities = communitiesList.get(position);
        holder.activityDescription.setText(communities.getDescription());
        holder.activityMember.setText(communities.getMembers());
        holder.activityType.setText(communities.getType());
        holder.activityTarget.setText(communities.getMeasurement());
        Picasso.with(context).load(communities.getBlueIcon()).into(holder.imageViewact);
        Picasso.with(context).load(communities.getMeasurementIcon()).into(holder.imageViewact2);
        holder.members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommunityMembers.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return communitiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView activityDescription,activityTarget,activityType,activityMember;
        private ImageView imageViewact,imageViewact2,members;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityDescription = itemView.findViewById(R.id.description);
            activityTarget= itemView.findViewById(R.id.kilometerr);
            imageViewact = itemView.findViewById(R.id.appiconacttwo);
            imageViewact2 = itemView.findViewById(R.id.appiconactivity);
            activityType = itemView.findViewById(R.id.running);
            activityMember = itemView.findViewById(R.id.members);
            members = itemView.findViewById(R.id.membersall);
        }
    }


}
