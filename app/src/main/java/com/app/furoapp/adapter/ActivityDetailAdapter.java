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
import com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew.BodyItem;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ActivityDetailAdapter extends RecyclerView.Adapter<ActivityDetailAdapter.MyViewHolder> {
    private List<BodyItem> bodyList;
    private Context context;
    String video_id2;
    ContentFeedDetailInterface contentFeedDetailInterface;

    public ActivityDetailAdapter(List<BodyItem> bodyList, Context context) {
        this.bodyList = bodyList;
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_feed_detail_recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BodyItem body = bodyList.get(position);

        holder.mainParagraph.setVisibility(View.GONE);
        holder.mainTitle2.setVisibility(View.GONE);
        holder.mainTitle1.setVisibility(View.GONE);
        holder.linkk.setVisibility(View.GONE);
        holder.contentdetailimagecf.setVisibility(View.GONE);

        if (body.getType().equalsIgnoreCase("title1")) {
            holder.mainTitle1.setVisibility(View.VISIBLE);
            holder.mainTitle1.setText(body.getValue());


        }
        if (body.getType().equalsIgnoreCase("image")) {
            holder.contentdetailimagecf.setVisibility(View.VISIBLE);
            Picasso.with(context).load(body.getValue())
                    .into(holder.contentdetailimagecf);


        }
        if (body.getType().equalsIgnoreCase("paragraph")) {
            holder.mainParagraph.setVisibility(View.VISIBLE);
            holder.mainParagraph.setText(body.getValue());


        }
        if (body.getType().equalsIgnoreCase("title2")) {
            holder.mainTitle2.setVisibility(View.VISIBLE);
            holder.mainTitle2.setText(body.getValue());


        }
        if (body.getType().equalsIgnoreCase("video")) {
            holder.contentdetailimagecf.setVisibility(View.VISIBLE);

            try {
                video_id2 = extractYoutubeId(body.getValue());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(video_id2)) {
                String img_url = "http://img.youtube.com/vi/" + video_id2 + "/0.jpg";
//                String img_url  = "https://img.youtube.com/vi/" + video_id2 + "/hqdefault.jpg";
                holder.ivPlayButton.setVisibility(View.VISIBLE);
                Picasso.with(context).load(img_url).error(R.drawable.back_icon)
                        .into(holder.contentdetailimagecf);


            }


        }
        if (body.getType().equalsIgnoreCase("link")) {
            holder.linkk.setVisibility(View.VISIBLE);
            holder.linkk.setText(body.getValue());


        }
        holder.ivPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.youTubePlayer.setVisibility(View.VISIBLE);
                holder.contentdetailimagecf.setVisibility(View.GONE);

                holder.youTubePlayer.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {

                        youTubePlayer.loadVideo(video_id2, 0f);

                    }

                    @Override
                    public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

                    }

                    @Override
                    public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                    }

                    @Override
                    public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                    }

                    @Override
                    public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                    }

                    @Override
                    public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                    }

                    @Override
                    public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                    }
                });


            }


        });

    }

    @Override
    public int getItemCount() {
        if (bodyList != null && bodyList.size() > 0) {
            return bodyList.size();
        } else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mainTitle1, mainTitle2, mainParagraph, linkk;
        private ImageView contentdetailimagecf, ivPlayButton;
        private LinearLayout relativeLayoutImage;
        YouTubePlayerView youTubePlayer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayer = itemView.findViewById(R.id.youtubebe_player);

            relativeLayoutImage = itemView.findViewById(R.id.relativelayout);
            mainTitle1 = itemView.findViewById(R.id.title1);
            mainTitle2 = itemView.findViewById(R.id.title2);
            mainParagraph = itemView.findViewById(R.id.paragraph);
            contentdetailimagecf = itemView.findViewById(R.id.contentdetailimage);
            ivPlayButton = itemView.findViewById(R.id.ivPlayIconAllListNew);
            linkk = itemView.findViewById(R.id.link);
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

    public interface ContentFeedDetailInterface {
        void contentFeedDetailItem();

    }

    public void setContentDetailList(ContentFeedDetailInterface contentFeedFoodInterface) {
        this.contentFeedDetailInterface = contentFeedDetailInterface;

    }
}


