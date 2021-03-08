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
import com.app.furoapp.model.winnerApi.Loser;
import com.app.furoapp.model.winnerApi.Winner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoserDetailMapAdapter extends RecyclerView.Adapter<LoserDetailMapAdapter.MyViewHolder> {

    private List<Loser> winnerList;
    private Context context;

    @NonNull
    @Override
    public LoserDetailMapAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loserdetailmapitem, parent, false);

        return new LoserDetailMapAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoserDetailMapAdapter.MyViewHolder holder, int position) {
        Loser data = winnerList.get(position);
        holder.actDate.setText(data.getDate());
        holder.actDuration.setText(data.getAcitivityDuration());

        holder.actName.setText(data.getChallengeActivity());
        holder.actReps.setText(data.getActivityCount());
        holder.actTime.setText(data.getTime());
        holder.username.setText(data.getName());
        Picasso.with(context).load(data.getBlueIcon()).error(R.drawable.activity_ic).into(holder.actImage);


        Picasso.with(context).load(data.getFile()).into(holder.winnervideoview);
        Picasso.with(context).load(data.getImage()).error(R.drawable.profileiconnewmap).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return winnerList.size();
    }

    public LoserDetailMapAdapter(List<Loser> winnerList, Context context) {

        this.winnerList = winnerList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView winnervideoview;

        ImageView actImage, repsImage, profileImage, playIvButton, pauseButton;
        TextView actName, actReps, actDuration, actDate, actTime, username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            winnervideoview = itemView.findViewById(R.id.videoPreviewLoserMap);
            actImage = itemView.findViewById(R.id.WinnerActivityimageemapp);
            repsImage = itemView.findViewById(R.id.winnerRepsImageemapp);
            actName = itemView.findViewById(R.id.winnercompletionRateemapp);
            actReps = itemView.findViewById(R.id.winnerrepssmapp);
            actDuration = itemView.findViewById(R.id.cwinnerTimeDurationnmapp);
            actDate = itemView.findViewById(R.id.winnerDateemapp);
            actTime = itemView.findViewById(R.id.winnerTimeemapp);
            profileImage = itemView.findViewById(R.id.ivUserProfileLosermapp);
            username = itemView.findViewById(R.id.tvJustFinishedtexttmapp);

        }
    }
}
