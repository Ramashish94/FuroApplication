package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.mapchallenge.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MapActivityAcceptChallengeAdapter extends RecyclerView.Adapter<MapActivityAcceptChallengeAdapter.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public MapActivityAcceptChallengeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_accept_challenge_map_item, parent, false);

        return new MapActivityAcceptChallengeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MapActivityAcceptChallengeAdapter.MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());
        holder.challengetime.setText(challengeDetails.getTime());
        holder.accceptDate.setText(challengeDetails.getDate());
        holder.challenegActivityCount.setText(challengeDetails.getDistance());
        holder.challenegActivityName.setText(challengeDetails.getChallengeActivity());

        holder.distance.setText(challengeDetails.getDistance());
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());
        holder.recduration.setText(challengeDetails.getAcitivityDuration());
        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activityImageAccept);

        Picasso.with(context).load(challengeDetails.getImage()).into(holder.mapImage);


    }


    public MapActivityAcceptChallengeAdapter(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView repsImage;
        private TextView acitivity_duration, recduration, distance, challenge_name, challenegActivityCount, challenegActivityName, name, userName, challengetime, accceptDate;
        private ImageView mapImage, profile, userprofile, activityImageAccept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            recduration = itemView.findViewById(R.id.reciveDurationmap);
            distance = itemView.findViewById(R.id.AcceptchallangeRap);
            acitivity_duration = itemView.findViewById(R.id.AcceptchallangeTimeline);
            challenge_name = itemView.findViewById(R.id.challangeActivity);
            name = itemView.findViewById(R.id.usernameAccept);
            repsImage = itemView.findViewById(R.id.challengeimagereps);
            accceptDate = itemView.findViewById(R.id.Acceptchallangedate);
            userName = itemView.findViewById(R.id.usernameAccept);
            mapImage = itemView.findViewById(R.id.mapImageprieview);
            activityImageAccept = itemView.findViewById(R.id.acceptchallengeactivityimage);
            userprofile = itemView.findViewById(R.id.userProfileAccept);
            challengetime = itemView.findViewById(R.id.AcceptchallangeTime);
            challenegActivityCount = itemView.findViewById(R.id.tvActivityCategoryCounttdis);
            challenegActivityName = itemView.findViewById(R.id.tvActivityCategoryName);
            profile = itemView.findViewById(R.id.userProfile);
        }
    }
}
