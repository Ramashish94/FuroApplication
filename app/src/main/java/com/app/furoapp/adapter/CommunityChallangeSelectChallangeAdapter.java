package com.app.furoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.ChallangeRuleActivity;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.fragment.challenges.HomeChallengesFragment;
import com.app.furoapp.model.ChallengeItemModel;
import com.app.furoapp.model.communityChallange.Challenge;

import java.util.List;

import static com.app.furoapp.enums.EnumConstants.COMMUNITY_CHALLENGE_JOIN_FRAGMENT;

public class CommunityChallangeSelectChallangeAdapter extends RecyclerView.Adapter<CommunityChallangeSelectChallangeAdapter.MyViewHolder> {

    private List<Challenge> challengeItemModelList;

    private Context context;
    private CommunityChallangeSelectChallangeInterface communityChallangeSelectChallangeInterface;

    @NonNull
    @Override
    public CommunityChallangeSelectChallangeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_community_selected_row, parent, false);

        return new CommunityChallangeSelectChallangeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        Challenge challengem= challengeItemModelList.get(position);
        holder.communityClubChallange.setText(challengem.getChallenge());
        holder.challangeCompletion.setText(challengem.getCompletionRate());
        holder.challangeTarget.setText(challengem.getTarget());
        holder.hallangeTimeline.setText(challengem.getTimeline());

        holder.outerlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayoutButtons.setVisibility(View.VISIBLE);
                holder.ruleTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, ChallangeRuleActivity.class);
                        context.startActivity(i);

                    }
                });
                holder.acceptChallangeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      Bundle bundle =  new Bundle();


                        if (context instanceof HomeMainActivity) {
                            ((HomeMainActivity)context).setDisplayFragment(COMMUNITY_CHALLENGE_JOIN_FRAGMENT,bundle);
                        }
                    }
                });

            }

        });


    }

    public CommunityChallangeSelectChallangeAdapter(List<Challenge> challengeItemModelList, Context context) {
        this.challengeItemModelList = challengeItemModelList;
        this.context = context;


    }


    @Override
    public int getItemCount() {
        if(challengeItemModelList != null && challengeItemModelList.size()>0){
            return challengeItemModelList.size();
        }else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        LinearLayout linearLayoutRoad, linearLayoutCircle, linearLayoutButtons, outerlinearlayout;
        TextView ruleTextView,acceptChallangeTextView,communityClubChallange,challangeCompletion,challangeTarget,hallangeTimeline;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutCircle = itemView.findViewById(R.id.imagecircle);
            linearLayoutRoad = itemView.findViewById(R.id.imageRoad);
            linearLayoutRoad = itemView.findViewById(R.id.imagewatch);
            imageView = itemView.findViewById(R.id.image1);
            communityClubChallange= itemView.findViewById(R.id.communityClubchallangename);
            challangeCompletion= itemView.findViewById(R.id.completionRate);
            challangeTarget= itemView.findViewById(R.id.challangetarget);
            hallangeTimeline= itemView.findViewById(R.id.challangeTimeline);

            ruleTextView = itemView.findViewById(R.id.tvRules);
            acceptChallangeTextView= itemView.findViewById(R.id.tvAcceptChallengeBtn);
            linearLayoutButtons = itemView.findViewById(R.id.llBottomChallengeVIew);
            outerlinearlayout = itemView.findViewById(R.id.linearlayout);
        }
    }

    public interface CommunityChallangeSelectChallangeInterface {
        public void allCommunityChallangeSelectChallangeItem(int position);

    }

    public void setAllCommunityChallangeSelectChallange(CommunityChallangeSelectChallangeInterface communityChallangeSelectChallangeInterface) {
        this.communityChallangeSelectChallangeInterface = communityChallangeSelectChallangeInterface;

    }
}
