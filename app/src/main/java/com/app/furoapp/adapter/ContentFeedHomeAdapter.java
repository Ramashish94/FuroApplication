package com.app.furoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew.Datum;
import com.app.furoapp.utils.FuroPrefs;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ContentFeedHomeAdapter extends RecyclerView.Adapter<ContentFeedHomeAdapter.MyViewHolder> {

    public List<Datum> datumList;
    private Context context;
    //    public Boolean clicked = true;
    int indexPosition;
    private ContentFeedCallback feedCallback;

    public ContentFeedHomeAdapter(List<Datum> datumList, Context context, ContentFeedCallback feedCallback) {
        this.datumList = datumList;
        this.context = context;
        this.feedCallback = feedCallback;
    }

    @NonNull
    @Override
    public ContentFeedHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_all, parent, false);
        return new ContentFeedHomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Datum datum = datumList.get(position);
        String videoid = null;

        holder.tvTitleAll.setText(datum.getType());
        Picasso.with(context).load(datum.getIcon()).into(holder.actIcon);
        holder.tvTextAll.setText(datum.getDescription());
        /*added*/
        if (datum.getTotalLikes() != null)
            holder.tvCountLike.setText(datum.getTotalLikes().toString());
        if (datum.getTotalComments() != null)
            holder.tvCountsCmnt.setText(datum.getTotalComments().toString());
        if (datum.getTotalViews() != null)
            holder.tvCountViews.setText(datum.getTotalViews().toString());
        try {
            videoid = extractYoutubeId(datum.getVideo());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(videoid)) {
            String img_url = "http://img.youtube.com/vi/" + videoid + "/0.jpg";
            holder.ivPlaybutton.setVisibility(View.VISIBLE);
            Picasso.with(context).load(img_url).error(R.drawable.back_icon)
                    .into(holder.ivImageCategoryall);

            final String videoNewId = videoid;
            holder.ivPlaybutton.setOnClickListener(view -> {
                FuroPrefs.putString(context, "Youtube_Video_Id", videoNewId);
                feedCallback.contentFeedItem(position,holder.getAdapterPosition());
            });
            /*............*/
            holder.ivImageCategoryall.setOnClickListener(view -> {
                feedCallback.contentFeedItem2(position,datum.getId());
            });
            /*............*/

        } else {
            if (!TextUtils.isEmpty(datum.getImage())) {
                holder.ivPlaybutton.setVisibility(View.GONE);
                Picasso.with(context).load(datum.getImage()).into(holder.ivImageCategoryall);

                holder.ivImageCategoryall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        feedCallback.contentFeedItem2(position,datum.getId());
                    }
                });
            }
        }
        /*like*/
        holder.llLikeAndDislike.setOnClickListener(v -> {
            feedCallback.onClickLike(position, datum);
        });
        if (datum.getUserLike() != null)
            if (datum.getUserLike().equals("0")) {
                    //clicked = false;
                holder.ivLikeUnlikeImg.setImageResource(R.drawable.thumb_unlike);
                holder.tvCountLike.setTextColor(Color.BLACK);
                holder.tvLiketxt.setTextColor(Color.BLACK);
            } else {
                   //clicked = true;
                holder.ivLikeUnlikeImg.setImageResource(R.drawable.thumb_like);
                holder.tvCountLike.setTextColor(Color.parseColor("#19CFE6"));
                holder.tvLiketxt.setTextColor(Color.parseColor("#19CFE6"));
            }

        /*comments*/
        holder.llComntsSec.setOnClickListener(v -> {
            feedCallback.onClickComment(position, datum);
        });

        /*views*/
       /* holder.llViewsSec.setOnClickListener(v -> {
            feedCallback.onClickView(position, datum);
        });
        if (datum.getUserView() != null)
            if (datum.getUserView().equals("0")) {
                holder.ivViews.setImageResource(R.drawable.view_image);
                holder.tvCountViews.setTextColor(Color.BLACK);
                holder.tvView.setTextColor(Color.BLACK);
            } else {
                holder.ivViews.setImageResource(R.drawable.selectviews);
                holder.tvCountViews.setTextColor(Color.parseColor("#19CFE6"));
                holder.tvView.setTextColor(Color.parseColor("#19CFE6"));
            }*/

        /*share*/
        holder.ivShare.setOnClickListener(v -> {
            feedCallback.onClickShare(position, datum);
        });

        /*save*/
        holder.ivSave.setOnClickListener(v -> {
            feedCallback.onClickSaveBookmark(position, datum);
        });
        if (datum.getUserSave() != null)
            if (datum.getUserSave().equals("0")) {
                holder.ivSave.setImageResource(R.drawable.unselct_bookmark_ribbon);
            } else {
                holder.ivSave.setImageResource(R.drawable.select_bookmark_ribbon);

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
        if (datumList != null && datumList.size() > 0) {
            return datumList.size();
        } else {
            return 0;
        }
    }

    public void updateData(int position, Datum data) {
        datumList.set(position, data);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImageCategoryall, ivPlaybutton, actIcon;
        public TextView tvTextAll, tvTitleAll, tvCountLike, tvLiketxt, tvCountsCmnt, tvComntsTxt, tvCountViews, tvView;
        public LinearLayout linearLayout, llComntsSec, llViewsSec;
        public ImageView ivLikeUnlikeImg, ivChat, ivViews, ivShare, ivSave;
        public LinearLayout llLikeAndDislike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageCategoryall = itemView.findViewById(R.id.activityImageall);
            tvTextAll = itemView.findViewById(R.id.acTextall);
            actIcon = itemView.findViewById(R.id.iconActivity);
            tvTitleAll = itemView.findViewById(R.id.ac_titleall);
            ivPlaybutton = itemView.findViewById(R.id.ivPlayIconAllListNew);
            linearLayout = itemView.findViewById(R.id.lineaarlayout);
            llComntsSec = itemView.findViewById(R.id.llComntsSec);
            llLikeAndDislike = itemView.findViewById(R.id.llLikeAndDislike);
            ivLikeUnlikeImg = itemView.findViewById(R.id.ivUnlike);
            tvCountLike = itemView.findViewById(R.id.tvCountLike);
            tvLiketxt = itemView.findViewById(R.id.tvLiketxt);
            ivChat = itemView.findViewById(R.id.ivChat);
            tvCountsCmnt = itemView.findViewById(R.id.tvCountsCmnt);
            tvComntsTxt = itemView.findViewById(R.id.tvComntsTxt);
            ivViews = itemView.findViewById(R.id.ivViews);
            llViewsSec = itemView.findViewById(R.id.llViewsSec);
            tvCountViews = itemView.findViewById(R.id.tvCountViews);
            tvView = itemView.findViewById(R.id.tvView);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivSave = itemView.findViewById(R.id.ivBookMark);

        }
    }

    public interface ContentFeedCallback {
        void contentFeedItem(int pos,int videoId);

        void contentFeedItem2(int pos,int id);

        void onClickLike(int pos, Datum data);

        void onClickSaveBookmark(int pos, Datum data);

        void onClickShare(int pos, Datum data);

        void onClickComment(int pos, Datum data);

        void onClickView(int pos, Datum data);
    }
}
