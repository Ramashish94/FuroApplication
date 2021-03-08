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
import com.app.furoapp.model.winnerApi.Winner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WinnerActivityAdapter extends RecyclerView.Adapter<WinnerActivityAdapter.MyViewHolder> {

    private List<Winner> winnerList;
    private Context context;

    @NonNull
    @Override
    public WinnerActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.winnerdetailitem, parent, false);

        return new WinnerActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnerActivityAdapter.MyViewHolder holder, int position) {
        Winner data = winnerList.get(position);
        holder.actDate.setText(data.getDate());
        holder.actDuration.setText(data.getAcitivityDuration());
        holder.winnervideoview.setVideoPath(data.getFile());
        holder.actName.setText(data.getChallengeActivity());
        holder.actReps.setText(data.getActivityCount());
        holder.actTime.setText(data.getTime());
        holder.username.setText(data.getName());
        Picasso.with(context).load(data.getBlueIcon()).error(R.drawable.activity_ic).into(holder.actImage);

        Picasso.with(context).load(data.getImage()).error(R.drawable.profileiconnewmap).into(holder.profileImage);


        holder.winnervideoview.start();

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.winnervideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
                holder.winnervideoview.setVideoPath(data.getFile());
                holder.playIvButton.setVisibility(View.GONE);
                holder.winnervideoview.setVisibility(View.VISIBLE);
                holder.pauseButton.setVisibility(View.VISIBLE);


                // start playing
                holder.winnervideoview.start();
                holder.winnervideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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


        holder.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.pauseButton.setVisibility(View.GONE);
                holder.playIvButton.setVisibility(View.VISIBLE);
                holder.winnervideoview.setVisibility(View.VISIBLE);
                holder.winnervideoview.setVideoPath(data.getFile());
                // start playing
                holder.winnervideoview.pause();


            }
        });


    }

    @Override
    public int getItemCount() {
        return winnerList.size();
    }

    public WinnerActivityAdapter(List<Winner> winnerList, Context context) {

        this.winnerList = winnerList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        VideoView winnervideoview;
        ProgressBar progressBar;
        ImageView actImage, repsImage, profileImage, playIvButton, pauseButton;
        TextView actName, actReps, actDuration, actDate, actTime, username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            winnervideoview = itemView.findViewById(R.id.videoPreviewWinner);
            actImage = itemView.findViewById(R.id.WinnerActivityimage);
            repsImage = itemView.findViewById(R.id.winnerRepsImage);
            actName = itemView.findViewById(R.id.winnercompletionRate);
            actReps = itemView.findViewById(R.id.winnerreps);
            actDuration = itemView.findViewById(R.id.cwinnerTimeDuration);
            actDate = itemView.findViewById(R.id.winnerDate);
            actTime = itemView.findViewById(R.id.winnerTime);
            profileImage = itemView.findViewById(R.id.ivUserProfile);
            username = itemView.findViewById(R.id.tvJustFinishedtext);
            playIvButton = itemView.findViewById(R.id.ivPlayButtonn);
            pauseButton = itemView.findViewById(R.id.ivPlauseButtonnnnn);
            progressBar = itemView.findViewById(R.id.progressbarr);
        }
    }
}
