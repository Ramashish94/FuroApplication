package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.ChallengeItemModel;
import com.app.furoapp.model.ClubChallengeModel;

import java.util.List;

public class ProfileFlexibityChallangeAadapter extends RecyclerView.Adapter<ProfileFlexibityChallangeAadapter.MyViewHolder> {

    private List<ChallengeItemModel>challengeItemModelList;
    private Context context;

    @NonNull
    @Override
    public ProfileFlexibityChallangeAadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_profile_your_activity, parent, false);

        return new ProfileFlexibityChallangeAadapter.MyViewHolder(itemView);
    }

    public ProfileFlexibityChallangeAadapter( List<ChallengeItemModel>challengeItemModelList,Context context) {
        this.challengeItemModelList = challengeItemModelList;
        this.context= context;


    }

    @Override
    public void onBindViewHolder(@NonNull ProfileFlexibityChallangeAadapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return challengeItemModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivItemActivityItem);
        }
    }
}
