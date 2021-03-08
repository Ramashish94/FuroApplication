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

public class WinnerByYouDetailActivityAdapter extends RecyclerView.Adapter<WinnerByYouDetailActivityAdapter.MyViewHolder> {

    private List<ChallengeDetails> challengeDetailsList;
    private Context context;

    @NonNull
    @Override
    public WinnerByYouDetailActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.winnerbyyoudetailactivityadapteritem, parent, false);

        return new WinnerByYouDetailActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChallengeDetails challengeDetails = challengeDetailsList.get(position);
        holder.userName.setText(challengeDetails.getName());
        holder.acitivity_duration.setText(challengeDetails.getAcitivityDuration());
        holder.videoView.setVideoPath(challengeDetails.getVideo());
        holder.challengetime.setText(challengeDetails.getTime());
        holder.accceptDate.setText(challengeDetails.getDate());
        holder.challenegActivityName.setText(challengeDetails.getChallengeActivity());
        holder.challenegActivityCount.setText(challengeDetails.getActivityCount());
        holder.activity_count.setText(challengeDetails.getActivityCount());
        holder.challenge_name.setText(challengeDetails.getChallengeActivity());

        Picasso.with(context).load(challengeDetails.getUserimage()).error(R.drawable.user_icon).into(holder.userprofile);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.activityImageAccept);
        Picasso.with(context).load(challengeDetails.getBlackIcon()).into(holder.repsImage);


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





    public WinnerByYouDetailActivityAdapter(List<ChallengeDetails> challengeDetailsList, Context context) {

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
