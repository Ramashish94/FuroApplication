package com.app.furoapp.activity.newFeature.likeAndSaved.SavedList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost.SavedOnPost;
import com.app.furoapp.utils.FuroPrefs;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MyViewHolder> {

    private Context context;
    List<SavedOnPost> savedOnPostList;
    public ContentSavedCallback contentSavedCallback;
    public String imageUrl = "https://api.fitnessquotient.in/common/images/blogs/";

    public SavedListAdapter(Context applicationContext, List<SavedOnPost> savedOnPostList, ContentSavedCallback contentSavedCallback) {
        this.context = applicationContext;
        this.savedOnPostList = savedOnPostList;
        this.contentSavedCallback = contentSavedCallback;
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
        holder.tvTopTitel.setText("" + savedOnPost);
        Picasso.with(context).load(savedOnPost.getActivityDetail().getIcon()).error(R.drawable.fq_bannnerrrrrr).into(holder.ivTopIcon);
        // Picasso.with(context).load(savedOnPost.getActivityDetail().getImageUrl()).error(R.drawable.fq_bannnerrrrrr).into(holder.ivAllImage);
        holder.tvBottomTittal.setText("" + savedOnPost.getActivityDetail().getDescription());
        String videoid = null;

        try {
            videoid = extractYoutubeId((String) savedOnPost.getActivityDetail().getVideo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(videoid)) {
            String img_url = "http://img.youtube.com/vi/" + videoid + "/0.jpg";
            holder.ivPlayIcon.setVisibility(View.VISIBLE);
            Picasso.with(context).load(img_url).error(R.drawable.back_icon)
                    .into(holder.ivAllImage);

            final String videoNewId = videoid;
            holder.ivPlayIcon.setOnClickListener(view -> {
                FuroPrefs.putString(context, "Youtube_Video_Id", videoNewId);
                contentSavedCallback.contentSavedItem(holder.getAdapterPosition());
            });

//            holder.ivAllImage.setOnClickListener(view -> {
//                contentSavedCallback.contentSavedItem2(savedOnPost.getId());
//            });
        } else {
            if (!TextUtils.isEmpty(savedOnPost.getActivityDetail().getImageUrl())) {
                holder.ivPlayIcon.setVisibility(View.GONE);
                Picasso.with(context).load(savedOnPost.getActivityDetail().getImageUrl()).into(holder.ivAllImage);
                holder.ivAllImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contentSavedCallback.contentSavedItem2(savedOnPost.getPostId());
                    }
                });
            }
        }
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        try {
            String query = new URL(url).getQuery();
            String[] param = query.split("&");
            String id = null;
            for (String row : param) {
                String[] param1 = row.split("=");
                if (param1[0].equals("v")) {
                    id = param1[1];
                }
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
        public ImageView ivAllImage, ivTopIcon, ivPlayIcon;
        public YouTubePlayerView youtubebe_player;
        public TextView tvTopTitel, tvBottomTittal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAllImage = itemView.findViewById(R.id.ivAllImage);
            ivTopIcon = itemView.findViewById(R.id.ivTopIcon);
            ivPlayIcon = itemView.findViewById(R.id.ivPlayIcon);
            tvTopTitel = itemView.findViewById(R.id.tvTopTitel);
            tvBottomTittal = itemView.findViewById(R.id.tvBottomTittal);
        }
    }

    public interface ContentSavedCallback {
        void contentSavedItem(int videoId);

        void contentSavedItem2(int id);

//        void onClickLike(int pos, Datum data);
//
//        void onClickSaveBookmark(int pos, Datum data);
//
//        void onClickShare(int pos, Datum data);
//
//        void onClickComment(int pos, Datum data);
//
//        void onClickView(int pos, Datum data);
    }
}
