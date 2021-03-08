package com.app.furoapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.challengeByYouDetail.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChallengeByUserandChallengeForUserDetailActivityAdapterBy extends RecyclerView.Adapter<ChallengeByUserandChallengeForUserDetailActivityAdapterBy.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public ChallengeByUserandChallengeForUserDetailActivityAdapterBy.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challangebyyouandforyouitemby, parent, false);

        return new ChallengeByUserandChallengeForUserDetailActivityAdapterBy.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeByUserandChallengeForUserDetailActivityAdapterBy.MyViewHolder holder, int position) {
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

        Picasso.with(context).load(challengeDetails.getImage()).into(holder.videoView);




    }


    public ChallengeByUserandChallengeForUserDetailActivityAdapterBy(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoView;
        private String imagePath;
        ProgressBar progressBar = null;

        private ImageView playIvButton, pauseButton, byRepsImage;
        private TextView acitivity_duration, activity_count, challenge_name, date, name, userName, challengetimeNew,activityCountChallenge,activityChallengeNameByYou;
        private ImageView profile, userprofile, activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoPreviewByUser);


            playIvButton = itemView.findViewById(R.id.ivPlayButton);

            pauseButton = itemView.findViewById(R.id.ivPlauseButtonnnn);
            progressBar = itemView.findViewById(R.id.progressbarNew);
            activity = itemView.findViewById(R.id.imageActivityRecieweByUser);
            acitivity_duration = itemView.findViewById(R.id.challangeTimelineByUser);
            challenge_name = itemView.findViewById(R.id.challangeActivityRecieweByUser);
            name = itemView.findViewById(R.id.usernameByUser);
            date = itemView.findViewById(R.id.challangedateRecieweByUser);
            activity_count = itemView.findViewById(R.id.challangeRecieweRapByUser);
            userName = itemView.findViewById(R.id.usernameByUser);
            userprofile = itemView.findViewById(R.id.userProfileByUser);
            activityCountChallenge= itemView.findViewById(R.id.ActivityCountCategoryByUser);
            activityChallengeNameByYou= itemView.findViewById(R.id.ActivityCategoryByUser);
            challengetimeNew = itemView.findViewById(R.id.challangeTimeRecieveitemByUser);
            profile = itemView.findViewById(R.id.userProfileByUser);
        }
    }
}
