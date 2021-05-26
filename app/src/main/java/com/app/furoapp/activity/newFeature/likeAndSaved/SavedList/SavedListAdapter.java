package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList;

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
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MyViewHolder> {

    private Context context;
    List<SavedOnPost> savedOnPostList;

    public SavedListAdapter(Context applicationContext, List<SavedOnPost> savedOnPostList) {
        this.context = applicationContext;
        this.savedOnPostList = savedOnPostList;
    }

    @NonNull
    @Override
    public SavedListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmarked_saved, parent, false);

        return new SavedListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedListAdapter.MyViewHolder holder, int position) {
        SavedOnPost savedOnPost = savedOnPostList.get(holder.getAdapterPosition());
        holder.tvTittle.setText("" + savedOnPost.getTitle());
        holder.tvAcTextall.setText("" + savedOnPost.getDescription());
    }

    @Override
    public int getItemCount() {
        if (savedOnPostList != null && savedOnPostList.size() > 0) {
            return savedOnPostList.size();
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
