package com.app.furoapp.activity.newFeature.likeAndSaved.likedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost.SavedOnPost;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost.LikeOnPost;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.MyViewHolder> {

    private Context context;
    List<LikeOnPost> likeOnPostList;

    public LikeListAdapter(Context applicationContext, List<LikeOnPost> likeOnPostList) {
        this.context = applicationContext;
        this.likeOnPostList = likeOnPostList;
    }

    @NonNull
    @Override
    public LikeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmarked_saved, parent, false);

        return new LikeListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeListAdapter.MyViewHolder holder, int position) {
        LikeOnPost likeOnPost = likeOnPostList.get(holder.getAdapterPosition());
        holder.tvTittle.setText("" + likeOnPost.getTitle());
        holder.tvAcTextall.setText("" + likeOnPost.getDescription());
    }

    @Override
    public int getItemCount() {
        if (likeOnPostList != null && likeOnPostList.size() > 0) {
            return likeOnPostList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivActivityImageAll, ivIconActivity, ivPlayIconAllListNew;
        public YouTubePlayerView youtubebe_player;
        public TextView tvTittle, tvAcTextall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivActivityImageAll = itemView.findViewById(R.id.ivActivityImageAll);
            ivIconActivity = itemView.findViewById(R.id.ivIconActivity);
            ivPlayIconAllListNew = itemView.findViewById(R.id.ivPlayIconAllListNew);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvAcTextall = itemView.findViewById(R.id.tvAcTextall);
        }
    }
}
