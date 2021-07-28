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
import com.app.furoapp.model.challengeforyouRecieve.ReceiveChallenge;

import java.util.List;

public class ChallangesForYouAdapter extends RecyclerView.Adapter<ChallangesForYouAdapter.MyViewHolder> {
    ChallengeForYouInterface challengeForYouInterface;

    private List<ReceiveChallenge> receiveChallenges;
    private Context context;

    ContentFeedDetailAdapter.ContentFeedDetailInterface contentFeedDetailInterface;
    @NonNull
    @Override
    public ChallangesForYouAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challengesforyouitem, parent, false);

        return new ChallangesForYouAdapter.MyViewHolder(itemView);
    }

    public ChallangesForYouAdapter(List<ReceiveChallenge> receiveChallenges, Context context) {
        this.receiveChallenges = receiveChallenges;
        this.context = context;


    }



    @Override
    public void onBindViewHolder(@NonNull ChallangesForYouAdapter.MyViewHolder holder, int position) {

        ReceiveChallenge receiveChallenge = receiveChallenges.get(position);
        holder.textViewDate.setText(receiveChallenge.getDate());
        holder.textViewActivity.setText(receiveChallenge.getChallengeActivity());
        holder.textViewFriend.setText(receiveChallenge.getChallengedBy());
        holder.linearLayoutForYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (challengeForYouInterface != null) {
                    challengeForYouInterface.challengeForYouItem(receiveChallenge.getChallengeId(),holder.getAdapterPosition());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (receiveChallenges != null && receiveChallenges.size() > 0) {
            return receiveChallenges.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate,textViewFriend,textViewActivity;
        LinearLayout linearLayoutForYou;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewActivity= itemView.findViewById(R.id.activityopen);
            textViewDate= itemView.findViewById(R.id.dateopen);
            linearLayoutForYou = itemView.findViewById(R.id.linearLayoutForYou);
            textViewFriend = itemView.findViewById(R.id.friendsopen);
        }
    }

    public interface ChallengeForYouInterface {
        void challengeForYouItem(String idChallenge,int position);

    }

    public void setChallengeForYouItemList(ChallengeForYouInterface challengeForYouInterface) {
        this.challengeForYouInterface = challengeForYouInterface;

    }
}
