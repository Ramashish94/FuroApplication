package com.app.furoapp.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ContentFeedHomeAdapter extends RecyclerView.Adapter<ContentFeedHomeAdapter.MyViewHolder> {

    private List<Datum> datumList;
    private ContentFeedInterface contentFeedInterface;
    private Context context;
    HomeMainActivity homeMainActivity;


    @NonNull
    @Override
    public ContentFeedHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_all, parent, false);


        return new ContentFeedHomeAdapter.MyViewHolder(itemView);
    }

    public ContentFeedHomeAdapter(List<Datum> datumList, Context context) {
        this.datumList = datumList;
        this.context = context;


    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedHomeAdapter.MyViewHolder holder, int position) {
        Datum datum = datumList.get(position);
        String videoid = null;

        holder.tvTitleAll.setText(datum.getType());
        Picasso.with(context).load(datum.getIcon())
                .into(holder.actIcon);
        holder.tvTextAll.setText(datum.getDescription());

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
                Picasso.with(context).load(datum.getImage())
                        .into(holder.ivImageCategoryall);

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
        private ImageView ivImageCategoryall, ivPlaybutton, actIcon;
        private TextView tvTextAll, tvTitleAll;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageCategoryall = itemView.findViewById(R.id.activityImageall);
            tvTextAll = itemView.findViewById(R.id.acTextall);
            actIcon = itemView.findViewById(R.id.iconActivity);
            tvTitleAll = itemView.findViewById(R.id.ac_titleall);
            ivPlaybutton = itemView.findViewById(R.id.ivPlayIconAllListNew);
            linearLayout = itemView.findViewById(R.id.lineaarlayout);
        }
    }

    public interface ContentFeedInterface {
        void contentFeedItem(int videoid);
        void contentFeedItem2(int id);

    }

    public void setContentFeedList(ContentFeedInterface contentFeedInterface) {
        this.contentFeedInterface = contentFeedInterface;


    }
}
