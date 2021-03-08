package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.allCommunity.Community;

import java.util.List;

public class CommunitiesListAdapter extends RecyclerView.Adapter<CommunitiesListAdapter.MyViewHolder> {

    private List<Community> communityList;
    private Context context;
    private AllCommunityInterface allCommunityInterface;

    @NonNull
    @Override
    public CommunitiesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_community, parent, false);

        return new CommunitiesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommunitiesListAdapter.MyViewHolder holder, int position) {
        Community community = communityList.get(position);
        holder.community.setText(community.getCommunity());
        holder.communityMembers.setText(community.getMembers());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCommunityInterface != null) {
                    allCommunityInterface.allCommunityItem(community.getId(),community.getCommunity());
                }
            }
        });

    }

    public CommunitiesListAdapter(List<Community> communityList, Context context) {

        this.communityList = communityList;
        this.context = context;

    }

    public void setAllCommunityModels(AllCommunityInterface allCommunityInterface) {
        this.allCommunityInterface = allCommunityInterface;

    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView community, communityMembers;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            community = itemView.findViewById(R.id.tvChallengesName);
            communityMembers = itemView.findViewById(R.id.memberss);
            linearLayout = itemView.findViewById(R.id.linearcommunitylayout);

        }
    }

    public interface AllCommunityInterface {
        public void allCommunityItem(int id,String communityName);

    }


    public void allCommunitiesList(AllCommunityInterface allCommunityInterface) {
        this.allCommunityInterface = allCommunityInterface;

    }
}


