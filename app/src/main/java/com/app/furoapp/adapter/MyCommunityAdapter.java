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
import com.app.furoapp.model.myCommunities.Community;

import java.util.List;

public class MyCommunityAdapter extends RecyclerView.Adapter<MyCommunityAdapter.MyViewHolder> {
    private Context context;
    private List<Community> myCommunityList;
    MyCommunityInterface myCommunityInterface;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_community, parent, false);

        return new MyCommunityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Community community = myCommunityList.get(position);
        holder.myCommunity.setText(community.getCommunity());
        holder.myCommunityMembers.setText(community.getMembers());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myCommunityInterface != null) {
                    myCommunityInterface.MyCommunityItem(community.getId(),community.getCommunity());
                }
            }
        });

    }

    public MyCommunityAdapter(List<Community> myCommunityList, Context context) {
        this.myCommunityList = myCommunityList;
        this.context = context;

    }


    @Override
    public int getItemCount() {
        if (myCommunityList != null && myCommunityList.size() > 0) {
            return myCommunityList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myCommunityMembers,myCommunity;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myCommunityMembers = itemView.findViewById(R.id.memberssMyCommunity);
            myCommunity = itemView.findViewById(R.id.tvChallengesNameMyCommunity);
            linearLayout = itemView.findViewById(R.id.mycommunitylinearLayout);
        }
    }

    public interface MyCommunityInterface {
        public void MyCommunityItem(int id,String communityName);

    }

    public void setMyCommunity(MyCommunityInterface myCommunityInterface) {
        this.myCommunityInterface = myCommunityInterface;

    }
}
