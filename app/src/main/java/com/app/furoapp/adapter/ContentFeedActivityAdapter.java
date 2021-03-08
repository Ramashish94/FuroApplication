package com.app.furoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.furoapp.R;
import com.app.furoapp.model.content_feed.Activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContentFeedActivityAdapter extends RecyclerView.Adapter<ContentFeedActivityAdapter.MyViewHolder> {

    ContentFeedActivityInterface contentFeedActivityInterface;

    private List<com.app.furoapp.model.content_feed.Activity> activityList;
    private Context context;
    @NonNull
    @Override
    public ContentFeedActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feed_activities, parent, false);

        return new ContentFeedActivityAdapter.MyViewHolder(itemView);

    }
    public ContentFeedActivityAdapter(List<Activity> activityList, Context context) {
        this.activityList = activityList;
        this.context = context;


    }

    @Override
    public void onBindViewHolder(@NonNull ContentFeedActivityAdapter.MyViewHolder holder, int position) {
        Activity activity = activityList.get(position);

        holder.actTitle.setText(activity.getTitle());
        holder.actText.setText(activity.getDescription());
        Picasso.with(context).load(activity.getImage())
                .into(holder.activityImg);

        holder.activityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contentFeedActivityInterface != null) {

                    contentFeedActivityInterface.contentFeedActivityItem(activity.getId());
                }

            }
        });
    }





    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView activityImg ;
        private TextView actTitle, actText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activityImg = itemView.findViewById(R.id.activityImage);
            actTitle = itemView.findViewById(R.id.ac_title);
            actText = itemView.findViewById(R.id.acText);


        }
    }
    public interface ContentFeedActivityInterface {
        public void contentFeedActivityItem(int id);

    }

    public void setContentFeedActivityList(ContentFeedActivityInterface contentFeedActivityInterface) {
        this.contentFeedActivityInterface = contentFeedActivityInterface;

    }
}


