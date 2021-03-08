package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.challengeforyoudetail.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChallengeForYouDetailAdapterMap extends RecyclerView.Adapter<ChallengeForYouDetailAdapterMap.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public ChallengeForYouDetailAdapterMap.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challangeforyouandforyouitemmap, parent, false);

        return new ChallengeForYouDetailAdapterMap.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());

        holder.challengetimeNew.setText(challengeDetails.getTime());
        holder.date.setText(challengeDetails.getDate());
        holder.activityChallengeNameByYou.setText(challengeDetails.getChallengeActivity());
        holder.activityCountChallenge.setText((challengeDetails.getActivityCount()));
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());

        holder.activity_count.setText(challengeDetails.getDistance());
        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activity);

        Picasso.with(context).load(challengeDetails.getImage()).into(holder.mapImageView);





    }




    public ChallengeForYouDetailAdapterMap(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mapImageView;
        private String imagePath;
        ProgressBar progressBar = null;

        private ImageView playIvButton, pauseButton, byRepsImage;
        private TextView acitivity_duration, activity_count, challenge_name, date, name, userName, challengetimeNew,activityCountChallenge,activityChallengeNameByYou;
        private ImageView profile, userprofile, activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mapImageView = itemView.findViewById(R.id.videoPreviewByUsermap);






            activity = itemView.findViewById(R.id.imageActivityRecieweByUsermap);
            acitivity_duration = itemView.findViewById(R.id.challangeTimelineByUsermap);
            challenge_name = itemView.findViewById(R.id.challangeActivityRecieweByUsermap);
            name = itemView.findViewById(R.id.usernameByUsermap);
            date = itemView.findViewById(R.id.challangedateRecieweByUsermap);
            activity_count = itemView.findViewById(R.id.challangeRecieweRapByUsermap);
            userName = itemView.findViewById(R.id.usernameByUsermap);
            userprofile = itemView.findViewById(R.id.userProfileByUser);
            activityCountChallenge= itemView.findViewById(R.id.ActivityCountCategoryByUsermap);
            activityChallengeNameByYou= itemView.findViewById(R.id.ActivityCategoryByUsermap);
            challengetimeNew = itemView.findViewById(R.id.challangeTimeRecieveitemByUsermap);
            profile = itemView.findViewById(R.id.userProfileByUser);
        }
    }
}
