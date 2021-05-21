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
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {

    private Context context;
    List<SavedTestModel> savedTestModelList;

    public SavedAdapter(Context applicationContext, List<SavedTestModel> savedTestModelList) {
        this.context = applicationContext;
        this.savedTestModelList = savedTestModelList;
    }

    @NonNull
    @Override
    public SavedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmarked_saved, parent, false);

        return new SavedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.MyViewHolder holder, int position) {
        SavedTestModel savedTestModel = savedTestModelList.get(position);
        holder.tvTittle.setText("" + savedTestModel.getTittle());
        holder.tvAcTextall.setText("" + savedTestModel.getDefineText());
    }

    @Override
    public int getItemCount() {
        if (savedTestModelList != null && savedTestModelList.size() > 0) {
            return savedTestModelList.size();
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
