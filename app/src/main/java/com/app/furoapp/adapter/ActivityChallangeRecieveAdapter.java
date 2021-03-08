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
import com.app.furoapp.model.challengedetail.ChallengeDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityChallangeRecieveAdapter extends RecyclerView.Adapter<ActivityChallangeRecieveAdapter.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public ActivityChallangeRecieveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challangerecieveitem, parent, false);

        return new ActivityChallangeRecieveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityChallangeRecieveAdapter.MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());
        holder.videoView.setVideoPath(challengeDetails.getVideo());
        holder.challengetimeNew.setText(challengeDetails.getTime());
        holder.date.setText(challengeDetails.getDate());

        holder.challActCount.setText(challengeDetails.getActivityCount());
        holder.challActName.setText(challengeDetails.getChallengeActivity());
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());
        holder.activity_count.setText(challengeDetails.getActivityCount());
        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activity);
        /*Picasso.with(context).load(challengeDetails.getBlueIcon()).into(holder.challRepsImage);*/


        holder.videoView.start();

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        holder.progressBar.setVisibility(View.GONE);
                        holder.pauseButton.setVisibility(View.VISIBLE);
                        mp.start();
                    }
                });
            }
        });


        holder.playIvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.setVideoPath(challengeDetails.getVideo());
                holder.playIvButton.setVisibility(View.GONE);
                holder.videoView.setVisibility(View.VISIBLE);
                holder.pauseButton.setVisibility(View.VISIBLE);


                // start playing
                holder.videoView.start();
                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        mp.start();
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                           int arg2) {
                                // TODO Auto-generated method stub
                                holder.progressBar.setVisibility(View.GONE);

                                mp.start();

                            }
                        });
                    }
                });
            }
        });



        holder. pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.  pauseButton.setVisibility(View.GONE);
                holder.  playIvButton.setVisibility(View.VISIBLE);
                holder. videoView.setVisibility(View.VISIBLE);
                holder. videoView.setVideoPath(challengeDetails.getVideo());
                // start playing
                holder. videoView.pause();



            }
        });


    }





    public ActivityChallangeRecieveAdapter(List<ChallengeDetails> challengeDetailsList, Context context) {

        this.challengeDetailsList = challengeDetailsList;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return challengeDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;
        private  String imagePath;
        ProgressBar progressBar = null;

        private ImageView playIvButton, pauseButton, challRepsImage;
        private TextView acitivity_duration, activity_count,challActCount,challActName, challenge_name,date, name, userName,challengetimeNew;
        private ImageView profile,userprofile,activity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoPreview);


            playIvButton = itemView.findViewById(R.id.ivPlayButton);

            pauseButton = itemView.findViewById(R.id.ivPlauseButtonnnn);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            activity=itemView.findViewById(R.id.imageActivityReciewe);
            acitivity_duration = itemView.findViewById(R.id.challangeTimeline);
            challenge_name = itemView.findViewById(R.id.challangeActivityReciewe);
            name = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.challangedateReciewe);
            activity_count=itemView.findViewById(R.id.challangeRecieweRap);
            userName = itemView.findViewById(R.id.username);
            challActCount=itemView.findViewById(R.id.tvActivityCategorycount);
            challActName = itemView.findViewById(R.id.tvActivityCategoryName);

            userprofile = itemView.findViewById(R.id.userProfile);
            challengetimeNew=itemView.findViewById(R.id.challangeTimeRecieveitem);
            profile = itemView.findViewById(R.id.userProfile);
        }
    }
}
