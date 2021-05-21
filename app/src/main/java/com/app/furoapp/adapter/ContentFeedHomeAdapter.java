package com.app.furoapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.model.content_feed.activityListing.Datum;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.widget.SwitchButton;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ContentFeedHomeAdapter extends RecyclerView.Adapter<ContentFeedHomeAdapter.MyViewHolder> {

    public List<Datum> datumList;
    private ContentFeedInterface contentFeedInterface;
    private Context context;
    HomeMainActivity homeMainActivity;
    public Boolean clicked = true;
    int indexPosition;

    public ContentFeedHomeAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;
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
        /*holder.tvCountLike.setText(datum.getTotalLikes());
        holder.tvCountsCmnt.setText(datum.getTotalComments());
        holder.tvCountViews.setText(datum.getTotalViews());*/
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
            holder.ivPlaybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (contentFeedInterface != null) {
                        FuroPrefs.putString(context, "Youtube_Video_Id", videoNewId);
                        contentFeedInterface.contentFeedItem(holder.getAdapterPosition());
                    }
                }
            });
            /*added*/
           /* holder.llLikeAndDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLikeCommentsShareViewsBookmarkClick != null) {
                        FuroPrefs.putString(context, "Youtube_Video_Id", videoNewId);
                    }
                }
            });*/

            holder.ivImageCategoryall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (contentFeedInterface != null) {
                        contentFeedInterface.contentFeedItem2(datum.getId());
                    }

                }
            });

        } else {
            if (!TextUtils.isEmpty(datum.getImage())) {
                holder.ivPlaybutton.setVisibility(View.GONE);
                Picasso.with(context).load(datum.getImage()).into(holder.ivImageCategoryall);

                holder.ivImageCategoryall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (contentFeedInterface != null) {
                            contentFeedInterface.contentFeedItem2(datum.getId());
                        }

                    }
                });
            }
        }

        holder.llLikeAndDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    clicked = false;
                    holder.ivLikeUnlikeImg.setImageResource(R.drawable.thumb_like);
                    holder.tvCountLike.setTextColor(Color.parseColor("#19CFE6"));
                    holder.tvLiketxt.setTextColor(Color.parseColor("#19CFE6"));
                } else {
                    clicked = true;
                    holder.ivLikeUnlikeImg.setImageResource(R.drawable.thumb_unlike);
                    holder.tvCountLike.setTextColor(Color.BLACK);
                    holder.tvLiketxt.setTextColor(Color.BLACK);
                }
            }
        });
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImageCategoryall, ivPlaybutton, actIcon;
        public TextView tvTextAll, tvTitleAll, tvCountLike, tvLiketxt, tvCountsCmnt, tvComntsTxt, tvCountViews, tvView;
        public LinearLayout linearLayout, llComntsSec;
        public ImageView ivLikeUnlikeImg, ivChat, ivViews, ivShare;
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
            tvCountViews = itemView.findViewById(R.id.tvCountViews);
            tvView = itemView.findViewById(R.id.tvView);
            ivShare = itemView.findViewById(R.id.ivShare);

        }
    }

    public interface ContentFeedInterface {
        void contentFeedItem(int videoid);

        void contentFeedItem2(int id);

    }

    public void setContentFeedList(ContentFeedInterface contentFeedInterface) {
        this.contentFeedInterface = contentFeedInterface;
    }

    /*added*/
    public OnLikeCommentsShareViewsBookmarkClick onLikeCommentsShareViewsBookmarkClick;

    public void OnLikeCommentsShareViewsBookmark(OnLikeCommentsShareViewsBookmarkClick onLikeCommentsShareViewsBookmark) {
        this.onLikeCommentsShareViewsBookmarkClick = onLikeCommentsShareViewsBookmarkClick;
    }

    public interface OnLikeCommentsShareViewsBookmarkClick {
        void onLikeAndDislikeClick(int id);

        void onCommentsUnCommentsClick();

        void onViewsClick(int id);

        Void onBookedUnBookMarkedClick(int id);
    }

}
