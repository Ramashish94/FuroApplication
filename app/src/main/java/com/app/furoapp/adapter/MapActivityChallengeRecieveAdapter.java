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
import com.app.furoapp.model.mapchallenge.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MapActivityChallengeRecieveAdapter extends RecyclerView.Adapter<MapActivityChallengeRecieveAdapter.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public MapActivityChallengeRecieveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challangerecievemapitem, parent, false);

        return new MapActivityChallengeRecieveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MapActivityChallengeRecieveAdapter.MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());
        holder.challActCount.setText(challengeDetails.getDistance());
        holder.challengetimeNew.setText(challengeDetails.getTime());
        holder.date.setText(challengeDetails.getDate());
        holder.recdura.setText(challengeDetails.getAcitivityDuration());

        holder.distance.setText(challengeDetails.getDistance());
        holder.challActName.setText(challengeDetails.getChallengeActivity());
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());

        Picasso.with(context).load(challengeDetails.getImage()).into(holder.mapImage);
        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activity);
       /* Picasso.with(context).load(challengeDetails.getBlueIcon()).into(holder.challRepsImage);*/


    }

    public MapActivityChallengeRecieveAdapter(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mapImage;
        private  String imagePath;
        ProgressBar progressBar = null;

        private ImageView playIvButton, pauseButton, challRepsImage;
        private TextView acitivity_duration,distance, activity_count,challActCount,challActName, challenge_name,date,recdura, name, userName,challengetimeNew;
        private ImageView profile,userprofile,activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mapImage = itemView.findViewById(R.id.mapPreview);



            challRepsImage=itemView.findViewById(R.id.challengerepsicon);

            distance = itemView.findViewById(R.id.challangeRecieweRapdistance);

            activity=itemView.findViewById(R.id.imageActivityReciewe);
            acitivity_duration = itemView.findViewById(R.id.challangeTimeline);
            challenge_name = itemView.findViewById(R.id.challangeActivityReciewe);
            name = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.challangedateReciewe);
            activity_count=itemView.findViewById(R.id.challangeRecieweRap);
            userName = itemView.findViewById(R.id.username);
            recdura = itemView.findViewById(R.id.reciveDuration);
            challActCount=itemView.findViewById(R.id.tvActivityCategorycount);
            challActName = itemView.findViewById(R.id.tvActivityCategoryName);

            userprofile = itemView.findViewById(R.id.userProfile);
            challengetimeNew=itemView.findViewById(R.id.challangeTimeRecieveitem);
            profile = itemView.findViewById(R.id.userProfile);
        }
    }
}
