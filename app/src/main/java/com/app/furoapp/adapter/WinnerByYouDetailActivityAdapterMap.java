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
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WinnerByYouDetailActivityAdapterMap extends RecyclerView.Adapter<WinnerByYouDetailActivityAdapterMap.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public WinnerByYouDetailActivityAdapterMap.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.winnerbyyoudetailactivityadapteritemmap, parent, false);

        return new WinnerByYouDetailActivityAdapterMap.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());
        holder.challengetime.setText(challengeDetails.getTime());
        holder.accceptDate.setText(challengeDetails.getDate());
        holder.challenegActivityName.setText(challengeDetails.getChallengeActivity());
        holder.challenegActivityCount.setText(challengeDetails.getActivityCount());
        holder.activity_count.setText(challengeDetails.getActivityCount());
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());

        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activityImageAccept);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.repsImage);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.videoView);





    }





    public WinnerByYouDetailActivityAdapterMap(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }




    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoView;
        private  String imagePath;
        ProgressBar progressBar = null;

        private ImageView playIvButton, pauseButton, repsImage;
        private TextView acitivity_duration, activity_count, challenge_name,challenegActivityCount,challenegActivityName, name, userName,challengetime ,accceptDate;
        private ImageView profile,userprofile,activityImageAccept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoPreview);


            playIvButton = itemView.findViewById(R.id.ivPlayButton);
            pauseButton = itemView.findViewById(R.id.ivPlauseButtonnnn);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            activity_count=itemView.findViewById(R.id.AcceptchallangeRap);
            acitivity_duration = itemView.findViewById(R.id.AcceptchallangeTimeline);
            challenge_name = itemView.findViewById(R.id.challangeActivity);
            name = itemView.findViewById(R.id.usernameAccept);
            repsImage= itemView.findViewById(R.id.challengeimagereps);
            accceptDate = itemView.findViewById(R.id.Acceptchallangedate);
            userName = itemView.findViewById(R.id.usernameAccept);
            activityImageAccept=itemView.findViewById(R.id.acceptchallengeactivityimage);
            userprofile = itemView.findViewById(R.id.userProfileAccept);
            challengetime=itemView.findViewById(R.id.AcceptchallangeTime);
            challenegActivityCount=itemView.findViewById(R.id.tvActivityCategoryCountt);
            challenegActivityName=itemView.findViewById(R.id.tvActivityCategoryName);
            profile = itemView.findViewById(R.id.userProfile);
        }
    }
}
