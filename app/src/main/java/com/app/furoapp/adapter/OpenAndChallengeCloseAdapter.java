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
import com.app.furoapp.model.challengeByUser.SendChallenge;

import java.util.List;

public class OpenAndChallengeCloseAdapter extends RecyclerView.Adapter<OpenAndChallengeCloseAdapter.MyViewHolder> {

    private List<SendChallenge> sendChallengeslist;
    private ChallengeByYouInterface challengeByYouInterface;

    private Context context;


    @NonNull
    @Override
    public OpenAndChallengeCloseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.openandchallengeitem, parent, false);

        return new OpenAndChallengeCloseAdapter.MyViewHolder(itemView);
    }

    public OpenAndChallengeCloseAdapter(List<SendChallenge> sendChallengeslist, Context context) {
        this.sendChallengeslist = sendChallengeslist;
        this.context = context;


    }


    @Override
    public void onBindViewHolder(@NonNull OpenAndChallengeCloseAdapter.MyViewHolder holder, int position) {

        SendChallenge sendChallenge = sendChallengeslist.get(position);
        holder.textViewDate.setText(sendChallenge.getDate());
        holder.textViewFriendNew.setText(sendChallenge.getCountChallengers());
        holder.textViewActivity.setText(sendChallenge.getChallengeActivity());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (challengeByYouInterface != null) {
                    challengeByYouInterface.challengeForYouItem(sendChallenge.getChallengeId(),holder.getAdapterPosition());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return sendChallengeslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewFriendNew, textViewActivity;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewActivity = itemView.findViewById(R.id.activityopennew);
            textViewDate = itemView.findViewById(R.id.dateopennew);
            textViewFriendNew = itemView.findViewById(R.id.friendsopennewFuro);
            linearLayout = itemView.findViewById(R.id.challengeForyouLinearLayout);
        }
    }


    public interface ChallengeByYouInterface {
        void challengeForYouItem(String IdChallenge,int position);

    }

    public void setChallengeByYouItemList(ChallengeByYouInterface challengeByYouInterface) {
        this.challengeByYouInterface = challengeByYouInterface;

    }


}
