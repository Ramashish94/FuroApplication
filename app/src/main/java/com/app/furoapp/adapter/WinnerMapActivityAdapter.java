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

public class WinnerMapActivityAdapter extends RecyclerView.Adapter<WinnerMapActivityAdapter.MyViewHolder> {

    private List<Winner> winnerList;
    private Context context;

    @NonNull
    @Override
    public WinnerMapActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.winnerdetailmapitem, parent, false);

        return new WinnerMapActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnerMapActivityAdapter.MyViewHolder holder, int position) {
        Winner data = winnerList.get(position);
        holder.actDate.setText(data.getDate());
        holder.actDuration.setText(data.getAcitivityDuration());

        holder.actName.setText(data.getChallengeActivity());
        holder.actReps.setText(data.getActivityCount());
        holder.actTime.setText(data.getTime());
        holder.username.setText(data.getName());
        Picasso.with(context).load(data.getBlueIcon()).error(R.drawable.activity_ic).into(holder.actImage);

        Picasso.with(context).load(data.getImage()).error(R.drawable.profileiconnewmap).into(holder.profileImage);
        Picasso.with(context).load(data.getFile()).into(holder.winnervideoview);






    }

    @Override
    public int getItemCount() {
        return winnerList.size();
    }

    public WinnerMapActivityAdapter(List<Winner> winnerList, Context context) {

        this.winnerList = winnerList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView winnervideoview;

        ImageView actImage, repsImage, profileImage, playIvButton, pauseButton;
        TextView actName, actReps, actDuration, actDate, actTime, username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            winnervideoview = itemView.findViewById(R.id.videoPreviewWinnerMap);
            actImage = itemView.findViewById(R.id.WinnerActivityimagemap);
            repsImage = itemView.findViewById(R.id.winnerRepsImagemap);
            actName = itemView.findViewById(R.id.winnercompletionRatemap);
            actReps = itemView.findViewById(R.id.winnerrepsmap);
            actDuration = itemView.findViewById(R.id.cwinnerTimeDurationmap);
            actDate = itemView.findViewById(R.id.winnerDatemap);
            actTime = itemView.findViewById(R.id.winnerTimemap);
            profileImage = itemView.findViewById(R.id.ivUserProfilemap);
            username = itemView.findViewById(R.id.tvJustFinishedtextmap);


        }
    }
}
